package com.client.external;

import com.client.model.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
//@FeignClient(name="product-feign-client-01",url = "http://localhost:8083")
@FeignClient(name="MOS-SERVICE-PRODUCT")
public interface ProductApiFeignClient {

    @GetMapping(value = "/external/product/all")
    APIResponse productGetAll();

    @GetMapping(value = "/external/product", produces ="application/json")
    APIResponse findOne(@RequestParam String productId);

}
