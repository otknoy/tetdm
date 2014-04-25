単語間にスペースを入れて表示
サンプル２(Sample2(ID=10001))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))との組み合わせにセットします。


「結果表示」ボタンを押すと、入力したテキストの中の単語を区切り，単語と単語の間にスペースを入れた文字列を生成します


[作者とライセンス情報]

・作者：TETDM開発チーム
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容に依らず、自由に追加、改変することで、あなたが著作権を主張することができます。


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  Sample2

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:		空の文字列を作成して送信
case 1:		空白入り入力テキストを作成して送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,"");			String 型	空の文字列
setDataString(1,MyMethod());		String 型	空白入り入力テキスト

     ・クラス情報：
public class Sample2 extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
