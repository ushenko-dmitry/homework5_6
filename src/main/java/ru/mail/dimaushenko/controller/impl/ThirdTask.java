package ru.mail.dimaushenko.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.model.AddUserGroupDTO;
import ru.mail.dimaushenko.utils.PropertyUtil;

import static ru.mail.dimaushenko.constants.PropertiesTask.THIRD_TASK_AMOUNT_USER_GROUPS;
import static ru.mail.dimaushenko.constants.PropertyFiles.TASK_PROPERTIES;

public class ThirdTask implements ControllerService {

    PropertyUtil propertyUtil = PropertyUtil.getInstance();

    private final String namePattern = "user group ";

    @Override
    public void run() {
        
        int amountUserGroups = propertyUtil.getIntegerProperty(TASK_PROPERTIES, THIRD_TASK_AMOUNT_USER_GROUPS);

        for (int i = 0; i < amountUserGroups; i++) {
            AddUserGroupDTO userGroupDTO = new AddUserGroupDTO();
            userGroupDTO.setName(namePattern + i);
            
            
        }
    }

}
