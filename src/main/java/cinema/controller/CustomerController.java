package cinema.controller;

import cinema.model.Movie;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    final static Logger logger = Logger.getLogger(CustomerController.class);
    private static final String URL = "http://localhost:8080/mongo";
    private static final String URL2 = "http://localhost:8080/test";

    @RequestMapping(value = "/test"/*,  produces = MediaType.APPLICATION_JSON_VALUE*/)
    public void testPost() throws URISyntaxException {

        URI uri = new URI(URL2);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        RequestEntity<String> requestEntity = new RequestEntity<String>("##heyhey##", HttpMethod.PUT, uri);
        restTemplate.put(URL2, requestEntity, String.class);
    }
}
