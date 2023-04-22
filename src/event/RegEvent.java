package event;

import view.Register;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RegEvent implements MouseListener {

    public RegEvent() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //鼠标左键按下才会触发事件
        if(e.getButton()==MouseEvent.BUTTON1) {
            new Register();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
