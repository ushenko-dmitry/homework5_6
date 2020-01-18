package ru.mail.dimaushenko.controller.impl;

import java.util.List;
import ru.mail.dimaushenko.controller.ControllerService;

import static ru.mail.dimaushenko.constants.PropertyTask.FOUR_TASK_AMOUNT_USERS;
import static ru.mail.dimaushenko.constants.PropertyTask.FOUR_TASK_MAX_AGE;
import static ru.mail.dimaushenko.constants.PropertyTask.FOUR_TASK_MIN_AGE;
import ru.mail.dimaushenko.repository.model.UserGroup;
import ru.mail.dimaushenko.service.UserGroupService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.impl.UserGroupServiceIpml;
import ru.mail.dimaushenko.service.impl.UserServiceImpl;
import ru.mail.dimaushenko.service.model.AddUserWithUserGroupDTO;
import ru.mail.dimaushenko.service.model.UserGroupIdDTO;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.RandomUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilTaskImpl;

public class FourTask implements ControllerService {

    private static ControllerService instance = null;

    private FourTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new FourTask();
        }
        return instance;
    }

    private final PropertyUtil propertyUtil = PropertyUtilTaskImpl.getInstance();
    private final RandomUtil randomUtil = RandomUtil.getInstance();
    private final UserGroupService userGroupService = UserGroupServiceIpml.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    
    private final String usernamePattern = "user_";
    private final String passwordPattern = "pass_";
    
    @Override
    public void run() {
        
        int amountUser = propertyUtil.getIntegerProperty(FOUR_TASK_AMOUNT_USERS);
        int ageMin = propertyUtil.getIntegerProperty(FOUR_TASK_MIN_AGE);
        int ageMax = propertyUtil.getIntegerProperty(FOUR_TASK_MAX_AGE);
        
        for (int i = 0; i < amountUser; i++){
            AddUserWithUserGroupDTO addUserDTO = new AddUserWithUserGroupDTO();
            
            addUserDTO.setUsername(usernamePattern + i);
            addUserDTO.setPassword(passwordPattern + (amountUser-i));
            addUserDTO.setAge(randomUtil.getInt(ageMin, ageMax));
            addUserDTO.setIsActive(true);
            List<UserGroupIdDTO> userGroups = userGroupService.getAllUserGroupId();
            addUserDTO.setUserGroupId(randomUtil.getInt(userGroups));
            
            userService.addEntityWithUserGroup(addUserDTO);
        }
        
    }

}
