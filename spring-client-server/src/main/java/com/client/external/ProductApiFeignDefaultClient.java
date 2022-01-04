package com.client.external;

import com.client.config.FeignConfigCustom;
import com.client.config.ProductErrorDecoder;
import com.client.model.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * default (GlobalCustomErrorDecoder)  을 사용하지 않고 configuratin 속성을 사용하여 특정 error decoder 지정 예제
 *
 * feign.client.default-to-properties 는 false 로 지정하여 클레스에 선언된
 * 속성을 우선선위로 지정하였기때문에 ProductErrorDecoder 가 동작된다.
 *
 *
 *
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021/12/27
 */
@FeignClient(name="product-feign-client-01",url = "http://localhost:8083"
//        ,configuration = ProductErrorDecoder.class
)
public interface ProductApiFeignDefaultClient {

    @GetMapping(value = "/external/product", produces ="application/json")
    APIResponse findOne(@RequestParam(required = false) String productId);

    @GetMapping(value = "/external/product/404")
    APIResponse find404();


}
