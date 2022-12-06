package ai.zerok.echorelayapp.processor;

import ai.zerok.echorelayapp.utils.QueryResultListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class DependentProcessorApi extends DependentProcessorImpl {

    @Override
    public Object process(String key, String value) throws IOException {
        final String uri = "http://" + value;

        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(uri, Object.class);
        String resultString = objectMapper.writeValueAsString(result);
        JsonNode jsonNode = objectMapper.readTree(resultString);

        return jsonNode;
    }

}
