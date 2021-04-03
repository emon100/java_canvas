import java.util.Optional;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * 画板类
 * @author 王一蒙
 */
class PaintSurface extends JComponent {
    private class SurfaceMouseEventListener extends MouseAdapter {
        /**
         * 监听鼠标点击
         * @param e 鼠标事件
         */
        public void mousePressed(MouseEvent e) {
            boolean isLeftClick = (e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK)!=0;
            if (isLeftClick) {// left click
                startDrag = e.getPoint();
                endDrag = new Point();
                endDrag.setLocation(startDrag.getX(), startDrag.getY());
                leftClickListener(e);
            }
        }
        /**
         * 监听鼠标释放
         * @param e 鼠标事件
         */
        public void mouseReleased(MouseEvent e) {
            if (selectedTip != null) {
                stm.execute(new Command() {//TODO: move away this closure.
                    final Drawable selected = selectedDrawable;
                    final double deltax = endDrag.getX() - startDrag.getX();
                    final double deltay = endDrag.getY() - startDrag.getY();
                    boolean moved=false;

                    @Override
                    public void execute() {
                        if(!moved) moved=true;
                        else{
                            Point2D.Float selectedStart = selected.getStartPoint();
                            selected.moveToInStart((float) (selectedStart.getX() + deltax),
                                    (float) (selectedStart.getY() + deltay));
                        }
                    }

                    @Override
                    public void unexecute() {
                        Point2D.Float selectedStart = selected.getStartPoint();
                        selected.moveToInStart((float) (selectedStart.getX() - deltax),
                                (float) (selectedStart.getY() - deltay));
                    }
                });
                selectedTip = null;
                selectedDrawable = null;
            }

            if (eraserDrawable != null) {
                eraserDrawable = null;
            }

            if (tmpDrawable != null) {
                startDrag = null;
                endDrag = null;
                stm.execute(new Command() {
                    final Drawable tmp = tmpDrawable;
                    {
                        tmp.setColor(stm.getColor());
                        tmp.setAlpha(stm.getAlpha());
                        tmp.setBorder(stm.getColor(), stm.getMyStroke());
                        tmp.disableFill();
                    }

                    @Override
                    public void execute() {
                        stm.getAllDrawable().add(tmp);
                    }

                    @Override
                    public void unexecute() {
                        stm.getAllDrawable().remove(tmp);

                    }
                });
                tmpDrawable = null;
            }
            startDrag = null;
            endDrag = null;
            repaint();
        }

        /**
         * 监听鼠标拖动
         * @param e 鼠标事件
         */
        public void mouseDragged(MouseEvent e) {
            // System.out.println(e.getX()+" "+e.getY());
            if (endDrag != null) {
                var deltax = e.getX() - endDrag.getX();
                var deltay = e.getY() - endDrag.getY();
                int x = e.getX();
                int y = e.getY();
                if (tmpDrawable != null) { //如果有临时绘制对象，给这个对象设置新的结束绘制点
                    tmpDrawable.putEndPoint(x, y);
                }
                if (selectedTip != null) { //如果有临时绘制对象，给这个对象设置新的结束绘制点
                    var selectedStart = selectedTip.getStartPoint();
                    selectedTip.moveToInStart((float) (selectedStart.getX() + deltax),
                            (float) (selectedStart.getY() + deltay));
                    var selectedDrawableStart = selectedDrawable.getStartPoint();
                    selectedDrawable.moveToInStart((float) (selectedDrawableStart.getX() + deltax),
                            (float) (selectedDrawableStart.getY() + deltay));
                }
                if (eraserDrawable != null) {
                    var eraserDrawableStart = eraserDrawable.getStartPoint();
                    eraserDrawable.moveToInStart((float) (eraserDrawableStart.getX() + deltax),
                            (float) (eraserDrawableStart.getY() + deltay));
                    Drawable s = getIntersectDrawable(endDrag);
                    eraseDrawable(s);
                }
                endDrag.move(x, y);
                repaint();
            } else {
                endDrag = e.getPoint();
            }
        }

        /**
         * 处理鼠标左键点击事件
         * @param e 鼠标事件
         */
        private void leftClickListener(MouseEvent e) {
            //TODO: Please remove side effect.
            tmpDrawable = null; //清空临时绘制对象
            tryCreateTmpDrawable(); //检测是否应该创建新的临时绘制对象
            if (tmpDrawable == null) {
                tryUseTools(); //未创建新的临时绘制对象，代表应当使用工具
            }
            repaint();
        }

    }

