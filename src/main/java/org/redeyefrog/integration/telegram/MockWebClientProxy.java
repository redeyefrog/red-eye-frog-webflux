package org.redeyefrog.integration.telegram;

import org.redeyefrog.integration.proxy.WebClientProxy;
import org.redeyefrog.integration.telegram.dto.MockFindCompanyRequest;
import org.redeyefrog.integration.telegram.dto.MockFindCompanyResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MockWebClientProxy extends WebClientProxy {

    public Flux<MockFindCompanyResponse> findCompany(MockFindCompanyRequest request) {
        return callTelegramFlux("/findCompany", request, MockFindCompanyResponse.class);
    }

}
