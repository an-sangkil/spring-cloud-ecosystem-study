package com.client.config;

import com.client.exception.BadRequestException;
import com.client.exception.InternalServerException;
import com.client.exception.NotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Date;


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
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 400:
            case 500:
            case 501:
            case 502:
            case 503:
                FeignException feignException = FeignException.errorStatus(methodKey,response);
                return new RetryableException(response.status(), response.reason(), response.request().httpMethod(),feignException, null, response.request());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }

//    @Bean
//    public ErrorDecoder productErrorDecoder(){
//        return new ProductErrorDecoder();
//    }

//    @Bean
//    public Retryer retryer(){
//        return new Retryer.Default();
//    }


}
