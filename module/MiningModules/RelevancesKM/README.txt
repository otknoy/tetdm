キーワード対と関連度からなるデータを作成する
単語間関連度(RelevancesKM(ID=10))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))
キーワードマップ(SimpleKeywordMap(ID=7))


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))キーワードマップ(SimpleKeywordMap(ID=7))との組み合わせにセットします。


[本モジュールの概要]
可視化モジュールSimpleKeywordMapが読み取り可能な、
キーワード対と関連度からなるデータを作成するモジュールです。
特定タスクを想定したものではなく、主にSimpleKeywordMapの動作確認用です。
本モジュールでは、関連度に含まれる属性は3種類定義していますが、
特定タスクを想定したマイニングモジュールを作成する際には、
タスクに応じた適切な数の属性を定義することができます。

[本モジュールの使い方]
詳しくは、以下をご覧ください。
http://krectmt3.sd.tmu.ac.jp/~kajinami/RelevancesKM.pdf


[作者とライセンス情報]

[著作権表示]
Copyright(C) 2011.12.9 Tomoki Kajinami

・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  RelevancesKM

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:
●●●●
     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,text);		String型		テキスト間の関連度データを文字列として作成
setDataString(99,text);		String型		テキスト間の関連度データを文字列として作成

     ・クラス情報：
public final class RelevancesKM extends MiningModule{

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし












