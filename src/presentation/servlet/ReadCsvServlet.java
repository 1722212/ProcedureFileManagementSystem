package presentation.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import logic.bean.ProcedureFileBean;
import presentation.util.Constance;

/**
 * 運用手順書貸出/返却依頼表から入力情報を取得する
 */
@WebServlet("/ReadCsvServlet")
public class ReadCsvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 読み取り先のディレクトリ
		String INPUT_DIR = Constance.TARGET_DIR;
		// 対象のエクセルファイルのパス
		String xlsxFileAddress = INPUT_DIR + Constance.TARGET_FILE;
		// ワークブック変数を初期化
		Workbook workbook = null;

		try {
			// ファイル読込み
			workbook = WorkbookFactory.create(new FileInputStream(xlsxFileAddress));
		} catch (EncryptedDocumentException e) {

			// エラーメッセージをリクエストに格納
			String errorMessage = Constance.ENCRYPTEDDOCUMENTEXCEPTION + e.getMessage();
			request.setAttribute("", errorMessage);

			e.printStackTrace();

		} catch (InvalidFormatException e) {
			// エラーメッセージをリクエストに格納
			String errorMessage = Constance.INVALIDFORMATEXCEPTION + e.getMessage();
			request.setAttribute("", errorMessage);
			e.printStackTrace();
		}

		// 貸出/返却依頼表のBeanを格納するリスト生成
		List<ProcedureFileBean> procedureFileBeanList = new ArrayList<>();

		// シート名を取得
		Sheet sheet = workbook.getSheet("依頼表");

		// 案件名を取得
		Row row1 = sheet.getRow(1);// １行目を取得
		Cell cellOfProjectName = row1.getCell(2);// １行C列を取得
		String projectName = cellOfProjectName.getStringCellValue();// 案件名を取得

		// 貸出情報が入力されている行を取得（5行目以降）
		LOOP1: for (int i = 4; i < 1000; i++) {

			// 列から管理情報を取得
			Row row = sheet.getRow(i);// 行を取得
			// 貸出/返却依頼表のBean生成
			ProcedureFileBean procedureFileBean = new ProcedureFileBean();
			procedureFileBean.setProjectName(projectName);// 案件名

			// 行が未入力の場合はLOOP1をbreak
			if (row == null) {
				break LOOP1;
			}
			Cell cellA = row.getCell(0);// A列を取得
			procedureFileBean.setSequenceNumber(cellA.getNumericCellValue());// シーケンス番号

			// マニュアル種類が未入力の場合はLOOP1をbreak
			if (row.getCell(1) == null) {
				break LOOP1;
			}
			Cell cellB = row.getCell(1);// B列を取得
			procedureFileBean.setManualCategory(cellB.getStringCellValue());// マニュアル種類

			Cell cellC = row.getCell(2);// C列を取得
			procedureFileBean.setManualFileName(cellC.getStringCellValue());// 対象運用手順書

			Cell cellD = row.getCell(3);// D列を取得
			procedureFileBean.setTargetPage(cellD.getNumericCellValue());// 対象ページ

			Cell cellE = row.getCell(4);// E列を取得
			procedureFileBean.setOperationCategory(cellE.getStringCellValue());// 区分

			Cell cellF = row.getCell(5);// F列を取得
			procedureFileBean.setApplicantForRental(cellF.getStringCellValue());// 貸出時申請者

			Cell cellG = row.getCell(6);// G列を取得
			procedureFileBean.setTelForRental(cellG.getStringCellValue());// 貸出時連絡先

			Cell cellH = row.getCell(7);// H列を取得
			procedureFileBean.setRentalRequestDate(cellH.getDateCellValue());// 貸出依頼日

			Cell cellI = row.getCell(8);// I列を取得
			procedureFileBean.setReturnPlanDate(cellI.getDateCellValue());// 返却予定日

			Cell cellJ = row.getCell(9);// J列を取得
			procedureFileBean.setReleasePlunDate(cellJ.getDateCellValue());// リリース予定日

			if (row.getCell(10) != null) {
				Cell cellK = row.getCell(10);// K列を取得
				procedureFileBean.setApplicantForReturn(cellK.getStringCellValue());// 返却時申請者
			}

			if (row.getCell(11) != null) {
				Cell cellL = row.getCell(11);// L列を取得
				procedureFileBean.setTelForReturn(cellL.getStringCellValue());// 返却時連絡先
			}

			if (row.getCell(12) != null) {
				Cell cellM = row.getCell(12);// M列を取得
				procedureFileBean.setReturnDate(cellM.getDateCellValue());// 返却日
			}

			if (row.getCell(13) != null) {
				Cell cellN = row.getCell(13);// N列を取得
				procedureFileBean.setReleaseDate(cellN.getDateCellValue());// リリース日
			}

			// リストに追加
			procedureFileBeanList.add(procedureFileBean);
		}
		// ワークブックをクローズ
		workbook.close();

		// 手順書情報をリストに追加
		HttpSession session = request.getSession();
		session.setAttribute("procedureFileBeanList", procedureFileBeanList);

		// 依頼表一覧画面へフォワード
		request.getRequestDispatcher(Constance.SHOW_PROCEDURE_TABLE_JSP).forward(request, response);

	}

}
