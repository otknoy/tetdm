�t�H�[�J�X�Z�O�����g��I���ł���`�F�b�N�{�b�N�X��\��
�Z�O�����g�I��(SegmentSelect(ID=1100))

�`�F�b�N�{�b�N�X�ɂ��C�t�H�[�J�X�����Ă�Z�O�����g�i�e�L�X�gor�i���j�̏W���𖾎��I�ɑI���ł��܂��D
�`�F�b�N�{�b�N�X���`�F�b�N�����ƁC
�I�����ꂽ�Z�O�����g���t�H�[�J�X�Z�O�����g�Ƃ��ď�񂪏㏑������܂��D

�u����-1�ɃZ�b�g�v�{�^���ŁC�t�H�[�J�X�Z�O�����g����Ȃ镔���e�L�X�g�f�[�^���쐬���C�u����-1�v�ɕۑ����܂��D
�u����-2�ɃZ�b�g�v�{�^���ŁC�t�H�[�J�X�Z�O�����g����Ȃ镔���e�L�X�g�f�[�^���쐬���C�u����-2�v�ɕۑ����܂��D

[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  SegmentSelect

text.focus.segments[] �̒l�� true/false �ɃZ�b�g���܂�

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
public class SegmentSelect extends VisualizationModule implements ItemListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.focusSegments[i])
�I�����ꂽ�Z�O�����g���t�H�[�J�X���ɃZ�b�g���܂�

     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F
repaintOthersByClick();
�t�H�[�J�X����Ă���Z�O�����g�ɂ��ƂÂ��āA���̉����c�[���ĕ`������s���܂�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F
executeOthersByClick();
�Z�O�����g���`�F�b�N����ƁA���̏����c�[���̏������Ď��s���܂�

     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
