package cn.sucec.major_adjust_system.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.sucec.major_adjust_system.model.DetailwarningTable;
import cn.sucec.major_adjust_system.model.MajorTable;
import cn.sucec.major_adjust_system.model.WarningTable;

public class ExcelutilMine {

	public static List<MajorTable> importExcel(InputStream in) throws IOException {

		/**
		 * POI：用来读取Excel
		 * 
		 * Excel中因为计算原因会出现#N/A的情况，为避免获取时出错此处统一用了try-catch语句
		 * 其实要对付的只有根据人数那里，因为就算是其他为空但是在筛选之前就已经被排除掉了
		 * 这里说明每一项为空的时候采取的措施
		 * 序号id：不会为空，就算是空则设为10000
		 * 专业代码：不会为空
		 * 专业名称：不会为空
		 * 招生年份：若是空则设置为10000
		 * 是否连续5年未招生：这个不会为空
		 * 年制：年制不会为空
		 * 是否艺体类专业：是否艺体不会为空
		 * 是否在省部级评估中被预警：不会为空
		 * 是否在校级评估中被预警：不会为空
		 * 专业调剂率-去年的：若为空，设置为900.0
		 * 专业调剂率-今年的：若为空，设置为900.0
		 * LastTransfer：900.0
		 * MiddleTransfer：900.0
		 * ThisTransfer:900.0
		 * HighStudentNumber：10000
		 * LowStudentNumber：10000
		 * FirstEmploymentRate：900.0
		 * SecondEmploymentRate：900.0
		 * CurrentPosrgraduteRate：900.0
		 * 
		 * Integer 类型catch后置为10000；
		 * Double 类型catch后置为900.0；
		 * 百分数获取后为小数，存入数据库时会出现四舍五入现象，故放大了100倍
		 * */
		
		List<MajorTable> majorExceList = new ArrayList<MajorTable>();
		FileInputStream fileIn = (FileInputStream) in;

		Workbook wb0 = new XSSFWorkbook(fileIn);
		// 获取Excel文档中的第一个表单
		Sheet sheet = wb0.getSheetAt(0);

		// 遍历所有行
		for (Row row : sheet) {

			// 去掉空行
			if (row == null) {
				continue;
			}
			// 遍历所有的列

			Integer Id;
			Integer EnrollmentYear; // 招生年份
			String ContinuousEnrollment; // 是否连续5年未招生
			Integer Years; // 年制
			String Art; // 是否艺体类专业
			String ProvinceWarning; // 是否在省部级评估中被预警
			String SchoolWarning; // 是否在校级专业评估中被预警
			Double LastAdjustment; // 专业调剂率-去年的
			Double ThisAdjustment; // 专业调剂率-今年的
			Double LastTransfer; // 申请转出人数/该专业本年级在籍在册在校生人数的比例
			Double MiddleTransfer; // 申请转出人数/该专业本年级在籍在册在校生人数的比例
			Double ThisTransfer; // 申请转出人数/该专业本年级在籍在册在校生人数的比例
			Integer HighStudentNumber; // 转专业或专业分流后学生人数-去年的
			Integer LowStudentNumber; // 转专业或专业分流后学生人数-今年的
			Double FirstEmploymentRate; // 初次就业率-去年的
			Double SecondEmploymentRate; // 初次就业率-今年的
			Double CurrentPosrgraduteRate; // 应届毕业生考研率

			try {
				Id = (int) row.getCell(0).getNumericCellValue();
			} catch (Exception e) {
				Id = 10000;
			}

			// 专业代码和专业名称不可能为#N/A
			String MajorCode = row.getCell(1).getStringCellValue();
			String MajorName = row.getCell(2).getStringCellValue();

			try {
				EnrollmentYear = (int) row.getCell(3).getNumericCellValue();//招生年份
			} catch (IllegalStateException e) {
				EnrollmentYear = 10000;
			}
			try {
				ContinuousEnrollment = row.getCell(4).getStringCellValue();//是否连续五年未招生
			} catch (Exception e) {
				ContinuousEnrollment="NULL";
			}
			
			try {
				Years = (int) row.getCell(5).getNumericCellValue();//年制
			} catch (Exception e) {
				Years = 10000;
			}

			try {
				Art = row.getCell(6).getStringCellValue();//是否艺体
			} catch (Exception e) {
				Art = "NULL";
			}
			try {
				ProvinceWarning = row.getCell(7).getStringCellValue();//省预警
			} catch (Exception e) {
				ProvinceWarning = "NULL";
			}
			try {
				SchoolWarning = row.getCell(8).getStringCellValue();//校预警
			} catch (Exception e) {
				SchoolWarning = "NULL";
			}

			try {
				LastAdjustment =100*( row.getCell(9).getNumericCellValue());//去年调剂率
			} catch (Exception e) {
				LastAdjustment = 900.0;//取高值，默认为0.0，下同
			}
			try {
				ThisAdjustment = 100*(row.getCell(10).getNumericCellValue());
			} catch (Exception e) {
				ThisAdjustment = 900.0;//取高值，默认为0.0
			}
			try {
				LastTransfer = 100*(row.getCell(11).getNumericCellValue());
			} catch (Exception e) {
				LastTransfer = 0.0;
			}
			try {
				MiddleTransfer = 100*(row.getCell(12).getNumericCellValue());
			} catch (Exception e) {
				MiddleTransfer = 0.0;
			}

			try {
				ThisTransfer = 100*(row.getCell(13).getNumericCellValue());
			} catch (Exception e) {
				ThisTransfer = 0.0;
			}
			try {
				HighStudentNumber = (int) row.getCell(14).getNumericCellValue();
			} catch (Exception e) {
				HighStudentNumber = 10000;//人数取低值，默认10000
			}
			try {
				LowStudentNumber = (int) row.getCell(15).getNumericCellValue();
			} catch (Exception e) {
				LowStudentNumber = 10000;
			}
			try {
				FirstEmploymentRate = 100*(row.getCell(16).getNumericCellValue());
			} catch (Exception e) {
				FirstEmploymentRate = 900.0;//就业率取低值，默认900
			}
			try {
				SecondEmploymentRate = 100*(row.getCell(17).getNumericCellValue());
			} catch (Exception e) {
				SecondEmploymentRate = 900.0;
			}
			try {
				CurrentPosrgraduteRate = 100*(row.getCell(18).getNumericCellValue());
			} catch (Exception e) {
				CurrentPosrgraduteRate = 900.0;//考研率取低值，默认900
			}

			MajorTable major = new MajorTable(Id, MajorCode, MajorName, EnrollmentYear, ContinuousEnrollment, Years, Art,
					ProvinceWarning, SchoolWarning, LastAdjustment, ThisAdjustment, LastTransfer, MiddleTransfer,
					ThisTransfer, HighStudentNumber, LowStudentNumber, FirstEmploymentRate, SecondEmploymentRate,
					CurrentPosrgraduteRate);
			
			majorExceList.add(major);
		}
		return majorExceList;
	}

