package org.redeyefrog.api.handler;

import org.redeyefrog.api.dto.common.FrogRequest;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.company.CompanyRequest;
import org.redeyefrog.api.dto.company.CompanyResponse;
import org.redeyefrog.api.dto.company.FindCompanyQueryCondition;
import org.redeyefrog.api.dto.company.FindCompanyQueryResult;
import org.redeyefrog.api.service.CompanyService;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.api.validator.CompanyValidator;
import org.redeyefrog.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CompanyHandler {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyValidator companyValidator;

    public Mono<ServerResponse> find(ServerRequest request) {
        FindCompanyQueryCondition queryCondition = new FindCompanyQueryCondition();
        queryCondition.setCompanyUid(request.queryParam("companyUid").orElse(null));
        queryCondition.setCompanyName(request.queryParam("companyName").orElse(null));
        return Mono.just(queryCondition)
                   .flatMap(condition -> companyService.find(condition))
                   .flatMap(companies -> FrogTransformer.transfer(new ParameterizedTypeReference<FrogResponse<FindCompanyQueryResult>>() {
                   }, StatusCode.SUCCESS, FindCompanyQueryResult.builder()
                                                                .companyList(companies)
                                                                .build()));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<FrogRequest<CompanyRequest>>() {
                      })
                      .map(companyValidator::validateSave)
                      .flatMap(frogRequest -> companyService.save(frogRequest.getRequest()))
                      .flatMap(isSuccess -> FrogTransformer.transfer(new ParameterizedTypeReference<FrogResponse<CompanyResponse>>() {
                      }, isSuccess ? StatusCode.SUCCESS : StatusCode.FAIL));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<FrogRequest<CompanyRequest>>() {
                      })
                      .map(companyValidator::validateUpdate)
                      .flatMap(frogRequest -> companyService.update(frogRequest.getRequest()))
                      .flatMap(isSuccess -> FrogTransformer.transfer(new ParameterizedTypeReference<FrogResponse<CompanyResponse>>() {
                      }, isSuccess ? StatusCode.SUCCESS : StatusCode.FAIL));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String uid = request.queryParam("uid").orElseGet(() -> request.pathVariable("uid"));
        return companyService.delete(uid)
                             .flatMap(isSuccess -> FrogTransformer.transfer(new ParameterizedTypeReference<FrogResponse<CompanyResponse>>() {
                             }, isSuccess ? StatusCode.SUCCESS : StatusCode.FAIL));
    }

}
