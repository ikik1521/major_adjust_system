package cn.sucec.major_adjust_system.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sucec.major_adjust_system.dao.BaseDao;
import cn.sucec.major_adjust_system.dao.MajorTableDao;
import cn.sucec.major_adjust_system.dao.WarningTableDao;
import cn.sucec.major_adjust_system.model.MajorTable;
import cn.sucec.major_adjust_system.model.WarningTable;
import cn.sucec.major_adjust_system.tools.Change;
import cn.sucec.major_adjust_system.tools.ExcelutilMine;

@Service("majorTableService")
public class MajorTableServiceImpl extends BaseServiceImpl<MajorTable> implements MajorTableService {

	@Autowired
	private MajorTableDao majorTableDao;

	@Override
	public BaseDao getBaseDao() {
		return majorTableDao;
	}

	@Autowired
	private WarningTableDao warningTableDao;
	
	String tableName = "major_table";

	/**
	 * 导入Excel
	 */
	@Override
	public void importExcelInfo(InputStream in) {
		List<MajorTable> majorTables;
		// 插入
		try {
			majorTables = ExcelutilMine.importExcel(in);
			for (MajorTable major : majorTables) {
				super.add(major);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 数据导出
	 */
	@Override
	public void exportExcelInfo(ServletOutputStream outputStream) {
		// 根据条件查询数据，把数据装载到一个list中
		List<WarningTable> list = warningTableDao.getAll();

		// 调用ExcelUtil的方法
		try {
			ExcelutilMine.createExcelFile("专业预警", list,outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getMajorName(String majorCode) {
		return majorTableDao.getMajorName(majorCode);
	}

	@Override
	public int getEyByMajorCode(String majorCode) {
		return majorTableDao.getEyByMajorCode(majorCode);
	}

	@Override
	public List<MajorTable> selectAll(Integer nowYear) {
		return majorTableDao.selectAll(nowYear);
	}

	@Override
	public List<MajorTable> getMajorLess20(Integer nowYear) {
		return majorTableDao.getMajorLess20(nowYear);
	}

	@Override
	public void zhuanYeFenXi(int year) {
		List<MajorTable> majors = selectAll(year);
		int number = majors.size();
		int count = (int) Math.round(number*0.05);
		Change.ThisAdjustment(majors, count, year);
	}
	
}
