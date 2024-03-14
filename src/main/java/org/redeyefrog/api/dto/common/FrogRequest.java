package org.redeyefrog.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FrogRequest<T> {

    @Valid
    @JsonProperty("FROG_HEADER")
    private FrogHeader header;

    @Valid
    @NotNull(message = "Request is null")
    @JsonProperty("FROG_REQUEST")
    private T request;

}
