package ru.mail.dimaushenko.controller.impl;

import ru.mail.dimaushenko.controller.ControllerService;

public class FirstTask implements ControllerService {

    private static ControllerService instance = null;

    private FirstTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new FirstTask();
        }
        return instance;
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
