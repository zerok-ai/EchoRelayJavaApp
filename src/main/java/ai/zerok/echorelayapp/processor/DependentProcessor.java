package ai.zerok.echorelayapp.processor;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface DependentProcessor {
    public abstract Object process(String key, String value) throws JsonProcessingException, IOException;
}
