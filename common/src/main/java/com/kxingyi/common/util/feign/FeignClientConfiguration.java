package com.kxingyi.common.util.feign;

import feign.Client;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;

import java.util.List;

/**
 * @author hujie
 * @date 2020/5/25 9:39
 **/
public abstract class FeignClientConfiguration {
    public abstract Client client();

    public abstract Decoder decoder();

    public abstract Encoder encoder();

    public abstract Retryer retryer();

    public abstract List<RequestInterceptor> requestInterceptor();

    public abstract String urlPre();
}
