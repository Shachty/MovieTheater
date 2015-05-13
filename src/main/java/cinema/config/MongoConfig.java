package cinema.config;

import com.mongodb.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.UnknownHostException;
import java.util.Arrays;


/**
 * Created by Daniel on 10.05.2015.
 */
@Configuration
public class MongoConfig {


    final static Logger logger = Logger.getLogger(MongoConfig.class);


    @Bean
    public DB db() throws Exception {


        try{
           return mongoClient().getDB("workflow");
        } catch (UnknownHostException e) {
            logger.error("Can not acces MongoClient. Message : " + e.getMessage());
        }

        throw new Exception();

    }

   @Bean
    public MongoClient mongoClient() throws UnknownHostException {

        return new MongoClient(new ServerAddress("ds031882.mongolab.com", 31882), Arrays.asList(mongoCredential()));

    }

    @Bean
    public MongoCredential mongoCredential(){
        return MongoCredential.createMongoCRCredential("shachty", "workflow", "workflow".toCharArray());
    }

    @Bean
    public Mongo mongoBean() throws Exception {
        try {
            return new Mongo("ds031882.mongolab.com", 31882);
        } catch (UnknownHostException e) {
            logger.error("Can not acces MongoClient. Message : " + e.getMessage());
        }
        throw new Exception();
    }

}
