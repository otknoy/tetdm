�㉺2�ɕ����ꂽ�E�B���h�E���ꂼ��ɕ������\������
�f���A���e�L�X�g(DualTextDisplay(ID=15))


     �E�g�����F������̕\��
     �E�g�����F������̕\���C�㉺�e�L�X�g�G���A�Ƃ��}�E�X�őI������������Ƀt�H�[�J�X�𓖂āC���ׂĂ̏������W���[���ɘA�������v�����o��


[��҂ƃ��C�Z���X���]

�E��ҁF�L���s����w�V�X�e���C���^�t�F�[�X������
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  DualTextDisplay

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0:		�㉺�e�L�X�g�G���A�C�t�H���g�Z�b�g
case 1:		��e�L�X�g�G���A�D�e�L�X�g�t�@�C���ۑ�
case 9:		�㉺�e�L�X�g�G���A�C�t�H���g�T�C�Y�k��
case 10:	�㉺�e�L�X�g�G���A�C�t�H���g�T�C�Y�g��
case 100:	��e�L�X�g�G���A�C���̓e�L�X�g�̕\��
case 101:	��e�L�X�g�G���A�C�e�L�X�g�\���i�Ō㕔�j
case 102:	���e�L�X�g�G���A�C�t�H�[�J�X�P���F�t���Ńe�L�X�g�\���i�擪���j
case 103:	���e�L�X�g�G���A�C�F�Ȃ��e�L�X�g�\���ɖ߂��i�擪���j

     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int dataID, String t)
case 101:	String�^	��ɕ\������e�L�X�g
case 102:	String�^	���ɕ\������e�L�X�g

     �E�N���X���F
public class DualTextDisplay extends VisualizationModule implements MouseListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F
executeAllByClick();
executeAllByClick();
�}�E�X�Ńh���b�O����������� mainFocusString�Ƃ��ď������W���[�������s
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
