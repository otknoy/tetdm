yahooが提供しているテキスト解析の機能を利用して文章のタグクラウド用のデータを生成する
キーフレーズ(YKPExtraction(ID=15))

・対応する可視化ツール：
タグクラウド(TagCloudView(ID=14))


「パネルセット」ボタンで、タグクラウド(TagCloudView(ID=14))との組み合わせにセットします。


・使い道：yahooAPIを利用して文章のタグクラウド用のデータを生成、Yahoo! Japanデベロッパーネットワークで公開されているキーフレーズ抽出
（http://developer.yahoo.co.jp/webapi/jlp/keyphrase/v1/extract.html）により，タグクラウド用のデータを生成
     ・使い方：利用には，Yahoo! JapanのアプリケーションIDが必要です（http://help.yahoo.co.jp/help/jp/developer/developer-06.html）．
取得したアプリケーションIDを記載したファイルをファイル名「yahoo_appid」として module/YKPExtraction フォルダに置いてください．



[作者とライセンス情報]

・作者：高間康史
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  YKPExtraction

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:					タグクラウド用データを生成して送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataStringArray(0,words);		String[] 型	単語集合
setDataIntegerArray(1,vals);		int[] 型		値（頻度）リスト
setDataStringArray(2,phSentence);	String[] 型	文集合

     ・クラス情報：
public class YKPExtraction extends MiningModule

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
