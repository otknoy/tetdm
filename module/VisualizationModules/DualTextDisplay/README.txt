上下2つに分かれたウィンドウそれぞれに文字列を表示する
デュアルテキスト(DualTextDisplay(ID=15))


     ・使い道：文字列の表示
     ・使い方：文字列の表示，上下テキストエリアともマウスで選択した文字列にフォーカスを当て，すべての処理モジュールに連動処理要請を出す


[作者とライセンス情報]

・作者：広島市立大学システムインタフェース研究室
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  DualTextDisplay

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		上下テキストエリア，フォントセット
case 1:		上テキストエリア．テキストファイル保存
case 9:		上下テキストエリア，フォントサイズ縮小
case 10:	上下テキストエリア，フォントサイズ拡大
case 100:	上テキストエリア，入力テキストの表示
case 101:	上テキストエリア，テキスト表示（最後部）
case 102:	下テキストエリア，フォーカス単語を色付きでテキスト表示（先頭部）
case 103:	下テキストエリア，色なしテキスト表示に戻す（先頭部）

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, String t)
case 101:	String型	上に表示するテキスト
case 102:	String型	下に表示するテキスト

     ・クラス情報：
public class DualTextDisplay extends VisualizationModule implements MouseListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：
executeAllByClick();
executeAllByClick();
マウスでドラッグした文字列を mainFocusStringとして処理モジュールを実行
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
