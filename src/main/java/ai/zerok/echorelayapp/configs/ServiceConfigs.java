package ai.zerok.echorelayapp.configs;

import ai.zerok.echorelayapp.utils.ClassPathResourceReader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Configuration
//@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "service")
//@PropertySource(value = "classpath:service.json")

@Configuration
@EnableConfigurationProperties
public class ServiceConfigs {

    private static ServiceConfigs INSTANCE = null;

    @Value("${service.resource}")
    private String resourceName;

    @PostConstruct
    public void init() {
        INSTANCE = this;
        ObjectMapper mapper = new ObjectMapper();
        ServiceConfigs serviceConfigs = null;
        String content = new ClassPathResourceReader(resourceName).getContent();
        try {
            serviceConfigs = mapper.readValue(content, ServiceConfigs.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        INSTANCE = serviceConfigs;
        INSTANCE.postProcess();
    }

    @JsonProperty
    private String name;

    @JsonProperty
    private List<Endpoint> endpoints;

    @JsonIgnore
    private Map<String, Endpoint> pathsToEndpoints;

    private void postProcess() {
        if(endpoints != null){
            pathsToEndpoints = endpoints.stream()
//                    .map(Endpoint::getPath)
                    .collect(Collectors.toMap(Endpoint::getPath, (s) -> {
                        return s;
                    }));
        }
    }

    public List<String> getPaths() {
//        if(paths == null){
//            return new ArrayList<>();
//        }
//        return paths;
        return new ArrayList<>();
    }

    public boolean isValidEndpoint(String endpoint){
        return getPaths().contains(endpoint);
    }

    public Endpoint getEndpoint(String path){
        return pathsToEndpoints.get(path);
    }


    public static ServiceConfigs getInstance(){
        return INSTANCE;
    }
}
