package ru.mail.dimaushenko.repository.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.repository.ConnectionPool;

import static ru.mail.dimaushenko.constants.PropertyFiles.HIKARI_HOMEWORK_5_6_PROPERTIES;

public class ConnectionPoolImpl implements ConnectionPool {

    private static ConnectionPool instance = null;
    private HikariDataSource ds = null;
    private final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private ConnectionPoolImpl() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPoolImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            try {
                InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream(HIKARI_HOMEWORK_5_6_PROPERTIES);
                Properties properties = new Properties();
                properties.load(propertiesStream);
                HikariConfig config = new HikariConfig(properties);
                ds = new HikariDataSource(config);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return ds.getConnection();
    }

}
