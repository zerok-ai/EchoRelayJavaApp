package ai.zerok.echorelayapp.configs;


import ai.zerok.echorelayapp.processor.DependentProcessor;
import ai.zerok.echorelayapp.processor.DependentProcessorFactory;
import ai.zerok.echorelayapp.services.TestService;
import ai.zerok.echorelayapp.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endpoint {
    @JsonProperty
    private String path;

    @JsonProperty
    private DependentType type = DependentType.NONE;

    @JsonProperty
    private Dependents dependents;

    @JsonProperty
    private ServiceResponse response;

    @JsonIgnore
    public Map<String, Object> process(TestService testService) {
        Map<String, Object> responseToReturn = new HashMap<>();
        response.getData().keySet().forEach(key -> {
            String valueRule = response.getData().get(key);
            //TODO: do something better here
            if(valueRule.startsWith("${{")){
                if(valueRule.equals("${{date}}")){
                    responseToReturn.put(key, TimeUtils.getFormattedDate());
                }
            }else{
//                String[] valueRuleSplits = valueRule.split(".");
                if(dependents != null){
                    DependentProcessor dependentProcessor = DependentProcessorFactory.getDependentProcessor(type, testService);
                    for (String dependentKey : dependents.keySet()){
                        try {
                            Object dependentResponse = dependentProcessor.process(dependentKey, dependents.get(key));
                            switch (type){
                                case API:
                                    JsonNode jsonNode = (JsonNode) dependentResponse;
                                    Object value = jsonNode.at(valueRule);
                                    responseToReturn.put(key, value);
                                    break;
                                case MYSQL:
                                    String mysqlResult = String.valueOf(dependentResponse);
                                    responseToReturn.put(key, mysqlResult);
                                    break;
                            }
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        });

        return responseToReturn;
    }
}
