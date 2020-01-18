package ru.mail.dimaushenko.repository.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ru.mail.dimaushenko.repository.ConnectionPool;

import static ru.mail.dimaushenko.constants.PropertyFiles.HIKARI_HOMEWORK_5_6_PROPERTIES;
import static ru.mail.dimaushenko.constants.PropertyFiles.HIKARI_MYSQL_PROPERTIES;
import ru.mail.dimaushenko.utils.PropertyUtil;
import ru.mail.dimaushenko.utils.impl.PropertyUtilTaskImpl;

public class ConnectionPoolImpl implements ConnectionPool {

    private static ConnectionPool instance = null;
//    private HikariDataSource dsToHW = null;
//    private HikariDataSource dsToMYSQL = null;

    private Connection connectionHW = null;
    private Connection connectionMysql = null;

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
//        if (dsToHW == null){
//            HikariConfig config = new HikariConfig(HIKARI_HOMEWORK_5_6_PROPERTIES);
//            dsToHW = new HikariDataSource(config);
//        }
//        return dsToHW.getConnection();

        String url = "jdbc:mysql://localhost:3306/jd2_homework5_6?useSSL=false";
        String user = "dmitry";
        String password = "154789";
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Connection getConnectionToMainDB() throws SQLException {
//        if (dsToMYSQL == null){
//            HikariConfig config = new HikariConfig(HIKARI_MYSQL_PROPERTIES);
//            dsToMYSQL = new HikariDataSource(config);
//        }
//        return dsToMYSQL.getConnection();

        String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
        String user = "dmitry";
        String password = "154789";
        return DriverManager.getConnection(url, user, password);
    }

}
