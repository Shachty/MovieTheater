package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;


public class Offer {

    private Long id;
    private List<Item> items;

    @JsonCreator
    public Offer(@JsonProperty("id") long id,
                   @JsonProperty("items")List<Item> items
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
    public void setItems(List<Item> items) {this.items = items;}
    public List<Item> getItems() {return this.items; }

}
