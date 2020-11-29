import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * author: AlbertTan
 * date: 2020-11-25
 */
public class Chrome extends JFrame {

    private States states;
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
    public Chrome(States states) {

        //设置标题
        super("ACE26画图");

        this.states = states;

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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.fill = GridBagConstraints.BOTH;    //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况

        /*
         * 文件保存、撤销与重做图片对象
         */
        //保存文件

        //JLabel fileLabel = new JLabel("文件");

        //打开文件
        JButton openFileBtn = new JButton("打开");
        openFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("openFileBtn clicked");
                //TODO: Open file button listener
            }
        });

        //新建文件
        JButton newFileBtn = new JButton("新建");
        newFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("newFileBtn clicked");
                //TODO: New file button listener
            }
        });

        //保存
        JButton saveFileBtn = new JButton("保存");
        saveFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("saveFileBtn clicked");
                //TODO: Save file button listener
            }
        });

        //撤销
        JLabel undoImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/undo.png")));
        undoImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("undo");
                // TODO: states.undo();
            }
        });

        //重做
        JLabel redoImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/redo.png")));
        redoImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("redo");
                // TODO: states.redo();
            }
        });

        /*
         * 工具栏图片对象
         */
        JLabel toolsLabel = new JLabel("工具");

        //选择
        JLabel chooseImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/arrow.png")));
        chooseImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(States.TYPE.SELECT);
                System.out.println("select");
            }
        });

        //文本
        JLabel textImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/text.png")));
        textImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.TEXTBOX);
                System.out.println("textBox");
            }
        });

        //擦除
        JLabel eraserImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/eraser.png")));
        eraserImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("eraser but didn't set TYPE");
            }
        });

        //填充
        JLabel fillColorImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/fillColor.png")));
        fillColorImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("fill but didn't set TYPE");
            }
        });

        //图形
        JLabel graphicLabel = new JLabel("图形");

        //笔
        JLabel pencilImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/pencil.png")));
        pencilImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.PATH);
                System.out.println("pencil -- PATH");
            }
        });

        //矩形
        JLabel rectangleImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/rectangle.png")));
        rectangleImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.RECTANGLE);
                System.out.println("rectangle");
            }
        });

        //三角形
        JLabel triangleImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/triangle.png")));
        triangleImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.TRIANGLE);
                System.out.println("triangle");
            }
        });

        //直线
        JLabel lineImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/line.png")));
        lineImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.LINE);
                System.out.println("line");
            }
        });

        //椭圆
        JLabel ellipseImgLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/ellipse.png")));
        ellipseImgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.ELLIPSE);
                System.out.println("ellipse");
            }
        });

        //颜色
        JLabel colorLabel = new JLabel("颜色");
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(2,4));
        //显示当前颜色
        JLabel currentColorTextLabel = new JLabel("当前");
        JPanel currentColorPanel = new JPanel();
        currentColorPanel.setBackground(Color.black);
        //颜色选择器
        JLabel chooseColorTextLabel = new JLabel("任意");
        JPanel chooseColorPanel = new JPanel();
        chooseColorPanel.setBackground(Color.black);
        chooseColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 显示颜色选取器对话框, 返回选取的颜色（线程将被阻塞, 直到对话框被关闭）
                Color color = JColorChooser.showDialog(chooseColorPanel, "选取颜色", null);

                // 如果用户取消或关闭窗口, 则返回的 color 为 null
                if (color == null) {
                    return;
                }

                // 把选取的颜色设置为标签的背景
                chooseColorPanel.setBackground(color);
                currentColorPanel.setBackground(color);
                //当前选取状态设置
                states.setColor(color);

            }
        });
        //TODO: Box too small: split into panels
        
        JPanel whiteColorPanel = new JPanel();
        whiteColorPanel.setBackground(Color.white);
        whiteColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.WHITE);
                currentColorPanel.setBackground(Color.WHITE);
            }
        });
        JPanel blackColorPanel = new JPanel();
        blackColorPanel.setBackground(Color.black);
        blackColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.black);
                currentColorPanel.setBackground(Color.black);
            }
        });
        JPanel redColorPanel = new JPanel();
        redColorPanel.setBackground(Color.red);
        redColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.red);
                currentColorPanel.setBackground(Color.red);
            }
        });
        JPanel yellowColorPanel = new JPanel();
        yellowColorPanel.setBackground(Color.yellow);
        yellowColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.yellow);
                currentColorPanel.setBackground(Color.yellow);
            }
        });
        JPanel greenColorPanel = new JPanel();
        greenColorPanel.setBackground(Color.green);
        greenColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.green);
                currentColorPanel.setBackground(Color.green);
            }
        });
        JPanel blueColorPanel = new JPanel();
        blueColorPanel.setBackground(Color.blue);
        blueColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.blue);
                currentColorPanel.setBackground(Color.blue);
            }
        });
        JPanel grayColorPanel = new JPanel();
        grayColorPanel.setBackground(Color.GRAY);
        grayColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.gray);
                currentColorPanel.setBackground(Color.gray);
            }
        });
        JPanel pinkColorPanel = new JPanel();
        pinkColorPanel.setBackground(Color.pink);
        pinkColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setColor(Color.pink);
                currentColorPanel.setBackground(Color.pink);
            }
        });

        colorPanel.add(whiteColorPanel);
        colorPanel.add(blackColorPanel);
        colorPanel.add(redColorPanel);
        colorPanel.add(yellowColorPanel);
        colorPanel.add(greenColorPanel);
        colorPanel.add(blueColorPanel);
        colorPanel.add(grayColorPanel);
        colorPanel.add(pinkColorPanel);
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
        setConstrainSizeAndPos(gridBagConstraints,9,1,4,2);
        gridBagLayout.setConstraints(colorPanel, gridBagConstraints);

        //当前颜色Label
        setConstrainSizeAndPos(gridBagConstraints,8,1,1,1);
        gridBagLayout.setConstraints(currentColorTextLabel, gridBagConstraints);
        setConstrainSizeAndPos(gridBagConstraints,8,2,1,1);
        gridBagLayout.setConstraints(currentColorPanel, gridBagConstraints);

        //选择颜色Label
        setConstrainSizeAndPos(gridBagConstraints,13,1,1,1);
        gridBagLayout.setConstraints(chooseColorTextLabel, gridBagConstraints);
        setConstrainSizeAndPos(gridBagConstraints,13,2,1,1);
        gridBagLayout.setConstraints(chooseColorPanel, gridBagConstraints);

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
        add(currentColorTextLabel);
        add(currentColorPanel);
        add(colorLabel);
        add(colorPanel);
        add(chooseColorTextLabel);
        add(chooseColorPanel);

        //填充颜色后方的空白
        add(new JPanel(), new GBC(14,1).setWeight(1, 0));
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
