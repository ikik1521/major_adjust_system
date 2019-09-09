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
//	 * �ϴ��ļ���upload �鿴����Ԥ��רҵ��thisYear �鿴����Ԥ��רҵ��lastYear ��ͣ����רҵ��stopMajor
//	 * ����רҵ��cancleMajor �����ļ���download ������ݣ�deleteAll ����רҵ�����ѯԤ��ԭ��queryReasons
//	 * �˳���¼��logout
//	 * 
//	 * URLǰ׺��/major_adjust_system/
//	 

	// �ϴ��ļ�
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile upfile) throws Exception {

		InputStream inputExcel = upfile.getInputStream();

		majorTableService.importExcelInfo(inputExcel);

		inputExcel.close();
		
		return "�ϴ��ɹ�";
	}

	// �����ļ���download
	@RequestMapping("/download")
	@ResponseBody // Ϊ�˽��getOutputStream() has already been called for this response
	public void downloadExcel(HttpServletResponse response) throws IOException, InvocationTargetException,
			ClassNotFoundException, IllegalAccessException, IntrospectionException, ParseException {

		response.reset();
		response.setContentType("application/x-execl");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String("Ԥ������.xlsx".getBytes(), "ISO-8859-1"));
		ServletOutputStream outputStream = response.getOutputStream();

		majorTableService.exportExcelInfo(outputStream);

		if (outputStream != null) {
			outputStream.close();
		}

	}

	// �鿴����Ԥ��רҵ��thisYear
	@RequestMapping("/thisYear")
	@ResponseBody
	public List<DetailwarningTable> queryThisYearWaringMajor() {

		Calendar cale = null;
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);

		//System.out.println("������רҵ����");
		int n=majorTableService.existed(year);
		
		if (n>0) {
			//System.out.println("�ѷ�������Ҫ���·�����ɾ�����ݿ��е��������");
			
			List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getWarningMajorByYear(year);
			
			/*for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
				System.out.println(detailwarningMajor);
			}*/
			return detailwarningMajors;
		}else {
			// �Դ������ı�������ݷ���������Ԥ��רҵ������ϸԤ��רҵ���ݱ���
			majorTableService.zhuanYeFenXi(year);
			// ��Ԥ��רҵ���ݱ���з���������Ҫ��ͣ������רҵ
			pauseTableService.fenXiZanTingZhuanYe(year);
			// ��Ԥ��רҵ���majorTable����з���������Ҫ����רҵ������רҵ
			cancleTableService.fenXiCheXiaoZhuanYe(year);
			
			// ������ݲ�ѯԤ��רҵ
			List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getWarningMajorByYear(year);
			
			/*for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
				System.out.println(detailwarningMajor);
			}*/
			return detailwarningMajors;
		}
		
	}

	// �鿴����Ԥ��רҵ��lastYear
	@RequestMapping("/lastYear")
	@ResponseBody
	public List<DetailwarningTable> queryLastYearWaringMajor() {

		//System.out.println("�����ǲ鿴����Ԥ��רҵ");
		List<DetailwarningTable> detailwarningMajors = detailwarningTableService.getAll();
		
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			//System.out.println(detailwarningMajor);
		}
		
		for (DetailwarningTable detailwarningMajor : detailwarningMajors) {
			//System.out.println(detailwarningMajor);
		}

		return detailwarningMajors;

	}
	
	//������ݲ�ѯĳ��Ԥ��
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
	
	// ��ͣ����רҵ��stopMajor
	@RequestMapping("/stopMajor")
	@ResponseBody
	public List<PauseTable> queryStopMajor() {

		//System.out.println("�����ǲ鿴��ͣ����Ԥ��רҵ");
		List<PauseTable> pauseMajors = pauseTableService.selectAll();
		
		/*for (PauseTable pauseMajor : pauseMajors) {
			System.out.println(pauseMajor);
		}*/

		return pauseMajors;

	}

	// ����רҵ��cancleMajor
	@RequestMapping("/cancleMajor")
	@ResponseBody
	public List<CancleTable> queryCancleMajor() {

		//System.out.println("�����ǲ鿴����רҵ����");
		List<CancleTable> cancleTables = cancleTableService.selectAll();
		/*for (CancleTable cancleMajor : cancleTables) {
			System.out.println("����רҵ��" + cancleMajor);
		}*/
		return cancleTables;
	}

	// ������ݣ�deleteAll
	@RequestMapping("/deleteAll")
	public void deleteAll() {

		// ͨ��service����ÿһ�����ɾ��������������ϸԤ��רҵ�������ݱ�����Ķ������
		cancleTableService.clearDate();
		majorTableService.clearDate();
		pauseTableService.clearDate();
		pwarningTableService.clearDate();
		warningTableService.clearDate();
	}
	
	//����ɾ��
	@RequestMapping("/deleteOne")
	@ResponseBody
	public void deleteDetail(@RequestParam("warningYear") int year,@RequestParam("majorCode") String majorCode) {
		detailwarningTableService.deleteByYearAndMajorCode(year, majorCode);
		//System.out.println("��ִ��ɾ��������");
		
	}
	// ��¼
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
			//System.out.println("�û�����������������µ�¼");
			User user2 =new User();
			return user2;
		}
	}

	// �˳���¼��logout
	@RequestMapping("/logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.invalidate();
		//System.out.println("���˳�");

		response.sendRedirect("main.html");
	}

}
