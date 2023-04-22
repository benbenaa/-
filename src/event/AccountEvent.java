package event;

import view.Register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountEvent implements ActionListener {
    public AccountEvent() {
    }
    JTextField use;
    @Override
    public void actionPerformed(ActionEvent e) {
        use= (JTextField) e.getSource();
        switch (use.getName()){
            case "nameText":
                Register.phoneText.requestFocus();
                break;
            case "phoneText":
                Register.accountText.requestFocus();
                break;
            case "accountText":
                Register.passwordText.requestFocus();
                break;
            case "passwordText":
                Register.passwordTwoText.requestFocus();
                break;
            case "passwordTwoText":
                Register.reg.requestFocus();
                break;
            default:
        }
    }
}
