package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    public void addEntity(Connection connection, T t) throws SQLException;

    public T getEntityById(Connection connection, int id) throws SQLException;

    public List<T> getAll(Connection connection) throws SQLException;

    public boolean removeEntity(Connection connection, T t) throws SQLException;

    public void updateEntity(Connection connection, T t) throws SQLException;

}
