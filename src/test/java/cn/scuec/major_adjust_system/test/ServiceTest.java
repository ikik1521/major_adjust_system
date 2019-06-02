package cn.scuec.major_adjust_system.test;

import java.beans.IntrospectionException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import cn.scuec.major_adjust_system.service.MajorTableService;
import cn.scuec.major_adjust_system.service.MajorTableServiceImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring.xml" })
public class ServiceTest {
	
	@Autowired
	MajorTableService majorTableService;

	

//	//输出Excel表单
//	@Test
//	public void testexport1() throws IOException, InvocationTargetException, ClassNotFoundException,
//			IllegalAccessException, IntrospectionException, net.sf.jsqlparser.parser.ParseException, ParseException {
//
//		//XSSFWorkbook workbook = (XSSFWorkbook) mService.exportExcelInfo();
//		
//		OutputStream out = new FileOutputStream("E://预警专业名单.xlsx");
//		workbook.write(out);
//		out.close();
//	}
//
//	@Test
//	public void testImportExcelInfo() throws IOException {
//		majorTableService.importExcelInfo(in);
//	}
}
