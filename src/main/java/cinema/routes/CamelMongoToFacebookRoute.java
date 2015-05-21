package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 * Created by Asus on 19.05.2015.
 */
@Component
public class CamelMongoToFacebookRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        from("file:tmp/in?noop=true")
                .log("got file from mongoDB")
                .to("facebook://postStatusMessage?inBody=message&" + getOAuthKeys())
                .log("written to mongoDB");
    }

    private String getOAuthKeys(){
        String oAuthAccessToken="CAAK8TT51AOQBAB33Az8Q6NNKJmIPsYr9sFNFYHWRL23CZBF7l8oAvaZBOrDpXFzNWZC5Y0PykARHLBTVWyXtW3ySU6t0ZCsXHh7D2BPNZBdwrmtVmcqweXEQslZAP9iO1LqbM240cM4ftZBQ7mj0C8vrAdttLRafy3ZBmLbDkJ8aNmAOWm8KF71HRMblnsiggL4ZD";
        String oAuthAppId="769989899780324";
        String oAuthAppSecret="1b3f6821328040af2e017b685b72ea5d";


        return "oAuthAppId="+oAuthAppId+"&oAuthAppSecret="+oAuthAppSecret+"&oAuthAccessToken="+oAuthAccessToken;
    }
}
