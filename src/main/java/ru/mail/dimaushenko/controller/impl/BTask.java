package ru.mail.dimaushenko.controller.impl;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.TableService;
import ru.mail.dimaushenko.service.impl.UserGroupTableServiceImpl;
import ru.mail.dimaushenko.service.impl.UserInformationTableServiceImpl;
import ru.mail.dimaushenko.service.impl.UserTableServiceImpl;

public class BTask implements ControllerService {

    private static ControllerService instance = null;

    private BTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new BTask();
        }
        return instance;
    }

    private final TableService userTableService = UserTableServiceImpl.getInstance();
    private final TableService userGroupTableService = UserGroupTableServiceImpl.getInstance();
    private final TableService userInformationTableService = UserInformationTableServiceImpl.getInstance();

    @Override
    public void run() {

        boolean isUserGroupTableCreated = userGroupTableService.createTable();
        if (isUserGroupTableCreated) {
            boolean isUserTableCreated = userTableService.createTable();
            if (isUserTableCreated) {
                userInformationTableService.createTable();
            }
        }

    }

}
