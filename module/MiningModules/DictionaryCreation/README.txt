形態素解析器Igo用の辞書に単語を追加して再構築します[このモジュールの説明を20から50文字程度で記入して下さい]
辞書構築(DictionaryCreation(ID=9998))

・対応する可視化ツール：
ファイル(FileDisplay(ID=5))


「パネルセット」ボタンで、ファイル(FileDisplay(ID=5))との組み合わせにセットします。
このパネルより左のパネルで，別に可視化モジュール「ファイル」がセットされていると正しく動作しません．


[使い方を記入してください]
「保存＆辞書構築」ボタン：追加する単語を保存して辞書を再構築します．
「辞書初期化」ボタン：追加対象となっている単語を除いて辞書を再構築します．

追加対象とする単語は，可視化モジュール「ファイル(FileDisplay(ID=5))」のテキストエリア内に，
単語とその読みを，カンマ区切りで一行で記述して与えて下さい．
（テキストエリア内の，一行目の説明文は消さないで下さい）
ex)
浦島太郎,ウラシマタロウ
乙姫様,オトヒメサマ


[作者とライセンス情報]

・作者：TETDM開発メンバー
・本モジュールの利用許諾について，TETDMサイト(http://tetdm.jp/)内のTETDM利用許諾書（http://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM統合環境&openfile=license.txt）の内容をそのまま適用します．


モジュール開発者向け情報
-----
[README.txt for MINING MODULE]  :  DictionaryCreation

     ・処理内容の説明（メソッドminingOperations内のcase文の説明）：
case 0:
	辞書に追加する単語のファイル「AddWords.txt」の名前を，可視化モジュールに渡します．
case 1:
	ファイル「AddWords.txt」を読み込みます．
case 2:
	ファイル「AddWords.csv」を作成して，辞書を再構築します．
case 3:
	辞書を初期化します．
     ・出力データの説明：
resetData();//For FileDisplay
setDataString(10000,myModulePath+"AddWords.txt");
追加する単語のリストを保存するファイル名

     ・クラス情報：
public class DictionaryCreation extends MiningModule implements ActionListener
     ・フォーカス型変数の利用：なし
     ・フォーカス情報による処理連動フラグ(連動実行)：なし
     ・オプションによる可視化連動(連動要請)：なし
     ・オプションによる処理連動(連動要請)：なし
     ・データ取得による処理連動(連動要請)：なし
