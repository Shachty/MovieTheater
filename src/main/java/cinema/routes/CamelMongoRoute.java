package cinema.routes;

import com.mongodb.Mongo;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel on 11.05.2015.
 */
@Component
public class CamelMongoRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        JacksonDataFormat format = new JacksonDataFormat();
        format.setAllowJmsType(true);

        from("file:src/main/resources/tickets?noop=true")
         //       .marshal(xmlJsonFormat)
         //       .log("xml to json")
                .convertBodyTo(String.class)
                        .log("to String")
                .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert").log("written to mongoDB");
                /*.unmarshal(format)
        //from("file://src/main/resources/test?noop=true")
                        .recipientList(simple("smtps://smtp.gmail.com?username=moviecenter.wmpm@gmail.com&password=workflow&to=s.scheickl@gmx.net&subject=Your reservation"));
        ;*/
   }

/*

    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);
        from("timer://foo?fixedRate=true&delay=0&period=10000")
                .to("http4://localhost/mon")
                        .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert")
                .to("file:tmp/mongoout");
    }
*/


}
