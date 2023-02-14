package ai.zerok.echorelayapp.parser;

import ai.zerok.echorelayapp.configs.Dependents;
import ai.zerok.echorelayapp.processor.DependentProcessor;
import ai.zerok.echorelayapp.processor.DependentProcessorFactory;
import ai.zerok.echorelayapp.services.TestService;
import ai.zerok.echorelayapp.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value = { "scripts" })
@AllArgsConstructor
public class EndpointSpec {

    private String path;
    private Map<String, Object> response;

    private List<Dependents> dependents;

    public EndpointSpec() {
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }

    public void setDependents(List<Dependents> dependents) {
        this.dependents = dependents;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public List<Dependents> getDependents() {
        return dependents;
    }


    public Map<String, Object> process(TestService testService) throws IOException {
        ApiSpec apiSpec = new ApiSpec();

        if (dependents != null){
            for (Dependents dependent : dependents) {
                DependentProcessor dependentProcessor = DependentProcessorFactory.getDependentProcessor(dependent, testService);
                ApiSpecResponse response = new ApiSpecResponse(dependentProcessor.process());
                apiSpec.getDependents().add(response);
            }
        }

        return processResponses(apiSpec, dependents);
    }

    private Map<String, Object> processResponses(ApiSpec apiSpec, List<Dependents> dependents) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if (!response.containsKey("json")) {
            return response;
        }

        Map<String, Object> responseToReturn = response.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String json = mapper.writeValueAsString(apiSpec);

        String s = responseToReturn.get("json").toString().replace("\n", "");
        TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
        HashMap<String, String> o = mapper.readValue(s, typeRef);

        HashMap<String, Object> innerResponse = new HashMap<>();
        for (String key : o.keySet()) {
            String jsonPath = o.get(key);

            if(jsonPath.contains("new Date()")) {
                innerResponse.put(key, TimeUtils.getFormattedDate());
            } else {
                jsonPath = jsonPath.replace("{", ".");
                jsonPath = jsonPath.replace("}", "");
                DocumentContext jsonContext = JsonPath.parse(json);
                String value;
                value = jsonContext.read(jsonPath);
                innerResponse.put(key, value);
            }
        }
        responseToReturn.put("json", innerResponse);
//        System.out.println(mapper.writeValueAsString(responseToReturn));
        return responseToReturn;
    }
}