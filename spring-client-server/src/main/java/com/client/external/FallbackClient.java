package com.client.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2022 by CJENM|MezzoMedia. All right reserved.
 * @since 2022/01/04
 */
@FeignClient(
        name="FallbackClient"
        ,url = "http://localhost:8083"
        ,fallback =Fallback.class
        //,decode404 = true
)
public interface FallbackClient {

    @RequestMapping("/product/lazy/get_all")
    String none();


}
