import javax.swing.*;

public class Canvas{

    /**
     * 测试用主窗口
     */
    private static final long serialVersionUID = 1L;



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                States states = new States();
                Chrome chrome = new Chrome(states);
                chrome.add(new PaintSurface(states),new Chrome.GBC(0,3,15,7).setFill(Chrome.GBC.BOTH).setWeight(0,100));
                chrome.setVisible(true);
            });
    }

}