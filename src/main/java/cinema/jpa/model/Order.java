package cinema.jpa.model;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 */
@Entity
public class Order {

    @Id
    private Double id;
    private String supplierProductId;
    @ManyToOne
    private Snack snack;
    private Double orderSnackNumber;

}

//order.snack.setNumber = order.snack.getNumber + order.orderSnackNumber;
