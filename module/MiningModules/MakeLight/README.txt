���͒��̊e���ɂ��āC���Ƃ̊֘A�x���v�Z����
���Ɖe�f�[�^(MakeLight(ID=12))

�E�Ή���������c�[���F
�e�L�X�g(�J���[)(TextDisplayColor(ID=2))
���z(ScoreDist(ID=4))
�L�[���[�h�I��(KeywordSelect(ID=9))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(�J���[)(TextDisplayColor(ID=2))���z(ScoreDist(ID=4))�L�[���[�h�I��(KeywordSelect(ID=9))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�e�L�X�g(�J���[)(TextDisplayColor(ID=2))�Ƃ̑g�ݍ��킹�ŁA
���͒��̊e���̎��֘A�x��]�����ĕ����n�C���C�g���܂��D
�i�W�]��i�v��j(Panoramic ID=11)�ɂ���ē�����ϓ_��𕶏͂̎��Ƃ��āC�e���̎��֘A�x��]������D�j

���z(ScoreDist(ID=4))�Ƃ̑g�ݍ��킹�ŁA
�e�L�X�g�S�̂̌��̗ʂ��A�_�O���t�ŕ\�����܂��D

�L�[���[�h�I��(KeywordSelect(ID=9))�Ƃ̑g�ݍ��킹�ŁA
���P��i�ϓ_��j��I���ł��܂��B


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D
�E�����\�����F�����z�q�E�����\���E���R�n�F���Ɖe��p�����e�L�X�g�̃e�[�}�֘A�x�̉���, �l�H�m�\�w��_�����CVol.24, No.6, pp.480 -- 488, (2009).
Yoko Nishihara and Wataru Sunayama: Text Visualization using Light and Shadow based on Topic Relevance, International Journal of Intelligent Information Processing, Vol.2, No.2, pp.1 -- 8, (2011).


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  MakeLight

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 4502:				���Ɖe�̃f�[�^�𐶐����đ��M
case 0:					���Ɖe�̃f�[�^�𐶐����đ��M

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataIntegerArray(1,attr);				int[] �^		�e���̎��֘A�x��1����9�Ő��l���CTextDisplay2�̉��F�\���p
setDataDoubleArray(2,light_value);//for scoredist	double[] �^	�e���̎��֘A�x�̎����l()(ScoreDist�p)
setDataInteger(77,5);			int �^ 	5 	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P�ꃊ�X�g�̐�
setDataDoubleArray(10,value);		double[] �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���l
setDataString(10,boxLabel[0]);		String �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���
setDataDoubleArray(11,value);		double[] �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���l
setDataString(11,boxLabel[1]);		String �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���
setDataDoubleArray(12,value);		double[] �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���l
setDataString(12,boxLabel[2]);		String �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���
setDataDoubleArray(13,value);		double[] �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���l
setDataString(13,boxLabel[3]);		String �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���
setDataDoubleArray(14,value);		double[] �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���l
setDataString(14,boxLabel[4]);		String �^	�L�[���[�h�I��(KeywordSelect(ID=9))�ŕ\������P��̕]���

     �E�N���X���F
public class MakeLight extends MiningModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F
text.focus.focusKeywords[i] = false;
�ϓ_��̏�����

     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F
focusClickExecute = true;
�W�]��i�v��j(Panoramic ID=11) �Ŏ��P��i�ϓ_��j���������Ƃ��ɘA�����s
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F
topicWords = getDataIntegerArray(11, 50);
�e�L�X�g�̊ϓ_�ƂȂ�P��W��	�W�]��i�v��j(Panoramic ID=11)����擾
