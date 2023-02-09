package ai.zerok.echorelayapp.processor;

import ai.zerok.echorelayapp.configs.DependentType;
import ai.zerok.echorelayapp.services.TestService;

public class DependentProcessorFactory {

    public static DependentProcessor getDependentProcessor(DependentType dependentType, Object... params){
        DependentProcessor dependentProcessor = new DependentProcessorApi();
        switch (dependentType){
            case API -> dependentProcessor = new DependentProcessorApi();
            case NONE -> dependentProcessor = new DependentProcessorApi();
            case MYSQL -> dependentProcessor = new DependentProcessorMySql((TestService) params[0]);
            case MONGO -> dependentProcessor = new DependentProcessorMongo((TestService) params[0]);
        }

        return dependentProcessor;
    }

}
