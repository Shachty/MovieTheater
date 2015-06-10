package cinema.jpa.model;

import org.apache.camel.component.jpa.Consumed;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Entity
@Table(name = "CINEMA_Enquiry")
@NamedQueries({ @NamedQuery(name = "@HQL_GET_UNPROCESSED_ORDERS",
        query = "from Enquiry o where o.camelProcessed = false") })
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Item> items;
    private boolean camelProcessed = false;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean getCamelProcessed() {
        return this.camelProcessed;
    }

    public void setCamelProcessed(boolean bool) {
        this.camelProcessed = bool;
    }

    public void addItemToOrder(Item item) {
        if(this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

    public void addSnackToOrder(Snack snack, Long quantityToOrder, String supplierId) {
        Item oi = new Item();
        oi.setSnack(snack);
        oi.setOrderSnackNumber(quantityToOrder);
        oi.setSupplierProductId(supplierId);
        this.addItemToOrder(oi);
    }

    @Consumed
    public void markAsProcessed() {
        this.camelProcessed = true;
    }

}
