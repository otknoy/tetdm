�����c�[���ō쐬���ꂽ�f�[�^��p���ă��[�_�[�`���[�g��\������
���[�_�[�`���[�g(RadarChart(ID=16))


�E�g�����F�}�C�j���O���W���[���ō쐬���ꂽ�f�[�^��p���ă��[�_�[�`���[�g��\�����܂��B
�E�g�����F�����_�ȉ�2���܂ł̒l�\����ON/OFF�A3�i�K�̕����T�C�Y�ύX�A�R�F�̐F�ύX�A�F�̓����x�̕ύX���A�����̃p�l���ōs���܂��B

�E�Ή����鏈���c�[���FsetDataString(int id,String str)�ŏo�͂���A�Ȃ�����str�̓��e�����L�̌`���̏����c�[���ƘA�g�ł��܂��B
�T���v���f�[�^���o�͂��鏈�����W���[����p�ӂ��Ă��܂��iRadarChartTest�j�B
str�̓��e�F
"�v�f�i���j�̖��O,�l�i0�`1�̎����𕶎���^�ɕϊ��������́j\n"���R�ȏ�A�K�v�Ȏ��̐��̕���������܂��B

[��҂ƃ��C�Z���X���]

�E��ҁF�����m�L
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  RadarChart

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0:		���[�_�[�`���[�g�̐����ƕ\��

     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int dataID, String textData){
default:	id��0�Cstr�͈ȉ��̌`���ł��B�T���v���f�[�^���o�͂��鏈�����W���[���iRadarChartTest�j���Q�l�ɂ��Ă��������B
str�̓��e�F
"�v�f�i���j�̖��O,�l�i0�`1�̎����𕶎���^�ɕϊ��������́j\n"���R�ȏ�A�K�v�Ȏ��̐��̕���������܂��B
���̐��ɂ���ăG���[��Ԃ��������s���Ă��܂���̂ŁA�R���ȏォ�����I�Ƀ��[�_�[�`���[�g���ǂ߂�͈͂ŁA�f�[�^��p�ӂ��Ă��������B
�T���v���f�[�^���o�͂��鏈�����W���[���iRadarChartTest�j�ł́A�ő�W���ɂ��Ă��܂��B
str�̗�i3�̎��������[�_�[�`���[�g���ł��܂��j�F
���邳,0.64\n
�g����,0.4534\n
�Â���,0.835\n

     �E�N���X���F
public class RadarChart extends VisualizationModule implements ActionListener,ChangeListener{

     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
