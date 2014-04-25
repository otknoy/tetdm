処理ツールの展望台が出力するキーワードを富士山画像の上に配置
キーワード(展望台)(XDrawDisplay(ID=8))


処理ツール 展望台（要約）(Panoramic ID=11)で抽出された3種類のキーワード（観点語(オレンジ)、背景語(青)、特徴語(緑)）を富士山画像（fuji.jpg）の上に配置して表示します．
パネル内でクリックした際に、赤線より上に配置されたキーワード集合を観点（フォーカスキーワード集合）としてセットします。
パネル内でクリックした際に、各種処理ツールが再処理を実行します。主に処理ツール 展望台（要約）(Panoramic ID=11)への観点語入力に用いられます。
要約生成の際に観点の効果を高める為には、処理ツール 展望台（要約）(Panoramic ID=11) で 「背景語を利用するボタン(Background)をOFF」にしておくことが望ましいです．
各キーワードを表すノードは、マウスでドラッグ可能です。
背景をドラッグすることにより、キーワードの一括移動が可能です。


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  XDrawDisplay

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		指定されたキーワードを富士山画像の上に配置

     ・処理モジュールから受け取れる入力データ：
public void setData(int dataID, int data[])
case 50:	int[] 型	主題キーワードのID
case 51:	int[] 型	背景キーワードのID
case 52:	int[] 型	特徴キーワードのID
case 77:	int[] 型	観点キーワードのID

     ・クラス情報：
public class XDrawDisplay extends VisualizationModule implements MouseListener, MouseMotionListener

     ・フォーカス型変数の利用：
text.focus.focusKeywords[map.node[i].id] = true;
//text.focus.mainFocusKeyword = map.node[touchNumber].id;
マウスクリック時に、フォーカスキーワード集合をセット

     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：
executeAllByClick();
マウスクリック時に指定ラインより上にある単語をフォーカスキーワード集合として，すべての処理モジュールを再実行
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし


[本モジュールの使い方] 


利用者向け情報  
-----

     ・対応する可視化／処理ツール：
	- マイニングモジュール：Panoramic
     ・必要な外部データファイル：fuji.jpg
     ・作者情報：
     ・既発表文献：


モジュール開発者向け情報
-----
	

     ・既存の可視化モジュールの継承の有無：なし
     ・フォーカス情報による可視化連動の有無：あり，
     ・処理連動の有無：なし
 


