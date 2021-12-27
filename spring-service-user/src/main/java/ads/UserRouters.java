package ads;

import ads.external.ProductApiFeignClient;
import ads.model.APIResponse;
import io.netty.util.internal.ConstantTimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
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
@AllArgsConstructor
public class UserRouters {

    final private ProductApiFeignClient productApiFeignClient;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return
                RouterFunctions.route(RequestPredicates.GET("/user/get_all")
                        .and(RequestPredicates.accept(MediaType.ALL)), request -> {

                    try {
                        // 테스트를 위한 지연시간 4 초 , 게이트웨이 설정 시간 3초
                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return ServerResponse.ok().body(Mono.just("hello user get all"), String.class);
                }).and(
                        RouterFunctions.route(RequestPredicates.GET("/user/userId"), request -> {
                            String userId = (String) request.attribute("userId").orElseGet(() -> "");
                            System.out.println(userId);
                            Mono<String> data = Mono.just("you search , user id equal =" + userId);
                            return ServerResponse.ok().body(data, String.class);
                        }))
                        .andRoute(RequestPredicates.GET("/user/save"), userHandler::save)
                        .andRoute(RequestPredicates.GET("/user/delete"), request -> {
                            Mono<String> bodyData = request.bodyToMono(String.class);

                            return ServerResponse.ok().body(bodyData, String.class);
                        })
                        .and(route(GET("/feign/test01"), request -> {

                            APIResponse apiResponse =  productApiFeignClient.productGetAll();
                            log.debug("apiResponse = {}",apiResponse );


                            return ServerResponse.ok().body(Mono.just("open feign test 01 - success"), String.class);
                        }) )
                        .and(route(GET("/feign/test02"), request -> {
                            String productId = request.queryParam("productId").orElse("");
                            APIResponse apiResponse =  productApiFeignClient.findOne(productId);
                            log.debug("apiResponse = {}",apiResponse );

                            return ServerResponse.ok().body(Mono.just("open feign test 02 - success"), String.class);
                        }) )


                ;
    }

}
