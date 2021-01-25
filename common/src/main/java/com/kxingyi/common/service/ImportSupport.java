package com.kxingyi.common.service;

import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.util.ImportUtil;
import com.kxingyi.common.validator.ValidationGroup;
import com.kxingyi.common.web.response.HandleResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author hujie
 * @date 2020/2/6 13:47
 **/
public interface ImportSupport<Ipo, Bo> {

    String FAIL_MESSAGE = "读取excel文件失败，请检查excel文件内容及格式，尝试重新上传文件!";
    /**
     * 生成某实体的导入模板excel
     */
    default ResponseEntity<byte[]> downLoadImportTemplate(String sheetName, Class<?> templateClazz) {
        try {
            return ImportUtil.generateImportTemplate(sheetName, templateClazz);
        } catch (IOException e) {
            throw new BusinessException("创建" + sheetName + "失败!");
        }
    }

    /**
     * 解析Validator校验结果
     *
     * @return 返回解析的结果字符串，多个错误使用【,】分隔
     */
    default String analyze(Set<ConstraintViolation<Bo>> result) {
        StringJoiner sj = new StringJoiner("，");
        result.forEach(error -> sj.add(error.getMessage()));
        return sj.toString();
    }

    default List<Ipo> getItoList(MultipartFile file, Class<Ipo> clazz) {
        try {
            return ImportUtil.getItoList(file, clazz);
        } catch (Exception e) {
            throw new BusinessException(FAIL_MESSAGE);
        }
    }

    /**
     * Bo的校验，在本项目中建议实现此方法时分为{@link ValidationGroup 这里的组}
     *
     * @param bo    校验的对象
     * @param groups 具体的组
     * @return
     */
    String validate(Bo bo, Class<?>... groups);

    /**
     * 把导入模板obj转换为对应的Bo，因为实体的create和update方法的参数都为Bo且需要使用Bo进行参数校验
     *
     * @param ipo 导入模板obj
     * @return
     */
    Bo ipoToBo(Ipo ipo);

    HandleResult importData(MultipartFile file);
}
