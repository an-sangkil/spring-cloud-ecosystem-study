package gateway.config;

import java.time.Duration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021-06-21
 */

@Configuration
public class CircuitBreakerConfiguration {

    // 3초동안 응답 대기.
    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    /**
     * 서킷 브레이커에 대한 커스텀 빈 설정 (application.yml 에서도 설정 가능함)
     * @return
     */
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(20)
                        .permittedNumberOfCallsInHalfOpenState(5) // 서킷이 반쯤 열렸을때 허용되는 호출 수, 기본크기 10
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(60))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(TIMEOUT)
                        .build())
                .build()
        );
    }
}