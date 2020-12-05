import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;
import java.beans.Transient;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.event.*;

/** 
 * 这个类是文本对象类，这个类可以实现绘制文本和呼出编辑对话框。
 * @author 王一蒙
 * 
 */
public class TextBox implements Drawable, Serializable {

    private static final long serialVersionUID = 979367086863174749L;//自动生成的序列化对象

    private class TextTestPanel extends JComponent {
        /**
         *
         */
        private static final long serialVersionUID = 7484077858050896589L;

        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
        /**
         * 编辑对话框弹出的函数
         */
        public void showTextBoxDialog() {
            JPanel fontSelectorPanel = new JPanel();
            fontSelectorPanel.setLayout(new BoxLayout(fontSelectorPanel, BoxLayout.Y_AXIS));
            fontSelectorPanel.add(new JLabel("Font family:"));

            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            JComboBox<String> fonts = new JComboBox<>(gEnv.getAvailableFontFamilyNames());
            fonts.setSelectedItem(0);   
            fonts.setMaximumRowCount(5);
            fontSelectorPanel.add(fonts);

            fontSelectorPanel.add(new JLabel("Style:"));

            String[] styleNames = { "Plain", "Bold", "Italic", "Bold Italic" };
            JComboBox<String> styles = new JComboBox<>(styleNames);
            fontSelectorPanel.add(styles);

            fontSelectorPanel.add(new JLabel("Size:"));

            JSpinner sizes = new JSpinner(new SpinnerNumberModel(fontSize, 6, 60, 1));
            fontSelectorPanel.add(sizes);

            fonts.addItemListener(i -> {
                try {
                    fontName = (String) fonts.getSelectedItem();
                    outerComponent.repaint();
                } catch (NumberFormatException nfe) {
                }
            });

            styles.addItemListener(i -> {
                try {
                    fontstyle = styles.getSelectedIndex();
                    outerComponent.repaint();
                } catch (NumberFormatException nfe) {
                }
            });

            sizes.addChangeListener(c -> {
                try {
                    String size = sizes.getModel().getValue().toString();
                    fontSize = Integer.parseInt(size);
                    outerComponent.repaint();
                } catch (NumberFormatException nfe) {
                    fontSize=14;
                }
            });


            JDialog dialog = new JDialog();
            dialog.setSize(300, 250);
            dialog.setTitle("input");

            JPanel root = new JPanel();
            JPanel inputpanel = new JPanel();
            JTextField input = new JTextField(20);
            input.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    input();
                }
                public void removeUpdate(DocumentEvent e) {
                    input();
                }
                public void insertUpdate(DocumentEvent e) {
                    input();
                }
                
                public void input() {
                    filled = input.getText();
                    outerComponent.repaint();
                }
            });

            inputpanel.setLayout(new FlowLayout());
            JButton enterButton = new JButton("Enter");
            enterButton.addActionListener(e->{
                dialog.dispose();
            });
            inputpanel.add(input);
            inputpanel.add(enterButton);
            
            input.setText("Input Here");
            root.setLayout( new BoxLayout(root, BoxLayout.Y_AXIS));
            root.add(fontSelectorPanel);
            root.add(inputpanel);
            root.add(textTestPanel); 
            
            dialog.add(root);
            dialog.setVisible(true);
        }

    }
    
    TextTestPanel textTestPanel;
    transient JComponent outerComponent = null;
    // filled代表是否填充文字
    String filled = null; // 填充的文字

    String fontName = "SansSerif"; // 字体名
    int fontstyle = Font.BOLD; // 字形
    int fontSize = 14; // 字号

    Color color = Color.BLACK; // 画笔颜色
    float alpha = 1f; // 透明度

    Point2D.Float startPoint=null;

    Rectangle2D.Float outBound=null;

    TextBox(Point2D.Float p,JComponent outer) {
        startPoint = p;
        outerComponent = outer;
        outBound=null;
        textTestPanel = new TextTestPanel();
        getOutBound();
    }

    /**
     * 将文本对象绘制到Graphics2D对象上
     * @param g 绘制到的Graphics2D对象
     */
    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        if(outerComponent==null){
            System.out.println("fuck");
        }
        g.setColor(color); //设置绘制颜色
        if (isFilled()) {
            var oldFont = g.getFont(); //保存g之前的字体
            g.setFont(new Font(fontName, fontstyle, fontSize)); //设置字体
            g.drawString(filled, startPoint.x, startPoint.y);  //绘制文字
            g.setFont(oldFont); //恢复g之前的字体
        } else { //绘制光标
            g.setStroke(new MyStroke(1.5f)); 
            g.drawLine((int) startPoint.x, (int) startPoint.y, (int) startPoint.x, (int) startPoint.y - fontSize);
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
    public void setFill() { // 弹出一个选框，设置文字，影响字体，字号等
        textTestPanel.showTextBoxDialog();
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
        if(isFilled()){
            if(outBound!=null){
                return outBound;
            }
            Font f = new Font(fontName, fontstyle, fontSize);
            var context = outerComponent.getFontMetrics(f).getFontRenderContext();
            outBound = (Rectangle2D.Float) f.getStringBounds(filled, context);
            outBound.setRect(startPoint.x,startPoint.y-fontSize,outBound.getWidth(),outBound.getHeight());
            return outBound;
        }else{
            return new Rectangle2D.Float((int) startPoint.x, (int) startPoint.y, (int) startPoint.x+1,fontSize);
        }
    }

    @Override
    public Float getTopLeft() {
        return new Point2D.Float(startPoint.x,startPoint.y-fontSize);
    }

    @Override
    public Float getBottomRight() {
        var outBound = getOutBound();
        return new Point2D.Float((float) outBound.getMaxX(), (float) outBound.getMaxY());
    }

}