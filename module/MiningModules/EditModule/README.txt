�e�L�X�g�̕ҏW�C�ۑ��C�s�ԍ��̒ǉ����s���܂�
�G�f�B�^(EditModule(ID=1))

�E�Ή���������c�[���F
�e�L�X�g(TextDisplay(ID=1))


�u�p�l���Z�b�g�v�{�^���ŁA�e�L�X�g(TextDisplay(ID=1))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


�Ή���������c�[���ƂƂ��ɗ��p���邱�ƂŁA�ȉ��̓�������܂��B
�Ȃ����̓e�L�X�g�Ƃ́A�I���W�i���̃e�L�X�g���R�s�[���č쐬�����e�L�X�g���w���A
�ۑ����s���Ă��A�I���W�i���̃e�L�X�g�̓��e���㏑������邱�Ƃ͂���܂���B

�{�^���F
�u�ҏW�L�����Z���v�F�Ō�ɕۑ������Ƃ��̏�Ԃɖ߂��܂��B
�u�ҏW�ۑ�+���s�v�F�\������Ă���e�L�X�g����̓e�L�X�g�Ƃ��ăt�@�C���ɕۑ����A�S�c�[�����������Ď��s���܂��B
�u�^�O�J�b�g�v�F�Z�O�����g�̋�؂�L�����������܂��B
�u���s�ŋ�_�v�F���s������Ƃ���ɁA��_��}�����܂��B�i��̕��͍쐬���܂���j
�u��s�ŕ����v�F��s������Ƃ���ɁA�Z�O�����g�̋�؂�L����}�����܂��B�i��̃Z�O�����g�͍쐬���܂���j
�u�s�ԍ��v�F�s�ԍ��t���̓��̓e�L�X�g��\�����܂��B



[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


[���W���[���J���Ҍ������]
-----
[README.txt for MINING MODULE]  :  EditModule

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:���̓e�L�X�g�f�[�^�𑗐M
case 1:�e�L�X�g�f�[�^���t�@�C������ēǂݍ���
case 3:�Z�O�����g�̋�؂������
case 4:��_��}��
case 5:�Z�O�����g�̋�؂��}��
case 6:���̓e�L�X�g�f�[�^�ɍs�ԍ���t�^�����e�L�X�g�𐶐����đ��M

     �E�o�̓f�[�^�̐����i�����̌^����ǉ����ĉ������j�F
setDataString(0,text.originalText);	String�^	���̓e�L�X�g�f�[�^
setDataString(1,numberedText);		String�^	���̓e�L�X�g�f�[�^�ɍs�ԍ���t�^�����e�L�X�g
setDataString(1,editedText);		String�^	���̓e�L�X�g�f�[�^�ɋ�_��Z�O�����g��؂��t�^�����e�L�X�g

     �E�N���X���F
public class EditModule extends MiningModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�
