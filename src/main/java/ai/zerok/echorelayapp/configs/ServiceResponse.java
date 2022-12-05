package ai.zerok.echorelayapp.configs;

import ai.zerok.echorelayapp.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

    @JsonProperty
    private HashMap<String, String> data;

    @JsonIgnore
    public Map<String, Object> createResponse(){
        Map<String, Object> response = new HashMap<>();
        this.data.keySet().forEach(key -> {
            String valueRule = this.data.get(key);
            //TODO: do something better here
            if(valueRule.startsWith("${{")){
                if(valueRule.equals("${{date}}")){
                    response.put(key, TimeUtils.getFormattedDate());
                }
            }else{
                String[] valueRuleSplits = valueRule.split(".");
            }

        });

        return response;
    }
}
