�i���⍂�p�o�̒P��C�w�肵���P��C����炪�܂܂�镶�𒊏o����
�P�ꒊ�o(WordExtraction(ID=3))

�E�Ή���������c�[���F
�e�L�X�g(�J���[)(TextDisplayColor(ID=2))
�t�@�C��(FileDisplay(ID=5))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(�J���[)(TextDisplayColor(ID=2))�t�@�C��(FileDisplay(ID=5))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�e�L�X�g(�J���[)(TextDisplayColor(ID=2))�Ƃ̑g�ݍ��킹�ŁA
     �E�g�����F���ڂ������P���i���A����炪�܂܂�镶�𒊏o����ۂɎg��
     �E�g�����F
	�i�P�j�u�i���v�{�^���F���͒��̕i���̕��z���������Ƃ��FPartOfSpeech�{�^���������ƁA�u���ݒ�v���Łu�L�[���[�h�Ƃ��Ď��o���P��̕i���Ǝ�ށv�ɐݒ肳��Ă���i�����n�C���C�g�\�������B�n�C���C�g�̐F�͕i�����ɈقȂ�
	�i�Q�j�u���p�x�v�{�^���F���͒��ŕp�x�̍����P����������Ƃ��FFrequency�{�^���������ƁA�u���ݒ�v���Łu�L�[���[�h�Ƃ��Ď��o���P��̕i���Ǝ�ށv�ɐݒ肳��Ă���P��̂����A�p�x�̍����P���ʂQ�O�����n�C���C�g�\�������B
	�i�R�j�u�w��P��v�{�^���F����̒P��̕��z���������Ƃ��FDictionary:W�{�^���������ƁA�����ɓo�^���ꂽ�P�ꂪ�n�C���C�g�\�������B�����͖{README.txt�t�@�C��������t�H���_�̒���ExtractionWords.txt�B
	�i�S�j�u�w��P��i���j�v�{�^���F����̒P�ꂪ�܂܂�镶�̕��z���������Ƃ��FDictionary:S�{�^���������ƁA�����ɓo�^���ꂽ�P����܂ޕ����n�C���C�g�\�������B�����͖{README.txt�t�@�C��������t�H���_�̒���ExtractionWords.txt�B
	�u�ۑ������o�v�{�^���F
	�p�l���Z�b�g�{�^������������A�����c�[�� �t�@�C��(FileDisplay(ID=5))���Z�b�g���ꂽ��ԂŁA
	�t�@�C��(FileDisplay(ID=5))�̓��e���C�t�@�C��ExtractionWords.txt�ɕۑ����C�ۑ����ꂽ�P��ɂ��n�C���C�g�\������


[��҂ƃ��C�Z���X���]

�E��ҁF�����z�q
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  WordExtraction

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:		�����̃t�@�C�����C�������̒P���𑗐M
case 1:		�������̒P��̍ēǂݍ���
case 25:	�e�L�X�g�t�B�[���h���̕�����擾�C�����v��
case 11:	�g�����i�P�j
case 12:	�g�����i�Q�j
case 13:	�g�����i�R�j
case 23:	�g�����i�S�j

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(47,myModulePath+"ExtractionWords.txt");	String	�����t�@�C����
setDataString(0,createDictionalyWords());		String	�������̒P���i���s�Ō����j
setDataString(25,query);				String	�e�L�X�g�t�B�[���h�ɓ��͂��ꂽ�����p�P��
setDataIntegerArray(11,wordHighlightNumber);		int[]	�i���ɂ��P��\���̐F�w��ԍ���B�z��̗v�f���͓��̓e�L�X�g���̒P��̉��א��ɂȂ�B�z��ɂ̓n�C���C�g����ۂ̐F�̔ԍ�����������B
setDataIntegerArray(12,wordHighlightNumber);		int[]	���p�x�ɂ��P��\���̐F�w��ԍ���B�z��̗v�f���͓��̓e�L�X�g���̒P��̉��א��ɂȂ�B�z��ɂ̓n�C���C�g����ۂ̐F�̔ԍ�����������B
setDataIntegerArray(13,wordHighlightNumber);		int[]	�w��P��ɂ��P��\���̐F�w��ԍ���B�z��̗v�f���͓��̓e�L�X�g���̒P��̉��א��ɂȂ�B�z��ɂ̓n�C���C�g����ۂ̐F�̔ԍ�����������B
setDataIntegerArray(23,sentenceHighlightNumber);	int[]	�w��P��ɂ�镶�\���̐F�w��ԍ���B�z��̗v�f���͓��̓e�L�X�g���̕��̐��ɂȂ�B�z��ɂ̓n�C���C�g����ۂ̐F�̔ԍ�����������B

     �E�N���X���F
public class WordExtraction extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F
displayOtherModule(5,991);
�ʃp�l�����ŁC�����c�[�� �t�@�C��(FileDisplay(ID=5)) ���g���Ă���ꍇ�C
�t�@�C�� ExtractionWords.txt�̒P����X�V�ۑ����C�ēx�n�C���C�g�\�����s���D
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
