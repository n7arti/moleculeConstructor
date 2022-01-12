package controller;

import frame.sub.Elements;
import frame.sub.MoleculDraw;
import frame.main.MainDesktopPane;
import frame.main.MainFrame;
import menu.MainMenuBar;
import model.ErrMesLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class Start
{
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                //Создаем массив объектов APPLICATIONS, который будет хранить ссылки на объекты программы
                Map<String, Object> Application = new HashMap();
                ErrMesLog eml = new ErrMesLog();
                //Создаем главное окно программы
                JFrame PFrame = new MainFrame();
                eml.doLog("Начало работы программы");
                PFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        eml.doLog("Конец работы программы");
                    }
                });
                PFrame.setName(App.PFrame);
                App.Frames.put(App.PFrame, PFrame);

                //Создаем область рабочего стола в которую можно помещать фреймы
                JDesktopPane desktopPanePFrame = new MainDesktopPane();
                desktopPanePFrame.setName(App.desktopPanePFrame);

                PFrame.add(desktopPanePFrame);
                PFrame.setVisible(true);
                App.Frames.put(App.desktopPanePFrame, desktopPanePFrame);

                //создаем окно с инструментами
                Elements PropFrame = new Elements();
                //Создаем окно с молекулой
                JPanel GFrame = new MoleculDraw(PropFrame);
                GFrame.setVisible(true);
                Application.put("GFrame", GFrame);

                PropFrame.setVisible(true);
                Application.put("PropFrame", PropFrame);


                //Создаем меню
                JMenuBar MMB = new MainMenuBar(desktopPanePFrame);

                //Добавляем в главное окно все ранее созданные компоненты
                desktopPanePFrame.add(GFrame, BorderLayout.WEST);
                desktopPanePFrame.add(PropFrame, BorderLayout.EAST);
                PFrame.setJMenuBar(MMB);
            }
        });
    }
}