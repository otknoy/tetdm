���͂����P��i������j�����ƂɁC�ő�T�O���̃c�C�[�g���擾����
Twitter(TweetExtraction(ID=20))

�E�Ή���������c�[���F
�e�L�X�g(�J���[)(TextDisplayColor(ID=2))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(�J���[)(TextDisplayColor(ID=2))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�����̃e�L�X�g�t�H�[���ɒP��i������j�����āA���s�i�G���^�[�A���^�[���j�L�[�������ƁA���͂������_���ŐV�Ƃ��ő�T�O���̃c�C�[�g���擾����B
�uClear/Check�v�{�^���������ƁA�e�L�X�g�t�H�[���̕�������������A�擾�����c�C�[�g���̌�������n�C���C�g�\������B


[��҂ƃ��C�Z���X���]

�E��ҁF�����z�q
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  TweetExtraction

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:					�Ȃ�
case 23://SEARCH AND SAVE RESULTS	�e�L�X�g�t�H�[���̒P���twitter�����iTwitter API���g���āA���͂��ꂽ�N�G�����܂�Tweet���ő�T�O���擾�j�C���ʂ�ۑ�
case 1:					��������C�\�����̃e�L�X�g���ŉ���
case 24://FOCUS SEARCH KEYWORD		��������C�\�����̃e�L�X�g���ŉ���
case 26://DISPLAY SEARCH RESULTS WITHOUT SAVING		���݃R�����g�A�E�g��
     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(24,text.focus.getMainFocusString());	String �^	������imainFocusString�j
setDataString(1,text.originalText);			String �^	���e�L�X�g
setDataString(24,query);				���݃R�����g�A�E�g��
setDataString(1,MyMethod());//violation: not created in 0 or 1 		���݃R�����g�A�E�g��
     �E�N���X���F
public class TweetExtraction extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