	public static void createExcelFile(String sheetName, List<DetailwarningTable> list,ServletOutputStream outputStream) throws IOException {

		// 创建新的Excel工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
		XSSFSheet sheet = workbook.createSheet(sheetName);

		/* 设置样式 */

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		// 注意：4.0以上版本必须这样写，传统方式无效
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// 垂直居中

		// 设置边框
		/*
		 * cellStyle.setBorderBottom(BorderStyle.THIN);
		 * cellStyle.setBorderTop(BorderStyle.THIN);
		 * cellStyle.setBorderLeft(BorderStyle.THIN);
		 * cellStyle.setBorderRight(BorderStyle.THIN);
		 */

		sheet.setDefaultRowHeightInPoints(20);// 设置缺省列高
		sheet.setDefaultColumnWidth(30);// 设置缺省列宽
		// sheet.setColumnWidth(3, 50);// 设置指定列的列宽

		/** 创建内容 */

		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		XSSFRow row1 = sheet.createRow(0);
		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		XSSFCell cell = row1.createCell(0);
		// 设置合并单元格内容（第一行的表名）
		cell.setCellValue("预警专业");

		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

		// 表头
		XSSFRow row2 = sheet.createRow(1);
		Cell cell0 = row2.createCell(0);
		cell0.setCellValue("年份");
		cell0.setCellStyle(cellStyle);//设置格式为居中
		Cell cell1 = row2.createCell(1);
		cell1.setCellValue("专业代码");
		cell1.setCellStyle(cellStyle);
		Cell cell2 = row2.createCell(2);
		cell2.setCellValue("专业名称");
		cell2.setCellStyle(cellStyle);
		Cell cell3 = row2.createCell(3);
		cell3.setCellValue("预警原因");
		cell3.setCellStyle(cellStyle);

		/*
		 * row2.createCell(1).setCellValue("专业代码");
		 * row2.createCell(2).setCellValue("专业名称");
		 * row2.createCell(3).setCellValue("预警原因");
		 */

		cell.setCellStyle(cellStyle);
		row1.setRowStyle(cellStyle);
		row2.setRowStyle(cellStyle);

		// 填充表单内容
		int i = 2;
		for (DetailwarningTable wt : list) {

			XSSFRow row = sheet.createRow(i);
			Cell cell00 = row.createCell(0);
			cell00.setCellValue(wt.getWarningYear());
			cell00.setCellStyle(cellStyle);
			Cell cell01 = row.createCell(1);
			cell01.setCellValue(wt.getMajorCode());
			cell01.setCellStyle(cellStyle);
			Cell cell02 = row.createCell(2);
			cell02.setCellValue(wt.getMajorName());
			cell02.setCellStyle(cellStyle);
			Cell cell03 = row.createCell(3);
			cell03.setCellValue(wt.getWarningReason());
			cell03.setCellStyle(cellStyle);

			/*
			 * row.createCell(1).setCellValue(wt.getMajor_code());
			 * row.createCell(2).setCellValue(wt.getMajor_name());
			 * row.createCell(3).setCellValue(wt.getWaring_reason());
			 */

			row.setRowStyle(cellStyle);
			i++;
		}

		//写入硬盘文件
			workbook.write(outputStream);
			workbook.close();
		
	}
}