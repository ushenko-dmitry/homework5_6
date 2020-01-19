package ru.mail.dimaushenko;

import ru.mail.dimaushenko.controller.impl.CTask;
import ru.mail.dimaushenko.controller.impl.DTask;
import ru.mail.dimaushenko.controller.impl.ETask;
import ru.mail.dimaushenko.controller.impl.FTask;
import ru.mail.dimaushenko.controller.impl.GTask;
import ru.mail.dimaushenko.controller.impl.HTask;
import ru.mail.dimaushenko.utils.PrepareDataBase;

public class App {

    public static void main(String[] args) {
        PrepareDataBase.getInstance().prepareDB();
        CTask.getInstance().run();
        DTask.getInstance().run();
        ETask.getInstance().run();
        FTask.getInstance().run();
        GTask.getInstance().run();
        HTask.getInstance().run();
    }
}
