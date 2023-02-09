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

    public Mono<ServerResponse> campaignDetail(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("ads campaign detail,  path=/ads/campaign/detail"),String.class);
    }
    public Mono<ServerResponse> campaignDelete(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("ads campaign delete,  path=/campaign/delete"),String.class);
    }
    public Mono<ServerResponse> getCampaignAll(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("ads campaign seatchAll,  path=/campaign/"),String.class);
    }
}
