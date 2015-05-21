package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;

/**
 * Created by Daniel on 21.05.2015.
 */
public class TheaterRoomToMongo extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        from("file:src/main/resources/theaterRooms?noop=true")
                .marshal(xmlJsonFormat)
                .log("xml to json")
                .convertBodyTo(String.class)
                .to("mongodb:mongoBean?database=workflow&collection=theaterRooms&operation=insert").log("written to mongoDB");

    }
}
