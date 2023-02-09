package com.client.controller;

import com.client.external.ProductApiFeignDefaultClient;
import com.client.model.APIResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 디스커버리 패턴이 정용되지 않은 (eureka) feign client 를 호출하는 controller
 *  - IP 또는 HOST 지정하여 호출
 *
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021/12/27
 */
@Slf4j
@Controller
@AllArgsConstructor
public class DefaultController {

    private final ProductApiFeignDefaultClient productApiFeignDefaultClient;

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/default/findone")
    public @ResponseBody
    APIResponse feign_default_findOne(@RequestParam(required = false) String productId) {
        return productApiFeignDefaultClient.findOne(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/default/404")
    public @ResponseBody
    APIResponse feign_default_404() {
        return productApiFeignDefaultClient.find404();
    }

}
