package frame.sub;

import menu.FileAction;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Класс, отвечающий за работу с нарисованной молекулой
 *
 * @author Артамонова Анастасия
 */

public class MoleculDraw extends JPanel implements ActionListener, PropertyChangeListener {
    private static JPanel pool;
    private Elements elements;
    private int countConnections;
    private Atom atom1;
    private Atom atom2;
    private JButton delete;
    private ErrMesLog eml;
    private boolean isSelectedElement;

    public MoleculDraw(Elements elements) {
        eml = new ErrMesLog();
        this.elements = elements;
        Storage.atoms = new ArrayList<>();
        setLayout(new BorderLayout());
        pool = new JPanel(null);
        pool.setSize(getWidth(), getHeight());
        pool.setLocation(0, 0);
        pool.setBackground(Color.white);
        countConnections = 0;
        isSelectedElement = false;

        setSize(650, 560);
        setPreferredSize(new Dimension(650, 560));
        delete = new JButton("Delete");
        delete.setSize(80, 50);
        delete.setLocation(10, getHeight() - 60);
        delete.addActionListener(this);
        delete.setActionCommand("delete");
        MyMouseHandler m1 = new MyMouseHandler();
        MyMouseHandlerM m2 = new MyMouseHandlerM();
        addMouseListener(m1);
        addMouseMotionListener(m2);
        FileAction.menuAdapter.addPropertyChangeListener(this);
        add(pool);
        pool.revalidate();
        pool.repaint();
    }

