package ru.mail.dimaushenko.controller.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;

import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_DROP_TABLE_USER;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_DROP_TABLE_USER_GROUP;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_DROP_TABLE_USER_INFORMATION;

public class ATask implements ControllerService {

    private static ControllerService instance = null;

    private ATask() {
    }

    public static ControllerService getInstance() {
        if (instance == null) {
            instance = new ATask();
        }
        return instance;
    }

    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PropertyUtil propertyUtil = PropertyUtilConstantsImpl.getInstance();

    @Override
    public void run() {
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_DROP_TABLE_USER_INFORMATION))) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_DROP_TABLE_USER))) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareCall(propertyUtil.getProperty(SQL_REQUEST_DROP_TABLE_USER_GROUP))) {
                statement.execute();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}
