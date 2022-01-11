package com.client.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
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
        //,fallback =FallbackClient.Fallback.class
        ,fallbackFactory = FallbackClient.TestFallbackFactory.class
        ,decode404 = true
)
public interface FallbackClient {

    @RequestMapping("/product/timeout")
    String timeout();

    @RequestMapping("/product/exception")
    String exception();

    @Component
    public class Fallback implements FallbackClient{
        @Override
        public String timeout() {
            return "fixed response timeout";
        }

        @Override
        public String exception() {
            return "fixed response exception";
        }
    }

    @Slf4j
    @Component
    public class TestFallbackFactory implements FallbackFactory<Fallback>{
        @Override
        public Fallback create(Throwable cause) {
            log.error("test fallback error" , cause);
            return new Fallback();
        }
    }
}
