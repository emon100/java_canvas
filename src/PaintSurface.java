import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

class PaintSurface extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ArrayList<Shape> shapes = new ArrayList<Shape>();

    // public Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED,
    // Color.BLUE, Color.PINK };
    // GeneralPath gp = new GeneralPath();
    // int ra = 0;// 控制下一个图形的形状

    Point startDrag, endDrag; // 鼠标起始点，终止点
    Dimension size = getSize(); // 当前窗口大小

    StatesModel stm;// 程序中所有的的状态

    Drawable tmpDrawable = null; // 用于显示提示的临时Drawable对象

    public PaintSurface(StatesModel stmo) {
        stm = stmo;

        JPopupMenu popup = new JPopupMenu();// setPopUpMenu()
        popup.add(new JMenuItem("Cut"));
        setComponentPopupMenu(popup);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {// left click
                    startDrag = e.getPoint();
                    endDrag = e.getPoint();
                    createTmpDrawable();
                    repaint();
                } else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) { // right click

                } else {
                    // ignore
                }
            }

            // 鼠标松开是可以创建一个矩形，将起始点归零
            public void mouseReleased(MouseEvent e) {
                startDrag = null;
                endDrag = null;
                if (tmpDrawable != null) {
                    tmpDrawable.setColor(stm.getColor());
                    tmpDrawable.setAlpha(stm.getAlpha());
                    tmpDrawable.setBorder(stm.getColor(), new BasicStroke());
                    stmo.getAllDrawable().add(tmpDrawable);
                    tmpDrawable = null;
                    repaint();
                }
            }
        });

        

        // 鼠标移动时，我们只需要知道鼠标的重点位置就好
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // System.out.println(e.getX()+" "+e.getY());
                if (endDrag != null) {
                    int x = e.getX();
                    int y = e.getY();
                    endDrag.move(x, y);
                    tmpDrawable.putEndPoint(new Point2D.Float(x, y));
                    repaint();
                }

            }
        });
    }

    private void createTmpDrawable() {
        switch (stm.getType()) {
            case LINE: {
                tmpDrawable = BasicDrawableFactory.makeLine(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
            }
                break;
            case PATH: {
                tmpDrawable = BasicDrawableFactory.makePath(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
            }
                break;
            case TEXTBOX:{
                tmpDrawable = BasicDrawableFactory.makeTextBox(startDrag.x, startDrag.y, startDrag.x, startDrag.y);
            }
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + stm.getType());
        }
        float[] dash1 = { 3.0f, 3.0f };
        // tmp
        tmpDrawable.setBorder(tmpDrawable.getBorderColor(),
                new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 3.0f));
        tmpDrawable.disableFill();
        tmpDrawable.setColor(stm.getColor());
        tmpDrawable.setAlpha(stm.getAlpha());
    }

    // 初始化背景
    private void paintBackground(Graphics2D g2) {
        g2.setPaint(Color.LIGHT_GRAY);

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
        var a = stm.getAllDrawable();
        for (var d : a) {
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
