package com.dsmp.report.jms.rabbitmq;

import com.kxingyi.common.util.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author byliu
 */
public abstract class AbstractAmqpProducer {

    @Autowired
    public AmqpTemplate amqpTemplate;
    private Exchange exchange;
    private Queue queue;

    public AbstractAmqpProducer() {
        queue = setQueue();
        exchange = setExchange();
    }

    /**
     * @return routingkey
     */
    abstract String getRoutingKey();

    /**
     * 注入exchange的抽象方法
     *
     * @return Exchange
     */
    abstract Exchange setExchange();

    /**
     * 注入queue的抽象方法
     *
     * @return Queue
     */
    abstract Queue setQueue();

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(Object msg) {
        amqpTemplate.convertAndSend(exchange.getName(), getRoutingKey(), JsonUtils.toJson(msg));
    }
}
