package cinema.controller;

import cinema.HelloService;
import cinema.jpa.model.dao.impl.MovieDAO;
import cinema.routes.CamelCsvToHibernateRoute;
import cinema.routes.CamelMongoRoute;
import cinema.routes.CamelMongoToFacebookRoute;
import cinema.routes.CamelMongoToTwitterRoute;
import cinema.routes.CamelXmlFileToHttpRoute;
import cinema.service.CoffeeService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@RestController
public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);

    @Autowired
    CoffeeService coffeeService;
    @Autowired
    HelloService helloService;

    @Autowired
    CamelContext camelContext;

    //injected routes
    @Autowired
    CamelMongoRoute camelMongoRoute;
    @Autowired
    CamelXmlFileToHttpRoute camelXmlFileToHttpRoute;
    @Autowired
    CamelCsvToHibernateRoute camelCsvToHibernateRoute;
    @Autowired
    CamelMongoToTwitterRoute camelMongoToTwitterRoute;
    @Autowired
    CamelMongoToFacebookRoute camelMongoToFacebookRoute;

    private int reservationCounter = 1;

    @RequestMapping("/start-routes")
    public String startRoutes(){

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

        //FcaebookRoute
        RouteBuilder routeBuilderFacebook = camelMongoToFacebookRoute;
        try {
            this.camelContext.addRoutes(routeBuilderFacebook);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilderFacebook.toString() + ". Failmessage: " + e.getMessage());
        }

        //camelCsvToHibernateRoute
        routeBuilder = camelCsvToHibernateRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }



        //XMLFileToHttpRoute
        routeBuilder = camelXmlFileToHttpRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
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

    @RequestMapping("/reserve")
    public HttpStatus reserve(@RequestParam String body){

        this.writeFile(body);

        return HttpStatus.OK;

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

    private void writeFile(String content) {
        try {

            File file = new File("src/main/resources/tickets/reservation" + this.reservationCounter + ".json");

                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Wrote reservation " + this.reservationCounter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
