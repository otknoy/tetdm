文章内のキーワードから作成される，２つのキーワード集合A,Bの比較を行うための出力を行います．
キーワード比較(KeywordCompare(ID=21))


[使い方を記入してください]
キーワード集合の比較を行います．


[作者とライセンス情報]

・作者：システムインタフェース研究室（広島市立大学）
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  KeywordCompare

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4501:
case 0:
特に処理はありません．
     ・処理モジュールから受け取れる入力データ：
public boolean setData(int dataID, int data[])
case 0:dataA = data;
case 1:dataB = data;

部分テキストにおけるキーワード集合A,Bについて，その各キーワードの頻度を，dataAとdataBのそれぞれに受け取ることで，
キーワード集合の比較結果を表示します．

     ・クラス情報：
public class KeywordCompare extends VisualizationModule// implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし