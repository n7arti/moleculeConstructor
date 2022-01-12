package menu;

import javax.swing.*;

/**
 * Класс компонентов меню.
 * Определяет возможности меню
 */
public class FileMenu extends JMenu {
    private JDesktopPane JDP;

    public FileMenu(String StartName) {
        super(StartName); //Это имя первого пункта меню в колонке
        makeMenu();
        this.JDP = null;
    }

    public FileMenu(String StartName, JDesktopPane jdp) {
        super(StartName); //Это имя первого пункта меню в колонке
        this.JDP = jdp;
        makeMenu();
    }

    private void makeMenu() {
        JMenuItem Punkt1 = new JMenuItem("Save molecul");
        JMenuItem Punkt2 = new JMenuItem("Upload molecul");
        Punkt1.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        Punkt2.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

        this.add(Punkt1);
        this.add(Punkt2);

        Punkt1.addActionListener(new FileAction("Save molecul", this.JDP));
        Punkt2.addActionListener(new FileAction("Upload molecul", this.JDP));
    }
}
