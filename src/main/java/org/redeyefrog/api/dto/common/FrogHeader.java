package org.redeyefrog.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FrogHeader {

    @JsonProperty("RESULT_CODE")
    private String resultCode;

    @JsonProperty("RESULT_DESC")
    private String resultDesc;

}
