package cinema.jpa.model;

import javax.persistence.*;
import java.util.List;

/**
 */
@Entity
@Table(name = "CINEMA_Snack")
@NamedQueries({ @NamedQuery(name = "@HQL_GET_ALL_SNACKS",
        query = "from Snack s") })
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double number;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getNumber() {
        return this.number;
    }

//    public List<Order> getOrders() {
//        return this.orders;
//    }

    public void setId(Long param) {
        this.id = param;
    }

    public void setName(String param) {
        this.name = param;
    }

    public void setNumber(Double param) {
        this.number = param;
    }

//    public void setOrders(List<Order> param) {
//        this.orders = param;
//    }

}
