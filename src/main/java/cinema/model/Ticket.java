package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {

    private boolean isReservation;
    private BigDecimal overallPrice;
    private Screening screening;
    private long customerId;
    private String mail;
    private Date date;

    @JsonCreator
    public Ticket(@JsonProperty("isReservation") boolean isReservation,
                  @JsonProperty("overallPrice")BigDecimal overallPrice,
                  @JsonProperty("screening")Screening screening,
                  @JsonProperty("customerId")long customerId,
                  @JsonProperty("mail") String mail
                /*  @JsonProperty("date") Date date*/) {
        this.isReservation = isReservation;
        this.overallPrice = overallPrice;
        this.screening = screening;
        this.customerId = customerId;
        this.mail = mail;
    //    this.date = date;


    }

    public boolean isReservation() {
        return isReservation;
    }

    public void setIsReservation(boolean isReservation) {
        this.isReservation = isReservation;
    }

    public BigDecimal getOverallPrice() {
        return overallPrice;
    }

    public void setOverallPrice(BigDecimal overallPrice) {
        this.overallPrice = overallPrice;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
