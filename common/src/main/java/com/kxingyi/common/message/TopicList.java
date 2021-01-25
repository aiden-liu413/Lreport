package com.kxingyi.common.message;

/**
 * @Author: chengpan
 * @Date: 2019/12/3
 * {@link EnsureKafkaTopicConfig}
 */
public interface TopicList {



    String TOPIC_DSMP_POLICY_ADD = "policy.add";
    String TOPIC_DSMP_POLICY_MODIFY = "policy.modify";
    String TOPIC_DSMP_POLICY_DELETE = "policy.delete";


    String TOPIC_DSMP_RISK_EVENT_ADD = "risk.event.add";

    String TOPIC_DSMP_USER_SYNC = "user";
    String TOPIC_DSMP_ORG_SYNC = "org";

    /**
     * SOAP资产同步topics
     */
    String TOPIC_DSMP_ASSETS_RESOURCE_SYNC = "assets.resource";
    String TOPIC_DSMP_ASSETS_RESOURCE_GROUP_SYNC = "assets.resource_group";
    String TOPIC_DSMP_ASSETS_RESOURCE_TYPE_SYNC = "assets.resource_type";
}