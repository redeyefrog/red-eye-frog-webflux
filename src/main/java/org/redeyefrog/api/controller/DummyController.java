package org.redeyefrog.api.controller;

import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.company.FindCompanyQueryCondition;
import org.redeyefrog.api.dto.company.FindCompanyQueryResult;
import org.redeyefrog.api.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/dummy/api/v1")
@RestController
public class DummyController {

    @Autowired
    DummyService dummyService;

    @PostMapping("/findCompany")
    public Mono<FrogResponse<FindCompanyQueryResult>> findCompany(@RequestBody FindCompanyQueryCondition condition) {
        return dummyService.findCompany(condition);
    }

}
