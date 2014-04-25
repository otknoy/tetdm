段落数ベース，あるいは文数ベースで，テキストを二分割した部分テキストを作成します．
テキスト二分割(HalfText(ID=25))

・対応する可視化ツール：
キーワード比較(KeywordCompare(ID=21))


「パネルセット」ボタンで、キーワード比較(KeywordCompare(ID=21))との組み合わせにセットします。


[使い方を記入してください]
段落数ベース，あるいは文数ベースで，テキストを二分割した部分テキストを作成します．
作成した部分テキストは，段落ベースの場合，部分-1と部分-2に，
文ベースの場合，部分-3と部分-4に保存されます．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  HalfText

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:
特になし

     ・出力データの説明：
setDataIntegerArray(0,frequency);
setDataIntegerArray(1,frequency);
setDataIntegerArray(0,frequency);
setDataIntegerArray(1,frequency);

キーワードの頻度の配列，
段落データ(前半，後半)，文データ(前半，後半)


     ・クラス情報：
public class HalfText extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：
 text.focus.focusSegments[i] = true;
text.focus.focusSentences[i] = true;
段落数ベースの前半と後半の部分テキスト
文数ベースの前半と後半の部分テキスト
を作成

     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
