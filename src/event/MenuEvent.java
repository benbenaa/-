package event;

import bean.User;
import util.SqlUtils;
import view.ChangeAccount;
import view.Manage;
import view.loginStart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuEvent implements ActionListener {
    JMenuItem item;
    @Override
    public void actionPerformed(ActionEvent e) {
        item = (JMenuItem) e.getSource();
        List<User> users = SqlUtils.select();
        StringBuilder str = new StringBuilder("姓名\t手机\t账号\t状态\n");


        switch (item.getName()){

            case "item1": if("admin".equals(Manage.text)) {
                for (User user : users) {
                    if (user.getStart() == 1) {
                        str.append(user.toString());
                    }
                }
                Manage.resultText.setText(str.toString());
            }else {
                JOptionPane.showMessageDialog(null,"你不是管理员无法查看","提示消息",JOptionPane.WARNING_MESSAGE);
            }
            break;

            case "item2":
                if("admin".equals(Manage.text)) {
                    for (User user : users) {
                        str.append(user.toString());
                    }
                    Manage.resultText.setText(str.toString());
                }else{
                    JOptionPane.showMessageDialog(null,"你不是管理员无法查看","提示消息",JOptionPane.WARNING_MESSAGE);
                }
            break;

            case "item3":
                if("admin".equals(Manage.text)) {
                    new ChangeAccount();
                }else{
                    JOptionPane.showMessageDialog(null,"你不是管理员无法修改","提示信息",JOptionPane.WARNING_MESSAGE);
                }
                break;

            case "item_1":
                SqlUtils.setStart(Manage.text,0);
                loginStart.closeManege();
                break;
            default:
        }
    }
}
