import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Chrome是整个画板对象，在整个程序运行到程序关闭的过程中
 * 有且仅有一个Chrome对象。
 * @author AlbertTan
 */
public class Chrome extends JFrame {

    /**状态集合*/
    private States states;

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
        setSize(500,500);
        setMinimumSize( new Dimension(650,650) );

        //设置右上角退出操作
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (states.hasBeenSaved) {
                    System.exit(0);
                }
                Object[] options = {"保存", "不保存", "取消"};
                int res = JOptionPane.showOptionDialog(null,"文件未保存，是否保存","文件未保存",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                switch (res) {
                    case 0:
                        //保存
                        //设置默认打开目录为当前项目文件夹下
                        JFileChooser jFileChooser = new JFileChooser( System.getProperty("user.dir") + "//src//serializedFiles" );
                        jFileChooser.showSaveDialog(null);
                        File savePath = jFileChooser.getSelectedFile();
                        //如果没有选定文件或者文件不存在
                        if (savePath == null)  {
                            return;
                        }
                        if (!savePath.exists()){
                            try {
                                if (!savePath.createNewFile() ) {
                                    JOptionPane.showMessageDialog(null, "新建文件失败", "错误",JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                                return;
                            }
                        }
                        try (
                            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))
                        ){
                            out.writeObject( states.getAllDrawable() );
                            System.exit(0);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case 1:
                        //不保存，退出程序
                        System.exit(0);
                    case 2:
                        //取消
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });


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
        //打开文件
        JButton openFileBtn = new JButton("打开");
        openFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!states.hasBeenSaved) {
                    Object[] options = {"保存", "不保存", "取消"};
                    int res = JOptionPane.showOptionDialog(null,"文件未保存，是否保存","文件未保存",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                    switch (res) {
                        case 0:
                            //保存
                            //设置默认打开目录为当前项目文件夹下
                            JFileChooser jFileChooser = new JFileChooser( System.getProperty("user.dir") + "//src//serializedFiles" );
                            jFileChooser.showSaveDialog(null);
                            File savePath = jFileChooser.getSelectedFile();
                            //如果没有选定文件或者文件不存在
                            if (savePath == null)  {
                                return;
                            }
                            if (!savePath.exists()){
                                try {
                                    if (!savePath.createNewFile() ) {
                                        JOptionPane.showMessageDialog(null, "新建文件失败", "错误",JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                    return;
                                }
                            }
                            try (
                                var outputStream = new FileOutputStream(savePath);
                                ObjectOutputStream out = new ObjectOutputStream(outputStream)
                            ){
                                out.writeObject( states.getAllDrawable() );
                                states.hasBeenSaved = true;
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case 1:
                            //不保存，退出程序
                            break;
                        case 2:
                            //取消
                            break;
                        default:
                            break;
                    }
                }
                JFileChooser jFileChooser = new JFileChooser( System.getProperty("user.dir") + "//src//serializedFiles" );
                jFileChooser.showDialog(null, "打开");
                File targetFile = jFileChooser.getSelectedFile();
                //如果没有选定文件或者文件不存在
                if (targetFile == null || !targetFile.exists()) {
                    JOptionPane.showMessageDialog(null, "文件未打开！", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try (
                    ObjectInputStream out = new ObjectInputStream(new FileInputStream(targetFile))
                ) {
                    var readedObject = (ArrayList<Drawable>) out.readObject();
                    states.iniStates();
                    states.setDrawable(readedObject);
                    repaint();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        //新建文件
        JButton newFileBtn = new JButton("新建");
        newFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!states.hasBeenSaved) {
                    Object[] options = {"保存", "不保存", "取消"};
                    int res = JOptionPane.showOptionDialog(null,"文件未保存，是否保存","文件未保存",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                    switch (res) {
                        case 0:
                            //保存
                            //设置默认打开目录为当前项目文件夹下
                            JFileChooser jFileChooser = new JFileChooser( System.getProperty("user.dir") + "//src//serializedFiles" );
                            jFileChooser.showSaveDialog(null);
                            File savePath = jFileChooser.getSelectedFile();
                            //如果没有选定文件或者文件不存在
                            if (savePath == null)  {
                                return;
                            }
                            if (!savePath.exists()){
                                try {
                                    if (!savePath.createNewFile() ) {
                                        JOptionPane.showMessageDialog(null, "新建文件失败", "错误",JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                    return;
                                }
                            }
                            try (
                                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))
                            ){
                                out.writeObject( states.getAllDrawable() );
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case 1:
                            //不保存，退出程序
                            break;
                        case 2:
                            //取消
                            break;
                        default:
                            break;
                    }
                }
                states.iniStates();
                repaint();
            }
        });

        //保存
        JButton saveFileBtn = new JButton("保存");
        saveFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //设置默认打开目录为当前项目文件夹下
                JFileChooser jFileChooser = new JFileChooser( System.getProperty("user.dir") + "//src//serializedFiles" );
                jFileChooser.showSaveDialog(null);
                File savePath = jFileChooser.getSelectedFile();
                //如果没有选定文件或者文件不存在
                if (savePath == null)  {
                    return;
                }
                if (!savePath.exists()){
                    try {
                        if (!savePath.createNewFile() ) {
                            JOptionPane.showMessageDialog(null, "新建文件失败", "错误",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        return;
                    }
                }
                try (
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savePath))
                ) {
                    out.writeObject( states.getAllDrawable() );
                    states.hasBeenSaved = true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //撤销
        JLabel undoLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/undo.png")));
        undoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.undo();
                repaint();
            }
        });

        //重做
        JLabel redoLabel = new JLabel(new ImageIcon(this.getClass().getResource("images/redo.png")));
        redoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.redo();
                repaint();
            }
        });

