import javax.swing.*;
import java.awt.*;

/*
 * author: AlbertTan
 * date: 2020-11-25
 */
public class Chrome extends JFrame {

    /**
     * GBC
     */
    public static class GBC extends GridBagConstraints {
        //初始化左上角位置
        public GBC(int gridx, int gridy) {
            this.gridx = gridx;
            this.gridy = gridy;
        }

        //初始化左上角位置和所占行数和列数
        public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
            this.gridx = gridx;
            this.gridy = gridy;
            this.gridwidth = gridwidth;
            this.gridheight = gridheight;
        }

        //对齐方式
        public GBC setAnchor(int anchor) {
            this.anchor = anchor;
            return this;
        }

        //是否拉伸及拉伸方向
        public GBC setFill(int fill) {
            this.fill = fill;
            return this;
        }

        //x和y方向上的增量
        public GBC setWeight(double weightx, double weighty) {
            this.weightx = weightx;
            this.weighty = weighty;
            return this;
        }

        //外部填充
        public GBC setInsets(int distance) {
            this.insets = new Insets(distance, distance, distance, distance);
            return this;
        }

        //外填充
        public GBC setInsets(int top, int left, int bottom, int right) {
            this.insets = new Insets(top, left, bottom, right);
            return this;
        }

        //内填充
        public GBC setIpad(int ipadx, int ipady) {
            this.ipadx = ipadx;
            this.ipady = ipady;
            return this;
        }
    }


    /**
     * 构造函数 生成一个完成的Chrome
     */
    public Chrome() {

        //设置标题
        super("ACE26画图");


        //设置主题
        setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        //设置窗口大小
        setSize(600,600);

        //设置右上角退出操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*
         * 对窗体进行布局
         */
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);                           //窗体对象设置为GridBagLayout布局
        //addGridBagPanes();
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.fill=GridBagConstraints.BOTH;    //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况


        /*
         * 文件保存、撤销与重做图片对象
         */
        //保存文件

        //JLabel fileLabel = new JLabel("文件");

        //打开文件
        JButton openFileBtn = new JButton("打开");
        //新建文件
        JButton newFileBtn = new JButton("新建");
        //保存
        JButton saveFileBtn = new JButton("保存");

