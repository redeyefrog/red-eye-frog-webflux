package org.redeyefrog.integration.proxy;

import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientProxy {

    @Autowired
    protected WebClient webClient;

    protected <Q, S> Mono<S> callTelegramMono(String uri, Q request, Class<S> responseClass) {
        return webClient.post()
                        .uri(uri)
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::exceptionMono)
                        .bodyToMono(responseClass)
                        .doOnError(e -> new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e));
    }

    protected <Q, S> Mono<S> callTelegramMono(String uri, Q request, ParameterizedTypeReference<S> responseType) {
        return webClient.post()
                        .uri(uri)
                        .bodyValue(request)
                        .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().isError()) {
                                return clientResponse.createException()
                                                     .flatMap(e -> Mono.error(new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e)));
                            }
                            return clientResponse.bodyToMono(responseType);
                        })
                        .doOnError(e -> new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e));
    }

    protected <Q, S> Flux<S> callTelegramFlux(String uri, Q request, Class<S> responseClass) {
        return webClient.post()
                        .uri(uri)
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(HttpStatus::isError, clientResponse -> clientResponse.createException()
                                                                                       .flatMap(e -> Mono.error(new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e))))
                        .bodyToFlux(responseClass)
                        .doOnError(e -> new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e));
    }

    protected <Q, S> Flux<S> callTelegramFlux(String uri, Q request, ParameterizedTypeReference<S> responseType) {
        return webClient.post()
                        .uri(uri)
                        .bodyValue(request)
                        .exchangeToFlux(clientResponse -> {
                            if (clientResponse.statusCode().isError()) {
                                return clientResponse.createException()
                                                     .flatMapMany(e -> Flux.error(new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e)));
                            }
                            return clientResponse.bodyToFlux(responseType);
                        })
                        .doOnError(e -> new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e));
    }

    private Mono<FrogRuntimeException> exceptionMono(ClientResponse clientResponse) {
        return clientResponse.createException()
                             .flatMap(e -> Mono.error(new FrogRuntimeException(StatusCode.CALL_TELEGRAM_ERROR, e)));
    }

}
