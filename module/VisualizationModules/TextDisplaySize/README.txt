�������\������Dx���W�ɉ������t�H���g�T�C�Y�ɕύX�ł���
�e�L�X�g(�T�C�Y)(TextDisplaySize(ID=11))


     �E�g�����F������̕\���i�t�H���g�T�C�Y�ύX�\�j
     �E�g�����F������Smaller size, Larger size�Ə����ꂽ�p�l����ŃN���b�N����ƁC
	x���W�i�����j�ɉ������t�H���g�T�C�Y�ɕύX�\


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  TextDisplaySize

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F�i���j�p������TextDisplay�̓��e
case 0:		�e�L�X�g�\��
case 1:		�e�L�X�g�t�@�C���ۑ�
case 9:		�t�H���g�T�C�Y�k��
case 10:	�t�H���g�T�C�Y�g��
case 100:	���̓e�L�X�g�̕\��

     �E�������W���[������󂯎�����̓f�[�^�F�i���j�p������TextDisplay�̓��e
public void setData(int dataID, String t)
default:	String�^		�\������e�L�X�g

public void setData(int dataID, String t[])
case 1:		String[]�^	�\������e�L�X�g�C���s��؂�Ō�������ĕ\�������
default:	String[]�^	�\������e�L�X�g�C�X�y�[�X��؂�Ō�������ĕ\�������

     �E�N���X���F
public class TextDisplaySize extends TextDisplay
�e�L�X�g�iTextDisplay(ID=1)�j�̓��e���p��
     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�



[�{���W���[���̎g����] 


���p�Ҍ������  
-----

     �E�Ή���������^�����c�[���F���ɒ�܂������̂͂Ȃ�
     �E�K�v�ȊO���f�[�^�t�@�C���F�Ȃ�
     �E��ҏ��F
     �E�����\�����F


���W���[���J���Ҍ������
-----
     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
	��TextDisplay�̓����̃��\�b�h�𗘗p��
		0:	�e�L�X�g�\��
		1:	�e�L�X�g�t�@�C���ۑ�
		9:	�t�H���g�T�C�Y�k��
		10:	�t�H���g�T�C�Y�g��,
		100:	���̓e�L�X�g�̕\��

     �E�������W���[������󂯎���f�[�^�i���\�b�hsetData�̈����Ɛ����j�F
	��TextDisplay�̓����̃��\�b�h�𗘗p��
		default:	String�^	�\������e�L�X�g
		default:	String[]�^		�\������e�L�X�g�C�X�y�[�X��؂�Ō��������
		1:	String[]�^		�\������e�L�X�g�C���s��؂�Ō��������

     �E�����̉������W���[���̌p���̗L���FTextDisplay
     �E�t�H�[�J�X���ɂ������A���̗L���F�Ȃ�
     �E�����A���̗L���F�Ȃ�
 


