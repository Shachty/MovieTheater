package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class CamelMongoToTwitterRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception{
        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setForceTopLevelObject(true);

        from("file:tmp/in?noop=true")
                .log("file to witter")
                .to("mongodb:mongoBean?database=workflow&collection=workflow&operation=insert").log("written to twitter");

       // from("ftp://localhost/dir")
         //       .to("file:tmp/ftp/out");
        //from("file:tmp/in?noop=true")
        //         .to("file:tmp/out")
        //.to("twitter://timeline/user?consumerKey=4ztAqgtOpu61vyuUiLxqoI2xV&consumerSecret=9uuh7Vm7KtTmlyt2QDADLzYOaX0VkkRSfbtLwf8pPxDMS6THbB&accessToken=3240346329-PdlJTNwUHre4YW5ySic7x1505NaCZCTfC4JCheM&accessTokenSecret=FnLGjfjm9raE5Pyrs35XK5C5xb3recJ6Rg5TtZyLYEI4f")
        //;

    }
}