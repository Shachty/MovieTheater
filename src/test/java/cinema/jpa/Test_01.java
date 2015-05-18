package cinema.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.AssertTrue;

import cinema.jpa.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class Test_01 {

    @Test
    public void TestMovieObjects() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        assertNotNull(theManager);

        theManager.getTransaction().begin();

        // für ein screening brauche ich ein Theater und ein Movie

        Movie movie = new Movie();
        movie.setName("name");
        theManager.persist(movie);
        System.out.println(movie.getId());
        Long movieId = movie.getId();

        Theater theat = new Theater();
        theat.setName("theater1");
        theat.setSeatsTotal(10);

        theManager.persist(theat);
        Long theatId = theat.getId();
        assertNotNull(theatId);

        Screening screen = new Screening();
        screen.setMovie(movie);
        screen.setTheater(theat);

        theManager.persist(screen);
        Long screenId = theat.getId();
        assertNotNull(theatId);
        theManager.getTransaction().commit();

        Movie m1 = (Movie)theManager.find(Movie.class, movieId);
        System.out.println(movie.getId());
        assertNotNull(m1);
        assertSame(m1, movie);

        Screening s2 = (Screening)theManager.find(Screening.class, screenId);
        assertNotNull(s2);
        assertEquals(s2.getMovie(), movie);
        assertEquals(s2.getTheater(), theat);

    }
}