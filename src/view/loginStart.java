package view;

import bean.User;
import util.style;
import util.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class loginStart extends JFrame {
    FlowLayout flowLayout;//定义一个布局居中
    FlowLayout flowLayout1;//定义一个布局右对齐
    JLabel background;//背景
    JLabel account;//账号
    JLabel register;//注册
    JLabel find;//注册
    JLabel password;//密码
    JLabel title;//标题
    public static JTextField accountText;//输入账号
    JPasswordField passwordText;//输入密码
    JButton ok;//登录按钮
    //窗口常量
    static final int WIDTH = 800;
    static final int HEIGHT = 450;
    //定义一个箱子
    JPanel jPanel_1;//放图片和其他的组件
    JPanel jPanel_2;//只放标题
    JPanel jPanel_3;//只放账号密码
    static Manage m;

    public loginStart() {
        init();
        setVisible(true);//设置窗口可否显示
        setResizable(false);//设置窗口是否可变
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口默认的关闭方式
        validate();//让组件生效
    }

    public loginStart(String str) {
        init();
        setVisible(true);//设置窗口可否显示
        setResizable(false);//设置窗口是否可变
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口默认的关闭方式
        validate();//让组件生效
        accountText.setText(str);
    }


    private void init() {
        flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout1 = new FlowLayout(FlowLayout.RIGHT);
        jPanel_1 = new JPanel();//盘子初始化
        jPanel_1.setBounds(800, 500, WIDTH, HEIGHT);
        jPanel_1.setLayout(null);//设置布局为空
        jPanel_2 = new JPanel();
        jPanel_2.setBounds(0, 70, WIDTH, 50);
        jPanel_2.setLayout(flowLayout);
        jPanel_2.setOpaque(false);
        jPanel_3 = new JPanel();
        jPanel_3.setBounds(230, 125, 300, 300);
        jPanel_3.setLayout(flowLayout1);
        jPanel_3.setOpaque(false);//将当前的盘子设置成透明
        this.setTitle("学生信息管理系统");


        //设置当前窗口的大小
        //this.setBounds();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        //获取屏幕的高度和宽度
        int width = dimension.width;
        int height = dimension.height;
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;
        //设置位置和窗口的大小
        this.setBounds(x, y, WIDTH, HEIGHT);
        //设置背景图片
        ImageIcon img = new ImageIcon("E:\\IDEA\\ideafiles\\graduation\\src\\img\\1.jpg");
        background = new JLabel(img);
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

        title = new JLabel("学生信息管理系统");
        title.setFont(style.title_font);
        title.setForeground(Color.cyan);
        //添加账号和密码的标签
        account = new JLabel("账号 ");
        account.setFont(style.account_font);
        account.setForeground(new Color(249, 247, 247));
        accountText = new JTextField(15);
        accountText.setFont(style.password_font);

        password = new JLabel("密码 ");
        password.setFont(style.account_font);
        password.setForeground(new Color(249, 249, 247));
        passwordText = new JPasswordField(15);
        passwordText.setFont(style.password_font);


        ok = new JButton("安全登录");
        ok.setPreferredSize(new Dimension(280, 30));
        ok.setFont(style.ok_font);
        ok.setBackground(new Color(8, 189, 252));
        ok.setForeground(new Color(0, 35, 254));
        ok.addActionListener(e -> login());//添加按钮登录事件
        passwordText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    login();//按下enter键触发登录事件
                }
            }
        });
        register = new JLabel("注册账号");
        register.setBounds(10, 380, 100, 40);
        register.setForeground(new Color(249, 249, 247));
        register.setFont(style.register_font);
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    drop("register");//开启注册界面
                }
            }
        });
        find = new JLabel("忘记密码");
        find.setBounds(700, 380, 100, 40);
        find.setForeground(new Color(249, 249, 247));
        find.setFont(style.register_font);
        find.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    drop("find");//开启注册界面
                }
            }
        });
        jPanel_1.add(register);
        jPanel_1.add(find);
        jPanel_3.add(account);
        jPanel_3.add(accountText);
        jPanel_3.add(password);
        jPanel_3.add(passwordText);
        jPanel_3.add(ok);
        jPanel_2.add(title);
        jPanel_1.add(jPanel_2);
        jPanel_1.add(jPanel_3);
        jPanel_1.add(background);
        this.add(jPanel_1);

    }

    /**
     * @param str login创建登录界面,register创建注册界面
     */
    private void drop(String str) {
        switch (str){
            case "register":
                new Register();
                dispose();
                break;
            case "login":
                    SqlUtils.setStart(accountText.getText(), 1);
                    m = new Manage(accountText.getText());
                    this.dispose();
                break;
            case "find":
                new FindPassword();
                dispose();
                break;
            default:dispose();
        }
    }
    public static void closeManege() {
        m.dispose();
    }

    private void login() {
        User user = new User();
        String account = accountText.getText();
        String password = new String(passwordText.getPassword());
        user.setAccount(account);
        user.setPassword(password);
        if (SqlUtils.login(user)) {
            drop("login");//当前窗口销毁，开启登录窗口
        } else {
            if (accountText.getText().equals("") && "".equals(passwordText.getText())) {
                JOptionPane.showMessageDialog(null, "请输入账号和密码", "登录消息", JOptionPane.WARNING_MESSAGE);
            } else if ("".equals(passwordText.getText()) && !accountText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "密码为空", "登录消息", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "账号或者密码错误", "登录消息", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
