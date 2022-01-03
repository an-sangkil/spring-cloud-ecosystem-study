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
public class BadRequestException extends ResponseStatusException {
    public BadRequestException(HttpStatus status) {
        super(status);
    }

    public BadRequestException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public BadRequestException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public BadRequestException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
