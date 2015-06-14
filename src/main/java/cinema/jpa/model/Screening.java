package cinema.jpa.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "CINEMA_Screening")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Movie movie;
    @OneToOne
    private Theater theater;
    private Calendar screeningTime;

    public Long getId() {
        return this.id;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public Theater getTheater() {
        return this.theater;
    }

    public void setId(Long param) {
        this.id = param;
    }

    public void setMovie(Movie param) {
        this.movie = param;
    }

    public void setTheater(Theater param) {
        this.theater = param;
    }

    public void setScreeningTime(Calendar time) {
        this.screeningTime = time;
    }

    public Calendar getScreeningTime() {
        return this.screeningTime;
    }
}
