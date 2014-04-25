フォーカス情報を利用するツールがフォーカスしている情報(内容)を表示する
連動可視化(FocusDisplay(ID=1016))


現在注目している（マウスで接触、クリック、選択した）情報としての
フォーカス情報（他のモジュールでフォーカスされている情報）を表示する．
ツール下部の、メイン、サブ、キーワード、メイン、サブ、セグメント、メイン、サブ、文のエリアをクリックすることで、
対応するフォーカス情報に表示内容を変更できる
＃ツールがフォーカスの機能を実装している場合のみ表示される

[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  FocusDisplay

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 4501:	テキスト可視化
case 4502:	テキスト可視化
case 0:		テキスト可視化
case 11:	メインキーワード	に表示モード変更
case 12:	サブキーワード	に表示モード変更
case 13:	キーワード集合	に表示モード変更
case 21:	メインセグメント	に表示モード変更
case 22:	サブセグメント	に表示モード変更
case 23:	セグメント集合	に表示モード変更
case 31:	メイン文	に表示モード変更
case 32:	サブ文	に表示モード変更
case 33:	文集合	に表示モード変更

     ・処理モジュールから受け取れる入力データ：なし
     ・クラス情報：
public class FocusDisplay extends VisualizationModule

     ・フォーカス型変数の利用：
if(text.focus.focusKeywords[i])
if(text.focus.focusSegments[i])
if(text.focus.focusSentences[i])
case 0:if(text.focus.mainFocusKeyword >= 0)
case 1: if(text.focus.subFocusKeyword >= 0)
case 0:if(text.focus.mainFocusSegment >= 0)
case 1: if(text.focus.subFocusSegment >= 0)
case 0:if(text.focus.mainFocusSentence >= 0)
case 1: if(text.focus.subFocusSentence >= 0)
フォーカス情報として、
キーワード集合，セグメント集合，文集合，メインキーワード，サブキーワード，メインセグメント，サブセグメント，メイン文，サブ文
をチェックする

     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
