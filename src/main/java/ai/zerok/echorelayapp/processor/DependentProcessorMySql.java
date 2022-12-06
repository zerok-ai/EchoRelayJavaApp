package ai.zerok.echorelayapp.processor;

import ai.zerok.echorelayapp.services.TestService;
import ai.zerok.echorelayapp.utils.QueryResultListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DependentProcessorMySql extends DependentProcessorImpl {

    private TestService testService;

    @Override
    public Object process( String key, String value) throws IOException {
        return testService.executeRawQUeryMySQL(value, null);
    }

}
