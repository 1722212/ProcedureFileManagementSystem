package old;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import logic.bean.ProcedureFileBean;
import presentation.util.Constance;

/**
 * 貸出依頼書から管理表を更新する処理
 *
 * @author Takayama
 *
 */
public class UpdateManagementTableForRental {

	/**
	 * コマンドライン引数１：貸出ファイル情報のパス
	 *
	 * ０：正常終了<br>
	 * １：ENCRYPTEDDOCUMENTEXCEPTION<br>
	 * ２：INVALIDFORMATEXCEPTION<br>
	 * ３：FILENOTFOUNDEXCEPTION<br>
	 * ４：IOEXCEPTION<br>
	 * ５：UnsupportedEncodingException<br>
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// 貸出ファイル情報を格納するリスト
		List<ProcedureFileBean> targetFileBeanList = new ArrayList<>();
		// BufferdReader初期化
		BufferedReader br = null;

		// 貸出ファイル情報を取得
		try {
			// 読込み準備
			File inputFile = new File(args[0]);
			FileInputStream fis = new FileInputStream(inputFile);
			InputStreamReader is = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(is);

			// 行
			String line = null;
			while ((line = br.readLine()) != null) {
				// 貸出ファイル情報を生成
				ProcedureFileBean targetFileBean = new ProcedureFileBean();
				// 1行をカンマで分割
				String[] columns = line.split(",");
				// Beanに値をセット
				for (int j = 0; j < columns.length; j++) {
					targetFileBean.setManualCategory(columns[0]);
					targetFileBean.setManualFileName(columns[1]);
					targetFileBean.setTargetPage(Integer.parseInt(columns[2]));
					targetFileBean.setOperationCategory(columns[3]);
					// リストに追加
					targetFileBeanList.add(targetFileBean);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(3);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(1);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(4);
		} finally {
			// クローズ
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(4);
			}
		}

		/*
		 * 読み取った情報をもとに管理表を更新
		 */

		// 管理表がを置いてあるフォルダのパス
		final String INPUT_DIR = Constance.TARGET_DIR;
		// 変数初期化
		Workbook updateWorkbook = null;
		FileOutputStream fos = null;

		try {
			// 管理表を指定
			FileInputStream fis = new FileInputStream(INPUT_DIR + Constance.MANAGEMENT_FILE);
			updateWorkbook = WorkbookFactory.create(fis);
			// シートを取得
			Sheet sheet = updateWorkbook.getSheet("管理表");

			// 貸出対象ファイル名ごとに管理表内を検索
			for (ProcedureFileBean targetFileBean : targetFileBeanList) {

				// 貸出情報が入力されている行を取得（5行目以降）
				LOOP1: for (int i = 12; i < 10000; i++) {

					// 列から管理情報を取得
					Row row = sheet.getRow(i);
					// 手順書情報のBean生成
					ProcedureFileBean procedureFileBean = new ProcedureFileBean();

					// 行が未入力の場合はLOOP1を抜ける
					if (row == null) {
						break LOOP1;
					}
					// 管理表の手順書名を取得
					Cell cellB = row.getCell(1);
					procedureFileBean.setManualFileName(cellB.getStringCellValue());
					// 拡張子を取り除く
					String[] fileNameWithoutExtension = procedureFileBean.getManualFileName().split(".");

					// 貸出対象ファイル名と比較
					if (targetFileBean.getManualFileName().equals(fileNameWithoutExtension[0])) {
						// 全体ステータスを引継済に変更
						Cell cellH = row.getCell(7);
						cellH.setCellValue("引継済");
						CellStyle style = updateWorkbook.createCellStyle();

						// 該当セルの背景色を赤色に変更
						style.setFillForegroundColor(IndexedColors.RED.getIndex());
						cellH.setCellStyle(style);

						// LOOP1を抜ける
						break LOOP1;
					}
				}
			}
			// 管理表を更新
			fos = new FileOutputStream(INPUT_DIR + Constance.MANAGEMENT_FILE);
			updateWorkbook.write(fos);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(3);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			System.exit(2);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(4);

		} finally {
			try {
				// ファイルクローズ
				fos.close();
				// ワークブッククローズ
				updateWorkbook.close();

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(4);
			}
		}
	}
}