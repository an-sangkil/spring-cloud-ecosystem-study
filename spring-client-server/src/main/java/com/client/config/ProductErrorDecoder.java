package com.client.config;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;


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
public class ProductErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    /**
     * RetryableException 을 해주어야 Retry 가 동작된다. 다른 익셉션은 리트라이 하지 않음.
     * 이에 retry 에 해당하는 응답 코드만 별도로 정의하여 예외처리 해준다.
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
            case 500:
            case 501:
            case 502:
            case 503:
                log.warn("product error code {}", response.status());
                FeignException feignException = FeignException.errorStatus(methodKey,response);
                return new RetryableException(response.status(), response.reason(), response.request().httpMethod(),feignException, null, response.request());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }



}
