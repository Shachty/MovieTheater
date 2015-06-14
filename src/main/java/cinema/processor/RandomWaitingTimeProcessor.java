package cinema.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Random;

public class RandomWaitingTimeProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("waitingTime", getRandom((int)(1000*2.5)));
    }

    private long getRandom(int time){
        Random random = new Random();
        return random.nextInt(time);
    }
}
