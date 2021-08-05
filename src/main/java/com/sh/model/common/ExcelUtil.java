package com.sh.model.common;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtil {
    // 表名，行头字段数组，对象列表，输出路径
    public static void exportExcel(List<String> title, List<LinkedList<Object>> listData, HttpServletResponse response)
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
        // 创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(); //通过工作簿创建工作表
        // 创建字体
        HSSFFont fonttitle = wb.createFont();
        fonttitle.setFontName("黑体"); // 字体
        fonttitle.setBold(true); // 宽度
        //设置标题样式
        HSSFCellStyle styleTitle = wb.createCellStyle(); //创建单元格样式
        styleTitle.setAlignment(HorizontalAlignment.CENTER); //水平对齐
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);    //垂直对齐
        styleTitle.setWrapText(true); //设置换行文本
        styleTitle.setFont(fonttitle);
        //设置文本样式
        HSSFCellStyle styleText = wb.createCellStyle();
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitle.setShrinkToFit(true);

        HSSFRow row = sheet.createRow(0);
        // 创建表头
        for (int x = 0; x < title.size(); x++) {  //根据标题个数创建单元格
            Cell cell = row.createCell(x);
            cell.setCellValue(title.get(x));   //设置单元格的值
            cell.setCellStyle(styleTitle);
        }
        for (int i = 0; i < listData.size(); i++) {
            row = sheet.createRow(i + 1);  //根据工作表创建行
            for (int j = 0; j < listData.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                Object obj = listData.get(i).get(j);  //i 行 j 列
                cell.setCellValue(obj == null ? "" : obj.toString());// 字段反射 得到值


                cell.setCellStyle(styleText);
            }
        }
        OutputStream os = null;
        try {
            response.setContentType("application/vnd.ms-excel");  //响应格式
            os = response.getOutputStream();
            wb.write(os);  //工作簿写入流中
            os.flush();
        } catch (Exception e) {
            log.error("导出excel失败", e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 表名，行头字段数组，对象列表，输出路径
    public static LinkedList<LinkedHashMap<String, Object>> importExcel(MultipartFile file) throws IOException {
        Workbook wb = null;
        DecimalFormat df = new DecimalFormat("########");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ("application/vnd.ms-excel".equals(file.getContentType())) {
            wb = new HSSFWorkbook(file.getInputStream());   //Excel2003以前
        } else {
            wb = new XSSFWorkbook(file.getInputStream());   //Excel2007以前
        }
        LinkedList<LinkedHashMap<String, Object>> list = new LinkedList<LinkedHashMap<String, Object>>();
        //传入的是工作簿不一定只有一张表
        for (int x = 0; x < wb.getNumberOfSheets(); x++) { // x代表 表的数量
            Sheet sheet = wb.getSheetAt(x);   //获取工作表
            if (sheet == null) {
                continue;
            }
            for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {  //getLastRowNum 获取最后一行的编号
                Row row = sheet.getRow(rownum);
                if (row == null) {
                    continue;
                }
                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                for (int column = 0; column < row.getLastCellNum(); column++) { //getLastCellNum  获取最后一个单元的数
                    Object key = sheet.getRow(0).getCell(column);
                    if (key != null) {
                        String res = StringUtil.replaceBlank(key.toString());// 拿到key
                        Cell cell = row.getCell(column);
                        if (cell == null) {
                            continue;
                        }
                        switch (cell.getCellType()) {
                            case STRING:
                                map.put(res, StringUtil.replaceBlank(cell.getStringCellValue()));
                                break;
                            case NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date d = cell.getDateCellValue();
                                    map.put(res, sdf.format(d));
                                } else {
                                    String temp = df.format(cell.getNumericCellValue());
                                    map.put(res, temp);
                                }
                                break;
                            case BOOLEAN:
                                map.put(res, cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                map.put(res, cell.getErrorCellValue());
                                break;
                            case FORMULA:
                                String num2 = cell.getCellFormula();
                                map.put(res, num2);
                                break;
                            default:
                                map.put(res, "");
                                break;
                        }
                    } else {
                        continue;
                    }
                }
                if (!map.isEmpty()) {
                    list.add(map);
                }
            }
        }
        wb.close();
        return list;
    }

}
