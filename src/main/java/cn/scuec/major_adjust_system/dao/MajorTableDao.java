package cn.scuec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.scuec.major_adjust_system.model.MajorTable;

public interface MajorTableDao extends BaseDao {
	
	/**
	 * ����רҵ�����ѯרҵ����
	 * �Ѳ��ԣ�����ʹ��
	 */
	public String getMajorName(@Param("majorCode") String majorCode);
	
	/**
	 * ����רҵ����(major_code)���������(enrollment_year)
	 * �Ѳ��ԣ�����ʹ��
	 * @param majorCode
	 * @return
	 */
	public int getEyByMajorCode(@Param("majorCode") String majorCode);
	
	/**
	 * �����ļ��е�����Ҫ������дmajor�еĲ��ң���ʽ���£�
	 * רҵ�ܸ���-��ͣ������רҵ-������רҵ-û�����缰���ϱ�ҵ����רҵ=��Ϊ������רҵ����
	 * �Ѳ��ԣ�����ʹ��
	 */
	public List<MajorTable> selectAll(@Param("nowYear") Integer nowYear);
	
	/**
	 * �����������С�תרҵ��רҵ������ѧ������-����ġ���������20�˵�רҵ����������������
	 * �������������켰���ϱ�ҵ����רҵ�ģ����������<20�˵�major����
	 * �� nowYear-5>(���ݿ���е��������enrollment_year)
	 * �Ѳ��ԣ�����ʹ��
	 */
	public List<MajorTable> getMajorLess20(@Param("nowYear") Integer nowYear);

	/**
	 * �������תרҵ��רҵ������ѧ����������20�˵�רҵ���ϣ�����������
	 * @return
	 */
	public List<MajorTable> getMajorLess20JinNian(@Param("nowYear") Integer nowYear);

	/**
	 * ���ȥ��תרҵ��רҵ������ѧ����������20�˵�רҵ���ϣ�����������
	 * @return
	 */
	public List<MajorTable> getMajorLess20QuNian(@Param("nowYear") Integer nowYear);
	
	public List<MajorTable> getWuNianWeiZhaoSheng();
	
	public void clearDate();
	
	//��ѯ�Ƿ��ظ�
	public int existed(int year);
	
	
}
