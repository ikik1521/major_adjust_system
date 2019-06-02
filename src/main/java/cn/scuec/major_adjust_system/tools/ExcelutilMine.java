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

	public static void createExcelFile( List<DetailwarningTable> thisYearlist,List<DetailwarningTable> list,List<PauseTable> pauseList,List<CancleTable> cancleList,ServletOutputStream outputStream) throws IOException {

		// �����µ�Excel������
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ��Excel�������н�һ����������Ϊȱʡֵ, Ҳ����ָ��Sheet����
		XSSFSheet sheet = workbook.createSheet("����Ԥ��רҵ");
		XSSFSheet sheet2 = workbook.createSheet("������ϸ����");
		XSSFSheet sheet3 = workbook.createSheet("����Ԥ������"); 
		XSSFSheet sheet4 = workbook.createSheet("��ͣ����רҵ"); 
		XSSFSheet sheet5 = workbook.createSheet("ȡ��רҵ����"); 

		/* ������ʽ */

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		// ע�⣺4.0���ϰ汾��������д����ͳ��ʽ��Ч
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// ˮƽ����
		cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// ��ֱ����

		// ���ñ߿� cellStyle.setBorderBottom(BorderStyle.THIN);

		sheet.setDefaultRowHeightInPoints(20);// ����ȱʡ�и�
		sheet.setDefaultColumnWidth(30);// ����ȱʡ�п�
		// sheet.setColumnWidth(3, 50);// ����ָ���е��п�

		/** ��������������� */
		
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
		 */

		cell.setCellStyle(cellStyle);
		row1.setRowStyle(cellStyle);
		row2.setRowStyle(cellStyle);

		// ��������
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
			
			//ʹ��������ʽȥ��ԭ���ı��еķָ�����
			String reasonString=wt.getWarningReason();
			Pattern pattern=Pattern.compile("#");
			Matcher matcher=pattern.matcher(reasonString);
			matcher.replaceAll("");
			
			cell03.setCellValue( reasonString);
			cell03.setCellStyle(cellStyle);

			i++;
		}
		
			/** ����������ϸ���� */

		sheet2.setDefaultRowHeightInPoints(20);// ����ȱʡ�и�
		sheet2.setDefaultColumnWidth(10);// ����ȱʡ�п�
		
		Calendar cale = null;
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		
			XSSFRow sheet2_row0 = sheet2.createRow(0);
			Cell sheet2_cell0 = sheet2_row0.createCell(0);
			sheet2_cell0.setCellValue("���");
			sheet2_cell0.setCellStyle(cellStyle);//���ø�ʽΪ����
			Cell sheet2_cell1 = sheet2_row0.createCell(1);
			sheet2_cell1.setCellValue("רҵ����");
			sheet2_cell1.setCellStyle(cellStyle);
			Cell sheet2_cell2 = sheet2_row0.createCell(2);
			sheet2_cell2.setCellValue("רҵ����");
			sheet2_cell2.setCellStyle(cellStyle);
			Cell sheet2_cell3 = sheet2_row0.createCell(3);
			sheet2_cell3.setCellValue("�������");
			sheet2_cell3.setCellStyle(cellStyle);
			Cell sheet2_cell4 = sheet2_row0.createCell(4);
			sheet2_cell4.setCellValue("�Ƿ���������δ����");
			sheet2_cell4.setCellStyle(cellStyle);
			Cell sheet2_cell5 = sheet2_row0.createCell(5);
			sheet2_cell5.setCellValue("����");
			sheet2_cell5.setCellStyle(cellStyle);
			Cell sheet2_cell6 = sheet2_row0.createCell(6);
			sheet2_cell6.setCellValue("�Ƿ�������רҵ");
			sheet2_cell6.setCellStyle(cellStyle);
			Cell sheet2_cell7 = sheet2_row0.createCell(7);
			sheet2_cell7.setCellValue("�Ƿ���ʡ���������б�Ԥ��");
			sheet2_cell7.setCellStyle(cellStyle);
			Cell sheet2_cell8 = sheet2_row0.createCell(8);
			sheet2_cell8.setCellValue("�Ƿ���У��רҵ������Ԥ��");
			sheet2_cell8.setCellStyle(cellStyle);
			Cell sheet2_cell9 = sheet2_row0.createCell(9);
			sheet2_cell9.setCellValue((year-2)+"רҵ������");
			sheet2_cell9.setCellStyle(cellStyle);
			Cell sheet2_cella = sheet2_row0.createCell(10);
			sheet2_cella.setCellValue((year-1)+"רҵ������");
			sheet2_cella.setCellStyle(cellStyle);
			Cell sheet2_cellb = sheet2_row0.createCell(11);
			sheet2_cellb.setCellValue((year-2)+"��-"+(year-2)+"������ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���");
			sheet2_cellb.setCellStyle(cellStyle);
			Cell sheet2_cellc = sheet2_row0.createCell(12);
			sheet2_cellc.setCellValue((year-2)+"��-"+(year-1)+"������ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���");
			sheet2_cellc.setCellStyle(cellStyle);
			Cell sheet2_celld = sheet2_row0.createCell(13);
			sheet2_celld.setCellValue((year-1)+"��-"+(year-1)+"������ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���");
			sheet2_celld.setCellStyle(cellStyle);
			Cell sheet2_celle = sheet2_row0.createCell(14);
			sheet2_celle.setCellValue((year-2)+"��-"+(year-1)+"��תרҵ��רҵ������ѧ������");
			sheet2_celle.setCellStyle(cellStyle);
			Cell sheet2_cellf = sheet2_row0.createCell(15);
			sheet2_cellf.setCellValue((year-1)+"��-"+(year-1)+"��תרҵ��רҵ������ѧ������");
			sheet2_cellf.setCellStyle(cellStyle);
			Cell sheet2_cellg = sheet2_row0.createCell(16);
			sheet2_cellg.setCellValue((year-2)+"����ξ�ҵ��");
			sheet2_cellg.setCellStyle(cellStyle);
			Cell sheet2_cellh = sheet2_row0.createCell(17);
			sheet2_cellh.setCellValue((year-1)+"����ξ�ҵ��");
			sheet2_cellh.setCellStyle(cellStyle);
			Cell sheet2_celli = sheet2_row0.createCell(18);
			sheet2_celli.setCellValue((year-1)+"��Ӧ���ҵ��������");
			sheet2_celli.setCellStyle(cellStyle);
			
			//ת��Ϊ��ά����
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
		      
			for(int ai=0;ai<thisYearlistSize;ai++) {//�滻����ֵ
				for(int aj=0;aj<19;aj++) {
					if(ThisYearMajorArr[ai][aj].equals("900.0%")||ThisYearMajorArr[ai][aj].equals("10000")) {
						ThisYearMajorArr[ai][aj]="/";
					}
				}
			}

			
			// ��������
			
			for(int sheet2_i = 1;sheet2_i<=thisYearlistSize;sheet2_i++) {//i ��1��ʼ���˴���<=
				XSSFRow sheet_row=sheet2.createRow(sheet2_i);
				for(int aj=0;aj<19;aj++) {
					sheet_row.createCell(aj).setCellValue(ThisYearMajorArr[sheet2_i-1][aj]);
				}
			}
			
			/**������������*/

		sheet3.setDefaultRowHeightInPoints(20);// ����ȱʡ�и�
		sheet3.setDefaultColumnWidth(10);// ����ȱʡ�п�
		
		
			XSSFRow sheet3_row0 = sheet3.createRow(0);
			Cell sheet3_cell0 = sheet3_row0.createCell(0);
			sheet3_cell0.setCellValue("���");
			sheet3_cell0.setCellStyle(cellStyle);//���ø�ʽΪ����
			Cell sheet3_cell1 = sheet3_row0.createCell(1);
			sheet3_cell1.setCellValue("רҵ����");
			sheet3_cell1.setCellStyle(cellStyle);
			Cell sheet3_cell2 = sheet3_row0.createCell(2);
			sheet3_cell2.setCellValue("רҵ����");
			sheet3_cell2.setCellStyle(cellStyle);
			Cell sheet3_cell3 = sheet3_row0.createCell(3);
			sheet3_cell3.setCellValue("�������");
			sheet3_cell3.setCellStyle(cellStyle);
			Cell sheet3_cell4 = sheet3_row0.createCell(4);
			sheet3_cell4.setCellValue("�Ƿ���������δ����");
			sheet3_cell4.setCellStyle(cellStyle);
			Cell sheet3_cell5 = sheet3_row0.createCell(5);
			sheet3_cell5.setCellValue("����");
			sheet3_cell5.setCellStyle(cellStyle);
			Cell sheet3_cell6 = sheet3_row0.createCell(6);
			sheet3_cell6.setCellValue("�Ƿ�������רҵ");
			sheet3_cell6.setCellStyle(cellStyle);
			Cell sheet3_cell7 = sheet3_row0.createCell(7);
			sheet3_cell7.setCellValue("�Ƿ���ʡ���������б�Ԥ��");
			sheet3_cell7.setCellStyle(cellStyle);
			Cell sheet3_cell8 = sheet3_row0.createCell(8);
			sheet3_cell8.setCellValue("�Ƿ���У��רҵ������Ԥ��");
			sheet3_cell8.setCellStyle(cellStyle);
			Cell sheet3_cell9 = sheet3_row0.createCell(9);
			sheet3_cell9.setCellValue((year-2)+"רҵ������");
			sheet3_cell9.setCellStyle(cellStyle);
			Cell sheet3_cella = sheet3_row0.createCell(10);
			sheet3_cella.setCellValue((year-1)+"רҵ������");
			sheet3_cella.setCellStyle(cellStyle);
			Cell sheet3_cellb = sheet3_row0.createCell(11);
			sheet3_cellb.setCellValue((year-2)+"��-"+(year-2)+"������ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���");
			sheet3_cellb.setCellStyle(cellStyle);
			Cell sheet3_cellc = sheet3_row0.createCell(12);
			sheet3_cellc.setCellValue((year-2)+"��-"+(year-1)+"������ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���");
			sheet3_cellc.setCellStyle(cellStyle);
			Cell sheet3_celld = sheet3_row0.createCell(13);
			sheet3_celld.setCellValue((year-1)+"��-"+(year-1)+"������ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���");
			sheet3_celld.setCellStyle(cellStyle);
			Cell sheet3_celle = sheet3_row0.createCell(14);
			sheet3_celle.setCellValue((year-2)+"��-"+(year-1)+"��תרҵ��רҵ������ѧ������");
			sheet3_celle.setCellStyle(cellStyle);
			Cell sheet3_cellf = sheet3_row0.createCell(15);
			sheet3_cellf.setCellValue((year-1)+"��-"+(year-1)+"��תרҵ��רҵ������ѧ������");
			sheet3_cellf.setCellStyle(cellStyle);
			Cell sheet3_cellg = sheet3_row0.createCell(16);
			sheet3_cellg.setCellValue((year-2)+"����ξ�ҵ��");
			sheet3_cellg.setCellStyle(cellStyle);
			Cell sheet3_cellh = sheet3_row0.createCell(17);
			sheet3_cellh.setCellValue((year-1)+"����ξ�ҵ��");
			sheet3_cellh.setCellStyle(cellStyle);
			Cell sheet3_celli = sheet3_row0.createCell(18);
			sheet3_celli.setCellValue((year-1)+"��Ӧ���ҵ��������");
			sheet3_celli.setCellStyle(cellStyle);
			Cell sheet3_cellj = sheet3_row0.createCell(19);
			sheet3_cellj.setCellValue("Ԥ��ԭ��");
			sheet3_cellj.setCellStyle(cellStyle);
			
			//ת��Ϊ��ά����
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
		      
			for(int ai=0;ai<listSize;ai++) {//�滻����ֵ
				for(int aj=0;aj<20;aj++) {
					if(majorArr[ai][aj].equals("900.0%")||majorArr[ai][aj].equals("10000")) {
						majorArr[ai][aj]="/";
					}
				}
			}

			
			// ��������
			
			for(int sheet3_i = 1;sheet3_i<=listSize;sheet3_i++) {//i ��1��ʼ���˴���<=
				XSSFRow sheet_row=sheet3.createRow(sheet3_i);
				for(int aj=0;aj<20;aj++) {
					sheet_row.createCell(aj).setCellValue(majorArr[sheet3_i-1][aj]);
				}
			}
			
			
			
			
			/*��ͣ����רҵ*/
			sheet4.setDefaultRowHeightInPoints(20);// ����ȱʡ�и�
			sheet4.setDefaultColumnWidth(10);// ����ȱʡ�п�
			
		XSSFRow sheet4_row0 = sheet4.createRow(0);
			Cell sheet4_cell0 = sheet4_row0.createCell(0);
			sheet4_cell0.setCellValue("���");
			sheet4_cell0.setCellStyle(cellStyle);//���ø�ʽΪ����
			Cell sheet4_cell1 = sheet4_row0.createCell(1);
			sheet4_cell1.setCellValue("רҵ����");
			sheet4_cell1.setCellStyle(cellStyle);
			Cell sheet4_cell2 = sheet4_row0.createCell(2);
			sheet4_cell2.setCellValue("רҵ����");
			sheet4_cell2.setCellStyle(cellStyle);
			Cell sheet4_cell3 = sheet4_row0.createCell(3);
			sheet4_cell3.setCellValue("��ͣ����ԭ��");
			sheet4_cell3.setCellStyle(cellStyle);
			
			int sheet_4i=1;
			for (PauseTable pause : pauseList) {
				XSSFRow sheet4_row=sheet4.createRow(sheet_4i);
				sheet4_row.createCell(0).setCellValue(pause.getPauseYear());
				sheet4_row.createCell(1).setCellValue(pause.getMajorCode());
				sheet4_row.createCell(2).setCellValue(pause.getMajorName());
				sheet4_row.createCell(3).setCellValue(pause.getPauseReason());
			}
			
			
			
			/*����רҵ*/
			sheet5.setDefaultRowHeightInPoints(20);// ����ȱʡ�и�
			sheet5.setDefaultColumnWidth(10);// ����ȱʡ�п�
			
			XSSFRow sheet5_row2 = sheet5.createRow(0);
			Cell sheet5_cell0 = sheet5_row2.createCell(0);
			sheet5_cell0.setCellValue("���");
			sheet5_cell0.setCellStyle(cellStyle);//���ø�ʽΪ����
			Cell sheet5_cell1 = sheet5_row2.createCell(1);
			sheet5_cell1.setCellValue("רҵ����");
			sheet5_cell1.setCellStyle(cellStyle);
			Cell sheet5_cell2 = sheet5_row2.createCell(2);
			sheet5_cell2.setCellValue("רҵ����");
			sheet5_cell2.setCellStyle(cellStyle);
			Cell sheet5_cell3 = sheet5_row2.createCell(3);
			sheet5_cell3.setCellValue("רҵȡ��ԭ��");
			sheet5_cell3.setCellStyle(cellStyle);
			
			int sheet_5i=1;
			for (CancleTable cancle : cancleList) {
				XSSFRow sheet5_row=sheet5.createRow(sheet_5i);
				sheet5_row.createCell(0).setCellValue(cancle.getCancleYear());
				sheet5_row.createCell(1).setCellValue(cancle.getMajorCode());
				sheet5_row.createCell(2).setCellValue(cancle.getMajorName());
				sheet5_row.createCell(3).setCellValue(cancle.getCancleReason());
			}

			//д��Ӳ���ļ�
			workbook.write(outputStream);
			workbook.close();
		
	}
}