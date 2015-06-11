package cinema.service;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.language.Bean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnitaS on 10.06.2015.
 */
@Component
public class SocialMediaService {

    /**
     * The split body method returns something that is iteratable such as a java.util.List.
     *
     * @param body the payload of the incoming message
     * @return a list containing each part splitted
     */
    public List<String> splitBody(String body) {
        List<String> answer = new ArrayList<String>();

        int x = 0;
        String bodyForSplitting = "";
        int counterMessages = 0;
        int overallMessages = (int) Math.ceil(body.length() / 140);

        if (overallMessages == 0) {
            answer.add(body);
            return answer;
        } else {
            while (counterMessages <= overallMessages) {
                if (x >= body.length()-133) {
                    bodyForSplitting += Integer.toString(counterMessages+1) + "/" + Integer.toString(overallMessages+1) + " " + body.substring(x) + "~";
                } else {
                    bodyForSplitting += Integer.toString(counterMessages+1) + "/" + Integer.toString(overallMessages+1) + " " + body.substring(x, x + 133) + "~";
                }

                x += 133;
                counterMessages += 1;
            }

            String[] parts = bodyForSplitting.split("~");
            for (String part : parts) {
                answer.add(part);
            }

            return answer;
        }
    }
}
