package org.redeyefrog.api.router;

import org.redeyefrog.api.handler.CompanyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CompanyRouter {

    @Bean
    RouterFunction<ServerResponse> crudRoutes(CompanyHandler companyHandler) {
        return RouterFunctions.route()
                              .GET("/company/find", companyHandler::find)
                              .POST("/company/save", companyHandler::save)
                              .PUT("/company/update", companyHandler::update)
                              .DELETE("/company/delete", companyHandler::delete)
                              .DELETE("/company/delete/{uid}", companyHandler::delete)
                              .build();
    }

}
