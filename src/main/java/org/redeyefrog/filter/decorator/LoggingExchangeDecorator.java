package org.redeyefrog.filter.decorator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

import java.time.LocalDateTime;

public class LoggingExchangeDecorator extends ServerWebExchangeDecorator {

    private LoggingRequestDecorator requestDecorator;

    private LoggingResponseDecorator responseDecorator;

    public LoggingExchangeDecorator(ServerWebExchange delegate) {
        super(delegate);
        this.requestDecorator = new LoggingRequestDecorator(delegate.getRequest());
        this.responseDecorator = new LoggingResponseDecorator(delegate.getResponse(), LocalDateTime.now());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }

}
