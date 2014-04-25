ファイル「checklist」で指定された単語をハイライトする
単語チェック(PaperCheck(ID=2))

・対応する可視化ツール：
Htmlテキスト(TextDisplayHtml(ID=3))
ファイル(FileDisplay(ID=5))


「パネルセット」ボタンで、Htmlテキスト(TextDisplayHtml(ID=3))ファイル(FileDisplay(ID=5))との組み合わせにセットします。

Htmlテキスト(TextDisplayHtml(ID=3))との組み合わせで、
文章内の，ファイル checklist 内で指定された単語をハイライトします．

ボタン：
「checkresult(checkresult)」：
別パネル内で，可視化ツール ファイル(FileDisplay(ID=5)) が使われている場合，
ファイル checklistの単語を更新保存し，再度ハイライト表示を行う．
同時に、処理ツール テキスト分析(TextInfo(ID=19))が使われている場合，その表示内容を更新します．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  PaperCheck

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:	単語のチェックを実行する
case 1:	checklist の再読み込みを行い，単語のチェックを実行する

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(47,myModulePath+"checklist");//For File Edit	String型	ファイル checklist の絶対パス
setDataString(0,checkedText);		String型	単語のチェック結果のhtmlテキスト
setDataInteger(1,(100-score)/5);	int型	単語のチェック結果のスコア
setDataString(0,checkedText);		String型	単語のチェック結果のhtmlテキスト
setDataInteger(1,(100-score)/5);	int型	単語のチェック結果のスコア

     ・クラス情報：
public class PaperCheck extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：
displayOtherModule(5,991);
可視化ツール ファイル(FileDisplay(ID=5)) で，ファイル checklist 内の単語を表示させます
     ・オプションによる処理連動(連動要請)：
executeOtherModule(19,2);
executeOtherModule(19,1);
テキスト分析(TextInfo(ID=19))の表示を更新する（データ取得+表示）
     ・データ取得による処理連動(連動要請)：なし
