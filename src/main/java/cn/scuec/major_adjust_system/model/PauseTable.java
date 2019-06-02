package cn.scuec.major_adjust_system.model;

/**
 * ���������ϴ��ļ�����Ϊ��רҵ��ͣ������������
 * @author WangChuo
 *
 */
public class PauseTable {

	private Integer pauseYear; //����Ϊ��ͣ���������
	private String majorCode; // רҵ����
	private String majorName; //רҵ����
	private String pauseReason; //����ͣ������ԭ��
	
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
