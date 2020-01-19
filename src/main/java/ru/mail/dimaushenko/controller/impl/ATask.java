package ru.mail.dimaushenko.controller.impl;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.TableService;
import ru.mail.dimaushenko.service.impl.UserGroupTableServiceImpl;
import ru.mail.dimaushenko.service.impl.UserInformationTableServiceImpl;
import ru.mail.dimaushenko.service.impl.UserTableServiceImpl;

public class ATask implements ControllerService {

    private static ControllerService instance = null;

    private ATask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new ATask();
        }
        return instance;
    }

    private final TableService userTableService = UserTableServiceImpl.getInstance();
    private final TableService userGroupTableService = UserGroupTableServiceImpl.getInstance();
    private final TableService userInformationTableService = UserInformationTableServiceImpl.getInstance();

    @Override
    public void run() {

        boolean isUserInformationTableRemoved = userInformationTableService.removeTable();
        if (isUserInformationTableRemoved) {
            boolean isUserTableRemoved = userTableService.removeTable();
            if (isUserTableRemoved) {
                userGroupTableService.removeTable();
            }
        }

    }

}
