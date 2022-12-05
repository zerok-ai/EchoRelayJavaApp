package ai.zerok.echorelayapp.configs;

import ai.zerok.echorelayapp.utils.YamlPropertySourceFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Configuration
//@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "service")
//@PropertySource(value = "classpath:service.json")

public class ServiceConfigs {

    private static ServiceConfigs INSTANCE = null;

    @JsonProperty
    private String name;

    @JsonProperty
    private List<Endpoint> endpoints;

    @JsonIgnore
    private Map<String, Endpoint> pathsToEndpoints;

    static {
        INSTANCE = new ServiceConfigs();
        File file = new File(ServiceConfigs.class.getClassLoader().getResource("service.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        ServiceConfigs serviceConfigs = null;
        try {
            serviceConfigs = mapper.readValue(file, ServiceConfigs.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        INSTANCE.name = "service1";
//        INSTANCE.endpoints = new ArrayList<>();
//        INSTANCE.endpoints.add(new Endpoint("abc"));
        INSTANCE = serviceConfigs;
        INSTANCE.postProcess();
    }

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
