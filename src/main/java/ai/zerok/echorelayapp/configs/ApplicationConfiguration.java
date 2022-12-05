package ai.zerok.echorelayapp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "service")
public class ApplicationConfiguration {

//    @Value("resource")
//    private String resource;

    @Value("${service.resource}")
    private String serviceResource;

}
