package ai.zerok.echorelayapp.controller;

import ai.zerok.echorelayapp.configs.ApplicationConfiguration;
import ai.zerok.echorelayapp.configs.Endpoint;
import ai.zerok.echorelayapp.configs.ServiceConfigs;
import ai.zerok.echorelayapp.services.TestService;
import ai.zerok.echorelayapp.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class EchoController {

    private ServiceConfigs serviceConfigs = ServiceConfigs.getInstance();

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping(value="/{path}", method= RequestMethod.GET)
    public Map api(@PathVariable String path)
    {

        Endpoint endpoint = serviceConfigs.getEndpoint(path);
        if(endpoint == null){
            throw new ResourceNotFoundException();
        }

        return endpoint.process(testService);

//        return "Hello GeeksForGeeks";
    }
}
