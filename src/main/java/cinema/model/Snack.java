package cinema.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Snack {

    private Long id;
    private String name;
    private Double number;

    @JsonCreator
    public Snack(@JsonProperty("id") long id,
                @JsonProperty("name")String name,
                @JsonProperty("number")Double number
    ) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Snack(){}
    @XmlAttribute
    public void setId(Long param) {
        this.id = param;
    }
    @XmlElement
    public void setName(String param) {
        this.name = param;
    }
    @XmlElement
    public void setNumber(Double param) {
        this.number = param;
    }
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getNumber() {
        return this.number;
    }
}
