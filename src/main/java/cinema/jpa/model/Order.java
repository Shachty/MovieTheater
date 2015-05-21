package cinema.jpa.model;

import org.apache.camel.component.jpa.Consumed;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Entity
@Table(name = "CINEMA_Order")
@NamedQueries({ @NamedQuery(name = "@HQL_GET_UNPROCESSED_ORDERS",
        query = "from Order o where o.camelProcessed = false") })
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderItem> items;
    private boolean camelProcessed = false;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public boolean getCamelProcessed() {
        return this.camelProcessed;
    }

    public void setCamelProcessed(boolean bool) {
        this.camelProcessed = bool;
    }

    public void addItemToOrder(OrderItem item) {
        if(this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

    public void addSnackToOrder(Snack snack, Long quantityToOrder, String supplierId) {
        OrderItem oi = new OrderItem();
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

//order.snack.setNumber = order.snack.getNumber + order.orderSnackNumber;
