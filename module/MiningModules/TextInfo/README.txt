�e�L�X�g�̎���C���C�ŏd�v���C��ѐ��C���ܗL���C�s�K�ؕ\�����C��������\��
�e�L�X�g����(TextInfo(ID=19))

�E�Ή���������c�[���F
Html�e�L�X�g(TextDisplayHtml(ID=3))


�e�L�X�g�Ɋւ���e�����\�����܂��B
�u�p�l���Z�b�g�v�{�^���������ƁA�e�f�[�^�𐶐����Ă���c�[���Q�ɕ\�����؂�ւ��܂��B
Html�e�L�X�g(TextDisplayHtml(ID=3))
�v��i�W�]��j(Panoramic(ID=11))
���Ɖe(MakeLight(ID=12))
��ꒊ�o(SubjectExtraction(ID=5))
�쉺�胉�x��(LabelData(ID=13))
�P��`�F�b�N(PaperCheck(ID=2))
�����`�F�b�N(LongSentenceCheck(ID=16))


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  TextInfo

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 2:		�T�̃��W���[������f�[�^���擾���ĕ\������i�f�[�^�Đ����Ȃ��j
case 4502:	�T�̃��W���[������f�[�^���擾���ĕ\������i�f�[�^�Đ����Ȃ��j
case 0:		�T�̃��W���[������f�[�^���擾���ĕ\������i�f�[�^�Đ�������j
case 1:		�������W���[���̕\�����X�V

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(0,checkedText);	String �^	�擾�����f�[�^�𑍍������e�L�X�g(html)
setDataString(0,checkedText);	String �^	�擾�����f�[�^�𑍍������e�L�X�g(html)

     �E�N���X���F
public class TextInfo extends MiningModule

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F
focusClickExecute = true;	
�t�H�[�J�X�N���b�N���Ƀf�[�^���Ď擾
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F
subjectList = getDataIntegerArrayNew(11,77);	�v��i�W�]��j(Panoramic(ID=11))
keySentence = getDataStringNew(11,1);		�v��i�W�]��j(Panoramic(ID=11))
scoreDist = getDataDoubleArrayNew(12,2);	���Ɖe(MakeLight(ID=12))
relationIDs = getDataIntegerArrayNew(13,2);	�쉺�胉�x��(LabelData(ID=13))
noiseNumber = getDataIntegerNew(2,1);		�P��`�F�b�N(PaperCheck(ID=2))
veryLongSentenceNumber = getDataIntegerNew(16,1);	�����`�F�b�N(LongSentenceCheck(ID=16))
