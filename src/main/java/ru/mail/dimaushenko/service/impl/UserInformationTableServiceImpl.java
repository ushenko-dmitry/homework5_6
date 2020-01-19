package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.service.TableService;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER_INFORMATION;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_DROP_TABLE_USER_INFORMATION;

public class UserInformationTableServiceImpl implements TableService {

    private static TableService instance = null;

    private UserInformationTableServiceImpl() {
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new UserInformationTableServiceImpl();
        }
        return instance;
    }

    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PropertyUtil propertyUtil = PropertyUtilConstantsImpl.getInstance();

    @Override
    public boolean createTable() {
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_CREATE_TABLE_USER_INFORMATION))) {
                statement.execute();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public boolean removeTable() {
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_DROP_TABLE_USER_INFORMATION))) {
                statement.execute();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }

}