    /**
     *
     */

    SurfaceMouseEventListener listener = new SurfaceMouseEventListener(); //鼠标监听器

    final float[] dash1 = { 3.0f, 3.0f }; // 虚线的实心空心设置

    Point startDrag = null, endDrag = null; // 鼠标起始点，终止点

    StatesModel stm;// 程序中所有的的状态

    Drawable tmpDrawable = null; // 用于显示提示的临时Drawable对象
    Drawable selectedTip = null; // 用于显示被选择对象外边框的临时Drawable对象
    Drawable selectedDrawable = null; // 被选择的对象
    Drawable eraserDrawable = null; // 橡皮擦显示对象

    /**
     * PaintSurface构造函数
     * @param stmo 接受一个非空的StatesModel对象
     */
    public PaintSurface(StatesModel stmo) {
        assert stmo!=null;
        stm = stmo;

        setPopupMenu(); //设置右键菜单

        // 监听鼠标点击，释放
        addMouseListener(listener);

        // 监听鼠标拖动
        addMouseMotionListener(listener);
    }


    /**
     * 设置右键菜单
     */
    private void setPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        var a = new JMenuItem("暂无功能");
        popup.add(a);
        setComponentPopupMenu(popup);
    }

    /**
     * 查找与点p相交的Drawble对象
     * @param p Point类点
     * @return 如有相交的则返回Drawble对象，否则返回null
     */
    private Drawable getIntersectDrawable(Point p) {
        var l = stm.getAllDrawable();
        for (int i = l.size(); i-- > 0;) {
            var shape = l.get(i);
            // 如果填充了，在图形上即为真，或者没填充且只在边框上
            boolean onDrawable = (shape.isFilled() && shape.pointOnFill(p.x, p.y)) || shape.pointOn(p.x, p.y);
            if (onDrawable) {
                return shape;
            }
        }
        return null;
    }

    /**
     * 删除Drawble对象
     * @param s 要删除的Drawble对象
     */
    private void eraseDrawable(Drawable s) {
        if (s != null) {
            stm.execute(new Command() {
                final Drawable removedDrawable = s;
                final int removedDrawableIndex = stm.getAllDrawable().indexOf(s);

                public void execute() {
                    stm.getAllDrawable().remove(removedDrawableIndex);
                    System.out.println(stm.getColor());
                }

                @Override
                public void unexecute() {
                    stm.getAllDrawable().add(removedDrawableIndex, removedDrawable);
                }
            });
        }
    }

    /**
     * 获得点p所处的区域属于哪个Drawble对象的填充区
     * @return 返回所处填充区对应的Drawble对象，否则返回null
     */
    private Drawable getFillDrawable() {
        var l = stm.getAllDrawable();
        for (int i = l.size(); i-- > 0;) {
            var shape = l.get(i);
            // 如果填充了，在图形上即为真，或者没填充且只在边框上
            if (shape.pointOnFill(startDrag.x, startDrag.y)) {
                return shape;
            }
        }
        return null;
    }

    /**
     * 尝试生成一个临时绘制对象，并赋值给tmpDrawable
     */
    private void tryCreateTmpDrawable() {
        switch (stm.getType()) {
            case LINE: {
                tmpDrawable = BasicDrawableFactory.makeLine(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case PATH: {
                tmpDrawable = BasicDrawableFactory.makePath(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case TEXTBOX: {
                tmpDrawable = BasicDrawableFactory.makeTextBox(startDrag.x, startDrag.y, this);
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case RECTANGLE: {
                tmpDrawable = BasicDrawableFactory.makeRec(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.disableFill();
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case ELLIPSE: {
                tmpDrawable = BasicDrawableFactory.makeEllipse(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.disableFill();
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case TRIANGLE: {
                tmpDrawable = BasicDrawableFactory.makeTri(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.disableFill();
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            default:
                break;

        }
    }

    /**
     * 对目前对应的工具进行使用
     */
    private void tryUseTools() {
        switch (stm.getType()) {
            case SELECT: {
                selectedDrawable = getIntersectDrawable(startDrag);
                if (selectedDrawable != null) {
                    System.out.println("selected");
                    selectedTip = getSelectedTip(selectedDrawable);
                }
            }
                break;
            case FILL: {
                Optional.ofNullable(getFillDrawable()).ifPresent(s -> {
                    stm.execute(new Command() {
                        final boolean isFilledBefore = s.isFilled();
                        final Color filledColorBefore = isFilledBefore ? s.getFillColor() : Color.white;
                        final Color filledColorNow = stm.getColor();

                        public void execute() {
                            s.setFill(filledColorNow);
                        }

                        public void unexecute() {
                            if (isFilledBefore) {
                                s.setFill(filledColorBefore);
                            } else {
                                s.disableFill();
                            }
                        }
                    });
                });
            }
                break;
            case ERASER: {
                eraserDrawable = createEraserDrawble();
                Drawable s = getIntersectDrawable(endDrag);
                eraseDrawable(s);
            }
                break;
            default:
                throw new IllegalArgumentException("LeftClickListener: Unexpected value: " + stm.getType());
        }
    }

    /**
     * 获得Drawable对象的选择框
     * @param shape 选中Drawable对象
     * @return 选中对象的选择框
     */
    private Drawable getSelectedTip(Drawable shape) {
        var rec = shape.getOutBound();
        var res = BasicDrawableFactory.makeRec((int) rec.getX(), (int) rec.getY(), (int) (rec.getX() + rec.getWidth()),
                (int) (rec.getY() + rec.getHeight()));
        res.setBorder(shape.getBorderColor(),
                new MyStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
        return res;
    }

    /**
     * 在画板上创建橡皮擦对象，一个小矩形
     */
    private Drawable createEraserDrawble() {
        return BasicDrawableFactory.makeRec(startDrag.x - 3, startDrag.y - 3, startDrag.x + 3, startDrag.y + 3);
    }

    /**
     * 绘制画图板的背景
     * @param g2 Graphics2D类型
     */
    private void paintBackground(Graphics2D g2) {// TODO: 改的有创意一点，比如类似PS的灰白相间代表透明
        Dimension surfaceDimension = getSize();
        g2.setPaint(Color.WHITE);

        for (int i = 0; i < surfaceDimension.width; i += 10) {
            Shape line = new Line2D.Float(i, 0, i, surfaceDimension.height);
            g2.draw(line);
        }

        for (int i = 0; i < surfaceDimension.height; i += 10) {
            Shape line = new Line2D.Float(0, i, surfaceDimension.width, i);
            g2.draw(line);
        }
    }

    /**
     * 绘制画图板，交给每一个图素来完成
     * @param g2 Graphics2D类型
     */
    private void paintBoard(Graphics2D g2) {
        for (var d : stm.getAllDrawable()) {
            d.drawOnGraphics2D(g2);
        }
    }

    /**
     * 返回坐标的绘制点
     * @param mouseNow
     * @return
     */
    private Point getToolTipdrawPoint(Point mouseNow) {
        Dimension surfaceDimension = getSize();
        int newX = Math.max(Math.min(mouseNow.x, surfaceDimension.width - 70), 5);
        int newY = Math.min(Math.max(mouseNow.y, 15), surfaceDimension.height - 5);
        return new Point(newX, newY);
    }

    /**
     * 绘制画图板上的提示，如提示的虚线，移动的对象，橡皮擦对象
     * @param g2 Graphics2D类型
     */
    private void paintTips(Graphics2D g2) {
        // 提示的虚线
        if (startDrag != null && endDrag != null) {
            paintDottedLine(g2);
        }
        if (tmpDrawable != null) { // 提示对象的绘制
            tmpDrawable.drawOnGraphics2D(g2);
        }
        if (selectedTip != null) { // 移动对象的绘制
            selectedTip.drawOnGraphics2D(g2);
        }
        if (eraserDrawable != null) { // 橡皮擦对象绘制
            eraserDrawable.drawOnGraphics2D(g2);
        }
    }

    /**
     * 绘制画图板上的虚线
     * @param g2 Graphics2D类型
     */
    private void paintDottedLine(Graphics2D g2) {
        var oldComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        // 提示坐标
        Point toolTipPoint = getToolTipdrawPoint(endDrag);
        g2.setPaint(Color.BLACK);
        g2.drawString("X: " + endDrag.x + " Y:" + endDrag.y, toolTipPoint.x, toolTipPoint.y);
        g2.setComposite(oldComposite);
    }


    /**
     * 绘制画图板，在repaint()被调用或重绘事件发生后会被自动调用。
     * @param g Graphics类型
     */
    public void paintComponent(Graphics g) {

        // step0: initial g2
        Graphics2D g2 = initializeGraphics2D(g);

        // step1: paintBackground
        paintBackground(g2);

        // step2: paintBoard
        paintBoard(g2);

        // step3: paintTips
        paintTips(g2);
    }

    private Graphics2D initializeGraphics2D(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2;
    }

}
