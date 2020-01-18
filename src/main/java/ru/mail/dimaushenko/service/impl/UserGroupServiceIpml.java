package ru.mail.dimaushenko.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mail.dimaushenko.repository.ConnectionPool;
import ru.mail.dimaushenko.repository.UserGroupRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.UserGroupRepositoryImpl;
import ru.mail.dimaushenko.service.UserGroupService;
import ru.mail.dimaushenko.service.model.AddUserGroupDTO;
import ru.mail.dimaushenko.service.model.UserGroupDTO;
import ru.mail.dimaushenko.repository.model.UserGroup;
import ru.mail.dimaushenko.service.model.UserGroupIdDTO;

public class UserGroupServiceIpml implements UserGroupService {

    private static UserGroupService instance = null;

    private UserGroupServiceIpml() {

    }

    public static UserGroupService getInstance() {
        if (instance == null) {
            instance = new UserGroupServiceIpml();
        }
        return instance;
    }

    private ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();

    @Override
    public void addUserGroup(AddUserGroupDTO AddUserGroupDTO) {
        UserGroup userGroup = convertAddUserGroupToUserGroup(AddUserGroupDTO);
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroupRepository.addEntity(connection, userGroup);
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(UserGroupServiceIpml.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserGroupServiceIpml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<UserGroupIdDTO> getAllUserGroupId() {
        List<UserGroupIdDTO> userGroupIdDTOs = new ArrayList();
        List<UserGroup> userGroups = new ArrayList();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroups = userGroupRepository.getAll(connection);
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(UserGroupServiceIpml.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserGroupServiceIpml.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (UserGroup userGroup : userGroups) {
            UserGroupIdDTO userGroupIdDTO = convertUserGroupToUserGroupIdDTO(userGroup);
            userGroupIdDTOs.add(userGroupIdDTO);
        }
        return userGroupIdDTOs;
    }

    private UserGroup convertAddUserGroupToUserGroup(AddUserGroupDTO userGroupDTO) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(userGroupDTO.getName());
        return userGroup;
    }

    private UserGroupDTO convertUserGroupToViewUserGroupDTO(UserGroup userGroup) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();

        userGroupDTO.setName(userGroup.getName());
        userGroupDTO.setAmountOfUser(userGroup.getUsers().size());

        return userGroupDTO;

    }

    private UserGroupIdDTO convertUserGroupToUserGroupIdDTO(UserGroup userGroup) {
        UserGroupIdDTO userGroupIdDTO = new UserGroupIdDTO();
        
        userGroupIdDTO.setId(userGroup.getId());
        userGroupIdDTO.setName(userGroup.getName());
        
        return userGroupIdDTO;
    }

}
