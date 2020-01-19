package ru.mail.dimaushenko.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.service.UserGroupService;
import ru.mail.dimaushenko.service.impl.UserGroupServiceIpml;
import ru.mail.dimaushenko.service.model.UserGroupDTO;

public class FTask implements ControllerService {

    private static ControllerService instance = null;

    private FTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new FTask();
        }
        return instance;
    }

    private final UserGroupService userGroupService = UserGroupServiceIpml.getInstance();
    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void run() {
        List<UserGroupDTO> userGroupsDTO = userGroupService.getAllUserGroup();

        for (UserGroupDTO userGroupDTO : userGroupsDTO) {
            LOGGER.info(userGroupDTO);
        }
    }

}
