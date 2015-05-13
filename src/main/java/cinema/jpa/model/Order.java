package cinema.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 */
@Entity
public class Order {

    @Id
    private Double id;
    private String supplierProductId;
    private Snacks snack;
    private Double orderSnackNumber;

}

//order.snack.setNumber = order.snack.getNumber + order.orderSnackNumber;
