段落の並び替えを行えます（推奨並び表示機能付き）
段落並び替え(ParagraphChange(ID=18))


[使い方を記入してください]
段落のテキストを上下にドラッグすることで、段落の順番を入れ替えられます。

段落番号の右の矢印の向きは、以下を表しています。
上向き：推奨する段落の位置がより上にある
下向き：推奨する段落の位置がより下にある
また、矢印の数が多いほど、その強さを表しています。

下部の「推奨」ボタンを押すと、推奨する並びに自動的に並び替えます。
＃推奨する並びは、組み合わせる処理ツールによって与えられます。
＃処理ツールからの推奨する並びが与えられていない場合は、もとの段落の順番が推奨する並びとなります。
下部の「並び替え保存」ボタンを押すと、並び替えの結果を保存します。

[作者とライセンス情報]

・作者：広島市立大学システムインタフェース研究室
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  ParagraphChange

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:
case 1:並び替えの結果を保存
case 2:推奨する並びに自動的に並び替え

     ・処理モジュールから受け取れる入力データ：
public boolean setData(int dataID, int data[])
case 0:

推奨する並びの段落番号の配列

     ・クラス情報：
public class ParagraphChange extends VisualizationModule implements MouseMotionListener, MouseListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
