package cinema.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import cinema.jpa.model.*;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class Test_01 {

    private  EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("thePersistenceUnit");
    }


    @Test
    public void TestMovieObjects() {
        EntityManagerFactory factory = getEntityManagerFactory();
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
        screen.setScreeningTime(Calendar.getInstance());

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

    @Test
    public void testSnacks()
    {
        EntityManagerFactory factory = getEntityManagerFactory();
        EntityManager theManager = factory.createEntityManager();
        assertNotNull(theManager);

        theManager.getTransaction().begin();

        Snack[] snacks = new Snack[10];

        for(int i = 1; i < snacks.length; i++) {
            snacks[i] = new Snack();
            assertNotNull(snacks[i]);
            snacks[i].setName("Snack"+i);
            snacks[i].setNumber(new Double(i * 10));
            theManager.persist(snacks[i]);
        }

        Enquiry enquiry1 = new Enquiry();
        Enquiry enquiry2 = new Enquiry();

        enquiry1.addSnackToOrder(snacks[1], new Long(10), "supid1");
        enquiry1.addSnackToOrder(snacks[2], new Long(11), "supid2");
        enquiry1.addSnackToOrder(snacks[3], new Long(12), "supid3");
        enquiry1.addSnackToOrder(snacks[4], new Long(13), "supid4");
        enquiry1.addSnackToOrder(snacks[5], new Long(14), "supid5");

        enquiry2.addSnackToOrder(snacks[3], new Long(1), "supid6");
        enquiry2.addSnackToOrder(snacks[4], new Long(2), "supid7");
        enquiry2.addSnackToOrder(snacks[5], new Long(3), "supid8");
        enquiry2.addSnackToOrder(snacks[6], new Long(4), "supid9");
        enquiry2.addSnackToOrder(snacks[7], new Long(5), "supid10");
        enquiry2.addSnackToOrder(snacks[8], new Long(6), "supid11");
        enquiry2.addSnackToOrder(snacks[9], new Long(7), "supid12");

        theManager.persist(enquiry1);
        theManager.persist(enquiry2);

        theManager.getTransaction().commit();

        for(int i = 1; i < snacks.length; i++) {
            assertNotNull(snacks[i].getId());
        }

//        EnquiryDAO theOrderDao = new EnquiryDAO();
        Enquiry fetchEnquiry1 = (Enquiry)theManager.find(Enquiry.class, enquiry1.getId());
        Enquiry fetchEnquiry2 = (Enquiry)theManager.find(Enquiry.class, enquiry2.getId());

        assertEquals(5, fetchEnquiry1.getItems().size());
        assertEquals(7, fetchEnquiry2.getItems().size());

        boolean found = false;
        for(Item oi : enquiry1.getItems()) {
            if(oi.getSnack().equals(snacks[1])) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for(Item oi : enquiry2.getItems()) {
            if(oi.getSnack().equals(snacks[9])) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for(Item oi : enquiry2.getItems()) {
            if(oi.getSnack().equals(snacks[1])) {
                found = true;
                break;
            }
        }
        assertFalse(found);

    }
}