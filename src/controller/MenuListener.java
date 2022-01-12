package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MenuListener implements PropertyChangeListener {


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "startUpload": {
                break;
            }
            default:{
                System.out.println("Event " + evt.getPropertyName() + "not support");
            }
        }
    }
}
