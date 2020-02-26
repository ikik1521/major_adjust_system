package cn.scuec.major_adjust_system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.scuec.major_adjust_system.dao.BaseDao;
import cn.scuec.major_adjust_system.dao.PauseTableDao;
import cn.scuec.major_adjust_system.model.DetailwarningTable;
import cn.scuec.major_adjust_system.model.MajorTable;
import cn.scuec.major_adjust_system.model.PauseTable;

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
	public void fenXiZanTingZhuanYe(int year) {//���������ǵ�ǰ���:2019
		// ���Ѿ���õ�Ԥ��רҵ�������з������������ͣ����רҵ����롰��ͣ����רҵ�����ݱ���
		// ��ʮ�����ĵ���������������һ��Ϊ�˹�����

		// �ڶ���������������ΪԤ��רҵ�ģ�˼·���ѵ����ȥ���Ԥ��רҵ��רҵ������зֱ�ȡ��һ��list���ϣ�
		List<DetailwarningTable> warningMajorQuNian = detailwarningTableService.getWarningMajorByYear(year-1);
		List<String> warningMajorCodeQuNian = new ArrayList<>();
		for (DetailwarningTable warningMajor : warningMajorQuNian) {
			warningMajorCodeQuNian.add(warningMajor.getMajorCode());
		}

		List<DetailwarningTable> warningMajorDangNian = detailwarningTableService.getWarningMajorByYear(year);
		List<String> warningMajorCodeDangNian = new ArrayList<>();
		for (DetailwarningTable warningMajor : warningMajorDangNian) {
			warningMajorCodeDangNian.add(warningMajor.getMajorCode());
		}

		// ����Ԥ�����뼯�Ͻ���ȡ����
		List<PauseTable> resultList1 = new ArrayList<>();
		for (String item : warningMajorCodeQuNian) {
			if (warningMajorCodeDangNian.contains(item)) {
				// �������ϸԤ��רҵ������ȥ�������ظ��ļ��ϣ�����ֻҪרҵ��������־Ϳ�����
				List<DetailwarningTable> detailwarningMajors = detailwarningTableService
						.getWarningMajorByMajorCode(item);
				DetailwarningTable detailwarningMajor = detailwarningMajors.get(0);
				PauseTable pauseMajor = new PauseTable(year, item, detailwarningMajor.getMajorName(), "���������걻��ΪԤ��רҵ");
				resultList1.add(pauseMajor);
			}
		}

		// ��Ԥ�����ϵĽ������뵽��ͣ����רҵ������
		for (PauseTable pauseTable : resultList1) {
			add(pauseTable);
		}

		// ����������������תרҵ���߷���֮��ѧ����������20�˵ģ�˼·������ȡ�����ȥ��ļ���ȡ����
		List<MajorTable> less20MajorJinNian = majorTableService.getMajorLess20JinNian(year);
		List<String> less20JinNianMajorCode = new ArrayList<>();
		for (MajorTable warningMajor : less20MajorJinNian) {
			less20JinNianMajorCode.add(warningMajor.getMajorCode());
		}
		//System.out.println("���겻��20�˵ģ�" + less20JinNianMajorCode);

		List<MajorTable> less20MajorQuNian = majorTableService.getMajorLess20QuNian(year);
		List<String> less20QuNianMajorCode = new ArrayList<>();
		for (MajorTable warningMajor : less20MajorQuNian) {
			less20QuNianMajorCode.add(warningMajor.getMajorCode());
		}
		//System.out.println("ȥ�겻��20�˵ģ�" + less20QuNianMajorCode);

		// ��������20�˵Ĵ��뼯�Ͻ���ȡ����
		List<PauseTable> resultList2 = new ArrayList<>();
		for (String item : less20JinNianMajorCode) {
			if (less20QuNianMajorCode.contains(item)) {
				// �������ϸԤ��רҵ������ȥ�������ظ��ļ��ϣ�����ֻҪרҵ��������־Ϳ�����
				MajorTable major = majorTableService.selectOne(item);
				PauseTable pauseMajor = new PauseTable(year, item, major.getMajorName(),
						"#��������תרҵ��רҵ������ѧ����������20��");
				resultList2.add(pauseMajor);
			}
		}
		//System.out.println("��ͣרҵΪ��" + resultList2);
		// ��������Ԥ�����ϴ��뵽��ͣ����רҵ������
		for (PauseTable pauseTable : resultList2) {
			add(pauseTable);
		}
	}

	@Override
	public void clearDate() {
		pauseTableDao.clearDate();
	}

	@Override
	public void deleteByYearAndMajorCode(int year, String majorCode) {
		pauseTableDao.deleteByYearAndMajorCode(year, majorCode);
	}

	@Override
	public List<PauseTable> getAll() {
		
		 List<PauseTable> list =pauseTableDao.getAll();
		 return list;
	}

}
