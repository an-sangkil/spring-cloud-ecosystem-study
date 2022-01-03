package com.client.config;

import com.client.exception.BadRequestException;
import com.client.exception.InternalServerException;
import com.client.exception.NotFoundException;
import feign.Request;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


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
public class GlobalCustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

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
