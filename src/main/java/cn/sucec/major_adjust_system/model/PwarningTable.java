package cn.sucec.major_adjust_system.model;

public class PwarningTable {
	
	private Integer PwarningYear;
	private String	PmajorCode;
	private String	PmajorName;
	private String	PwarningReason;
	
	
	public Integer getPwarningYear() {
		return PwarningYear;
	}
	public void setPwarningYear(Integer pwarningYear) {
		PwarningYear = pwarningYear;
	}
	public String getPmajorCode() {
		return PmajorCode;
	}
	public void setPmajorCode(String pmajorCode) {
		PmajorCode = pmajorCode;
	}
	public String getPmajorName() {
		return PmajorName;
	}
	public void setPmajorName(String pmajorName) {
		PmajorName = pmajorName;
	}
	public String getPwarningReason() {
		return PwarningReason;
	}
	public void setPwarningReason(String pwarningReason) {
		PwarningReason = pwarningReason;
	}
	
	@Override
	public String toString() {
		return "PwarningTable [PwarningYear=" + PwarningYear + ", PmajorCode=" + PmajorCode + ", PmajorName="
				+ PmajorName + ", PwarningReason=" + PwarningReason + "]";
	}
	
	public PwarningTable() {
		super();
	}
	
	public PwarningTable(Integer pwarningYear, String pmajorCode, String pmajorName, String pwarningReason) {
		super();
		PwarningYear = pwarningYear;
		PmajorCode = pmajorCode;
		PmajorName = pmajorName;
		PwarningReason = pwarningReason;
	}
	
}
