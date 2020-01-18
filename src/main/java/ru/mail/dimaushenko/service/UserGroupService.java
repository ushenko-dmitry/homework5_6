package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.AddUserGroupDTO;
import ru.mail.dimaushenko.service.model.UserGroupIdDTO;

public interface UserGroupService {

    public void addUserGroup(AddUserGroupDTO userGroupDTO);

    public List<UserGroupIdDTO> getAllUserGroupId();

}
