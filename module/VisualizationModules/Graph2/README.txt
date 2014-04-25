一次元の数値データの折れ線グラフを表示します
グラフ２(Graph2(ID=23))


[使い方を記入してください]
「P-」「P+」ボタン：縦軸の正の部分を増減させる
「M-」「M+」ボタン：縦軸の負の部分を増減させる
「SUM」：一次元データの、累積データを表示します。
「%」：一次元データの最大値と最小値の差を100%として表示します。


[作者とライセンス情報]

・作者：TETDM開発チーム
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  Graph2

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4503:グラフを表示する為のdouble型配列valueのfocusNumber番目に，
	text.focus.mainFocusDoubleの値を代入する．
	focusNumberの値は0から始まって1ずつ増え，10に達すると0に戻る．

case 0:
グラフの表示

case 1:focusNumberの値を0に初期化する

     ・処理モジュールから受け取れる入力データ：
public boolean setData(int dataID, double score[])
case 0:value = score;

グラフとして表示する一次元数値データ

     ・クラス情報：
public class Graph2 extends VisualizationModule implements ActionListener

     ・フォーカス型変数の利用：
	text.focus.getMainFocusDouble();
	フォーカスタイミングによる可視化連動時にデータを取得する
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
