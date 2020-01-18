package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.mail.dimaushenko.repository.UserGroupRepository;
import ru.mail.dimaushenko.repository.model.UserGroup;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_INSERT_USER_GROUP;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_SELECT_ALL_USER_GROUPS;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_GET_USER_GROUP_BY_ID;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_COLUMN_USER_GROUP_ID;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_COLUMN_USER_GROUP_NAME;

public class UserGroupRepositoryImpl extends GeneralRepositoryImpl<UserGroup> implements UserGroupRepository {

    private static UserGroupRepository instance = null;

    private UserGroupRepositoryImpl() {
    }

    public static UserGroupRepository getInstance() {
        if (instance == null) {
            instance = new UserGroupRepositoryImpl();
        }
        return instance;
    }

    private PropertyUtil propertyUtil = PropertyUtilConstantsImpl.getInstance();

    @Override
    public void addEntity(Connection connection, UserGroup t) throws SQLException {
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_INSERT_USER_GROUP))) {
            statement.setString(1, t.getName());
            statement.execute();
        }
    }

    @Override
    public UserGroup getEntityById(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_GET_USER_GROUP_BY_ID))) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return getUserGroup(result);
                }
            }
        }
        return null;
    }

    @Override
    public List<UserGroup> getAll(Connection connection) throws SQLException {
        List<UserGroup> userGroups = new ArrayList();
        try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_SELECT_ALL_USER_GROUPS))) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    userGroups.add(getUserGroup(result));
                }
            }
        }
        return userGroups;
    }

    private UserGroup getUserGroup(ResultSet result) throws SQLException {
        UserGroup userGroup = new UserGroup();

        userGroup.setId(result.getInt(propertyUtil.getProperty(SQL_COLUMN_USER_GROUP_ID)));
        userGroup.setName(result.getString(propertyUtil.getProperty(SQL_COLUMN_USER_GROUP_NAME)));

        return userGroup;
    }

}
