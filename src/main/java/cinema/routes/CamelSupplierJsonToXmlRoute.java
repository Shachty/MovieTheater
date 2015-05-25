package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 * Created by Asus on 25.05.2015.
 */

@Component
public class CamelSupplierJsonToXmlRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

    /*from("direct:findAll")
            .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=findAll")*/
        from("file://tmp/in?noop=true")
                .log("got file from mongoDB")
                .recipientList(simple("facebook://postStatusMessage?inBody=message&"))
                .log("written to facebook");

    }


}
