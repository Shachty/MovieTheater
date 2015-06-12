package cinema.dto.mongo;

import cinema.model.Ticket;
import cinema.model.TicketStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Daniel on 21.05.2015.
 */
public class TicketMongoDTO {

    private MongoId _id;
    private String firstName;
    private String lastName;
    private BigDecimal pricePerPerson;
    private int numberOfPersons;
    private int theaterRoom;
    private String movieName;
    private String time;
    private long customerId;
    private String mail;
    private TicketStatus ticketStatus;

    @JsonCreator
    public TicketMongoDTO(@JsonProperty("_id") MongoId _id,
                          @JsonProperty("ticketStatus") TicketStatus ticketStatus,
                          @JsonProperty("firstName")String firstName,
                          @JsonProperty("lastName")String lastName,
                          @JsonProperty("numberOfPersons")int numberOfPersons,
                          @JsonProperty("theaterRoom") int theaterRoom,
                          @JsonProperty("movieName")String movieName,
                          @JsonProperty("time")String time,
                          @JsonProperty("customerId")long customerId,
                          @JsonProperty("mail")String mail) {
        this._id = _id;
        this.ticketStatus =ticketStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfPersons = numberOfPersons;
        this.theaterRoom = theaterRoom;
        this.movieName = movieName;
        this.time = time;
        this.customerId = customerId;
        this.mail = mail;
    }

    public MongoId get_id() {
        return _id;
    }

    public void set_id(MongoId _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(BigDecimal pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public int getTheaterRoom() {
        return theaterRoom;
    }

    public void setTheaterRoom(int theaterRoom) {
        this.theaterRoom = theaterRoom;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
