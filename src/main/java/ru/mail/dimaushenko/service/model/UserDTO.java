package ru.mail.dimaushenko.service.model;

public class UserDTO {

    private String username;
    private String password;
    private boolean isActive;
    private int age;
    private String userGroupName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", password=" + password + ", isActive=" + isActive + ", age=" + age + ", userGroupName=" + userGroupName + '}';
    }

}
