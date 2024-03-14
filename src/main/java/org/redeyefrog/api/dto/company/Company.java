package org.redeyefrog.api.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Company {

    @JsonProperty("Company_Uid")
    private String companyUid;

    @JsonProperty("Company_Name")
    private String companyName;

    @JsonProperty("Company_Address")
    private String companyAddress;

    @JsonProperty("Company_Phone")
    private String companyPhone;

    @JsonProperty("Company_Email")
    private String companyEmail;

    @JsonProperty("Update_Time")
    private String updateTime;

}
