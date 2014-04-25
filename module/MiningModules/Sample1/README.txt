単語間にスペースを入れて表示
サンプル１(Sample1(ID=10000))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))との組み合わせにセットします。


入力したテキストの中の単語を区切り，単語と単語の間にスペースを入れたテキストを生成します


[作者とライセンス情報]

・作者：TETDM開発チーム
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容に依らず、自由に追加、改変することで、あなたが著作権を主張することができます．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  Sample1

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:				空白入り入力テキストを作成して送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,MyMethod());	String 型	空白入り入力テキスト

     ・クラス情報：
public class Sample1 extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
