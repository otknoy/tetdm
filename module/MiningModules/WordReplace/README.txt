����̒P���ʂ̒P��ɒu��������
�P��u��(WordReplace(ID=4))

�E�Ή���������c�[���F
�e�L�X�g(TextDisplay(ID=1))
�t�@�C��(FileDisplay(ID=5))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(TextDisplay(ID=1))�t�@�C��(FileDisplay(ID=5))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�e�L�X�g(TextDisplay(ID=1))�Ƃ̑g�ݍ��킹�ŁA
     �E�g�����F����̒P���ʂ̒P��ɒu�����������ۂɎg���i��؂�ꂽ�P��P�ʂł݂̂œ��삷��j
     �E�g�����F�����t�@�C��ReplaceWords.txt�ɒu���O�̒P��ƒu����̒P����X�y�[�X��؂�̃y�A�ɂ��ċL�q����B
	�u�ۑ����u���v�{�^���F
	�p�l���Z�b�g�{�^������������A�����c�[�� �t�@�C��(FileDisplay(ID=5))���Z�b�g���ꂽ��ԂŁA
	�t�@�C��(FileDisplay(ID=5))�̓��e���C�t�@�C��ReplaceWords.txt�ɕۑ����C�ۑ����ꂽ�P��ɂ��P��̒u�����s��


[��҂ƃ��C�Z���X���]

�E��ҁF�����z�q
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  WordReplace

     �E�������e�Ɛ�������f�[�^�̐����F����ReplaceWords.txt���̒u���O�̒P�����̓e�L�X�g����T���B�u���O�̒P�ꂪ����΁A�����t�@�C�����̒u����̒P��ɒu��������B�P��̒u��������������A�V�����e�L�X�g��String�^�̕ϐ��Ƃ��ĕԂ��B

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:	�����̃t�@�C�����C�������̒P���𑗐M
case 1:	�������̒P��̍ēǂݍ���

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(47,myModulePath+"ReplaceWords.txt");	String	�����t�@�C����
setDataString(0,MyMethod());				String	�������̒P���i���s�Ō����j

     �E�N���X���F
public class WordReplace extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F
displayOtherModule(5,991);
�ʃp�l�����ŁC�����c�[�� �t�@�C��(FileDisplay(ID=5)) ���g���Ă���ꍇ�C
�t�@�C�� ReplaceWords.txt�̒P����X�V�ۑ����C�ēx�n�C���C�g�\�����s���D
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
