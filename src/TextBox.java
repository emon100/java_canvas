import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;
import javax.swing.JComponent;


/** 
 * 这个类是文本对象类，这个类可以实现绘制文本和呼出编辑对话框。
 * @author 王一蒙
 * 
 */
public class TextBox implements Drawable {

    private static final long serialVersionUID = 979367086863174749L;//自动生成的序列化对象

    
    // filled代表是否填充文字
    String filled = null; // 填充的文字

    Font font = new Font("SansSerif",Font.BOLD,14);

    Color color = Color.BLACK; // 画笔颜色
    float alpha = 1f; // 透明度

    Point2D.Float startPoint=null;

    Rectangle2D outBound = null;

    TextBox(Point2D.Float p) {
        startPoint = p;
        getOutBound();
    }

    public void setFont(Font f){
        font = f;
    }

    public void setText(String s){
        filled = s;
    }

    /**
     * 将文本对象绘制到Graphics2D对象上
     * @param g 绘制到的Graphics2D对象
     */
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setColor(color); //设置绘制颜色
        if (isFilled()) {
            var oldFont = g.getFont(); //保存g之前的字体
            g.setFont(font); //设置字体
            g.drawString(filled, startPoint.x, startPoint.y);  //绘制文字
            g.setFont(oldFont); //恢复g之前的字体
        } else { //绘制光标
            g.setStroke(new MyStroke(1.5f)); 
            g.drawLine((int) startPoint.x, (int) startPoint.y, (int) startPoint.x, (int) startPoint.y - font.getSize());
        }
    }

    /**
     * 返回此对象是否已经有文本填充
     */
    @Override
    public boolean isFilled() {
        return filled != null;
    }

    @Override
    public void setFill() {
        //ignore
    }

    @Override
    public void setFill(Color c) {
        color = c;
    }

    @Override
    public void setAlpha(float f) {

    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void setBorder(Color c, MyStroke s) {

    }

    @Override
    public Color getBorderColor() {
        return color;
    }

    @Override
    public MyStroke getMyStroke() {
        return new MyStroke();
    }

    @Override
    public Color getFillColor() {
        return color;
    }

    @Override
    public void disableFill() {
        setFill();
    }

    @Override
    public void scale(float times) {

    }

    @Override
    public void moveToInStart(Float p) {
        startPoint = p;
        outBound=null;
    }

    @Override
    public void moveToInStart(float x, float y) {
        startPoint.setLocation(x, y);
        outBound=null;
    }

    @Override
    public Float getStartPoint() {
        return startPoint;
    }

    @Override
    public void putEndPoint(Float p) {
        startPoint = p;
        outBound=null;
    }

    @Override
    public void putEndPoint(float x, float y) {
        startPoint.setLocation(x, y);
        outBound=null;
    }

    @Override
    public Float getEndPoint() {
        return startPoint;
    }

    @Override
    public boolean pointOn(Float p) {
        return false;
    }   

    @Override
    public boolean pointOn(float x, float y) {
        return false;
    }

    @Override
    public boolean pointOnFill(Float p) {
        return getOutBound().contains(p);
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        return getOutBound().contains(x,y);
    }


    @Override
    public Rectangle2D getOutBound() {
        int fontSize = font.getSize();
        if(isFilled()){
            if(outBound!=null){
                return outBound;
            }
            var tmpG = new JComponent(){};
            var context = tmpG.getFontMetrics(font).getFontRenderContext();
            outBound =  font.getStringBounds(filled, context);
            outBound.setRect(startPoint.x,startPoint.y-fontSize,outBound.getWidth(),outBound.getHeight());
            return outBound;
        }else{
            return new Rectangle2D.Float((int) startPoint.x, (int) startPoint.y, (int) startPoint.x+1,fontSize);
        }
    }

    @Override
    public Float getTopLeft() {
        return new Point2D.Float(startPoint.x,startPoint.y-font.getSize());
    }

    @Override
    public Float getBottomRight() {
        var outBound = getOutBound();
        return new Point2D.Float((float) outBound.getMaxX(), (float) outBound.getMaxY());
    }

}