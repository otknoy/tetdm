文章の主題との関連を表す各キーワードのラベルデータを生成
川下りラベル(LabelData(ID=13))

・対応する可視化ツール：
川下り(FlowPanel(ID=12))


「パネルセット」ボタンで、川下り(FlowPanel(ID=12))との組み合わせにセットします。


文章の主題との関連を表す各キーワードのラベルデータを生成します．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．
・既発表文献：
	- 砂山渡・谷川信弘：テキストの主題に基づく一貫性評価と結論抽出への応用, 日本知能情報ファジィ学会誌, Vol.23, No.5, pp.727 -- 738, (2011).


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  LabelData

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 4502:				川下り用ラベルデータの生成，キーワードの各セグメントにおけるラベルを作成して送信
case 0:					川下り用ラベルデータの生成，キーワードの各セグメントにおけるラベルを作成して送信
case 1://case 10100:			各キーワードが主題関連語かどうかのチェックリストを生成して送信
case 2://case 10200:			主題関連語のIDリストを生成して送信
case 11:				結論文としての評価値リストを送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataIntegerArray(1,subject_check);/////	int[] 型		各キーワードが主題関連語かどうかのチェックリスト
setDataIntegerArray(2,subject_idlist);/////	int[] 型		主題関連語のIDリスト
setDataDoubleArray(11,makeConclusionValue());	double[] 型	結論文としての評価値リスト
setDataIntegerArray(1355,level);		int[] 型		キーワードの最終レベル
setDataIntegerArray(1355000+i,label[i]);	セグメント番号(0から)+1355000:	int[] 型	各セグメントにおけるキーワードのラベル番号

     ・クラス情報：
public class LabelData extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：
focusClickExecute = true;
展望台（要約）(Panoramic ID=11)で、フォーカスキーワード集合（主題単語集合）が代わったときに連動実行
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：
sbase_idlist = getDataIntegerArray(11, 77);
主題単語リストを展望台（要約）(Panoramic ID=11)から取得
