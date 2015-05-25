package cinema.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Enquiry {

    private Long id;
    private List<Item> items;

    @JsonCreator
    public Enquiry(@JsonProperty("id") long id,
                  @JsonProperty("items")List<Item> items
        ) {
        this.id = id;
        this.items = items;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {return this.items; }
    public void setItems(List<Item> items) {this.items = items;}


    public void addItemToOffer(Item item, double price) {
        if(this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

}