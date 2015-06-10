package cinema.routes;

import cinema.processor.EnquiryAggregationStrategyProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Jakob on 21.05.2015.
 */
@Component
public class CamelMongoToFTPRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /*
        //download
        //from("ftp://user:root@localhost/a?binary=true&noop=true")
        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in?binary=true&password=OmaOpa_12")
                .log("got file from ftp")
                .to("file:tmp/ftp/out")
                .log("written to mongoDB");


*/
        //upload
        from("file:tmp/in?noop=true&consumer.delay=5000")
                .log("-----------------------------------------------------")
                .to("ftp://{{ftp.username}}@{{ftp.host}}:21/htdocs/out?binary=true&password={{ftp.password}}")
                //.to("ftp://user:root@localhost/a")
                .log("written to ftp");

/*
        //upload Enquiry
        from("file:src/main/resources/Item?noop=true")//from("ftp://user:root@localhost/a")
                .log("got file from Hibernate - Items")
                .unmarshal().json(JsonLibrary.Jackson, ItemDTO.class) // TODO: wird nicht mehr gebrauch, wenn von hibernate
                .aggregate(constant(true), enquiryAggregationStrategyProcessor()).completionTimeout(3000)
                .setHeader("CamelFileName", simple("enquiry_${in.header.CamelFileName}.json"))// TODO: wird nicht mehr gebrauch, wenn von hibernate
                .marshal().json(JsonLibrary.Jackson, EnquiryDTO.class)// TODO: wird nicht mehr gebrauch, wenn von hibernate
                .to("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")//.to("ftp://user:root@localhost/offers_2")
                .log("written to ftpServer - enquiry");
*/
    }
    @Bean
    private AggregationStrategy enquiryAggregationStrategyProcessor() {
        return new EnquiryAggregationStrategyProcessor();
    }

}
