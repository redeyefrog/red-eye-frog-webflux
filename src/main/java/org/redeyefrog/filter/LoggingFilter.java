package org.redeyefrog.filter;

import lombok.extern.slf4j.Slf4j;
import org.redeyefrog.filter.decorator.LoggingExchangeDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LoggingExchangeDecorator decorator = new LoggingExchangeDecorator(exchange);
        return chain.filter(decorator);
    }

}
