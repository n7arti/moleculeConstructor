package model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Класс, отвечающий за работу связей между атомами
 */
public class Connection extends JComponent {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int size1;
    private int size2;
    private int sizeX;
    private int sizeY;
    private Line2D line;
    private boolean isClick;
    private final int bias;

    public int getSizeX() {
        return sizeX;
    }
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    public boolean getisIsclick() {
        return isClick;
    }
    public void setClick(boolean click) {
        this.isClick = click;
    }
    public int getSize1() {
        return size1;
    }
    public void setSize1(int size1) {
        this.size1 = size1;
    }
    public int getSize2() {
        return size2;
    }
    public void setSize2(int size2) {
        this.size2 = size2;
    }
    public int getX1() {
        return x1;
    }
    public void setX1(int x1) {
        this.x1 = x1;
    }
    public int getY1() {
        return y1;
    }
    public void setY1(int y1) {
        this.y1 = y1;
    }
    public int getX2() {
        return x2;
    }
    public void setX2(int x2) {
        this.x2 = x2;
    }
    public int getY2() {
        return y2;
    }
    public void setY2(int y2) {
        this.y2 = y2;
    }

    /**
     * В конструкторе вычисляются начальная и конечная точки связи
     * @param atom1 первый атом
     * @param atom2 второй атом
     */
    public Connection(Atom atom1, Atom atom2) {
        super();
        //setBackground(Color.cyan);
        this.x1 = atom1.getX();
        this.y1 = atom1.getY();
        this.x2 = atom2.getX();
        this.y2 = atom2.getY();
        this.size1 = atom2.getSizeE();
        this.size2 = atom2.getSizeE();
        this.bias = 0;
        isClick = false;
        System.out.println("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
        if (x2 < x1) {
            x1 = x1 ^ x2 ^ (x2 = x1);
            y1 = y1 ^ y2 ^ (y2 = y1);
            size1 = size1 ^ size2 ^ (size2 = size1);
        }
        sizeX = Math.max(Math.abs(x1 - x2) + size1 / 2, 20);
        sizeY = Math.max(Math.abs(y1 - y2) + size2 / 2, 20);
        setSize(sizeX, sizeY);
        setLocation(Math.min(x1, x2), Math.min(y1, y2));
        line = new Line2D.Double(new Point2D.Double(x1 - getX() + size1 / 2, y1 - getY() + size1 / 2), new Point2D.Double(x2 - getX() + size2 / 2, y2 - getY() + size2 / 2));
    }
    /**
     * В конструкторе вычисляются начальная и конечная точки второй связи
     * @param atom1 первый атом
     * @param atom2 второй атом
     * @param bias смещение относительно первой линии
     */
    public Connection(Atom atom1, Atom atom2, int bias){
        super();
        this.x1 = atom1.getX();
        this.y1 = atom1.getY();
        this.x2 = atom2.getX();
        this.y2 = atom2.getY();
        this.size1 = atom2.getSizeE();
        this.size2 = atom2.getSizeE();
        this.bias = bias;
        isClick = false;
        System.out.println("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
        if (x2 < x1) {
            x1 = x1 ^ x2 ^ (x2 = x1);
            y1 = y1 ^ y2 ^ (y2 = y1);
            size1 = size1 ^ size2 ^ (size2 = size1);
        }
        sizeX = Math.max(Math.abs(x1 - x2 ) + size1 / 2, 20);
        sizeY = Math.max(Math.abs(y1 - y2) + size2 / 2, 20);
        setSize(sizeX, sizeY);
        setLocation(Math.min(x1 - bias, x2-bias), Math.min(y1-bias, y2-bias));
        line = new Line2D.Double(new Point2D.Double(x1 - getX() + size1 / 2 - bias, y1 - getY() + size1 / 2 - bias), new Point2D.Double(x2 - getX() + size2 / 2 - bias, y2 - getY() + size2 / 2 - bias));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.repaint(); // clear component //
        Graphics2D g2d = (Graphics2D) g;
        if (isClick)
            g2d.setColor(Color.red);
        else
            g2d.setColor(Color.black);
        this.line = new Line2D.Double(new Point2D.Double(x1 - getX() + size1 / 2 - bias, y1 - getY() + size1 / 2 - bias), new Point2D.Double(x2 - getX() + size2 / 2 - bias, y2 - getY() + size2 / 2 - bias));
        g2d.setStroke(new BasicStroke(2.0F));
        g2d.draw(line);

    }

    @Override
    public Dimension getPreferredSize() {
        Rectangle bounds = line.getBounds();

        int width = bounds.x + bounds.width;
        int height = bounds.y + bounds.height;

        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        Rectangle bounds = line.getBounds();

        int width = bounds.x + bounds.width;
        int height = bounds.y + bounds.height;

        return new Dimension(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        Rectangle bounds = line.getBounds();

        int width = bounds.x + bounds.width;
        int height = bounds.y + bounds.height;

        return new Dimension(width, height);
    }

    public String saveInfo() {
        StringBuilder result = new StringBuilder();
        result.append("x1: ").append(x1).append('\n');
        result.append("y1: ").append(y1).append('\n');
        result.append("x2: ").append(x2).append('\n');
        result.append("y2: ").append(y2).append('\n');
        return result.toString();
    }
}
