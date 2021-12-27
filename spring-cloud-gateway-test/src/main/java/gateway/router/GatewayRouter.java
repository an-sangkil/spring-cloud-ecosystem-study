package gateway.router;

import gateway.filter.GlobalFilter;
import gateway.filter.ProductFilter;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Set;

/**
 * <pre>
 * application.yml 에 api gateway route 를 설정 할수도 있고, 다음처럼
 * RouterLocator를 @Bean 으로 만들어서 라우터를 생성할 수 있다.
 *
 * 하지만 yml에 생성한것과 호화 되지 않고 독립적으로 실행 되는 것이라고 보면 된다.
 * 예를 들어 yml 에 등록된 default-filters 에 대해 전혀 동작되지 않는다는것이다.
 * -> https://github.com/spring-cloud/spring-cloud-gateway/issues/263 여기에 이슈로 올라와 있다.
 *
 * 그렇기 때문에 java bean 을 사용한 route 를 구성한다면
 *  - 독립적 구성이며, yml 에 정의한것과 화환이 되지 않는다는것을 인지해야한다.
 *  - 별도로 서킷브레이커 설정, 리트라이 정책등을 다시금 정해 주어야 한다.
 *
 * </pre>
 *
 * @author skan
 */
@Component
public class GatewayRouter {

    @Bean
    public RouteLocator productRouterLocator(RouteLocatorBuilder routeLocatorBuilder
            , ProductFilter productFilter, GlobalFilter globalFilter
    ) {

        return routeLocatorBuilder
                .routes()

                .route("product-service-router", predicateSpec ->
                        predicateSpec
                                .path("/product/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec
                                                .filter(globalFilter.apply(new GlobalFilter.Config("p_global","p_global_message")),1)
                                                .filter(productFilter.apply("Hello product filter"),2)
                                                // 필터에서 강제로 header 추가가 가능함
                                                .addRequestHeader("hello-header", "hello product header custom")
                                                // rewrite path 하지않고 오는 그대로 사용함으로 주석
                                                //.rewritePath("/product/**","/")
                                                .retry(retryConfig ->
                                                        retryConfig.setRetries(3)
                                                                .setMethods(HttpMethod.GET, HttpMethod.POST)
                                                                //http status가 SERVICE_UNAVAILABLE(503)인 경우 재시도 한다.
                                                                .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.NOT_FOUND, HttpStatus.BAD_GATEWAY)
                                                                //재시도 간격은 backoff에 설정된 간격대로 처음에는 10ms 후, 그 다음에는 10ms *(2^1) = 20ms 후에 재시도한다.
                                                                .setBackoff(Duration.ofMillis(1000), Duration.ofMillis(5000), 2, false)
                                                                // 특정 익셉션이 나온경우에만 재시도한다.
                                                                //.setExceptions(Exception.class,RuntimeException.class)
                                                                .setSeries(HttpStatus.Series.CLIENT_ERROR, HttpStatus.Series.SERVER_ERROR)

                                                )
                                                .circuitBreaker(config ->
                                                        config.setName("myCircuitBreaker")
                                                                .setFallbackUri("forward:/fallback/default")
                                                                .addStatusCode("INTERNAL_SERVER_ERROR")
                                                )
                                )
                                .uri("lb://MOS-SERVICE-PRODUCT")
                )
                .build();
    }


    @Bean
    @Deprecated
    public RouteLocator customRouterLocator(RouteLocatorBuilder routeLocatorBuilder
            , ProductFilter productFilter, GlobalFilter globalFilter
    ) {
        return routeLocatorBuilder
                .routes()
                // TODO HOST 기반 retry 설정 구현중 (동작이 안된다... ㅜㅜ )
                .route("global_retry_test",predicateSpec ->
                        predicateSpec
                                //.path("/product/**")
                                .host("localhost","127.0.0.1","*.retry.com","192.168.0.6")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec
                                                //.prefixPath("/*")
                                                .rewritePath("/(?<path>.*)","/$\\{path}")
                                                .retry(retryConfig ->
                                                        retryConfig.setRetries(3)
                                                                .setMethods(HttpMethod.GET, HttpMethod.POST)
                                                                //http status가 SERVICE_UNAVAILABLE(503)인 경우 재시도 한다.
                                                                .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.NOT_FOUND, HttpStatus.BAD_GATEWAY)
                                                                //재시도 간격은 backoff에 설정된 간격대로 처음에는 10ms 후, 그 다음에는 10ms *(2^1) = 20ms 후에 재시도한다.
                                                                .setBackoff(Duration.ofMillis(1000), Duration.ofMillis(5000), 2, false)
                                                                // 특정 익셉션이 나온경우에만 재시도한다.
                                                                //.setExceptions(Exception.class,RuntimeException.class)
                                                                .setSeries(HttpStatus.Series.CLIENT_ERROR, HttpStatus.Series.SERVER_ERROR)


                                                 )
                                )
                                .uri("lb://MOS-SERVICE-PRODUCT")
                )
                .build();
    }


    /**
     * 서킷 브레이크레이커에 의해 실패된 요청이 있을경우 이쪽으로 리다이렉트된다.
     * - 실패된 내용에 대한 응답을 준다.
     *
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route().path("/fallback", builder -> builder
                        .GET("/default", RequestPredicates.accept(MediaType.ALL), request -> {


                            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(APIResponse.builder().message("this is fallback default message").build()), APIResponse.class);
                        }))

                .build();
    }

    @Data
    public static class APIResponse {
        private final String status;
        private final String data;
        private final String message;

        @Builder
        public APIResponse(String status, String data, String message) {
            this.status = status;
            this.data = data;
            this.message = message;
        }
    }

}
