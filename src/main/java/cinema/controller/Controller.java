package cinema.controller;

import cinema.HelloService;
import cinema.service.CoffeeService;
import cinema.service.SocialMediaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel on 06.05.2015.
 */

@RestController
public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);

    @Autowired
    CoffeeService coffeeService;

    @Autowired
    HelloService helloService;

    @Autowired
    SocialMediaService socialMediaService;


    @RequestMapping("/start-coffee")
    public String startCoffee() throws Exception {

        logger.info("started coffee example");

        this.coffeeService.startCoffee();

        return "coffee started";
    }

    @RequestMapping("/hello")
    public String testIfAsync() {

        logger.info("testIfAsync");

        return "yess";
    }

    @RequestMapping("/facebook")
    public String startFacebookEndpoint() {

        logger.info("started Facebook Endpoint");
        return "facebook";
    }


}
