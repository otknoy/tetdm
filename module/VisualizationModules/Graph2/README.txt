�ꎟ���̐��l�f�[�^�̐܂���O���t��\�����܂�
�O���t�Q(Graph2(ID=23))


[�g�������L�����Ă�������]
�uP-�v�uP+�v�{�^���F�c���̐��̕����𑝌�������
�uM-�v�uM+�v�{�^���F�c���̕��̕����𑝌�������
�uSUM�v�F�ꎟ���f�[�^�́A�ݐσf�[�^��\�����܂��B
�u%�v�F�ꎟ���f�[�^�̍ő�l�ƍŏ��l�̍���100%�Ƃ��ĕ\�����܂��B


[��҂ƃ��C�Z���X���]

�E��ҁFTETDM�J���`�[��
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  Graph2

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 4503:�O���t��\������ׂ�double�^�z��value��focusNumber�ԖڂɁC
	text.focus.mainFocusDouble�̒l��������D
	focusNumber�̒l��0����n�܂���1�������C10�ɒB�����0�ɖ߂�D

case 0:
�O���t�̕\��

case 1:focusNumber�̒l��0�ɏ���������

     �E�������W���[������󂯎�����̓f�[�^�F
public boolean setData(int dataID, double score[])
case 0:value = score;

�O���t�Ƃ��ĕ\������ꎟ�����l�f�[�^

     �E�N���X���F
public class Graph2 extends VisualizationModule implements ActionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
	text.focus.getMainFocusDouble();
	�t�H�[�J�X�^�C�~���O�ɂ������A�����Ƀf�[�^���擾����
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
