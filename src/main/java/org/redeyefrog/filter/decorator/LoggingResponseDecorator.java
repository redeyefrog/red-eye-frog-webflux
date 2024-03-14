package org.redeyefrog.filter.decorator;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class LoggingResponseDecorator extends ServerHttpResponseDecorator {

    private LocalDateTime startTime;

    public LoggingResponseDecorator(ServerHttpResponse delegate, LocalDateTime startTime) {
        super(delegate);
        this.startTime = startTime;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return super.writeWith(Mono.from(body).doOnNext(this::logResponse));
    }

    private void logResponse(DataBuffer dataBuffer) {
        LocalDateTime endTime = LocalDateTime.now();
        log.info(" << Response body: {}, status: {}, content-type: {}, duration: {}ms",
                StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer()),
                getStatusCode(),
                getHeaders().getContentType(),
                Duration.between(startTime, endTime).toMillis());
    }

}
