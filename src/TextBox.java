import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;
import javax.swing.JOptionPane;

public class TextBox implements Drawable{

    private void TextBoxDialog() { 
        var oPane = new JOptionPane();
        //oPane.add()TODO: 字体，字号选择对话框
        filled = oPane.showInputDialog(null, "设置文本","Input Here");
    }
    String filled = null; //填充的文字

    String fontName = "SansSerif"; //字体名
    int fontstyle = Font.BOLD; //字形
    int fontSize = 14; //字号

    Color color = Color.BLACK;      //画笔颜色
    float alpha = 1f;       //透明度
    
    Point2D.Float startPoint;
    TextBox(Point2D.Float p) {
        startPoint = p;
    }
    
    //几乎所有的计算都可以借用矩形
    //filled代表是否填充文字
    
    
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setColor(color);
        if(isFilled()){
            var oldFont = g.getFont();
            g.setFont(new Font(fontName,fontstyle,fontSize));
            g.drawString(filled,startPoint.x,startPoint.y);
            g.setFont(oldFont);
        }else{
            g.setStroke(new MyStroke(1.5f));
            g.drawLine((int)startPoint.x,(int)startPoint.y,(int)startPoint.x,(int)startPoint.y-fontSize);
        }
    }

    @Override 
    public boolean isFilled(){
        return filled!=null; 
    }

    @Override
    public void setFill(){ //弹出一个选框，设置文字，影响字体，字号等

    }

    @Override
    public void setFill(Color c){ 
        color=c;
    }

    @Override
    public void setAlpha(float f) {
        
    }

    @Override
    public void setColor(Color c) {
        color=c;
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
        startPoint = p ;
    }

    @Override
    public void moveToInStart(float x, float y) {
        startPoint.setLocation(x, y);
    }

    @Override
    public Float getStartPoint() {
        return startPoint;
    }

    @Override
    public void putEndPoint(Float p) {
        startPoint = p;
    }

    @Override
    public void putEndPoint(float x, float y) {
        startPoint.setLocation(x, y);
    }

    @Override
    public Float getEndPoint() {
        return startPoint;
    }

    @Override
    public boolean pointOn(Float p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pointOn(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pointOnFill(Float p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pointOnFill(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setStartPoint(Float p) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setStartPoint(float x, float y) {
        // TODO Auto-generated method stub

    }

    @Override
    public Rectangle2D getOutBound() {
        // TODO Auto-generated method stub
        return null;
    }

}