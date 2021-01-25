package com.kxingyi.common.util;

import com.kxingyi.common.annotation.excelhead.ImportField;
import com.kxingyi.common.exception.BusinessException;
import com.google.common.collect.Maps;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hujie
 * @date 2020/2/6 15:35
 **/
public class ImportUtil {
    private static Logger log = LoggerFactory.getLogger(ImportUtil.class);

    public static ResponseEntity<byte[]> generateImportTemplate(String sheetName, Class<?> templateClazz) throws IOException {
        Workbook wb = ExcelUtil.createExcel(sheetName, templateClazz);
        return ExcelUtil.downloadExcel(sheetName, wb);
    }

    public static <T> List<T> getItoList(MultipartFile file, Class<T> templateClazz) throws Exception {
        List<T> result = new ArrayList<>();
        OPCPackage pkg = OPCPackage.open(file.getInputStream());
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        ExcelHandler<T> handler = new ExcelHandler<>(templateClazz);
        XMLReader parser = handler.getSheetParser(sst);

        // 根据名称查找sheet
        XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator)r.getSheetsData();
        while (sheets.hasNext()) {
            InputStream sheet = sheets.next();
            String sheetName = sheets.getSheetName();
            if (sheetName.startsWith("hidden")) {
                sheet.close();
                continue;
            }
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            result.addAll(handler.getDataList());
            handler.resetRowNum();
            sheet.close();
        }

        handler = null;

        return result;
    }

    static class ExcelHandler<S> extends DefaultHandler {

        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsSharedString;
        private int curRow = 0;
        private String curCellName = "";
        private List<S> dataList = new ArrayList<>();
        private final Map<Integer, Method> setMethodMap;
        private final Class<S> templateClass;

        /**
         * 读取当前行的数据。key是单元格名称如A1,value是单元格中的值。如果单元格式空,则没有数据。
         */
        private final Map<String, String> rowValueMap = new HashMap<>();

        ExcelHandler(Class<S> templateClass) {
            this.templateClass = templateClass;
            Field[] fields = templateClass.getDeclaredFields();
            Method[] methods = templateClass.getDeclaredMethods();
            this.setMethodMap = Maps.newHashMapWithExpectedSize(fields.length);
            for (Field field : fields) {
                ImportField importField = field.getAnnotation(ImportField.class);
                if (importField != null) {
                    for (Method method : methods) {
                        if (("set" + field.getName()).toLowerCase().equals(method.getName().toLowerCase())) {
                            setMethodMap.put(importField.fieldCell(), method);
                            break;
                        }
                    }
                }
            }
        }

        /**
         * 处理单行数据的回调方法。
         *
         * @param curRow 当前行号
         * @param rowValueMap 当前行的值
         */
        private void optRows(int curRow, Map<String, String> rowValueMap) {
            Map<Integer, String> dataMap = new HashMap<>();
            if (curRow == 0) {
                Map<Integer, String> headers = new HashMap<>();
                rowValueMap.forEach((k,v)->headers.put(covertRowId2Int(k), v));
                validateHeaders(headers, templateClass);
            } else {
                rowValueMap.forEach((k,v)->dataMap.put(covertRowId2Int(k), v));
                dataList.add(convertData(dataMap, templateClass));
            }
        }

        @Override
        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
            // c => 单元格
            if (name.equals("c")) {

                // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
                String cellType = attributes.getValue("t");
                if (cellType != null && cellType.equals("s")) {
                    nextIsSharedString = true;
                } else {
                    nextIsSharedString = false;
                }
            }

            // 置空
            lastContents = "";

            /**
             * 记录当前读取单元格的名称
             */
            String cellName = attributes.getValue("r");
            if (cellName != null && !cellName.isEmpty()) {
                curCellName = cellName;
            }
        }

        @Override
        public void endElement(String uri, String localName, String name) throws SAXException {
            // 根据SST的索引值的到单元格的真正要存储的字符串
            // 这时characters()方法可能会被调用多次
            if (nextIsSharedString) {
                try {
                    int idx = Integer.parseInt(lastContents);
                    lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                } catch (Exception e) {

                }
            }

            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // t => 单元格的值，不在SST中索引的内联值
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            if (name.equals("v") || name.equals("t")) {
                String value = lastContents.trim();
                value = value.equals("") ? null : value;
                rowValueMap.put(curCellName, value);
            } else {
                // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
                if (name.equals("row")) {
                    optRows(curRow, rowValueMap);
                    rowValueMap.clear();
                    curRow++;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // 得到单元格内容的值
            lastContents += new String(ch, start, length);
        }

        /**
         * 获取转换后的数据
         */
        public List<S> getDataList() {
            return dataList;
        }

        /**
         * 重置当前行数，每个sheet解析完后需要执行
         */
        public void resetRowNum() {
            this.dataList.clear();
            this.curRow = 0;
        }

        private <S> S convertData(Map<Integer, String> data, Class<S> clazz) {
            try {

                S result = clazz.newInstance();
                setMethodMap.forEach((k, v) -> {
                    try {
                        v.invoke(result, data.get(k));
                    } catch (Exception e) {
                        log.error("excel数据转化失败，反射调用方法" + v.getName(), e);
                        throw new BusinessException("解析excel文件失败!");
                    }
                });
                return result;
            } catch (Exception e) {
                log.error("excel数据转化失败: ", e);
                throw new BusinessException("解析excel文件失败!");
            }
        }

        private void validateHeaders(Map<Integer, String> outputHeaders, Class<?> clazz) {
            Field[] fields = clazz.getDeclaredFields();
            Map<Integer, String> headers = new HashMap<>(fields.length);
            for (Field field : fields) {
                ImportField importField = field.getAnnotation(ImportField.class);
                if (importField != null) {
                    headers.put(importField.fieldCell(), importField.fieldName());
                }
            }

            if (outputHeaders == null) {
                throw new BusinessException("请下载最新的导入模板!");
            }
            // 导入excel可能和导出excel相同，但实际字段不同，故此验证先关闭
            /*else if (outputHeaders.size() != headers.size()) {
                throw new BusinessException("请下载最新的导入模板!");
            }*/

            for (Map.Entry<Integer, String> entry : headers.entrySet()) {
                String cellValue = outputHeaders.get(entry.getKey());
                if (cellValue == null) {
                    throw new BusinessException("请下载最新的导入模板!");
                }
                if (!cellValue.equals(entry.getValue())) {
                    throw new BusinessException("请下载最新的导入模板!");
                }
            }
        }

        /**
         * 获取单个sheet页的xml解析器。
         * @param sst
         * @return
         * @throws SAXException
         */
        private XMLReader getSheetParser(SharedStringsTable sst) throws SAXException {
            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            this.sst = sst;
            parser.setContentHandler(this);
            return parser;
        }

        private int covertRowId2Int(String cellId) {
            StringBuilder sb = new StringBuilder();
            String column = "";
            //从cellId中提取列号
            for(char c:cellId.toCharArray()){
                if (Character.isAlphabetic(c)){
                    sb.append(c);
                }else{
                    column = sb.toString();
                }
            }
            //列号字符转数字
            int result = 0;
            for (char c : column.toCharArray()) {
                result = result * 26 + (c - 'A');
            }
            return result;
        }
    }
}
