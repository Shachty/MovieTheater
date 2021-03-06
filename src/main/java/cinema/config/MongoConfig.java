package cinema.config;

import com.mongodb.*;
import org.apache.camel.component.mongodb.MongoDbComponent;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.UnknownHostException;
import java.util.Arrays;

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

    @Bean(name = "mongoBean")
    public Mongo mongoBean() throws Exception {
        try {
            Mongo mongo = new Mongo("ds031882.mongolab.com", 31882);
            mongo.getDB("workflow").authenticate("shachty","workflow".toCharArray());
            return mongo;
        } catch (UnknownHostException e) {
            logger.error("Can not access MongoClient. Message : " + e.getMessage());
        }
        throw new Exception();
    }

    @Bean(name = "mongodb")
    public MongoDbComponent mongodbBean() {
        return new MongoDbComponent();
    }

}
