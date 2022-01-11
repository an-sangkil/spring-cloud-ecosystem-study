package com.client.config;

import feign.Logger;
import feign.Retryer;
import feign.Target;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import java.lang.reflect.Method;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * feign 에 대한 기본 설정
 *
 * @author skan
 * @since 2021/12/27
 */
@Configuration
public class GlobalCustomFeignConfig {

    /**
     *  Feign 사용시 LocalDate, LocalDateTime 에 대한 설정
     */
    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegister() {
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(registry);
        };
    }

    /**
     * 기본 설정이 NON 으로 FULL 로 지정하여 request, response, header,body, metadata 모두를 로깅한다.
     * 로그 설정에서 @Feign package 를 debug level로 변경해 준다.
     */
    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.BASIC;
    }


    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (String feignClientName, Target<?> target, Method method) -> feignClientName + "_" + method.getName();
    }

    @Bean
    public Retryer retryer(){
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }

}
