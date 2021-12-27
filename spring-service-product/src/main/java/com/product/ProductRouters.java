package com.product;

import com.product.model.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;


import java.util.concurrent.TimeUnit;

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
@Slf4j
@Component
public class ProductRouters {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ProductHandler productHandler) {
        return
                RouterFunctions.route(GET("/product/get_all")
                                .and(RequestPredicates.accept(MediaType.ALL)), request -> {

                            try {
                                // 테스트를 위한 지연시간 4 초 , 게이트웨이 설정 시간 3초
                                TimeUnit.SECONDS.sleep(4);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            return ServerResponse.ok().body(Mono.just("hello user get all"), String.class);
                        }).and(
                                RouterFunctions.route(GET("/product/productId"), request -> {
                                    String userId = (String) request.attribute("productId").orElseGet(() -> "");
                                    System.out.println(userId);
                                    Mono<String> data = Mono.just("you search , product id equal =" + userId);
                                    return ServerResponse.ok().body(data, String.class);
                                }))
                        .andRoute(GET("/product/save"), productHandler::save)
                        .andRoute(GET("/product/delete"), request -> {
                            Mono<String> bodyData = request.bodyToMono(String.class);
                            return ServerResponse.ok().body(bodyData, String.class);
                        })
                        .and(
                                RouterFunctions.route(GET("/product/notfound_error"), request -> {
                                    log.info("/product/notfound_error call");
                                    return ServerResponse.status(HttpStatus.BAD_GATEWAY).body(Mono.just("dd"), String.class);
                                })
                        ).and(RouterFunctions
                                .route(GET("/external/product/all"), request -> {
                                    try {
                                        APIResponse apiResponse = new APIResponse("SUCCESS", "data", "message");
                                        return ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(
                                                Mono.just(apiResponse), APIResponse.class
                                        );
                                    } catch (Exception e) {
                                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                                Mono.just(new APIResponse("FAIL", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage())), APIResponse.class
                                        );
                                    }
                                }))
                        .and(RouterFunctions
                                .route(GET("/external/product"), request -> {

                                    request.queryParam("productId");

                                    try {
                                        APIResponse apiResponse = new APIResponse("SUCCESS", "data", "message");
                                        return ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(
                                                Mono.just(apiResponse), APIResponse.class
                                        );
                                    } catch (Exception e) {
                                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                                Mono.just(new APIResponse("FAIL", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage())), APIResponse.class
                                        );
                                    }
                                }))


                ;
    }

}
