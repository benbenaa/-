package view;

import util.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeOne extends JFrame {
    FlowLayout flowLayout=new FlowLayout(FlowLayout.CENTER);
    static final int WIDTH=300;
    static final int HEIGHT=80;
    JLabel password;
    JPasswordField passwordText;
    JButton button;
    private final String account;
    public ChangeOne(String str){
        account=str;
        init();
        setVisible(true);//设置窗口可否显示
        setResizable(false);//设置窗口是否可变
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置窗口默认的关闭方式
        validate();//让组件生效
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
        this.setTitle("修改当前账户的密码");
        //点击X关闭把当前账户设置为离线
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SqlUtils.setStart(account,0);
            }
        });
        password = new JLabel("密码");
        passwordText = new JPasswordField(15);
        button = new JButton("修改密码");
        button.addActionListener(e -> updatePassword());


        this.add(password);
        this.add(passwordText);
        this.add(button);
    }

    private void updatePassword() {
        boolean update = SqlUtils.updateOne(new String (passwordText.getPassword()), account);
        if(update){
            JOptionPane.showMessageDialog(null, "修改成功", "消息", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            SqlUtils.setStart(account,0);
            loginStart.closeManege();
            new loginStart();
        }else {
            JOptionPane.showMessageDialog(null, "修改失败", "消息", JOptionPane.WARNING_MESSAGE);
        }
    }
}
