import java.awt.*;
import java.awt.geom.*;



public class BasicDrawableFactory {
   

   
    

    public static Drawable makeLine(int startx, int starty, int endx, int endy) {
        var a = new Line(new Point(startx, starty));
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

    public static Drawable makeTri(int startx, int starty ,int endx, int endy) {
        var a = new Triangle(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

    public static Drawable makeEllipse(int startx, int starty, int endx, int endy) {
        var a = new Ellipse(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
    }

	public static Drawable makeTextBox(int startx, int starty ,int endx, int endy) {
		var a = new TextBox(new Point2D.Float(startx, starty));
        a.putEndPoint(new Point2D.Float(endx, endy));
        return a;
	}
}
