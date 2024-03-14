package org.redeyefrog.handler;

import lombok.extern.slf4j.Slf4j;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class FrogExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Mono<FrogResponse> onBindException(BindException e) {
        List<String> messages = e.getAllErrors()
                                 .stream()
                                 .map(ObjectError::getDefaultMessage)
                                 .collect(Collectors.toList());
        return Mono.just(FrogTransformer.transfer(StatusCode.VALIDATE_ERROR.getCode(), String.join(",", messages)));
    }

    @ExceptionHandler(FrogRuntimeException.class)
    public Mono<FrogResponse> onFrogRuntimeException(FrogRuntimeException e) {
        log.error(e.getMessage(), e.getCause());
        return Mono.just(FrogTransformer.transfer(e.getStatusCode()));
    }

    @ExceptionHandler(Throwable.class)
    public Mono<FrogResponse> onThrowable(Throwable t) {
        log.error(t.getMessage(), t);
        return Mono.just(FrogTransformer.transfer(StatusCode.SYSTEM_ERROR));
    }

}
