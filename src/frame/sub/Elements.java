package frame.sub;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс, содержащий и определяющий все кнопки на панели инструментов.
 * Не обрабатывает нажатия, а хранит информацию о нажатой кнопке.
 */
public class Elements extends JPanel {
    protected Element H;
    protected Element C;
    protected Element N;
    protected Element O;
    protected Element R;

    public Elements() {

        setLayout(new FlowLayout());
        setBackground(new Color(30, 144, 255));
        setSize(250, 560);
        setPreferredSize(new Dimension(236, 560));
        H = new Element(new Hydrogen(240/2, 106 / 2 - 5), "H");
        H.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                H.isClick = !H.isClick;
                if (H.isClick) {
                    C.isClick = false;
                    N.isClick = false;
                    O.isClick = false;
                    R.isClick = false;
                }
            }
        });
        C = new Element(new Carbon(240/2, 106 / 2 - 7), "C");
        C.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                C.isClick = !C.isClick;
                if (C.isClick) {
                    H.isClick = false;
                    N.isClick = false;
                    O.isClick = false;
                    R.isClick = false;
                }
            }
        });
        N = new Element(new Nitrogen(240/2, 106 / 2 - 10), "N");
        N.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                N.isClick = !N.isClick;
                if (N.isClick) {
                    C.isClick = false;
                    H.isClick = false;
                    O.isClick = false;
                    R.isClick = false;
                }
            }
        });
        O = new Element(new Oxygen(240/2, 106 / 2 - 14), "O");
        O.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                O.isClick = !O.isClick;
                if (O.isClick) {
                    C.isClick = false;
                    N.isClick = false;
                    H.isClick = false;
                    R.isClick = false;
                }
            }
        });
        R = new Element(new Radical(240/2 - 20, 106 / 2 - 10), "");
        R.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                R.isClick = !R.isClick;
                if (R.isClick) {
                    C.isClick = false;
                    N.isClick = false;
                    H.isClick = false;
                    O.isClick = false;
                }
            }
        });
        add(H);
        add(C);
        add(N);
        add(O);
        add(R);
    }
}