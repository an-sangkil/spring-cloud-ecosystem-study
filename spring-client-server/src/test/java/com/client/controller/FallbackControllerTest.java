package com.client.controller;

import com.client.external.FallbackClient;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2022 by CJENM|MezzoMedia. All right reserved.
 * @since 2022/01/20
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(FallbackController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class FallbackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FallbackClient fallbackClient;

    @Test
    void fallbackClient_timeout() throws Exception {

        BDDMockito.given(fallbackClient.timeout()).will(invocation -> "mock test");
        mockMvc.perform(get("/client/feign/fallback/timeout"))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN+";charset=UTF-8"))
                .andDo(MockMvcRestDocumentation.document("fallback"));
    }

    @Test
    void fallbackClient_exception() {
    }
}