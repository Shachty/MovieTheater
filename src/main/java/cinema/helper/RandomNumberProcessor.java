package cinema.helper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Random;

/**
 * Created by Asus on 27.05.2015.
 */
public class RandomNumberProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("waitingTime", getRandom(1000*20));
    }

    private long getRandom(int time){
        Random random = new Random();
        return random.nextInt(time);
    }
}
