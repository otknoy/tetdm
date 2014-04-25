入力した単語（検索語）をもとに，最大５０件のツイートを取得する
Twitter(TweetExtraction(ID=20))

・対応する可視化ツール：
テキスト(カラー)(TextDisplayColor(ID=2))


「パネルセット」ボタンで、テキスト(カラー)(TextDisplayColor(ID=2))との組み合わせにセットします。


下部のテキストフォームに単語（検索語）を入れて、改行（エンター、リターン）キーを押すと、入力した時点を最新とし最大５０件のツイートを取得する。
「Clear/Check」ボタンを押すと、テキストフォームの文字列を消去し、取得したツイート内の検索語をハイライト表示する。


[作者とライセンス情報]

・作者：西原陽子
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  TweetExtraction

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:					なし
case 23://SEARCH AND SAVE RESULTS	テキストフォームの単語でtwitter検索（Twitter APIを使って、入力されたクエリを含むTweetを最大５０件取得），結果を保存
case 1:					検索語を，表示中のテキスト内で可視化
case 24://FOCUS SEARCH KEYWORD		検索語を，表示中のテキスト内で可視化
case 26://DISPLAY SEARCH RESULTS WITHOUT SAVING		現在コメントアウト中
     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(24,text.focus.getMainFocusString());	String 型	検索語（mainFocusString）
setDataString(1,text.originalText);			String 型	元テキスト
setDataString(24,query);				現在コメントアウト中
setDataString(1,MyMethod());//violation: not created in 0 or 1 		現在コメントアウト中
     ・クラス情報：
public class TweetExtraction extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
