アノテーション(注釈)を付与すべき文を抽出する
アノテーション(AnnotationMining(ID=9))

・対応する可視化ツール：
テキスト(TextDisplay(ID=1))
テキスト(カラー)(TextDisplayColor(ID=2))


「パネルセット」ボタンで、テキスト(TextDisplay(ID=1))テキスト(カラー)(TextDisplayColor(ID=2))との組み合わせにセットします。

テキスト(カラー)(TextDisplayColor(ID=2))との組み合わせで、
     ・使い道：アノテーションを付与すべき文を抽出．
		入力されたテキストから，アノテーションを付与すべき文を抽出します．
		アノテーション付与が推奨される程度を３段階に分けており，強く推奨，中程度の推奨，余力があれば推奨とそれぞれ抽出します。
     ・使い方：抽出された文にアノテーションを付与することで、テキストの内容を深く理解することができます。


[作者とライセンス情報]

・作者：西原陽子
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．
・既発表文献：
	- 西原陽子, 伊藤彩, 大澤幸生, 意見の理解を支援するアノテーションシステム, マイニングツールの統合と活用＆情報編纂研究会, (2011.10)
	- 伊藤彩, 西原陽子, 大澤幸生, 書き手の意見理解促進のためのアノテーション付与推奨箇所抽出手法, 第25回人工知能学会全国大会, 1B2-NFC3-6 (2011.6)

モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  AnnotationMining

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:						各文のアノテーション付与の推奨の程度を送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,MyMethod());			String 型	アノテーションつき入力テキスト
setDataIntegerArray(1,annotationNumber);	int[] 型		各文のアノテーションの種類による可視化用の色番号

     ・クラス情報：
public class AnnotationMining extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
