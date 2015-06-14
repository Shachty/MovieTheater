package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Item {

    private Long id;
    private String supplierProductId;
    private Snack snack;
    private Long orderSnackNumber;
    private Double price;

    @JsonCreator
    public Item(@JsonProperty("id") long id,
                @JsonProperty("supplierProductId")String supplierProductId,
                @JsonProperty("snack")Snack snack,
                @JsonProperty("orderSnackNumber")Long orderSnackNumber,
                @JsonProperty("price")Double price
    ) {
        this.id = id;
        this.supplierProductId = supplierProductId;
        this.snack = snack;
        this.orderSnackNumber = orderSnackNumber;
        this.price = price;
    }

    public Item(){}

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }
    @XmlAttribute
    public void setSupplierProductId(String id) {
        this.supplierProductId = id;
    }
    @XmlElement
    public void setSnack(Snack snack) {
        this.snack = snack;
    }
    @XmlElement
    public void setOrderSnackNumber(Long nr) {
        this.orderSnackNumber = nr;
    }

    public Long getId() {
        return this.id;
    }

    public String getSupplierProductId() {
        return this.supplierProductId;
    }

    public Snack getSnack() {
        return this.snack;
    }

    public Long getOrderSnackNumber() {
        return this.orderSnackNumber;
    }

    @XmlElement
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }
}
