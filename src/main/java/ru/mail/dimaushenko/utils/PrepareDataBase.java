package ru.mail.dimaushenko.utils;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.repository.impl.ConnectionPoolImpl;
import ru.mail.dimaushenko.utils.impl.PropertyUtilConstantsImpl;
import ru.mail.dimaushenko.repository.impl.ConnectionPoolMysqlImpl;

import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER_GROUP;
import static ru.mail.dimaushenko.constants.PropertyConstants.SQL_REQUEST_CREATE_TABLE_USER_INFORMATION;

public class PrepareDataBase {

    private static PrepareDataBase instance = null;

    private PrepareDataBase() {
    }

    public static PrepareDataBase getInstance() {
        if (instance == null) {
            instance = new PrepareDataBase();
        }
        return instance;
    }

    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PropertyUtil propertyUtil = PropertyUtilConstantsImpl.getInstance();

    private final String DATABASE_NAME = "jd2_homework5_6";

    private final String SQL_REQUEST_SHOW_DB = "SHOW DATABASES;";
    private final String SQL_REQUEST_DROP_DB = "DROP DATABASE " + DATABASE_NAME + ";";
    private final String SQL_REQUEST_CREATE_DB = "CREATE DATABASE " + DATABASE_NAME + ";";

    private final String SQL_COLUMN_DATABASE = "Database";

    public void prepareDB() {
        boolean isDBFound = false;

        try (
                Connection connection = ConnectionPoolMysqlImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_SHOW_DB);
                ResultSet result = statement.executeQuery();) {
            while (result.next()) {
                if (result.getString(SQL_COLUMN_DATABASE).equals(DATABASE_NAME)) {
                    isDBFound = true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        if (isDBFound) {
            try (
                    Connection connection = ConnectionPoolMysqlImpl.getInstance().getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_DROP_DB);) {
                statement.execute();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(PrepareDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try (
                Connection connection = ConnectionPoolMysqlImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_CREATE_DB);) {
            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

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
