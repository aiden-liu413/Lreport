package com.dsmp.report.jms.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

/**
 * @author byliu
 */
@Component
public class ReportTaskExecProducer extends AbstractAmqpProducer{
    /**
     * @return routingkey
     */
    @Override
    String getRoutingKey() {
        return "report.task.exec";
    }

    /**
     * 注入exchange的抽象方法
     */
    @Override
    Exchange setExchange() {
        return new DirectExchange("D-EXCHANGE");
    }

    /**
     * 注入queue的抽象方法
     * @return
     */
    @Override
    Queue setQueue() {
        return new Queue("test");
    }
}
