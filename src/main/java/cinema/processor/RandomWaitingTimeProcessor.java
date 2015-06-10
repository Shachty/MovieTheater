package cinema.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Random;

/**
 * Created by Asus on 27.05.2015.
 */
public class RandomWaitingTimeProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("waitingTime", getRandom(1000*10));
    }

    private long getRandom(int time){
        Random random = new Random();
        return random.nextInt(time);
    }
}
