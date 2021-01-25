package com.kxingyi.common.somp;

import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.util.JsonUtils;
import com.kxingyi.common.util.okhttp.OkHttpBuilderProvider;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author hujie
 * @date 2020/11/20
 */
public class SOMPRestTemplateFactory {

    public static RestTemplate getRestTemplate(String ip, String port, String accessKey, String secretKey) {
        return new RestTemplate(new SOMPHttpRequestFactory(ip, port, accessKey, secretKey));
    }

    private static class SOMPHttpRequestFactory extends OkHttp3ClientHttpRequestFactory {
        private final String ip;
        private final String port;

        public SOMPHttpRequestFactory(String ip, String port, String accessKey, String secretKey) {
            super(new SOMPOkHttpClientProvider(ip, port, accessKey, secretKey).getHttpClient());
            this.ip = ip;
            this.port = port;
        }

        @Override
        public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) {
            return super.createRequest(generateAbsoluteURI(uri), httpMethod);
        }

        @Override
        public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) {
            return super.createAsyncRequest(generateAbsoluteURI(uri), httpMethod);
        }

        private URI generateAbsoluteURI(URI old) {
            try {
                return new URI("https", old.getUserInfo(), ip, Integer.parseInt(port), "/api/v1" + old.getPath(), old.getQuery(), old.getFragment());
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private static class SOMPOkHttpClientProvider {
        private final OkHttpClient tokenHttpClient;
        private final Logger logger = LoggerFactory.getLogger(SOMPOkHttpClientProvider.class);
        private final String ip;
        private final String port;
        private final String accessKey;
        private final String secretKey;

        {
            tokenHttpClient = OkHttpBuilderProvider.trustAllCertHttpClientBuilder()
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .build();
        }

        public SOMPOkHttpClientProvider(String ip, String port, String accessKey, String secretKey) {
            this.ip = ip;
            this.port = port;
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }

        public OkHttpClient getHttpClient() {
            return OkHttpBuilderProvider.trustAllCertHttpClientBuilder()
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .addInterceptor(new TokenInterceptor())
                    .build();
        }

        private String getToken() {
            HashMap<String, Object> param = Maps.newHashMapWithExpectedSize(2);
            param.put("accessKey", accessKey);
            param.put("secretKey", secretKey);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JsonUtils.toJson(param));
            Request request = new Request.Builder()
                    .url("https://" + ip + ":" + port + "/api/v1/" + "/getAccessToken")
                    .post(requestBody)
                    .build();

            Response response = null;
            String token = null;
            try {
                response = tokenHttpClient.newCall(request).execute();
                SOMPResponse tokenResponse = JsonUtils.toObject(response.body().string(), SOMPResponse.class);
                if (tokenResponse.getCode() != 0) {
                    throw new BusinessException("can not get token from SOMP" + tokenResponse.getMsg());
                }
                token = tokenResponse.getData();
            } catch (Exception e) {
                logger.error("can not get token from SOMP", e);
                throw new BusinessException("can not get token from SOMP");
            }

            if (StringUtils.isBlank(token)) {
                throw new BusinessException("can not get token from SOMP, plz check [accessKey] [secretKey]");
            }

            return token;

        }

        private class TokenInterceptor implements Interceptor {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //每次请求重新拿token，不判断token是否过期
                String token = getToken();

                String TOKEN_HEADER_KEY = "token";
                Request request = chain.request().newBuilder()
                        .addHeader(TOKEN_HEADER_KEY, token)
                        .build();

                return chain.proceed(request);
            }
        }

        private static class SOMPResponse {
            /**
             * code : 0
             * msg : 执行成功
             * data : a5d9c2f1-a764-4610-94a3-d6f926a181bf
             */
            private int code;
            private String msg;
            private String data;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }
    }

}
