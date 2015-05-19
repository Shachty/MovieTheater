package cinema.jpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Entity
@Table(name = "CINEMA_OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * @var String supplierProductId their snack id, free text
     */
    private String supplierProductId;
    /**
     * @var Snack snack link to our Snack object
     */
    @OneToOne
    private Snack snack;
    /**
     * @var Long orderSnackNumber how many of the snack should be ordered with the supplier..?
     */
    private Long orderSnackNumber;

    public Long getId() {
        return this.id;
    }

    public String getSupplierProductId() {
        return this.supplierProductId;
    }

    public Snack getSnack() {
        return this.snack;
    }

    public Long getOrderSnackNumber() {
        return this.orderSnackNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSupplierProductId(String id) {
        this.supplierProductId = id;
    }

    public void setSnack(Snack snack) {
        this.snack = snack;
    }

    public void setOrderSnackNumber(Long nr) {
        this.orderSnackNumber = nr;
    }
}

//order.snack.setNumber = order.snack.getNumber + order.orderSnackNumber;
