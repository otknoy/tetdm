テキスト中の各主題の文章中の出現率を計算する
主題語含有率(TopicDistribution(ID=23))

・対応する可視化ツール：
レーダーチャート(RadarChart(ID=16))


文章中に含まれる主題語が、実際に文章中にどの程度現れているか計算します。
下部にあるプルダウンメニューから計算方法を選択できます。


[作者とライセンス情報]

・作者：石井達也、梶並知記
・本モジュールの利用許諾について、TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します。


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  TopicDistribution

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:Panoramic(ID=11)モジュールから取得するデータを利用し計算を行います。

     ・出力データ（引数の型情報を追加して下さい）：

setDataString(0,makeData());	String型で"主題語,値（0～1の実数を文字列型に変換したもの）\n"が、主題語の数だけ含まれます。

     ・クラス情報：
public class TopicDistribution extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：
topicList=getNewDataIntegerArray(11,77);	要約（展望台）(Panoramic(ID=11))