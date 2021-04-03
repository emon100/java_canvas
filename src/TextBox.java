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

    
    String filled = null; // 填充的文字

    
    Font font = new Font("SansSerif",Font.BOLD,14);// 当前字体

    Color color = Color.BLACK; // 画笔颜色
    float alpha = 1f; // 透明度

    Point2D.Float startPoint=null; //绘制起始点

    Rectangle2D outBound = null; //外边框

    TextBox(Point2D.Float p) { //构造函数
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
            g.setStroke(new MyStroke(1.5f).getBasicStroke()); 
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

    
    /**
     * 忽略设置填充
     */
    @Override
    public void setFill() {
        //ignore
    }

    /**
     * 忽略设置填充
     */
    @Override
    public void setFill(Color c) {
        color = c;
    }

    /**
     * 忽略设置透明度
     */
    @Override
    public void setAlpha(float f) {

    }

    /**
     * 设置文字颜色
     */
    @Override
    public void setColor(Color c) {
        color = c;
    }

    /**
     * 忽略文字边框设置
     */
    @Override
    public void setBorder(Color c, MyStroke s) {

    }

    /**
     * 设置边框
     */
    @Override
    public Color getBorderColor() {
        return color;
    }

    /**
     * 获得画笔
     * @return 画笔
     */
    @Override
    public MyStroke getMyStroke() {
        return new MyStroke();
    }

    /**
     * 获得画笔
     * @return 画笔
     */
    @Override
    public Color getFillColor() {
        return color;
    }

    /**
     * 忽略设置填充
     */
    @Override
    public void disableFill() {
        setFill();
    }

    /**
     * 忽略设置放大倍数
     */
    @Override
    public void scale(float times) {

    }

    /**
     * @param p 文本框移动到的位置
     */
    @Override
    public void moveToInStart(Float p) {
        startPoint = p;
        outBound=null;
    }

    /**
     * @param x 文本框移动到的x位置
     * @param y 文本框移动到的y位置
     */
    @Override
    public void moveToInStart(float x, float y) {
        startPoint.setLocation(x, y);
        outBound=null;
    }

    /**
     * 获得绘制起始点
     * @return Point2D.Float 起始点
     */
    @Override
    public Float getStartPoint() {
        return startPoint;
    }

    /**
     * 设置绘制结束点
     * @param p 绘制结束点
     */
    @Override
    public void putEndPoint(Float p) {
        startPoint = p;
        outBound=null;
    }

    /**
     * 设置绘制结束点
     * @param x 绘制结束点x
     * @param y 绘制结束点y
     */
    @Override
    public void putEndPoint(float x, float y) {
        startPoint.setLocation(x, y);
        outBound=null;
    }

    /**
     * 
     * @return 获得绘制结束点
     */
    @Override
    public Float getEndPoint() {
        return startPoint;
    }

    /**
     * 返回点是否在文本边框上
     */
    @Override
    public boolean pointOn(Float p) {
        return false;
    }   

    /**
     * 返回点是否在文本边框上
     */
    @Override
    public boolean pointOn(float x, float y) {
        return false;
    }

    /**
     * 返回点是否在文本填充上
     */
    @Override
    public boolean pointOnFill(Float p) {
        return getOutBound().contains(p);
    }

    /**
     * 返回点是否在文本填充上
     */
    @Override
    public boolean pointOnFill(float x, float y) {
        return getOutBound().contains(x,y);
    }

    /**
     * 获得文本外边框
     * @return Rectangle2D对象外边框
     */
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

    /**
     * 获得文本框左上点
     */
    @Override
    public Float getTopLeft() {
        return new Point2D.Float(startPoint.x,startPoint.y-font.getSize());
    }

    /**
     * 获得文本框右下点
     */
    @Override
    public Float getBottomRight() {
        var outBound = getOutBound();
        return new Point2D.Float((float) outBound.getMaxX(), (float) outBound.getMaxY());
    }

}