package ru.mail.dimaushenko.controller.impl;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.model.AddUserGroupDTO;
import ru.mail.dimaushenko.utils.impl.PropertyUtilTaskImpl;
import ru.mail.dimaushenko.service.UserGroupService;
import ru.mail.dimaushenko.service.impl.UserGroupServiceIpml;

import static ru.mail.dimaushenko.constants.PropertyTask.C_TASK_AMOUNT_USER_GROUPS;

public class CTask implements ControllerService {

    private static ControllerService instance = null;

    private CTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new CTask();
        }
        return instance;
    }

    PropertyUtilTaskImpl propertyUtil = PropertyUtilTaskImpl.getInstance();
    UserGroupService userGroupService = UserGroupServiceIpml.getInstance();

    private final String namePattern = "user group ";

    @Override
    public void run() {

        int amountUserGroups = propertyUtil.getIntegerProperty(C_TASK_AMOUNT_USER_GROUPS);

        for (int i = 0; i < amountUserGroups; i++) {
            AddUserGroupDTO userGroupDTO = new AddUserGroupDTO();
            userGroupDTO.setName(namePattern + i);
            userGroupService.addUserGroup(userGroupDTO);
        }
    }

}
