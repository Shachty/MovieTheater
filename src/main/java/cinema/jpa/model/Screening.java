package cinema.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 */
@Entity
public class Screening {

    @Id
    private Double id;
    @OneToOne
    private Movie movie;
    @OneToOne
    private Theater theater;

}
