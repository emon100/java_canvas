import java.awt.*;
import java.awt.geom.*;

/**
 * 提供生成各种Drawble对象方法的工厂类
 * @author 王一蒙
 */
public class BasicDrawableFactory {

    /**
     * 创造一个直线
     * @param startx 绘制起始点的x
     * @param starty 绘制起始点的y
     * @param endx 绘制终止点的x
     * @param endy 绘制起始点的y
     * @return Drawble对象
     */
    public static Drawable makeLine(int startx, int starty, int endx, int endy) {
        var a = new Line(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    /**
     * 创造一个直线
     * @param startx 绘制起始点的x
     * @param starty 绘制起始点的y
     * @param endx 绘制终止点的x
     * @param endy 绘制起始点的y
     * @return Drawble对象
     */
    public static Drawable makePath(int startx, int starty, int endx, int endy) {
        var a = new Path(new Point.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    /**
     * 创造一个矩形
     * @param startx 绘制起始点的x
     * @param starty 绘制起始点的y
     * @param endx 绘制终止点的x
     * @param endy 绘制起始点的y
     * @return Drawble对象
     */ 
    public static Drawable makeRec(int startx, int starty, int endx, int endy) {
        var a = new Rectangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    /**
     * 创造一个三角形
     * @param startx 绘制起始点的x
     * @param starty 绘制起始点的y
     * @param endx 绘制终止点的x
     * @param endy 绘制起始点的y
     * @return Drawble对象
     */ 
    public static Drawable makeTri(int startx, int starty, int endx, int endy) {
        var a = new Triangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    /**
     * 创造一个直线
     * @param startx 绘制起始点的x
     * @param starty 绘制起始点的y
     * @param endx 绘制终止点的x
     * @param endy 绘制起始点的y
     * @return Drawble对象
     */ 
    public static Drawable makeEllipse(int startx, int starty, int endx, int endy) {
        var a = new Ellipse(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    /**
     * 创造一个文本框
     * @param startx 绘制起始点的x
     * @param starty 绘制起始点的y
     * @return Drawble对象
     */     
    public static Drawable makeTextBox(int startx, int starty, PaintSurface outer) {
        var a = new TextBox(new Point2D.Float(startx, starty));
        var b = new TextSettingPanel(outer, a);
        b.showTextBoxDialog();
        return a;
    }

}
