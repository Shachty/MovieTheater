package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheaterRoom {

    private long theaterRoomId;
    private int overallSeats;
    private int availableSeats;


    @JsonCreator
    public TheaterRoom(@JsonProperty("theaterRoomId") long theaterRoomId,
                       @JsonProperty("overallSeats") int overallSeats,
                       @JsonProperty("availableSeats") int availableSeats) {
        this.theaterRoomId = theaterRoomId;
        this.overallSeats = overallSeats;
        this.availableSeats = availableSeats;
    }

    public long getTheaterRoomId() {
        return theaterRoomId;
    }

    public void setTheaterRoomId(long theaterRoomId) {
        this.theaterRoomId = theaterRoomId;
    }

    public int getOverallSeats() {
        return overallSeats;
    }

    public void setOverallSeats(int overallSeats) {
        this.overallSeats = overallSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
