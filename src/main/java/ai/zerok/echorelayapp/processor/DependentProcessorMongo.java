package ai.zerok.echorelayapp.processor;

import ai.zerok.echorelayapp.services.TestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DependentProcessorMongo extends DependentProcessorImpl {

    private TestService testService;

    @Override
    public Object process() throws IOException {
        return testService.executeRawQueryMongo();
    }
}
