指定した数の重要文を抽出する
要約(展望台)(Panoramic(ID=11))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))
キーワード(展望台)(XDrawDisplay(ID=8))

「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))キーワード(展望台)(XDrawDisplay(ID=8))との組み合わせにセットします。

	テキスト(TextDisplay(ID=1))との組み合わせで、
	- 指定した数の重要文を抽出します． 「Indicative 指示的要約」「Informative 報知的要約」「Conclusion 結論文抽出」を行います．
		-- 指示的要約：テキスト全体からのキーとなる文を抽出します
		-- 報知的要約：テキスト全体のストーリーを再現する文を抽出します
		-- 結論文抽出：テキストの話の結論となる文を抽出します
	- 水色パネル部分で、抽出する文の数を指定します(初期値は10)
	- 右の４つのボタンで，「Add Viewpoint 観点語の補完の有無」「Basic 背景語使用の有無」「Viewpoint 観点語使用の有無」「Feature 特徴語使用の有無」を選択できます．
		-- 観点語：要約する際の観点を表す単語
		-- 背景語：文章の背景となる高頻度の単語
		-- 特徴語：文章の特徴を表す、観点語との結びつきが強い単語

	キーワード(展望台)(XDrawDisplay(ID=8))との組み合わせで、
	- 観点語、背景語、特徴語をそれぞれ、オレンジ、青、緑色で表示します。
	- パネル内でクリックしたときに、赤線より上にある単語を観点語として、要約を再生成します。


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．
・既発表文献：
	- 砂山渡・谷内田正彦：文章の特徴を表すキーワードを発見して重要文を抽出する展望台システム，電子情報通信学会論文誌, Vol.J84-D-I, No.2, pp.146 -- 154, (2001).
	- 砂山渡・谷内田正彦：観点に基づいて重要文を抽出する展望台システムとそのサーチエンジンへの実装，人工知能学会論文誌，Vol.17, No.1, pp.14 -- 22, (2002).

モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  Panoramic

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 4502:	文章を要約して送信，単語の可視化用データの作成
case 0:		文章を要約して送信，単語の可視化用データの作成
case 1:		文章を要約して送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,summarized_text);	String 型	要約したテキスト
setDataString(1,summarized_text);	String 型	要約したテキスト
setDataIntegerArray(77,sbase_idlist);// option is not intend to some specified modules	int[] 型	観点キーワードのIDリスト
setDataIntegerArray(50,topic_idlist);	int[] 型	主題キーワードのIDリスト
setDataIntegerArray(51,back_idlist);	int[] 型	背景キーワードのIDリスト
setDataIntegerArray(52,feat_idlist);	int[] 型	特徴キーワードのIDリスト
setDataString(0,summarized_text);	String 型	要約したテキスト
setDataString(0,summarized_text);	String 型	要約したテキスト
setDataString(0,summarized_text);	String 型	要約したテキスト
setDataString(0,summarized_text);	String 型	要約したテキスト
setDataString(0,summarized_text);	String 型	要約したテキスト

     ・クラス情報：
public class Panoramic extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：
if(text.focus.focusKeywords[i] && sbasenum < sbasemax)//////////////////FOR LINKAGE
focusKeywordsを観点語として要約を実行する

     ・フォーカス情報による処理連動フラグ(連動実行)：
focusClickExecute = true;
フォーカスキーワード集合がある場合、その単語を観点語として処理を行う
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：
conclusionValue = getDataDoubleArray(13,11);
川下りラベル(LabelData(ID=13))から、各文の結論文としての評価値を取得する
