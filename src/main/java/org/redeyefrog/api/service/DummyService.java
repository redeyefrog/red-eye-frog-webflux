package org.redeyefrog.api.service;

import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.company.Company;
import org.redeyefrog.api.dto.company.FindCompanyQueryCondition;
import org.redeyefrog.api.dto.company.FindCompanyQueryResult;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.integration.telegram.MockWebClientProxy;
import org.redeyefrog.integration.telegram.dto.MockFindCompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DummyService {

    @Autowired
    MockWebClientProxy mockWebClientProxy;

    public Mono<FrogResponse<FindCompanyQueryResult>> findCompany(FindCompanyQueryCondition condition) {
//        return condition.flatMap(queryCondition -> {
//            MockFindCompanyRequest findCompanyRequest = MockFindCompanyRequest.builder()
//                                                                              .companyId(queryCondition.getCompanyId())
//                                                                              .build();
//            return mockWebClientProxy.findCompany(findCompanyRequest)
//                                     .map(company -> Company.builder()
//                                                            .companyUid(company.getUid())
//                                                            .companyName(company.getName())
//                                                            .companyAddress(company.getAddress())
//                                                            .companyTel(company.getTel())
//                                                            .build())
//                                     .collectList()
//                                     .map(companies -> FrogTransformer.transfer(StatusCode.SUCCESS, FindCompanyQueryResult.builder()
//                                                                                                                          .companyList(companies)
//                                                                                                                          .build()));
//        });
        MockFindCompanyRequest mockFindCompanyRequest = MockFindCompanyRequest.builder()
                                                                              .companyId(condition.getCompanyUid())
                                                                              .build();
        return mockWebClientProxy.findCompany(mockFindCompanyRequest)
                                 .map(company -> Company.builder()
                                                        .companyUid(company.getUid())
                                                        .companyName(company.getName())
                                                        .companyAddress(company.getAddress())
                                                        .companyPhone(company.getTel())
                                                        .build())
                                 .collectList()
                                 .map(companies -> FrogTransformer.transfer(StatusCode.SUCCESS, FindCompanyQueryResult.builder()
                                                                                                                      .companyList(companies)
                                                                                                                      .build()));
    }

}
