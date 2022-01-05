package com.client.external;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * Description :
 *
 *
 * </pre>
 *
 * @author skan
 * @version Copyright (C) 2022 by CJENM|MezzoMedia. All right reserved.
 * @since 2022/01/04
 */
@Component
public class Fallback implements FallbackClient{

    @Override
    public String productLazyGetAll() {
        return "fixed response getall";

    }

    @Override
    public String none() {
        return "fixed response none";
    }
}
