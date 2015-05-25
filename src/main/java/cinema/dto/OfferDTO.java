package cinema.dto;

import cinema.model.Offer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfferDTO {

    private Offer offer;

    @JsonCreator
    public OfferDTO(@JsonProperty("Offer") Offer offer) {
        this.offer = offer;
    }
    public OfferDTO(){}

    @XmlElement
    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    public Offer getOffer() {
        return offer;
    }
}
