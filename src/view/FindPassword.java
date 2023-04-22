package view;

import util.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FindPassword  extends JFrame {
    FlowLayout flowLayout=new FlowLayout(FlowLayout.CENTER);
    static final int WIDTH=250;
    static final int HEIGHT=200;
    JLabel text;
    JLabel phone;
    JLabel validCode;
    JTextField phoneText;
    JTextField validCodeText;
    JButton button;
    private final ValidCode code=new ValidCode();
    public FindPassword(){
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

    private void close() {
        this.dispose();
        new loginStart();
    }

    private void init() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        //获取屏幕的高度和宽度
        int width = dimension.width;
        int height = dimension.height;
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;
        //设置位置和窗口的大小
        this.setBounds(x, y, WIDTH, HEIGHT);
        this.setLayout(flowLayout);
        this.setTitle("找回密码");
        text=new JLabel("请输入你要找回密码的手机号");
        phone = new JLabel("手机号");
        validCode = new JLabel("验证码");
        code.setBounds(200,100,40,40);
        phoneText = new JTextField(15);
        validCodeText = new JTextField(15);
        button = new JButton("找回密码");
        button.addActionListener(e -> findPassword());

        this.add(phone);
        this.add(phoneText);
        this.add(validCode);
        this.add(validCodeText);
        this.add(code);
        this.add(button);
    }

    private void findPassword() {
        if("".equals(phoneText.getText())){
            JOptionPane.showMessageDialog(null,"手机号为空","提示消息",JOptionPane.WARNING_MESSAGE);
            code.nextCode();
            return;
        }
        if("".equals(code.getCode())){
            JOptionPane.showMessageDialog(null,"验证码为空","提示消息",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(isFlag()) {
            try {
                String password = SqlUtils.selectPassword(phoneText.getText());
                JOptionPane.showMessageDialog(null, "您的密码是：" + password, "提示消息", JOptionPane.WARNING_MESSAGE);
                new loginStart();
                this.dispose();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "找不到此手机号", "提示消息", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null,"验证码输入错误","提示消息",JOptionPane.WARNING_MESSAGE);
            code.nextCode();
        }

    }
    private boolean isFlag(){
        return code.getCode().equalsIgnoreCase(validCodeText.getText());
    }
}
