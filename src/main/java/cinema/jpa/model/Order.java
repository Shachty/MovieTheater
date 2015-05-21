package cinema.jpa.model;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Entity
@Table(name = "CINEMA_Order")
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

}

//order.snack.setNumber = order.snack.getNumber + order.orderSnackNumber;
