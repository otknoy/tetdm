�e�c�[���̃\�[�X�v���O�����CREADME�t�@�C���Ȃǂ�������
�\�[�X�\��(SourceRead(ID=99999))

�E�Ή���������c�[���F
�e�L�X�g(TextDisplay(ID=1))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(TextDisplay(ID=1))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�e�c�[���̃\�[�X�v���O���������邱�Ƃ��ł��܂��B
README�{�^���������ĕ\���������e�ɁA���������ƕ\������Ă���ӏ��ɏ���ǉ�������ŁA
�e���W���[����README.txt���쐬���邱�Ƃ��ł��܂��D

     �E�g�����F
	�i�P�j�u�\�[�X�v�{�^���F�\�[�X�v���O������\��
	�i�Q�j�u�R���X�g���N�^�v�{�^���F�\�[�X���̃R���X�g���N�^��\��
	�i�R�j�u�O���[�o���v�{�^���F�\�[�X���̃O���[�o���̈��\��
	�i�S�j�u��{���\�b�h�v�{�^���FTETDM�̃c�[���쐬�d�l�Ɋ܂܂�郁�\�b�h�̕\��
	�i�T�j�u�`�F�b�N�v�{�^���FTETDM�T�C�g�ɃA�b�v���[�h����ۂɁC�񐄏��ȍ��ڂ̕\��
	�i�U�j�uREADME�v�{�^���FREADME.txt�t�@�C�������ۂɕK�v�ȏ��̕\��
	�i�V�j���j���[�F�}�C�j���O�����c�[���C�܂��͉����C���^�t�F�[�X�c�[����I������


[��҂ƃ��C�Z���X���]

�E��ҁFTETDM�J���`�[��
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  SourceRead

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:miningOperations(5);
case 6://source				�\�[�X�v���O�����̕�������擾
case 1://constructor			�\�[�X���̃R���X�g���N�^�̕�������擾
case 2://global variables		�\�[�X���̃O���[�o���̈�̕�������擾
case 3://basic functions		TETDM�̃c�[���쐬�d�l�Ɋ܂܂�郁�\�b�h�̕�������擾
case 4://check				TETDM�T�C�g�ɃA�b�v���[�h����ۂɁC�񐄏��ȍ��ڂ̕�����𐶐�
case 5://save readme.txt		README.txt�t�@�C�������ۂɕK�v�ȏ��̕�����𐶐�

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(0,sourceText());				��Lcase6�̃f�[�^�̑��M
setDataString(0,extractConstructor(moduleName));	��Lcase1�̃f�[�^�̑��M
setDataString(0,extractGlobalAreas());			��Lcase2�̃f�[�^�̑��M
setDataString(0,extractFunctions(basicFunctions));	��Lcase3�̃f�[�^�̑��M
setDataString(0,checkWarnings());			��Lcase4�̃f�[�^�̑��M
setDataString(0, readmeText);				��Lcase5�̃f�[�^�̑��M

     �E�N���X���F
public class SourceRead extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F

     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F

     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�

     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�

     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
