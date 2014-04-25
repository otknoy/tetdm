テキスト（セグメント）の集合を分類して地図（OKmap）用のデータを作成します
再帰的クラスタリング(RClustering(ID=6))

・対応する可視化ツール：
OKmap(OKmap(ID=9))
連動可視化(FocusDisplay(ID=1016))


「パネルセット」ボタンで、OKmap(OKmap(ID=9))連動可視化(FocusDisplay(ID=1016))との組み合わせにセットします。


[使い方を記入してください]
特に操作は必要ありません．


[作者とライセンス情報]

・作者：システムインタフェース研究室（広島市立大学）
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  RClustering

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:
再帰的クラスタリングを行い，地図用のデータを作成
     ・出力データの説明：
setDataInteger(0,text.segmentNumber);
地図上の全ノード数
setDataStringArray(100+i, names);
エリアのラベル名
setDataStringArray(10,createSegmentName());
ノード名
setDataInteger(1,clusterData[number].clusterNumber);//Number of Cluster
エリア数
setDataIntegerArray2(2,elements);//Cluster elements
エリア内の要素数
setDataBooleanArray2(3,clusterData[number].link);//Links
リンク
setDataBooleanArray2(4,clusterData[number].strongLink);//Strong Links
強リンク
     ・クラス情報：
public class RClustering extends MiningModule
     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし