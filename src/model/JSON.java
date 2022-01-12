package model;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Класс сохраняющий/считывающий молекулу пользователя в файл/с файла
 */
public class JSON {
    private static ErrMesLog eml = new ErrMesLog();

    /**
     * Сохраняет в файл
     */
    public static void toGSON() {
        String json = "";
        String jsonCon = "";


        for (Atom atom : Storage.atoms) {
            System.out.println(atom.saveInfo());
            json += atom.saveInfo();
            for (Connection connection : atom.getCurrentConnect()) {
                if (atom.getX() == connection.getX1())
                    jsonCon += connection.saveInfo();
            }
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("moleculs.json");
            writer.write(json);
            writer.close();
            writer = new FileWriter("connections.json");
            writer.write(jsonCon);
            writer.close();
            eml.doLog("Молекулы сохранены");
        } catch (IOException e) {
            eml.errLog("Файл не обнаружен. Данные не сохранены");
            JOptionPane.showMessageDialog(null, "Файл не обнаружен. Данные не сохранены");
        }
    }

    /**
     * Загружает из файла
     */
    public static void fromGSON() {
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader1 = null;
        Atom atom, atom1, atom2;
        int x;
        int y;
        int connections;
        int size;
        int r, g, b;
        int x1, x2, y1, y2;
        String line;
        atom1 = new Atom();
        atom2 = new Atom();


        Storage.atoms.removeAll(Storage.atoms);
        try {
            bufferedReader = new BufferedReader(
                    new FileReader("moleculs.json"));
            while ((line = bufferedReader.readLine()) != null) {
                x = Integer.parseInt(line.split(": ")[1]);
                y = Integer.parseInt(bufferedReader.readLine().split(": ")[1]);
                connections = Integer.parseInt(bufferedReader.readLine().split(": ")[1]);
                size = Integer.parseInt(bufferedReader.readLine().split(": ")[1]);
                r = Integer.parseInt(bufferedReader.readLine().split(": ")[1]);
                g = Integer.parseInt(bufferedReader.readLine().split(": ")[1]);
                b = Integer.parseInt(bufferedReader.readLine().split(": ")[1]);
                atom = new Atom(x, y, connections, size, new Color(r, g, b));
                Storage.atoms.add(atom);
            }
        } catch (Exception e) {
            eml.errLog("Файл не обнаружен. Данные не загружены");
            JOptionPane.showMessageDialog(null, "Файл molecul не обнаружен. Данные не загружены");
        }
        try {
            bufferedReader1 = new BufferedReader(
                    new FileReader("connections.json"));
            while ((line = bufferedReader1.readLine()) != null) {
                x1 = Integer.parseInt(line.split(": ")[1]);
                y1 = Integer.parseInt(bufferedReader1.readLine().split(": ")[1]);
                x2 = Integer.parseInt(bufferedReader1.readLine().split(": ")[1]);
                y2 = Integer.parseInt(bufferedReader1.readLine().split(": ")[1]);
                for (Atom molecult : Storage.atoms) {
                    if (molecult.getX() == x1 & molecult.getY() == y1)
                        atom1 = molecult;
                    if (molecult.getX() == x2 & molecult.getY() == y2)
                        atom2 = molecult;
                }
                Connection connection;
                if (checkConnection(atom1, atom2))
                    connection = new Connection(atom1, atom2);
                else {
                    System.out.println("connection bias");
                    connection = new Connection(atom1, atom2, 6);
                }
                atom1.setCurrentConnect(connection);
                atom2.setCurrentConnect(connection);

            }
            bufferedReader.close();
            bufferedReader1.close();
        } catch (Exception e) {
            eml.errLog("Файл не обнаружен. Данные не загружены");
            JOptionPane.showMessageDialog(null, "Файл connections не обнаружен. Данные не загружены");
        }
        for (Atom molecult : Storage.atoms)
            System.out.println("lala " + molecult.saveInfo());
    }

    public static boolean checkConnection(Atom atom1,Atom atom2) {
        for (Connection connection1 : atom1.getCurrentConnect()) {
            if (connection1.getX1() == atom1.getX() & connection1.getX2() == atom2.getX() & connection1.getY2() == atom2.getY())
                return false;
            else if (connection1.getX2() == atom1.getX() & connection1.getX1() == atom2.getX() & connection1.getY1() == atom2.getY())
                return false;
        }
        return true;

    }
}
