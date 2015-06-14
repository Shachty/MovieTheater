package cinema.service;

import cinema.dto.mongo.ScreeningMongoDTO;
import cinema.dto.mongo.ScreeningsMongoDTO;
import cinema.model.Screening;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.language.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SocialMediaService{

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
                if (x >= body.length() - 133) {
                    bodyForSplitting += Integer.toString(counterMessages + 1) + "/" + Integer.toString(overallMessages + 1) + " " + body.substring(x) + "~";
                } else {
                    bodyForSplitting += Integer.toString(counterMessages + 1) + "/" + Integer.toString(overallMessages + 1) + " " + body.substring(x, x + 133) + "~";
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
