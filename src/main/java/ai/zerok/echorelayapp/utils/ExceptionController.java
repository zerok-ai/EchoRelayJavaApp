package ai.zerok.echorelayapp.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Throwable.class)
    public String runtimeException(Throwable ex) {
        throw new RuntimeException(ex);
    }

}
