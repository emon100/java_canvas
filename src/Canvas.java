import java.awt.*;
import javax.swing.*;

public class Canvas extends JFrame {

    /**
     *测试用主窗口
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new Canvas();
    }

    public Canvas() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
    }
}