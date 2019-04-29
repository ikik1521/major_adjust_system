package cn.sucec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.MajorTable;

public interface MajorTableDao extends BaseDao {
	
	/**
	 * 根据专业代码查询专业名称
	 * 已测试，可以使用
	 */
	public String getMajorName(@Param("majorCode") String majorCode);
	
	/**
	 * 根据专业代码(major_code)查招生年份(enrollment_year)
	 * 已测试，可以使用
	 * @param majorCode
	 * @return
	 */
	public int getEyByMajorCode(@Param("majorCode") String majorCode);
	
	/**
	 * 根据文件中的特殊要求来重写major中的查找，公式如下：
	 * 专业总个数-暂停招生的专业-艺体类专业-没有两界及以上毕业生的专业=作为基数的专业个数
	 * 已测试，可以使用
	 */
	public List<MajorTable> selectAll(@Param("nowYear") Integer nowYear);
	
	/**
	 * 挑出当年所有“转专业或专业分流后学生人数-今年的”人数不足20人的专业名单，不含艺体生
	 * 而且是已有两届及以上毕业生的专业的，当年的人数<20人的major集合
	 * 即 nowYear-5>(数据库的中的招生年份enrollment_year)
	 * 已测试，可以使用
	 */
	public List<MajorTable> getMajorLess20(@Param("nowYear") Integer nowYear);

	/**
	 * 查出当年转专业或专业分流后学生人数不足20人的专业集合，不含艺体生
	 * @return
	 */
	public List<MajorTable> getMajorLess20JinNian(@Param("nowYear") Integer nowYear);

	/**
	 * 查出去年转专业或专业分流后学生人数不足20人的专业集合，不含艺体生
	 * @return
	 */
	public List<MajorTable> getMajorLess20QuNian(@Param("nowYear") Integer nowYear);
	
	
}
