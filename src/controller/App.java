package controller;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class App {

    /*
     * Frames содержит все графические объекты прлиожения для быстрого доступа к ним
     * Ключ содержит имена окон в иерархической последовательности вложенности
     */

    public static final Map<String, Object> Frames = new HashMap<String, Object>();
    public static final Map <String, String> Keys = new HashMap<String, String>();

    public static final String PFrame = "PFrame";
    public static final String desktopPanePFrame = "desktopPanePFrame";
    public static final String PPrefix = PFrame+"/"+desktopPanePFrame;


    private static String StatusKey = PPrefix+"/FrameAppStatus/panel/scrollJTA/JTA";

    //Метод добавляет запись в строку состояния
    public static final boolean putStatus(String t) {
        if( !t.isEmpty() && Frames.get(StatusKey) != null ) {
            JTextArea JTA  = (JTextArea) Frames.get(StatusKey);
            JTA.setText(JTA.getText()+"\n"+t);
            return true;
        } else {
            return false;
        }
    }

    //Прототип метода обработки запроса
    public static String getResponse(Map <String, Object> Request) {
        String Result = "";
        return Result;
    }
}