package com.client.controller;

import com.client.external.FallbackClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2022 by CJENM|MezzoMedia. All right reserved.
 * @since 2022/01/11
 */
@Slf4j
@Controller
@AllArgsConstructor
public class FallbackTestController {

    private final FallbackClient fallbackClient;

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/fallback/timeout")
    public @ResponseBody
    String fallbackClient_timeout() {
        String responseStr = fallbackClient.timeout();
        log.debug("responseStr = {}", responseStr);
        return responseStr;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/feign/fallback/exception")
    public @ResponseBody String fallbackClient_exception() {
        try {
            String responseStr = fallbackClient.exception();
            log.debug("responseStr = {}", responseStr);
            return responseStr;

        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
