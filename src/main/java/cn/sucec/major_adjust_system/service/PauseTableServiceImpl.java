package cn.sucec.major_adjust_system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sucec.major_adjust_system.dao.BaseDao;
import cn.sucec.major_adjust_system.dao.PauseTableDao;
import cn.sucec.major_adjust_system.model.DetailwarningTable;
import cn.sucec.major_adjust_system.model.MajorTable;
import cn.sucec.major_adjust_system.model.PauseTable;

@Service("pauseTableService")
public class PauseTableServiceImpl extends BaseServiceImpl<PauseTable> implements PauseTableService {

	@Autowired
	private PauseTableDao pauseTableDao;

	@Autowired
	private DetailwarningTableService detailwarningTableService;

	@Autowired
	private MajorTableService majorTableService;

	@Override
	public BaseDao getBaseDao() {
		return pauseTableDao;
	}

	@Override
	public void fenXiZanTingZhuanYe(int year) {
		// 对已经获得的预警专业名单进行分析，计算出暂停招生专业后存入“暂停招生专业”数据表中
		// 第十七条文档规则有三条，第一条是根据主观判断自己添加的

		// 第二条是连续两年列为预警专业的：思路：把当年和去年的预警专业的专业代码进行分别取出一个list集合，
		List<DetailwarningTable> warningMajorQuNian = detailwarningTableService.getWarningMajorByYear(year - 1);
		List<String> warningMajorCodeQuNian = new ArrayList<>();
		for (DetailwarningTable warningMajor : warningMajorQuNian) {
			warningMajorCodeQuNian.add(warningMajor.getMajorCode());
		}

		List<DetailwarningTable> warningMajorDangNian = detailwarningTableService.getWarningMajorByYear(year);
		List<String> warningMajorCodeDangNian = new ArrayList<>();
		for (DetailwarningTable warningMajor : warningMajorDangNian) {
			warningMajorCodeDangNian.add(warningMajor.getMajorCode());
		}

		// 两个预警代码集合进行取交集
		List<PauseTable> resultList1 = new ArrayList<>();
		for (String item : warningMajorCodeQuNian) {
			if (warningMajorCodeDangNian.contains(item)) {
				// 这里从详细预警专业名单中去除的是重复的集合，但是只要专业代码和名字就可以了
				List<DetailwarningTable> detailwarningMajors = detailwarningTableService
						.getWarningMajorByMajorCode(item);
				DetailwarningTable detailwarningMajor = detailwarningMajors.get(0);
				PauseTable pauseMajor = new PauseTable(year, item, detailwarningMajor.getMajorName(), "，连续两年被列为预警专业");
				resultList1.add(pauseMajor);
			}
		}

		// 将预警集合的交集存入到暂停招生专业名单中
		for (PauseTable pauseTable : resultList1) {
			add(pauseTable);
		}

		// 第三条是连续两年转专业或者分流之后学生人数不足20人的：思路：还是取当年和去年的集合取交集
		List<MajorTable> less20MajorJinNian = majorTableService.getMajorLess20JinNian(year);
		List<String> less20JinNianMajorCode = new ArrayList<>();
		for (MajorTable warningMajor : less20MajorJinNian) {
			less20JinNianMajorCode.add(warningMajor.getMajorCode());
		}
		//System.out.println("今年不足20人的：" + less20JinNianMajorCode);

		List<MajorTable> less20MajorQuNian = majorTableService.getMajorLess20QuNian(year);
		List<String> less20QuNianMajorCode = new ArrayList<>();
		for (MajorTable warningMajor : less20MajorQuNian) {
			less20QuNianMajorCode.add(warningMajor.getMajorCode());
		}
		//System.out.println("去年不足20人的：" + less20QuNianMajorCode);

		// 两个不足20人的代码集合进行取交集
		List<PauseTable> resultList2 = new ArrayList<>();
		for (String item : less20JinNianMajorCode) {
			if (less20QuNianMajorCode.contains(item)) {
				// 这里从详细预警专业名单中去除的是重复的集合，但是只要专业代码和名字就可以了
				MajorTable major = majorTableService.selectOne(item);
				PauseTable pauseMajor = new PauseTable(year, item, major.getMajorName(),
						"，连续两年转专业或专业分流后学生人数不足20人");
				resultList2.add(pauseMajor);
			}
		}
		//System.out.println("暂停专业为：" + resultList2);
		// 将交集的预警集合存入到暂停招生专业名单中
		for (PauseTable pauseTable : resultList2) {
			add(pauseTable);
		}
	}

}
