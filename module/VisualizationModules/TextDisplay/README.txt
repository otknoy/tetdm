�e�L�X�g����\������
�e�L�X�g(TextDisplay(ID=1))


�e�L�X�g����\�����܂�


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  TextDisplay

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0:		�e�L�X�g�\��
case 1:		�e�L�X�g�t�@�C���ۑ�
case 9:		�t�H���g�T�C�Y�k��
case 10:	�t�H���g�T�C�Y�g��
case 100:	���̓e�L�X�g�̕\��

     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int dataID, String t)
default:	String�^		�\������e�L�X�g

public void setData(int dataID, String t[])
case 1:		String[]�^	�\������e�L�X�g�C���s��؂�Ō�������ĕ\�������
default:	String[]�^	�\������e�L�X�g�C�X�y�[�X��؂�Ō�������ĕ\�������

     �E�N���X���F
public class TextDisplay extends VisualizationModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
