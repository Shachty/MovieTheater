package cinema.jpa.model;

import javax.persistence.*;

@Entity
@Table(name = "CINEMA_Theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer seatsTotal;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeatsTotal() {
        return this.seatsTotal;
    }

    public void setSeatsTotal(Integer seats) {
        this.seatsTotal = seats;
    }

}
