�t�H�[�J�X����I���ł���`�F�b�N�{�b�N�X��\��
���I��(SegmentSelect(ID=1101))

�`�F�b�N�{�b�N�X�ɂ��C�t�H�[�J�X�����Ă镶�̏W���𖾎��I�ɑI���ł��܂��D
�`�F�b�N�{�b�N�X���`�F�b�N�����ƁC
�I�����ꂽ�����t�H�[�J�X���Ƃ��ď�񂪏㏑������܂��D

[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  SentenceSelect

text.focus.sentences[] �̒l�� true/false �ɃZ�b�g���܂�

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0:		�`�F�b�N�{�b�N�X�����������܂�

     �E�������W���[������󂯎�����̓f�[�^�F�Ȃ�

     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int dataID, int data)
case 77:	�`�F�b�N�{�b�N�X�W���̐�

public void setData(int dataID, double data[])
default:	(dataID-10)�Ԗڂ̃`�F�b�N�{�b�N�X�W���̂��߂̃L�[���[�h�̕]���l�W���@�idataID��10����20�j

public void setData(int dataID, String data)
default:	(dataID-10)�Ԗڂ̃`�F�b�N�{�b�N�X�W���̃��x��

     �E�N���X���F
public class SentenceSelect extends VisualizationModule implements ItemListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.focusSentences[i])
�I�����ꂽ�Z�O�����g���t�H�[�J�X���ɃZ�b�g���܂�

     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F
repaintOthersByClick();
�t�H�[�J�X����Ă���Z�O�����g�ɂ��ƂÂ��āA���̉����c�[���ĕ`������s���܂�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F
executeOthersByClick();
�Z�O�����g���`�F�b�N����ƁA���̏����c�[���̏������Ď��s���܂�

     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
