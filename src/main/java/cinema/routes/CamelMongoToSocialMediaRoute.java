package cinema.routes;

import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.ScreeningsMongoDTO;
import cinema.model.Screening;
import cinema.service.SocialMediaService;
import facebook4j.FacebookException;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Component
public class CamelMongoToSocialMediaRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        onException(TwitterException.class, FacebookException.class)
                .process(new Processor() {
                    //no redelivieries
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

                        String logMessage = "";

                        if (caused.getMessage().contains("Status is a duplicate")) {
                            logMessage = "Not posted to twitter because it was a duplicate";
                        } else if (caused.getMessage().contains("Duplicate status message")) {
                            logMessage = "Not posted to facebook because it was a duplicate";
                        } else {
                            logMessage = caused.getMessage();
                        }

                        exchange.getIn().setBody(logMessage);
                    }
                })
                .log("Handled exception: ${body}")
                .handled(true);

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
                                    socialMediaMessageFull += "Fully booked: " + screening.getMovie().getMovieName() + " at " + screening.getTime();
                                } else {
                                    socialMediaMessageFull += ", " + screening.getMovie().getMovieName() + " at " + screening.getTime();
                                }
                            } else {
                                if (screening.getTheaterRoom().getAvailableSeats() < (screening.getTheaterRoom().getOverallSeats() * 0.25)) {
                                    if (socialMediaMessage.equals("")) {
                                        socialMediaMessage += "Book NOW! Movies almost fully booked: " + screening.getMovie().getMovieName() + " at " + screening.getTime();
                                    } else {
                                        socialMediaMessage += ", " + screening.getMovie().getMovieName() + " at " + screening.getTime();
                                    }
                                }
                            }
                        }

                        String output = "";

                        if (!socialMediaMessage.equals("")) {
                            output += socialMediaMessage;
                            if (!socialMediaMessageFull.equals("")) {
                                output += " - " + socialMediaMessageFull;
                            }
                        } else {
                            /*activate if you want to show also a message about full rooms*/
                            if (!socialMediaMessageFull.equals("")) {
                                output += socialMediaMessageFull;
                            } else {
                                output = "";
                            }
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
                .split().method("splitterBean", "splitBody")
                .to("twitter://timeline/user?consumerKey={{twitter.consumerKey}}&consumerSecret={{twitter.consumerSecret}}&accessToken={{twitter.accessToken}}&accessTokenSecret={{twitter.accessTokenSecret}}")
                .log("written to twitter");

        from("direct:facebook")
                .recipientList(simple("facebook://postStatusMessage?inBody=message&oAuthAppId={{facebook.oAuthAppId}}&oAuthAppSecret={{facebook.oAuthAppSecret}}&oAuthAccessToken={{facebook.oAuthAccessToken}}"))
                .log("written to facebook");
    }
}