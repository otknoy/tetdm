�t�@�C���uchecklist�v�Ŏw�肳�ꂽ�P����n�C���C�g����
�P��`�F�b�N(PaperCheck(ID=2))

�E�Ή���������c�[���F
Html�e�L�X�g(TextDisplayHtml(ID=3))
�t�@�C��(FileDisplay(ID=5))


�u�p�l���Z�b�g�v�{�^���ŁAHtml�e�L�X�g(TextDisplayHtml(ID=3))�t�@�C��(FileDisplay(ID=5))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B

Html�e�L�X�g(TextDisplayHtml(ID=3))�Ƃ̑g�ݍ��킹�ŁA
���͓��́C�t�@�C�� checklist ���Ŏw�肳�ꂽ�P����n�C���C�g���܂��D

�{�^���F
�ucheckresult(checkresult)�v�F
�ʃp�l�����ŁC�����c�[�� �t�@�C��(FileDisplay(ID=5)) ���g���Ă���ꍇ�C
�t�@�C�� checklist�̒P����X�V�ۑ����C�ēx�n�C���C�g�\�����s���D
�����ɁA�����c�[�� �e�L�X�g����(TextInfo(ID=19))���g���Ă���ꍇ�C���̕\�����e���X�V���܂��D


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  PaperCheck

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:	�P��̃`�F�b�N�����s����
case 1:	checklist �̍ēǂݍ��݂��s���C�P��̃`�F�b�N�����s����

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(47,myModulePath+"checklist");//For File Edit	String�^	�t�@�C�� checklist �̐�΃p�X
setDataString(0,checkedText);		String�^	�P��̃`�F�b�N���ʂ�html�e�L�X�g
setDataInteger(1,(100-score)/5);	int�^	�P��̃`�F�b�N���ʂ̃X�R�A
setDataString(0,checkedText);		String�^	�P��̃`�F�b�N���ʂ�html�e�L�X�g
setDataInteger(1,(100-score)/5);	int�^	�P��̃`�F�b�N���ʂ̃X�R�A

     �E�N���X���F
public class PaperCheck extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F
displayOtherModule(5,991);
�����c�[�� �t�@�C��(FileDisplay(ID=5)) �ŁC�t�@�C�� checklist ���̒P���\�������܂�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F
executeOtherModule(19,2);
executeOtherModule(19,1);
�e�L�X�g����(TextInfo(ID=19))�̕\�����X�V����i�f�[�^�擾+�\���j
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
