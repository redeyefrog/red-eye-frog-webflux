package org.redeyefrog.api.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCompanyQueryCondition {

    @JsonProperty("company_uid")
    private String companyUid;

    @JsonProperty("company_name")
    private String companyName;

}
