package frame.main;

import controller.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Класс панели в которую помещаются плавающие окна
 */
public class MainDesktopPane extends JDesktopPane implements KeyListener, MouseListener {

    public MainDesktopPane() {
        super();
        setLayout(new BorderLayout());
        addKeyListener(this);
        addMouseListener(this);
        this.getActionMap();
        this.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.KEY_PRESSED), "");
    }

    //Метод добавляет компонент на MainDesktopPane и с ключём String в App.Frames
    public Component add(Component c, String key) {
        Component lc;
        lc = super.add(c);
        if (!App.Frames.containsKey(key)) { //Добавляем в App.Frame только если там еще нет элемента с таким ключем
            App.Frames.put(key, c);
        }
        return lc;
    }

    public Component add(Component c) {
        Component lc;
        lc = super.add(c);
        if (!App.Frames.containsKey(App.PPrefix + "/" + c.getName())) {
            //Добавляем в App.Frame только если там еще нет элемента с таким ключем
            App.Frames.put(App.PPrefix + "/" + c.getName(), c);
        }
        return lc;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("MainDesktopPane > " + e.getX() + " " + e.getY() + " " + e.getXOnScreen() + " " + e.getYOnScreen());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println("MainDesktopPane keyPressed > " + arg0.getKeyCode() + " " + arg0.getKeyText(arg0.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println("MainDesktopPane keyReleased > " + arg0.getKeyCode() + " " + arg0.getKeyText(arg0.getKeyCode()));
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        //System.out.println("MainDesktopPane keyTyped > "+arg0.getKeyCode()+" "+arg0.getKeyText(arg0.getKeyCode()));
    }

    Action someAction = new AbstractAction() {
        public void actionPerformed(KeyEvent e) {
            // TODO Auto-generated method stub
            //KeyEvent temp = (KeyEvent) e;
            System.out.println("MainDesktopPane А > " + e.getKeyCode() + " " + e.getKeyText(e.getKeyCode()));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

        }
    };

}
