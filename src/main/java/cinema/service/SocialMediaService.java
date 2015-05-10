package cinema.service;

import facebook4j.FacebookResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.facebook.FacebookComponent;
import org.apache.camel.component.facebook.FacebookEndpoint;
import org.apache.camel.component.facebook.config.FacebookConfiguration;
import org.apache.camel.component.facebook.config.FacebookEndpointConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.autoconfigure.social.FacebookProperties;
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
                conf.setOAuthAccessToken("CAAK8TT51AOQBADQrwNNIUNc39ofPMCJsdbwZA71ZAiod3d9uH8gZBuGUMP8Xm2BXOFeC0mBXFu3fT3ZCZBTiOLbCiAQjCVDwKtODMJuDE6DJaPbAhHHTuNsbCIo92NZBtsddQ0kZAHsva8Ru8HddUGu4yX2IIVnZBVCVdfl1lYRr1MkIOIcn6CdFL3mjZAYVukAMaSW9fmYp6ttZCnfrVdZBJha");
                conf.setOAuthAppId("769989899780324");
                conf.setOAuthAppSecret("1b3f6821328040af2e017b685b72ea5d");
                Endpoint end = new FacebookEndpoint("facebook://postFeed?message=abc", new FacebookComponent(camelContext, conf), "", new FacebookEndpointConfiguration());

                from("file:tmp/in").to(end);
                //from("file:tmp/in").to("facebook://postFeed?message=abc&oAuthAppId=1643747599195694&oAuthAppSecret=b4308c6d220393a71e3b9d6ccfb4bcd3&oAuthAccessToken=CAAK8TT51AOQBAGwqhWBrt6Ss21Y4JEiXHsjVzs18j0U4mZB4cofqi1GbHJsOZBCaUYf5GMfbaBvD19Yf7O0Im5OI4EBZCrRygy6sxJVVQpNtXMt5IN75WEpnHkoGa9ZAxdlt2xyY2kPiWmN4I0FYHXjw7dl5o6PnTlMj4prvnR72ZCX47W9OmC0SGRpiagWIHsnKbJWFu9vfwIa61SZCLZB");

            }
        });
        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        template.sendBody("facebook://postFeed?message=abc&oAuthAppId=769989899780324&oAuthAppSecret=1b3f6821328040af2e017b685b72ea5d&oAuthAccessToken=CAAK8TT51AOQBADQrwNNIUNc39ofPMCJsdbwZA71ZAiod3d9uH8gZBuGUMP8Xm2BXOFeC0mBXFu3fT3ZCZBTiOLbCiAQjCVDwKtODMJuDE6DJaPbAhHHTuNsbCIo92NZBtsddQ0kZAHsva8Ru8HddUGu4yX2IIVnZBVCVdfl1lYRr1MkIOIcn6CdFL3mjZAYVukAMaSW9fmYp6ttZCnfrVdZBJha", "Test Message: ");
        Thread.sleep(60 * 5 * 1000);
        camelContext.stop();

    }
}
