package cinema.routes;

import cinema.dto.EnquiryDTO;
import cinema.helper.SupplierCsvCreatorProcessor;
import cinema.helper.SupplierOfferProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CamelSupplierPublishSubscribeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/enquiries?noop=true")//from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")
               .multicast().parallelProcessing()
                .to("direct:supplierXml", "direct:supplierCsv", "direct:supplierJson");
    }
}
