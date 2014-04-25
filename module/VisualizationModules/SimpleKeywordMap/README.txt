キーワード対と関連度からなるデータを受け取り、関連度に応じて、キーワードを2次元平面上に配置する
キーワードマップ(SimpleKeywordMap(ID=7))


[本モジュールの概要]
キーワード対と関連度からなるデータを受け取り、
関連度に応じて、キーワードを2次元平面上に配置するモジュールです。
関連度に含まれる属性の重みを制御することにより、
多角的な視点からデータの分析が可能です。
データは、マイニングモジュールにより生成されます。


[本モジュールの使い方]
詳しくは、以下をご覧ください。
http://krectmt3.sd.tmu.ac.jp/~kajinami/SimpleKeywordMap.pdf

[作者とライセンス情報]

[著作権表示]
Copyright(C) 2012.1.31 Tomoki Kajinami

・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容に加えて、以下の「KM license」を適用します。

[KM license]
第1条：あなたは、ソースコードに含まれる「public void setVisualizationPanel()」メソッド内に記述されているフィールド「arrangementStopFlg」「threshold」「threshold2」「redLink」「blueLink」の値を、下記の「条件1」「条件2」を満たす限りにおいて、任意に変更可能である。
条件1：あなたは、ソースコードを変更した旨をソースコードに記述する必要なない。
条件2：あなたは、本モジュールの著作権を主張することはできない。


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  SimpleKeywordMap

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:			マップ表示

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, String output_text){
case 99://check a data		String型	関連度データ


     ・クラス情報：
public final class SimpleKeywordMap extends VisualizationModule implements Runnable{

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
