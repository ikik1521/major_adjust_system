package cn.sucec.major_adjust_system.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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
import cn.sucec.major_adjust_system.service.PwarningTableService;
import cn.sucec.major_adjust_system.service.UserService;
import cn.sucec.major_adjust_system.service.WarningTableService;

@Controller
public class MajorController {
	
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

	// 上传文件会自动绑定到MultipartFile中
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {

		// 上传文件名
		String filename = file.getOriginalFilename();

		InputStream inputExcel = file.getInputStream();

		majorTableService.importExcelInfo(inputExcel);

		inputExcel.close();

		System.out.println(filename);

		return "success";

	}

	@RequestMapping("/download")
	public String downloadExcel(HttpServletResponse response) throws IOException, InvocationTargetException,
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
		return "success";

	}
	
	@RequestMapping("/zhuanyefenxi")
	public String zhuanyefenxi() {
		int year = 2019;
		System.out.println("这里是专业分析");
		// 对传上来的表进行数据分析，挑出预警专业放入详细预警专业数据表中
		majorTableService.zhuanYeFenXi(year);
		// 对预警专业数据表进行分析，挑出要暂停招生的专业
		pauseTableService.fenXiZanTingZhuanYe(year);
		// 对预警专业表和majorTaxble表进行分析，挑出要进行专业撤销的专业
		cancleTableService.fenXiCheXiaoZhuanYe(year);
		return "success";
	}
	
	@RequestMapping("/chakanjinnianyujing")
	public String chakanjinnianyujing() {
		int year = 2019;
		System.out.println("这里是查看今年预警专业");
		List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getWarningMajorByYear(year);
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			System.out.println(detailwarningMajor);
		}
		return "success";
	}
	
	@RequestMapping("/chakanwangnianyujing")
	public String chakanwangnianyujing() {
		System.out.println("这里是查看往年预警专业");
		List<DetailwarningTable> detailwarningMajors = detailwarningTableService.selectAll();
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			System.out.println(detailwarningMajor);
		}
		return "success";
	}
	
	@RequestMapping("/chaKanZanTingZhuanYe")
	public String chaKanZanTingZhuanYe() {
		System.out.println("这里是查看暂停招生预警专业");
		List<PauseTable> pauseMajors = pauseTableService.selectAll();
		for (PauseTable pauseMajor : pauseMajors) {
			System.out.println(pauseMajor);
		}
		return "success";
	}
	
	@RequestMapping("/chaKanZhuanYeCheXiao")
	public String chaKanZhuanYeCheXiao() {
		System.out.println("这里是查看撤销专业名单");
		List<CancleTable> cancleTables = cancleTableService.selectAll();
		for (CancleTable cancleMajor : cancleTables) {
			System.out.println("======撤销专业：" + cancleMajor);
		}
		return "success";
	}
	
	@RequestMapping("/qingkongshuju")
	public String qingkongshuju() {
		System.out.println(""+"这里是清空所有数据");
		// 通过service调用每一个表的删除方法，除了详细预警专业名单数据表以外的都清除掉
		cancleTableService.clearDate();
		majorTableService.clearDate();
		pauseTableService.clearDate();
		pwarningTableService.clearDate();
		warningTableService.clearDate();
		return "success";
	}

	@RequestMapping(value="/login",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,Model model,
			HttpSession session,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		User user = userService.getUserByUserName(username);
		
		if(user != null && password.equals(user.getPassword())) {
			
			session.setAttribute("USER_INFO",user);
			
			resp.sendRedirect("context.jsp");
			return "登陆成功！";
		}else {
			//System.out.println("用户名或密码错误，请重新登录");
			resp.sendRedirect("index.jsp");
			return "用户名或密码错误，请重新登录！";
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpSession session,HttpServletResponse response) throws IOException {
		session.invalidate();
		System.out.println(" 已退出");
		
		response.sendRedirect("index.jsp");
	}
}
