package org.redeyefrog.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FrogResponse<T> {

    @JsonProperty("FROG_HEADER")
    private FrogHeader header;

    @JsonProperty("FROG_RESPONSE")
    private T response;

}
