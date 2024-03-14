package org.redeyefrog.api.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FindCompanyQueryResult {

    @JsonProperty("Company_List")
    private List<Company> companyList;

}
