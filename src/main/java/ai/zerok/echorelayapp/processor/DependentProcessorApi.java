package ai.zerok.echorelayapp.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class DependentProcessorApi extends DependentProcessorImpl {

    private String value;

    @Override
    public Object process() throws IOException {
        final String uri = "http://" + value;

        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(uri, Object.class);
        String resultString = objectMapper.writeValueAsString(result);
        JsonNode jsonNode = objectMapper.readTree(resultString);

        return jsonNode;
    }

}
