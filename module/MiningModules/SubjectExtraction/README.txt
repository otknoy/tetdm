�e�L�X�g���̎��Ɋւ�����i���C��ꂪ�Ȃ����j��\��������
��ꒊ�o(SubjectExtraction(ID=5))

�E�Ή���������c�[���F
�e�L�X�g(�J���[)(TextDisplayColor(ID=2))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(�J���[)(TextDisplayColor(ID=2))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


     �E�g�����F�e�L�X�g���̎��̏���\��������
     �E�g�����F
	�i�P�j�u���Ȃ����v�{�^���F���̂Ȃ������n�C���C�g����i���߂̏����쐬�j
	�i�Q�j�u�S���v�{�^���F�S�Ă̎����n�C���C�g����i���߂̏����쐬�j
	�i�R�j�u���i�L�[���[�h�̂݁j�v�{�^���F�L�[���[�h�Ɋ܂܂������n�C���C�g����i���߂̏����쐬�j
	�i�S�j�u���i�L�[���[�h�ȊO�j�v�{�^���F�L�[���[�h�Ɋ܂܂�Ȃ������n�C���C�g����i���߂̏����쐬�j


[��҂ƃ��C�Z���X���]

�E��ҁFTETDM�J���`�[��
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  SubjectExtraction

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:		���̂Ȃ������n�C���C�g����f�[�^�𐶐�
case 1:		���ƒP����n�C���C�g����f�[�^�\���i�z��j�̏�����
case 11:	���̂Ȃ������n�C���C�g����f�[�^�𐶐�
case 12:	�S�Ă̎����n�C���C�g����f�[�^�𐶐�
case 13:	�L�[���[�h�Ɋ܂܂����i�L�[���[�h�̂݁j���n�C���C�g����f�[�^�𐶐�
case 14:	�L�[���[�h�Ɋ܂܂����i�L�[���[�h�ȊO�j���n�C���C�g����f�[�^�𐶐�

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataIntegerArray(23,sentenceHighlightNumber);	�����n�C���C�g���閾�邳�f�[�^
setDataIntegerArray(23,sentenceHighlightNumber);	�����n�C���C�g���閾�邳�f�[�^
setDataIntegerArray(11,wordHighlightNumber);		�P����n�C���C�g���閾�邳�f�[�^
setDataIntegerArray(11,wordHighlightNumber);		�P����n�C���C�g���閾�邳�f�[�^
setDataIntegerArray(11,wordHighlightNumber);		�P����n�C���C�g���閾�邳�f�[�^

     �E�N���X���F
public class SubjectExtraction extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
