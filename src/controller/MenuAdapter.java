package controller;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MenuAdapter {
    private final PropertyChangeSupport support;


    public MenuAdapter(){
        support = new PropertyChangeSupport(this);
    }

    public void uploadEvent() {
        support.firePropertyChange("startUpload", 0, 1);
    }

    public void addPropertyChangeListener(PropertyChangeListener menuListener) {
        support.addPropertyChangeListener(menuListener);
    }
    public void removePropertyChangeListener(PropertyChangeListener menuListener) {
        support.removePropertyChangeListener(menuListener);
    }

    public void finishEvent() {
        support.firePropertyChange("finish", false, true);
    }
}

