package cn.scuec.major_adjust_system.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.scuec.major_adjust_system.model.CancleTable;
import cn.scuec.major_adjust_system.model.DetailwarningTable;
import cn.scuec.major_adjust_system.model.PauseTable;
import cn.scuec.major_adjust_system.model.User;
import cn.scuec.major_adjust_system.service.CancleTableService;
import cn.scuec.major_adjust_system.service.DetailwarningTableService;
import cn.scuec.major_adjust_system.service.MajorTableService;
import cn.scuec.major_adjust_system.service.PauseTableService;
import cn.scuec.major_adjust_system.service.PwarningTableService;
import cn.scuec.major_adjust_system.service.UserService;
import cn.scuec.major_adjust_system.service.WarningTableService;

@Controller
public class MajorAdjustController {
	@Autowired
	private MajorTableService majorTableService;

	@Autowired
	private DetailwarningTableService detailwarningTableService;

	@Autowired
	private PauseTableService pauseTableService;

	@Autowired
	private CancleTableService cancleTableService;

	@Autowired
	private PwarningTableService pwarningTableService;

	@Autowired
	private WarningTableService warningTableService;

	@Autowired
	private UserService userService;

//	
//	 * 上传文件：upload 查看今年预警专业：thisYear 查看往年预警专业：lastYear 暂停招生专业：stopMajor
//	 * 撤销专业：cancleMajor 下载文件：download 清空数据：deleteAll 根据专业代码查询预警原因：queryReasons
//	 * 退出登录：logout
//	 * 
//	 * URL前缀：/major_adjust_system/
//	 

	// 上传文件
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile upfile) throws Exception {

		InputStream inputExcel = upfile.getInputStream();

		majorTableService.importExcelInfo(inputExcel);

		inputExcel.close();
		
		return "上传成功";
	}

	// 下载文件：download
	@RequestMapping("/download")
	@ResponseBody // 为了解决getOutputStream() has already been called for this response
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

		//System.out.println("这里是专业分析");
		int n=majorTableService.existed(year);
		
		if (n>0) {
			//System.out.println("已分析，若要重新分析请删除数据库中当年的数据");
			
			List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getWarningMajorByYear(year);
			
			/*for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
				System.out.println(detailwarningMajor);
			}*/
			return detailwarningMajors;
		}else {
			// 对传上来的表进行数据分析，挑出预警专业放入详细预警专业数据表中
			majorTableService.zhuanYeFenXi(year);
			// 对预警专业数据表进行分析，挑出要暂停招生的专业
			pauseTableService.fenXiZanTingZhuanYe(year);
			// 对预警专业表和majorTable表进行分析，挑出要进行专业撤销的专业
			cancleTableService.fenXiCheXiaoZhuanYe(year);
			
			// 根据年份查询预警专业
			List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getWarningMajorByYear(year);
			
			/*for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
				System.out.println(detailwarningMajor);
			}*/
			return detailwarningMajors;
		}
		
	}

	// 查看往年预警专业：lastYear
	@RequestMapping("/lastYear")
	@ResponseBody
	public List<DetailwarningTable> queryLastYearWaringMajor() {

		//System.out.println("这里是查看往年预警专业");
		List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getAll();
		
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			//System.out.println(detailwarningMajor);
		}
		
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			//System.out.println(detailwarningMajor);
		}

		return detailwarningMajors;

	}
	
	//根据年份查询某年预警
	@RequestMapping("/searchByYear")
	@ResponseBody
	public List<DetailwarningTable> seachByYear(@RequestParam(value="searchYear") int year) {
		//System.out.println("hello");
		List<DetailwarningTable> listYear=detailwarningTableService.getWarningMajorByYear(year);
		
		/*for (DetailwarningTable detailwarningTable : listYear) {
			System.out.println(detailwarningTable);
		}*/
		return listYear;
	}
	
	// 暂停招生专业：stopMajor
	@RequestMapping("/stopMajor")
	@ResponseBody
	public List<PauseTable> queryStopMajor() {

		//System.out.println("这里是查看暂停招生预警专业");
		List<PauseTable> pauseMajors = pauseTableService.selectAll();
		
		/*for (PauseTable pauseMajor : pauseMajors) {
			System.out.println(pauseMajor);
		}*/

		return pauseMajors;

	}

	// 撤销专业：cancleMajor
	@RequestMapping("/cancleMajor")
	@ResponseBody
	public List<CancleTable> queryCancleMajor() {

		//System.out.println("这里是查看撤销专业名单");
		List<CancleTable> cancleTables = cancleTableService.selectAll();
		/*for (CancleTable cancleMajor : cancleTables) {
			System.out.println("撤销专业：" + cancleMajor);
		}*/
		return cancleTables;
	}

	// 清空数据：deleteAll
	@RequestMapping("/deleteAll")
	public void deleteAll() {

		// 通过service调用每一个表的删除方法，除了详细预警专业名单数据表以外的都清除掉
		cancleTableService.clearDate();
		majorTableService.clearDate();
		pauseTableService.clearDate();
		pwarningTableService.clearDate();
		warningTableService.clearDate();
	}
	
	//单项删除
	@RequestMapping("/deleteOne")
	@ResponseBody
	public void deleteDetail(@RequestParam("warningYear") int year,@RequestParam("majorCode") String majorCode) {
		detailwarningTableService.deleteByYearAndMajorCode(year, majorCode);
		//System.out.println("已执行删除操作！");
		
	}
	// 登录
	@RequestMapping(value = "/login", produces="application/json;charset=UTF-8")
	@ResponseBody
	public User login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		User user = userService.getUserByUserName(username);
		//System.out.println(user);

		if (user != null && password.equals(user.getPassword())) {

			session.setAttribute("USER_INFO", user);
			return user;

		} else {
			//System.out.println("用户名或密码错误，请重新登录");
			User user2 =new User();
			return user2;
		}
	}

	// 退出登录：logout
	@RequestMapping("/logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.invalidate();
		//System.out.println("已退出");

		response.sendRedirect("main.html");
	}

}
