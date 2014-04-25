文章中の各文について，主題との関連度を計算する
光と影データ(MakeLight(ID=12))

・対応する可視化ツール：
テキスト(カラー)(TextDisplayColor(ID=2))
分布(ScoreDist(ID=4))
キーワード選択(KeywordSelect(ID=9))


「パネルセット」ボタンで、テキスト(カラー)(TextDisplayColor(ID=2))分布(ScoreDist(ID=4))キーワード選択(KeywordSelect(ID=9))との組み合わせにセットします。


テキスト(カラー)(TextDisplayColor(ID=2))との組み合わせで、
文章中の各文の主題関連度を評価して文をハイライトします．
（展望台（要約）(Panoramic ID=11)によって得られる観点語を文章の主題として，各文の主題関連度を評価する．）

分布(ScoreDist(ID=4))との組み合わせで、
テキスト全体の光の量を、棒グラフで表示します．

キーワード選択(KeywordSelect(ID=9))との組み合わせで、
主題単語（観点語）を選択できます。


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．
・既発表文献：西原陽子・佐藤圭太・砂山渡：光と影を用いたテキストのテーマ関連度の可視化, 人工知能学会論文誌，Vol.24, No.6, pp.480 -- 488, (2009).
Yoko Nishihara and Wataru Sunayama: Text Visualization using Light and Shadow based on Topic Relevance, International Journal of Intelligent Information Processing, Vol.2, No.2, pp.1 -- 8, (2011).


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  MakeLight

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 4502:				光と影のデータを生成して送信
case 0:					光と影のデータを生成して送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataIntegerArray(1,attr);				int[] 型		各文の主題関連度を1から9で数値化，TextDisplay2の黄色表示用
setDataDoubleArray(2,light_value);//for scoredist	double[] 型	各文の主題関連度の実数値()(ScoreDist用)
setDataInteger(77,5);			int 型 	5 	キーワード選択(KeywordSelect(ID=9))で表示する単語リストの数
setDataDoubleArray(10,value);		double[] 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価値
setDataString(10,boxLabel[0]);		String 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価基準
setDataDoubleArray(11,value);		double[] 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価値
setDataString(11,boxLabel[1]);		String 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価基準
setDataDoubleArray(12,value);		double[] 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価値
setDataString(12,boxLabel[2]);		String 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価基準
setDataDoubleArray(13,value);		double[] 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価値
setDataString(13,boxLabel[3]);		String 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価基準
setDataDoubleArray(14,value);		double[] 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価値
setDataString(14,boxLabel[4]);		String 型	キーワード選択(KeywordSelect(ID=9))で表示する単語の評価基準

     ・クラス情報：
public class MakeLight extends MiningModule

     ・フォーカス型変数の利用：
text.focus.focusKeywords[i] = false;
観点語の初期化

     ・フォーカス情報による処理連動フラグ(連動実行)：
focusClickExecute = true;
展望台（要約）(Panoramic ID=11) で主題単語（観点語）が代わったときに連動実行
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：
topicWords = getDataIntegerArray(11, 50);
テキストの観点となる単語集合	展望台（要約）(Panoramic ID=11)から取得
