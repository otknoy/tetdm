処理ツールで作成されたデータを用いてレーダーチャートを表示する
レーダーチャート(RadarChart(ID=16))


・使い道：マイニングモジュールで作成されたデータを用いてレーダーチャートを表示します。
・使い方：小数点以下2桁までの値表示のON/OFF、3段階の文字サイズ変更、３色の色変更、色の透明度の変更を、下部のパネルで行えます。

・対応する処理ツール：setDataString(int id,String str)で出力され、なおかつstrの内容が下記の形式の処理ツールと連携できます。
サンプルデータを出力する処理モジュールを用意しています（RadarChartTest）。
strの内容：
"要素（軸）の名前,値（0～1の実数を文字列型に変換したもの）\n"が３つ以上、必要な軸の数の分だけ入ります。

[作者とライセンス情報]

・作者：梶並知記
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  RadarChart

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		レーダーチャートの生成と表示

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, String textData){
default:	idは0，strは以下の形式です。サンプルデータを出力する処理モジュール（RadarChartTest）を参考にしてください。
strの内容：
"要素（軸）の名前,値（0～1の実数を文字列型に変換したもの）\n"が３つ以上、必要な軸の数の分だけ入ります。
軸の数によってエラーを返す処理を行っていませんので、３軸以上かつ現実的にレーダーチャートが読める範囲で、データを用意してください。
サンプルデータを出力する処理モジュール（RadarChartTest）では、最大８軸にしています。
strの例（3つの軸をもつレーダーチャートができます）：
明るさ,0.64\n
暖かさ,0.4534\n
静かさ,0.835\n

     ・クラス情報：
public class RadarChart extends VisualizationModule implements ActionListener,ChangeListener{

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
