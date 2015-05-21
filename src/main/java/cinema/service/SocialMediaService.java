package cinema.service;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Asus on 07.05.2015.
 */
@Service
public class SocialMediaService {

    @Async
    public void startCoffee() throws Exception {

        final CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                FacebookConfiguration conf = new FacebookConfiguration();

                    System.out.println("XYZ");

                //Endpoint end = new FacebookEndpoint("facebook://postFeed?inBody=postUpdate", new FacebookComponent(camelContext, conf), "", new FacebookEndpointConfiguration());
                //from("file:tmp/in?noop=true").process(new LogProcessor()).to(end);

             //   from("file:tmp/in?noop=true").process(new LogProcessor()).to("facebook://postStatusMessage?inBody=message&"+getOAuthKeys());
                //from("file:tmp/in?noop=true").process(new LogProcessor()).to("facebook://postFeed?inBody=postUpdate&"+getOAuthKeys());

            }
        });
        //ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        System.out.println("123");
        //template.sendBody("facebook://postFeed?inBody=abc&"+getOAuthKeys(), "Test Message: ");
        Thread.sleep(60 * 5 * 1000);
        camelContext.stop();

    }

    private String getOAuthKeys(){
        String oAuthAccessToken="CAAK8TT51AOQBAB33Az8Q6NNKJmIPsYr9sFNFYHWRL23CZBF7l8oAvaZBOrDpXFzNWZC5Y0PykARHLBTVWyXtW3ySU6t0ZCsXHh7D2BPNZBdwrmtVmcqweXEQslZAP9iO1LqbM240cM4ftZBQ7mj0C8vrAdttLRafy3ZBmLbDkJ8aNmAOWm8KF71HRMblnsiggL4ZD";
        String oAuthAppId="769989899780324";
        String oAuthAppSecret="1b3f6821328040af2e017b685b72ea5d";


        return "oAuthAppId="+oAuthAppId+"&oAuthAppSecret="+oAuthAppSecret+"&oAuthAccessToken="+oAuthAccessToken;
    }
}
