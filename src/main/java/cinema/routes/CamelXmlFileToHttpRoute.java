package cinema.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel on 17.05.2015.
 */

@Component
public class CamelXmlFileToHttpRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        from("file:tmp/mongo?noop=true").marshal(xmlJsonFormat).convertBodyTo(String.class)
                .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
        .to("http4://localhost/mon")
                .to("mock:results");



    }
}
