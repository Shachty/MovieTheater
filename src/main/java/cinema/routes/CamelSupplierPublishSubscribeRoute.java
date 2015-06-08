package cinema.routes;

import cinema.dto.EnquiryDTO;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelSupplierPublishSubscribeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out/enquiry?binary=true&password=OmaOpa_12")
               .multicast().parallelProcessing()
                .to("direct:supplierXml", "direct:supplierCsv", "direct:supplierJson");
    }
}
