import java.util.Optional;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

class PaintSurface extends JComponent {
    private class SurfaceMouseEventListener extends MouseAdapter {
        /**
         * 监听鼠标点击
         */
        public void mousePressed(MouseEvent e) {
            if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {// left click
                startDrag = e.getPoint();
                endDrag = new Point();
                endDrag.setLocation(startDrag.getX(), startDrag.getY());
                leftClickListener(e);
            } else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) { // right click

            } else {
                // ignore

            }

        }

        /**
         * 监听鼠标释放
         */
        public void mouseReleased(MouseEvent e) {
            if (tmpDrawable != null) {
                stm.execute(new Command() {
                    Drawable tmp = tmpDrawable;
                    {
                        tmp.setColor(stm.getColor());
                        tmp.setAlpha(stm.getAlpha());
                        tmp.setBorder(stm.getColor(), new MyStroke()); // todo
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

            if (selectedTip != null) {
                stm.execute(new Command() {
                    Drawable selected = selectedDrawable;
                    double deltax = endDrag.getX() - startDrag.getX();
                    double deltay = endDrag.getY() - startDrag.getY();

                    @Override
                    public void execute() {
                        Point2D.Float selectedStart = selected.getStartPoint();
                        selected.moveToInStart((float) (selectedStart.getX() + deltax),
                                (float) (selectedStart.getY() + deltay));

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

            startDrag = null;
            endDrag = null;
            repaint();
        }

        /**
         * 监听鼠标拖动
         */
        public void mouseDragged(MouseEvent e) {
            // System.out.println(e.getX()+" "+e.getY());
            if (endDrag != null) {
                var deltax = e.getX() - endDrag.getX();
                var deltay = e.getY() - endDrag.getY();
                int x = e.getX();
                int y = e.getY();
                if (tmpDrawable != null) {
                    tmpDrawable.putEndPoint(x, y);
                }
                if (selectedTip != null) {
                    var selectedStart = selectedTip.getStartPoint();
                    selectedTip.moveToInStart((float) (selectedStart.getX() + deltax),
                            (float) (selectedStart.getY() + deltay));
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

        private void leftClickListener(MouseEvent e) {
            tmpDrawable = null;
            tryCreateTmpDrawable();
            if (tmpDrawable == null) {
                tryUseTools();
            }
            repaint();
        }

    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    SurfaceMouseEventListener listener = new SurfaceMouseEventListener();

    final float[] dash1 = { 3.0f, 3.0f }; // 虚线的实心空心设置

    Point startDrag, endDrag; // 鼠标起始点，终止点
    Dimension size = getSize(); // 当前窗口大小

    StatesModel stm;// 程序中所有的的状态

    Drawable tmpDrawable = null; // 用于显示提示的临时Drawable对象
    Drawable selectedTip = null; // 用于显示被选择对象外边框的临时Drawable对象
    Drawable selectedDrawable = null; // 被选择的对象
    Drawable eraserDrawable = null; // 橡皮擦显示对象

    public PaintSurface(StatesModel stmo) { // 构造函数，接受一个StatesModel对象
        stm = stmo;

        JPopupMenu popup = new JPopupMenu();// setPopUpMenu()
        popup.add(new JMenuItem("Cut"));
        setComponentPopupMenu(popup);

        // 监听鼠标点击，释放
        this.addMouseListener(listener);

        // 监听鼠标拖动
        this.addMouseMotionListener(listener);
    }

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

    private void eraseDrawable(Drawable s) {
        if (s != null) {
            stm.execute(new Command() {
                Drawable removedDrawable = s;
                int removedDrawableIndex = stm.getAllDrawable().indexOf(s);

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

    private void tryCreateTmpDrawable() {
        switch (stm.getType()) {
            case LINE: {
                tmpDrawable = BasicDrawableFactory.makeLine(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, MyStroke.CAP_BUTT, MyStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case PATH: {
                tmpDrawable = BasicDrawableFactory.makePath(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, MyStroke.CAP_BUTT, MyStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case TEXTBOX: {
                tmpDrawable = BasicDrawableFactory.makeTextBox(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case RECTANGLE: {
                tmpDrawable = BasicDrawableFactory.makeRec(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, MyStroke.CAP_BUTT, MyStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.disableFill();
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case ELLIPSE: {
                tmpDrawable = BasicDrawableFactory.makeEllipse(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, MyStroke.CAP_BUTT, MyStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.disableFill();
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            case TRIANGLE: {
                tmpDrawable = BasicDrawableFactory.makeTri(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
                tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                        new MyStroke(3.0f, MyStroke.CAP_BUTT, MyStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
                tmpDrawable.disableFill();
                tmpDrawable.setColor(stm.getColor());
                tmpDrawable.setAlpha(stm.getAlpha());
            }
                break;
            default:
                break;

        }
    }

    private void tryUseTools() {
        switch (stm.getType()) {
            case SELECT: {
                selectedDrawable = getIntersectDrawable(startDrag);
                if (selectedDrawable != null) {
                    System.out.println("selected");
                    selectedTip = getSelectedTip(selectedDrawable);
                    repaint();
                }
            }
                break;
            case FILL: {
                Optional.ofNullable(getFillDrawable()).ifPresent(s -> {
                    stm.execute(new Command() {
                        boolean isFilledBefore = s.isFilled();
                        Color filledColorBefore = isFilledBefore ? s.getFillColor() : Color.white;
                        Color filledColorNow = stm.getColor();

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
                    repaint();
                });
            }
                break;
            case ERASER: {
                eraserDrawable = createEraserDrawble();
                Drawable s = getIntersectDrawable(endDrag);
                eraseDrawable(s);
                repaint();
            }
                break;
            default:
                throw new IllegalArgumentException("LeftClickListener: Unexpected value: " + stm.getType());
        }
    }

    private Drawable getSelectedTip(Drawable shape) {
        var rec = shape.getOutBound();
        var res = BasicDrawableFactory.makeRec((int) rec.getX(), (int) rec.getY(), (int) (rec.getX() + rec.getWidth()),
                (int) (rec.getY() + rec.getHeight()));
        res.setBorder(shape.getBorderColor(),
                new MyStroke(3.0f, MyStroke.CAP_BUTT, MyStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
        return res;
    }

    private Drawable createEraserDrawble() {
        var rec = BasicDrawableFactory.makeRec(startDrag.x - 3, startDrag.y - 3, startDrag.x + 3, startDrag.y + 3);
        return rec;
    }

    // 初始化背景
    private void paintBackground(Graphics2D g2) {// TODO: 改的有创意一点，比如类似PS的灰白相间代表透明
        g2.setPaint(Color.WHITE);

        for (int i = 0; i < size.width; i += 10) {
            Shape line = new Line2D.Float(i, 0, i, size.height);
            g2.draw(line);
        }

        for (int i = 0; i < size.height; i += 10) {
            Shape line = new Line2D.Float(0, i, size.width, i);
            g2.draw(line);
        }
    }

    private void paintBoard(Graphics2D g2) {
        for (var d : stm.getAllDrawable()) {
            d.drawOnGraphics2D(g2);
        }
    }

    private Point getToolTipdrawPoint(Point mouseNow) {
        int newX = Math.max(Math.min(mouseNow.x, size.width - 70), 5);
        int newY = Math.min(Math.max(mouseNow.y, 15), size.height - 5);
        return new Point(newX, newY);
    }

    private void paintTips(Graphics2D g2) {
        // 提示的虚线
        if (startDrag != null && endDrag != null) {
            var oldComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

            // 提示坐标
            Point toolTipPoint = getToolTipdrawPoint(endDrag);
            g2.setPaint(Color.BLACK);
            g2.drawString("X: " + endDrag.x + " Y:" + endDrag.y, toolTipPoint.x, toolTipPoint.y);
            g2.setComposite(oldComposite);
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

    public void paintComponent(Graphics g) {
        // System.out.println("paintComponent called");

        size = getSize();
        // step0: initial g2
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // step1: paintBackground
        paintBackground(g2);

        // step2: paintBoard
        paintBoard(g2);

        // step3: paintTips
        paintTips(g2);
    }

}
