package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;

import ru.mail.dimaushenko.repository.ConnectionPool;
import ru.mail.dimaushenko.repository.UserGroupRepository;
import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.repository.impl.UserGroupRepositoryImpl;
import ru.mail.dimaushenko.repository.impl.UserRepositoryImpl;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.repository.model.UserGroup;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.AddUserWithUserGroupDTO;
import ru.mail.dimaushenko.service.model.FullUserDTO;
import ru.mail.dimaushenko.service.model.UserDTO;

public class UserServiceImpl implements UserService {

    private static UserService instance = null;

    private UserServiceImpl() {

    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();
    private final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void addEntityWithUserGroup(AddUserWithUserGroupDTO addUserDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertUserDTOToUser(connection, addUserDTO);
                userRepository.addEntity(connection, user);
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
    public List<UserDTO> getAll() {
        List<UserDTO> usersDTO = new ArrayList();

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getAll(connection);
                usersDTO = convertUserToUsersDTO(users);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return usersDTO;
    }

    @Override
    public List<FullUserDTO> getAllFull() {
        List<FullUserDTO> usersDTO = new ArrayList();

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getAll(connection);
                usersDTO = convertUserToFullUsersDTO(users);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return usersDTO;
    }

    @Override
    public boolean removeUser(FullUserDTO userDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertFullUserDTOToUser(userDTO);
                if (userRepository.removeEntity(connection, user)) {
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public void updateUser(FullUserDTO userDTO) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertFullUserDTOToUser(userDTO);
                userRepository.updateEntity(connection, user);
                connection.commit();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private User convertUserDTOToUser(Connection connection, AddUserWithUserGroupDTO addUserDTO) throws SQLException {
        User user = new User();

        user.setUsername(addUserDTO.getUsername());
        user.setPassword(addUserDTO.getPassword());
        user.setAge(addUserDTO.getAge());
        user.setIsActive(addUserDTO.isIsActive());
        UserGroup userGroup = userGroupRepository.getEntityById(connection, addUserDTO.getUserGroupId());
        user.setUserGroup(userGroup);

        return user;
    }

    private List<UserDTO> convertUserToUsersDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();

            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setIsActive(user.isIsActive());
            userDTO.setAge(user.getAge());
            userDTO.setUserGroupName(user.getUserGroup().getName());

            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    private List<FullUserDTO> convertUserToFullUsersDTO(List<User> users) {
        List<FullUserDTO> usersDTO = new ArrayList();
        for (User user : users) {
            FullUserDTO userDTO = new FullUserDTO();

            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setIsActive(user.isIsActive());
            userDTO.setAge(user.getAge());
            userDTO.setUserGroup(user.getUserGroup());

            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    private User convertFullUserDTOToUser(FullUserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAge(userDTO.getAge());
        user.setIsActive(userDTO.isIsActive());
        user.setUserGroup(userDTO.getUserGroup());

        return user;
    }

}
