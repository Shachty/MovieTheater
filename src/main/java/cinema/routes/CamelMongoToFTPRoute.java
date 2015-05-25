package cinema.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Jakob on 21.05.2015.
 */
@Component
public class CamelMongoToFTPRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //download
        //from("ftp://user:root@localhost/a?binary=true&noop=true")
        from("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/in?binary=true&password=OmaOpa_12")
                .log("got file from ftp")
                .to("file:tmp/ftp/out")
                .log("written to mongoDB");



        //upload
        from("file:tmp/in?noop=true")
                .log("got file from mongoDB")
                .to("ftp://b7_16249111@ftp.byethost7.com:21/htdocs/out?binary=true&password=OmaOpa_12")
                //.to("ftp://user:root@localhost/a")
                .log("written to ftp");

    }
}
