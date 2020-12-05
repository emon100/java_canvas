import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * 设置文字属性的对话框类
 * @author 王一蒙
 */
public class TextSettingPanel extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 7484077858050896589L;

    PaintSurface outerComponent = null;
    TextBox textBox = null;

    final Dimension preferredSize = new Dimension(200, 200);

    String fontName = "SansSerif"; // 字体名
    int fontStyle = Font.BOLD; // 字形
    int fontSize = 14; // 字号 

    public TextSettingPanel(PaintSurface outer, TextBox t) {
        if (outer == null || t == null) {
            throw new NullPointerException("There should have an outerComponent");
        }
        outerComponent = outer;
        textBox = t;
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }


    /**
     * 编辑对话框弹出的函数
     */
    public void showTextBoxDialog() {
        textBox.setFont(new Font(fontName,fontStyle,fontSize));
        textBox.setText("Input Here");

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
                textBox.setFont(new Font(fontName,fontStyle,fontSize));
                outerComponent.repaint();
            } catch (NumberFormatException nfe) {
            }
        });

        styles.addItemListener(i -> {
            try {
                fontStyle = styles.getSelectedIndex();
                textBox.setFont(new Font(fontName,fontStyle,fontSize));
                outerComponent.repaint();
            } catch (NumberFormatException nfe) {
            }
        });

        sizes.addChangeListener(c -> {
            try {
                String size = sizes.getModel().getValue().toString();
                fontSize = Integer.parseInt(size);
                textBox.setFont(new Font(fontName,fontStyle,fontSize));
                outerComponent.repaint();
            } catch (NumberFormatException nfe) {
                fontSize = 14;
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
                textBox.setText(input.getText());
                outerComponent.repaint();
            }
        });

        inputpanel.setLayout(new FlowLayout());
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            dialog.dispose();
        });
        inputpanel.add(input);
        inputpanel.add(enterButton);

        input.setText("Input Here");
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.add(fontSelectorPanel);
        root.add(inputpanel);

        dialog.add(root);
        dialog.setVisible(true);
    }

}
