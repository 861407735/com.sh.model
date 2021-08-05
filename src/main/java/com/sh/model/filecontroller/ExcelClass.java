package com.sh.model.filecontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sh.model.common.StringUtil;

public class ExcelClass {
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\richi\\Desktop\\数据目录上报1.xlsx"));
		Workbook wb = new XSSFWorkbook(fis);

		DecimalFormat df = new DecimalFormat("########");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 换表标识符
		String tablenameold = "法轮功人员信息";
		String tablenamenew = "";
		for (int x = 0; x < wb.getNumberOfSheets(); x++) { // x代表 表的数量
			Sheet sheet = wb.getSheetAt(x);
			if (sheet == null) {
				continue;
			}
			LinkedHashMap<String, Object> tableInfo = new LinkedHashMap<>();
			LinkedList<LinkedHashMap<String, Object>> fieldInfoList = new LinkedList<LinkedHashMap<String, Object>>();
			// 处理第一个表
			for (int rownum = 2; rownum <= sheet.getLastRowNum(); rownum++) {
				Row row = sheet.getRow(rownum);
				if (row == null) {
					continue;
				}
				// 第一行第一列表信息处理
				for (int column = 0; column <= 29; column++) {
					Object key = sheet.getRow(1).getCell(column);
					if (key != null) {
						String res = StringUtil.replaceBlank(key.toString());// 拿到key
						Cell cell = row.getCell(column);
						if (cell == null) {
							break;
						}
						if (column == 0) {
							tablenamenew = StringUtil.replaceBlank(cell.getStringCellValue());
							if (!tablenameold.equals(tablenamenew)) {
								System.out.println(tablenameold);
								tablenameold = tablenamenew;
								System.out.println(tableInfo);
								System.out.println(fieldInfoList);
								tableInfo = new LinkedHashMap<>();
								fieldInfoList = new LinkedList<LinkedHashMap<String, Object>>();
							}
						}
						switch (cell.getCellType()) {
						case STRING:
							tableInfo.put(res, StringUtil.replaceBlank(cell.getStringCellValue()));
							break;
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date d = cell.getDateCellValue();
								tableInfo.put(res, sdf.format(d));
							} else {
								String temp = df.format(cell.getNumericCellValue());
								tableInfo.put(res, temp);
							}
							break;
						case BOOLEAN:
							tableInfo.put(res, cell.getBooleanCellValue());
							break;
						case ERROR:
							tableInfo.put(res, cell.getErrorCellValue());
							break;
						case FORMULA:
							String num2 = cell.getCellFormula();
							tableInfo.put(res, num2);
							break;
						default:
							tableInfo.put(res, "");
							break;
						}
					} else {
						continue;
					}
				}
				// 第一行第一列字段信息处理
				LinkedHashMap<String, Object> fieldInfo = new LinkedHashMap<String, Object>();
				for (int column = 31; column < 48; column++) {
					Object key = sheet.getRow(1).getCell(column);
					if (key != null) {
						String res = StringUtil.replaceBlank(key.toString());// 拿到key
						Cell cell = row.getCell(column);
						if (cell == null) {
							fieldInfo.put(res, "");
							continue;
						}
						switch (cell.getCellType()) {
						case STRING:
							fieldInfo.put(res, StringUtil.replaceBlank(cell.getStringCellValue()));
							break;
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date d = cell.getDateCellValue();
								fieldInfo.put(res, sdf.format(d));
							} else {
								String temp = df.format(cell.getNumericCellValue());
								fieldInfo.put(res, temp);
							}
							break;
						case BOOLEAN:
							fieldInfo.put(res, cell.getBooleanCellValue());
							break;
						case ERROR:
							fieldInfo.put(res, cell.getErrorCellValue());
							break;
						case FORMULA:
							String num2 = cell.getCellFormula();
							fieldInfo.put(res, num2);
							break;
						default:
							fieldInfo.put(res, "");
							break;
						}
					} else {
						continue;
					}
				}
				if (!fieldInfo.isEmpty()) {
					fieldInfoList.add(fieldInfo);
				}
			}
			System.out.println(tablenameold);
			System.out.println(tableInfo);
			System.out.println(fieldInfoList);
		}
		wb.close();
	}
	
	public void getHttpClinet(String url,Object obj) throws ClientProtocolException, IOException {
		HttpPost httpPost=new HttpPost(url);
		httpPost.addHeader("", "");
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();//连接池
		CloseableHttpResponse response=HttpClients.custom().setConnectionManager(cm).build().execute(httpPost);  
		HttpEntity entity = response.getEntity(); 
        if (entity != null) {  
            String result = EntityUtils.toString(entity,Consts.UTF_8);
            System.out.println(result);
            response.close();  
        }  
	}
}
