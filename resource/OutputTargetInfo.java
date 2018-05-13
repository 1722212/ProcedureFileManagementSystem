import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import logic.bean.ProcedureFileBean;

/**
 * 貸出依頼書から貸出対象のファイル情報を出力する処理<br>
 *
 * リターンコード<br>
 *
 * ０：正常終了<br>
 * １：ENCRYPTEDDOCUMENTEXCEPTION<br>
 * ２：INVALIDFORMATEXCEPTION<br>
 * ３：FILENOTFOUNDEXCEPTION<br>
 * ４：IOEXCEPTION<br>
 *
 * @author *****
 *
 */
public class OutputTargetInfo {

	/**
	 * コマンドライン引数１：貸出依頼書のパス
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// 読込み対象のファイルパス
		String inputFilePath = args[0];
		// ワークブック変数を初期化
		Workbook openWorkbook = null;

		try {
			// ファイル読込み準備
			openWorkbook = WorkbookFactory.create(new FileInputStream(inputFilePath));
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			System.exit(1);

		} catch (InvalidFormatException e) {
			e.printStackTrace();
			System.exit(2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(3);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(4);
		}

		// 貸出/返却依頼表のBeanを格納するリスト生成
		List<ProcedureFileBean> procedureFileBeanList = new ArrayList<>();

		// シート名を取得
		Sheet sheet = openWorkbook.getSheet("依頼表");

		// 案件名を取得
		Row row1 = sheet.getRow(1);
		Cell cellOfProjectName = row1.getCell(2);
		String projectName = cellOfProjectName.getStringCellValue();

		// 貸出情報が入力されている行を取得（5行目以降）
		LOOP1: for (int i = 4; i < 1000; i++) {

			// 列から管理情報を取得
			Row row = sheet.getRow(i);
			// 貸出/返却依頼表のBean生成
			ProcedureFileBean procedureFileBean = new ProcedureFileBean();

			// 案件名をセット
			procedureFileBean.setProjectName(projectName);

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
		try {
			openWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(4);
		}

		// 出力ファイルの作成
		FileWriter fileWriter = null;
		try {
			// ファイルは上書きする
			fileWriter = new FileWriter("C:\\Users\\s13rp\\Desktop\\target_file.csv", false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(4);
		}

		PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));

		// ファイルに書込み
		for (ProcedureFileBean procedureFileBean : procedureFileBeanList) {

			// マニュアル種類
			printWriter.print(procedureFileBean.getManualCategory() + ",");
			// 手順書名
			printWriter.print(procedureFileBean.getManualFileName() + ",");
			// 対象ページ
			printWriter.print(procedureFileBean.getTargetPage() + ",");
			// 区分
			printWriter.print(procedureFileBean.getOperationCategory());
			// 改行
			printWriter.println();
		}

		// クローズ処理
		printWriter.close();

		// 正常終了
		System.out.println("正常終了しました");
		System.exit(0);

	}

}
