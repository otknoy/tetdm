文章のトップダウンの構造と評価値を出力する
トップボトム木(DrawTopBottom(ID=22))


[使い方を記入してください]
セグメント間の関連をツリー構造で表示する．マウスでノードの移動ができる．


[作者とライセンス情報]

・作者：システムインタフェース研究室（広島市立大学）
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  DrawTopBottom

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4502://Click
case 0:
ツリー構造可視化

     ・処理モジュールから受け取れる入力データ：なし
     ・クラス情報：
public class DrawTopBottom extends VisualizationModule implements MouseListener, MouseMotionListener

     ・フォーカス型変数の利用：
text.focus.mainFocusSegment = touch_num;
text.focus.subFocusSegment = minSeg;
メインセグメント，サブセグメント（マウスカーソルが当たっているノードをメイン，メインと最も似ているノードをサブとする）

     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：
executeOthersByClick();

     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし

     ・必要な外部データファイル：
	- tree2.png
