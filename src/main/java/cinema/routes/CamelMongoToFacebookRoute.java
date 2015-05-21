package cinema.routes;

import cinema.helper.LogProcessor;
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

        /*from("direct:findAll")
                .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=findAll")*/
        from("file://tmp/in?noop=true")
                .log("got file from mongoDB")
                .recipientList(simple("facebook://postStatusMessage?message=${body}&" + getOAuthKeys()))
                .log("written to facebook");
    }

    private String getOAuthKeys(){
        String oAuthAccessToken="CAAK8TT51AOQBAIXjsxPfU7SH6hD1yY6qmFlMMHzdGVQfDiEf8ZAKIjmsiOkKZAfr8y05IElNJvi1WXASyIAEdszJke4WbkzykvidB5UHVRlRaR8EkS6399X1t96hiNlZBSDKAsifMQF5eucYUcHUZAnHi1vaOLd9bec6veCH9B53ZAZAL3BMPohfRkDviZABtC8ZBh21IM7qUiMr2KHSmcAy";
        String oAuthAppId="769989899780324";
        String oAuthAppSecret="1b3f6821328040af2e017b685b72ea5d";


        return "oAuthAppId="+oAuthAppId+"&oAuthAppSecret="+oAuthAppSecret+"&oAuthAccessToken="+oAuthAccessToken;
    }
}
