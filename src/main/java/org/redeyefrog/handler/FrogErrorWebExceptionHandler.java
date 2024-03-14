package org.redeyefrog.handler;

import lombok.extern.slf4j.Slf4j;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Order(-2)
@Component
public class FrogErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public FrogErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
//                             .body(BodyInserters.fromValue(getErrorAttributes(request, ErrorAttributeOptions.defaults())));
                             .body(BodyInserters.fromValue(getBody(getError(request))));
    }

    private FrogResponse getBody(Throwable t) {
        if (t instanceof FrogRuntimeException) {
            FrogRuntimeException e = (FrogRuntimeException) t;
            logging(e.getCause());
            return FrogTransformer.transfer(e.getStatusCode().getCode(), e.getMessage());
        }
        logging(t);
        return FrogTransformer.transfer(StatusCode.SYSTEM_ERROR);
    }

    private void logging(Throwable t) {
        if (!Objects.isNull(t)) {
            log.error(t.getMessage(), t);
        }
    }

}
