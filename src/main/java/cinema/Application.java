package cinema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");



    }
}
