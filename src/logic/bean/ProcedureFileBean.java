package logic.bean;
import java.util.Date;

/**
 * 運用手順書に関する情報のBean
 * @author s13rp
 *
 */
public class ProcedureFileBean {

	/**
	 * 貸出案件名
	 */
	public String projectName;

	/**
	 * 依頼表シーケンスナンバー
	 */
	public double sequenceNumber;

	/**
	 * マニュアル種類
	 */
	public String manualCategory;

	/**
	 * 手順書名
	 */
	public String manualFileName;

	/**
	 * 対象ページ
	 */
	public double targetPage;

	/**
	 * 区分
	 */
	public String operationCategory;

	/**
	 * 貸出時申請者
	 */
	public String applicantForRental;

	/**
	 * 返却時申請者
	 */
	public String applicantForReturn;

	/**
	 * 貸出時連絡先
	 */
	public String telForRental;

	/**
	 * 返却時連絡先
	 */
	public String telForReturn;

	/**
	 * 貸出依頼日
	 */
	public Date rentalRequestDate;

	/**
	 * 返却予定日
	 */
	public Date returnPlanDate;

	/**
	 * 返却日
	 */
	public Date returnDate;

	/**
	 * リリース予定日
	 */
	public Date releasePlunDate;

	/**
	 * リリース日
	 */
	public Date releaseDate;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public double getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(double sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getManualCategory() {
		return manualCategory;
	}

	public void setManualCategory(String manualCategory) {
		this.manualCategory = manualCategory;
	}

	public String getManualFileName() {
		return manualFileName;
	}

	public void setManualFileName(String manualFileName) {
		this.manualFileName = manualFileName;
	}

	public double getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(double targetPage) {
		this.targetPage = targetPage;
	}

	public String getOperationCategory() {
		return operationCategory;
	}

	public void setOperationCategory(String operationCategory) {
		this.operationCategory = operationCategory;
	}

	public String getApplicantForRental() {
		return applicantForRental;
	}

	public void setApplicantForRental(String applicantForRental) {
		this.applicantForRental = applicantForRental;
	}

	public String getApplicantForReturn() {
		return applicantForReturn;
	}

	public void setApplicantForReturn(String applicantForReturn) {
		this.applicantForReturn = applicantForReturn;
	}

	public String getTelForRental() {
		return telForRental;
	}

	public void setTelForRental(String telForRental) {
		this.telForRental = telForRental;
	}

	public String getTelForReturn() {
		return telForReturn;
	}

	public void setTelForReturn(String telForReturn) {
		this.telForReturn = telForReturn;
	}

	public Date getRentalRequestDate() {
		return rentalRequestDate;
	}

	public void setRentalRequestDate(Date rentalRequestDate) {
		this.rentalRequestDate = rentalRequestDate;
	}

	public Date getReturnPlanDate() {
		return returnPlanDate;
	}

	public void setReturnPlanDate(Date returnPlanDate) {
		this.returnPlanDate = returnPlanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getReleasePlunDate() {
		return releasePlunDate;
	}

	public void setReleasePlunDate(Date releasePlunDate) {
		this.releasePlunDate = releasePlunDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "ManagementTable [projectName=" + projectName + ", sequenceNumber=" + sequenceNumber
				+ ", manualCategory=" + manualCategory + ", manualFileName=" + manualFileName + ", targetPage="
				+ targetPage + ", operationCategory=" + operationCategory + ", applicantForRental=" + applicantForRental
				+ ", applicantForReturn=" + applicantForReturn + ", telForRental=" + telForRental + ", telForReturn="
				+ telForReturn + ", rentalRequestDate=" + rentalRequestDate + ", returnPlanDate=" + returnPlanDate
				+ ", returnDate=" + returnDate + ", releasePlunDate=" + releasePlunDate + ", releaseDate=" + releaseDate
				+ "]";
	}

}
