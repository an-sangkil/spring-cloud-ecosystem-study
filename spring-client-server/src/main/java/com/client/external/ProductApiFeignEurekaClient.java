package com.client.external;

import com.client.config.GlobalCustomFeignConfig;
import com.client.config.ProductErrorDecoder;
import com.client.model.APIResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * application.yml 에등록된 default-errorDecode 를 사용하는 예제
 * @link GlobalCustomErrorDecoder.class
 *
 * @FeignClient 를 통해 클레스의 우선순위를 더 높게 하고 싶다면
 * feign.client.default-to-properties 는 false 로 지정한다.
 *
 *
 * @author skan
 * @version Copyright (C) 2021 by CJENM|MezzoMedia. All right reserved.
 * @since 2021/12/27
 */
@FeignClient(
        name="MOS-SERVICE-PRODUCT"
        , configuration = {GlobalCustomFeignConfig.class, ProductErrorDecoder.class}
        , fallback = ProductApiFeignEurekaClient.ProductFallback.class
)
public interface ProductApiFeignEurekaClient {

    @GetMapping(value = "/external/product/all")
    APIResponse productGetAll();

    @GetMapping(value = "/external/product", produces ="application/json")
    APIResponse findOne(@RequestParam(required = false) String productId);

    @GetMapping(value = "/external/product/404")
    APIResponse find404();

    @Component
    @Qualifier("productFallbackEureka")
    class ProductFallback implements ProductApiFeignEurekaClient{

        @Override
        public APIResponse productGetAll() {
            return new APIResponse("","","fixed product getall");
        }

        @Override
        public APIResponse findOne(String productId) {
            return new APIResponse("","","fixed product findOne");
        }

        @Override
        public APIResponse find404() {
            return new APIResponse("","","fixed product 404");
        }
    }
}
