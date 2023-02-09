package com.client.external;

import com.client.model.APIResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
@FeignClient(
  name = "product-feign-client-01",
  url = "http://localhost:8083",
  fallback = ProductApiFeignDefaultClient.ProductFallback.class
)
public interface ProductApiFeignDefaultClient {
  @GetMapping(value = "/external/product", produces = "application/json")
  APIResponse findOne(
    @RequestParam(required = false, name = "productId") String productId
  );

  @GetMapping(value = "/external/product/404")
  APIResponse find404();

  /**
   * product fallback 클레스로 target 에대한 api 호출 실패시 동작 한다.
   */
  @Component
  @Qualifier("ProductFallbackDefault")
  class ProductFallback implements ProductApiFeignDefaultClient {

    @Override
    public APIResponse findOne(String productId) {
      return new APIResponse("error", "fallback error \n /client/feign/test/default/findone >>  /external/product", "fallback error message , fixed product findOne");
    }

    @Override
    public APIResponse find404() {
      return new APIResponse("error", "fallback error \n >> /client/feign/test/default/404  >>  /external/product/404, ", "fixed product 404");
    }
  }
}
