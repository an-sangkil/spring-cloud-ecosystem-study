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
public class ClientEurekaController {

    private final ProductApiFeignEurekaClient productApiFeignEurekaClient;

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/eureka/product/getall")
    public @ResponseBody
    APIResponse feign_eureka_getAll() {
        return productApiFeignEurekaClient.productGetAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/eureka/findone")
    public @ResponseBody
    APIResponse feign_eureka_findOne(@RequestParam(required = false) String productId) {
        return productApiFeignEurekaClient.findOne(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test/eureka/404")
    public @ResponseBody
    APIResponse feign_eureka_404() {
        return productApiFeignEurekaClient.find404();
    }


}
