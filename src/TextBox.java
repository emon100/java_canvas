import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;
import javax.swing.*;
import javax.swing.event.*;


public class TextBox implements Drawable {
    private class TextTestPanel extends JComponent {

        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        public void setFont(Font font) {
            super.setFont(font);
            repaint();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.setFont(getFont());
            FontMetrics metrics = g.getFontMetrics();
            String text = filled == null ? "Input Here" : filled;
            int x = getWidth() / 2 - metrics.stringWidth(text) / 2;
            int y = getHeight() - 20;
            g.drawString(text, x, y);
        }

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

            TextTestPanel textTestPanel = this;
            textTestPanel.setBackground(Color.white);

            fonts.addItemListener(i -> {
                try {
                    fontName = (String) fonts.getSelectedItem();

                    textTestPanel.setFont(new Font(fontName, fontstyle, fontSize));
                } catch (NumberFormatException nfe) {
                }
            });

            styles.addItemListener(i -> {
                try {
                    fontstyle = styles.getSelectedIndex();
                    textTestPanel.setFont(new Font(fontName, fontstyle, fontSize));
                } catch (NumberFormatException nfe) {
                }
            });

            sizes.addChangeListener(c -> {
                try {
                    String size = sizes.getModel().getValue().toString();
                    fontSize = Integer.parseInt(size);
                    textTestPanel.setFont(new Font(fontName, fontstyle, fontSize));
                } catch (NumberFormatException nfe) {
                }
            });

            JDialog dialog = new JDialog(null, "input", ModalityType.APPLICATION_MODAL);
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
                    repaint();
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
            dialog.setSize(300, 300);
            dialog.setVisible(true);
        }

    }
    
    TextTestPanel textTestPanel = new TextTestPanel();
    
    // filled代表是否填充文字
    String filled = null; // 填充的文字

    String fontName = "SansSerif"; // 字体名
    int fontstyle = Font.BOLD; // 字形
    int fontSize = 14; // 字号

    Color color = Color.BLACK; // 画笔颜色
    float alpha = 1f; // 透明度

    Point2D.Float startPoint;

    TextBox(Point2D.Float p) {
        startPoint = p;
    }

    @Override
    public void drawOnGraphics2D(Graphics2D g) {
        g.setColor(color);
        if (isFilled()) {
            var oldFont = g.getFont();
            g.setFont(new Font(fontName, fontstyle, fontSize));
            g.drawString(filled, startPoint.x, startPoint.y);
            g.setFont(oldFont);
        } else {
            g.setStroke(new MyStroke(1.5f));
            g.drawLine((int) startPoint.x, (int) startPoint.y, (int) startPoint.x, (int) startPoint.y - fontSize);
        }
    }

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