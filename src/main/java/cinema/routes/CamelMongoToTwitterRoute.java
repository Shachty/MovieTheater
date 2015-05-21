package cinema.routes;

import cinema.model.Ticket;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CamelMongoToTwitterRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        //from("file:tmp/in?noop=true")
        //        .to("direct:findAll");

        from("direct:findAll")
                .log("body ${body}")
                .setBody().constant("")
                .enrich("mongodb:mongoBean?database=workflow&collection=workflow&operation=findAll&dynamicity=true")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                String output = "";
                                String payload = exchange.getIn().getBody(String.class);
                                //  BasicDBList list = payload;
                                List<HashMap<Integer, BasicDBObject>> list = exchange.getIn().getBody(List.class);

                                // do something with the payload and/or exchange here
                                for (HashMap<Integer, BasicDBObject> hash : list) {
                                    BasicDBObject t = (BasicDBObject) hash.get("Ticket");
                                    output += t.toString();

                                }

                                exchange.getIn().setBody(output);
                            }
                        })
                .log("file of mongo ${body}")
        .setBody().constant(new Date().toString()).to("twitter://timeline/user?consumerKey=j5gTr0r72YSgle7b1CSzFtrg6&consumerSecret=crZI8W4R11i1bSjvKt49hd3DdYV2zTgx0Fy0YKGqz5JfzIuofF&accessToken=3240346329-PdlJTNwUHre4YW5ySic7x1505NaCZCTfC4JCheM&accessTokenSecret=FnLGjfjm9raE5Pyrs35XK5C5xb3recJ6Rg5TtZyLYEI4f")
        ;

        //  from("mongodb:mongoBean?database=workflow&collection=workflow")
        //          .log("file to twitter")
        //         .to("file:tmp/out");
        // .to("twitter://timeline/user?consumerKey=j5gTr0r72YSgle7b1CSzFtrg6&consumerSecret=crZI8W4R11i1bSjvKt49hd3DdYV2zTgx0Fy0YKGqz5JfzIuofF&accessToken=3240346329-PdlJTNwUHre4YW5ySic7x1505NaCZCTfC4JCheM&accessTokenSecret=FnLGjfjm9raE5Pyrs35XK5C5xb3recJ6Rg5TtZyLYEI4f");

        //this works:
        // .to("file:tmp/out");

        // from("direct:count").to("mongodb:myDb?database=tickets&collection=flights&operation=count&dynamicity=true");
        //    Long result = template.requestBodyAndHeader("direct:count", "irrelevantBody", MongoDbConstants.COLLECTION, "dynamicCollectionName");
        //  assertTrue("Result is not of type Long", result instanceof Long);

        //  DBObject query = ...
        // Long count = template.requestBodyAndHeader("direct:count", query, MongoDbConstants.COLLECTION, "dynamicCollectionName");
    }
}