package cinema.model;

import cinema.model.Movie;
import cinema.model.TheaterRoom;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniel on 13.05.2015.
 */
public class Screening {

    private TheaterRoom theaterRoom;
    private Movie movie;

    @JsonCreator
    public Screening(@JsonProperty("theaterRoom")TheaterRoom theaterRoom,
                     @JsonProperty("movie")Movie movie) {
        this.theaterRoom = theaterRoom;
        this.movie = movie;
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
}
