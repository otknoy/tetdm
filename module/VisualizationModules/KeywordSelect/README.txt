�t�H�[�J�X�L�[���[�h��I���ł���`�F�b�N�{�b�N�X��\��
�L�[���[�h�I��(KeywordSelect(ID=1102))

�`�F�b�N�{�b�N�X�ɂ��C�t�H�[�J�X�����Ă�L�[���[�h�̏W���𖾎��I�ɑI���ł��܂��D
�`�F�b�N�{�b�N�X���`�F�b�N�����ƁC
�I�����ꂽ�L�[���[�h���t�H�[�J�X�L�[���[�h�Ƃ��ď�񂪏㏑������C
�A���ΏۂƂȂ��Ă��鏈���c�[���C�Ȃ�тɉ����c�[���̏������Ď��s���܂��D


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  KeywordSelect

text.focus.keywords[] �̒l�� true/false �ɃZ�b�g���܂�

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0:		�`�F�b�N�{�b�N�X�̍쐬

     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int dataID, int data)
case 77:	�`�F�b�N�{�b�N�X�W���̐�

public void setData(int dataID, double data[])
default:	(dataID-10)�Ԗڂ̃`�F�b�N�{�b�N�X�W���̂��߂̃L�[���[�h�̕]���l�W���@�idataID��10����20�j

public void setData(int dataID, String data)
default:	(dataID-10)�Ԗڂ̃`�F�b�N�{�b�N�X�W���̃��x��

     �E�N���X���F
public class KeywordSelect extends VisualizationModule implements ItemListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.focusKeywords[i])
�`�F�b�N����Ă���L�[���[�h�W�����A�t�H�[�J�X�L�[���[�h�W���Ƃ��ăZ�b�g���܂�

     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F
repaintOthersByClick();
�t�H�[�J�X����Ă���L�[���[�h�ɂ��ƂÂ��āA���̉����c�[���ĕ`������s���܂�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F
executeOthersByClick();
�L�[���[�h���`�F�b�N����ƁA���̏����c�[���̏������Ď��s���܂�

     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
