package cinema.jpa.model;

import javax.persistence.*;

@Entity
public class Theater {

    @Id
    private Double id;
    private String name;
    private Integer seatsTotal;

    public Double getId() {
        return this.id;
    }

    public void setId(Double id) {
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
