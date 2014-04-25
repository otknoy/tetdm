各セグメントの独自性を確認できる
セグメント独自性(DrawOriginal(ID=1013))


セグメント（テキストor段落）間の関係をグラフで表示します．セグメントはノードで表されます．
独自性が高いノードほど青いゾーン（上の方）に表示され、独自性が低いほど赤いゾーン（下の方）に表示されます．
マウスでノードの移動ができます．
何もないところでドラッグすると、ノード全体を移動できます．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．
・既発表文献：砂山渡・川口俊明：内容の独自性の視覚化によるレポートの独自性評価支援システム，人工知能学会論文誌，Vol.23, No.6, pp.392 -- 401, (2008).


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  DrawOriginal

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4501:		セグメント間の関連可視化
case 0:			セグメント間の関連可視化
case 1:			独自性のデータをファイル OriginalData に保存します

     ・処理モジュールから受け取れる入力データ：なし
     ・クラス情報：
public class DrawOriginal extends VisualizationModule implements MouseListener, MouseMotionListener

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
