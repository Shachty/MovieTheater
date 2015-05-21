package cinema.controller;

import cinema.HelloService;
import cinema.jpa.model.dao.impl.MovieDAO;
import cinema.routes.*;
import cinema.service.CoffeeService;
import cinema.service.SocialMediaService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);

    @Autowired
    private MovieDAO theDao;

    @Autowired
    CoffeeService coffeeService;
    @Autowired
    HelloService helloService;


    @Autowired
    CamelContext camelContext;
    @Autowired
    SocialMediaService socialMediaService;

    //injected routes
    @Autowired
    CamelMongoRoute camelMongoRoute;
    @Autowired
    CamelXmlFileToHttpRoute camelXmlFileToHttpRoute;
    @Autowired
    CamelMongoToTwitterRoute camelMongoToTwitterRoute;
    @Autowired
    CamelMongoToFTPRoute camelMongoToFTPRoute;

    @RequestMapping("/start-coffee")
    public String startCoffee() throws Exception {

        logger.info("started coffee example");

        this.coffeeService.startCoffee();

        return "coffee started";
    }
    

    @RequestMapping("/start-routes")
    public String startRoutes(){

        System.out.println(theDao.findAll());

/*

        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("mongoBean", mongoBean);

        CamelContext camelContext = new DefaultCamelContext(simpleRegistry);
*/

        //Mongoroute
        RouteBuilder routeBuilder = camelMongoRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //TwitterRoute
        RouteBuilder routeBuilderTwitter = camelMongoToTwitterRoute;
        try {
            this.camelContext.addRoutes(routeBuilderTwitter);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilderTwitter.toString() + ". Failmessage: " + e.getMessage());
        }

        //FTPRoute
        RouteBuilder routeBuilderFtp = camelMongoToFTPRoute;
        try {
            this.camelContext.addRoutes(routeBuilderFtp);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilderFtp.toString() + ". Failmessage: " + e.getMessage());
        }
/*

        //XMLFileToHttpRoute
        routeBuilder = camelXmlFileToHttpRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }
*/

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

    @RequestMapping("/facebook")
    public String startFacebookEndpoint() {

        logger.info("started Facebook Endpoint");
        return "facebook";
    }

    @RequestMapping(value = "/test", method = RequestMethod.PUT)
    public ResponseEntity<String> testPost(@RequestBody ResponseEntity<String> response){

        System.out.println("###jooooowowow");
        return new ResponseEntity<String>(HttpStatus.OK);

    }




}
