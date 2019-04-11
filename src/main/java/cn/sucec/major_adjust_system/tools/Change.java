package cn.sucec.major_adjust_system.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sucec.major_adjust_system.model.Major;

/**
 * 此工具类是将一些List集合转变成需要的Map集合
 * @author WangChuo
 *
 */
public  class Change {
	
	
	/**
	 * 此方法是获取majorCode-thisAdjustment
	 * 类型的map集合
	 * 已测试，可以使用
	 * @param majors
	 * @return
	 */
	public Map<String, Double> ThisAdjustment(List<Major> majors) {
		Map<String,Double> hashMap = new HashMap<>();
		for (Major major : majors) {
			hashMap.put(major.getMajorCode(), major.getThisAdjustment());
		}
		return hashMap;
	}
	
	/**
	 * 此方法是获取majorCode-thisTransfer
	 * 类型的map集合
	 * 已测试，可以使用
	 * @param majors
	 * @return
	 */
	public Map<String, Double> ThisTransfer(List<Major> majors) {
		Map<String,Double> hashMap = new HashMap<>();
		for (Major major : majors) {
			hashMap.put(major.getMajorCode(), major.getThisTransfer());
		}
		return hashMap;
	}
	
	
	
	
}
