�t�H�[�J�X����Ă�������n�C���C�g����
�t�H�[�J�X�`�F�b�N(FocusCheck(ID=18))

�E�Ή���������c�[���F
Html�e�L�X�g(TextDisplayHtml(ID=3))


�u�p�l���Z�b�g�v�{�^���ŁAHtml�e�L�X�g(TextDisplayHtml(ID=3))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�\������t�H�[�J�X����ύX���܂�
�{�^���F
�u�L�[���[�h�v�F���C������уT�u�̃t�H�[�J�X�L�[���[�h��\�����܂�
�u�L�[�W���v�F�W���Ƃ��ăt�H�[�J�X����Ă���L�[���[�h��\�����܂�
�u���v�F���C������уT�u�̃t�H�[�J�X����\�����܂�
�u���W���v�F�W���Ƃ��ăt�H�[�J�X����Ă��镶��\�����܂�
�u�Z�O�����g�v�F���C������уT�u�̃t�H�[�J�X�Z�O�����g�i�i��or�e�L�X�g�j��\�����܂�
�u�Z�O�W���v�F�W���Ƃ��ăt�H�[�J�X����Ă���Z�O�����g�i�i��or�e�L�X�g�j��\�����܂�

[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  FocusCheck

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:			�t�H�[�J�X����Ă�������n�C���C�g����Html�𐶐����đ��M
case 4501:		�t�H�[�J�X����Ă�������n�C���C�g����Html�𐶐����đ��M
case 4502:		�t�H�[�J�X����Ă�������n�C���C�g����Html�𐶐����đ��M
case 1:			�\�����e�̕ύX
case 2:			�\�����e�̕ύX
case 3:			�\�����e�̕ύX
case 4:			�\�����e�̕ύX
case 5:			�\�����e�̕ύX
case 6:			�\�����e�̕ύX

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(0,checkedText);		String �^	�t�H�[�J�X����Ă�������n�C���C�g����Html
setDataString(0,checkedText);		String �^	�t�H�[�J�X����Ă�������n�C���C�g����Html

     �E�N���X���F
public class FocusCheck extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.focusKeywords[text.segment[i].sentence[j].wordIDList[k]])
if(text.focus.focusSegments[i])
if(text.focus.focusSentences[ text.segment[i].positionOfFirstSentence + j ])
mainK = text.focus.mainFocusKeyword;
subK = text.focus.subFocusKeyword;
mainK = text.focus.mainFocusSegment;
subK = text.focus.subFocusSegment;
mainK = text.focus.mainFocusSentence;
subK = text.focus.subFocusSentence;
�Z�O�����g�C���C�L�[���[�h�́C���C���C�T�u�C�W��

     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F
focusTouchExecute = true;focusClickExecute = true;
�t�H�[�J�X��񂪕ύX���ꂽ�Ƃ��ɁA���̓��e��\������
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
