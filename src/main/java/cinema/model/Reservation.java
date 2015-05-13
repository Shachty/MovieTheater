package cinema.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Daniel on 07.05.2015.
 */
public class Reservation {


    private Movie movie;
    private Person[] persons;
    private BigDecimal overallPrice;

    public Reservation(){}

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public BigDecimal getOverallPrice() {
        return overallPrice;
    }

    public void setOverallPrice(BigDecimal overallPrice) {
        this.overallPrice = overallPrice;
    }
/*
    @Override
    public String toString(){

        return "{Reservation:{" +
                "Movie:" + movie.toString() + "," +
                "Persons:[" + getPersonsString() + "]" + "," +
                "overallPrice=  " + overallPrice.toString() +
                "}}";
    }
*/
    private String getPersonsString(){

        String ret = "";

        for(Person person : persons){
            ret += person.toString();
        }

        return ret;
    }
}
