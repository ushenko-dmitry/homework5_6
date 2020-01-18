package ru.mail.dimaushenko.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilTaskImpl;
import ru.mail.dimaushenko.service.model.FullUserDTO;

import static ru.mail.dimaushenko.constants.PropertyTask.G_TASK_AGE_FOR_REMOVE;

public class GTask implements ControllerService {

    private static ControllerService instance = null;

    private GTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new GTask();
        }
        return instance;
    }

    private final PropertyUtil propertyUtil = PropertyUtilTaskImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final String amountRemoveUserPattern = "Removed users: ";

    @Override
    public void run() {

        List<FullUserDTO> usersDTO = userService.getAllFull();
        int amountRemoveUsers = 0;

        for (FullUserDTO userDTO : usersDTO) {
            if (userDTO.getAge() < propertyUtil.getIntegerProperty(G_TASK_AGE_FOR_REMOVE)) {
                if (userService.removeUser(userDTO)) {
                    amountRemoveUsers++;
                }
            }
        }
        LOGGER.info(amountRemoveUserPattern + amountRemoveUsers);

    }

}
