package com.dsmp.report.rabbitmq;

import com.dsmp.report.common.domain.ReportTaskExec;
import com.dsmp.report.jms.rabbitmq.ReportTaskExecProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author byliu
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JmsTest {

    @Autowired
    ReportTaskExecProducer reportTaskExecProducer;
    @Test
    public void testSend(){
        reportTaskExecProducer.send(new ReportTaskExec("test:error", "658"));
    }

}
