テキスト中の主語に関する情報（主語，主語がない文）を表示させる
主語抽出(SubjectExtraction(ID=5))

・対応する可視化ツール：
テキスト(カラー)(TextDisplayColor(ID=2))


「パネルセット」ボタンで、テキスト(カラー)(TextDisplayColor(ID=2))との組み合わせにセットします。


     ・使い道：テキスト中の主語の情報を表示させる
     ・使い方：
	（１）「主語なし文」ボタン：主語のない文をハイライトする（ための情報を作成）
	（２）「全主語」ボタン：全ての主語をハイライトする（ための情報を作成）
	（３）「主語（キーワードのみ）」ボタン：キーワードに含まれる主語をハイライトする（ための情報を作成）
	（４）「主語（キーワード以外）」ボタン：キーワードに含まれない主語をハイライトする（ための情報を作成）


[作者とライセンス情報]

・作者：TETDM開発チーム
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  SubjectExtraction

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:		主語のない文をハイライトするデータを生成
case 1:		文と単語をハイライトするデータ構造（配列）の初期化
case 11:	主語のない文をハイライトするデータを生成
case 12:	全ての主語をハイライトするデータを生成
case 13:	キーワードに含まれる主語（キーワードのみ）をハイライトするデータを生成
case 14:	キーワードに含まれる主語（キーワード以外）をハイライトするデータを生成

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataIntegerArray(23,sentenceHighlightNumber);	文をハイライトする明るさデータ
setDataIntegerArray(23,sentenceHighlightNumber);	文をハイライトする明るさデータ
setDataIntegerArray(11,wordHighlightNumber);		単語をハイライトする明るさデータ
setDataIntegerArray(11,wordHighlightNumber);		単語をハイライトする明るさデータ
setDataIntegerArray(11,wordHighlightNumber);		単語をハイライトする明るさデータ

     ・クラス情報：
public class SubjectExtraction extends MiningModule implements ActionListener

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
