package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;


public class Offer {

    private Long id;
    private List<Item> items;
    private Double sumPrice;
    private String companyName;
    private String companyMail;

    @JsonCreator
    public Offer(@JsonProperty("id") long id,
                   @JsonProperty("items")List<Item> items,
                 @JsonProperty("sumPrice") double sumPrice,
                 @JsonProperty("companyName") String companyName,
                @JsonProperty("companyMail") String companyMail
    ) {
        this.id = id;
        this.items = items;
        this.sumPrice = sumPrice;
        this.companyMail = companyMail;
        this.companyName = companyName;
    }


    @XmlAttribute
    public void setId(Long id) {this.id = id;}
    public Long getId() {
        return this.id;
    }


    @XmlElement
    public void setItems(List<Item> items) {this.items = items;}
    public List<Item> getItems() {return this.items; }


    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }
}
