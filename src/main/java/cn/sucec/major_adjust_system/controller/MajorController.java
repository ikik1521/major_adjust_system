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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.sucec.major_adjust_system.model.DetailwarningTable;
import cn.sucec.major_adjust_system.service.DetailwarningTableService;
import cn.sucec.major_adjust_system.service.MajorTableService;
import cn.sucec.major_adjust_system.service.PwarningTableService;

@Controller
public class MajorController {
	
	@Autowired
	private MajorTableService majorTableService;
	
	@Autowired
	private DetailwarningTableService detailwarningTableService;

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
		majorTableService.zhuanYeFenXi(year);
		return "success";
	}
	
	@RequestMapping("/chakanyujing")
	public String chakanyujing() {
		System.out.println("这里是查看预警专业");
		List<DetailwarningTable> detailwarningTables = detailwarningTableService.selectAll();
		
		return "success";
	}
}
