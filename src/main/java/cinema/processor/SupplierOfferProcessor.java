package cinema.processor;

import cinema.dto.EnquiryDTO;
import cinema.dto.OfferDTO;
import cinema.model.Enquiry;
import cinema.model.Item;
import cinema.model.Offer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SupplierOfferProcessor implements Processor{

    private static int counter;
    private ArrayList<String> companyNameList;
    private ArrayList<String> companyMailList;
    private int modcount;

    public SupplierOfferProcessor(){
        super();
        this.counter = 0;

        companyNameList = new ArrayList<String>();
        companyNameList.add(0,"Hofer");
        companyNameList.add(1,"Billa");
        companyNameList.add(2,"Metro");
        companyNameList.add(3,"Spar");
        companyNameList.add(4,"Lidl");
        companyNameList.add(5,"Zielpunkt");
        companyNameList.add(6,"Nah & Frisch");
        companyNameList.add(7,"Kastner");
        companyNameList.add(8,"Jhony's");
        companyNameList.add(9,"Supplier Co.");

        companyMailList = new ArrayList<String>();
        companyMailList.add(0,"jakob.perger@gmail.com");
        companyMailList.add(1,"e1126650@student.tuwien.ac.at");
        companyMailList.add(2,"e1126058@student.tuwien.ac.at");
        companyMailList.add(3,"siemayr.anita@gmx.at");
        companyMailList.add(4,"s.scheickl@gmx.net");
        companyMailList.add(5,"e1127548@student.tuwien.ac.at");
        companyMailList.add(6,"daniel.shatkin@gmail.com");
        companyMailList.add(7,"e1127230@tuwien.ac.at");
        companyMailList.add(8,"e0827919@student.tuwien.ac.at");
        companyMailList.add(9,"s.scheickl@gmx.net");

    }

    @Override
    public void process(Exchange exchange) throws Exception {
        EnquiryDTO EnquiryDTO = (EnquiryDTO) exchange.getIn().getBody();
        Enquiry enquiry = EnquiryDTO.getEnquiry();

        Double price = getRandomPrice();
        Double sumPrice = 0.0;

        List<Item> list = new ArrayList<Item>();
        Iterator i = enquiry.getItems().iterator();

        while (i.hasNext()) {
            Item item = (Item) i.next();
            price = getRandomPrice();
            item.setPrice(price);
            list.add(item);
            sumPrice += price*item.getOrderSnackNumber();
        }
        modcount = counter % 10;
        counter ++;
        Offer offer = new Offer(enquiry.getId(), list, sumPrice,companyNameList.get(modcount),companyMailList.get(modcount));
        exchange.getIn().setBody(new OfferDTO(offer), OfferDTO.class);
        String filename = (String)exchange.getIn().getHeader("CamelFileName");
        exchange.getIn().setHeader("CamelFileName", filename.replace(".json",""));
    }

    private double getRandomPrice(){
        Random random = new Random();
        Double upperLimit = 3.0;
        Double lowerLimit = 1.0;
        return random.nextDouble()*(upperLimit-lowerLimit)+lowerLimit;
    }
}
