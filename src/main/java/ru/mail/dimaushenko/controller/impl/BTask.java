package ru.mail.dimaushenko.controller.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;

import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER_GROUP;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER_INFORMATION;

public class BTask implements ControllerService {

    private static ControllerService instance = null;

    private BTask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new BTask();
        }
        return instance;
    }

    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PropertyUtil propertyUtil = PropertyUtilConstantsImpl.getInstance();

    @Override
    public void run() {
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_CREATE_TABLE_USER_GROUP))) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_CREATE_TABLE_USER))) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_CREATE_TABLE_USER_INFORMATION))) {
                statement.execute();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}
