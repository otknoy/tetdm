フォーカスされている情報をハイライトする
フォーカスチェック(FocusCheck(ID=18))

・対応する可視化ツール：
Htmlテキスト(TextDisplayHtml(ID=3))


「パネルセット」ボタンで、Htmlテキスト(TextDisplayHtml(ID=3))との組み合わせにセットします。


表示するフォーカス情報を変更します
ボタン：
「キーワード」：メインおよびサブのフォーカスキーワードを表示します
「キー集合」：集合としてフォーカスされているキーワードを表示します
「文」：メインおよびサブのフォーカス文を表示します
「文集合」：集合としてフォーカスされている文を表示します
「セグメント」：メインおよびサブのフォーカスセグメント（段落orテキスト）を表示します
「セグ集合」：集合としてフォーカスされているセグメント（段落orテキスト）を表示します

[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  FocusCheck

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:			フォーカスされている情報をハイライトしたHtmlを生成して送信
case 4501:		フォーカスされている情報をハイライトしたHtmlを生成して送信
case 4502:		フォーカスされている情報をハイライトしたHtmlを生成して送信
case 1:			表示内容の変更
case 2:			表示内容の変更
case 3:			表示内容の変更
case 4:			表示内容の変更
case 5:			表示内容の変更
case 6:			表示内容の変更

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,checkedText);		String 型	フォーカスされている情報をハイライトしたHtml
setDataString(0,checkedText);		String 型	フォーカスされている情報をハイライトしたHtml

     ・クラス情報：
public class FocusCheck extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：
if(text.focus.focusKeywords[text.segment[i].sentence[j].wordIDList[k]])
if(text.focus.focusSegments[i])
if(text.focus.focusSentences[ text.segment[i].positionOfFirstSentence + j ])
mainK = text.focus.mainFocusKeyword;
subK = text.focus.subFocusKeyword;
mainK = text.focus.mainFocusSegment;
subK = text.focus.subFocusSegment;
mainK = text.focus.mainFocusSentence;
subK = text.focus.subFocusSentence;
セグメント，文，キーワードの，メイン，サブ，集合

     ・フォーカス情報による処理連動フラグ(連動実行)：
focusTouchExecute = true;focusClickExecute = true;
フォーカス情報が変更されたときに、その内容を表示する
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
