package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.AddUserWithUserGroupDTO;
import ru.mail.dimaushenko.service.model.UserDTO;

public interface UserService {

    public void addEntityWithUserGroup(AddUserWithUserGroupDTO addUserDTO);
    
    public List<UserDTO> getAll();
    
}
