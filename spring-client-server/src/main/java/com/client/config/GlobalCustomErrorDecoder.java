package com.client.config;

import com.client.exception.BadRequestException;
import com.client.exception.InternalServerException;
import com.client.exception.NotFoundException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;


/**
 *
 *  application.yml 에 feign.client.default 로 등록해서 global 로 사용한다.
 *
 * @author skan
 * @since 2021/12/27
 */
@Slf4j
public class GlobalCustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        log.warn("global error code {}, reason = {}", response.status(), response.reason());
        switch (response.status()){
            case 400:
                return new BadRequestException(HttpStatus.valueOf(response.status()));
            case 404:
                return new NotFoundException(HttpStatus.valueOf(response.status()));
            case 500:
                return new InternalServerException(HttpStatus.valueOf(response.status()));
            case 501:
            case 502:
            case 503:
                return new RetryableException(response.status(),response.reason(), response.request().httpMethod(), null,response.request());
        }
        return new Default().decode(methodKey, response);
    }
}
