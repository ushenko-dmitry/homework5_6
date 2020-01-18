package ru.mail.dimaushenko;

//import com.zaxxer.hikari.HikariConfig;
import ru.mail.dimaushenko.controller.ControllerService;
import ru.mail.dimaushenko.controller.impl.FiveTask;
import ru.mail.dimaushenko.controller.impl.FourTask;
import ru.mail.dimaushenko.controller.impl.ThirdTask;
import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.impl.UserRepositoryImpl;

public class App 
{
    public static void main( String[] args )
    {
        UserRepository repository = UserRepositoryImpl.getInstance();
//        ThirdTask.getInstance().run();
//        ControllerService controllerService =  FourTask.getInstance();
//        controllerService.run();
        FiveTask.getInstance().run();
        
        
    }
}
