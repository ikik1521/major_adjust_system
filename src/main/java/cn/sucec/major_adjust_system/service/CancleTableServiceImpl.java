package cn.sucec.major_adjust_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sucec.major_adjust_system.dao.BaseDao;
import cn.sucec.major_adjust_system.dao.CancleTableDao;
import cn.sucec.major_adjust_system.model.CancleTable;
import cn.sucec.major_adjust_system.model.MajorTable;

@Service("cancleTableService")
public class CancleTableServiceImpl extends BaseServiceImpl<CancleTable> implements CancleTableService {

	@Autowired
	private CancleTableDao cancleTableDao;

	@Autowired
	private DetailwarningTableService detailwarningTableService;

	@Autowired
	private MajorTableService majorTableService;

	@Override
	public BaseDao getBaseDao() {
		return cancleTableDao;
	}

	@Override
	public void fenXiCheXiaoZhuanYe(int year) {
		// ����ϸԤ��רҵ���majorTable�������ݷ�����ѡ��Ҫ���г���������רҵ
		// �ĵ���ʮ��������������һ�����������ۼ���������Ԥ�������ģ��ڶ�������������δ������רҵ

		// 1.�������ۼ���������Ԥ�������� ˼·��ͨ��detailwarningTableService��ķ����������ϸԤ��
		// ������ﵽ���ε�רҵ��רҵ����
		String cancleMajorCode = detailwarningTableService.getcauseMajorCode();
		if (cancleMajorCode != null) {
			// System.out.println(cancleMajorCode);
			String cancleMajorName = majorTableService.getMajorName(cancleMajorCode);
			// System.out.println(cancleMajorName);
			String cancleReason1 = "#�������ۼ���������Ԥ������";
			CancleTable cancleMajor = new CancleTable(year, cancleMajorCode, cancleMajorName, cancleReason1);
			add(cancleMajor);
		} else {
			return;
		}

		// 2.��������δ������רҵ ˼·��ֱ�Ӳ�
		List<MajorTable> cancleMajors = majorTableService.getWuNianWeiZhaoSheng();
		// System.out.println(cancleMajors);
		if (cancleMajors != null) {
			String cancleReason2 = "#��������δ������רҵ";
			for (MajorTable cancleMajor1 : cancleMajors) {
				CancleTable cancleTable2 = new CancleTable(year, cancleMajor1.getMajorCode(),
						cancleMajor1.getMajorName(), cancleReason2);
				add(cancleTable2);
			}
		}else {
			return;
		}

	}

	@Override
	public void clearDate() {
		cancleTableDao.clearDate();
	}

	@Override
	public void deleteByYearAndMajorCode(int year, String majorCode) {
		cancleTableDao.deleteByYearAndMajorCode(year, majorCode);
	}

	@Override
	public List<CancleTable> getAll() {
		List <CancleTable> list=cancleTableDao.getAll();
		return list;
	}

}
