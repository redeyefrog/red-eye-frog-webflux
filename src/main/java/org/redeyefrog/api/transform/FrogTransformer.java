package org.redeyefrog.api.transform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redeyefrog.api.dto.common.FrogHeader;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.enums.StatusCode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FrogTransformer {

    public static <T> FrogResponse<T> transfer(StatusCode statusCode) {
        return transfer(statusCode, null);
    }

    public static <T> FrogResponse<T> transfer(StatusCode statusCode, T content) {
        return transfer(statusCode.getCode(), statusCode.getDesc(), content);
    }

    public static <T> FrogResponse<T> transfer(String resultCode, String resultDesc) {
        return transfer(resultCode, resultDesc, null);
    }

    public static <T> FrogResponse<T> transfer(String resultCode, String resultDesc, T content) {
        return FrogResponse.<T>builder()
                           .header(FrogHeader.builder()
                                             .resultCode(resultCode)
                                             .resultDesc(resultDesc)
                                             .build())
                           .response(Objects.isNull(content) ? null : content)
                           .build();
    }

    public static <S, T> Mono<ServerResponse> transfer(ParameterizedTypeReference<S> typeReference, StatusCode statusCode) {
        return transfer(typeReference, statusCode, null);
    }

    public static <S, T> Mono<ServerResponse> transfer(ParameterizedTypeReference<S> typeReference, StatusCode statusCode, T content) {
        return transfer(typeReference, statusCode.getCode(), statusCode.getDesc(), content);
    }

    public static <S, T> Mono<ServerResponse> transfer(ParameterizedTypeReference<S> typeReference, String resultCode, String resultDesc, T content) {
        return ServerResponse.ok()
                             .body(Mono.just(transfer(resultCode, resultDesc, content)), typeReference);
    }

}
