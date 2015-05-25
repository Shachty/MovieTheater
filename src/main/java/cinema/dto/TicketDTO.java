package cinema.dto;

import cinema.model.Ticket;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

/**
 * Created by Daniel on 21.05.2015.
 */
public class TicketDTO {

    public Ticket ticket;

    @JsonCreator
    public TicketDTO(@JsonProperty("Ticket") Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
