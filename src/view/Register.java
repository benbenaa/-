package view;

import bean.User;
import event.AccountEvent;
import util.style;
import util.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Register extends JFrame {
    private static final int WIDTH=410;
    private static final int HEIGHT=380;
    FlowLayout flowLayout;
    JLabel background;
    JLabel title;
    JLabel name;//名字
    public static JTextField nameText;//名字文本框
    JLabel account;
    public static JTextField accountText;
    JLabel password;//密码
    public static JPasswordField passwordText;
    JLabel passwordTwo;//确认密码
    public static JPasswordField passwordTwoText;
    JLabel phone;//手机号
    public static JTextField phoneText;//手机号输入框
    public static JButton reg;//注册按钮

    JPanel jPanel_1;
    JPanel jPanel_2;
    JPanel jPanel_3;
    ActionListener listener_1;


    ImageIcon img=new ImageIcon("E:\\IDEA\\ideafiles\\graduation\\src\\img\\2.jpg");
    public Register()  {
        init();
        setVisible(true);//设置窗口可否显示
        setResizable(false);//设置窗口是否可变
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置窗口默认的关闭方式
        validate();//让组件生效
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

    }

    private void init() {
         flowLayout=new FlowLayout(FlowLayout.CENTER);
        //
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension dimension=kit.getScreenSize();
        //获取屏幕的高度和宽度
        int width=dimension.width;
        int height=dimension.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        //设置位置和窗口的大小
        this.setBounds(x,y,WIDTH,HEIGHT);
        jPanel_1=new JPanel();
        jPanel_1.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        jPanel_1.setLayout(null);
        //设置背景图片

        background=new JLabel(img);
        background.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        title=new JLabel("信息注册");
        title.setFont(style.title_font);
        title.setForeground(new Color(102,102,102));
        jPanel_2=new JPanel();
        jPanel_2.setBounds(0,20,WIDTH-10,70);
        jPanel_2.setOpaque(false);
        jPanel_3=new JPanel();
        jPanel_3.setLayout(flowLayout);
        jPanel_3.setBounds(50,70,300,250);
        jPanel_3.setOpaque(false);
        //第一行数据初始化
        name=new JLabel("姓    名:");
        nameText=new JTextField(15);
        name.setFont(style.account_font);
        name.setForeground(new Color(102,102,102));
        nameText.setPreferredSize(new Dimension(15,28));
        nameText.setForeground(new Color(18,120,192));
        phone=new JLabel("手    机:");
        phoneText=new JTextField(15);
        phone.setFont(style.account_font);
        phone.setForeground(new Color(102,102,102));
        phoneText.setPreferredSize(new Dimension(15,28));
        phoneText.setForeground(new Color(18,120,192));
        //nameSatisfy.setPreferredSize(new Dimension(100,20));

        //第二行
        account=new JLabel("账    号:");
        accountText=new JTextField(15);
        account.setFont(style.account_font);
        account.setForeground(new Color(102,102,102));
        accountText.setForeground(new Color(18,120,192));
        accountText.setPreferredSize(new Dimension(15,28));

        //第三行
        password=new JLabel("密    码:");
        passwordText=new JPasswordField(15);
        password.setFont(style.account_font);
        password.setForeground(new Color(102,102,102));
        passwordText.setForeground(new Color(18,120,192));
        passwordText.setPreferredSize(new Dimension(15,28));

        //第四行
        passwordTwo=new JLabel("确认密码:");
        passwordTwoText=new JPasswordField(15);
        passwordTwo.setFont(style.account_font);
        passwordTwo.setForeground(new Color(102,102,102));
        passwordTwoText.setForeground(new Color(18,120,192));
        passwordTwoText.setPreferredSize(new Dimension(15,28));
        passwordTwoText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    ButtonEvent();
                }
            }
        });
        //第五行
        reg=new JButton("注          册");
        reg.setPreferredSize(new Dimension(240,30));
        reg.setFont(style.register_font);
        reg.setForeground(new Color(102, 102, 102));
        //reg.setBackground(new Color(255, 255, 255, 255));

        jPanel_3.add(name);
        jPanel_3.add(nameText);
        jPanel_3.add(account);
        jPanel_3.add(accountText);
        jPanel_3.add(phone);
        jPanel_3.add(phoneText);
        jPanel_3.add(password);
        jPanel_3.add(passwordText);
        jPanel_3.add(passwordTwo);
        jPanel_3.add(passwordTwoText);
        jPanel_3.add(reg);
        jPanel_2.add(title);
        jPanel_3.setBorder(BorderFactory.createTitledBorder("基本功能界面"));
        jPanel_1.add(jPanel_3);
        jPanel_1.add(jPanel_2);
        jPanel_1.add(background);
        this.add(jPanel_1);
        setNameText();
        allEvent();
    }
    public void allEvent(){
        listener_1=new AccountEvent();
        nameText.addActionListener(listener_1);
        phoneText.addActionListener(listener_1);
        accountText.addActionListener(listener_1);
        passwordText.addActionListener(listener_1);
        reg.addActionListener(e -> ButtonEvent());
    }

    public void setNameText(){
        nameText.setName("nameText");
        phoneText.setName("phoneText");
        accountText.setName("accountText");
        passwordText.setName("passwordText");
        passwordTwoText.setName("passwordTwoText");
        reg.setName("reg");
    }
    private void drop(){
        this.dispose();
        new loginStart(accountText.getText());
    }
    private void close(){
        this.dispose();
        new loginStart();
    }
    private void ButtonEvent(){
        String name=nameText.getText();
        String account=accountText.getText();
        String phone=phoneText.getText();
        char[] str = passwordText.getPassword();
        String password=new String(str);
        char[] str2 = passwordTwoText.getPassword();
        String passwordTwo=new String(str2);
        if("".equals(name)){
            JOptionPane.showMessageDialog(null,"姓名不能为空","注册消息",JOptionPane.WARNING_MESSAGE);
        }else if("".equals(phone)){
            JOptionPane.showMessageDialog(null,"手机号不能为空","注册消息",JOptionPane.WARNING_MESSAGE);
        }else if("".equals(account)){
            JOptionPane.showMessageDialog(null,"账号不能为空","注册消息",JOptionPane.WARNING_MESSAGE);
        }else if("".equals(Register.passwordText.getText())){
            JOptionPane.showMessageDialog(null,"密码不能为空","注册消息",JOptionPane.WARNING_MESSAGE);
        }else if("".equals(Register.passwordTwoText.getText())){
            JOptionPane.showMessageDialog(null,"确认密码不能为空","注册消息",JOptionPane.WARNING_MESSAGE);
        }else{
            if(password.equals(passwordTwo)){
                User user=new User(name,account,password,phone,0);
                SqlUtils.createDb("Manger");
                SqlUtils.createProcedure("student");
                boolean insert = SqlUtils.insert(user);
                if(insert) {
                    JOptionPane.showMessageDialog(null, "注册成功", "注册消息", JOptionPane.WARNING_MESSAGE);
                    drop();
                }else{
                    JOptionPane.showMessageDialog(null, "注册失败", "注册消息", JOptionPane.WARNING_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"两次输入密码不一致","注册消息",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
