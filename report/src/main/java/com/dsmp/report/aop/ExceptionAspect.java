package com.dsmp.report.aop;

import com.bstek.ureport.exception.ReportException;
import com.dsmp.common.exception.BusinessException;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.log.Looger;
import com.dsmp.report.web.service.IReportTemplteService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author byliu
 * 针对模板的增删改查操作的环绕切面
 **/
@Aspect
@Component
public class ExceptionAspect extends Looger {
    private ExtendJsonProvider extendJsonProvider;
    @Autowired
    private IReportTemplteService IReportTemplteService;

    public void setExtendJsonProvider(ExtendJsonProvider extendJsonProvider) {
        this.extendJsonProvider = extendJsonProvider;
    }

    @Around("execution(* com.dsmp.report.web.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            if(e instanceof BusinessException){
                logger.info(e.getMessage());
                throw e;
            }else{
                logger.info(e.getMessage(), e);
                throw new ReportException("报表系统发生未知错误,请联系管理员！");
            }
        }
    }

    @Around("@annotation(com.dsmp.report.aop.AppendUserId)")
    public void addAfterReturning(ProceedingJoinPoint joinPoint) {
        try {
            Object res = joinPoint.proceed();
            if(!(null == extendJsonProvider)){
                ReportTemplte reportTemplte = (ReportTemplte) res;
                String currentUserId = extendJsonProvider.getExtendJson();
                IReportTemplteService.setExtendJson(extendJsonProvider.getExtendJson(), reportTemplte);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
