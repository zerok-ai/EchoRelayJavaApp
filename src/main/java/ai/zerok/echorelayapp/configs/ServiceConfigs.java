package ai.zerok.echorelayapp.configs;

import ai.zerok.echorelayapp.parser.Apis;
import ai.zerok.echorelayapp.parser.Connection;
import ai.zerok.echorelayapp.parser.EndpointSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@Configuration
@EnableConfigurationProperties
public class ServiceConfigs {

    private static ServiceConfigs INSTANCE = null;

    private String ENV_CONF_FILE ="CONF_FILE";

    String convertYamlToJson(String yaml) throws JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }

    @PostConstruct
    public void init() throws IOException {
        INSTANCE = this;
        ObjectMapper mapper = new ObjectMapper();
        ServiceConfigs serviceConfigs = new ServiceConfigs();
        String filePath = "/Users/vaibhavpaharia/ok/EchoRelayJavaApp/src/main/resources/service1-definition.yaml";
//        String filePath = System.getenv(ENV_CONF_FILE);

        String content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
//        System.out.println(content);
        String[] s = content.split("---");

        for (String item : s) {
            String jsonPath = "$.kind";
            String jsonString = convertYamlToJson(item);

            DocumentContext jsonContext = JsonPath.parse(jsonString);
            String value = jsonContext.read(jsonPath);

            if (value.equals("Connection")) {
                serviceConfigs.setConnection(mapper.readValue(jsonString, Connection.class));
            } else if (value.equals("APIs")) {
                serviceConfigs.setApis(mapper.readValue(jsonString, Apis.class));
            }
        }

//        serviceConfigs.setConnection(mapper.readValue(convertYamlToJson(s[0]), Connection.class));
//        serviceConfigs.setApis(mapper.readValue(convertYamlToJson(s[1]), Apis.class));

//        createConfigFileAndCopyContents();

//        String filePathYaml = "service-definition.yaml";
//        Yaml yaml = new Yaml();
//        InputStream inputStream = this.getClass()
//                .getClassLoader()
//                .getResourceAsStream(filePathYaml);
//        for (Object object : yaml.loadAll(inputStream)) {
//            Map<String, Object> o = (Map<String, Object>) object;
//
//            if (o.get("kind").equals("Connection")) {
//                connection = mapper.convertValue(o, Connection.class);
//                continue;
//            } else if (o.get("kind").equals("APIs")) {
//                apis = mapper.convertValue(o, Apis.class);
//            } else {
//                continue;
//            }
//
//            serviceConfigs.setApis(apis);
//            serviceConfigs.setConnection(connection);
//        }

        INSTANCE = serviceConfigs;
        INSTANCE.postProcess();
    }

    private void createConfigFileAndCopyContents() throws IOException {
        String destination = "service-definition.yaml";
        System.err.println(destination);
        File file = new File(destination);
        new PrintWriter(destination).close();
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
//        System.err.println("previou file deleted:" + file.delete());
        if (file.exists()) {
            System.err.println("File already exists.");
        } else {
            System.err.println("File is created!");
        }

//        String source = System.getenv(ENV_CONF_FILE);
        String source = "/Users/vaibhavpaharia/ok/EchoRelayJavaApp/src/main/resources/service1-definition.yaml";
        FileChannel src = new FileInputStream(source).getChannel();
        FileChannel dest = new FileOutputStream(destination).getChannel();
        dest.transferFrom(src, 0, src.size());
    }

    @JsonProperty
    private String name;

    @JsonIgnore
    private Apis apis;

    @JsonIgnore
    private Map<String, EndpointSpec> endpointMap;

    @JsonIgnore Connection connection;



    private void postProcess() {
        if (endpointMap == null) {
            endpointMap = new HashMap<>();
            for (EndpointSpec e : apis.getSpec()) {
                endpointMap.put(e.getPath(), e);
            }
        }
    }

    public EndpointSpec getEndpoint(String path){
        if (path == null) {
            return endpointMap.get("/");
        }
        return endpointMap.get("/"+path);
    }

    public static ServiceConfigs getInstance(){
        return INSTANCE;
    }

    public Apis getApis() {
        return apis;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setApis(Apis apis) {
        this.apis = apis;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
