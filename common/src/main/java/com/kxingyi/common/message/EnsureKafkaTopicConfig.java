package com.kxingyi.common.message;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kxingyi.common.message.TopicList.*;

/**
 * @author: wu_chao
 * @date: 2020/10/29
 * @time: 11:12
 */
@Configuration
public class EnsureKafkaTopicConfig {
    @Bean
    public NewTopic addPolicy() {
        return new NewTopic(TOPIC_DSMP_POLICY_ADD, 1, (short) 1);
    }

    @Bean
    public NewTopic modifyPolicy() {
        return new NewTopic(TOPIC_DSMP_POLICY_MODIFY, 1, (short) 1);
    }

    @Bean
    public NewTopic deletePolicy() {
        return new NewTopic(TOPIC_DSMP_POLICY_DELETE, 1, (short) 1);
    }

    @Bean
    public NewTopic riskEventAdd() {
        return new NewTopic(TOPIC_DSMP_RISK_EVENT_ADD, 1, (short) 1);
    }

    @Bean
    public NewTopic userSync() {
        return new NewTopic(TOPIC_DSMP_USER_SYNC, 1, (short) 1);
    }

    @Bean
    public NewTopic orgSync() {
        return new NewTopic(TOPIC_DSMP_ORG_SYNC, 1, (short) 1);
    }

    @Bean
    public NewTopic assetsResourceSync() {
        return new NewTopic(TOPIC_DSMP_ASSETS_RESOURCE_SYNC, 1, (short) 1);
    }

    @Bean
    public NewTopic assetsResourceGroupSync() {
        return new NewTopic(TOPIC_DSMP_ASSETS_RESOURCE_GROUP_SYNC, 1, (short) 1);
    }

    @Bean
    public NewTopic assetsResourceTypeSync() {
        return new NewTopic(TOPIC_DSMP_ASSETS_RESOURCE_TYPE_SYNC, 1, (short) 1);
    }
}
