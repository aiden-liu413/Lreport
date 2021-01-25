package com.kxingyi.common.util.elasticsearch;


import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;
import java.util.Iterator;

/**
 * @author byliu on 2020/07/22
 */
@EnableConfigurationProperties(ElasticSearchPerperties.class)
public class ElasticSearchConfig {
    private final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Bean(name = "transportClient")
    public TransportClient initEsClient(ElasticSearchPerperties esPerperties) {
        String clusterName = esPerperties.getClusterName();
        String esAddressCfg = esPerperties.getAddress();
        boolean sniff = esPerperties.isSniff();
        String xpack_security_user = esPerperties.getUser();
        String xpack_security_pwd = esPerperties.getPwd();
        boolean showDsl = esPerperties.isShowDsl();

        clusterName = StringUtils.isBlank(clusterName) ? "elasticsearch" : clusterName;
        esAddressCfg = StringUtils.isBlank(esAddressCfg) ? "localhost:9300" : esAddressCfg;
        ElasticsearchUtil.setShowDsl(showDsl);
        logger.info("init es client...");
        logger.info("es_cluster_name = {}", clusterName);
        logger.info("es_address = {} ", esAddressCfg);

        TransportClient client = null;

        try {
            if (!StringUtils.isBlank(xpack_security_user) && !StringUtils.isBlank(xpack_security_pwd)) {
                Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", sniff)
                        .put("xpack.security.user", xpack_security_user + ":" + xpack_security_pwd).put("client.transport.ping_timeout", "60s").build();
                client = new PreBuiltXPackTransportClient(settings);
            } else {
                Settings settings = Settings.builder().put("cluster.name", clusterName)
                        .put("client.transport.sniff", sniff).build();
                client = new PreBuiltTransportClient(settings);
            }

            Iterable<String> esAddress = Splitter.on(',').trimResults().split(esAddressCfg);
            Iterator<String> addressIter = esAddress.iterator();
            while (addressIter.hasNext()) {
                String address = addressIter.next();
                String[] params = address.split(":");
                String ip = params[0];
                int port = Integer.parseInt(params[1]);
                logger.info("-- add ES node  = " + ip + ":" + port);
                client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(ip, port)));
            }

            for (DiscoveryNode node : client.connectedNodes()) {
                logger.info("-- connect ES node :" + node.getAddress());
            }
        } catch (Exception e) {
            logger.error("-- init ES environment failed", e);
        } finally {
            ElasticsearchUtil.setClient(client);
            return client;
        }
    }
}
