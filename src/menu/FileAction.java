package menu;

import controller.MenuAdapter;
import model.JSON;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Класс для обработки нажатий в меню
 */
public class FileAction extends AbstractAction {
    private JDesktopPane JDP; //Для работы с объектами рабочего стола
    private String PunktMenuName;
    public static MenuAdapter menuAdapter = new MenuAdapter();

    public FileAction(String name, JDesktopPane jdp) {
        super(name);
        this.JDP = jdp;
        this.PunktMenuName = name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.JDP != null) {
            //Выводим данные в консоль для контроля
            System.out.println(getValue(Action.NAME)
                    + " selected "
                    + this.JDP.getName()
            );
            //Делаем видимыми соответствующие фреймы

            if (this.PunktMenuName == "Save molecul") {
                JSON.toGSON();
            }
            if (this.PunktMenuName == "Upload molecul") {
                System.out.println("getValue(Action.NAME)");
                JSON.fromGSON();
                menuAdapter.uploadEvent();
                //MoleculDraw.addAndRepaint();
            }
        } else {
            JMenuItem JMI = (JMenuItem) e.getSource();
            System.out.println(getValue(Action.NAME)
                            + " selected \n"
                            + e.getSource().getClass().getName()
                            + "\n"
                            + JMI.getParent().getClass().getName()
                            + "\n"
            );
        }
    }
}
