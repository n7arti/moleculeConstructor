package model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Базовый класс, от которого унаследованы все виды атомов.
 * Не является абстрактным классом.
 */
public class Atom extends JComponent {
    private int x;
    private int y;
    private int connections;
    private int size;
    private Color color;
    private ArrayList<Connection> currentConnect;
    private Ellipse2D.Double ellipse;
    private Rectangle2D.Double rectangle;
    private boolean isClick;

    public ArrayList<Connection> getCurrentConnect() {
        return currentConnect;
    }

    public void setCurrentConnect(Connection currentConnect) {
        this.currentConnect.add(currentConnect);
    }
    public void deleteCurrentConnect(Connection currentConnect) {
        this.currentConnect.remove(currentConnect);
    }
    public boolean getClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public int getSizeE() {
        return size;
    }
    public int getYSizeE() {
        if (size==10)
            return 2*size;
        else
            return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public Atom(int x, int y, int connections, int size, Color color) {
        super();
        setSize(size, size);
        this.x = x;
        this.y = y;
        this.connections = connections;
        this.size = size;
        this.color = color;
        isClick = false;
        currentConnect = new ArrayList<>();
    }

    public Atom() {
        x = 0;
        y = 0;
        connections = 0;
        size = 10;
        color = Color.BLACK;
    }

    @Override
    public Dimension getMinimumSize() {
        if (size == 10)
            return new Dimension(size+1, 2*size+1);
        else
            return new Dimension(size, size);
    }

    @Override
    public Dimension getPreferredSize() {
        if (size == 10)
            return new Dimension(size+1, 2*size+1);
        else
            return new Dimension(size, size);

    }

    @Override
    public Dimension getMaximumSize() {
        if (size == 10)
            return new Dimension(size+1, 2*size+1);
        else
            return new Dimension(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.repaint(); // clear component //
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        if (size == 10) {
            this.rectangle = new Rectangle2D.Double(0, 0, size, 2*size);
            g2d.fill(this.rectangle);
            if (isClick)
                g2d.setColor(Color.red);
            else g2d.setColor(Color.black);
            g2d.draw(this.rectangle);
            g.setColor(Color.black);
            String text = "R";
            g2d.drawString(text, 1, 15);
        } else {
            this.ellipse = new Ellipse2D.Double(0, 0, size, size);
            g2d.fill(this.ellipse);
            if (isClick)
                g2d.setColor(Color.red);
            else g2d.setColor(new Color(105, 105, 105));
            g2d.draw(this.ellipse);
        }
    }

    public String saveInfo() {
        StringBuilder result = new StringBuilder();
        result.append("x: ").append(x).append('\n');
        result.append("y: ").append(y).append('\n');
        result.append("connections: ").append(connections).append('\n');
        result.append("size: ").append(size).append('\n');
        result.append("r: ").append(color.getRed()).append('\n');
        result.append("g: ").append(color.getGreen()).append('\n');
        result.append("b: ").append(color.getBlue()).append('\n');
        return result.toString();
    }
}
