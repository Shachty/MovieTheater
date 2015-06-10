package cinema.service;

import cinema.jpa.model.Snack;
import cinema.jpa.model.dao.impl.SnackDAO;
import org.apache.camel.Body;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Tyler on 19.05.2015.
 */
@Component
public class SnackService {

    private final Log log = LogFactory.getLog(SnackService.class);

    @Autowired
    SnackDAO snackDao;

    @Transactional
    public void importCsvList(@Body List body) {
        log.debug("importCsv...");

        for(int i = 0; i < body.size(); i++) {
            Snack theSnack = new Snack();
            List bodyLineItem = (List)body.get(i);
            theSnack.setName(new String((String) bodyLineItem.get(0)));
            theSnack.setNumber(new Double((String) bodyLineItem.get(1)));
            snackDao.save(theSnack);
        }
    }

    @Transactional
    public boolean orderSnacks(Map<String, Integer> order) {
        log.debug("order Snack");
        List<Snack> snackList = snackDao.findAll();
        System.out.println(snackList);
        boolean onStock = true;
        for(Snack snack: snackList) {
            if(snack.getNumber()<order.get(snack.getName())){
                 return false;
            }
        }
        for(Snack snack: snackList) {
            snack.setNumber(snack.getNumber()-order.get(snack.getName()));
            snack.setCamelProcessed(false);
            snackDao.save(snack);
        }
        return true;
    }

    @Transactional
    public List<Snack> getSnacks() {
        return snackDao.findAll();
    }
}
