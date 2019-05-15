package cn.sucec.major_adjust_system.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

import cn.sucec.major_adjust_system.model.DetailwarningTable;
import cn.sucec.major_adjust_system.model.MajorTable;
import cn.sucec.major_adjust_system.model.WarningTable;

public class ExcelutilMine {

	public static List<MajorTable> importExcel(InputStream in) throws IOException {

		/**
		 * POI��������ȡExcel
		 * 
		 * Excel����Ϊ����ԭ������#N/A�������Ϊ�����ȡʱ����˴�ͳһ����try-catch���
		 * ��ʵҪ�Ը���ֻ�и������������Ϊ����������Ϊ�յ�����ɸѡ֮ǰ���Ѿ����ų�����
		 * ����˵��ÿһ��Ϊ�յ�ʱ���ȡ�Ĵ�ʩ
		 * ���id������Ϊ�գ������ǿ�����Ϊ10000
		 * רҵ���룺����Ϊ��
		 * רҵ���ƣ�����Ϊ��
		 * ������ݣ����ǿ�������Ϊ10000
		 * �Ƿ�����5��δ�������������Ϊ��
		 * ���ƣ����Ʋ���Ϊ��
		 * �Ƿ�������רҵ���Ƿ����岻��Ϊ��
		 * �Ƿ���ʡ���������б�Ԥ����������Ϊ��
		 * �Ƿ���У�������б�Ԥ��������Ϊ��
		 * רҵ������-ȥ��ģ���Ϊ�գ�����Ϊ900.0
		 * רҵ������-����ģ���Ϊ�գ�����Ϊ900.0
		 * LastTransfer��900.0
		 * MiddleTransfer��900.0
		 * ThisTransfer:900.0
		 * HighStudentNumber��10000
		 * LowStudentNumber��10000
		 * FirstEmploymentRate��900.0
		 * SecondEmploymentRate��900.0
		 * CurrentPosrgraduteRate��900.0
		 * 
		 * Integer ����catch����Ϊ10000��
		 * Double ����catch����Ϊ900.0��
		 * �ٷ�����ȡ��ΪС�����������ݿ�ʱ����������������󣬹ʷŴ���100��
		 * */
		
		List<MajorTable> majorExceList = new ArrayList<MajorTable>();
		FileInputStream fileIn = (FileInputStream) in;

		Workbook wb0 = new XSSFWorkbook(fileIn);
		// ��ȡExcel�ĵ��еĵ�һ����
		Sheet sheet = wb0.getSheetAt(0);

		// ����������
		for (Row row : sheet) {

			// ȥ������
			if (row == null) {
				continue;
			}
			// �������е���

			Integer Id;
			Integer EnrollmentYear; // �������
			String ContinuousEnrollment; // �Ƿ�����5��δ����
			Integer Years; // ����
			String Art; // �Ƿ�������רҵ
			String ProvinceWarning; // �Ƿ���ʡ���������б�Ԥ��
			String SchoolWarning; // �Ƿ���У��רҵ�����б�Ԥ��
			Double LastAdjustment; // רҵ������-ȥ���
			Double ThisAdjustment; // רҵ������-�����
			Double LastTransfer; // ����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���
			Double MiddleTransfer; // ����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���
			Double ThisTransfer; // ����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���
			Integer HighStudentNumber; // תרҵ��רҵ������ѧ������-ȥ���
			Integer LowStudentNumber; // תרҵ��רҵ������ѧ������-�����
			Double FirstEmploymentRate; // ���ξ�ҵ��-ȥ���
			Double SecondEmploymentRate; // ���ξ�ҵ��-�����
			Double CurrentPosrgraduteRate; // Ӧ���ҵ��������

			try {
				Id = (int) row.getCell(0).getNumericCellValue();
			} catch (Exception e) {
				Id = 10000;
			}

			// רҵ�����רҵ���Ʋ�����Ϊ#N/A
			String MajorCode = row.getCell(1).getStringCellValue();
			String MajorName = row.getCell(2).getStringCellValue();

			try {
				EnrollmentYear = (int) row.getCell(3).getNumericCellValue();//�������
			} catch (IllegalStateException e) {
				EnrollmentYear = 10000;
			}
			try {
				ContinuousEnrollment = row.getCell(4).getStringCellValue();//�Ƿ���������δ����
			} catch (Exception e) {
				ContinuousEnrollment="NULL";
			}
			
			try {
				Years = (int) row.getCell(5).getNumericCellValue();//����
			} catch (Exception e) {
				Years = 10000;
			}

			try {
				Art = row.getCell(6).getStringCellValue();//�Ƿ�����
			} catch (Exception e) {
				Art = "NULL";
			}
			try {
				ProvinceWarning = row.getCell(7).getStringCellValue();//ʡԤ��
			} catch (Exception e) {
				ProvinceWarning = "NULL";
			}
			try {
				SchoolWarning = row.getCell(8).getStringCellValue();//УԤ��
			} catch (Exception e) {
				SchoolWarning = "NULL";
			}

			try {
				LastAdjustment =100*( row.getCell(9).getNumericCellValue());//ȥ�������
			} catch (Exception e) {
				LastAdjustment = 900.0;//ȡ��ֵ��Ĭ��Ϊ0.0����ͬ
			}
			try {
				ThisAdjustment = 100*(row.getCell(10).getNumericCellValue());
			} catch (Exception e) {
				ThisAdjustment = 900.0;//ȡ��ֵ��Ĭ��Ϊ0.0
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
				HighStudentNumber = 10000;//����ȡ��ֵ��Ĭ��10000
			}
			try {
				LowStudentNumber = (int) row.getCell(15).getNumericCellValue();
			} catch (Exception e) {
				LowStudentNumber = 10000;
			}
			try {
				FirstEmploymentRate = 100*(row.getCell(16).getNumericCellValue());
			} catch (Exception e) {
				FirstEmploymentRate = 900.0;//��ҵ��ȡ��ֵ��Ĭ��900
			}
			try {
				SecondEmploymentRate = 100*(row.getCell(17).getNumericCellValue());
			} catch (Exception e) {
				SecondEmploymentRate = 900.0;
			}
			try {
				CurrentPosrgraduteRate = 100*(row.getCell(18).getNumericCellValue());
			} catch (Exception e) {
				CurrentPosrgraduteRate = 900.0;//������ȡ��ֵ��Ĭ��900
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

		// �����µ�Excel������
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ��Excel�������н�һ����������Ϊȱʡֵ, Ҳ����ָ��Sheet����
		XSSFSheet sheet = workbook.createSheet(sheetName);

		/* ������ʽ */

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		// ע�⣺4.0���ϰ汾��������д����ͳ��ʽ��Ч
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// ˮƽ����
		cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// ��ֱ����

		// ���ñ߿�
		/*
		 * cellStyle.setBorderBottom(BorderStyle.THIN);
		 * cellStyle.setBorderTop(BorderStyle.THIN);
		 * cellStyle.setBorderLeft(BorderStyle.THIN);
		 * cellStyle.setBorderRight(BorderStyle.THIN);
		 */

		sheet.setDefaultRowHeightInPoints(20);// ����ȱʡ�и�
		sheet.setDefaultColumnWidth(30);// ����ȱʡ�п�
		// sheet.setColumnWidth(3, 50);// ����ָ���е��п�

		/** �������� */

		// ��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
		XSSFRow row1 = sheet.createRow(0);
		// ������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
		XSSFCell cell = row1.createCell(0);
		// ���úϲ���Ԫ�����ݣ���һ�еı�����
		cell.setCellValue("Ԥ��רҵ");

		// �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

		// ��ͷ
		XSSFRow row2 = sheet.createRow(1);
		Cell cell0 = row2.createCell(0);
		cell0.setCellValue("���");
		cell0.setCellStyle(cellStyle);//���ø�ʽΪ����
		Cell cell1 = row2.createCell(1);
		cell1.setCellValue("רҵ����");
		cell1.setCellStyle(cellStyle);
		Cell cell2 = row2.createCell(2);
		cell2.setCellValue("רҵ����");
		cell2.setCellStyle(cellStyle);
		Cell cell3 = row2.createCell(3);
		cell3.setCellValue("Ԥ��ԭ��");
		cell3.setCellStyle(cellStyle);

		/*
		 * row2.createCell(1).setCellValue("רҵ����");
		 * row2.createCell(2).setCellValue("רҵ����");
		 * row2.createCell(3).setCellValue("Ԥ��ԭ��");
		 */

		cell.setCellStyle(cellStyle);
		row1.setRowStyle(cellStyle);
		row2.setRowStyle(cellStyle);

		// ��������
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
			
			//ʹ��������ʽȥ��ԭ���ı��еķָ�����
			String reasonString=wt.getWarningReason();
			Pattern pattern=Pattern.compile("#");
			Matcher matcher=pattern.matcher(reasonString);
			matcher.replaceAll("");
			
			cell03.setCellValue( reasonString);
			cell03.setCellStyle(cellStyle);

			/*
			 * row.createCell(1).setCellValue(wt.getMajor_code());
			 * row.createCell(2).setCellValue(wt.getMajor_name());
			 * row.createCell(3).setCellValue(wt.getWaring_reason());
			 */

			row.setRowStyle(cellStyle);
			i++;
		}

		//д��Ӳ���ļ�
			workbook.write(outputStream);
			workbook.close();
		
	}
}