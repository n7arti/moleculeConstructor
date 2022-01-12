package frame.sub;

import model.Atom;

import javax.swing.*;
import java.awt.*;

/**
 * Кнопка для обработки нажатий с каким-либо атомом
 */
public class Element extends JPanel {
    protected boolean isClick;

    public Element(Atom component, String name) {
        isClick = false;
        setLayout(null);
        setBackground(new Color(135, 206, 250));
        setSize(200, 100);
        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(105, 105, 105)));
        setPreferredSize(new Dimension(240, 106));
        if (component.getSizeE() == 10)
            component.setSize(2 * component.getSizeE() + 1, 4 * component.getSizeE() + 1);
        else
            component.setSize(component.getSizeE() + 1, component.getSizeE() + 1);
        add(component);
        JLabel label = new JLabel(name);
        label.setLocation(getX() + getSize().width - 180, getY());
        label.setSize(138, 112);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
        revalidate();
        repaint();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.repaint(); // clear component //
        if (isClick)
            setBackground(new Color(173, 177, 184));
        else setBackground(new Color(135, 206, 250));
    }
}
