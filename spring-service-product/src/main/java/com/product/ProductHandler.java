package com.product;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
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
public class ProductHandler {

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("SAVE PRODUCT SUCCESS"),String.class);
    }
}
