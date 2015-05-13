package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Daniel on 13.05.2015.
 */
public class Ticket {

    private boolean isReservation;
    private BigDecimal overallPrice;
    private Screening screening;
    private long customerId;

    @JsonCreator
    public Ticket(@JsonProperty("isReservation") boolean isReservation,
                  @JsonProperty("overallPrice")BigDecimal overallPrice,
                  @JsonProperty("screening")Screening screening,
                  @JsonProperty("customerId")long customerId) {
        this.isReservation = isReservation;
        this.overallPrice = overallPrice;
        this.screening = screening;
        this.customerId = customerId;
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
