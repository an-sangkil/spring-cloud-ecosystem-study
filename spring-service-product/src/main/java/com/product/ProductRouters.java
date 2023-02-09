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


import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * 상품 컨트롤러
 *
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
                RouterFunctions

                        // 타임아웃 익셉션 테스트
                        .route(GET("/product/timeout-exception"),request -> {
                            try {
                                // 테스트를 위한 지연시간 4 초 , 게이트웨이 설정 시간 3초
                                TimeUnit.SECONDS.sleep(4);
                            } catch (InterruptedException e) {
                                log.error("time out error occur ", e);
                                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.error(e), Throwable.class);
                            }
                            return ServerResponse.status(HttpStatus.OK).body(Mono.just("product, "), String.class);
                        })

                        // 요청에 따라 랜덤하게 예외를 발생시키거나, 성공메세지를 리턴한다.
                        .andRoute(GET("/product/exception"), request -> {
                                    if (new Random().nextBoolean()) {
                                        throw new RuntimeException("exception occurred");
                                    }
                                    return ServerResponse.ok().body(Mono.just("success"),String.class);
                                }
                        )

                        // 데이터 조회 용도의 테스트기능
                        .andRoute(
                                GET("/product/get-all")
                                        .and(accept(MediaType.ALL)), request -> {
                                    return ServerResponse.ok().body(Mono.just("hello user get all"), String.class);
                                }).and(
                                RouterFunctions.route(GET("/product/detail"), request -> {
                                    String userId = (String) request.queryParam("productId").orElseGet(() -> "");
                                    System.out.println(userId);
                                    Mono<String> data = Mono.just("you search , product id equal =" + userId);
                                    return ServerResponse.ok().body(data, String.class);
                                }))
                        .andRoute(GET("/product/save"), productHandler::save)
                        .andRoute(GET("/product/delete"), request -> {
                            Mono<String> bodyData = request.bodyToMono(String.class);
                            System.out.println("body data = " + bodyData);
                            return ServerResponse.ok().body("delete product success", String.class);
                        })

                        // 외부 제공 API
                        .and(RouterFunctions
                                .route(GET("/external/product/all"), request -> {
                                    try {
                                        APIResponse apiResponse = new APIResponse("SUCCESS", "product data", "message");
                                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(apiResponse), APIResponse.class);
                                    } catch (Exception e) {
                                        log.error("product all error", e);
                                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(new APIResponse("FAIL", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage())), APIResponse.class);
                                    }
                                }))
                        .and(RouterFunctions
                                .route(GET("/external/product"), request -> {
                                    try {
                                        String productId = request.queryParam("productId").orElseThrow(IllegalArgumentException::new);
                                        System.out.println("query param productId = " + productId);

                                        APIResponse apiResponse = new APIResponse("SUCCESS", "data", "message");
                                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(apiResponse), APIResponse.class);
                                    } catch (Exception e) {
                                        log.error("product find error", e);
                                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                                Mono.just(new APIResponse("FAIL", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage())), APIResponse.class
                                        );
                                    }
                                }))
                ;
    }

}
