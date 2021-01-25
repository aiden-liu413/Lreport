package com.dsmp.report.config.feign;

import com.dsmp.common.exception.BusinessException;
import com.dsmp.common.service.BaseService;
import com.dsmp.common.util.feign.DefaultFeignClientConfiguration;
import com.dsmp.common.util.okhttp.OkHttpBuilderProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author byliu
 **/
@Component
public class DataSyncFeignConfig extends DefaultFeignClientConfiguration {

    @Value("${data.sync.api.url-pre}")
    private String urlPre;

    @Override
    public String urlPre() {
        return urlPre;
    }

    @Override
    public Decoder decoder() {
        return new JacksonDecoder(customObjectMapper());
    }

    @Override
    public Encoder encoder() {
        return new JacksonEncoder(customObjectMapper());
    }

    @Override
    public Client client() {
        return new feign.okhttp.OkHttpClient(new DataSyncOkHttpClientProvider().getHttpClient());
    }

    private ObjectMapper customObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}

class DataSyncOkHttpClientProvider {

    private volatile OkHttpClient httpClient;

    DataSyncOkHttpClientProvider() {

    }

    OkHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (DataSyncOkHttpClientProvider.class) {
                if (httpClient == null) {
                    httpClient = OkHttpBuilderProvider.trustAllCertHttpClientBuilder()
                            .connectTimeout(5L, TimeUnit.SECONDS)
                            .writeTimeout(5L, TimeUnit.SECONDS)
                            .readTimeout(5L, TimeUnit.SECONDS)
                            .addInterceptor(new HeaderInterceptor())
                            .addInterceptor(new ResponseInterceptor())
                            .build();
                }
            }
        }
        return httpClient;
    }

    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json;charset=UTF-8");
            return chain.proceed(builder.build());
        }
    }


    private class ResponseInterceptor extends BaseService implements Interceptor {

        @Override
        public Response intercept(Chain chain) {
            Response response = null;
            try {
                response = chain.proceed(chain.request());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                throw new BusinessException("内部程序错误");
            }
            switch (response.code()) {
                case 404:
                case 500:
                    logger.error("{}", response.body());
                    throw new BusinessException("内部程序错误");
            }
            return response;
        }
    }

}