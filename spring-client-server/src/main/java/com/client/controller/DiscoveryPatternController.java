package com.client.controller;

import com.client.external.ProductApiFeignEurekaClient;
import com.client.model.APIResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 * Description :
 *
 * 디스커버리 패턴이 적용된(eureka) feign client 를 호출하는 controller
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021/12/27
 */
@Slf4j
@Controller
@AllArgsConstructor
public class DiscoveryPatternController {

    private final ProductApiFeignEurekaClient productApiFeignEurekaClient;

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/eureka/product/getall")
    public @ResponseBody
    APIResponse feign_eureka_getAll() {
        return productApiFeignEurekaClient.productGetAll();
    }

    // 게이트웨이를 타지 않고 직접 호출한다.
    //
    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/eureka/findone")
    public @ResponseBody
    APIResponse feign_eureka_findOne(@RequestParam(required = false) String productId) {
        return productApiFeignEurekaClient.findOne(productId);
    }

    /**
     * 페인에서 404 오류가 발생될때 -> 커스텀 처리
     */
    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/eureka/404")
    public @ResponseBody
    APIResponse feign_eureka_404() {
        return productApiFeignEurekaClient.find404();
    }


}
