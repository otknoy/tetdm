実験レポート集合に点数をつける
スコア計算(MakeScore(ID=22))

・対応する可視化ツール：
スコアネットワーク(ScoreNetwork(ID=6))


「パネルセット」ボタンで、スコアネットワーク(ScoreNetwork(ID=6))との組み合わせにセットします。


     ・使い道：実験レポート集合に点数をつけます．レポートの主題に沿った単語と，独自の単語，意見による考察，が含まれていると点数が高くなります．
	また結果と考察が1:1の割合で含まれるほど点数が高くなります．
     ・使い方：キーワードとして，動詞，形容詞を含むように環境設定で，設定して下さい．特に動詞を含まない場合は，意見文を評価しません．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  MakeScore

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:					点数をつける

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataDoubleArray(55,score);		点数のデータを送信
setDataBoolean(10,false);		標準色で表示するか否かのフラグデータを送信

     ・クラス情報：
public class MakeScore extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
