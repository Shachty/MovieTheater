package cinema;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;

import cinema.routes.*;

@SpringBootApplication
@EnableAsync
public class Application {

    final static Logger logger = Logger.getLogger(Application.class);

    public static ArrayList<Class> camelAutoloadRoutes;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext("cinema");
        startCamelContext(context);

    }

    public static void startCamelContext(ApplicationContext ctx) {

        logger.debug("startCamelContext");
        CamelContext camelContext = ctx.getBean(org.apache.camel.impl.DefaultCamelContext.class);

        camelAutoloadRoutes = new ArrayList<>();
        camelAutoloadRoutes.add(CamelCsvToHibernateRoute.class);
        camelAutoloadRoutes.add(CamelMongoRoute.class);
        camelAutoloadRoutes.add(CamelTicketToHttpRoute.class);
        camelAutoloadRoutes.add(CamelCsvToHibernateRoute.class);
        camelAutoloadRoutes.add(CamelHibernateToSupplierRoute.class);
//        camelAutoloadRoutes.add(CamelMailRoute.class);
//        camelAutoloadRoutes.add(CamelMongoToTwitterRoute.class);
//        camelAutoloadRoutes.add(CamelMongoToFacebookRoute.class);
//        camelAutoloadRoutes.add(CamelSupplierJsonToXmlRoute.class);
//        camelAutoloadRoutes.add(CamelSupplierJsonToCsvRoute.class);
//        camelAutoloadRoutes.add(CamelSupplierJsonToJsonRoute.class);
//        camelAutoloadRoutes.add(ScreeningToMongo.class);
//        camelAutoloadRoutes.add(CamelCheckTicketRoute.class);

//        for(String str : ctx.getBeanDefinitionNames()) {
//            logger.info(str);
//        }

        for(Class c : Application.camelAutoloadRoutes)
        {
            Object cBean = ctx.getBean(c);
            if(cBean instanceof org.apache.camel.builder.RouteBuilder == false) {
                logger.error("Configured route for WFPM route: " + c.getClass() + " must extend RouteBuilder");
            }

            RouteBuilder routeBuilder = (RouteBuilder)cBean;
            try {
                camelContext.addRoutes(routeBuilder);
            } catch (Exception e) {
                logger.error("Could not add route: " + routeBuilder.toString() + ". Reason: " + e.getMessage());
            }
        }

        //start of the action
        try {
            camelContext.start();
            //Thread.sleep(10 * 1 * 1000);
            //camelContext.stop();
        } catch (Exception e) {
            logger.error("Fail. Message: " + e.getMessage());
        }
    }
}
