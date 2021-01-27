package com.dsmp.report;

import com.bstek.ureport.export.ExportManager;
import com.kxingyi.common.util.elasticsearch.ElasticsearchUtil;
import com.kxingyi.common.util.elasticsearch.IndexName;
import com.dsmp.report.common.bo.ReportTaskBo;
import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTaskParam;
import com.dsmp.report.transfer.RiskTransfer;
import com.dsmp.report.web.request.ReportTaskPagination;
import net.sf.json.JSONObject;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author byliu
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Test {
    /*@org.junit.Test
    public void testCountBy() {
        *//*long count = repository.countDbFieldInfoBySensitiveType(null);
        long count2 = repository.countFieldCheckStatusByClassify("1", null);
        System.out.println(count2);*//*
        HtmlReport htmlReport = exportManager.exportHtml("dsmp:操作日志分析.venus.report", "request.getContextPath()", Maps.newHashMap());
        System.out.println(htmlReport);
    }*/

    @Autowired
    ExportManager exportManager;

    @org.junit.Test
    public void main(){
        WebDriver driver = openChrome();
        driver.navigate().to("http://localhost:8080/ureport/preview?_u=dsmp:%E6%93%8D%E4%BD%9C%E6%97%A5%E5%BF%97%E5%88%86%E6%9E%90.venus.report");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    public static WebDriver openChrome(){
        WebDriver driver;
        // cherome driver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\byliu\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置为 headless 模式 （无头浏览器）
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        //设置隐性等待时间
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        return driver;
    }

    @org.junit.Test
    public void testAddData() {
        //ElasticsearchUtil.deleteIndex("sjrz_qm_20201110");
        /*List<Map<String, Object>> list = ElasticsearchUtil.searchListData("sjrz_qm_20201110",
                IndexName.AUDIT_TYPENAME,
                QueryBuilders.matchAllQuery(),
                null,
                50,
                null,
                null,
                null,
                null);
        for (Map map : list) {
            map.put("WHEN_OP_DATE", "2021-01-07 13:11:03");
            map.put("WHEN_OP_DAY", "20210107");
            map.put("WHEN_OP_TIME", 1609996251);
            map.put("WHEN_OP_RECV_TIME", 1609996251);
            map.put("sys_day", "20210107");

            ElasticsearchUtil.addData(JSONObject.fromObject(map), "sjrz_qm_20210107",
                    IndexName.AUDIT_TYPENAME);
        }*/

    }
    @Autowired
    RiskTransfer riskTransfer;
    @org.junit.Test
    public void testMail() throws IOException {
        /*RiskParam param = new RiskParam(new Date(), new Date());
        List<RiskVo> analysisOfSourceAndLevel = riskTransfer.getAnalysisOfSourceAndLevel(param);
        System.out.println(analysisOfSourceAndLevel);*/

        /*Map<String, InputStream> map = new HashMap();
        map.put("test.pdf", dsmp);
        EmailUtils.sendMessage("smtp.qq.com", 587, "825869330@qq.com", "jidsybmkquhubfej", new String[]{"1272501502@qq.com"}, "ssssssss", "ssssssssssss", map);*/
        ReportTaskBo bo = new ReportTaskBo();
        ReportTask doo = new ReportTask();
        doo.setId("11111");
        doo.setParams("{ssssss}");
        doo.setCreateTime(new Date());
        ReportTaskParam reportTaskParam = new ReportTaskParam();
        reportTaskParam.setStartTime(new Date());
        bo.setParams(reportTaskParam);
        bo.setId("22222");
        BeanUtils.copyProperties(bo, doo);
        System.out.println(bo);
        System.out.println(doo);
    }
}
