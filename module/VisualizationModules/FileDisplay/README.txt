指定するファイルの内容を表示する
ファイル(FileDisplay(ID=5))


指定されたファイルの内容を表示する（ファイル名の指定は処理ツールからのみ可能）
編集された内容を指定ファイルに更新保存する（操作は処理ツールからのみ可能）


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  FileDisplay

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		テキスト表示
case 991:	指定ファイルへのテキスト保存（ファイルの上書き保存）
case 992:	指定ファイルへのテキスト保存（元ファイルがなかった場合も保存）
case 9:		フォントサイズ小さく
case 10:	フォントサイズ大きく

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, String filename)
case 47:		String型 ファイル名

     ・クラス情報：
public class FileDisplay extends VisualizationModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
