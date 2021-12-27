package gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            System.out.println("global filter - start");

            //System.out.println("exchange.getRequest() value = " + exchange.getRequest());
            System.out.printf("config name = %s, message = %s \n",config.getName(), config.getMessage());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("global filter - end");
            }));

        });
    }

    @Data
    public static class Config {
        private String name;
        private String message;

        public Config(String name, String message) {
            this.name = name;
            this.message = message;
        }


    }
}
