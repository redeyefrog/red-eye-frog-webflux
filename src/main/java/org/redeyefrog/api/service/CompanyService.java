package org.redeyefrog.api.service;

import org.redeyefrog.api.dto.company.Company;
import org.redeyefrog.api.dto.company.CompanyRequest;
import org.redeyefrog.api.dto.company.FindCompanyQueryCondition;
import org.redeyefrog.persistence.dao.CompanyDao;
import org.redeyefrog.persistence.entity.CompanyEntity;
import org.redeyefrog.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    CompanyDao companyDao;

    public Mono<List<Company>> find(FindCompanyQueryCondition condition) {
        return Mono.just(companyDao.findByCondition(condition.getCompanyUid(), condition.getCompanyName())
                                   .stream()
                                   .map(entity -> Company.builder()
                                                         .companyUid(entity.getUid())
                                                         .companyName(entity.getName())
                                                         .companyAddress(entity.getAddress())
                                                         .companyPhone(entity.getPhone())
                                                         .companyEmail(entity.getEmail())
                                                         .updateTime(Objects.isNull(entity.getUpdateTime()) ?
                                                                 DateUtils.formatDate(entity.getCreateTime()) : DateUtils.formatDate(entity.getUpdateTime()))
                                                         .build())
                                   .collect(Collectors.toList()));
    }

    public Mono<Boolean> save(CompanyRequest request) {
        CompanyEntity companyEntity = CompanyEntity.builder()
                                                   .uid(request.getCompanyUid())
                                                   .name(request.getCompanyName())
                                                   .address(request.getCompanyAddress())
                                                   .phone(request.getCompanyPhone())
                                                   .email(request.getCompanyEmail())
                                                   .build();
        return Mono.just(companyDao.insert(companyEntity) > 0);
    }

    public Mono<Boolean> update(CompanyRequest request) {
        CompanyEntity companyEntity = CompanyEntity.builder()
                                                   .uid(request.getCompanyUid())
                                                   .name(request.getCompanyName())
                                                   .address(request.getCompanyAddress())
                                                   .phone(request.getCompanyPhone())
                                                   .email(request.getCompanyEmail())
                                                   .build();
        return Mono.just(companyDao.update(companyEntity) > 0);
    }

    public Mono<Boolean> delete(String uid) {
        return Mono.just(companyDao.deleteByUid(uid) > 0);
    }

}
