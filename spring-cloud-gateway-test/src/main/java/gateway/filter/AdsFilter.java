package gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021-06-17
 */
@Component
public class AdsFilter extends AbstractGatewayFilterFactory<AdsFilter.Config> {
    public AdsFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(String routeId, Consumer<Config> consumer) {
        return super.apply(routeId, consumer);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            System.out.println("start");
            System.out.println("exchange.getRequest() value = " + exchange.getRequest());
            System.out.println(" ads config data = " + config.toString());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("end");
            }));

        });
    }

    @Data
    public static class Config {
        private String name;
    }

}
