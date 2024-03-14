package org.redeyefrog.api.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Component
public class HelloHandler {

    public Mono<ServerResponse> sayHello(ServerRequest request) {
        String greeting = MessageFormat.format("Hello {0}", request.pathVariable("name"));
        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_PLAIN)
                             .bodyValue(greeting)
//                             .body(BodyInserters.fromValue(greeting))
//                             .body(Mono.just(greeting), String.class)
                             .switchIfEmpty(ServerResponse.notFound()
                                                          .build());
    }

}
