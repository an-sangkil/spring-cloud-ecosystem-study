package com.client.controller;

import com.client.external.ProductApiFeignClient;
import com.client.model.APIResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
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
@Controller
@AllArgsConstructor
public class ClientController {

    private final ProductApiFeignClient productApiFeignClient;

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test01")
    public @ResponseBody
    APIResponse feignTest01() {
        return productApiFeignClient.productGetAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test02")
    public @ResponseBody
    APIResponse feignTest02(@RequestParam(defaultValue = "") String productId) {
        return productApiFeignClient.findOne(productId);
    }

}
