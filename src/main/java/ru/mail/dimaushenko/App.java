package ru.mail.dimaushenko;

import com.zaxxer.hikari.HikariConfig;
import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.controller.impl.ThirdTask;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

//        HikariConfig config = new HikariConfig();
        ControllerService controllerService = new ThirdTask();
        controllerService.run();
//        config.addDataSourceProperty(propertyName, args);
    }
}
