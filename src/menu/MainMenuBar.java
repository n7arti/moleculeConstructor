package menu;

import javax.swing.*;

/**
 * Класс для отображения кнопок меню на верхней панели
 */
public class MainMenuBar extends JMenuBar {
    private JDesktopPane JDP;

    public MainMenuBar(JDesktopPane jdp) {
        super();
        this.JDP = jdp;
        FileMenu FileMenu = new FileMenu("File", jdp);
        this.add(FileMenu); //Для нескольких пунктов будет несколько new и add
    }
}

