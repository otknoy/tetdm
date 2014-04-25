テキスト内の，各文の長さをチェックする
長文チェック(LongSentenceCheck(ID=16))

・対応する可視化ツール：
Htmlテキスト(TextDisplayHtml(ID=3))


「パネルセット」ボタンで、Htmlテキスト(TextDisplayHtml(ID=3))との組み合わせにセットします。


・使い道：入力テキスト内の長文をチェックします。
・使い方：長い文（51字以上）だとその文字数が紫色、とても長い文（100字以上）だとその文字数が赤色で表示されます。
インタラクティブな操作は必要ありません。
注意：場合によっては見た目とは1、2文字異なる文字数が表示されます。


[作者とライセンス情報]

・作者：梶並知記
[著作権表示]
Copyright(C) 2011.12.13 Tomoki Kajinami

本モジュールの利用許諾について、TETDM利用許諾書（http://www.sys.info.hiroshima-cu.ac.jp/people/sunayama/future/licence.txt）の内容に加えて、以下の「LSC licence」を適用します。

[LSC licence]
第1条：あなたは、ソースコードに含まれる「public void selectedFirstTime()」メソッド内に記述されているフィールド「longThreshold」「veryLongThreshold」「longColor」「veryLongColor」の値を、下記の「条件1」「条件2」を満たす限りにおいて、任意に変更可能である。
条件1：あなたは、ソースコードを変更した旨をソースコードに記述する必要なない。
条件2：あなたは、本モジュールの著作権を主張することはできない。


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  LongSentenceCheck

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:				解析結果データ（html）を生成して送信

     ・出力データの説明（引数の型情報を追加して下さい）：
setDataString(0,MyMethod());			String 型	解析結果データ（html）
setDataInteger(1,veryLongSentenceNumber);	int 型		とても長い文の数

     ・クラス情報：
public final class LongSentenceCheck extends MiningModule{

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
