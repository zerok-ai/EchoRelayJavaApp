package ai.zerok.echorelayapp.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Apis {

    private String kind;

    @JsonProperty("metadata")
    private Map<String, String> metaData;

    private List<EndpointSpec> spec;

    public Apis() {
    }

    public Apis(String kind, Map<String, String> metaData, List<EndpointSpec> spec) {
        this.kind = kind;
        this.metaData = metaData;
        this.spec = spec;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public void setSpec(List<EndpointSpec> spec) {
        this.spec = spec;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public List<EndpointSpec> getSpec() {
        return spec;
    }
}