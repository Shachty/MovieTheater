package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {

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
    public Ticket(@JsonProperty("ticketStatus") TicketStatus ticketStatus,
                  @JsonProperty("firstName")String firstName,
                  @JsonProperty("lastName")String lastName,
                  @JsonProperty("numberOfPersons")int numberOfPersons,
                  @JsonProperty("theaterRoom") int theaterRoom,
                  @JsonProperty("movieName")String movieName,
                  @JsonProperty("time")String time,
                  @JsonProperty("customerId")long customerId,
                  @JsonProperty("mail")String mail) {
        this.ticketStatus = ticketStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfPersons = numberOfPersons;
        this.theaterRoom = theaterRoom;
        this.movieName = movieName;
        this.time = time;
        this.customerId = customerId;
        this.mail = mail;
    }
/*

    @JsonCreator
    public Ticket(@JsonProperty("isReservation") boolean isReservation,
                  @JsonProperty("firstName")String firstName,
                  @JsonProperty("lastName")String lastName,
                  @JsonProperty("numberOfPersons")int numberOfPersons,
                  @JsonProperty("theaterRoom") int theaterRoom,
                  @JsonProperty("movieName")String movieName,
                  @JsonProperty("time")String time,
                  @JsonProperty("customerId")long customerId,
                  @JsonProperty("mail")String mail,
                  @JsonProperty("pricePerPerson")BigDecimal pricePerPerson) {
        this.isReservation = isReservation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pricePerPerson = pricePerPerson;
        this.numberOfPersons = numberOfPersons;
        this.theaterRoom = theaterRoom;
        this.movieName = movieName;
        this.time = time;
        this.customerId = customerId;
        this.mail = mail;
    }
*/

    public static String toXmlString(Ticket ticket){
        String ret = "<Ticket>" +
                "<ticketStatus>"+ticket.getTicketStatus()+"</ticketStatus>"+
                "<firstName>"+ticket.getFirstName()+"</firstName>"+
                "<lastName>"+ticket.getLastName()+"</lastName>"+
                "<numberOfPersons>"+ticket.getNumberOfPersons()+"</numberOfPersons>"+
                "<theaterRoom>"+ticket.getTheaterRoom()+"</theaterRoom>"+
                "<movieName>"+ticket.getMovieName()+"</movieName>"+
                "<time>"+ticket.getTime()+"</time>"+
                "<customerId>"+ticket.getCustomerId()+"</customerId>"+
                "<mail>"+ticket.getMail()+"</mail>"+
                "</Ticket>";

        return ret;
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