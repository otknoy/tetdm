�e�L�X�g�̕���P���w�i�F�t���ŕ\���ł���
�e�L�X�g(�J���[)(TextDisplayColor(ID=2))


�e�L�X�g��\�����܂��D
����P���w�i�F�t���ŕ\�����邱�Ƃ��ł��܂��D


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  TextDisplayColor

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0:		�\������e�L�X�g�̊e���ɔw�i�F�����ĕ\��
case 11:	�\������e�L�X�g�̊e�P��ɔw�i�F�����ĕ\��
case 1:		�e�L�X�g�ۑ�
case 9:		�t�H���g�T�C�Y�k��
case 10:	�t�H���g�T�C�Y�g��
case 25:	�\�����̃e�L�X�g��ϐ��Ɏ擾�C������24�̓��e�����s
case 24:	�\������e�L�X�g�̓���̒P��ɔw�i�F��t���ĕ\��

     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int dataID, int data[])
case 1:		int[]�^	�e�L�X�g�̊e���̐F���i0����20�̐��l�Ŏw��)
case 23:	int[]�^	�e�L�X�g�̊e���̐F���i0����20�̐��l�Ŏw��)
case 11:	int[]�^	�e�L�X�g�̊e�P��̐F���i0����20�̐��l�Ŏw��)
case 12:	int[]�^	�e�L�X�g�̊e�P��̐F���i0����20�̐��l�Ŏw��)
case 13:	int[]�^	�e�L�X�g�̊e�P��̐F���i0����20�̐��l�Ŏw��)

public void setData(int dataID, String data)
case 1:		String�^	�\������e�L�X�g
case 24:	String�^	�F��t����P��
case 25:	String�^	�F��t����P��

     �E�N���X���F
public class TextDisplayColor extends VisualizationModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
