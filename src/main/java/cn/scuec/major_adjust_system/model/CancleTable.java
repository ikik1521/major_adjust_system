package cn.scuec.major_adjust_system.model;

/**
 * ���롰רҵȡ������רҵ����
 * @author WangChuo
 *
 */
public class CancleTable {

	private Integer cancleYear; //����Ϊȡ�������
	private String majorCode; // רҵ����
	private String majorName; //רҵ����
	private String cancleReason; //����ȡ������������
	
	public Integer getCancleYear() {
		return cancleYear;
	}
	public void setCancleYear(Integer cancleYear) {
		this.cancleYear = cancleYear;
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
	public String getCancleReason() {
		return cancleReason;
	}
	public void setCancleReason(String cancleReason) {
		this.cancleReason = cancleReason;
	}
	
	@Override
	public String toString() {
		return "CancleTable [cancleYear=" + cancleYear + ", majorCode=" + majorCode + ", majorName=" + majorName
				+ ", cancleReason=" + cancleReason + "]";
	}
	
	public CancleTable() {
		super();
	}
	
	public CancleTable(Integer cancleYear, String majorCode, String majorName, String cancleReason) {
		super();
		this.cancleYear = cancleYear;
		this.majorCode = majorCode;
		this.majorName = majorName;
		this.cancleReason = cancleReason;
	}
	
}
