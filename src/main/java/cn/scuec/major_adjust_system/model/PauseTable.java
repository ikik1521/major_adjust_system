package cn.scuec.major_adjust_system.model;

/**
 * 经过本次上传文件后被列为“专业暂停招生”的名单
 * @author WangChuo
 *
 */
public class PauseTable {

	private Integer pauseYear; //被评为暂停招生的年份
	private String majorCode; // 专业代码
	private String majorName; //专业名称
	private String pauseReason; //被暂停招生的原因
	
	public Integer getPauseYear() {
		return pauseYear;
	}
	public void setPauseYear(Integer pauseYear) {
		this.pauseYear = pauseYear;
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
	public String getPauseReason() {
		return pauseReason;
	}
	public void setPauseReason(String pauseReason) {
		this.pauseReason = pauseReason;
	}
	
	@Override
	public String toString() {
		return "PauseTable [pauseYear=" + pauseYear + ", majorCode=" + majorCode + ", majorName=" + majorName
				+ ", pauseReason=" + pauseReason + "]";
	}
	
	public PauseTable() {
		super();
	}
	
	public PauseTable(Integer pauseYear, String majorCode, String majorName, String pauseReason) {
		super();
		this.pauseYear = pauseYear;
		this.majorCode = majorCode;
		this.majorName = majorName;
		this.pauseReason = pauseReason;
	}
	
}
