��̒i���Ԃ̒P��̏o���֌W�𖾎�����
�֘A�`�F�b�N(RelationCheck(ID=17))

�E�Ή���������c�[���F
Html�e�L�X�g(TextDisplayHtml(ID=3))


�u�p�l���Z�b�g�v�{�^���ŁA
�Z�O�����g�Ǝ���(DrawOriginal(ID=1013))
�A������(FocusDisplay(ID=1016))
�A������(FocusDisplay(ID=1016))
Html�e�L�X�g(TextDisplayHtml(ID=3))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B

Html�e�L�X�g(TextDisplayHtml(ID=3))�Ƃ̑g�ݍ��킹�ŁA
�Q�̃e�L�X�g�i�Z�O�����g�Ǝ���(DrawOriginal(ID=1013))��ŐG�����m�[�h���\���e�L�X�g�A����т��̃e�L�X�g�ƍł��֘A������e�L�X�g�j�Ԃ̒P��̏o���֌W�𖾎�

�A������(FocusDisplay(ID=1016))�Ƃ̑g�ݍ��킹�ŁA
�Z�O�����g�Ǝ���(DrawOriginal(ID=1013))��ŐG�����m�[�h���\���e�L�X�g�A�i���C�� �Z�O�����g�j
�܂��́A���̃e�L�X�g�ƍł��֘A������e�L�X�g �̓��e��\���i�T�u �Z�O�����g�j



[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  RelationCheck

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 4501:		�e�L�X�g�Ԃ̊֘A���`�F�b�N�������ʂ̕�����𑗐M
case 0:			�e�L�X�g�Ԃ̊֘A���`�F�b�N�������ʂ̕�����𑗐M
case 1:			�e�L�X�g�Ԃ̊֘A���`�F�b�N�������ʂ̕�����𑗐M

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(0,checkedText);			String �^	�e�L�X�g�Ԃ̊֘A���`�F�b�N�������ʂ̕�����
setDataString(0,"<HTML><BODY>"+checkedText+"</BODY></HTML>");		String �^	�e�L�X�g�Ԃ̊֘A���`�F�b�N�������ʂ̕�����

     �E�N���X���F
public class RelationCheck extends MiningModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.mainFocusSegment != -1 && text.focus.subFocusSegment != -1)
if(text.focus.mainFocusSegment != -1 && text.focus.subFocusSegment != -1)
mainFocusSegment �� subFocusSegment �ŕ\�����Z�O�����g�i�e�L�X�gor�i���j�Ԃ̊֘A�𒲂ׂ�

     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F
focusTouchExecute = true;
�Z�O�����g�Ǝ���(DrawOriginal(ID=1013))��ŐG�����m�[�h���\���e�L�X�g�A����т��̃e�L�X�g�ƍł��֘A������e�L�X�g�Ƃ̊֘A��\��
     �E�I�v�V�����ɂ������A��(�A���v��)�F
displayModule(1016, 22);
displayModuleFirst(1016, 21);
�u�Z�b�g�v�{�^���ɂ��p�l���Z�b�g��ɁA���C���A�T�u�t�H�[�J�X�Z�O�����g��\��
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F
executeModule(17,1);
�u�Z�b�g�v�{�^���ɂ��p�l���Z�b�g��ɁA�֘A�x�v�Z���������s
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
