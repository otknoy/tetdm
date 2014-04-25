二つの段落間の単語の出現関係を明示する
関連チェック(RelationCheck(ID=17))

・対応する可視化ツール：
Htmlテキスト(TextDisplayHtml(ID=3))


「パネルセット」ボタンで、
セグメント独自性(DrawOriginal(ID=1013))
連動可視化(FocusDisplay(ID=1016))
連動可視化(FocusDisplay(ID=1016))
Htmlテキスト(TextDisplayHtml(ID=3))との組み合わせにセットします。

Htmlテキスト(TextDisplayHtml(ID=3))との組み合わせで、
２つのテキスト（セグメント独自性(DrawOriginal(ID=1013))上で触ったノードが表すテキスト、およびそのテキストと最も関連があるテキスト）間の単語の出現関係を明示

連動可視化(FocusDisplay(ID=1016))との組み合わせで、
セグメント独自性(DrawOriginal(ID=1013))上で触ったノードが表すテキスト、（メイン セグメント）
または、そのテキストと最も関連があるテキスト の内容を表示（サブ セグメント）



[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  RelationCheck

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 4501:		テキスト間の関連をチェックした結果の文字列を送信
case 0:			テキスト間の関連をチェックした結果の文字列を送信
case 1:			テキスト間の関連をチェックした結果の文字列を送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,checkedText);			String 型	テキスト間の関連をチェックした結果の文字列
setDataString(0,"<HTML><BODY>"+checkedText+"</BODY></HTML>");		String 型	テキスト間の関連をチェックした結果の文字列

     ・クラス情報：
public class RelationCheck extends MiningModule

     ・フォーカス型変数の利用：
if(text.focus.mainFocusSegment != -1 && text.focus.subFocusSegment != -1)
if(text.focus.mainFocusSegment != -1 && text.focus.subFocusSegment != -1)
mainFocusSegment と subFocusSegment で表されるセグメント（テキストor段落）間の関連を調べる

     ・フォーカス情報による処理連動フラグ(連動実行)：
focusTouchExecute = true;
セグメント独自性(DrawOriginal(ID=1013))上で触ったノードが表すテキスト、およびそのテキストと最も関連があるテキストとの関連を表示
     ・オプションによる可視化連動(連動要請)：
displayModule(1016, 22);
displayModuleFirst(1016, 21);
「セット」ボタンによるパネルセット後に、メイン、サブフォーカスセグメントを表示
     ・オプションによる処理連動(連動要請)：
executeModule(17,1);
「セット」ボタンによるパネルセット後に、関連度計算処理を実行
     ・データ取得による処理連動(連動要請)：なし
