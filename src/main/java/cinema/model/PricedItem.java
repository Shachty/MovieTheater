package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;


public class PricedItem {

    private Item item;
    private Double price;

    @JsonCreator
    public PricedItem(@JsonProperty("item") Item item,
                @JsonProperty("price")Double price
    ) {
        this.item = item;
        this.price = price;
    }


    @XmlElement
    public void setItem(Item item) {
        this.item = item;
    }
    @XmlElement
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }

    public Item getItem() {
        return item;
    }
}
