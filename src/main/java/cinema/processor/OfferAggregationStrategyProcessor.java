package cinema.processor;

import cinema.dto.OfferDTO;
import cinema.model.Offer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.logging.Logger;

public class OfferAggregationStrategyProcessor implements AggregationStrategy {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    public OfferAggregationStrategyProcessor() {
        super();
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Message newIn = newExchange.getIn();
        OfferDTO newOfferDTO = (OfferDTO) newIn.getBody();
        Offer newOffer = newOfferDTO.getOffer();
        String message = "load offer -> id:" + newOffer.getId() +" name: " + newOffer.getCompanyName() +" mail: " + newOffer.getCompanyMail();
        message += String.format("price: %.2f" , newOffer.getSumPrice());
        logger.info(message);

        if (oldExchange != null) {
            Message oldIn = oldExchange.getIn();
            OfferDTO oldOfferDTO = (OfferDTO) oldIn.getBody();
            Offer oldOffer = oldOfferDTO.getOffer();
            if (newOffer.getSumPrice() > oldOffer.getSumPrice()) {
                return oldExchange;
            }
        }

        return newExchange;
    }
}


