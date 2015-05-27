package cinema.dto;

import cinema.model.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jakob on 25.05.2015.
 */
public class ItemDTO {
    public Item item;


    @JsonCreator
    public ItemDTO(@JsonProperty("Item") Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
