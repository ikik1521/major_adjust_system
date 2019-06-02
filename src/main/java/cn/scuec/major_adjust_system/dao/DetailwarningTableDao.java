package cn.scuec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.scuec.major_adjust_system.model.DetailwarningTable;

public interface DetailwarningTableDao extends BaseDao {

	public List<DetailwarningTable> getWarningMajorByYear(@Param("year") int year);

	public List<DetailwarningTable> getWarningMajorByMajorCode(@Param("majorCode") String majorCode);
	
	public List<DetailwarningTable> getAll();
	// ���������ۼ���������Ԥ��������רҵ����ѡ����
	public String getcauseMajorCode();
	
	/**
	 * ������ݺ�רҵ����ɾ��ĳһ������
	 * @param year
	 * @param majorCode
	 */
	public void deleteByYearAndMajorCode(@Param("year") int year, @Param("majorCode") String majorCode);
	
	/* public List<DetailwarningTable> getAll(); */
	
}
