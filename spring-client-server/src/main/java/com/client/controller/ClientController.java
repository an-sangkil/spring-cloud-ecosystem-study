package com.client.controller;

import com.client.external.ProductApiFeignClient01;
import com.client.external.ProductApiFeignClient02;
import com.client.model.APIResponse;
import lombok.AllArgsConstructor;
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

    private final ProductApiFeignClient01 productApiFeignClient01;
    private final ProductApiFeignClient02 productApiFeignClient02;

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test01/product/getall")
    public @ResponseBody
    APIResponse feignTest01() {
        return productApiFeignClient01.productGetAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test01/findone")
    public @ResponseBody
    APIResponse feignTest02(@RequestParam(required = false) String productId) {
        return productApiFeignClient01.findOne(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test01/404")
    public @ResponseBody
    APIResponse feignTest03(@RequestParam(defaultValue = "") String productId) {
        return productApiFeignClient01.find404();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test02/findone")
    public @ResponseBody
    APIResponse feignTest02findOne(@RequestParam(required = false) String productId) {
        return productApiFeignClient02.findOne(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/test02/404")
    public @ResponseBody
    APIResponse feignTest02404(@RequestParam(defaultValue = "") String productId) {
        return productApiFeignClient02.find404();
    }

}
