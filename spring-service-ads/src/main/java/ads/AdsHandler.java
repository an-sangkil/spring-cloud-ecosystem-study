package ads;

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
public class AdsHandler {

    public Mono<ServerResponse> adsAll(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("hello ads, get all"),String.class);
    }
    public Mono<ServerResponse> adsAllTypeNest(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("hello ads, get all, adsAllTypeNest, -- default "),String.class);
    }
    public Mono<ServerResponse> adsPathNestTest(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("hello ads, get all, http://127.0.0.1:8082/nest/ads/path/test "),String.class);
    }

    public Mono<ServerResponse> todayAds(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("today ads,  path=/today_ads/this_one"),String.class);
    }

    public Mono<ServerResponse> getAdsAll(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("today ads getAdsAll , path=/, router path "),String.class);
    }
}
