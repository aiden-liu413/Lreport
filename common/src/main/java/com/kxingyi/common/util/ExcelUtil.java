package com.kxingyi.common.util;

import com.kxingyi.common.annotation.excelhead.ImportField;
import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.web.request.CellOption;
import com.kxingyi.common.web.request.NullCellOption;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    private static final int DEFAULT_WINDOW_SIZE = 1000;

    public static int MAX_SHEET_ROW = 50000;

    /**
     * 获得Excel表中列的值
     *
     * @param cell
     * @return
     */
    public static String getCellVal(Cell cell) {
        if (cell == null)
            return "";
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue().trim();
    }


    private static void setCellVal(Cell cell, String val, CellStyle cellStyle) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(val == null ? "" : val);
    }

    /**
     * @param sheetName   工作簿名
     * @param head        列字
     * @param data        数据
     * @param colWidth    列宽
     * @param hiddenIndex 需要隐藏的列
     * @param fontName    字体名
     * @return
     */
    public static Workbook createExcel(String sheetName, List<String> head, List<List<String>> data
            , List<Integer> colWidth, List<Integer> hiddenIndex, String fontName) {
        Workbook wb = new SXSSFWorkbook(DEFAULT_WINDOW_SIZE);
        int count = data.size();
        int sheets = (int) Math.ceil((double) count / MAX_SHEET_ROW);
        int rowOfSheet = MAX_SHEET_ROW;
        Sheet sheet = wb.createSheet(sheetName);
        Row row = sheet.createRow(0);
        CellStyle basicCellStyle = wb.createCellStyle();
        basicCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        basicCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
                .getIndex());
        basicCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        basicCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        basicCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        basicCellStyle.setFont(font);
        basicCellStyle.setWrapText(true);
        row.setRowStyle(basicCellStyle);
        Cell cell;
        int index = 0;
        for (String code : head) {
            cell = row.createCell(index);
            cell.setCellStyle(basicCellStyle);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            sheet.setColumnWidth(index, colWidth.get(index) * 1500);
            cell.setCellValue(code);
            index++;
        }
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);

        CellStyle redCellStyle = wb.createCellStyle();// 红色单元格
        redCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        redCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        font = wb.createFont();
        font.setFontName("宋体");
        font.setColor(IndexedColors.RED.getIndex());
        font.setFontHeightInPoints((short) 12);
        redCellStyle.setFont(font);
        redCellStyle.setWrapText(true);

        for (int s = 0; s < sheets; s++) {
            if (s > 0) {
                sheet = wb.createSheet(sheetName + "_" + s);
                row = sheet.createRow(0);
                row.setHeightInPoints(18);
                index = 0;
                for (String code : head) {
                    cell = row.createCell(index);
                    cell.setCellStyle(basicCellStyle);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    sheet.setColumnWidth(index, colWidth.get(index) * 1500);
                    cell.setCellValue(code);
                    index++;
                }
            }
            int indexOfStore = 0;
            for (int i = 0; i < rowOfSheet; i++) {
                indexOfStore = s * rowOfSheet + i;
                if (indexOfStore == count)
                    break;
                List<String> record = data.get(indexOfStore);
                row = sheet.createRow(i + 1);
                row.setHeightInPoints(18);
                row.setRowStyle(cellStyle);
                cell = null;
                index = 0;
                for (String cellData : record) {
                    setCellVal(row.createCell(index++), cellData, cellStyle);
                }
            }
            for (Integer integer : hiddenIndex) {
                sheet.setColumnHidden(integer.intValue(), true);
            }
        }
        return wb;
    }

    public static Workbook createExcel(String sheetName, List<String> head, List<List<String>> data, List<Integer> hiddenIndex) {
        List<Integer> colWidth = new ArrayList<>(head.size());
        for (int i = 0; i < head.size(); i++) {
            colWidth.add(5);//默认列宽为5
        }
        return createExcel(sheetName, head, data, colWidth, hiddenIndex, "宋体");
    }

    public static Workbook createExcel(String sheetName, List<String> head, List<List<String>> data) {
        return createExcel(sheetName, head, data, Collections.emptyList());
    }

    public static Workbook createExcel(String sheetName, List<String> head, List<List<String>> data, Class<?> clazz) {
        Workbook wb = new SXSSFWorkbook(DEFAULT_WINDOW_SIZE);
        int count = data.size();
        int sheets = (int) Math.ceil((double) count / MAX_SHEET_ROW);
        int rowOfSheet = MAX_SHEET_ROW;
        Sheet sheet = wb.createSheet(sheetName);
        Row row = sheet.createRow(0);
        CellStyle basicCellStyle = wb.createCellStyle();
        basicCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        basicCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
                .getIndex());
        basicCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        basicCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        basicCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        basicCellStyle.setFont(font);
        basicCellStyle.setWrapText(true);
        row.setRowStyle(basicCellStyle);
        Cell cell;
        int index = 0;
        List<Cell> heads = new ArrayList<>(head.size());
        for (String code : head) {
            cell = row.createCell(index);
            cell.setCellStyle(basicCellStyle);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            sheet.setColumnWidth(index, 5 * 1500);
            cell.setCellValue(code);
            heads.add(cell);
            index++;
        }
        constructExportHead(wb, sheet, heads, clazz);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);

        CellStyle redCellStyle = wb.createCellStyle();// 红色单元格
        redCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        redCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        font = wb.createFont();
        font.setFontName("宋体");
        font.setColor(IndexedColors.RED.getIndex());
        font.setFontHeightInPoints((short) 12);
        redCellStyle.setFont(font);
        redCellStyle.setWrapText(true);

        for (int s = 0; s < sheets; s++) {
            if (s > 0) {
                sheet = wb.createSheet(sheetName + "_" + s);
                row = sheet.createRow(0);
                row.setHeightInPoints(18);
                index = 0;
                heads.clear();
                for (String code : head) {
                    cell = row.createCell(index);
                    cell.setCellStyle(basicCellStyle);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    sheet.setColumnWidth(index, 5 * 1500);
                    cell.setCellValue(code);
                    heads.add(cell);
                    index++;
                }
                constructExportHead(wb, sheet, heads, clazz);
            }

            int indexOfStore = 0;
            for (int i = 0; i < rowOfSheet; i++) {
                indexOfStore = s * rowOfSheet + i;
                if (indexOfStore == count)
                    break;
                List<String> record = data.get(indexOfStore);
                row = sheet.createRow(i + 1);
                row.setHeightInPoints(18);
                row.setRowStyle(cellStyle);
                cell = null;
                index = 0;
                for (String cellData : record) {
                    setCellVal(row.createCell(index++), cellData, cellStyle);
                }
            }
        }
        return wb;
    }

    private static void constructExportHead(Workbook wb, Sheet sheet, List<Cell> cells, Class<?> clazz) {
        String sheetName = sheet.getSheetName();
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0, j = 0; i < fields.length; i++) {
            Field field = fields[i];
            ImportField importField = field.getAnnotation(ImportField.class);
            if (importField != null) {
                Cell cell = cells.get(j);
                j++;
                if (StringUtils.isNotBlank(importField.comment())) {
                    cell.setCellComment(writeComment(sheet, importField.fieldCell(), importField.comment()));
                }
                if (importField.option() != NullCellOption.class) {
                    try {
                        String[] option = getOption(importField.option());
                        createSelectScope(wb, sheet, option, importField.fieldCell());
                    } catch (IllegalAccessException | InstantiationException e) {
                        log.error("创建" + sheetName + "失败", e);
                        throw new BusinessException("创建" + sheetName + "失败!");
                    } catch (IllegalArgumentException e) {
                        // 可能已经有同名的选择框了，复用它
                        useSelectScope(wb, sheet, importField.fieldCell());
                    }
                }
            }
        }
    }

    public static boolean validateExcelHeader(Sheet sheet, List<String> exceptedHeader) {
        Row row = sheet.getRow(0);
        if (row == null || row.getLastCellNum() != exceptedHeader.size()) {
            return false;
        }

        int columnCount = row.getLastCellNum();
        for (int n = 0; n < columnCount; n++) {
            Cell cell = row.getCell(n);
            if (cell == null)
                return false;
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String cellValue = cell.getStringCellValue().trim();
            if (!cellValue.equals(exceptedHeader.get(n))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据列名查找对应的ColumnIndex，否则返回-1
     *
     * @param sheet
     * @param headerName
     * @return
     */
    public static int findExcelHeaderIndex(Sheet sheet, String headerName) {
        Row row = sheet.getRow(0);
        if (row == null) {
            return -1;
        }

        int columnCount = row.getLastCellNum();
        for (int n = 0; n < columnCount; n++) {
            Cell cell = row.getCell(n);
            if (cell == null)
                continue;
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String cellValue = cell.getStringCellValue().trim();
            if (cellValue.equals(headerName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    public static ResponseEntity<byte[]> downloadExcel(String sheetName, Workbook wb) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.add("Access-Control-Expose-Headers", "Content-Disposition");
        httpHeaders.add("Content-Disposition", "attchement;filename="
                + URLEncoder.encode(sheetName, "UTF-8") + "-" + System.currentTimeMillis() + ".xlsx");
        return new ResponseEntity<>(bos.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    public static Workbook createExcel(String sheetName, Class<?> ipoClazz) {
        Workbook wb = new SXSSFWorkbook(DEFAULT_WINDOW_SIZE);
        CellStyle cellStyle = createCellStyle(wb);
        Sheet sheet = wb.createSheet(sheetName);
        Row head = sheet.createRow(0);
        sheet.setDefaultColumnWidth(20);
        head.setHeightInPoints(20);
        Field[] fields = ipoClazz.getDeclaredFields();
        for (Field field : fields) {
            ImportField importField = field.getAnnotation(ImportField.class);
            if (importField != null) {
                Cell cell = head.createCell(importField.fieldCell());
                cell.setCellValue(importField.fieldName());
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (StringUtils.isNotBlank(importField.comment())) {
                    cell.setCellComment(writeComment(sheet, importField.fieldCell(), importField.comment()));
                }
                if (importField.option() != NullCellOption.class) {
                    try {
                        String[] option = getOption(importField.option());
                        createSelectScope(wb, sheet, option, importField.fieldCell());
                    } catch (IllegalAccessException | InstantiationException e) {
                        log.error("创建" + sheetName + "失败", e);
                        throw new BusinessException("创建" + sheetName + "失败!");
                    }
                }
                cell.setCellStyle(cellStyle);
            }
        }
        return wb;
    }

    public static CellStyle createCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中

        // 背景色
        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderRight(CellStyle.BORDER_THIN);//右边框
        style.setBorderBottom(CellStyle.BORDER_THIN);//下边框
        // 生成一个字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        // 把字体 应用到当前样式
        style.setFont(font);
        return style;
    }

    private static Comment writeComment(Sheet sheet, int cellNum, String cotent) {
        Drawing patr = sheet.createDrawingPatriarch();
        Comment comment = patr.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) cellNum, 0, (short) (cellNum + 2), 2));
        RichTextString str = new XSSFRichTextString(cotent);
        comment.setString(str);
        return comment;
    }

    /**
     * 获取EXCEL单元格的值，一律转为String返回
     *
     * @param cell
     * @return
     */
    public static String getCellStrValue(Cell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {  // 处理日期格式、时间格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = cell.getDateCellValue();
                        value = sdf.format(date);
                    } else {
                        DataFormatter data = new DataFormatter();
                        value = data.formatCellValue(cell);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value += cell.getBooleanCellValue() + "";
                    break;
                default:
                    break;
            }
        }
        if (StringUtils.isBlank(value)) return null;
        else return value;
    }

    private static String[] getOption(Class option) throws IllegalAccessException, InstantiationException {
        Class[] classes = option.getInterfaces();
        if (classes.length == 0) {
            throw new BusinessException("option须继承" + CellOption.class.getName() + "接口");
        }
        String interfacePath = classes[0].getName();
        if (!CellOption.class.getName().equals(interfacePath)) {
            throw new BusinessException("option须继承" + CellOption.class.getName() + "接口");
        }
        CellOption cellOption = (CellOption) option.newInstance();
        return cellOption.getOptions();
    }

    /**
     * 创建下拉选项，会创建出一个sheet来存放选项数据，需要把这些sheet隐藏
     */
    private static void createSelectScope(Workbook wb, Sheet sheet, String[] option, int cellNum) {
        int referRows = 1000;//初始化1000行的引用列
        String tempName = "hidden" + cellNum;
        Sheet hidden = wb.createSheet(tempName);
        Cell cell = null;
        for (int i = 0, length = option.length; i < length; i++) {
            String name = option[i];
            Row row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }
        Name namedCell = wb.createName();
        namedCell.setNameName(tempName);
        namedCell.setRefersToFormula(tempName + "!$A$1:$A$" + option.length);
        //加载数据,将名称为hidden的
        useSelectScope(wb, sheet, tempName, cellNum, referRows);
    }

    private static void useSelectScope(Workbook wb, Sheet sheet, int cellNum) {
        useSelectScope(wb, sheet, "hidden" + cellNum, cellNum, 1000);
    }

    private static void useSelectScope(Workbook wb, Sheet sheet, String sheetName, int cellNum, int referRows) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(sheetName);

        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(1, referRows, cellNum,
                cellNum);
        DataValidation validation = validationHelper.createValidation(constraint, addressList);

        hiddenSheet(wb);
        sheet.addValidationData(validation);
    }

    /**
     * 隐藏下拉选择项的sheet
     */
    private static void hiddenSheet(Workbook wb) {
        int sheetNum = wb.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            //第一张表不隐藏
            if (i != 0) wb.setSheetHidden(i, true);
        }
    }
}
