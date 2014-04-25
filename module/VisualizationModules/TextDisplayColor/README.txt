テキストの文や単語を背景色付きで表示できる
テキスト(カラー)(TextDisplayColor(ID=2))


テキストを表示します．
文や単語を背景色付きで表示することができます．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  TextDisplayColor

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		表示するテキストの各文に背景色をつけて表示
case 11:	表示するテキストの各単語に背景色をつけて表示
case 1:		テキスト保存
case 9:		フォントサイズ縮小
case 10:	フォントサイズ拡大
case 25:	表示中のテキストを変数に取得，続いて24の内容を実行
case 24:	表示するテキストの特定の単語に背景色を付けて表示

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, int data[])
case 1:		int[]型	テキストの各文の色情報（0から20の数値で指定)
case 23:	int[]型	テキストの各文の色情報（0から20の数値で指定)
case 11:	int[]型	テキストの各単語の色情報（0から20の数値で指定)
case 12:	int[]型	テキストの各単語の色情報（0から20の数値で指定)
case 13:	int[]型	テキストの各単語の色情報（0から20の数値で指定)

public void setData(int dataID, String data)
case 1:		String型	表示するテキスト
case 24:	String型	色を付ける単語
case 25:	String型	色を付ける単語

     ・クラス情報：
public class TextDisplayColor extends VisualizationModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