        //字体粗细滑动条
        JLabel strokeLabel = new JLabel("画笔粗细");
        JSlider strokeSlider = new JSlider(1,5,1);
        // 设置主刻度间隔
        strokeSlider.setMajorTickSpacing(1);

        // 绘制 刻度 和 标签
        strokeSlider.setPaintTicks(true);
        strokeSlider.setPaintLabels(true);
        strokeSlider.addChangeListener(e -> states.setMyStroke(new MyStroke(strokeSlider.getValue()) ));

        /*
         * 工具栏图片对象
         */
        JLabel toolsLabel = new JLabel("工具");

        Dimension optionButtonDimension = new Dimension(26,26);
        //选择
        JButton chooseButton = new JButton(new ImageIcon(this.getClass().getResource("images/arrow.png")));
        chooseButton.setPreferredSize(optionButtonDimension);
        chooseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(States.TYPE.SELECT);
            }
        });
        //文本
        JButton textButton = new JButton(new ImageIcon(this.getClass().getResource("images/text.png")));
        textButton.setPreferredSize(optionButtonDimension);
        textButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.TEXTBOX);
            }
        });

        //擦除
        JButton eraserButton = new JButton(new ImageIcon(this.getClass().getResource("images/eraser.png")));
        eraserButton.setPreferredSize(optionButtonDimension);
        eraserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.ERASER);
            }
        });

        //填充
        JButton fillColorButton = new JButton(new ImageIcon(this.getClass().getResource("images/fillColor.png")));
        fillColorButton.setPreferredSize(optionButtonDimension);
        fillColorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(States.TYPE.FILL);
            }
        });

        //图形
        JLabel graphicLabel = new JLabel("图形");

        //笔
        JButton pencilButton = new JButton(new ImageIcon(this.getClass().getResource("images/pencil.png")));
        pencilButton.setPreferredSize(optionButtonDimension);
        pencilButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.PATH);
            }
        });

        //矩形
        JButton rectangleButton = new JButton(new ImageIcon(this.getClass().getResource("images/rectangle.png")));
        rectangleButton.setPreferredSize(optionButtonDimension);
        rectangleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.RECTANGLE);
            }
        });

        //三角形
        JButton triangleButton = new JButton(new ImageIcon(this.getClass().getResource("images/triangle.png")));
        triangleButton.setPreferredSize(optionButtonDimension);
        triangleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.TRIANGLE);
            }
        });

        //直线
        JButton lineButton = new JButton(new ImageIcon(this.getClass().getResource("images/line.png")));
        lineButton.setPreferredSize(optionButtonDimension);
        lineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.LINE);
            }
        });

        //椭圆
        JButton ellipseButton = new JButton(new ImageIcon(this.getClass().getResource("images/ellipse.png")));
        ellipseButton.setPreferredSize(optionButtonDimension);
        ellipseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                states.setType(StatesModel.TYPE.ELLIPSE);
            }
        });

        //颜色
        JLabel colorLabel = new JLabel("颜色");
        JPanel colorPanel = new JPanel();
        GridLayout colorGridLayout = new GridLayout(2,6);

        //设置行列间距
        colorGridLayout.setHgap(5);
        colorGridLayout.setVgap(3);
        colorPanel.setLayout(colorGridLayout);

        //显示当前颜色
        JLabel currentColorTextLabel = new JLabel("当前");
        JPanel currentColorPanel = new JPanel();
        currentColorPanel.setBackground( states.getColor() );

        //颜色选择器
        JLabel chooseColorTextLabel = new JLabel("其他");
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

        colorPanel.add(currentColorTextLabel);
        colorPanel.add(whiteColorPanel);
        colorPanel.add(blackColorPanel);
        colorPanel.add(redColorPanel);
        colorPanel.add(yellowColorPanel);
        colorPanel.add(chooseColorTextLabel);

        colorPanel.add(currentColorPanel);
        colorPanel.add(greenColorPanel);
        colorPanel.add(blueColorPanel);
        colorPanel.add(grayColorPanel);
        colorPanel.add(pinkColorPanel);
        colorPanel.add(chooseColorPanel);

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
        gridBagLayout.setConstraints(undoLabel, gridBagConstraints);

        //重做
        setConstrainSizeAndPos(gridBagConstraints,7,0,1,1);
        gridBagLayout.setConstraints(redoLabel, gridBagConstraints);

        //滑动条Panel
        setConstrainSizeAndPos(gridBagConstraints,8,0,1,1);
        gridBagLayout.setConstraints(strokeLabel, gridBagConstraints);

        //滑动条Slider
        setConstrainSizeAndPos(gridBagConstraints,9,0,3,1);
        gridBagLayout.setConstraints(strokeSlider, gridBagConstraints);

        //工具Label
        setConstrainSizeAndPos(gridBagConstraints,0,1,1,2);
        gridBagLayout.setConstraints(toolsLabel, gridBagConstraints);

        //选择
        setConstrainSizeAndPos(gridBagConstraints,1,1,1,1);
        gridBagLayout.setConstraints(chooseButton, gridBagConstraints);

        //文本
        setConstrainSizeAndPos(gridBagConstraints,2,1,1,1);
        gridBagLayout.setConstraints(textButton, gridBagConstraints);

        //擦除
        setConstrainSizeAndPos(gridBagConstraints,1,2,1,1);
        gridBagLayout.setConstraints(eraserButton, gridBagConstraints);

        //填充
        setConstrainSizeAndPos(gridBagConstraints,2,2,1,1);
        gridBagLayout.setConstraints(fillColorButton, gridBagConstraints);

        //图形Label
        setConstrainSizeAndPos(gridBagConstraints,3,1,1,2);
        gridBagLayout.setConstraints(graphicLabel, gridBagConstraints);

        //笔
        setConstrainSizeAndPos(gridBagConstraints,4,1,1,1);
        gridBagLayout.setConstraints(pencilButton, gridBagConstraints);

        //矩形
        setConstrainSizeAndPos(gridBagConstraints,5,1,1,1);
        gridBagLayout.setConstraints(rectangleButton, gridBagConstraints);

        //三角形
        setConstrainSizeAndPos(gridBagConstraints,6,1,1,1);
        gridBagLayout.setConstraints(triangleButton, gridBagConstraints);

        //直线
        setConstrainSizeAndPos(gridBagConstraints,4,2,1,1);
        gridBagLayout.setConstraints(lineButton, gridBagConstraints);

        //椭圆
        setConstrainSizeAndPos(gridBagConstraints,5,2,1,1);
        gridBagLayout.setConstraints(ellipseButton, gridBagConstraints);

        //颜色label
        setConstrainSizeAndPos(gridBagConstraints,7,1,1,2);
        gridBagLayout.setConstraints(colorLabel, gridBagConstraints);

        //颜色Panel
        setConstrainSizeAndPos(gridBagConstraints,8,1,6,2);
        gridBagLayout.setConstraints(colorPanel, gridBagConstraints);

        /*
         * 添加组件至Chrome
         * 必须从左到右，从上到下
         */

        //第一行
        add(openFileBtn);
        add(newFileBtn);
        add(saveFileBtn);
        add(undoLabel);
        add(redoLabel);
        add(strokeLabel);
        add(strokeSlider);

        // 第二、三行
        add(toolsLabel);
        add(chooseButton);
        add(textButton);
        add(eraserButton);
        add(fillColorButton);
        add(graphicLabel);
        add(pencilButton);
        add(rectangleButton);
        add(triangleButton);
        add(lineButton);
        add(ellipseButton);
        add(colorLabel);
        add(colorPanel);

        //填充颜色后方的空白
        add(new JPanel(), new GBC(14,1).setWeight(1, 0));
    }

    /** gridx,gridy:组件的左上角坐标;
     * gridWidth，gridHeight：组件占用的网格行数和列数\
     */
    public void setConstrainSizeAndPos (GridBagConstraints gridBagConstraints,int gridx, int gridy, int gridWidth, int gridHeight) {
        gridBagConstraints.gridx=gridx;
        gridBagConstraints.gridy=gridy;
        gridBagConstraints.gridwidth=gridWidth;
        gridBagConstraints.gridheight=gridHeight;
    }

    /**
     * 设置主题
     */
    public void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写GridBagConstraints类
     */
    public static class GBC extends GridBagConstraints {

        /**
         * 初始化左上角位置
         */
        public GBC(int gridx, int gridy) {
            this.gridx = gridx;
            this.gridy = gridy;
        }

        /**
         * 初始化左上角位置和所占行数和列数
         */
        public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
            this.gridx = gridx;
            this.gridy = gridy;
            this.gridwidth = gridwidth;
            this.gridheight = gridheight;
        }

        /**
         * 是否拉伸及拉伸方向
         */
        public GBC setFill(int fill) {
            this.fill = fill;
            return this;
        }

        /**
         * x和y方向上的增量
         */
        public GBC setWeight(double weightx, double weighty) {
            this.weightx = weightx;
            this.weighty = weighty;
            return this;
        }
    }

}
