キーワード間，セグメント間，文間の関連度に応じてリンクを生成して表示
ばねモデル(DrawNodes(ID=1017))


     ・使い道：キーワード間，セグメント間，文間の関連度調査．キーワード／セグメント／文 をノードとして，ノード間の関連度（cos類似度）に応じてリンクを生成して表示します
     ・使い方：
	- ばね Springボタン：ばねモデル実行開始および停止（下記４つのボタンのいずれかに切り替えたときは、このボタンのOn,Offを行ってください）
	- 単語間(段落) Keysegボタン：キーワードの関連度をセグメント内の共起(keywordRelationbySegment)により求めて表示します
	- 単語間(文) Keysenボタン：キーワードの関連度を文内の共起(keywordRelationBySentence)により求めて表示します
	- 段落間(単語) Segkeyボタン：セグメントの関連度をキーワードの共起(segmentRelation)により求めて表示します
	- 文間(単語) Senkeyボタン：文の関連度をキーワードの共起(sentenceRelation)により求めて表示します
	- しきい値は，リンク数がリンクを少なくとも１本もつノード数の半分を超えない値に自動設定されます．


[作者とライセンス情報]

・作者：砂山渡
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for VISUALIZATION MODULE]  :  DrawNodes

     ・可視化内容の説明（メソッドdisplayOperations内のcase文の説明）：
case 0:		関連度可視化

     ・処理モジュールから受け取れる入力データ：なし

     ・クラス情報：
public class DrawNodes extends VisualizationModule implements ActionListener, MouseListener, MouseMotionListener, Runnable

     ・フォーカス型変数の利用：なし
     ・フォーカス情報による可視化連動（連動要請）：なし
     ・フォーカス情報による処理連動（連動要請）：なし
     ・オプションによる可視化連動（連動要請）：なし
     ・オプションによる処理連動（連動要請）：なし
