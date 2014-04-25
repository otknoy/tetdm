特定の単語を別の単語に置き換える
単語置換(WordReplace(ID=4))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))
ファイル(FileDisplay(ID=5))


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))ファイル(FileDisplay(ID=5))との組み合わせにセットします。


テキスト(TextDisplay(ID=1))との組み合わせで、
     ・使い道：特定の単語を別の単語に置き換えたい際に使う（区切られた単語単位でのみで動作する）
     ・使い方：辞書ファイルReplaceWords.txtに置換前の単語と置換後の単語をスペース区切りのペアにして記述する。
	「保存＆置換」ボタン：
	パネルセットボタンを押した後、可視化ツール ファイル(FileDisplay(ID=5))がセットされた状態で、
	ファイル(FileDisplay(ID=5))の内容を，ファイルReplaceWords.txtに保存し，保存された単語により単語の置換を行う


[作者とライセンス情報]

・作者：西原陽子
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  WordReplace

     ・処理内容と生成するデータの説明：辞書ReplaceWords.txt中の置換前の単語を入力テキストから探す。置換前の単語があれば、辞書ファイル中の置換後の単語に置き換える。単語の置換が完了した後、新しいテキストをString型の変数として返す。

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:	辞書のファイル名，辞書内の単語列を送信
case 1:	辞書内の単語の再読み込み

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(47,myModulePath+"ReplaceWords.txt");	String	辞書ファイル名
setDataString(0,MyMethod());				String	辞書内の単語列（改行で結合）

     ・クラス情報：
public class WordReplace extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：
displayOtherModule(5,991);
別パネル内で，可視化ツール ファイル(FileDisplay(ID=5)) が使われている場合，
ファイル ReplaceWords.txtの単語を更新保存し，再度ハイライト表示を行う．
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
