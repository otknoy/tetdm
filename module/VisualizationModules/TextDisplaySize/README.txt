文字列を表示する．x座標に応じたフォントサイズに変更できる
テキスト(サイズ)(TextDisplaySize(ID=11))


     ・使い道：文字列の表示（フォントサイズ変更可能）
     ・使い方：下部のSmaller size, Larger sizeと書かれたパネル上でクリックすると，
	x座標（横軸）に応じたフォントサイズに変更可能


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  TextDisplaySize

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：（注）継承元のTextDisplayの内容
case 0:		テキスト表示
case 1:		テキストファイル保存
case 9:		フォントサイズ縮小
case 10:	フォントサイズ拡大
case 100:	入力テキストの表示

     ・処理モジュールから受け取れる入力データ：（注）継承元のTextDisplayの内容
public void setData(int dataID, String t)
default:	String型		表示するテキスト

public void setData(int dataID, String t[])
case 1:		String[]型	表示するテキスト，改行区切りで結合されて表示される
default:	String[]型	表示するテキスト，スペース区切りで結合されて表示される

     ・クラス情報：
public class TextDisplaySize extends TextDisplay
テキスト（TextDisplay(ID=1)）の内容を継承
     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし



[本モジュールの使い方] 


利用者向け情報  
-----

     ・対応する可視化／処理ツール：特に定まったものはない
     ・必要な外部データファイル：なし
     ・作者情報：
     ・既発表文献：


モジュール開発者向け情報
-----
     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
	＜TextDisplayの内部のメソッドを利用＞
		0:	テキスト表示
		1:	テキストファイル保存
		9:	フォントサイズ縮小
		10:	フォントサイズ拡大,
		100:	入力テキストの表示

     ・処理モジュールから受け取れるデータ（メソッドsetDataの引数と説明）：
	＜TextDisplayの内部のメソッドを利用＞
		default:	String型	表示するテキスト
		default:	String[]型		表示するテキスト，スペース区切りで結合される
		1:	String[]型		表示するテキスト，改行区切りで結合される

     ・既存の可視化モジュールの継承の有無：TextDisplay
     ・フォーカス情報による可視化連動の有無：なし
     ・処理連動の有無：なし
 


