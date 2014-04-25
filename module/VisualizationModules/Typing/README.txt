タイピングの出題と結果を表示する
タイピング(Typing(ID=20001))


[使い方を記入してください]
処理モジュール「タイピング(TypingControl)」と組み合わせて使用してください
特に操作は必要ありません

<出題時>
*で表示される文字数の英語を入力します
画面左上に、正解の文字が表示されます
画面下部には、一つ前の問題の解答が表示されます
出題中に他のボタン等に触ると入力できなくなります。その場合は、その問題をスキップしてください。


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  Typing

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:初期画面を表示する
case 1:出題を開始する
case 2:問題をスキップして次の問題を出題する
case 3:出題を終了する
     ・処理モジュールから受け取れる入力データ：なし
     ・クラス情報：
public class Typing extends VisualizationModule implements KeyListener
     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：
	タイミング連動：mainFocusDoubleに各問題終了時のラップタイムを代入する
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
