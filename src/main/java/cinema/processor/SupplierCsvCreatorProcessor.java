package cinema.processor;

import cinema.dto.OfferDTO;
import cinema.model.Offer;
import cinema.model.Item;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Asus on 25.05.2015.
 */
public class SupplierCsvCreatorProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        OfferDTO offerDTO = (OfferDTO) exchange.getIn().getBody();
        Offer offer = offerDTO.getOffer();
        Iterator<Item> i = offer.getItems().iterator();
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        map = new HashMap<>();
        map.put("offerId", "offerId");
        map.put("itemId", "itemId");
        map.put("price", "price");
        map.put("supplierProductId", "supplierProductId");
        map.put("snackId", "snackId");
        map.put("orderSnackNumber", "orderSnackNumber");
        map.put("name", "name");
        map.put("number", "number");
        map.put("sumprice", "sumprice");
        map.put("companyName", "companyname");
        map.put("companyMail", "companyemail");
        list.add(map);
        while (i.hasNext()) {
            Item item = i.next();
            map = new HashMap<>();
            map.put("offerId", offer.getId().toString());
            map.put("itemId", item.getId().toString());
            map.put("price", item.getPrice().toString());
            map.put("supplierProductId", item.getSupplierProductId().toString());
            map.put("snackId", item.getSnack().getId().toString());
            map.put("orderSnackNumber", item.getOrderSnackNumber().toString());
            map.put("name", item.getSnack().getName().toString());
            map.put("number", item.getSnack().getNumber().toString());
            map.put("sumprice", offer.getSumPrice().toString());
            map.put("companyName", offer.getCompanyName());
            map.put("companyMail", offer.getCompanyMail());
            list.add(map);
        }
        exchange.getIn().setBody(list);
        exchange.getIn().setHeader("offerId", offer.getId());
    }
}

