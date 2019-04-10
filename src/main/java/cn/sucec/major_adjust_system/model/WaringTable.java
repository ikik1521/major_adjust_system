package cn.sucec.major_adjust_system.model;

/**
 * 往年预警专业名单
 * @author WangChuo
 *
 */
public class WaringTable {

	private Integer waringYear; //被评为预警的年份
	private String majorCode; // 专业代码
	private String majorName; //专业名称
	private String waringReason; //被评为预警的原因
	
	public Integer getWaringYear() {
		return waringYear;
	}
	public void setWaringYear(Integer waringYear) {
		this.waringYear = waringYear;
	}
	public String getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getWaringReason() {
		return waringReason;
	}
	public void setWaringReason(String waringReason) {
		this.waringReason = waringReason;
	}
	
	@Override
	public String toString() {
		return "WaringTable [waringYear=" + waringYear + ", majorCode=" + majorCode + ", majorName=" + majorName
				+ ", waringReason=" + waringReason + "]";
	}
	
	public WaringTable() {
		super();
	}
	
	public WaringTable(Integer waringYear, String majorCode, String majorName, String waringReason) {
		super();
		this.waringYear = waringYear;
		this.majorCode = majorCode;
		this.majorName = majorName;
		this.waringReason = waringReason;
	}
	
}
