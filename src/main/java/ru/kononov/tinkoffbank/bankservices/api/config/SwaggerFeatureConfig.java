package ru.kononov.tinkoffbank.bankservices.api.config;

import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * подключение {@link Swagger2Feature}
 *
 * @author dkononov
 */
@Configuration
public class SwaggerFeatureConfig {

    @Value("${cxf.path}")
    private String basePath;

    @Bean("swagger2Feature")
    public Feature swagger2Feature() {
        return new Swagger2Feature();
    }

}