    /**
     * После загрузки из файла добавляет на панель все загруженные компоненты
     */
    public void addAndRepaint() {
        System.out.println("rrrr1");
        for (Atom atom : Storage.atoms) {
            System.out.println("tr" + atom.saveInfo());
            if (atom.getSizeE() == 10)
                atom.setSize(atom.getSizeE() + 1, 2 * atom.getSizeE() + 1);
            pool.add(atom);
            atom.setClick(false);
        }
        for (Atom atom : Storage.atoms) {
            for (Connection connection : atom.getCurrentConnect()) {
                if (atom.getX() == connection.getX1())
                    pool.add(connection);
            }
        }

        countConnections = 0;
        add(pool);
        pool.repaint();
        pool.revalidate();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Удаление элементов с панели
     *
     * @param e Для определения момента нажатия на кнопку "удалить"
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("delete")) {
            for (Atom component : Storage.atoms) {
                if (component.getClick()) {
                    switch (component.getConnections()) {
                        case 1: {
                            eml.doLog("Молекула водорода удалена х:" + component.getX() + " y: " + component.getY());
                            break;
                        }
                        case 2: {
                            eml.doLog("Молекула кислорода удалена х:" + component.getX() + " y: " + component.getY());
                            break;
                        }
                        case 3: {
                            eml.doLog("Молекула азота удалена х:" + component.getX() + " y: " + component.getY());
                            break;
                        }
                        case 4: {
                            eml.doLog("Молекула углерода удалена х:" + component.getX() + " y: " + component.getY());
                            break;
                        }
                    }
                    Storage.atoms.remove(component);
                    pool.remove(component);
                    countConnections--;
                    for (Connection connection : component.getCurrentConnect()) {
                        pool.remove(connection);
                        for (Atom atom : Storage.atoms)
                            if ((atom.getX() == connection.getX1() && atom.getY() == connection.getY1()) | (atom.getX() == connection.getX2() && atom.getY() == connection.getY2())) {
                                atom.deleteCurrentConnect(connection);
                                break;
                            }
                    }
                    break;
                }
                for (Connection connection : component.getCurrentConnect()) {
                    if (connection.getisIsclick()) {
                        pool.remove(connection);
                        for (Atom atom : Storage.atoms)
                            if (atom.getX() == connection.getX1() && atom.getY() == connection.getY1()) {
                                atom.deleteCurrentConnect(connection);
                                break;
                            }
                        for (Atom atom : Storage.atoms)
                            if (atom.getX() == connection.getX2() && atom.getY() == connection.getY2()) {
                                atom.deleteCurrentConnect(connection);
                                break;
                            }
                        break;
                    }
                }
            }
            pool.remove(delete);
            add(pool);
            pool.repaint();
            pool.revalidate();
        }

    }

    /**
     * Слушает нажатие на кнопку "загрузка с файла" в меню
     *
     * @param evt событие
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "startUpload": {
                addAndRepaint();
                break;
            }
            default: {
                System.out.println("Event " + evt.getPropertyName() + "not support");
            }
        }
    }

    public class MyMouseHandler implements MouseListener {
        /**
         * При нажатии на экран в зависимости от условий <p></p>
         * - выделяется/отменятеся выделение на компоненте(атоме, линии) <p></p>
         * - добавляется на экран атом, связь
         *
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("DrawProperties > " + e.getX() + " " + e.getY() + " " + e.getXOnScreen() + " " + e.getYOnScreen());
            clickComponent(e);
            addHydrogen(e);
            addCarbon(e);
            addNitrogen(e);
            addOxygen(e);
            addRadical(e);
            controlDelete();
        }

        /**
         * Добавляет или удаляет кнопку delete в зависимости от выделенных элементов
         */
        public void controlDelete() {
            isSelectedElement = false;
            for (Atom atom : Storage.atoms) {
                if (atom.getClick()) {
                    isSelectedElement = true;
                    break;
                }
                for (Connection connection : atom.getCurrentConnect())
                    if (connection.getisIsclick()) {
                        isSelectedElement = true;
                        break;
                    }
            }
            System.out.println("selected" + isSelectedElement);
            if (isSelectedElement)
                pool.add(delete);
            else
                pool.remove(delete);
            add(pool);
            pool.repaint();
            pool.revalidate();
        }

        /**
         * Если не нажата ни одна из кнопок боковой панели, проверить на какой компонент(связь, атом) произошло нажатие
         *
         * @param e нужен для определения координат (х,у) нажатия
         */
        public void clickComponent(MouseEvent e) {
            if (!elements.H.isClick & !elements.C.isClick & !elements.N.isClick & !elements.O.isClick & !elements.R.isClick)
                for (Atom component : Storage.atoms) {
                    atomClick(e, component);
                    if (countConnections == 0)
                        connectionClick(e, component);

                }
        }

        /**
         * Координаты нажатия находятся внутри области связи.
         * Связь рассматривается только для атома1(х1,у1).
         * Если данные условия соблюдены, то происходит определение наклона и совершено ли нажатие
         *
         * @param e         нужен для определения координат (х,у) нажатия
         * @param component атом
         */
        public void connectionClick(MouseEvent e, Atom component) {
            for (Connection connection1 : component.getCurrentConnect()) {
                if (Math.abs(e.getX() - connection1.getX1()) < connection1.getSizeX() & Math.abs(e.getY() - connection1.getY1()) < connection1.getSizeY()) {
                    if (connection1.getX1() == component.getX() & connection1.getY1() == component.getY()) {
                        detectionIncline(e, connection1);
                    }
                }
            }
        }

        /**
         * Определяет наклон линии связи (/, \, горизонтально, вертикально)
         *
         * @param e           нужен для определения координат (х,у) нажатия
         * @param connection1 связь
         */
        public void detectionIncline(MouseEvent e, Connection connection1) {
            if ((connection1.getX1() < connection1.getX2() & connection1.getY1() < connection1.getY2() | (connection1.getX1() > connection1.getX2() & connection1.getY1() > connection1.getY2())))
                inclineOne(e, connection1);
            else if ((connection1.getX1() < connection1.getX2() & connection1.getY1() > connection1.getY2() | (connection1.getX1() > connection1.getX2() & connection1.getY1() < connection1.getY2()))) {
                inclineTwo(e, connection1);
            } else {
                inclineThree(e, connection1);
            }
        }

        /**
         * При наклоне линии \. Вычисляется тангенс угла гипотенузы и нижней горизонтальной линии и умножается на расстояние от точки конца линии
         * до точки нажатия по х для определения координаты линии по у. Вычитается из нижней точки по у, чтобы найти
         * координату относительно начала фрейма. Если точка нажатия находится в пределах вычисленной точки,
         * то происходит "нажатие".
         *
         * @param e           нужен для определения координат (х,у) нажатия
         * @param connection1 связь
         */
        public void inclineOne(MouseEvent e, Connection connection1) {
            int linesize = connection1.getY2() - ((connection1.getX2() - e.getX()) * connection1.getSizeY() / connection1.getSizeX());
            System.out.println("linesize1 " + linesize);
            System.out.println("y " + e.getY());
            if (e.getY() < linesize + 20 & e.getY() > linesize - 10)
                connection1.setClick(!connection1.getisIsclick());
        }

        /**
         * При наклоне линии /. Вычисляется тангенс угла гипотенузы и нижней горизонтальной линии и умножается на расстояние от точки начала линии
         * до точки нажатия по х для определения координаты линии по у. Вычитается из нижней точки по у, чтобы найти
         * координату относительно начала фрейма. Если точка нажатия находится в пределах вычисленной точки,
         * то происходит "нажатие".
         *
         * @param e           нужен для определения координат (х,у) нажатия
         * @param connection1 связь
         */
        public void inclineTwo(MouseEvent e, Connection connection1) {
            int linesize = connection1.getY1() - ((e.getX() - connection1.getX1()) * connection1.getSizeY() / connection1.getSizeX());
            System.out.println("linesize2 " + linesize);
            System.out.println("y " + e.getY());
            if (e.getY() < linesize + 20 & e.getY() > linesize - 20) {
                connection1.setClick(!connection1.getisIsclick());
            }
        }

        /**
         * Когда связь расположена горизонтально или вертикально. Координаты нажатия должны совпасть
         * с координатой х связи при вертикальном расположении и с координатой у при горизонтальном расположении
         *
         * @param e           нужен для определения координат (х,у) нажатия
         * @param connection1 связь
         */
        public void inclineThree(MouseEvent e, Connection connection1) {
            System.out.println("linesize3y " + connection1.getY1());
            System.out.println("y " + e.getY());
            System.out.println("linesize3x " + connection1.getX1());
            System.out.println("x " + e.getX());
            if ((e.getY() < connection1.getY1() + 10 & e.getY() > connection1.getY1() - 10) | (e.getX() < (connection1.getX1() + 20) & e.getX() > (connection1.getX1() - 20))) {
                connection1.setClick(!connection1.getisIsclick());
            }
        }

        /**
         * Если координаты нажатия находятся внутри области атома, происходит "нажатие" на атом.
         * Происходит изменение его состояния нажатия (нажат/ не нажат).
         * В зависимости от количества нажатых элементов создает связь или добавляет кнопку удаления.
         * Если компонент был выделен, убирает кнопку удаления.
         *
         * @param e         нужен для определения координат (х,у) нажатия
         * @param component атом
         */
        public void atomClick(MouseEvent e, Atom component) {
            if (Math.abs(e.getX() - component.getX()) < component.getSizeE() & Math.abs(e.getY() - component.getY()) < component.getYSizeE()) {
                if (component.getClick()) {
                    pool.remove(delete);
                    countConnections--;
                }
                component.setClick(!component.getClick());
                if (component.getClick()) {
                    countConnections++;
                    firstSelectionAtom(component);
                    secondSelectionAtom(component);
                }
            }
        }

        /**
         * Первый выделенный атом запоминается как atom1
         *
         * @param component выделенный атом
         */
        public void firstSelectionAtom(Atom component) {
            if (countConnections == 1) {
                atom1 = component;
            }
        }

        /**
         * Второй выделенный элемент запоминается как atom2.
         * Выделения с двух нажатых атомов снимаются.
         * По возможности образуется связь.
         *
         * @param component выделенный атом
         */
        public void secondSelectionAtom(Atom component) {
            if (countConnections == 2) {
                System.out.println("con" + countConnections);
                atom2 = component;
                countConnections = 0;
                atom1.setClick(!atom1.getClick());
                atom2.setClick(!atom2.getClick());
                addConnection();
            }
        }

        /**
         * Если количество уже имеющихся связей у атомов не превышает допустимое количество, добавить связь,
         * иначе вывести уведомление об ошибке со справкой о максимальном количестве связей у каждого атома
         */
        public void addConnection() {
            if (atom1.getConnections() > atom1.getCurrentConnect().size() & atom2.getConnections() > atom2.getCurrentConnect().size()) {
                Connection connection;
                if (checkConnection())
                    connection = new Connection(atom1, atom2);
                else {
                    System.out.println("connection bias");
                    connection = new Connection(atom1, atom2, 6);
                }
                atom1.setCurrentConnect(connection);
                atom2.setCurrentConnect(connection);
                pool.add(connection);
            } else
                JOptionPane.showMessageDialog(null, "Максимальное количество связей:\nR - 1;\nH - 1;\nO - 2;\nN - 3;\n C - 4.\n Вы не можете добавить больше.");
        }

        public boolean checkConnection() {
            for (Connection connection1 : atom1.getCurrentConnect()) {
                if (connection1.getX1() == atom1.getX() & connection1.getX2() == atom2.getX() & connection1.getY2() == atom2.getY())
                    return false;
                else if (connection1.getX2() == atom1.getX() & connection1.getX1() == atom2.getX() & connection1.getY1() == atom2.getY())
                    return false;
            }
            return true;

        }

        /**
         * При нажатой кнопке "H" на боковой панели, добавление водорода на место нажатия на экране
         *
         * @param e нужен для определения координат (х,у) нажатия
         */
        public void addHydrogen(MouseEvent e) {
            if (elements.H.isClick) {
                removeAllSelection();
                Hydrogen hydrogen = new Hydrogen(e.getX() - 15 / 2, e.getY() - 15 / 2);
                hydrogen.setSize(hydrogen.getSizeE(), hydrogen.getSizeE());
                pool.add(hydrogen);
                Storage.atoms.add(hydrogen);
                eml.doLog("Добавлен атом водорода х:" + hydrogen.getX() + " y: " + hydrogen.getY());
            }
        }

        /**
         * При нажатой кнопке "C" на боковой панели, добавление углерода на место нажатия на экране
         *
         * @param e нужен для определения координат (х,у) нажатия
         */
        public void addCarbon(MouseEvent e) {
            if (elements.C.isClick) {
                removeAllSelection();
                Carbon carbon = new Carbon(e.getX() - 20 / 2, e.getY() - 20 / 2);
                carbon.setSize(carbon.getSizeE(), carbon.getSizeE());
                pool.add(carbon);
                Storage.atoms.add(carbon);
                eml.doLog("Добавлен атом углерод х:" + carbon.getX() + " y: " + carbon.getY());
            }
        }

        /**
         * При нажатой кнопке "N" на боковой панели, добавление азота на место нажатия на экране
         *
         * @param e нужен для определения координат (х,у) нажатия
         */
        public void addNitrogen(MouseEvent e) {
            if (elements.N.isClick) {
                removeAllSelection();
                Nitrogen nitrogen = new Nitrogen(e.getX() - 25 / 2, e.getY() - 25 / 2);
                nitrogen.setSize(nitrogen.getSizeE(), nitrogen.getSizeE());
                pool.add(nitrogen);
                Storage.atoms.add(nitrogen);
                eml.doLog("Добавлен атом азота х:" + nitrogen.getX() + " y: " + nitrogen.getY());
            }
        }

        /**
         * При нажатой кнопке "O" на боковой панели, добавление кислорода на место нажатия на экране
         *
         * @param e нужен для определения координат (х,у) нажатия
         */
        public void addOxygen(MouseEvent e) {
            if (elements.O.isClick) {
                removeAllSelection();
                Oxygen oxygen = new Oxygen(e.getX() - 30 / 2, e.getY() - 30 / 2);
                oxygen.setSize(oxygen.getSizeE(), oxygen.getSizeE());
                pool.add(oxygen);
                Storage.atoms.add(oxygen);
                eml.doLog("Добавлен атом кислорода х:" + oxygen.getX() + " y: " + oxygen.getY());
            }
        }

        /**
         * При нажатой кнопке "R" на боковой панели, добавление радикала на место нажатия на экране
         *
         * @param e нужен для определения координат (х,у) нажатия
         */
        public void addRadical(MouseEvent e) {
            if (elements.R.isClick) {
                removeAllSelection();
                Radical radical = new Radical(e.getX() - 10 / 2, e.getY() - 20 / 2);
                radical.setSize(radical.getSizeE() + 1, 2 * radical.getSizeE() + 1);
                pool.add(radical);
                Storage.atoms.add(radical);
                eml.doLog("Добавлен радикал х:" + radical.getX() + " y: " + radical.getY());
            }
        }

        /**
         * При добавлении нового атома снимается выделение со всех компонентов
         */
        public void removeAllSelection() {
            for (Atom component : Storage.atoms) {
                component.setClick(false);
                for (Connection connection : component.getCurrentConnect())
                    connection.setClick(false);
            }
            countConnections = 0;
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

    public class MyMouseHandlerM implements MouseMotionListener {

        /**
         * Обрабатывает перемещение выбранного курсором мыши элемента по панели
         *
         * @param e
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("dragged");
            if (!elements.H.isClick & !elements.C.isClick & !elements.N.isClick & !elements.O.isClick & !elements.R.isClick)
                for (Atom component : Storage.atoms)
                    if (Math.abs(e.getX() - component.getX()) < +component.getSizeE() & Math.abs(e.getY() - component.getY()) < +component.getYSizeE()) {
                        for (Connection connection : component.getCurrentConnect()) {
                            //System.out.println("conx1: "+ connection.getX1() + "conx2 "+ connection.getX2() + "X " + component.getX());
                            if (connection.getX1() == component.getX()) {
                                System.out.println("dragged1");
                                connection.setX1(e.getX() - component.getSizeE() / 2);
                                connection.setY1(e.getY() - component.getSizeE() / 2);
                                connection.setSizeX(Math.max(Math.abs(connection.getX1() - connection.getX2()) + connection.getSize1() / 2, 20));
                                connection.setSizeY(Math.max(Math.abs(connection.getY1() - connection.getY2()) + connection.getSize2() / 2, 20));
                                connection.setSize(connection.getSizeX(), connection.getSizeY());
                                connection.setLocation(Math.min(connection.getX1(), connection.getX2()), Math.min(connection.getY1(), connection.getY2()));
                            } else if (connection.getX2() == component.getX()) {
                                System.out.println("dragged2");
                                connection.setX2(e.getX() - component.getSizeE() / 2);
                                connection.setY2(e.getY() - component.getSizeE() / 2);
                                connection.setSizeX(Math.max(Math.abs(connection.getX1() - connection.getX2()) + connection.getSize1() / 2, 20));
                                connection.setSizeY(Math.max(Math.abs(connection.getY1() - connection.getY2()) + connection.getSize2() / 2, 20));
                                connection.setSize(connection.getSizeX(), connection.getSizeY());
                                connection.setLocation(Math.min(connection.getX1(), connection.getX2()), Math.min(connection.getY1(), connection.getY2()));
                            }
                        }
                        component.setX(e.getX() - component.getSizeE() / 2);
                        component.setY(e.getY() - component.getSizeE() / 2);
                        repaint();
                    }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}