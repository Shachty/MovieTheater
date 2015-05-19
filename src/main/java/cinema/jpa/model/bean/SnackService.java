package cinema.jpa.model.bean;

import cinema.jpa.model.Snack;
import cinema.jpa.model.dao.impl.SnackDAO;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theKernel on 19.05.2015.
 */
@Component
public class SnackService {

    @PersistenceContext
    EntityManager theManager;

    @Autowired
    SnackDAO snackDao;

    public void importCsvList(@Header("user") String user, @Body List body, Exchange exchange) {
        System.out.println("importCsv...");

        for(int i = 0; i < body.size(); i++) {
            Snack theSnack = new Snack();
            List bodyLineItem = (List)body.get(i);
            theSnack.setId(new Long((String) bodyLineItem.get(0)));
            theSnack.setName(new String((String) bodyLineItem.get(1)));
            theSnack.setNumber(new Double((String) bodyLineItem.get(2)));

            snackDao.save(theSnack);
        }
    }
}
