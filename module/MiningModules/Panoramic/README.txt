�w�肵�����̏d�v���𒊏o����
�v��(�W�]��)(Panoramic(ID=11))

�E�Ή���������c�[���F
�e�L�X�g(TextDisplay(ID=1))
�L�[���[�h(�W�]��)(XDrawDisplay(ID=8))

�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(TextDisplay(ID=1))�L�[���[�h(�W�]��)(XDrawDisplay(ID=8))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B

	�e�L�X�g(TextDisplay(ID=1))�Ƃ̑g�ݍ��킹�ŁA
	- �w�肵�����̏d�v���𒊏o���܂��D �uIndicative �w���I�v��v�uInformative ��m�I�v��v�uConclusion ���_�����o�v���s���܂��D
		-- �w���I�v��F�e�L�X�g�S�̂���̃L�[�ƂȂ镶�𒊏o���܂�
		-- ��m�I�v��F�e�L�X�g�S�̂̃X�g�[���[���Č����镶�𒊏o���܂�
		-- ���_�����o�F�e�L�X�g�̘b�̌��_�ƂȂ镶�𒊏o���܂�
	- ���F�p�l�������ŁA���o���镶�̐����w�肵�܂�(�����l��10)
	- �E�̂S�̃{�^���ŁC�uAdd Viewpoint �ϓ_��̕⊮�̗L���v�uBasic �w�i��g�p�̗L���v�uViewpoint �ϓ_��g�p�̗L���v�uFeature ������g�p�̗L���v��I���ł��܂��D
		-- �ϓ_��F�v�񂷂�ۂ̊ϓ_��\���P��
		-- �w�i��F���͂̔w�i�ƂȂ鍂�p�x�̒P��
		-- ������F���͂̓�����\���A�ϓ_��Ƃ̌��т��������P��

	�L�[���[�h(�W�]��)(XDrawDisplay(ID=8))�Ƃ̑g�ݍ��킹�ŁA
	- �ϓ_��A�w�i��A����������ꂼ��A�I�����W�A�A�ΐF�ŕ\�����܂��B
	- �p�l�����ŃN���b�N�����Ƃ��ɁA�Ԑ�����ɂ���P����ϓ_��Ƃ��āA�v����Đ������܂��B


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D
�E�����\�����F
	- ���R�n�E�J���c���F�F���͂̓�����\���L�[���[�h�𔭌����ďd�v���𒊏o����W�]��V�X�e���C�d�q���ʐM�w��_����, Vol.J84-D-I, No.2, pp.146 -- 154, (2001).
	- ���R�n�E�J���c���F�F�ϓ_�Ɋ�Â��ďd�v���𒊏o����W�]��V�X�e���Ƃ��̃T�[�`�G���W���ւ̎����C�l�H�m�\�w��_�����CVol.17, No.1, pp.14 -- 22, (2002).

���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  Panoramic

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 4502:	���͂�v�񂵂đ��M�C�P��̉����p�f�[�^�̍쐬
case 0:		���͂�v�񂵂đ��M�C�P��̉����p�f�[�^�̍쐬
case 1:		���͂�v�񂵂đ��M

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(0,summarized_text);	String �^	�v�񂵂��e�L�X�g
setDataString(1,summarized_text);	String �^	�v�񂵂��e�L�X�g
setDataIntegerArray(77,sbase_idlist);// option is not intend to some specified modules	int[] �^	�ϓ_�L�[���[�h��ID���X�g
setDataIntegerArray(50,topic_idlist);	int[] �^	���L�[���[�h��ID���X�g
setDataIntegerArray(51,back_idlist);	int[] �^	�w�i�L�[���[�h��ID���X�g
setDataIntegerArray(52,feat_idlist);	int[] �^	�����L�[���[�h��ID���X�g
setDataString(0,summarized_text);	String �^	�v�񂵂��e�L�X�g
setDataString(0,summarized_text);	String �^	�v�񂵂��e�L�X�g
setDataString(0,summarized_text);	String �^	�v�񂵂��e�L�X�g
setDataString(0,summarized_text);	String �^	�v�񂵂��e�L�X�g
setDataString(0,summarized_text);	String �^	�v�񂵂��e�L�X�g

     �E�N���X���F
public class Panoramic extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.focusKeywords[i] && sbasenum < sbasemax)//////////////////FOR LINKAGE
focusKeywords���ϓ_��Ƃ��ėv������s����

     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F
focusClickExecute = true;
�t�H�[�J�X�L�[���[�h�W��������ꍇ�A���̒P����ϓ_��Ƃ��ď������s��
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F
conclusionValue = getDataDoubleArray(13,11);
�쉺�胉�x��(LabelData(ID=13))����A�e���̌��_���Ƃ��Ă̕]���l���擾����
