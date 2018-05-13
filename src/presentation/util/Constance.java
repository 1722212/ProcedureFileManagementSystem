package presentation.util;

/**
 * 定数クラス
 *
 * @author s13rp
 *
 */
public class Constance {

	/**
	 * 読込み先のディレクトリ
	 */
	public static final String TARGET_DIR = "C:\\Users\\Takayama\\Desktop\\";

	/**
	 * 読込みファイル
	 */
	public static final String TARGET_FILE = "インターネットバンキングシステム_運用手順書貸出・返却依頼表.xlsx";

	/**
	 * 管理表のパス
	 */
	public static final String MANAGEMENT_FILE = "C:\\Users\\Takayama\\Desktop\\管理表.xlsx";

	/**
	 * ファイルフォーマット不正のエラーメッセージ
	 */
	public static final String INVALIDFORMATEXCEPTION = "ファイルの読み込みに失敗しました。ファイルのフォーマットが不正です。：";

	/**
	 * パスワード設定のエラーメッセージ
	 */
	public static final String ENCRYPTEDDOCUMENTEXCEPTION = "ファイルの読み込みに失敗しました。パスワード設定を確認してください。：";

	/**
	 * IOEXCEPTIONのエラーメッセージ
	 */
	public static final String IOEXCEPTION = "貸出依頼書の読み込みに失敗しました。ファイル内容を確認してください：";

	/**
	 * FILENOTFOUNDEXCEPTIONのエラーメッセージ
	 */
	public static final String FILENOTFOUNDEXCEPTION = "貸出依頼書が見つかりません。ファイルパスを確認してください：";

	/**
	 * 貸出/返却依頼表一覧画面
	 */
	public static final String SHOW_PROCEDURE_TABLE_JSP = "/WEB-INF/jsp/showProcedureTable.jsp";

}
