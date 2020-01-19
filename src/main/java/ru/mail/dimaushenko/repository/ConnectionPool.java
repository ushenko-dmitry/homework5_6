package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    public Connection getConnection() throws SQLException;

}
