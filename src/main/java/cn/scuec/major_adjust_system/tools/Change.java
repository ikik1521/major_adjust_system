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
  * �˹������ǽ�����Ҫ�������Ҵ������ݿ���
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
	 * �˷����Ǹ���thisAdjustment������������Ҫȡǰ���� �����רҵ������ �Ѳ��ԣ�����ʹ��
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
			pwarningReason = "#��ʮ����:(һ) ����רҵ������λ����������רҵ��ǰ5%��";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * �˷����Ǹ���lastTransfer������������Ҫȡǰ���� ȥ���-����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı��� �Ѳ��ԣ�����ʹ��
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
	 * �˷����Ǹ���thisTransfer������������Ҫȡǰ���� �����-����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı��� �Ѳ��ԣ�����ʹ��
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
			pwarningReason = "#��ʮ����:(��) �ڵ���תרҵ��רҵ������������������ת��ѧ���������רҵ���꼶�ڼ���У�������ı���λ��ȫУǰ5%��";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * �˷����Ǹ���firstEmploymentRate������������Ҫȡǰ���� ȥ��ĳ��ξ�ҵ��
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
	 * �˷����Ǹ���secondEmploymentRate������������Ҫȡǰ��������ĳ��ξ�ҵ�� �Ѳ��ԣ�����ʹ��
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
			pwarningReason = "#��ʮ����:(��) �����ҵ�����ξ�ҵ��λ����������רҵ�ĺ�5%��";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * �˷����Ǹ���currentPosrgraduteRate������������Ҫȡǰ���� Ӧ���ҵ�������� �Ѳ��ԣ�����ʹ��
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
			pwarningReason = "#��ʮ����:(��) ����Ӧ�������������Ϊλ����������רҵ�ĺ�5%��";
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			change.pwarningTableService.add(pwarningMajor2);
		}
	}

	/**
	 * �������С�תרҵ��רҵ������ѧ������-����ġ���������20�˵�רҵ���������������� ֱ�ӷ���ԤԤ��רҵ�����ݱ���
	 */
	public static void MajorNumberLess20(List<MajorTable> majorTables, int nowYear) {
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = "#��ʮ����:(��) ����תרҵ��רҵ��������������ѧ����������20�ˡ�";
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
	 * ԤԤ��ת��Ԥ��רҵ
	 * 
	 * @param warninglist �������ڵ���2��רҵ�ļ���
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
	 * �˹������ҳ���������רҵ������λ�ڵ�����������רҵǰ5%��רҵ
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

		// ȡ��������������ͬ��Ԫ�أ�����������רҵ������λ�ڵ�����������רҵǰ5%
		List<MajorTable> resultList = new ArrayList<>();
		for (MajorTable item : newList1) {
			if (newList2.contains(item)) {
				resultList.add(item);
			}
		}
		return resultList;
	}

	/**
	 * �˹������ҳ�������������ת��ѧ���������רҵ���꼶�ڼ���У�����ı���λ��ȫУǰ5%��רҵ
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
	 * �˹������ҳ�����������ξ�ҵ��λ��ȫУ��5%��רҵ
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
			if (majorTable.getProvinceWarning().equals("��")) {
				warningReason = warningReason + "#��ʮ����:(һ) �ڽ����������ʡ��������֯�������б�Ԥ���ġ�";
			}
			if (majorTable.getSchoolWarning().equals("��")) {
				warningReason = warningReason + "#��ʮ����:(��)��ѧУ��֯��רҵ�����б�Ԥ���ġ�";
			}
			if (twoYearThisAdjustment.contains(majorTable)) {
				warningReason = warningReason + "#��ʮ����:(��)��������רҵ������λ�ڵ�����������רҵǰ5%��";
			}
			if (twoYearsThisTransfer.contains(majorTable)) {
				warningReason = warningReason + "#��ʮ����:(��)������������ת��ѧ���������רҵ���꼶�ڼ���У�����ı���λ��ȫУǰ5%��";
			}
			if (twoYearsFirstEmploymentRate.contains(majorTable)) {
				warningReason = warningReason + "#��ʮ����:(��)����������ξ�ҵ��λ��ȫУ��5%��";
			}
			// System.out.println("=====warningReason:" + warningReason);
			WarningTable warningMajor = new WarningTable(warningYear, majorTable.getMajorCode(),
					majorTable.getMajorName(), warningReason);
			// System.out.println("--------warningMajor:" + warningMajor);
			// �Ȱ����е�רҵ��һ�飬�ٰ�ԭ���ǿյ�ɾ������ֻʣ�����ĵ�15��5������ɸѡ������Ԥ��רҵ����
			change.warningTableService.add(warningMajor);
			// ��ԭ���ǿ�ֵ��רҵ�Ӽ���Ԥ��רҵ������ɾ��
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
			// ���ϣ��Ѿ����ظ���Ԥ��רҵ��ԭ������ˣ�����Ҫ����Щ�����ԭ���רҵ�ŵ���ϸԤ��רҵ�����ݱ���ȥ
			MajorTable major = change.majorTableService.selectOne(warningMajorCode);
			//System.out.println(major);
			DetailwarningTable detailwarningMajor = new DetailwarningTable(year,major.getMajorCode(),
					major.getMajorName(), major.getEnrollmentYear(), major.getContinuousEnrollment(), major.getYears(),
					major.getArt(), major.getProvinceWarning(), major.getSchoolWarning(), major.getLastAdjustment(),
					major.getThisAdjustment(), major.getLastTransfer(), major.getMiddleTransfer(), major.getThisTransfer(),
					major.getHighStudentNumber(), major.getLowStudentNumber(), major.getFirstEmploymentRate(), major.getSecondEmploymentRate(),
					major.getCurrentPosrgraduteRate(), warnReason);
			change.detailwarningTableService.add(detailwarningMajor);
			// ����֮���ٰ���Щ�ظ���רҵɾ������û���ظ���Ԥ��רҵ�ŵ���ϸԤ��רҵ��ȥ
			// ����service�ķ��������ݿ���ɾ���Ѿ���ӵ���ϸԤ��רҵ�����е�רҵ
			change.warningTableService.delete(warningMajorCode);
			
		}
		//��ȡɾ����ļ��׵�Ԥ��list��Ȼ����ӵ���ϸ��Ԥ��רҵ�����У����ˣ�Ԥ��רҵ�ķ����������
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
