package view;

import util.SqlUtils;
import util.style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropAccount extends JFrame {
    private final String account;
    static final int WIDTH=300;
    static final int HEIGHT=120;
    FlowLayout flowLayout=new FlowLayout(FlowLayout.CENTER);
    JButton jButton1,jButton2;//确定和取消按钮
    JLabel jLabel;//提示语
    public DropAccount(String str){
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
        this.setTitle("提示信息");
        jLabel=new JLabel("确定注销此账户吗？");
        jButton1=new JButton("确定");
        jButton2=new JButton("取消");
        jLabel.setFont(style.title_font);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drop(account);
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                close();
            }
        });

        this.add(jLabel);
        this.add(jButton1);
        this.add(jButton2);
    }
    private void close(){
        this.dispose();
    }
    private void drop(String str){
        boolean delete = SqlUtils.delete(str);
        if(delete) {
            JOptionPane.showMessageDialog(null, "注销成功", "提示消息", JOptionPane.WARNING_MESSAGE);
            close();
            loginStart.closeManege();
            new loginStart();
        }else{
            JOptionPane.showMessageDialog(null, "查无此人", "提示消息", JOptionPane.WARNING_MESSAGE);
        }
    }
}
