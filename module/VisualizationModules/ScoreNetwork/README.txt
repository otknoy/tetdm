セグメント間の関連をグラフで表示する
スコアネットワーク(ScoreNetwork(ID=6))


セグメント（テキストor段落）間の関係をグラフで表示します．セグメントはノードで表されます．
点数が低いノードほど黄色いゾーン（上の方）に表示され、点数が高いほど青いゾーン（下の方）に表示されます．
マウスでノードの移動ができます．
何もないところでドラッグすると、ノード全体を移動できます．

[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  ScoreNetwork

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4501:	アクティブノードを初期化、関連度計算、セグメント間の関連可視化
case 0:		アクティブノードを初期化、関連度計算、セグメント間の関連可視化
case 1:		関連度計算、セグメント間の関連可視化
case 11:	関連度データを、ファイル ScoreData に保存

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, double score[])
case 55:	スコアデータ

public void setData(int dataID, boolean color)
case 10:	色情報 true:ノーマルカラー

     ・クラス情報：
public class ScoreNetwork extends VisualizationModule implements MouseListener, MouseMotionListener

     ・フォーカス型変数の利用：
if(text.focus.mainFocusSegment < 0)
text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//
メインセグメント，サブセグメント（マウスカーソルが当たっているノードをメイン，メインと最も似ているノードをサブとする）
     ・フォーカス情報による可視化連動（連動要請）：
repaintOthersByTouch();
マウスでノードを触ると、他の可視化ツールも再描画する
     ・フォーカス情報による処理連動（連動要請）：
executeAllByTouch();
マウスでノードを触ると、他の処理ツールが再処理する
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし



[本モジュールの使い方] 



利用者向け情報  
-----
     ・使い道：
     ・使い方：セグメント間の関連をグラフで表示する．マウスでノードの移動が行える．
     ・対応する可視化／処理ツール：特に定まったものはない
     ・必要な外部データファイル：なし
     ・作者情報：
     ・既発表文献：


 

