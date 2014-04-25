各文の単語をタグクラウドとして表示する
タグクラウド(TagCloudView(ID=14))


各文の単語をタグクラウドとして表示します。


[作者とライセンス情報]

・作者：高間康史
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  TagCloudView

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		各文の単語をタグクラウドとして表示

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, String[] t)
case 0:// keyphrases		String[]型	キーフレーズの文字列
case 2:// sentences		String[]型	文

public void setData(int dataID, int[] v){
case 1:				int[]型	キーフレーズの大きさ

     ・クラス情報：
public class TagCloudView extends VisualizationModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
