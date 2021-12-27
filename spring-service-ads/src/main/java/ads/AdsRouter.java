package ads;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
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
public class AdsRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(AdsHandler adsHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/ads/get_all"), adsHandler::adsAll);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunctionBuilder(AdsHandler adsHandler) {

        RouterFunction<ServerResponse> nestRoute = RouterFunctions.route().path("/nest/ads", b1 -> b1
                .nest(RequestPredicates.accept(MediaType.ALL), b2 -> b2
                        .GET("/{id}", request -> {

                            String id = request.pathVariable("id");
                            System.out.println(id);

                            return ServerResponse.ok().body(Mono.just("nest data" + id), String.class);
                        }))
                        .GET("/path/test", adsHandler::adsPathNestTest)
                        .GET(adsHandler::adsAllTypeNest)
                )

                .build();
        // 앞에 항상 같은 path 를 붙이기 위한방법
        RouterFunction<ServerResponse> getRoute = RouterFunctions.route().path("/get/ads", builder -> {
            builder.GET("/today_ads/this_one", adsHandler::todayAds);
            builder.GET(adsHandler::getAdsAll);
        }).GET("/tomorrow/ads",request -> ServerResponse.ok().body(Mono.just("non router path /tomorrow/ads"),String.class)).build();

        return RouterFunctions.route().add(nestRoute).add(getRoute).build().andRoute(RequestPredicates.GET("/ads/notfound_error"),request -> {
            return ServerResponse.status(HttpStatus.BAD_GATEWAY).body(Mono.just("dd"),String.class);
        });
    }
}
