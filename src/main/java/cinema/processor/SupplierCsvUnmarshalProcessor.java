package cinema.processor;

import cinema.dto.OfferDTO;
import cinema.model.Item;
import cinema.model.Offer;
import cinema.model.Snack;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;


public class SupplierCsvUnmarshalProcessor  implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<List<String>> list = (List<List<String>>) exchange.getIn().getBody();

        Iterator<List<String>> i = list.iterator();
        Map<String,Integer> indexMap = new HashMap<>();
        if(i.hasNext()){
            List<String> csvRow = i.next();
            Iterator<String> i2 = csvRow.iterator();
            int index = 0;
            while(i2.hasNext()){
                indexMap.put((String)i2.next(),index);
                index+=1;
            }
        }
        OfferDTO offerDTO = new OfferDTO();
        int offerId = 0;
        double sumprice = 0;
        String companyName="";
        String companyMail ="";
        List<Item> itemList = new ArrayList<Item>();
        while(i.hasNext()){
            List<String> csvRow = (List<String>)i.next();
            Item item = new Item(Long.parseLong(csvRow.get(indexMap.get("itemId"))),csvRow.get(indexMap.get("supplierProductId")),new Snack(Long.parseLong(csvRow.get(indexMap.get("snackId"))), csvRow.get(indexMap.get("name")),Double.parseDouble(csvRow.get(indexMap.get("number")))),Long.parseLong(csvRow.get(indexMap.get("orderSnackNumber"))),Double.parseDouble(csvRow.get(indexMap.get("price"))));
            itemList.add(item);
            offerId = Integer.parseInt(csvRow.get(indexMap.get("itemId")));
            sumprice = Double.parseDouble(csvRow.get(indexMap.get("sumprice")));
            companyName = csvRow.get(indexMap.get("companyName"));
            companyMail = csvRow.get(indexMap.get("companyMail"));
        }
        Offer offer = new Offer(offerId,itemList, sumprice, companyName, companyMail);

        offerDTO.setOffer(offer);
        exchange.getIn().setBody(offerDTO);
        exchange.getIn().setHeader("offerId", offer.getId());
    }
}
