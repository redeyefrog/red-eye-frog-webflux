package org.redeyefrog.api.router;

import org.redeyefrog.api.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloRouter {

    @Bean
    RouterFunction<ServerResponse> greeting(HelloHandler helloHandler) {
//        return RouterFunctions.route(RequestPredicates.GET("/hello/{name}"), helloHandler::sayHello);
        return RouterFunctions.route()
                              .GET("/hello/{name}", helloHandler::sayHello)
                              .build();
    }

}
