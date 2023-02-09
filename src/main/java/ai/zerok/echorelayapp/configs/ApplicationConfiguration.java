package ai.zerok.echorelayapp.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "service")
public class ApplicationConfiguration {

//    @Value("resource")
//    private String resource;

//    @Value("${service.resource}")
    private String serviceResource;

}
