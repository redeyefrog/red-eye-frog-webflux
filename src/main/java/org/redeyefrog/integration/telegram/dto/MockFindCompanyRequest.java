package org.redeyefrog.integration.telegram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MockFindCompanyRequest {

    @JsonProperty("COMPANY_ID")
    private String companyId;

}
