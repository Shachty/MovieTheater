package cinema.jpa.model;

import org.apache.camel.component.jpa.Consumed;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CINEMA_Snack")
@NamedQueries({ @NamedQuery(name = "@HQL_GET_ALL_SNACKS",
        query = "from Snack s where s.camelProcessed = false") })
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double number;
    private boolean camelProcessed = false;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getNumber() {
        return this.number;
    }

    public boolean getCamelProcessed() {
        return this.camelProcessed;
    }

    public void setId(Long param) {
        this.id = param;
    }

    public void setName(String param) {
        this.name = param;
    }

    public void setNumber(Double param) {
        this.number = param;
    }

    public void setCamelProcessed(boolean bool) {
        this.camelProcessed = bool;
    }

    @Consumed
    public void markAsProcessed() {
        this.camelProcessed = true;
    }

}
