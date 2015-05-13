package cinema.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 */
@Entity
public class Movie {

    @Id
    private Double id;
    private String name;

}
