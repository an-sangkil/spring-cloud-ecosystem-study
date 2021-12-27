package com.product.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode
public class APIResponse {
    private final String status;
    private final String data;
    private final String message;

    @Builder
    public APIResponse(String status, String data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}