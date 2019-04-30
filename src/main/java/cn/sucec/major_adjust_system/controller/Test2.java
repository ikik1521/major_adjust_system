/*package cn.sucec.major_adjust_system.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.sucec.major_adjust_system.model.Major;
import cn.sucec.major_adjust_system.model.WaringTable;
import cn.sucec.major_adjust_system.service.MajorService;
import net.sf.jsqlparser.parser.ParseException;

@Controller
public class Test2 {

	@Autowired
	MajorService majorService;
	
	 上传文件：upload
	 查看今年预警专业：thisYear
	 查看往年预警专业：lastYear
	 暂停招生专业：stopMajor
	 撤销专业：cancleMajor
	 下载文件：download
	 清空数据：deleteAll
	根据专业代码查询预警原因：queryReasons
	 退出登录：logout
	
	 URL前缀：/major_adjust_system/
	
	

	// 上传文件
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void  upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {

		InputStream inputExcel = file.getInputStream();

		majorService.importExcelInfo(inputExcel);

		inputExcel.close();

	}

	//下载文件：download
	@RequestMapping("/download")
	public void downloadExcel(HttpServletResponse response) throws IOException, InvocationTargetException,
			ClassNotFoundException, IllegalAccessException, IntrospectionException, ParseException {

		response.reset();
		response.setContentType("application/x-execl");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String("预警名单.xlsx".getBytes(), "ISO-8859-1"));
		ServletOutputStream outputStream = response.getOutputStream();

		majorService.exportExcelInfo(outputStream);

		if (outputStream != null) {
			outputStream.close();
		}

	}
	
	// 查看今年预警专业：thisYear
	@RequestMapping("/thisYear")
	@ResponseBody
	public String queryThisYearWaringMajor() {
		
		Calendar cale = null;  
        cale = Calendar.getInstance();  
        int year = cale.get(Calendar.YEAR);

//根据年份查询预警专业
		List<majorTable> warningTable=majorService.querythisYear(year);
       
       //预警专业数据库中需要放入的对象
       List<WaringTable> waringTables=new ArrayList<WaringTable>();

       //为预警专业属性赋值
       for (Major majorTable : warningTable) {
    	   
    	   WaringTable waringTable=new WaringTable();
    	   waringTable.setMajor_code(majorTable.getMajorCode());
    	   waringTable.setMajor_name(majorTable.getMajorName());
    	   waringTable.setWaring_year(year);
    	   //为原因属性赋值
    	   waringTable.setWaring_reason(waring_reason);
    	   //将预警专业放入list中
    	   waringTables.add(waringTable);
    	   
	}
      //将今年预警专业放到数据库中
      //majorService.saveStopMajor(List<WaringTable> waringTables);
      		
		return null;  
	}
	
	
	//查看往年预警专业：lastYear
	@RequestMapping("/lastYear")
	@ResponseBody
	public String queryLastYearWaringMajor() {
		
		//查询往年预警专业名单，返回List<WarningTable>。WarningTable对象只有四个属性：年份，代码，名字 ，原因
		//List<WarningTable> lasTables=majorService.getLastWaringMajor();
		
		
		return null;
		
	}
	

//	 暂停招生专业：stopMajor
	@RequestMapping("/stopMajor")
	@ResponseBody
	public String queryStopMajor() {
		
		//查询暂停招生专业，返回List<StopMajor>， StopMajor对象只有四个属性：年份，代码，名字 ，原因
		//List<stopMajor> lastTables=majorService.getStopMajor();
		
		
		//预警专业数据库中需要放入的对象
	       List<WaringTable> waringTables=new ArrayList<WaringTable>();

	       //为预警专业属性赋值
	       for (Major majorTable : warningTable) {
	    	   
	    	   WaringTable waringTable=new WaringTable();
	    	   
	    	   stopMajor.setMajor_code(majorTable.getMajorCode());
	    	   stopMajor.setMajor_name(majorTable.getMajorName());
	    	   stopMajor.setWaring_year(year);
	    	   //为原因属性赋值
	    	   stopMajor.setWaring_reason(waring_reason);
	    	   //将预警专业放入list中
	    	   waringTables.add(waringTable);
	    	   
		}		//将暂停招生专业放到数据库中
		//majorService.saveStopMajor(lastTables);
		
		return null;
		
	}
	
//	 撤销专业：cancleMajor
	@RequestMapping("/cancleMajor")
	@ResponseBody
	public String queryCancleMajor() {
		
		//查询暂停招生专业，返回List<StopMajor>， StopMajor对象只有四个属性：年份，代码，名字 ，原因
		//List<cancleMajor> cancleMajors =majorService.CancleMajor();
		
		//将撤销专业放到数据库中
		//majorService.saveCancleMajor(cancleMajors)
		return null;
	}
		
	
//	 清空数据：deleteAll
	@RequestMapping("/deleteAll")
	@ResponseBody
	public void  deleteAll() {
		
		//清空WaringTable
		//majorService.deleteAll();
	}
	
//登录
	@RequestMapping("/login")
	@ResponseBody
	public void  login(String user,String password) {}
		
//	 退出登录：logout
	@RequestMapping("/login")
	@ResponseBody
	public void  logout(String user,String password) {}
		
	
}
*/