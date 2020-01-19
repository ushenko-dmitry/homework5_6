package ru.mail.dimaushenko.controller.impl;

import java.util.List;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.service.model.FullUserDTO;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilTaskImpl;

import static ru.mail.dimaushenko.constants.PropertyTask.H_TASK_MAX_AGE;
import static ru.mail.dimaushenko.constants.PropertyTask.H_TASK_MIN_AGE;

public class HTask implements ControllerService {

    private static ControllerService instance = null;

    private HTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new HTask();
        }
        return instance;
    }

    private final PropertyUtil propertyUtil = PropertyUtilTaskImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void run() {
        int minAge = propertyUtil.getIntegerProperty(H_TASK_MIN_AGE);
        int maxAge = propertyUtil.getIntegerProperty(H_TASK_MAX_AGE);

        List<FullUserDTO> usersDTO = userService.getAllFull();

        for (FullUserDTO userDTO : usersDTO) {
            if (userDTO.getAge() >= minAge && userDTO.getAge() <= maxAge) {
                if (userDTO.isIsActive()) {
                    userDTO.setIsActive(false);
                    userService.updateUser(userDTO);
                }
            }
        }
    }

}
