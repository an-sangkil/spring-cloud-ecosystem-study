package com.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
public class NotFoundException extends ResponseStatusException {

    public NotFoundException(HttpStatus status) {
        super(status);
    }
}
