package org.redeyefrog.api.validator;

import org.redeyefrog.api.dto.common.FrogRequest;
import org.redeyefrog.api.dto.company.CompanyRequest;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.redeyefrog.persistence.dao.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator extends FrogValidator {

    @Autowired
    CompanyDao companyDao;

    public FrogRequest<CompanyRequest> validateSave(FrogRequest<CompanyRequest> request) {
        super.validate(request);
        String uid = request.getRequest().getCompanyUid();
        if (companyDao.isExist(uid)) {
            throw new FrogRuntimeException(StatusCode.DATA_ALREADY_EXIST);
        }
        return request;
    }

    public FrogRequest<CompanyRequest> validateUpdate(FrogRequest<CompanyRequest> request) {
        super.validate(request);
        String uid = request.getRequest().getCompanyUid();
        if (!companyDao.isExist(uid)) {
            throw new FrogRuntimeException(StatusCode.DATA_NOT_EXIST);
        }
        return request;
    }

}
