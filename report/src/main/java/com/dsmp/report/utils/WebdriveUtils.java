package com.dsmp.report.utils;

import com.kxingyi.common.util.JsonUtils;
import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTaskParam;
import com.dsmp.report.common.enums.CycleEnum;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author byliu
 **/
@Component
public class WebdriveUtils {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Value("${webdriver.path}")
    private static String webdriverPath;
    private static String port;
    private static String contextPath;

    public static boolean preview(String reportName, ReportTask task, Map<String, Object> param) {
        WebDriver driver = openChrome();

        CycleEnum cycleType = task.getCycleType();
        Date startTime = null, endTime = null;
        LocalDate now = LocalDate.now();
        switch (cycleType) {
            case DAY:
                startTime = DateUtils.localDate2Date(now);
                endTime = DateUtils.localDate2Date(now.plusDays(-1));
                break;
            case WEEK:
                startTime = DateUtils.localDate2Date(now);
                endTime = DateUtils.localDate2Date(now.plusWeeks(-1));
                break;
            case MONTH:
                startTime = DateUtils.localDate2Date(now);
                endTime = DateUtils.localDate2Date(now.plusMonths(-1));
                break;
            case YEAR:
                startTime = DateUtils.localDate2Date(now);
                endTime = DateUtils.localDate2Date(now.plusYears(-1));
                break;
            case CUSTOM:
                ReportTaskParam reportTaskParam = JsonUtils.toObject(task.getParams(), ReportTaskParam.class);
                startTime = reportTaskParam.getStartTime();
                endTime = reportTaskParam.getEndTime();
                break;
            default:
                break;
        }
        String startTimeFormat = FORMATTER.format(startTime);
        String endTimeFormat = FORMATTER.format(endTime);
        // 把时间参数协会到用于生成报表文件的congfig对象的报表参数中
        param.put("startTime", startTimeFormat);
        param.put("endTime", endTimeFormat);
        String url = "http://127.0.0.1:" + port + contextPath + "/ureport/preview?_u=dsmp:" +
                encode(reportName) + "&taskId=" + task.getId() +
                "&startTime=" + startTimeFormat + "&endTime=" + endTimeFormat;
        driver.navigate().to(url);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return true;
    }

    public static WebDriver openChrome() {
        WebDriver driver;
        // cherome driver
        System.setProperty("webdriver.chrome.driver", webdriverPath);
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置为 headless 模式 （无头浏览器）
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        //设置隐性等待时间
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        return driver;
    }

    protected static String encode(String value) {
        if (value == null) {
            return value;
        }
        try {
            value = URLEncoder.encode(value, "utf-8");
            return value;
        } catch (Exception ex) {
            return value;
        }
    }

    @Value("${server.port}")
    public void setPort(String port) {
        WebdriveUtils.port = port;
    }

    @Value("${server.servlet.context-path}")
    public void setContextPath(String contextPath) {
        WebdriveUtils.contextPath = contextPath;
    }

    @Value("${webdriver.path}")
    public void setWebdriverPath(String webdriverPath) {
        WebdriveUtils.webdriverPath = webdriverPath;
    }

}
