package cinema.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Jakob on 21.05.2015.
 */
public class CamelMongoToFTPRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //download
                    /*from("ftp://user:root@localhost/a?binary=true&noop=true")
                            .log("got file from ftp")
                            .to("file:tmp/ftp/out")
                            .log("written to mongoDB");
                    */


        //upload
        from("file:tmp/in?noop=true")
                .log("got file from mongoDB")
                .to("ftp://user:root@localhost/a")
                .log("written to ftp");
    }
}
