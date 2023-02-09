package com.client.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * fallback 에서 상세 오류 exception 을 확인하고 싶을때 fallbackFactory 를 사용한다.
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

    @RequestMapping("/product/timeout-exception")
    String timeout();

    /**
     * 예외를 호출하게 되는데, 호출받는 타겟에서 예외가 발생되면 fallback 이동작되고
     * 그렇치 않으면 데이터를 받아 온다.
     *
     */
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
            log.error("fallback error !! " , cause);
            return new Fallback();
        }
    }
}
