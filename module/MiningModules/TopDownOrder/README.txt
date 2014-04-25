文章がトップダウンな構成になる段落の並び順を計算します
トップダウン段落順序(TopDownOrder(ID=24))

・対応する可視化ツール：
段落並び替え(ParagraphChange(ID=18))


「パネルセット」ボタンで、段落並び替え(ParagraphChange(ID=18))との組み合わせにセットします。


[使い方を記入してください]
特に操作は必要ありません。


[作者とライセンス情報]

・作者：広島市立大学システムインタフェース研究室
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  TopDownOrder

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0: calculateAssist();
トップダウンな段落の並び順を計算します。
複数の段落と関連があり、複数の段落をまとめられる可能性のある段落、がより最初に
他の段落との関連が低く、細かい内容が書かれている段落、が後になるように並び順を計算します。

     ・出力データの説明：
setDataIntegerArray(num);
計算した段落の並び順を送ります。

     ・クラス情報：
public class TopDownOrder extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
