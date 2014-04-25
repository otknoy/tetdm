テキストの主役，主題，最重要文，一貫性，主語含有率，不適切表現数，長文数を表示
テキスト分析(TextInfo(ID=19))

・対応する可視化ツール：
Htmlテキスト(TextDisplayHtml(ID=3))


テキストに関する各種情報を表示します。
「パネルセット」ボタンを押すと、各データを生成しているツール群に表示が切り替わります。
Htmlテキスト(TextDisplayHtml(ID=3))
要約（展望台）(Panoramic(ID=11))
光と影(MakeLight(ID=12))
主語抽出(SubjectExtraction(ID=5))
川下りラベル(LabelData(ID=13))
単語チェック(PaperCheck(ID=2))
長文チェック(LongSentenceCheck(ID=16))


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  TextInfo

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 2:		５つのモジュールからデータを取得して表示する（データ再生成なし）
case 4502:	５つのモジュールからデータを取得して表示する（データ再生成なし）
case 0:		５つのモジュールからデータを取得して表示する（データ再生成あり）
case 1:		可視化モジュールの表示を更新

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,checkedText);	String 型	取得したデータを総合したテキスト(html)
setDataString(0,checkedText);	String 型	取得したデータを総合したテキスト(html)

     ・クラス情報：
public class TextInfo extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：
focusClickExecute = true;	
フォーカスクリック時にデータを再取得
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：
subjectList = getDataIntegerArrayNew(11,77);	要約（展望台）(Panoramic(ID=11))
keySentence = getDataStringNew(11,1);		要約（展望台）(Panoramic(ID=11))
scoreDist = getDataDoubleArrayNew(12,2);	光と影(MakeLight(ID=12))
relationIDs = getDataIntegerArrayNew(13,2);	川下りラベル(LabelData(ID=13))
noiseNumber = getDataIntegerNew(2,1);		単語チェック(PaperCheck(ID=2))
veryLongSentenceNumber = getDataIntegerNew(16,1);	長文チェック(LongSentenceCheck(ID=16))
