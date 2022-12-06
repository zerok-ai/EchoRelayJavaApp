package ai.zerok.echorelayapp.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasourceConfig {

//    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC
    @Value("${spring.datasource.url}")
    private String url;

//    spring.datasource.username=user
    @Value("${spring.datasource.username}")
    private String user;

//    spring.datasource.password=password
    @Value("${spring.datasource.password}")
    private String password;

//    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

//    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
    @Value("${spring.datasource.driverClassName}")
    private String driver;

}
