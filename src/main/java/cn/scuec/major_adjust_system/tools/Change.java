package cn.scuec.major_adjust_system.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.scuec.major_adjust_system.model.DetailwarningTable;
import cn.scuec.major_adjust_system.model.MajorTable;
import cn.scuec.major_adjust_system.model.PwarningTable;
import cn.scuec.major_adjust_system.model.WarningTable;
import cn.scuec.major_adjust_system.service.DetailwarningTableService;
import cn.scuec.major_adjust_system.service.MajorTableService;
import cn.scuec.major_adjust_system.service.PwarningTableService;
import cn.scuec.major_adjust_system.service.WarningTableService;

/**
  * 此工具类是进行需要的排序并且存入数据库中
 */
@Component
public class Change {

	@Autowired
	private PwarningTableService pwarningTableService;

	@Autowired
	private WarningTableService warningTableService;

	@Autowired
	private MajorTableService majorTableService;

	@Autowired
	private DetailwarningTableService detailwarningTableService;

	private static Change change;

	@PostConstruct
	public void init() {
		change = this;
		change.pwarningTableService = this.pwarningTableService;
		change.warningTableService = this.warningTableService;
		change.majorTableService = this.majorTableService;
		change.detailwarningTableService = this.detailwarningTableService;
	}

	/**
	 * 此方法是根据thisAdjustment进行升序排序，要取前三个 当年的专业调剂率 已测试，可以使用
	 * 
	 * @param majorTables
	 * @return
	 */
	@Autowired
	public static void ThisAdjustment(List<MajorTable> majorTables, int count, int nowYear) {
		Collections.sort(majorTables, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getThisAdjustment() < o2.getThisAdjustment()) {
					return 1;
				}
				if (o1.getThisAdjustment() == o2.getThisAdjustment()) {
					return 0;
				}
				return -1;
			}
		});

		List<MajorTable> newList = majorTables.subList(0, count);
		//System.out.println(newList);
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = null;
		for (MajorTable pwarningMajor : newList) {
			pmajorCode = pwarningMajor.getMajorCode();
			pmajorName = pwarningMajor.getMajorName();
			pwarningReason = "#第十四条:(一) 当年专业调剂率位于所有招生专业的前5%。";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * 此方法是根据lastTransfer进行升序排序，要取前三个 去年的-申请转出人数/该专业本年级在籍在册在校生人数的比例 已测试，可以使用
	 * 
	 * @param majorTables
	 * @return
	 */
	public static List<MajorTable> LastTransfer(List<MajorTable> majorTables) {
		Collections.sort(majorTables, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getLastTransfer() < o2.getLastTransfer()) {
					return 1;
				}
				if (o1.getLastTransfer() == o2.getLastTransfer()) {
					return 0;
				}
				return -1;
			}
		});
		return majorTables;
	}

	/**
	 * 此方法是根据thisTransfer进行升序排序，要取前三个 今年的-申请转出人数/该专业本年级在籍在册在校生人数的比例 已测试，可以使用
	 * 
	 * @param majorTables
	 * @return
	 */
	public static void ThisTransfer(List<MajorTable> majorTables, int count, int nowYear) {
		Collections.sort(majorTables, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getThisTransfer() < o2.getThisTransfer()) {
					return 1;
				}
				if (o1.getThisTransfer() == o2.getThisTransfer()) {
					return 0;
				}
				return -1;
			}
		});

		List<MajorTable> newList = majorTables.subList(0, count);
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = null;
		for (MajorTable pwarningMajor : newList) {
			pmajorCode = pwarningMajor.getMajorCode();
			pmajorName = pwarningMajor.getMajorName();
			pwarningReason = "#第十四条:(二) 在当年转专业或专业分流工作结束后，申请转出学生人数与该专业本年级在籍在校生人数的比例位于全校前5%。";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * 此方法是根据firstEmploymentRate进行升序排序，要取前三个 去年的初次就业率
	 * 
	 * @param majorTables
	 * @return
	 */
	public static List<MajorTable> FirstEmploymentRate(List<MajorTable> majorTables) {
//		for (MajorTable majorTable : majorTables) {
//			if (majorTable.getFirstEmploymentRate().equals(null)) {
//				majorTables.remove(majorTable);
//			}
//		}
		Collections.sort(majorTables, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getFirstEmploymentRate() > o2.getFirstEmploymentRate()) {
					return 1;
				}
				if (o1.getFirstEmploymentRate() == o2.getFirstEmploymentRate()) {
					return 0;
				}
				return -1;
			}
		});
		return majorTables;
	}

	/**
	 * 此方法是根据secondEmploymentRate进行升序排序，要取前三个当年的初次就业率 已测试，可以使用
	 * 
	 * @param majorTables
	 * @return
	 */
	public static void SecondEmploymentRate(List<MajorTable> majorTables, int count, int nowYear) {
		Collections.sort(majorTables, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getSecondEmploymentRate() > o2.getSecondEmploymentRate()) {
					return 1;
				}
				if (o1.getSecondEmploymentRate() == o2.getSecondEmploymentRate()) {
					return 0;
				}
				return -1;
			}
		});

		List<MajorTable> newList = majorTables.subList(0, count);
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = null;
		for (MajorTable pwarningMajor : newList) {
			pmajorCode = pwarningMajor.getMajorCode();
			pmajorName = pwarningMajor.getMajorName();
			pwarningReason = "#第十四条:(四) 当年毕业生初次就业率位于所有招生专业的后5%。";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * 此方法是根据currentPosrgraduteRate进行升序排序，要取前三个 应届毕业生考研率 已测试，可以使用
	 * 
	 * @param majorTables
	 * @return
	 */
	public static void CurrentPosrgraduteRate(List<MajorTable> majorTables, int count, int nowYear) {
		Collections.sort(majorTables, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getCurrentPosrgraduteRate() > o2.getCurrentPosrgraduteRate()) {
					return 1;
				}
				if (o1.getCurrentPosrgraduteRate() == o2.getCurrentPosrgraduteRate()) {
					return 0;
				}
				return -1;
			}
		});

		List<MajorTable> newList = majorTables.subList(0, count);
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = null;
		for (MajorTable pwarningMajor : newList) {
			pmajorCode = pwarningMajor.getMajorCode();
			pmajorName = pwarningMajor.getMajorName();
			pwarningReason = "#第十四条:(五) 当年应届毕生生考研率为位于所有招生专业的后5%。";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * 挑出所有“转专业或专业分流后学生人数-今年的”人数不足20人的专业名单，不含艺体生 直接放入预预警专业的数据表中
	 */
	public static void MajorNumberLess20(List<MajorTable> majorTables, int nowYear) {
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = "#第十四条:(三) 当年转专业或专业分流工作结束后学生人数不足20人。";
		for (MajorTable majorTable : majorTables) {
			if (majorTable.getLowStudentNumber() < 20) {
				pmajorCode = majorTable.getMajorCode();
				pmajorName = majorTable.getMajorName();
				PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
				change.pwarningTableService.add(pwarningMajor2);
			}
		}
	}

	/**
	 * 预预警转成预警专业
	 * 
	 * @param warninglist 条数大于等于2的专业的集合
	 */
	public static void YuYuJingToYuYjing(List<PwarningTable> warninglist) {
		// List<PwarningTable> allPwarningTables =
		// change.pwarningTableService.selectAll();
		for (PwarningTable warningTable : warninglist) {
			List<PwarningTable> list = change.pwarningTableService
					.getPwarningTableByMajorCode(warningTable.getPmajorCode());
			int warningYear = warningTable.getPwarningYear();
			String warningMajorCode = warningTable.getPmajorCode();
			String warnMajorName = warningTable.getPmajorName();
			String warnReason = "";
			for (PwarningTable plist : list) {
				warnReason = warnReason + plist.getPwarningReason();
			}
			WarningTable warningMajor = new WarningTable(warningYear, warningMajorCode, warnMajorName, warnReason);
			change.warningTableService.add(warningMajor);
		}
	}

	/**
	 * 此工具是找出连续两年专业调剂率位于当年所有招生专业前5%的专业
	 * 
	 * @param majorTables1
	 * @return
	 */
	public static List<MajorTable> TwoYearsThisAdjustment(List<MajorTable> majorTables1, int count) {

		List<MajorTable> majorTables2 = new ArrayList<>();
		majorTables2.addAll(majorTables1);

		Collections.sort(majorTables1, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getThisAdjustment() < o2.getThisAdjustment()) {
					return 1;
				}
				if (o1.getThisAdjustment() == o2.getThisAdjustment()) {
					return 0;
				}
				return -1;
			}
		});
		List<MajorTable> newList1 = majorTables1.subList(0, count);

		Collections.sort(majorTables2, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getLastAdjustment() < o2.getLastAdjustment()) {
					return 1;
				}
				if (o1.getLastAdjustment() == o2.getLastAdjustment()) {
					return 0;
				}
				return -1;
			}
		});
		List<MajorTable> newList2 = majorTables2.subList(0, count);

		// 取出两个集合中相同的元素，即连续两年专业调剂率位于当年所有招生专业前5%
		List<MajorTable> resultList = new ArrayList<>();
		for (MajorTable item : newList1) {
			if (newList2.contains(item)) {
				resultList.add(item);
			}
		}
		return resultList;
	}

	/**
	 * 此工具是找出连续两年申请转出学生人数与该专业本年级在籍在校人数的比例位于全校前5%的专业
	 * 
	 * @param majorTables1
	 * @return
	 */
	public static List<MajorTable> TwoYearsThisTransfer(List<MajorTable> majorTables1, int count) {

		List<MajorTable> majorTables2 = new ArrayList<>();
		majorTables2.addAll(majorTables1);

		Collections.sort(majorTables1, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getThisTransfer() < o2.getThisTransfer()) {
					return 1;
				}
				if (o1.getThisTransfer() == o2.getThisTransfer()) {
					return 0;
				}
				return -1;
			}
		});
		List<MajorTable> newList1 = majorTables1.subList(0, count);

		Collections.sort(majorTables2, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getLastTransfer() < o2.getLastTransfer()) {
					return 1;
				}
				if (o1.getLastTransfer() == o2.getLastTransfer()) {
					return 0;
				}
				return -1;
			}
		});
		List<MajorTable> newList2 = majorTables2.subList(0, count);

		List<MajorTable> resultList = new ArrayList<>();
		for (MajorTable item : newList1) {
			if (newList2.contains(item)) {
				resultList.add(item);
			}
		}
		return resultList;
	}

	/**
	 * 此工具是找出连续两年初次就业率位于全校后5%的专业
	 * 
	 * @param majorTables1
	 * @return
	 */
	public static List<MajorTable> TwoYearsFirstEmploymentRate(List<MajorTable> majorTables1, int count) {

		List<MajorTable> majorTables2 = new ArrayList<>();
		majorTables2.addAll(majorTables1);

		Collections.sort(majorTables1, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getFirstEmploymentRate() > o2.getFirstEmploymentRate()) {
					return 1;
				}
				if (o1.getFirstEmploymentRate() == o2.getFirstEmploymentRate()) {
					return 0;
				}
				return -1;
			}
		});
		List<MajorTable> newList1 = majorTables1.subList(0, count);

		Collections.sort(majorTables2, new Comparator<MajorTable>() {
			@Override
			public int compare(MajorTable o1, MajorTable o2) {
				if (o1.getSecondEmploymentRate() > o2.getSecondEmploymentRate()) {
					return 1;
				}
				if (o1.getSecondEmploymentRate() == o2.getSecondEmploymentRate()) {
					return 0;
				}
				return -1;
			}
		});
		List<MajorTable> newList2 = majorTables2.subList(0, count);

		List<MajorTable> resultList = new ArrayList<>();
		for (MajorTable item : newList1) {
			if (newList2.contains(item)) {
				resultList.add(item);
			}
		}
		return resultList;
	}

	public static void ZuiZhongYuJingZhuanYe(List<MajorTable> majors, int year, int count) {
		List<MajorTable> majorTables1 = new ArrayList<>();
		List<MajorTable> majorTables2 = new ArrayList<>();
		List<MajorTable> majorTables3 = new ArrayList<>();
		majorTables1.addAll(majors);
		majorTables2.addAll(majors);
		majorTables3.addAll(majors);
		List<MajorTable> twoYearThisAdjustment = Change.TwoYearsThisAdjustment(majorTables1, count);
		List<MajorTable> twoYearsThisTransfer = Change.TwoYearsThisTransfer(majorTables2, count);
		List<MajorTable> twoYearsFirstEmploymentRate = Change.TwoYearsFirstEmploymentRate(majorTables3, count);

		int warningYear = year;
		// String warningReason = null;

		for (MajorTable majorTable : majors) {
			String warningReason = "";
			if (majorTable.getProvinceWarning().equals("是")) {
				warningReason = warningReason + "#第十五条:(一) 在教育部或湖北省教育厅组织的评估中被预警的。";
			}
			if (majorTable.getSchoolWarning().equals("是")) {
				warningReason = warningReason + "#第十五条:(二)在学校组织的专业评估中被预警的。";
			}
			if (twoYearThisAdjustment.contains(majorTable)) {
				warningReason = warningReason + "#第十五条:(三)连续两年专业调剂率位于当年所有招生专业前5%。";
			}
			if (twoYearsThisTransfer.contains(majorTable)) {
				warningReason = warningReason + "#第十五条:(四)连续两年申请转出学生人数与该专业本年级在籍在校人数的比例位于全校前5%。";
			}
			if (twoYearsFirstEmploymentRate.contains(majorTable)) {
				warningReason = warningReason + "#第十五条:(五)连续两年初次就业率位于全校后5%。";
			}
			// System.out.println("=====warningReason:" + warningReason);
			WarningTable warningMajor = new WarningTable(warningYear, majorTable.getMajorCode(),
					majorTable.getMajorName(), warningReason);
			// System.out.println("--------warningMajor:" + warningMajor);
			// 先把所有的专业加一遍，再把原因是空的删掉，就只剩经过文档15中5条规则筛选出来的预警专业集合
			change.warningTableService.add(warningMajor);
			// 把原因是空值的专业从简易预警专业名单中删掉
			change.warningTableService.deleteWarningReasonNull();
		}
	}

	public static void YuJingToXiangXiYuJing(List<WarningTable> chongfuWarningMajor,int year) {
		for (WarningTable warningTable : chongfuWarningMajor) {
			List<WarningTable> list = change.warningTableService
					.getWarningTableByMajorCode(warningTable.getMajorCode());
			String warnReason = "";
			String warningMajorCode = warningTable.getMajorCode();
			for (WarningTable plist : list) {
				warnReason = warnReason + plist.getWarningReason();
			}
			// 以上，已经将重复的预警专业的原因加上了，现在要把这些整理好原因的专业放到详细预警专业的数据表中去
			MajorTable major = change.majorTableService.selectOne(warningMajorCode);
			//System.out.println(major);
			DetailwarningTable detailwarningMajor = new DetailwarningTable(year,major.getMajorCode(),
					major.getMajorName(), major.getEnrollmentYear(), major.getContinuousEnrollment(), major.getYears(),
					major.getArt(), major.getProvinceWarning(), major.getSchoolWarning(), major.getLastAdjustment(),
					major.getThisAdjustment(), major.getLastTransfer(), major.getMiddleTransfer(), major.getThisTransfer(),
					major.getHighStudentNumber(), major.getLowStudentNumber(), major.getFirstEmploymentRate(), major.getSecondEmploymentRate(),
					major.getCurrentPosrgraduteRate(), warnReason);
			change.detailwarningTableService.add(detailwarningMajor);
			// 放完之后再把这些重复的专业删掉，把没有重复的预警专业放到详细预警专业中去
			// 调用service的方法从数据库中删除已经添加到详细预警专业名单中的专业
			change.warningTableService.delete(warningMajorCode);
			
		}
		//获取删除后的简易的预警list，然后添加到详细的预警专业名单中，至此，预警专业的分析算是完成
		List<WarningTable> warningTables = change.warningTableService.selectAll();
		for (WarningTable warningTable : warningTables) {
			MajorTable major = change.majorTableService.selectOne(warningTable.getMajorCode());
			DetailwarningTable detailwarningMajor = new DetailwarningTable(year,major.getMajorCode(),
					major.getMajorName(), major.getEnrollmentYear(), major.getContinuousEnrollment(), major.getYears(),
					major.getArt(), major.getProvinceWarning(), major.getSchoolWarning(), major.getLastAdjustment(),
					major.getThisAdjustment(), major.getLastTransfer(), major.getMiddleTransfer(), major.getThisTransfer(),
					major.getHighStudentNumber(), major.getLowStudentNumber(), major.getFirstEmploymentRate(), major.getSecondEmploymentRate(),
					major.getCurrentPosrgraduteRate(), warningTable.getWarningReason());
			change.detailwarningTableService.add(detailwarningMajor);
		}
	}

}
