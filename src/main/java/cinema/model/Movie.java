package cinema.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniel on 07.05.2015.
 */
public class Movie {


    private String movieName;
    private long movieId;

    @JsonCreator
    public Movie(@JsonProperty("movieName")String movieName,
                 @JsonProperty("movieId")long movieId) {
        this.movieName = movieName;
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    /*
    @Override
    public String toString(){
        return "{" +
                "movieName=" + movieName.toString() + "," +
                "theaterRoom=" + theaterRoonm +
                "}";
    }*/
}
