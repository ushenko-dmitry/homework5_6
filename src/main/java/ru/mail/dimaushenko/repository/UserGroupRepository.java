package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ru.mail.dimaushenko.repository.model.UserGroup;
import ru.mail.dimaushenko.repository.model.UserGroupWithUserAmount;

public interface UserGroupRepository extends GeneralRepository<UserGroup> {

    public List<UserGroupWithUserAmount> getAllUserGroupWithUserAmount(Connection connection) throws SQLException;

}
