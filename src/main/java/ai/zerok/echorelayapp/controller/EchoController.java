package ai.zerok.echorelayapp.controller;

import ai.zerok.echorelayapp.configs.ServiceConfigs;
import ai.zerok.echorelayapp.parser.EndpointSpec;
import ai.zerok.echorelayapp.services.TestService;
import ai.zerok.echorelayapp.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
public class EchoController {

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping(value= {"/{path}", "/"}, method= RequestMethod.GET)
    public Map api(@PathVariable(value="path",required = false) String path) throws IOException {

        EndpointSpec endpoint = ServiceConfigs.getInstance().getEndpoint(path);
        if(endpoint == null){
            throw new ResourceNotFoundException();
        }

        return endpoint.process(testService);
    }
}
