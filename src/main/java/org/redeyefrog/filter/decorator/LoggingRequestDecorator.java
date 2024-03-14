package org.redeyefrog.filter.decorator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class LoggingRequestDecorator extends ServerHttpRequestDecorator {

    private final MediaType contentType;

    private final List<MediaType> loggingFromGetBody = Arrays.asList(MediaType.APPLICATION_JSON);

    private String requestBody;

    public LoggingRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        contentType = getHeaders().getContentType();
        if (Objects.isNull(contentType) || !loggingFromGetBody.contains(contentType)) {
            logRequest(null);
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(this::logRequest);
    }

    private void logRequest(DataBuffer dataBuffer) {
        requestBody = Objects.isNull(dataBuffer) ? null : StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer()).toString().replaceAll("\r|\n|\t", "");
        String parameters = getQueryParams().entrySet()
                                            .stream()
                                            .map(entry -> String.format("%s=%s", entry.getKey(), String.join(",", entry.getValue())))
                                            .collect(Collectors.joining(";"));
        log.info(" >> Request body: {}, parameters: {}, url: {}, method: {}, content-type: {}",
                requestBody,
                parameters,
                getURI(),
                getMethod(),
                contentType);
    }

}
