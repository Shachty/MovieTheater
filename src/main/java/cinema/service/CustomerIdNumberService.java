package cinema.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Daniel on 28.05.2015.
 */
@Service
public class CustomerIdNumberService {

    private long customerNumber;

    @PostConstruct
    public void setUp(){
        customerNumber = 1;
    }

   public long getNextCustomerNumber(){
        return customerNumber++;
    }
}
