package cn.scuec.major_adjust_system.model;

/**
 * רҵ����¼��רҵ����רҵ�������Ϣ
 * @author WangChuo
 *
 */ 
public class MajorTable {
	
	private Integer id; //���
	private String majorCode; // רҵ����
	private String majorName; //רҵ����
	private Integer enrollmentYear; //�������
	private String continuousEnrollment; //�Ƿ�����5��δ����
	private Integer years; //����
	private String art; //�Ƿ�������רҵ
	private String provinceWarning; //�Ƿ���ʡ���������б�Ԥ��
	private String schoolWarning; //�Ƿ���У��רҵ�����б�Ԥ��
	private Double lastAdjustment; //רҵ������-ȥ���
	private Double thisAdjustment; //רҵ������-�����
	private Double lastTransfer; //����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���
	private Double middleTransfer; //����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���
	private Double thisTransfer; //����ת������/��רҵ���꼶�ڼ��ڲ���У�������ı���
	private Integer highStudentNumber; //תרҵ��רҵ������ѧ������-ȥ���
	private Integer lowStudentNumber; //תרҵ��רҵ������ѧ������-�����
	private Double firstEmploymentRate; //���ξ�ҵ��-ȥ���
	private Double secondEmploymentRate; //���ξ�ҵ��-�����
	private Double currentPosrgraduteRate; //Ӧ���ҵ��������
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getEnrollmentYear() {
		return enrollmentYear;
	}
	public void setEnrollmentYear(Integer enrollmentYear) {
		this.enrollmentYear = enrollmentYear;
	}
	public String getContinuousEnrollment() {
		return continuousEnrollment;
	}
	public void setContinuousEnrollment(String continuousEnrollment) {
		this.continuousEnrollment = continuousEnrollment;
	}
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
	public String getArt() {
		return art;
	}
	public void setArt(String art) {
		this.art = art;
	}
	public String getProvinceWarning() {
		return provinceWarning;
	}
	public void setProvinceWarning(String provinceWarning) {
		this.provinceWarning = provinceWarning;
	}
	public String getSchoolWarning() {
		return schoolWarning;
	}
	public void setSchoolWarning(String schoolWarning) {
		this.schoolWarning = schoolWarning;
	}
	public Double getLastAdjustment() {
		return lastAdjustment;
	}
	public void setLastAdjustment(Double lastAdjustment) {
		this.lastAdjustment = lastAdjustment;
	}
	public Double getThisAdjustment() {
		return thisAdjustment;
	}
	public void setThisAdjustment(Double thisAdjustment) {
		this.thisAdjustment = thisAdjustment;
	}
	public Double getLastTransfer() {
		return lastTransfer;
	}
	public void setLastTransfer(Double lastTransfer) {
		this.lastTransfer = lastTransfer;
	}
	public Double getMiddleTransfer() {
		return middleTransfer;
	}
	public void setMiddleTransfer(Double middleTransfer) {
		this.middleTransfer = middleTransfer;
	}
	public Double getThisTransfer() {
		return thisTransfer;
	}
	public void setThisTransfer(Double thisTransfer) {
		this.thisTransfer = thisTransfer;
	}
	public Integer getHighStudentNumber() {
		return highStudentNumber;
	}
	public void setHighStudentNumber(Integer highStudentNumber) {
		this.highStudentNumber = highStudentNumber;
	}
	public Integer getLowStudentNumber() {
		return lowStudentNumber;
	}
	public void setLowStudentNumber(Integer lowStudentNumber) {
		this.lowStudentNumber = lowStudentNumber;
	}
	public Double getFirstEmploymentRate() {
		return firstEmploymentRate;
	}
	public void setFirstEmploymentRate(Double firstEmploymentRate) {
		this.firstEmploymentRate = firstEmploymentRate;
	}
	public Double getSecondEmploymentRate() {
		return secondEmploymentRate;
	}
	public void setSecondEmploymentRate(Double secondEmploymentRate) {
		this.secondEmploymentRate = secondEmploymentRate;
	}
	public Double getCurrentPosrgraduteRate() {
		return currentPosrgraduteRate;
	}
	public void setCurrentPosrgraduteRate(Double currentPosrgraduteRate) {
		this.currentPosrgraduteRate = currentPosrgraduteRate;
	}
	
	@Override
	public String toString() {
		return "MajorTable [id=" + id + ", majorCode=" + majorCode + ", majorName=" + majorName
				+ ", enrollmentYear=" + enrollmentYear + ", continuousEnrollment=" + continuousEnrollment + ", years="
				+ years + ", art=" + art + ", provinceWarning=" + provinceWarning + ", schoolWarning=" + schoolWarning
				+ ", lastAdjustment=" + lastAdjustment + ", thisAdjustment=" + thisAdjustment + ", lastTransfer="
				+ lastTransfer + ", middleTransfer=" + middleTransfer + ", thisTransfer=" + thisTransfer
				+ ", highStudentNumber=" + highStudentNumber + ", lowStudentNumber=" + lowStudentNumber
				+ ", firstEmploymentRate=" + firstEmploymentRate + ", secondEmploymentRate=" + secondEmploymentRate
				+ ", currentPosrgraduteRate=" + currentPosrgraduteRate + "]";
	}
	
	public MajorTable() {
		super();
	}
	
	public MajorTable(Integer id, String majorCode, String majorName, Integer enrollmentYear,
			String continuousEnrollment, Integer years, String art, String provinceWarning, String schoolWarning,
			Double lastAdjustment, Double thisAdjustment, Double lastTransfer, Double middleTransfer,
			Double thisTransfer, Integer highStudentNumber, Integer lowStudentNumber, Double firstEmploymentRate,
			Double secondEmploymentRate, Double currentPosrgraduteRate) {
		super();
		this.id = id;
		this.majorCode = majorCode;
		this.majorName = majorName;
		this.enrollmentYear = enrollmentYear;
		this.continuousEnrollment = continuousEnrollment;
		this.years = years;
		this.art = art;
		this.provinceWarning = provinceWarning;
		this.schoolWarning = schoolWarning;
		this.lastAdjustment = lastAdjustment;
		this.thisAdjustment = thisAdjustment;
		this.lastTransfer = lastTransfer;
		this.middleTransfer = middleTransfer;
		this.thisTransfer = thisTransfer;
		this.highStudentNumber = highStudentNumber;
		this.lowStudentNumber = lowStudentNumber;
		this.firstEmploymentRate = firstEmploymentRate;
		this.secondEmploymentRate = secondEmploymentRate;
		this.currentPosrgraduteRate = currentPosrgraduteRate;
	}
	
	
}
