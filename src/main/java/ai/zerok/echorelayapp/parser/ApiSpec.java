package ai.zerok.echorelayapp.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("apiSpec")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)

public class ApiSpec {
    private List<ApiSpecResponse> dependents;

    public ApiSpec() {
        dependents = new ArrayList<>();
    }

    public List<ApiSpecResponse> getDependents() {
        return dependents;
    }

    public void setDependents(List<ApiSpecResponse> dependents) {
        this.dependents = dependents;
    }
}
