package view;

import bean.User;
import util.SqlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author MagicBook
 */
public class ChangeAccount extends JFrame {
    static final int WIDTH=300;
    static final int HEIGHT=180;
    FlowLayout flowLayout=new FlowLayout(FlowLayout.CENTER);
    JLabel name,account,password;
    JTextField nameText,accountText;
    JPasswordField passwordText;
    JPanel jPanel;
    JButton change;

    public ChangeAccount() {
        init();
        //设置窗口可否显示
        setVisible(true);
        //设置窗口是否可变
        setResizable(false);
        //设置窗口默认的关闭方式
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        validate();//让组件生效
    }

    private void init() {
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension dimension=kit.getScreenSize();
        //获取屏幕的高度和宽度
        int width=dimension.width;
        int height=dimension.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        //设置位置和窗口的大小
        this.setBounds(x,y,WIDTH,HEIGHT);
        this.setLayout(flowLayout);
        name=new JLabel("姓名");
        password=new JLabel("密码");
        account=new JLabel("账号");
        nameText=new JTextField(18);
        accountText=new JTextField(18);
        passwordText=new JPasswordField(18);
        change=new JButton("更改员工信息");
        jPanel=new JPanel();
        jPanel.setLayout(flowLayout);
        jPanel.setPreferredSize(new Dimension(230,150));
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        passwordText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    updateData();
                }
            }
        });
        this.setTitle("更改信息");
        jPanel.add(name);
        jPanel.add(nameText);
        jPanel.add(account);
        jPanel.add(accountText);
        jPanel.add(password);
        jPanel.add(passwordText);
        jPanel.add(change);
        this.add(jPanel);
    }
    private void updateData(){
        User user=new User();
        if("".equals(nameText.getText())&& "".equals(accountText.getText())&&passwordText.getText().equals("")){
            JOptionPane.showMessageDialog(null,"姓名,账号，密码不能为空","修改消息",JOptionPane.WARNING_MESSAGE);
            return;
        }else {
            if ("".equals(nameText.getText())) {
                JOptionPane.showMessageDialog(null, "姓名不能为空", "修改消息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if ("".equals(accountText.getText())) {
                JOptionPane.showMessageDialog(null, "账户不能为空", "修改消息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if ("".equals(passwordText.getText())) {
                JOptionPane.showMessageDialog(null, "密码不能为空", "修改消息", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        user.setName(nameText.getText());
        user.setPassword(new String(passwordText.getPassword()));
        user.setAccount(accountText.getText());
        boolean update = SqlUtils.update(user);
        if(update){
            JOptionPane.showMessageDialog(null,"修改成功","修改消息",JOptionPane.WARNING_MESSAGE);
            SqlUtils.setStart(accountText.getText(),0);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(null,"找不到此账户","修改消息",JOptionPane.WARNING_MESSAGE);
        }
    }
}
