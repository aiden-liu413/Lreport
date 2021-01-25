package com.kxingyi.common.util.minio;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableConfigurationProperties(value = MinioProperties.class)
public class MinioRegister {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(name = "minioClient")
    public MinioClient init(MinioProperties minioProperties) {
        MinioClient minioClient = null;
        try {
            logger.info("init minio sdk");
            minioClient = MinioClient.builder().endpoint(minioProperties.getUrl()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).httpClient(getUnsafeOkHttpClent()).build();
            String suffixWhitStr = minioProperties.getSuffixWhitList();
            List suffixWhitList = new ArrayList();
            if (StringUtils.isNotBlank(suffixWhitStr)) {
                suffixWhitList = Arrays.asList(suffixWhitStr.split(","));
            }
            MinIoComponent.init(suffixWhitList, minioClient, minioProperties.getChunkBucKet());
            MinIoComponent.createDefaultBucket();
        } catch (Exception e) {
            logger.info("minio sdk init failed");
        } finally {
            return minioClient;
        }
    }

    /**
     * 通过代码，取消SSL验证
     **/
    public static OkHttpClient getUnsafeOkHttpClent() throws KeyManagementException {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);

            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
            return builder.build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
