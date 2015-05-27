package cinema.dto.mongo;

import cinema.model.Ticket;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniel on 21.05.2015.
 */
public class TicketMongoDTO {

    public Ticket ticket;
    private MongoId _id;

    @JsonCreator
    public TicketMongoDTO(@JsonProperty("_id") MongoId _id,
                          @JsonProperty("Ticket") Ticket ticket) {
        this.ticket = ticket;
        this._id = _id;
    }

    public MongoId get_id() {
        return _id;
    }

    public void set_id(MongoId _id) {
        this._id = _id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
