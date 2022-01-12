package frame.main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Ставит настройки главного окна
 * Ставит название, иконку, размер
 */

public class MainFrame extends JFrame implements MouseListener {

    public MainFrame() {
        super();
        setTitle("Molecul Constructor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        ImageIcon icon = new ImageIcon("icon.png");
        setLocationRelativeTo(null);
        setIconImage(icon.getImage());
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        System.out.println("MainFrame > "+e.getX()+" "+e.getY()+" "+e.getXOnScreen()+" "+e.getYOnScreen());
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





}
