import java.awt.*;
import java.awt.geom.*;

/**
 * 提供生成各种Drawble对象方法的工厂类
 * @author 王一蒙
 */
public class BasicDrawableFactory {
    public static Drawable makeLine(int startx, int starty, int endx, int endy) {
        var a = new Line(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makePath(int startx, int starty, int endx, int endy) {
        var a = new Path(new Point.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeRec(int startx, int starty, int endx, int endy) {
        var a = new Rectangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeTri(int startx, int starty, int endx, int endy) {
        var a = new Triangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeEllipse(int startx, int starty, int endx, int endy) {
        var a = new Ellipse(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeTextBox(int startx, int starty, PaintSurface outer) {
        var a = new TextBox(new Point2D.Float(startx, starty));
        var b = new TextSettingPanel(outer, a);
        b.showTextBoxDialog();
        return a;
    }

}
