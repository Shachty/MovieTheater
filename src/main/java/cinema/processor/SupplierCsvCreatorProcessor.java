package cinema.processor;

import cinema.dto.OfferDTO;
import cinema.model.Offer;
import cinema.model.PricedItem;
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
        Iterator<PricedItem> i = offer.getItems().iterator();
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
        list.add(map);
        while (i.hasNext()) {
            PricedItem item = i.next();
            map = new HashMap<>();
            map.put("offerId", offer.getId().toString());
            map.put("itemId", item.getItem().getId().toString());
            map.put("price", item.getPrice().toString());
            map.put("supplierProductId", item.getItem().getSupplierProductId().toString());
            map.put("snackId", item.getItem().getSnack().getId().toString());
            map.put("orderSnackNumber", item.getItem().getOrderSnackNumber().toString());
            map.put("name", item.getItem().getSnack().getName().toString());
            map.put("number", item.getItem().getSnack().getNumber().toString());
            list.add(map);
        }
        exchange.getIn().setBody(list);
        exchange.getIn().setHeader("offerId", offer.getId());
    }
}

