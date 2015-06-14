package cinema.dto;

import cinema.model.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
