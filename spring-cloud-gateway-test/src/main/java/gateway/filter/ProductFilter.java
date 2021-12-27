package gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021/12/23
 */
@Component
@Slf4j
public class ProductFilter extends AbstractGatewayFilterFactory<String> {

    @Override
    public GatewayFilter apply(String config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            String customHeader = exchange.getRequest().getHeaders().getFirst("hello-header");
            log.info("1. product header info token = {} , custom header = {}", token, customHeader );
            log.info("2. product filter config str = {}",config);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("3. product filter end");
            }));
        };
    }
}
