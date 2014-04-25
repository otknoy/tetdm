セグメント間の関連をツリー構造で表示する
セグメント木(DrawTree(ID=1015))


セグメント間の関連をツリー構造で表示します．
マウスでノードの移動ができます．
何もないところでドラッグすると、ノード全体を移動できます．


[作者とライセンス情報]

・作者：システムインタフェース研究室（広島市立大学）
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  DrawTree

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4501:	ツリー構造可視化
case 0:		ツリー構造可視化

     ・処理モジュールから受け取れる入力データ：なし
     ・クラス情報：
public class DrawTree extends VisualizationModule implements MouseListener, MouseMotionListener

     ・フォーカス型変数の利用：
if(text.focus.mainFocusSegment < 0)
text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//

メインセグメント，サブセグメント（マウスカーソルが当たっているノードをメイン，メインと最も似ているノードをサブとする）
     ・フォーカス情報による可視化連動（連動要請）：
repaintOthersByTouch();
マウスでノードを触ると、他の可視化ツールも再描画する
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし

     ・必要な外部データファイル：
	- tree2.png
