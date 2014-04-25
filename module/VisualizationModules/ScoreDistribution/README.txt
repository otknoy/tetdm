テキスト中の各文の評価値を横向きの棒グラフで表示する
分布(ScoreDistribution(ID=4))


テキスト中の各文の評価値を横向きの棒グラフで表示します
(棒の長さは最大評価値によって正規化されます）


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  ScoreDistribution

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		テキスト中の各文の評価値を横向きの棒グラフで表示

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, double score[])
case 2:		double[]	棒グラフ用の数値列（int型にキャストされる)

public void setData(int dataID, int score[])
case 2:		int[]		棒グラフ用の数値列

     ・クラス情報：
public class ScoreDistribution extends VisualizationModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
