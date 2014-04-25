可視化ツールでデータ受け取り時にエラーが出ないかの確認，ならびにデータ型コンバートによる表示の確認用
データ送信テスト(DataTest(ID=7777))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))
分布(ScoreDist(ID=4))
その他，setDataのcase文に，dataNumbersの値を超えるcase文を記述していない（dataIDの指定を前提としていない）可視化モジュール


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))分布(ScoreDist(ID=4))との組み合わせにセットします。


[使い方を記入してください]
各データ型のボタンを押すと，対応するデータ型のデータが，可視化ツールに送られます．
「データ数リセット」ボタンが押されている状態だと，可視化ツールの受け取りデータ数を0に初期化してからデータを送ります．


[作者とライセンス情報]

・作者：TETDM開発チーム
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  DataTest

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:
実行される処理はありません．

     ・出力データの説明：
resetData();
setDataBoolean(data);
resetData();
setDataInteger(data);
resetData();
setDataDouble(data);
resetData();
setDataString(data);
resetData();
setDataBooleanArray(data);
resetData();
setDataIntegerArray(data);
resetData();
setDataDoubleArray(data);
resetData();
setDataStringArray(data);
resetData();
setDataBooleanArray2(data);
resetData();
setDataIntegerArray2(data);
resetData();
setDataDoubleArray2(data);
送られるデータは，ボタンを参考にして下さい．


     ・クラス情報：
public class DataTest extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
