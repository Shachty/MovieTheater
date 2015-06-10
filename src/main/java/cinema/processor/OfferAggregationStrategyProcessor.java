package cinema.processor;

import cinema.dto.OfferDTO;
import cinema.model.Offer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Created by Jakob on 08.06.2015.
 */
public class OfferAggregationStrategyProcessor implements AggregationStrategy {


    public OfferAggregationStrategyProcessor() {
        super();
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Message newIn = newExchange.getIn();
        OfferDTO newOfferDTO = (OfferDTO) newIn.getBody();
        Offer newOffer = newOfferDTO.getOffer();


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


