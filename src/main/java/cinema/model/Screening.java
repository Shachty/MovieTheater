package cinema.model;

import cinema.model.Movie;
import cinema.model.TheaterRoom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Daniel on 13.05.2015.
 */
public class Screening {

    private TheaterRoom theaterRoom;
    private Movie movie;
    private BigDecimal pricePerPerson;
    private String time;



    @JsonCreator
    public Screening(@JsonProperty("theaterRoom")TheaterRoom theaterRoom,
                     @JsonProperty("movie")Movie movie,
                     @JsonProperty("price")BigDecimal pricePerPerson,
                     @JsonProperty("time") String time) {
        this.theaterRoom = theaterRoom;
        this.movie = movie;
        this.pricePerPerson = pricePerPerson;
        this.time = time;
    }

    public TheaterRoom getTheaterRoom() {
        return theaterRoom;
    }

    public void setTheaterRoom(TheaterRoom theaterRoom) {
        this.theaterRoom = theaterRoom;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public BigDecimal getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(BigDecimal pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
