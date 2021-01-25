package com.kxingyi.common.util;

import com.kxingyi.common.annotation.excelhead.ExportField;
import com.kxingyi.common.annotation.excelhead.ImportField;
import com.kxingyi.common.enums.ExportFieldDataType;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author hujie
 * @date 2020/2/6 15:34
 **/
public class ExportUtil {
    private static Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 把数据List转换为excel
     *
     * @param originData    用于创建excel的数据,类型为List<XXXVo>
     * @param templateClazz excel表头的模板，为XXXVo
     * @return
     */
    public static <T> ResponseEntity<byte[]> generateExcel(String sheetName, List<T> originData, Class<T> templateClazz) throws IOException {
        Field[] fields = templateClazz.getDeclaredFields();
        Method[] methods = templateClazz.getDeclaredMethods();
        LinkedHashMap<Field, Method> getMethodMap = Maps.newLinkedHashMapWithExpectedSize(methods.length);
        List<String> excelHeader = new ArrayList<>();
        List<List<String>> rowList = new ArrayList<>(originData.size());
        for (Field field : fields) {
            ExportField exportField = field.getAnnotation(ExportField.class);
            if (exportField != null) {
                excelHeader.add(exportField.fieldName());
                for (Method method : methods) {
                    if (("get" + field.getName()).toLowerCase().equals(method.getName().toLowerCase())) {
                        getMethodMap.put(field, method);
                        break;
                    }
                }
            }
        }
        originData.forEach(od -> rowList.add(getRowData(od, getMethodMap)));
        Workbook wb = ExcelUtil.createExcel(sheetName, excelHeader, rowList);
        return ExcelUtil.downloadExcel(sheetName, wb);
    }

    /**
     * 导出excel，用于
     * @param templateClazz 同时有{@link ExportField}和{@link ImportField}两种注解时。
     *
     */
    public static <T> ResponseEntity<byte[]> exportExcel(String sheetName, List<T> originData, Class<T> templateClazz) throws IOException {
        Field[] fields = templateClazz.getDeclaredFields();
        Method[] methods = templateClazz.getDeclaredMethods();
        LinkedHashMap<Field, Method> getMethodMap = Maps.newLinkedHashMapWithExpectedSize(methods.length);
        List<String> excelHeader = new ArrayList<>();
        List<List<String>> rowList = new ArrayList<>(originData.size());
        for (Field field : fields) {
            ExportField exportField = field.getAnnotation(ExportField.class);
            if (exportField != null) {
                excelHeader.add(exportField.fieldName());
                for (Method method : methods) {
                    if (("get" + field.getName()).toLowerCase().equals(method.getName().toLowerCase())) {
                        getMethodMap.put(field, method);
                        break;
                    }
                }
            }
        }
        originData.forEach(od -> rowList.add(getRowData(od, getMethodMap)));
        Workbook wb = ExcelUtil.createExcel(sheetName, excelHeader, rowList, templateClazz);
        return ExcelUtil.downloadExcel(sheetName, wb);
    }

    private static List<String> getRowData(Object o, Map<Field, Method> getMethodMap) {
        List<String> result = new ArrayList<>(getMethodMap.keySet().size());
        getMethodMap.forEach((k, v) -> {
            ExportField exportField = k.getAnnotation(ExportField.class);
            Object obj = null;
            try {
                obj = v.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("导出为Excel失败，Field信息" + k.toString(), e);
                result.add("导出此数据错误");
                return;
            }

            //使用LinkedHashMap与表头List保持一致，如果反射调用get方法返回为Null需要使用一个空字符串占位
            if (obj == null) {
                result.add("");
                return;
            }

            try {
                if (exportField.fieldDataType() == ExportFieldDataType.STRING || exportField.fieldDataType() == ExportFieldDataType.NUMBER) {
                    result.add(obj.toString());
                } else if (exportField.fieldDataType() == ExportFieldDataType.DATE) {
                    result.add(DateUtil.formatDefault((Date) obj));
                } else if (exportField.fieldDataType() == ExportFieldDataType.ENUM) {
                    Method method = obj.getClass().getDeclaredMethod(exportField.enumExportMethod());
                    result.add((String) method.invoke(obj));
                } else if (exportField.fieldDataType() == ExportFieldDataType.BOOLEAN) {
                    if ((Boolean) obj) result.add("是");
                    else result.add("否");
                } else if (exportField.fieldDataType() == ExportFieldDataType.OBJECT) {
                    Method method = obj.getClass().getDeclaredMethod(exportField.objectExportMethod());
                    result.add((String) method.invoke(obj));
                } else {
                    if (StringUtils.isNotBlank(exportField.listExportMethod())) {
                        ParameterizedType listGenericType = (ParameterizedType) k.getGenericType();
                        Class<?> clazz = (Class<?>) listGenericType.getActualTypeArguments()[0];
                        Method method = null;
                        try {
                            method = clazz.getDeclaredMethod(exportField.listExportMethod());
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        List dataList = (List) obj;
                        StringJoiner sj = new StringJoiner(",");
                        for (Object d : dataList) {
                            sj.add((String) method.invoke(d));
                        }
                        result.add(sj.toString());
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                logger.error("导出为Excel失败，Field信息" + k.toString(), e);
                result.add("导出此数据错误");
            }
        });
        return result;
    }
}