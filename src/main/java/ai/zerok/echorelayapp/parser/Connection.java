package ai.zerok.echorelayapp.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Connection {

    private String kind;

    @JsonProperty("metadata")
    private Map<String, String> metaData;

    private Spec spec;

    public Connection(String kind, Map<String, String> metaData, Spec spec) {
        this.kind = kind;
        this.metaData = metaData;
        this.spec = spec;
    }

    public Connection() {
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    public String getKind() {
        return kind;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public Spec getSpec() {
        return spec;
    }
}