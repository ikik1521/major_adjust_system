package cn.sucec.major_adjust_system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.Major;

/**
 * 因为这个项目不需要写Basedao,所以在一些参数上就不用写通用的了，可以直接在mapper.xml的sql语句中确定参数
 * @author WangChuo
 *
 */
public interface MajorDao {
	
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
	 * 获取所有专业的当年调剂率(this_adjustment)，不含艺体生
	 * 返回:	key-string:majorCode 专业代码
	 * 		value-Double:thisAdjustment 对应专业的当年调剂率
	 * @return
	 */
	@MapKey("majorCode")
	public Map<String, Double> getAllThisAdjustment();
	
	/**
	 * 获取所有专业的申请转出人数/该专业本年级在籍在册在校生人数的比例(this_transfer),不含艺体生
	 * 返回:	key-string:majorCode 专业代码
	 * 		value-Double:thisTransfer 对应专业的当年申请转出人数/该专业本年级在籍在册在校生人数的比例
	 * @return
	 */
	public Map<String, Double> getAllThistransfer();
	
	/**
	 * 挑出所有“转专业或专业分流后学生人数-今年的”人数不足20人的专业名单，不含艺体生
	 */
	public List<Major> getMajorLess20NoArt();
	
	/**
	 * 挑出所有“转专业或专业分流后学生人数-今年的”人数不足15人的艺体专业名单
	 */
	public List<Major> getMajorLess15Art();
	
	/**
	 * 获取所有"当年毕业生的初次就业率"，排除新设专业没有就业率，不含艺体生
	 * 返回:	key-string:majorCode 专业代码
	 * 		value-Double:firstEmploymentRate 对应专业的当年毕业生的初次就业率
	 * @return
	 */
	public Map<String, Double> getAllSecondEmployment();
	
	/**
	 * 获取所有当年应届毕业生的考研率
	 * 返回:	key-string:majorCode 专业代码
	 * 		value-Double:currentPosrgraduteRate 对应专业的应届毕业生考研率
	 */
	public Map<String, Double> getAllCurrentPosrgraduteRate();
	
	
	
}
