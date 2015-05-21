package cinema;

import cinema.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 21.05.2015.
 */
@Service
@Async
public class StartReservationService {

    @Autowired
    FileWriterService fileWriterService;

    private List<String> tickets;

    private int counter = 1;
    final static private String PATH = "src/main/resources/in/mongo/ticket";

    @PostConstruct
    public void setUp(){

       tickets = new ArrayList<>();
        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>13.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>1</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film01</movieName>\n" +
                "\t\t<movieId>1</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>16.0</overallPrice>\n" +
                "<customerId>2</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>2</theaterRoomId>\n" +
                "\t\t<overAllSeats>100</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film02</movieName>\n" +
                "\t\t<movieId>2</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>23.0</overallPrice>\n" +
                "<customerId>3</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>1</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>50</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film03</movieName>\n" +
                "\t\t<movieId>3</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>53.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>7</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>150</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film07</movieName>\n" +
                "\t\t<movieId>7</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>43.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>5</theaterRoomId>\n" +
                "\t\t<overAllSeats>100</overAllSeats>\n" +
                "\t\t<availableSeats>90</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film05</movieName>\n" +
                "\t\t<movieId>5</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>63.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>4</theaterRoomId>\n" +
                "\t\t<overAllSeats>400</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film01</movieName>\n" +
                "\t\t<movieId>1</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>13.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>1</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film01</movieName>\n" +
                "\t\t<movieId>1</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>13.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>1</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film01</movieName>\n" +
                "\t\t<movieId>1</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>13.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>1</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film01</movieName>\n" +
                "\t\t<movieId>1</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");

        tickets.add("<Ticket>\n" +
                "<isReservation>true</isReservation>\n" +
                "<overallPrice>13.0</overallPrice>\n" +
                "<customerId>1</customerId>\n" +
                "<screening>\n" +
                "\t<theaterRoom>\n" +
                "\t\t<theaterRoomId>1</theaterRoomId>\n" +
                "\t\t<overAllSeats>200</overAllSeats>\n" +
                "\t\t<availableSeats>100</availableSeats>\n" +
                "\t</theaterRoom>\n" +
                "\t<movie>\n" +
                "\t\t<movieName>film01</movieName>\n" +
                "\t\t<movieId>1</movieId>\n" +
                "\t</movie>\n" +
                "</screening>\n" +
                "<mail>Daniel.Shatkin@gmail.com</mail>\n" +
                "</Ticket>");


    }

        public void startReservations(){

            for(String s : this.tickets){
             this.fileWriterService.writeFile(PATH,s,counter,".xml");
                this.counter++;
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
