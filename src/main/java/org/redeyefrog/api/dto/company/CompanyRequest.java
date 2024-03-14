package org.redeyefrog.api.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CompanyRequest {

    @NotBlank(message = "Company_Uid is Blank")
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

}
