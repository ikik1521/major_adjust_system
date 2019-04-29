package cn.sucec.major_adjust_system.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.sucec.major_adjust_system.model.MajorTable;

public interface MajorTableService extends BaseService<MajorTable>{
	
	public void importExcelInfo(InputStream in);
	
	public void exportExcelInfo(ServletOutputStream outputStream);
	
	public String getMajorName(String majorCode);
	
	public int getEyByMajorCode(String majorCode);
	
	public List<MajorTable> selectAll(Integer nowYear);
	
	public List<MajorTable> getMajorLess20(Integer nowYear);

	public void zhuanYeFenXi(int year);

	public List<MajorTable> getMajorLess20JinNian(int year);

	public List<MajorTable> getMajorLess20QuNian(int year);

	
}
