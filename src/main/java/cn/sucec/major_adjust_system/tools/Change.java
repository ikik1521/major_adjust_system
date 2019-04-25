package cn.sucec.major_adjust_system.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import cn.sucec.major_adjust_system.dao.PwarningTableDao;
import cn.sucec.major_adjust_system.model.MajorTable;
import cn.sucec.major_adjust_system.model.PwarningTable;
import cn.sucec.major_adjust_system.service.BaseService;
import cn.sucec.major_adjust_system.service.BaseServiceImpl;
import cn.sucec.major_adjust_system.service.PwarningTableService;
import cn.sucec.major_adjust_system.service.PwarningTableServiceImpl;

/**
 * 此工具类是将一些List集合转变成需要的Map集合
 * @author WangChuo
 * 
 */
@ContextConfiguration({ "classpath:spring.xml" })
public class Change {
	
	@Autowired
	static PwarningTableService pwarningTableService = new PwarningTableServiceImpl();
	
	/**
	 * 此方法是根据lastAdjustment进行升序排序，要取前三个
	 * 去年的专业调剂率
	 * 已测试，可以使用
	 * @param majorTables
	 * @return
	 */
	public static List<MajorTable> LastAdjustment(List<MajorTable> majorTables,int count,int nowYear) {
		Collections.sort(majorTables, new Comparator<MajorTable>() {
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
		List<MajorTable> newList = majorTables.subList(0, count);
		List<PwarningTable> pwarningTables = new ArrayList<PwarningTable>();
		int PwarningYear = nowYear;
		String PmajorCode = null;
		String PmajorName = null;
		String PwarningReason = "";
		
		return newList;
	}
	
	/**
	 * 此方法是根据thisAdjustment进行升序排序，要取前三个
	 * 当年的专业调剂率
	 * 已测试，可以使用
	 * @param majorTables
	 * @return
	 */
	@Autowired
	public static void ThisAdjustment(List<MajorTable> majorTables,int count,int nowYear) {
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
		System.out.println(newList);
		List<PwarningTable> pwarningTables = new ArrayList<>();
		int pwarningYear = nowYear;
		String pmajorCode = null;
		String pmajorName = null;
		String pwarningReason = "当年专业调剂率位于所有招生专业的前5%";
		for (MajorTable pwarningMajor : newList) {
			pmajorCode = pwarningMajor.getMajorCode();
			pmajorName = pwarningMajor.getMajorName();
			PwarningTable pwarningMajor2 = new PwarningTable(pwarningYear, pmajorCode, pmajorName, pwarningReason);
			System.out.println("======" + pwarningMajor2);
			System.out.println("-=-=-=-" + pwarningTableService);
			pwarningTableService.addOne(pwarningMajor2);
		}
	}
	
	/**
	 * 此方法是根据lastTransfer进行升序排序，要取前三个
	 * 去年的-申请转出人数/该专业本年级在籍在册在校生人数的比例
	 * 已测试，可以使用
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
	 * 此方法是根据thisTransfer进行升序排序，要取前三个
	 * 今年的-申请转出人数/该专业本年级在籍在册在校生人数的比例
	 * 已测试，可以使用
	 * @param majorTables
	 * @return
	 */
	public static List<MajorTable> ThisTransfer(List<MajorTable> majorTables) {
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
		return majorTables;
	}
	
	
	
	/**
	 * 此方法是根据firstEmploymentRate进行升序排序，要取前三个
	 * 去年的初次就业率
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
	 * 此方法是根据secondEmploymentRate进行升序排序，要取前三个
	 * 当年的初次就业率
	 * 已测试，可以使用
	 * @param majorTables
	 * @return
	 */
	public static List<MajorTable> SecondEmploymentRate(List<MajorTable> majorTables) {
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
		return majorTables;
	}
	
	/**
	 * 此方法是根据currentPosrgraduteRate进行升序排序，要取前三个
	 * 应届毕业生考研率
	 * 已测试，可以使用
	 * @param majorTables
	 * @return
	 */
	public static List<MajorTable> CurrentPosrgraduteRate(List<MajorTable> majorTables) {
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
		return majorTables;
	}
	
}
