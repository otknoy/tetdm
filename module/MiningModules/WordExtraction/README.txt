品詞や高頻出の単語，指定した単語，それらが含まれる文を抽出する
単語抽出(WordExtraction(ID=3))

・対応する可視化ツール：
テキスト(カラー)(TextDisplayColor(ID=2))
ファイル(FileDisplay(ID=5))


「パネルセット」ボタンで、テキスト(カラー)(TextDisplayColor(ID=2))ファイル(FileDisplay(ID=5))との組み合わせにセットします。


テキスト(カラー)(TextDisplayColor(ID=2))との組み合わせで、
     ・使い道：注目したい単語や品詞、それらが含まれる文を抽出する際に使う
     ・使い方：
	（１）「品詞」ボタン：文章中の品詞の分布を見たいとき：PartOfSpeechボタンを押すと、「環境設定」中で「キーワードとして取り出す単語の品詞と種類」に設定されている品詞がハイライト表示される。ハイライトの色は品詞毎に異なる
	（２）「高頻度」ボタン：文章中で頻度の高い単語を見たいとき：Frequencyボタンを押すと、「環境設定」中で「キーワードとして取り出す単語の品詞と種類」に設定されている単語のうち、頻度の高い単語上位２０％がハイライト表示される。
	（３）「指定単語」ボタン：特定の単語の分布を見たいとき：Dictionary:Wボタンを押すと、辞書に登録された単語がハイライト表示される。辞書は本README.txtファイルがあるフォルダの中のExtractionWords.txt。
	（４）「指定単語（文）」ボタン：特定の単語が含まれる文の分布を見たいとき：Dictionary:Sボタンを押すと、辞書に登録された単語を含む文がハイライト表示される。辞書は本README.txtファイルがあるフォルダの中のExtractionWords.txt。
	「保存＆抽出」ボタン：
	パネルセットボタンを押した後、可視化ツール ファイル(FileDisplay(ID=5))がセットされた状態で、
	ファイル(FileDisplay(ID=5))の内容を，ファイルExtractionWords.txtに保存し，保存された単語によりハイライト表示する


[作者とライセンス情報]

・作者：西原陽子
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  WordExtraction

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:		辞書のファイル名，辞書内の単語列を送信
case 1:		辞書内の単語の再読み込み
case 25:	テキストフィールド内の文字列取得，可視化要請
case 11:	使い方（１）
case 12:	使い方（２）
case 13:	使い方（３）
case 23:	使い方（４）

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(47,myModulePath+"ExtractionWords.txt");	String	辞書ファイル名
setDataString(0,createDictionalyWords());		String	辞書内の単語列（改行で結合）
setDataString(25,query);				String	テキストフィールドに入力された検索用単語
setDataIntegerArray(11,wordHighlightNumber);		int[]	品詞による単語表示の色指定番号列。配列の要素数は入力テキスト中の単語の延べ数になる。配列にはハイライトする際の色の番号が代入される。
setDataIntegerArray(12,wordHighlightNumber);		int[]	高頻度による単語表示の色指定番号列。配列の要素数は入力テキスト中の単語の延べ数になる。配列にはハイライトする際の色の番号が代入される。
setDataIntegerArray(13,wordHighlightNumber);		int[]	指定単語による単語表示の色指定番号列。配列の要素数は入力テキスト中の単語の延べ数になる。配列にはハイライトする際の色の番号が代入される。
setDataIntegerArray(23,sentenceHighlightNumber);	int[]	指定単語による文表示の色指定番号列。配列の要素数は入力テキスト中の文の数になる。配列にはハイライトする際の色の番号が代入される。

     ・クラス情報：
public class WordExtraction extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：
displayOtherModule(5,991);
別パネル内で，可視化ツール ファイル(FileDisplay(ID=5)) が使われている場合，
ファイル ExtractionWords.txtの単語を更新保存し，再度ハイライト表示を行う．
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
