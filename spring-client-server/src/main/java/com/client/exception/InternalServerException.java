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
public class InternalServerException extends ResponseStatusException {


    public InternalServerException(HttpStatus status) {
        super(status);
    }

    public InternalServerException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public InternalServerException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public InternalServerException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
