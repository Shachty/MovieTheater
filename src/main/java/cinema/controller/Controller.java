package cinema.controller;

import cinema.service.FileWriterService;
import cinema.jpa.model.Snack;
import cinema.service.SnackService;
import cinema.routes.*;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);

    final static private String PATH = "src/main/resources/tickets/ticket";

    @Autowired
    FileWriterService fileWriterService;

    @Autowired
    SnackService snackService;

    @Autowired
    CamelContext camelContext;

    @Autowired
    ApplicationContext appContext;

    //injected routes
    @Autowired
    ScreeningToMongo screeningToMongo;

    private int reservationCounter = 1;

    @RequestMapping("getSnacks")
    public void getSnacks() {
        List<Snack> theSnacks = snackService.getSnacks();
        for(Snack snk : theSnacks) {
            logger.info(snk);
        }

        SpringApplication.exit(this.appContext);

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
