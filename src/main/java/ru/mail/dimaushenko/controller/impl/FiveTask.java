package ru.mail.dimaushenko.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.service.model.UserDTO;

public class FiveTask implements ControllerService {

    private static ControllerService instance = null;

    private FiveTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new FiveTask();
        }
        return instance;
    }

    private final UserService userService = UserServiceImpl.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    @Override
    public void run() {
        List<UserDTO> usersDTO = userService.getAll();
        for (UserDTO userDTO : usersDTO) {
            LOGGER.info(userDTO);
        }
    }


}
