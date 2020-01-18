package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;

import ru.mail.dimaushenko.repository.ConnectionPool;
import ru.mail.dimaushenko.repository.UserGroupRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.UserGroupRepositoryImpl;
import ru.mail.dimaushenko.service.UserGroupService;
import ru.mail.dimaushenko.service.model.AddUserGroupDTO;
import ru.mail.dimaushenko.service.model.UserGroupDTO;
import ru.mail.dimaushenko.repository.model.UserGroup;
import ru.mail.dimaushenko.repository.model.UserGroupWithUserAmount;
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

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();
    private final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void addUserGroup(AddUserGroupDTO AddUserGroupDTO) {
        UserGroup userGroup = convertAddUserGroupToUserGroup(AddUserGroupDTO);
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroupRepository.addEntity(connection, userGroup);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<UserGroupDTO> getAllUserGroup() {
        List<UserGroupDTO> userGroupsDTO = new ArrayList();
        List<UserGroupWithUserAmount> userGroupsWithUserAmount = new ArrayList();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroupsWithUserAmount = userGroupRepository.getAllUserGroupWithUserAmount(connection);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        for (UserGroupWithUserAmount userGroupWithUserAmount : userGroupsWithUserAmount) {
            UserGroupDTO userGroupDTO = convertUserGroupWithUserAmountToViewUserGroupDTO(userGroupWithUserAmount);
            userGroupsDTO.add(userGroupDTO);
        }
        return userGroupsDTO;
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
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
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

    private UserGroupDTO convertUserGroupWithUserAmountToViewUserGroupDTO(UserGroupWithUserAmount userGroup) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();

        userGroupDTO.setName(userGroup.getName());
        userGroupDTO.setAmountOfUser(userGroup.getUserAmount());

        return userGroupDTO;

    }

    private UserGroupIdDTO convertUserGroupToUserGroupIdDTO(UserGroup userGroup) {
        UserGroupIdDTO userGroupIdDTO = new UserGroupIdDTO();

        userGroupIdDTO.setId(userGroup.getId());
        userGroupIdDTO.setName(userGroup.getName());

        return userGroupIdDTO;
    }

}
