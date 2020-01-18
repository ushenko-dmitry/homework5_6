package ru.mail.dimaushenko.utils;

import java.util.List;
import java.util.Random;
import ru.mail.dimaushenko.service.model.UserGroupIdDTO;

public class RandomUtil {

    private static RandomUtil instance = null;

    private RandomUtil() {
    }

    public static RandomUtil getInstance() {
        if (instance == null) {
            instance = new RandomUtil();
        }
        return instance;
    }

    private final Random rand = new Random();

    public int getInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    public int getInt(List<UserGroupIdDTO> userGroups) {
        return userGroups.get(rand.nextInt(userGroups.size())).getId();
    }
}
