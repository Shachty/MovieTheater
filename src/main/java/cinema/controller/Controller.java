package cinema.controller;

import cinema.HelloService;
import cinema.routes.CamelMongoRoute;
import cinema.service.CoffeeService;
import com.mongodb.Mongo;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    //beans for selfregistering
    @Autowired
    Mongo mongoBean;

    //injected routes
    @Autowired
    CamelMongoRoute camelMongoRoute;

    @RequestMapping("/start-coffee")
    public String startCoffee() throws Exception {

        logger.info("started coffee example");

        this.coffeeService.startCoffee();

        return "coffee started";
    }

    @RequestMapping("/start-routes")
    public String startRoutes(){

        //If camelContext messed with the Spring registrated beans, register id yourself in the simpleRegistry
        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("mongoBean", mongoBean);

        CamelContext camelContext = new DefaultCamelContext(simpleRegistry);

        //Mongoroute
        RouteBuilder routeBuilder = camelMongoRoute;
        try {
            camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //add your routes right here


        //start of the action
        try {
            camelContext.start();
            Thread.sleep(60 * 5 * 1000);
            camelContext.stop();
        } catch (Exception e) {
            logger.error("Fail. Message: " + e.getMessage());
        }

        return "Routes ended at: " + new Date().toString();
    }


    @RequestMapping("/hello")
    public String testIfAsync() {

        logger.info("testIfAsync");

        return "yess";
    }

    @RequestMapping(value = "/test", method = RequestMethod.PUT)
    public ResponseEntity<String> testPost(@RequestBody ResponseEntity<String> response){

        System.out.println("###jooooowowow");
        return new ResponseEntity<String>(HttpStatus.OK);

    }




}
