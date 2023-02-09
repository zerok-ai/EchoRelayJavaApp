package ai.zerok.echorelayapp.configs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private final String CONF_FILE = "CONF_FILE";

    @PostConstruct
    public void init() throws IOException {
        INSTANCE = this;
        ObjectMapper mapper = new ObjectMapper();
        ServiceConfigs serviceConfigs = null;
        String filePath = System.getenv(CONF_FILE);
        Path fileName = Path.of(filePath);
        String content = Files.readString(fileName);

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
