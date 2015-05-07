package cinema.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel on 07.05.2015.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    final static Logger logger = Logger.getLogger(CustomerController.class);

    @RequestMapping("/start-reservation")
    public String startReservation(){
        logger.info("started reservation");




        return "reservation started";
    }
}
