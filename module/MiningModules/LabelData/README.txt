���͂̎��Ƃ̊֘A��\���e�L�[���[�h�̃��x���f�[�^�𐶐�
�쉺�胉�x��(LabelData(ID=13))

�E�Ή���������c�[���F
�쉺��(FlowPanel(ID=12))


�u�p�l���Z�b�g�v�{�^���ŁA�쉺��(FlowPanel(ID=12))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


���͂̎��Ƃ̊֘A��\���e�L�[���[�h�̃��x���f�[�^�𐶐����܂��D


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D
�E�����\�����F
	- ���R�n�E�J��M�O�F�e�L�X�g�̎��Ɋ�Â���ѐ��]���ƌ��_���o�ւ̉��p, ���{�m�\���t�@�W�B�w�, Vol.23, No.5, pp.727 -- 738, (2011).


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  LabelData

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 4502:				�쉺��p���x���f�[�^�̐����C�L�[���[�h�̊e�Z�O�����g�ɂ����郉�x�����쐬���đ��M
case 0:					�쉺��p���x���f�[�^�̐����C�L�[���[�h�̊e�Z�O�����g�ɂ����郉�x�����쐬���đ��M
case 1://case 10100:			�e�L�[���[�h�����֘A�ꂩ�ǂ����̃`�F�b�N���X�g�𐶐����đ��M
case 2://case 10200:			���֘A���ID���X�g�𐶐����đ��M
case 11:				���_���Ƃ��Ă̕]���l���X�g�𑗐M

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataIntegerArray(1,subject_check);/////	int[] �^		�e�L�[���[�h�����֘A�ꂩ�ǂ����̃`�F�b�N���X�g
setDataIntegerArray(2,subject_idlist);/////	int[] �^		���֘A���ID���X�g
setDataDoubleArray(11,makeConclusionValue());	double[] �^	���_���Ƃ��Ă̕]���l���X�g
setDataIntegerArray(1355,level);		int[] �^		�L�[���[�h�̍ŏI���x��
setDataIntegerArray(1355000+i,label[i]);	�Z�O�����g�ԍ�(0����)+1355000:	int[] �^	�e�Z�O�����g�ɂ�����L�[���[�h�̃��x���ԍ�

     �E�N���X���F
public class LabelData extends MiningModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F
focusClickExecute = true;
�W�]��i�v��j(Panoramic ID=11)�ŁA�t�H�[�J�X�L�[���[�h�W���i���P��W���j���������Ƃ��ɘA�����s
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F
sbase_idlist = getDataIntegerArray(11, 77);
���P�ꃊ�X�g��W�]��i�v��j(Panoramic ID=11)����擾
