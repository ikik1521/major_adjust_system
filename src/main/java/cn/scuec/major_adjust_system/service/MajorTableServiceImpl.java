package cn.scuec.major_adjust_system.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.scuec.major_adjust_system.dao.BaseDao;
import cn.scuec.major_adjust_system.dao.DetailwarningTableDao;
import cn.scuec.major_adjust_system.dao.MajorTableDao;
import cn.scuec.major_adjust_system.model.CancleTable;
import cn.scuec.major_adjust_system.model.DetailwarningTable;
import cn.scuec.major_adjust_system.model.MajorTable;
import cn.scuec.major_adjust_system.model.PauseTable;
import cn.scuec.major_adjust_system.model.PwarningTable;
import cn.scuec.major_adjust_system.model.WarningTable;
import cn.scuec.major_adjust_system.tools.Change;
import cn.scuec.major_adjust_system.tools.ExcelutilMine;

@Service("majorTableService")
public class MajorTableServiceImpl extends BaseServiceImpl<MajorTable> implements MajorTableService {

	@Autowired
	private MajorTableDao majorTableDao;

	@Autowired
	private PwarningTableService pwarningTableService;

	@Autowired
	private WarningTableService warningTableService;
	@Autowired
	private PauseTableService pauseTableService;
	
	@Autowired
	private CancleTableService cancleTableService;

	@Override
	public BaseDao getBaseDao() {
		return majorTableDao;
	}

	@Autowired
	private DetailwarningTableDao detailwarningTableDao;

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
			e.printStackTrace();
		}

	}

	/**
	 * 数据导出
	 */
	@Override
	public void exportExcelInfo(ServletOutputStream outputStream) {
		// 根据条件查询数据，把数据装载到一个list中
		/*Calendar cale = null;
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);*/
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sm=new SimpleDateFormat("yyyyMM");
		String now=sm.format(calendar.getTime());
		Integer year=Integer.parseInt(now);
		
		List<DetailwarningTable> thisYearList=detailwarningTableDao.getWarningMajorByYear(year);
		List<DetailwarningTable> list =detailwarningTableDao.getAll();
		List<PauseTable> pauseList=pauseTableService.getAll();
		List <CancleTable> cancleList=cancleTableService.getAll();
		
		// 调用ExcelUtil的方法
		try {
			ExcelutilMine.createExcelFile( thisYearList,list, pauseList,cancleList,outputStream);
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

	/**
	 * 进行专业分析，选出预预警专业
	 */
	@Override
	public void zhuanYeFenXi(int year) {//默认传过来的是年份+月份，用于区别分析的时间
		// 获取所有作为基数的专业列表
		Calendar cale = Calendar.getInstance();
		int yyear = cale.get(Calendar.YEAR);
		List<MajorTable> majors = selectAll(yyear);//此处的year作为“没有两界及以上毕业生的专业”的条件
		// 确定要取倒数几个专业
		int number = majors.size();
		int count = (int) Math.round(number * 0.05);
		
		// 根据文档第十四条那五个规则，依次放入预预警数据表中，但是没有原因合并和删除重复的
		Change.ThisAdjustment(majors, count, year);
		Change.ThisTransfer(majors, count, year);
		Change.SecondEmploymentRate(majors, count, year);
		Change.CurrentPosrgraduteRate(majors, count, year);
		Change.MajorNumberLess20(majors, year);

		List<PwarningTable> warninglist = pwarningTableService.getWarningMajor();
		// 将预预警专业名单中的专业添加到简易的预警专业名单中
		Change.YuYuJingToYuYjing(warninglist);
		
		// 根据文档第十五条的五条规则，加入简易的预警专业名单中
		// 这个时候在简易的预警专业名单中，有通过预预警专业过来的也有直接通过文档第十五条5条规则挑出来的
		// 所以涉及到重复的问题要进行排掉之后重新添加到详细的预警专业名单中
		Change.ZuiZhongYuJingZhuanYe(majors, year, count);
		// 把简易预警专业中的重复的预警专业集合挑出来
		List<WarningTable> chongfuWarningMajor = warningTableService.getChongFu();
		// 将具有重复的简易预警专业整理后放到详细的预警专业名单中
		Change.YuJingToXiangXiYuJing(chongfuWarningMajor,year);
	}
	

	@Override
	public List<MajorTable> getMajorLess20JinNian(int year) {
		return majorTableDao.getMajorLess20JinNian(year);
	}
	

	@Override
	public List<MajorTable> getMajorLess20QuNian(int year) {
		return majorTableDao.getMajorLess20QuNian(year);
	}

	@Override
	public List<MajorTable> getWuNianWeiZhaoSheng() {
		return majorTableDao.getWuNianWeiZhaoSheng();
	}

	@Override
	public void clearDate() {
		majorTableDao.clearDate();
	}

	@Override
	public int existed(int year) {
		
		return majorTableDao.existed(year);
	}
}
