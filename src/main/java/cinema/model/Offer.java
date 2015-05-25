package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Offer {

    private Long id;
    private List<PricedItem> items;

    @JsonCreator
    public Offer(@JsonProperty("id") long id,
                   @JsonProperty("items")List<PricedItem> items
    ) {
        this.id = id;
        this.items = items;
    }


    @XmlAttribute
    public void setId(Long id) {this.id = id;}
    public Long getId() {
        return this.id;
    }

    @XmlElement
    public void setItems(List<PricedItem> items) {this.items = items;}
    public List<PricedItem> getItems() {return this.items; }

}
