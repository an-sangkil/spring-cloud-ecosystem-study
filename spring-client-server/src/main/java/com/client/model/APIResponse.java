package com.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse {
    private String status;
    private String data;
    private String message;

    public APIResponse(){}

    public APIResponse(String status, String data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}