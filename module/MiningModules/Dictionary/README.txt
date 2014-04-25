オンライン辞書サイトを利用し，文字列の意味を調べる
国語辞書(Dictionary(ID=21))

・対応する可視化ツール：
デュアルテキスト(DualTextDisplay(ID=15))


「パネルセット」ボタンで、デュアルテキスト(DualTextDisplay(ID=15))との組み合わせにセットします。


	テキストフォームに単語を入力して、リターンキーを押すと、その単語の意味をオンライン辞書サイトから調べます
	同時に、入力テキスト内でその単語が出現する箇所をハイライト表示します。
	「履歴削除」ボタン：検索履歴と検索済み文字列の色をリセットします


[作者とライセンス情報]

・作者：広島市立大学システムインタフェース研究室
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  Dictionary

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 25:				テキストフィールドの文字列を，mainFocusStringに設定して，0番の処理を実行
case 4502:				フォーカス文字列mainFocusString があれば，オンライン辞書で検索して結果を送信
case 0: 				フォーカス文字列mainFocusString があれば，オンライン辞書で検索して結果を送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(102,searchResults);	String型 辞書検索結果の文字列
setDataString(102,searchResults);	String型 辞書検索結果の文字列

     ・クラス情報：
public class Dictionary extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：
focusClickExecute = true;
mainFocusString があれば，その文字列を辞書で検索
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
