package cinema.controller;

import cinema.FileWriterService;
import cinema.HelloService;
import cinema.StartReservationService;
import cinema.jpa.model.dao.impl.MovieDAO;
import cinema.routes.*;
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

    final static private String PATH = "src/main/resources/tickets/reservation";

    @Autowired
    FileWriterService fileWriterService;
    @Autowired
    StartReservationService startReservationService;

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
    CamelHibernateToSupplierRoute camelHibernateToSupplierRoute;
    @Autowired
    CamelHttpToEmailRoute camelHttpToEmailRoute;
    @Autowired
    CamelMongoToTwitterRoute camelMongoToTwitterRoute;
    @Autowired
    CamelMongoToFacebookRoute camelMongoToFacebookRoute;
    @Autowired
    CamelSupplierJsonToXmlRoute camelSupplierJsonToXmlRoute;
    @Autowired
    CamelSupplierJsonToJsonRoute camelSupplierJsonToJsonRoute;

    private int reservationCounter = 1;

    @RequestMapping("/start-routes")
    public String startRoutes(){

/*
        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("mongoBean", mongoBean);

        CamelContext camelContext = new DefaultCamelContext(simpleRegistry);
*/

        //starts the reservation async
        this.startReservationService.startReservations();

        //Mongoroute
        RouteBuilder routeBuilder = this.camelMongoRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //TwitterRoute
        routeBuilder = this.camelMongoToTwitterRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //FcaebookRoute
        routeBuilder = this.camelMongoToFacebookRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //camelCsvToHibernateRoute
        routeBuilder = this.camelCsvToHibernateRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //CamelHibernateToSupplierRoute
        routeBuilder = this.camelHibernateToSupplierRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //CamelSupplierJsonToXmlRoute
        routeBuilder = this.camelSupplierJsonToXmlRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route(supplierJsonToXml): " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //CamelSupplierJsonToJsonRoute
        routeBuilder = this.camelSupplierJsonToJsonRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route(supplierJsonToJson): " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }


        //XMLFileToHttpRoute
        routeBuilder = this.camelXmlFileToHttpRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //camelHttpToEmailRoute
        routeBuilder = this.camelHttpToEmailRoute;
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

        this.fileWriterService.writeFile(this.PATH,body,this.reservationCounter,".json");
        this.reservationCounter++;
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






}
