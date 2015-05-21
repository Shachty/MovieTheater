package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Ticket {

    private boolean isPurchased;
    private BigDecimal pricePerTicket;
    private Screening screening;
    private long customerId;
    private String mail;

    @JsonCreator
    public Ticket(@JsonProperty("isReservation") boolean isPurchased,
                  @JsonProperty("overallPrice")BigDecimal pricePerTicket,
                  @JsonProperty("screening")Screening screening,
                  @JsonProperty("customerId")long customerId,
                  @JsonProperty("mail") String mail) {
        this.isPurchased = isPurchased;
        this.pricePerTicket = pricePerTicket;
        this.screening = screening;
        this.customerId = customerId;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public BigDecimal getPricePerTicket() {
        return pricePerTicket;
    }

    public void setPricePerTicket(BigDecimal pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
