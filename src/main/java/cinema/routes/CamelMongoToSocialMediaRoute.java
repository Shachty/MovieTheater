package cinema.routes;

import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.ScreeningsMongoDTO;
import cinema.model.Screening;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CamelMongoToSocialMediaRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("direct:socialMedia")
                .setBody().constant(" ")
                .enrich("mongodb:mongoBean?database=workflow&collection=screenings&operation=findAll&dynamicity=true")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, ScreeningsMongoDTO.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String socialMediaMessage = "";
                        String socialMediaMessageFull = "";
                        ScreeningsMongoDTO screeningsMongoDTO = (ScreeningsMongoDTO) exchange.getIn().getBody();

                        for (Object obj : screeningsMongoDTO.getScreenings()) {
                            ScreeningMongoDTO screeningMongoDTO = (ScreeningMongoDTO) obj;
                            Screening screening = ((ScreeningMongoDTO) obj).getScreening();

                            if (screening.getTheaterRoom().getAvailableSeats() == 0) {
                                if (socialMediaMessageFull.equals("")) {
                                    socialMediaMessageFull += "No available seats left for: " + screening.getMovie().getMovieName();
                                } else {
                                    socialMediaMessageFull += ", " + screening.getMovie().getMovieName();
                                }
                            } else {
                                if (screening.getTheaterRoom().getAvailableSeats() < (screening.getTheaterRoom().getOverallSeats() * 0.25)) {
                                    if (socialMediaMessage.equals("")) {
                                        socialMediaMessage += "Buy tickets NOW! - These movies are almost fully booked: " + screening.getMovie().getMovieName();
                                    } else {
                                        socialMediaMessage += ", " + screening.getMovie().getMovieName();
                                    }
                                }
                            }
                        }

                        String output = "";

                        if (!socialMediaMessage.equals("")) {
                            if (new Date().getMinutes() < 10) {
                                output += Integer.toString(new Date().getHours()) + ":0" + Integer.toString(new Date().getMinutes());
                            } else {
                                output += Integer.toString(new Date().getHours()) + ":" + Integer.toString(new Date().getMinutes());
                            }

                            output += " " + socialMediaMessage;
                            if (!socialMediaMessageFull.equals("")) {
                                output += " - " + socialMediaMessageFull;
                            }
                        } else {
                            /*activate if you want to show also a message about full rooms*/
                           /* if (!socialMediaMessageFull.equals("")) {
                                if (new Date().getMinutes() < 10) {
                                    output += Integer.toString(new Date().getHours()) + ":0" + Integer.toString(new Date().getMinutes());
                                } else {
                                    output += Integer.toString(new Date().getHours()) + ":" + Integer.toString(new Date().getMinutes());
                                }

                                output += " " + socialMediaMessageFull;
                            } else {
                                output = "";
                            }
                            */
                        }

                        if (output.equals("")) {
                            output = "empty";
                        }
                        exchange.getIn().setBody(output);
                    }
                })
                .choice()
                .when(body().isNotEqualTo("empty"))
                .multicast().parallelProcessing()
                .to("direct:twitter", "direct:facebook");

        from("direct:twitter")
                .to("twitter://timeline/user?consumerKey=j5gTr0r72YSgle7b1CSzFtrg6&consumerSecret=crZI8W4R11i1bSjvKt49hd3DdYV2zTgx0Fy0YKGqz5JfzIuofF&accessToken=3240346329-PdlJTNwUHre4YW5ySic7x1505NaCZCTfC4JCheM&accessTokenSecret=FnLGjfjm9raE5Pyrs35XK5C5xb3recJ6Rg5TtZyLYEI4f")
                .log("written to twitter message: ${body}");

        from("direct:facebook")
                .recipientList(simple("facebook://postStatusMessage?inBody=message&" + getOAuthKeys()))
                .log("written to facebook message: ${body}");
    }

    private String getOAuthKeys() {
        String oAuthAccessToken = "CAAK8TT51AOQBAIXjsxPfU7SH6hD1yY6qmFlMMHzdGVQfDiEf8ZAKIjmsiOkKZAfr8y05IElNJvi1WXASyIAEdszJke4WbkzykvidB5UHVRlRaR8EkS6399X1t96hiNlZBSDKAsifMQF5eucYUcHUZAnHi1vaOLd9bec6veCH9B53ZAZAL3BMPohfRkDviZABtC8ZBh21IM7qUiMr2KHSmcAy";
        String oAuthAppId = "769989899780324";
        String oAuthAppSecret = "1b3f6821328040af2e017b685b72ea5d";


        return "oAuthAppId=" + oAuthAppId + "&oAuthAppSecret=" + oAuthAppSecret + "&oAuthAccessToken=" + oAuthAccessToken;
    }
}