//        ImageIcon saveImg = new ImageIcon(this.getClass().getResource("images/save.png"));
//        JLabel saveImgLabel = new JLabel(saveImg);
//        JButton saveBtn = new JButton("保存");

        //撤销
        ImageIcon undoImg = new ImageIcon(this.getClass().getResource("images/undo.png"));
        JLabel undoImgLabel = new JLabel(undoImg);

        //重做
        ImageIcon redoImg = new ImageIcon(this.getClass().getResource("images/redo.png"));
        JLabel redoImgLabel = new JLabel(redoImg);

        /*
         * 工具栏图片对象
         */
        JLabel toolsLabel = new JLabel("工具");

        //选择
        ImageIcon chooseImg = new ImageIcon(this.getClass().getResource("images/arrow.png"));
        JLabel chooseImgLabel = new JLabel(chooseImg);

        //文本
        ImageIcon textImg = new ImageIcon(this.getClass().getResource("images/text.png"));
        JLabel textImgLabel = new JLabel(textImg);

        //擦除
        ImageIcon eraserImg = new ImageIcon(this.getClass().getResource("images/eraser.png"));
        JLabel eraserImgLabel = new JLabel(eraserImg);

        //填充
        ImageIcon fillColorImg = new ImageIcon(this.getClass().getResource("images/fillColor.png"));
        JLabel fillColorImgLabel = new JLabel(fillColorImg);

        //图形
        JLabel graphicLabel = new JLabel("图形");

        //笔
        JLabel pencilImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/pencil.png")));

        //矩形
        JLabel rectangleImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/rectangle.png")));

        //三角形
        JLabel triangleImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/triangle.png")));

        //直线
        JLabel lineImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/line.png")));

        //椭圆
        JLabel ellipseImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/ellipse.png")));


        //颜色
        JLabel colorLabel = new JLabel("颜色");
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.BLUE);
        //TODO:xx
        //画板
        JPanel canvasPanel = new JPanel();
        JFrame canvasFrame = new JFrame();
        canvasPanel.setBackground(Color.CYAN);

        //清除
        JButton clearBtn = new JButton("清除");

        //从左上角开始分布
        //gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;

        /*
         * 设置constraints
         */

        //打开文件
        setConstrainSizeAndPos(gridBagConstraints,0,0,2,1);
        gridBagLayout.setConstraints(openFileBtn, gridBagConstraints);

        //新建文件
        setConstrainSizeAndPos(gridBagConstraints,2,0,2,1);
        gridBagLayout.setConstraints(newFileBtn, gridBagConstraints);

        //保存文件
        setConstrainSizeAndPos(gridBagConstraints,4,0,2,1);
        gridBagLayout.setConstraints(saveFileBtn, gridBagConstraints);

        //撤销
        setConstrainSizeAndPos(gridBagConstraints,6,0,1,1);
        gridBagLayout.setConstraints(undoImgLabel, gridBagConstraints);

        //重做
        setConstrainSizeAndPos(gridBagConstraints,7,0,1,1);
        gridBagLayout.setConstraints(redoImgLabel, gridBagConstraints);

        //工具Label
        setConstrainSizeAndPos(gridBagConstraints,0,1,1,2);
        gridBagLayout.setConstraints(toolsLabel, gridBagConstraints);

        //选择
        setConstrainSizeAndPos(gridBagConstraints,1,1,1,1);
        gridBagLayout.setConstraints(chooseImgLabel, gridBagConstraints);

        //文本
        setConstrainSizeAndPos(gridBagConstraints,2,1,1,1);
        gridBagLayout.setConstraints(textImgLabel, gridBagConstraints);

        //擦除
        setConstrainSizeAndPos(gridBagConstraints,1,2,1,1);
        gridBagLayout.setConstraints(eraserImgLabel, gridBagConstraints);

        //填充
        setConstrainSizeAndPos(gridBagConstraints,2,2,1,1);
        gridBagLayout.setConstraints(fillColorImgLabel, gridBagConstraints);

        //图形Label
        setConstrainSizeAndPos(gridBagConstraints,3,1,1,2);
        gridBagLayout.setConstraints(graphicLabel, gridBagConstraints);

        //笔
        setConstrainSizeAndPos(gridBagConstraints,4,1,1,1);
        gridBagLayout.setConstraints(pencilImgLabel, gridBagConstraints);

        //矩形
        setConstrainSizeAndPos(gridBagConstraints,5,1,1,1);
        gridBagLayout.setConstraints(rectangleImgLabel, gridBagConstraints);

        //三角形
        setConstrainSizeAndPos(gridBagConstraints,6,1,1,1);
        gridBagLayout.setConstraints(triangleImgLabel, gridBagConstraints);

        //直线
        setConstrainSizeAndPos(gridBagConstraints,4,2,1,1);
        gridBagLayout.setConstraints(lineImgLabel, gridBagConstraints);

        //椭圆
        setConstrainSizeAndPos(gridBagConstraints,5,2,1,1);
        gridBagLayout.setConstraints(ellipseImgLabel, gridBagConstraints);

        //颜色label
        setConstrainSizeAndPos(gridBagConstraints,7,1,1,2);
        gridBagLayout.setConstraints(colorLabel, gridBagConstraints);

        //颜色Panel
        setConstrainSizeAndPos(gridBagConstraints,8,1,4,2);
        gridBagLayout.setConstraints(colorPanel, gridBagConstraints);

        //画板Panel
        setConstrainSizeAndPos(gridBagConstraints,0,3,15,7);
        gridBagLayout.setConstraints(canvasPanel, gridBagConstraints);

        //清除
        setConstrainSizeAndPos(gridBagConstraints,15,10,1,1);
        gridBagLayout.setConstraints(clearBtn, gridBagConstraints);

        /*
         * 添加组件至Chrome
         * 必须从左到右，从上到下
         */

        //第一行
        add(openFileBtn);
        add(newFileBtn);
        add(saveFileBtn);
        add(undoImgLabel);
        add(redoImgLabel);

        // 第二、三行
        add(toolsLabel);
        add(chooseImgLabel);
        add(textImgLabel);
        add(eraserImgLabel);
        add(fillColorImgLabel);
        add(graphicLabel);
        add(pencilImgLabel);
        add(rectangleImgLabel);
        add(triangleImgLabel);
        add(lineImgLabel);
        add(ellipseImgLabel);
        add(colorLabel);
        add(colorPanel,new GBC(8,1,4,2).setFill(GBC.BOTH).setWeight(50, 0));

    }

    //gridx,gridy:组件的左上角坐标;gridWidth，gridHeight：组件占用的网格行数和列数
    public void setConstrainSizeAndPos (GridBagConstraints gridBagConstraints,int gridx, int gridy, int gridWidth, int gridHeight) {
        gridBagConstraints.gridx=gridx;
        gridBagConstraints.gridy=gridy;
        gridBagConstraints.gridwidth=gridWidth;
        gridBagConstraints.gridheight=gridHeight;
    }


    //设置主题
    public void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
