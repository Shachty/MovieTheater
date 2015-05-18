package cinema.jpa.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 */
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String supplierProductId;
    @ManyToOne
    private Snack snack;
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
