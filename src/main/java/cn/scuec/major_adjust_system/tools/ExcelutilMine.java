package cn.scuec.major_adjust_system.tools;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.cglib.beans.BeanMap;

import cn.scuec.major_adjust_system.model.CancleTable;
import cn.scuec.major_adjust_system.model.DetailwarningTable;
import cn.scuec.major_adjust_system.model.MajorTable;
import cn.scuec.major_adjust_system.model.PauseTable;
import cn.scuec.major_adjust_system.model.WarningTable;

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

	public static void createExcelFile( List<DetailwarningTable> thisYearlist,List<DetailwarningTable> list,List<PauseTable> pauseList,List<CancleTable> cancleList,ServletOutputStream outputStream) throws IOException {

		// 创建新的Excel工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
		XSSFSheet sheet = workbook.createSheet("今年预警专业");
		XSSFSheet sheet2 = workbook.createSheet("今年详细数据");
		XSSFSheet sheet3 = workbook.createSheet("往年预警名单"); 
		XSSFSheet sheet4 = workbook.createSheet("暂停招生专业"); 
		XSSFSheet sheet5 = workbook.createSheet("取消专业名单"); 

		/* 设置样式 */

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		// 注意：4.0以上版本必须这样写，传统方式无效
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// 垂直居中

		// 设置边框 cellStyle.setBorderBottom(BorderStyle.THIN);

		sheet.setDefaultRowHeightInPoints(20);// 设置缺省列高
		sheet.setDefaultColumnWidth(30);// 设置缺省列宽
		// sheet.setColumnWidth(3, 50);// 设置指定列的列宽

		/** 创建今年简略内容 */
		
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
		 */

		cell.setCellStyle(cellStyle);
		row1.setRowStyle(cellStyle);
		row2.setRowStyle(cellStyle);

		// 填充表单内容
		int i = 2;
		for (DetailwarningTable wt : thisYearlist) {

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
			
			//使用正则表达式去除原因文本中的分隔符号
			String reasonString=wt.getWarningReason();
			Pattern pattern=Pattern.compile("#");
			Matcher matcher=pattern.matcher(reasonString);
			matcher.replaceAll("");
			
			cell03.setCellValue( reasonString);
			cell03.setCellStyle(cellStyle);

			i++;
		}
		
			/** 创建今年详细内容 */

		sheet2.setDefaultRowHeightInPoints(20);// 设置缺省列高
		sheet2.setDefaultColumnWidth(10);// 设置缺省列宽
		
		Calendar cale = null;
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		
			XSSFRow sheet2_row0 = sheet2.createRow(0);
			Cell sheet2_cell0 = sheet2_row0.createCell(0);
			sheet2_cell0.setCellValue("年份");
			sheet2_cell0.setCellStyle(cellStyle);//设置格式为居中
			Cell sheet2_cell1 = sheet2_row0.createCell(1);
			sheet2_cell1.setCellValue("专业代码");
			sheet2_cell1.setCellStyle(cellStyle);
			Cell sheet2_cell2 = sheet2_row0.createCell(2);
			sheet2_cell2.setCellValue("专业名称");
			sheet2_cell2.setCellStyle(cellStyle);
			Cell sheet2_cell3 = sheet2_row0.createCell(3);
			sheet2_cell3.setCellValue("招生年份");
			sheet2_cell3.setCellStyle(cellStyle);
			Cell sheet2_cell4 = sheet2_row0.createCell(4);
			sheet2_cell4.setCellValue("是否连续五年未招生");
			sheet2_cell4.setCellStyle(cellStyle);
			Cell sheet2_cell5 = sheet2_row0.createCell(5);
			sheet2_cell5.setCellValue("年制");
			sheet2_cell5.setCellStyle(cellStyle);
			Cell sheet2_cell6 = sheet2_row0.createCell(6);
			sheet2_cell6.setCellValue("是否艺体类专业");
			sheet2_cell6.setCellStyle(cellStyle);
			Cell sheet2_cell7 = sheet2_row0.createCell(7);
			sheet2_cell7.setCellValue("是否在省部级评估中被预警");
			sheet2_cell7.setCellStyle(cellStyle);
			Cell sheet2_cell8 = sheet2_row0.createCell(8);
			sheet2_cell8.setCellValue("是否在校级专业评估中预警");
			sheet2_cell8.setCellStyle(cellStyle);
			Cell sheet2_cell9 = sheet2_row0.createCell(9);
			sheet2_cell9.setCellValue((year-2)+"专业调剂率");
			sheet2_cell9.setCellStyle(cellStyle);
			Cell sheet2_cella = sheet2_row0.createCell(10);
			sheet2_cella.setCellValue((year-1)+"专业调剂率");
			sheet2_cella.setCellStyle(cellStyle);
			Cell sheet2_cellb = sheet2_row0.createCell(11);
			sheet2_cellb.setCellValue((year-2)+"级-"+(year-2)+"年申请转出人数/该专业本年级在籍在册在校生人数的比例");
			sheet2_cellb.setCellStyle(cellStyle);
			Cell sheet2_cellc = sheet2_row0.createCell(12);
			sheet2_cellc.setCellValue((year-2)+"级-"+(year-1)+"年申请转出人数/该专业本年级在籍在册在校生人数的比例");
			sheet2_cellc.setCellStyle(cellStyle);
			Cell sheet2_celld = sheet2_row0.createCell(13);
			sheet2_celld.setCellValue((year-1)+"级-"+(year-1)+"年申请转出人数/该专业本年级在籍在册在校生人数的比例");
			sheet2_celld.setCellStyle(cellStyle);
			Cell sheet2_celle = sheet2_row0.createCell(14);
			sheet2_celle.setCellValue((year-2)+"级-"+(year-1)+"年转专业或专业分流后学生人数");
			sheet2_celle.setCellStyle(cellStyle);
			Cell sheet2_cellf = sheet2_row0.createCell(15);
			sheet2_cellf.setCellValue((year-1)+"级-"+(year-1)+"年转专业或专业分流后学生人数");
			sheet2_cellf.setCellStyle(cellStyle);
			Cell sheet2_cellg = sheet2_row0.createCell(16);
			sheet2_cellg.setCellValue((year-2)+"年初次就业率");
			sheet2_cellg.setCellStyle(cellStyle);
			Cell sheet2_cellh = sheet2_row0.createCell(17);
			sheet2_cellh.setCellValue((year-1)+"年初次就业率");
			sheet2_cellh.setCellStyle(cellStyle);
			Cell sheet2_celli = sheet2_row0.createCell(18);
			sheet2_celli.setCellValue((year-1)+"年应届毕业生考研率");
			sheet2_celli.setCellStyle(cellStyle);
			
			//转换为二维数组
			String [][] ThisYearMajorArr=new String [list.size()][19];
			int thisYearlistSize=thisYearlist.size();
			
				for(int ai=0;ai<thisYearlistSize;ai++) {
					
					ThisYearMajorArr[ai][0]=thisYearlist.get(ai).getWarningYear().toString();
					ThisYearMajorArr[ai][1]=thisYearlist.get(ai).getMajorCode();
					ThisYearMajorArr[ai][2]=thisYearlist.get(ai).getMajorName();
					ThisYearMajorArr[ai][3]=thisYearlist.get(ai).getEnrollmentYear().toString();
					ThisYearMajorArr[ai][4]=thisYearlist.get(ai).getContinuousEnrollment();
					ThisYearMajorArr[ai][5]=thisYearlist.get(ai).getYears().toString();
					ThisYearMajorArr[ai][6]=thisYearlist.get(ai).getArt();
					ThisYearMajorArr[ai][7]=thisYearlist.get(ai).getProvinceWarning();
					ThisYearMajorArr[ai][8]=thisYearlist.get(ai).getSchoolWarning();
					ThisYearMajorArr[ai][9]=thisYearlist.get(ai).getLastAdjustment().toString()+"%";
					ThisYearMajorArr[ai][10]=thisYearlist.get(ai).getThisAdjustment().toString()+"%";
					ThisYearMajorArr[ai][11]=thisYearlist.get(ai).getLastTransfer().toString()+"%";
					ThisYearMajorArr[ai][12]=thisYearlist.get(ai).getMiddleTransfer().toString()+"%";
					ThisYearMajorArr[ai][13]=thisYearlist.get(ai).getThisAdjustment().toString()+"%";
					ThisYearMajorArr[ai][14]=thisYearlist.get(ai).getHighStudentNumber().toString();
					ThisYearMajorArr[ai][15]=thisYearlist.get(ai).getLowStudentNumber().toString();
					ThisYearMajorArr[ai][16]=thisYearlist.get(ai).getFirstEmploymentRate().toString()+"%";
					ThisYearMajorArr[ai][17]=thisYearlist.get(ai).getSecondEmploymentRate().toString()+"%";
					ThisYearMajorArr[ai][18]=thisYearlist.get(ai).getCurrentPosrgraduteRate().toString()+"%";
				}
		      
			for(int ai=0;ai<thisYearlistSize;ai++) {//替换特殊值
				for(int aj=0;aj<19;aj++) {
					if(ThisYearMajorArr[ai][aj].equals("900.0%")||ThisYearMajorArr[ai][aj].equals("10000")) {
						ThisYearMajorArr[ai][aj]="/";
					}
				}
			}

			
			// 填充表单内容
			
			for(int sheet2_i = 1;sheet2_i<=thisYearlistSize;sheet2_i++) {//i 从1开始，此处用<=
				XSSFRow sheet_row=sheet2.createRow(sheet2_i);
				for(int aj=0;aj<19;aj++) {
					sheet_row.createCell(aj).setCellValue(ThisYearMajorArr[sheet2_i-1][aj]);
				}
			}
			
			/**往年所有内容*/

		sheet3.setDefaultRowHeightInPoints(20);// 设置缺省列高
		sheet3.setDefaultColumnWidth(10);// 设置缺省列宽
		
		
			XSSFRow sheet3_row0 = sheet3.createRow(0);
			Cell sheet3_cell0 = sheet3_row0.createCell(0);
			sheet3_cell0.setCellValue("年份");
			sheet3_cell0.setCellStyle(cellStyle);//设置格式为居中
			Cell sheet3_cell1 = sheet3_row0.createCell(1);
			sheet3_cell1.setCellValue("专业代码");
			sheet3_cell1.setCellStyle(cellStyle);
			Cell sheet3_cell2 = sheet3_row0.createCell(2);
			sheet3_cell2.setCellValue("专业名称");
			sheet3_cell2.setCellStyle(cellStyle);
			Cell sheet3_cell3 = sheet3_row0.createCell(3);
			sheet3_cell3.setCellValue("招生年份");
			sheet3_cell3.setCellStyle(cellStyle);
			Cell sheet3_cell4 = sheet3_row0.createCell(4);
			sheet3_cell4.setCellValue("是否连续五年未招生");
			sheet3_cell4.setCellStyle(cellStyle);
			Cell sheet3_cell5 = sheet3_row0.createCell(5);
			sheet3_cell5.setCellValue("年制");
			sheet3_cell5.setCellStyle(cellStyle);
			Cell sheet3_cell6 = sheet3_row0.createCell(6);
			sheet3_cell6.setCellValue("是否艺体类专业");
			sheet3_cell6.setCellStyle(cellStyle);
			Cell sheet3_cell7 = sheet3_row0.createCell(7);
			sheet3_cell7.setCellValue("是否在省部级评估中被预警");
			sheet3_cell7.setCellStyle(cellStyle);
			Cell sheet3_cell8 = sheet3_row0.createCell(8);
			sheet3_cell8.setCellValue("是否在校级专业评估中预警");
			sheet3_cell8.setCellStyle(cellStyle);
			Cell sheet3_cell9 = sheet3_row0.createCell(9);
			sheet3_cell9.setCellValue((year-2)+"专业调剂率");
			sheet3_cell9.setCellStyle(cellStyle);
			Cell sheet3_cella = sheet3_row0.createCell(10);
			sheet3_cella.setCellValue((year-1)+"专业调剂率");
			sheet3_cella.setCellStyle(cellStyle);
			Cell sheet3_cellb = sheet3_row0.createCell(11);
			sheet3_cellb.setCellValue((year-2)+"级-"+(year-2)+"年申请转出人数/该专业本年级在籍在册在校生人数的比例");
			sheet3_cellb.setCellStyle(cellStyle);
			Cell sheet3_cellc = sheet3_row0.createCell(12);
			sheet3_cellc.setCellValue((year-2)+"级-"+(year-1)+"年申请转出人数/该专业本年级在籍在册在校生人数的比例");
			sheet3_cellc.setCellStyle(cellStyle);
			Cell sheet3_celld = sheet3_row0.createCell(13);
			sheet3_celld.setCellValue((year-1)+"级-"+(year-1)+"年申请转出人数/该专业本年级在籍在册在校生人数的比例");
			sheet3_celld.setCellStyle(cellStyle);
			Cell sheet3_celle = sheet3_row0.createCell(14);
			sheet3_celle.setCellValue((year-2)+"级-"+(year-1)+"年转专业或专业分流后学生人数");
			sheet3_celle.setCellStyle(cellStyle);
			Cell sheet3_cellf = sheet3_row0.createCell(15);
			sheet3_cellf.setCellValue((year-1)+"级-"+(year-1)+"年转专业或专业分流后学生人数");
			sheet3_cellf.setCellStyle(cellStyle);
			Cell sheet3_cellg = sheet3_row0.createCell(16);
			sheet3_cellg.setCellValue((year-2)+"年初次就业率");
			sheet3_cellg.setCellStyle(cellStyle);
			Cell sheet3_cellh = sheet3_row0.createCell(17);
			sheet3_cellh.setCellValue((year-1)+"年初次就业率");
			sheet3_cellh.setCellStyle(cellStyle);
			Cell sheet3_celli = sheet3_row0.createCell(18);
			sheet3_celli.setCellValue((year-1)+"年应届毕业生考研率");
			sheet3_celli.setCellStyle(cellStyle);
			Cell sheet3_cellj = sheet3_row0.createCell(19);
			sheet3_cellj.setCellValue("预警原因");
			sheet3_cellj.setCellStyle(cellStyle);
			
			//转换为二维数组
			String [][] majorArr=new String [list.size()][20];
			int listSize=list.size();
			
				for(int ai=0;ai<listSize;ai++) {
					
						majorArr[ai][0]=list.get(ai).getWarningYear().toString();
						majorArr[ai][1]=list.get(ai).getMajorCode();
						majorArr[ai][2]=list.get(ai).getMajorName();
						majorArr[ai][3]=list.get(ai).getEnrollmentYear().toString();
						majorArr[ai][4]=list.get(ai).getContinuousEnrollment();
						majorArr[ai][5]=list.get(ai).getYears().toString();
						majorArr[ai][6]=list.get(ai).getArt();
						majorArr[ai][7]=list.get(ai).getProvinceWarning();
						majorArr[ai][8]=list.get(ai).getSchoolWarning();
						majorArr[ai][9]=list.get(ai).getLastAdjustment().toString()+"%";
						majorArr[ai][10]=list.get(ai).getThisAdjustment().toString()+"%";
						majorArr[ai][11]=list.get(ai).getLastTransfer().toString()+"%";
						majorArr[ai][12]=list.get(ai).getMiddleTransfer().toString()+"%";
						majorArr[ai][13]=list.get(ai).getThisAdjustment().toString()+"%";
						majorArr[ai][14]=list.get(ai).getHighStudentNumber().toString();
						majorArr[ai][15]=list.get(ai).getLowStudentNumber().toString();
						majorArr[ai][16]=list.get(ai).getFirstEmploymentRate().toString()+"%";
						majorArr[ai][17]=list.get(ai).getSecondEmploymentRate().toString()+"%";
						majorArr[ai][18]=list.get(ai).getCurrentPosrgraduteRate().toString()+"%";
						majorArr[ai][19]=list.get(ai).getWarningReason();
				}
		      
			for(int ai=0;ai<listSize;ai++) {//替换特殊值
				for(int aj=0;aj<20;aj++) {
					if(majorArr[ai][aj].equals("900.0%")||majorArr[ai][aj].equals("10000")) {
						majorArr[ai][aj]="/";
					}
				}
			}

			
			// 填充表单内容
			
			for(int sheet3_i = 1;sheet3_i<=listSize;sheet3_i++) {//i 从1开始，此处用<=
				XSSFRow sheet_row=sheet3.createRow(sheet3_i);
				for(int aj=0;aj<20;aj++) {
					sheet_row.createCell(aj).setCellValue(majorArr[sheet3_i-1][aj]);
				}
			}
			
			
			
			
			/*暂停招生专业*/
			sheet4.setDefaultRowHeightInPoints(20);// 设置缺省列高
			sheet4.setDefaultColumnWidth(10);// 设置缺省列宽
			
		XSSFRow sheet4_row0 = sheet4.createRow(0);
			Cell sheet4_cell0 = sheet4_row0.createCell(0);
			sheet4_cell0.setCellValue("年份");
			sheet4_cell0.setCellStyle(cellStyle);//设置格式为居中
			Cell sheet4_cell1 = sheet4_row0.createCell(1);
			sheet4_cell1.setCellValue("专业代码");
			sheet4_cell1.setCellStyle(cellStyle);
			Cell sheet4_cell2 = sheet4_row0.createCell(2);
			sheet4_cell2.setCellValue("专业名称");
			sheet4_cell2.setCellStyle(cellStyle);
			Cell sheet4_cell3 = sheet4_row0.createCell(3);
			sheet4_cell3.setCellValue("暂停招生原因");
			sheet4_cell3.setCellStyle(cellStyle);
			
			int sheet_4i=1;
			for (PauseTable pause : pauseList) {
				XSSFRow sheet4_row=sheet4.createRow(sheet_4i);
				sheet4_row.createCell(0).setCellValue(pause.getPauseYear());
				sheet4_row.createCell(1).setCellValue(pause.getMajorCode());
				sheet4_row.createCell(2).setCellValue(pause.getMajorName());
				sheet4_row.createCell(3).setCellValue(pause.getPauseReason());
			}
			
			
			
			/*撤销专业*/
			sheet5.setDefaultRowHeightInPoints(20);// 设置缺省列高
			sheet5.setDefaultColumnWidth(10);// 设置缺省列宽
			
			XSSFRow sheet5_row2 = sheet5.createRow(0);
			Cell sheet5_cell0 = sheet5_row2.createCell(0);
			sheet5_cell0.setCellValue("年份");
			sheet5_cell0.setCellStyle(cellStyle);//设置格式为居中
			Cell sheet5_cell1 = sheet5_row2.createCell(1);
			sheet5_cell1.setCellValue("专业代码");
			sheet5_cell1.setCellStyle(cellStyle);
			Cell sheet5_cell2 = sheet5_row2.createCell(2);
			sheet5_cell2.setCellValue("专业名称");
			sheet5_cell2.setCellStyle(cellStyle);
			Cell sheet5_cell3 = sheet5_row2.createCell(3);
			sheet5_cell3.setCellValue("专业取消原因");
			sheet5_cell3.setCellStyle(cellStyle);
			
			int sheet_5i=1;
			for (CancleTable cancle : cancleList) {
				XSSFRow sheet5_row=sheet5.createRow(sheet_5i);
				sheet5_row.createCell(0).setCellValue(cancle.getCancleYear());
				sheet5_row.createCell(1).setCellValue(cancle.getMajorCode());
				sheet5_row.createCell(2).setCellValue(cancle.getMajorName());
				sheet5_row.createCell(3).setCellValue(cancle.getCancleReason());
			}

			//写入硬盘文件
			workbook.write(outputStream);
			workbook.close();
		
	}
}