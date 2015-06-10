package cinema.routes;

import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.ScreeningsMongoDTO;
import cinema.processor.ResponseProcessor;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.util.List;

/**
 * Created by Daniel on 10.06.2015.
 */
@Component
public class CamelShowScreeningsRoute extends RouteBuilder {

    @Autowired
    ResponseProcessor responseProcessor;


    @Override
    public void configure() throws Exception {

        from("restlet:{{restlet.url}}/show-screenings?restletMethods=POST,DELETE,PUT,GET")
                .to("direct:showScreenings");

        from("direct:responseAllScreenings")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println();
                    }
                }).convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, ScreeningsMongoDTO.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        ScreeningsMongoDTO screeningsMongoDTO = (ScreeningsMongoDTO) exchange.getIn().getBody();
                        List<ScreeningMongoDTO> screeningMongoDTOs = screeningsMongoDTO.getScreenings();

                        String response = "The Screenings for today are: <br><br>";
                        int movieCounter = 1;

                        for (ScreeningMongoDTO s : screeningMongoDTOs) {
                            int availableSeats = s.getScreening().getTheaterRoom().getAvailableSeats();
                            double percentage = (double) s.getScreening().getTheaterRoom().getAvailableSeats() / (double) s.getScreening().getTheaterRoom().getOverallSeats();

                            boolean hurrySeats = false;
                            boolean soldOut = false;

                            if (percentage == 0) {
                                soldOut = true;
                            }
                            else if (percentage <= 0.25) {
                                hurrySeats = true;
                            }


                            response += movieCounter + ".<br>" +
                                    "Name: " + s.getScreening().getMovie().getMovieName() + "<br>" +
                                    "Theater Room: " + s.getScreening().getTheaterRoom().getTheaterRoomId() + "<br>" +
                                    "Time: " + s.getScreening().getTime() + "<br>" +
                                    "Available Seats: " + availableSeats + "<br>";

                            if (hurrySeats) {
                                response += "Hurry up if you want to see this movie. " + ((1.0 - percentage) * 100) + "% are booked.<br><br>";
                            }else if(soldOut){
                                response += "This screening is SOLD OUT!<br><br>";
                            }
                            else {
                                response += "<br>";
                            }
                            movieCounter++;
                        }

                        exchange.getIn().setBody(response);
                    }
                })
                .process(responseProcessor);
    }


}
