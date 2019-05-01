package cn.sucec.major_adjust_system.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.sucec.major_adjust_system.model.CancleTable;
import cn.sucec.major_adjust_system.model.DetailwarningTable;
import cn.sucec.major_adjust_system.model.PauseTable;
import cn.sucec.major_adjust_system.model.User;
import cn.sucec.major_adjust_system.service.CancleTableService;
import cn.sucec.major_adjust_system.service.DetailwarningTableService;
import cn.sucec.major_adjust_system.service.MajorTableService;
import cn.sucec.major_adjust_system.service.PauseTableService;
import cn.sucec.major_adjust_system.service.UserService;

//@Controller
public class Test2 {
	@Autowired
	private MajorTableService majorTableService;
	
	@Autowired
	private DetailwarningTableService detailwarningTableService;
	
	@Autowired
	private PauseTableService pauseTableService;
	
	@Autowired
	private CancleTableService cancleTableService;
	
	@Autowired
	private UserService userService;
	
	 /*上传文件：upload
	 查看今年预警专业：thisYear
	 查看往年预警专业：lastYear
	 暂停招生专业：stopMajor
	 撤销专业：cancleMajor
	 下载文件：download
	 清空数据：deleteAll
	根据专业代码查询预警原因：queryReasons
	 退出登录：logout
	
	 URL前缀：/major_adjust_system/
	*/
	

	// 上传文件
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void  upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {

		InputStream inputExcel = file.getInputStream();

		majorTableService.importExcelInfo(inputExcel);

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

		majorTableService.exportExcelInfo(outputStream);

		if (outputStream != null) {
			outputStream.close();
		}

	}
	
	// 查看今年预警专业：thisYear
	@RequestMapping("/thisYear")
	@ResponseBody
	public List<DetailwarningTable> queryThisYearWaringMajor() {
		
		Calendar cale = null;  
        cale = Calendar.getInstance();  
        int year = cale.get(Calendar.YEAR);

        //根据年份查询预警专业
        List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getWarningMajorByYear(year);
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			System.out.println(detailwarningMajor);
      		} 
		return detailwarningMajors;
      	}
      	
      	
      	//查看往年预警专业：lastYear
      	@RequestMapping("/lastYear")
      	@ResponseBody
      	public List<DetailwarningTable> queryLastYearWaringMajor() {
		
		System.out.println("这里是查看往年预警专业");
		List<DetailwarningTable> detailwarningMajors = detailwarningTableService.selectAll();
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			System.out.println(detailwarningMajor);
		}
		
		return detailwarningMajors;
		
	}
	

//	 暂停招生专业：stopMajor
	@RequestMapping("/stopMajor")
	@ResponseBody
	public List<PauseTable> queryStopMajor() {
		
		System.out.println("这里是查看暂停招生预警专业");
		List<PauseTable> pauseMajors = pauseTableService.selectAll();
		for (PauseTable pauseMajor : pauseMajors) {
			System.out.println(pauseMajor);
		}
		
		return pauseMajors;
		
	}
	
//	 撤销专业：cancleMajor
	@RequestMapping("/cancleMajor")
	@ResponseBody
	public List<CancleTable> queryCancleMajor() {
		
		System.out.println("这里是查看撤销专业名单");
		List<CancleTable> cancleTables = cancleTableService.selectAll();
		for (CancleTable cancleMajor : cancleTables) {
			System.out.println("======撤销专业：" + cancleMajor);
		}
		return cancleTables;
	}
		
	
//	 清空数据：deleteAll
	@RequestMapping("/deleteAll")
	@ResponseBody
	public void  deleteAll() {
		
		//清空WaringTable
		//majorService.deleteAll();
	}
	
//登录
	@RequestMapping(value="/login",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,Model model,
			HttpSession session,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		User admin=userService.getAdmin();
		
		if(username.equals(admin.getName())&&password.equals(admin.getPassword())) {
			
			session.setAttribute("USER_INFO",admin);
			
			resp.sendRedirect("context.jsp");
			return "登陆成功";
			
			

		}else {
			System.out.println("用户名或密码错误，请重新登录");
			
			resp.sendRedirect("index.jsp");
			return "用户名或密码错误，请重新登录";
		}
	}
		
//	 退出登录：logout
	@RequestMapping("/logout")
	public void logout(HttpSession session,HttpServletResponse response) throws IOException {
		session.invalidate();
		System.out.println("已退出");
		
		response.sendRedirect("index.jsp");
	}
	
}
