テキスト情報を表示する
テキスト(TextDisplay(ID=1))


テキスト情報を表示します


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  TextDisplay

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		テキスト表示
case 1:		テキストファイル保存
case 9:		フォントサイズ縮小
case 10:	フォントサイズ拡大
case 100:	入力テキストの表示

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, String t)
default:	String型		表示するテキスト

public void setData(int dataID, String t[])
case 1:		String[]型	表示するテキスト，改行区切りで結合されて表示される
default:	String[]型	表示するテキスト，スペース区切りで結合されて表示される

     ・クラス情報：
public class TextDisplay extends VisualizationModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
