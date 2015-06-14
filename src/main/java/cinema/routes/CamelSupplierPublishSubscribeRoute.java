package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelSupplierPublishSubscribeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("ftp://{{ftp.username}}@{{ftp.hostname}}:21/htdocs/out/enquiry?binary=true&password={{ftp.password}}&delete=true")//from("file://tmp/test/in?noop=true")//
                .log(simple("${body}").getText())
                .delay(5*1000)
               .multicast().parallelProcessing()
                .to("direct:supplierXml", "direct:supplierCsv", "direct:supplierJson");
    }
}
