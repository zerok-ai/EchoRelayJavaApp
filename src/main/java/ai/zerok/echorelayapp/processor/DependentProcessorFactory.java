package ai.zerok.echorelayapp.processor;

import ai.zerok.echorelayapp.configs.Dependents;
import ai.zerok.echorelayapp.services.TestService;

public class DependentProcessorFactory {

    public static DependentProcessor getDependentProcessor(Dependents dependent, TestService testService){
        DependentProcessor dependentProcessor = new DependentProcessorApi();
        if (dependent.containsKey("path")) {
            String path = dependent.get("path");
            return new DependentProcessorApi(path);
        } else if  (dependent.containsKey("kind")) {
            if (dependent.get("name").equals("mysqlConn")) {
                String query = dependent.get("query");
                return new DependentProcessorMySql(testService, query);
            } else if (dependent.get("name").equals("mongoConn")) {
                return new DependentProcessorMongo(testService);
            }
        }

        return dependentProcessor;
    }

}
