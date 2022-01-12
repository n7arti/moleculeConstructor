package model;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Класс, обрабатывающий ошибки и сохраняющий их, а так же действия пользователя в лог файл.
 */
public class ErrMesLog {
    private final Logger logger = Logger.getLogger(ErrMesLog.class.getName());
    private FileHandler fh = null;
    private static String FILENAME = "logfile.log";

    public ErrMesLog() {
        try {
            fh = new FileHandler(FILENAME, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
    }

    /**
     * Действия пользователя
     * @param str строка с действием
     */
    public void doLog(String str) {
        logger.info(str);
    }

    /**
     * Ошибки пользователя
     * @param str строка с ошибкой
     */
    public void errLog(String str){
        logger.severe(str);
    }
}
