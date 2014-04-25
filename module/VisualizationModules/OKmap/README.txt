再帰的クラスタリング（データの類似性に基づく分類）の結果を地図形式で表示します
OKmap(OKmap(ID=9))

段落数が０または１の時には，クラスタリングの意味がないため起動しません。


[使い方を記入してください]
マウスのドラッグで，地図の平行移動
マウスのスクロールで，ズームイン，ズームアウト
地点に触れることで，その地点に対応するテキスト（セグメント）の内容を，注目するテキスト（フォーカスセグメントにセット）として連動して表示
海の上でマウスの右クリック／左クリックで，海面のレベルを変更


[作者とライセンス情報]

・作者：システムインタフェース研究室（広島市立大学）
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  OKmap

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0: break;
特になし
case 100://Create Link Data in MapData
地図データの作成
case 400:
地図データの再構築
case 4501:
注目ノードを明示的に表示
     ・処理モジュールから受け取れる入力データ：
public void setData(int n, int data)
case 0: ノード数
case 1: クラスタ数（エリア数）

public void setData(int n, int data[][])
case 2: エリア内の要素数

public void setData(int n, boolean data[][])
case 3: リンク
case 4: 強リンク

public void setData(int n, String name[])
case 10:// Node Names	ノード名
default://100-Area Names エリアのラベル名

     ・クラス情報：
public class OKmap extends VisualizationModule implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener

     ・フォーカス型変数の利用：
touch_num = text.focus.mainFocusSegment;
//text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//
注目ノードを，触っているノードとしてセット

     ・フォーカス情報による可視化連動（連動要請）：
repaintOthersByTouch();
注目ノードの情報を共有
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし