package cinema.jpa.model;

import javax.persistence.*;

@Entity
public class Theater {

    @Id
    private Double id;

    public Double getId() {
        return this.id;
    }

    public void setId(Double id) {
        this.id = id;
    };

}
