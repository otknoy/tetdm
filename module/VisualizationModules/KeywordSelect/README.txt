フォーカスキーワードを選択できるチェックボックスを表示
キーワード選択(KeywordSelect(ID=1102))

チェックボックスにより，フォーカスをあてるキーワードの集合を明示的に選択できます．
チェックボックスがチェックされると，
選択されたキーワードがフォーカスキーワードとして情報が上書きされ，
連動対象となっている処理ツール，ならびに可視化ツールの処理を再実行します．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  KeywordSelect

text.focus.keywords[] の値を true/false にセットします

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		チェックボックスの作成

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, int data)
case 77:	チェックボックス集合の数

public void setData(int dataID, double data[])
default:	(dataID-10)番目のチェックボックス集合のためのキーワードの評価値集合　（dataIDは10から20）

public void setData(int dataID, String data)
default:	(dataID-10)番目のチェックボックス集合のラベル

     ・クラス情報：
public class KeywordSelect extends VisualizationModule implements ItemListener

     ・フォーカス型変数の利用：
if(text.focus.focusKeywords[i])
チェックされているキーワード集合を、フォーカスキーワード集合としてセットします

     ・フォーカス情報による可視化連動（連動要請）：
repaintOthersByClick();
フォーカスされているキーワードにもとづいて、他の可視化ツール再描画を実行します
     ・フォーカス情報による処理連動（連動要請）：
executeOthersByClick();
キーワードをチェックすると、他の処理ツールの処理を再実行します

     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
