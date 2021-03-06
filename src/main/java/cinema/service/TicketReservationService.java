package cinema.service;

import cinema.model.Ticket;
import cinema.model.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketReservationService {

    @Autowired
    FileWriterService fileWriterService;

    private int customerCounter = 1;

    public void createReservation(String firstName,
                                  String lastName,
                                  String movieName,
                                  String mail,
                                  int theaterRoomId,
                                  int numberOfPersons,
                                  String time){

        Ticket ticket = new Ticket(
                TicketStatus.RESERVATION_IN_PROGRESS,
                firstName,
                lastName,
                numberOfPersons,
                theaterRoomId,
                movieName,
                time,
                this.customerCounter,
                mail);

        fileWriterService.writeFile("src/main/resources/reservations/reservation",Ticket.toXmlString(ticket),this.customerCounter,".xml");

        this.customerCounter++;
    }

}
