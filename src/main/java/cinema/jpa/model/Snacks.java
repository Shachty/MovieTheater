package cinema.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 */
@Entity
public class Snacks {

    @Id
    private Double id;
    private String name;
    private Double number;
    @OneToMany
    private List<Order> orders;

}
