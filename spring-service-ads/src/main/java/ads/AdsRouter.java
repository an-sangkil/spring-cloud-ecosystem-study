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

    /*
        전체 광고 조회
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(AdsHandler adsHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/ads/get_all"), adsHandler::adsAll);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunctionBuilder(AdsHandler adsHandler) {

        /*
            - pathVariable  테스트를 하기 위함
            - route path 를 이용하여 앞단에 같은 path 를 사용하기
         */
        RouterFunction<ServerResponse> nestRoute = RouterFunctions.route().path("/ads/nest", b1 -> b1
                        .nest(RequestPredicates.accept(MediaType.ALL), b2 -> b2
                                .GET("/{id}", request -> {

                                    String id = request.pathVariable("id");
                                    System.out.println("id  = " + id);

                                    return ServerResponse.ok().body(Mono.just("nest path variable data =  " + id), String.class);
                                }))

                        // @path = /ads/nest/path/test
                        .GET("/path/test", adsHandler::adsPathNestTest)
                        // @path = /ads/nest/
                        .GET(adsHandler::adsAllTypeNest)
                )
                .build();


        // 앞에 항상 같은 path 를 붙이기 위한방법
        RouterFunction<ServerResponse> getRoute = RouterFunctions.route().path("/ads/campaign"
                , builder -> {
                    builder.GET("/detail", adsHandler::campaignDetail);
                    builder.GET("/delete", adsHandler::campaignDelete);
                    builder.GET(adsHandler::getCampaignAll);
                })

                // router path 해제 (항상 앞에 붙는 /ads/campaign path 해제 )
                .GET("/ads/adgroup/{adgroup_id}", request -> {

                    String adGroupId = request.pathVariable("adgroup_id");
                    System.out.println("adgroup id = " + adGroupId);

                    return ServerResponse.ok().body(Mono.just("non router path /ads/adgroup/" + adGroupId), String.class);
                }).build();

        // bad gateway test
        return RouterFunctions.route().add(nestRoute).add(getRoute).build().andRoute(RequestPredicates.GET("/ads/notfound_error"), request -> {
            return ServerResponse.status(HttpStatus.BAD_GATEWAY).body(Mono.just("sorry, ads is not found"), String.class);
        });
    }
}
