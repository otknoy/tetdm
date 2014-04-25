各ツールのソースプログラム，READMEファイルなどが見られる
ソース表示(SourceRead(ID=99999))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))との組み合わせにセットします。


各ツールのソースプログラムを見ることができます。
READMEボタンを押して表示される内容に、●●●●と表示されている箇所に情報を追加した上で、
各モジュールのREADME.txtを作成することができます．

     ・使い方：
	（１）「ソース」ボタン：ソースプログラムを表示
	（２）「コンストラクタ」ボタン：ソース内のコンストラクタを表示
	（３）「グローバル」ボタン：ソース内のグローバル領域を表示
	（４）「基本メソッド」ボタン：TETDMのツール作成仕様に含まれるメソッドの表示
	（５）「チェック」ボタン：TETDMサイトにアップロードする際に，非推奨な項目の表示
	（６）「README」ボタン：README.txtファイルを作る際に必要な情報の表示
	（７）メニュー：マイニング処理ツール，または可視化インタフェースツールを選択する


[作者とライセンス情報]

・作者：TETDM開発チーム
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  SourceRead

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:miningOperations(5);
case 6://source				ソースプログラムの文字列を取得
case 1://constructor			ソース内のコンストラクタの文字列を取得
case 2://global variables		ソース内のグローバル領域の文字列を取得
case 3://basic functions		TETDMのツール作成仕様に含まれるメソッドの文字列を取得
case 4://check				TETDMサイトにアップロードする際に，非推奨な項目の文字列を生成
case 5://save readme.txt		README.txtファイルを作る際に必要な情報の文字列を生成

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,sourceText());				上記case6のデータの送信
setDataString(0,extractConstructor(moduleName));	上記case1のデータの送信
setDataString(0,extractGlobalAreas());			上記case2のデータの送信
setDataString(0,extractFunctions(basicFunctions));	上記case3のデータの送信
setDataString(0,checkWarnings());			上記case4のデータの送信
setDataString(0, readmeText);				上記case5のデータの送信

     ・クラス情報：
public class SourceRead extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：

     ・フォーカス情報による処理連動フラグ(連動実行)：

     ・オプションによる可視化連動(連動要請)：なし

     ・オプションによる処理連動(連動要請)：なし

     ・データ取得による処理連動(連動要請)：なし
