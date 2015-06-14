package cinema.routes;

import cinema.dto.ScreeningDTO;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;


@Component
public class ScreeningToMongo extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        from("file:src/main/resources/screenings?noop=true")
                .marshal(xmlJsonFormat)
                .log("xml to json")
                .unmarshal().json(JsonLibrary.Jackson, ScreeningDTO.class)
                .to("mongodb:mongoBean?database=workflow&collection=screenings&operation=insert").log("written to mongoDB");
    }
}
