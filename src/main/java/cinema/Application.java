package cinema;

import cinema.routes.*;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;

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

        // Camel properties
        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("cinema.properties");
        camelContext.addComponent("properties", pc);

        camelAutoloadRoutes = new ArrayList<>();
        camelAutoloadRoutes.add(CamelCsvToHibernateRoute.class);
        camelAutoloadRoutes.add(CamelHttpReservationRoute.class);
        camelAutoloadRoutes.add(CamelCsvToHibernateRoute.class);
        camelAutoloadRoutes.add(CamelMongoRoute.class);
        camelAutoloadRoutes.add(CamelHibernateToSupplierRoute.class);
        camelAutoloadRoutes.add(CamelMailRoute.class);
        camelAutoloadRoutes.add(CamelConsumeTicketRoute.class);
       camelAutoloadRoutes.add(CamelMongoToSocialMediaRoute.class);
        camelAutoloadRoutes.add(CamelSupplierJsonToCsvRoute.class);
        camelAutoloadRoutes.add(CamelSupplierJsonToJsonRoute.class);
        camelAutoloadRoutes.add(CamelSupplierJsonToXmlRoute.class);
        camelAutoloadRoutes.add(CamelSupplierPublishSubscribeRoute.class);

        camelAutoloadRoutes.add(CamelHttpSnackOrderingRoute.class);

//        camelAutoloadRoutes.add(CamelMongoToTwitterRoute.class);
//        camelAutoloadRoutes.add(CamelMongoToFacebookRoute.class);

        camelAutoloadRoutes.add(CamelReserveTicketRoute.class);
        camelAutoloadRoutes.add(CamelSellTicketRoute.class);
        camelAutoloadRoutes.add(CamelTicketCheckerRoute.class);
        camelAutoloadRoutes.add(CamelChooseSupplierRoute.class);
        camelAutoloadRoutes.add(CamelShowScreeningsRoute.class);
 //       camelAutoloadRoutes.add(ScreeningToMongo.class);

//        for(String str : ctx.getBeanDefinitionNames()) {
//            logger.info(str);
//        }

        for(Class c : Application.camelAutoloadRoutes)
        {
            Object cBean = ctx.getBean(c);
            if(cBean instanceof org.apache.camel.builder.RouteBuilder == false) {
                logger.error("Configured route for WFPM route: " + c.getClass() + " must extend RouteBuilder");
                continue;
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
            //camelContext.stop();
        } catch (Exception e) {
            logger.error("Fail. Message: " + e.getMessage());
        }
    }
}
