�t�H�[�J�X���𗘗p����c�[�����t�H�[�J�X���Ă�����(���e)��\������
�A������(FocusDisplay(ID=1016))


���ݒ��ڂ��Ă���i�}�E�X�ŐڐG�A�N���b�N�A�I�������j���Ƃ��Ă�
�t�H�[�J�X���i���̃��W���[���Ńt�H�[�J�X����Ă�����j��\������D
�c�[�������́A���C���A�T�u�A�L�[���[�h�A���C���A�T�u�A�Z�O�����g�A���C���A�T�u�A���̃G���A���N���b�N���邱�ƂŁA
�Ή�����t�H�[�J�X���ɕ\�����e��ύX�ł���
���c�[�����t�H�[�J�X�̋@�\���������Ă���ꍇ�̂ݕ\�������

[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  FocusDisplay

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 4501:	�e�L�X�g����
case 4502:	�e�L�X�g����
case 0:		�e�L�X�g����
case 11:	���C���L�[���[�h	�ɕ\�����[�h�ύX
case 12:	�T�u�L�[���[�h	�ɕ\�����[�h�ύX
case 13:	�L�[���[�h�W��	�ɕ\�����[�h�ύX
case 21:	���C���Z�O�����g	�ɕ\�����[�h�ύX
case 22:	�T�u�Z�O�����g	�ɕ\�����[�h�ύX
case 23:	�Z�O�����g�W��	�ɕ\�����[�h�ύX
case 31:	���C����	�ɕ\�����[�h�ύX
case 32:	�T�u��	�ɕ\�����[�h�ύX
case 33:	���W��	�ɕ\�����[�h�ύX

     �E�������W���[������󂯎�����̓f�[�^�F�Ȃ�
     �E�N���X���F
public class FocusDisplay extends VisualizationModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.focusKeywords[i])
if(text.focus.focusSegments[i])
if(text.focus.focusSentences[i])
case 0:if(text.focus.mainFocusKeyword >= 0)
case 1: if(text.focus.subFocusKeyword >= 0)
case 0:if(text.focus.mainFocusSegment >= 0)
case 1: if(text.focus.subFocusSegment >= 0)
case 0:if(text.focus.mainFocusSentence >= 0)
case 1: if(text.focus.subFocusSentence >= 0)
�t�H�[�J�X���Ƃ��āA
�L�[���[�h�W���C�Z�O�����g�W���C���W���C���C���L�[���[�h�C�T�u�L�[���[�h�C���C���Z�O�����g�C�T�u�Z�O�����g�C���C�����C�T�u��
���`�F�b�N����

     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
