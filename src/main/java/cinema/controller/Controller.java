package cinema.controller;

import cinema.FileWriterService;
import cinema.HelloService;
import cinema.TicketReservationService;
import cinema.routes.*;
import cinema.service.CoffeeService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);

    final static private String PATH = "src/main/resources/tickets/ticket";

    @Autowired
    FileWriterService fileWriterService;
    @Autowired
    TicketReservationService ticketReservationService;

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
    CamelTicketToHttpRoute camelTicketToHttpRoute;
    @Autowired
    CamelCsvToHibernateRoute camelCsvToHibernateRoute;
    @Autowired
    CamelHibernateToSupplierRoute camelHibernateToSupplierRoute;
    @Autowired
    CamelMailRoute camelMailRoute;
    @Autowired
    CamelMongoToTwitterRoute camelMongoToTwitterRoute;
    @Autowired
    CamelMongoToFacebookRoute camelMongoToFacebookRoute;
    @Autowired
    CamelSupplierJsonToXmlRoute camelSupplierJsonToXmlRoute;
    @Autowired
    CamelSupplierJsonToCsvRoute camelSupplierJsonToCsvRoute;
    @Autowired
    CamelSupplierJsonToJsonRoute camelSupplierJsonToJsonRoute;
    @Autowired
    CamelSupplierPublishSubscribeRoute camelSupplierPublishSubscribeRoute;
    @Autowired
    ScreeningToMongo screeningToMongo;
    @Autowired
    CamelCheckTicketRoute camelCheckTicketRoute;

    private int reservationCounter = 1;

    @RequestMapping("/start-routes")
    public String startRoutes(){


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

        /*                      TODO einkommentieren
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
        }*/

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

        //CamelSupplierJsonToCsvRoute
        routeBuilder = this.camelSupplierJsonToCsvRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route(supplierJsonToCsv): " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //CamelSupplierPublishSubscribeRoute
        routeBuilder = this.camelSupplierPublishSubscribeRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route(supplierPublish): " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //XMLFileToHttpRoute
        routeBuilder = this.camelTicketToHttpRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        //camelHttpToEmailRoute
        routeBuilder = this.camelMailRoute;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }
        //checkTocketRoute
        routeBuilder = this.camelCheckTicketRoute;
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

    @RequestMapping("/insert-screenings")
    public void insertScreenings(){

        RouteBuilder routeBuilder = this.screeningToMongo;
        try {
            this.camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            logger.error("Could not add route: " + routeBuilder.toString() + ". Failmessage: " + e.getMessage());
        }

        try {
            this.camelContext.removeRoute("ScreeningToMongo");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //start of the action
        try {
            camelContext.start();
            Thread.sleep(10 * 1 * 1000);
            camelContext.stop();
        } catch (Exception e) {
            logger.error("Fail. Message: " + e.getMessage());
        }


    }

    @RequestMapping("/do-reservation")
    public String doReservation(@RequestParam("firstname") String firstName,
                                @RequestParam("lastname") String lastName,
                                @RequestParam("numberofpersons") int numberOfPersons,
                                @RequestParam("theaterroom") int theaterRoom,
                                @RequestParam("moviename") String movieName,
                                @RequestParam("time") String time,
                                @RequestParam("e-mail") String mail){


        this.ticketReservationService.createReservation(
                firstName,
                lastName,
                movieName,
                mail,
                theaterRoom,
                numberOfPersons,
                time);


        return "We received your reservation. You will get a confirmation or declining message on your provided e-mail adress soon.";
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
