package com.kxingyi.common.util.feign;

import com.kxingyi.common.util.okhttp.OkHttpBuilderProvider;
import feign.Client;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;

import java.util.Collections;
import java.util.List;

/**
 * @author hujie
 * @date 2020/5/14 16:40
 * feign的一些相关配置项,具体含义参考官方定义 https://github.com/OpenFeign/feign
 * urlPre例外，是自定义的配置项，用于和接口方法中定义的url合并起来构成完整的url
 **/
public abstract class DefaultFeignClientConfiguration extends FeignClientConfiguration {

    public Client client() {
        return new feign.okhttp.OkHttpClient(OkHttpBuilderProvider.trustAllCertHttpClientBuilder().build());
    }

    public Decoder decoder() {
        return new Decoder.Default();
    }

    public Encoder encoder() {
        return new Encoder.Default();
    }

    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    public List<RequestInterceptor> requestInterceptor() {
        return Collections.emptyList();
    }

    public abstract String urlPre();
}
