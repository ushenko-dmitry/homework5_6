package ru.mail.dimaushenko.service.model;

public class UserGroupDTO {

    private String name;
    private int amountOfUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountOfUser() {
        return amountOfUser;
    }

    public void setAmountOfUser(int amountofUser) {
        this.amountOfUser = amountofUser;
    }

    @Override
    public String toString() {
        return "UserGroupDTO{" + "name=" + name + ", amountOfUser=" + amountOfUser + '}';
    }

}
