package cn.sucec.major_adjust_system.model;

public class WarningTable {
	
	private Integer warningYear;
	private String	majorCode;
	private String	majorName;
	private String	warningReason;
	
	
	public Integer getWarningYear() {
		return warningYear;
	}
	public void setWarningYear(Integer warningYear) {
		this.warningYear = warningYear;
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
	public String getWarningReason() {
		return warningReason;
	}
	public void setWarningReason(String warningReason) {
		this.warningReason = warningReason;
	}
	
	@Override
	public String toString() {
		return "WarningTable [warningYear=" + warningYear + ", majorCode=" + majorCode + ", majorName=" + majorName
				+ ", warningReason=" + warningReason + "]";
	}
	
	public WarningTable() {
		super();
	}
	
	
	public WarningTable(Integer warningYear, String majorCode, String majorName, String warningReason) {
		super();
		this.warningYear = warningYear;
		this.majorCode = majorCode;
		this.majorName = majorName;
		this.warningReason = warningReason;
	}
	
}
