�Z�O�����g�Ԃ̊֘A���c���[�\���ŕ\������
�Z�O�����g��(DrawTree(ID=1015))


�Z�O�����g�Ԃ̊֘A���c���[�\���ŕ\�����܂��D
�}�E�X�Ńm�[�h�̈ړ����ł��܂��D
�����Ȃ��Ƃ���Ńh���b�O����ƁA�m�[�h�S�̂��ړ��ł��܂��D


[��҂ƃ��C�Z���X���]

�E��ҁF�V�X�e���C���^�t�F�[�X�������i�L���s����w�j
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  DrawTree

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 4501:	�c���[�\������
case 0:		�c���[�\������

     �E�������W���[������󂯎�����̓f�[�^�F�Ȃ�
     �E�N���X���F
public class DrawTree extends VisualizationModule implements MouseListener, MouseMotionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.mainFocusSegment < 0)
text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//

���C���Z�O�����g�C�T�u�Z�O�����g�i�}�E�X�J�[�\�����������Ă���m�[�h�����C���C���C���ƍł����Ă���m�[�h���T�u�Ƃ���j
     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F
repaintOthersByTouch();
�}�E�X�Ńm�[�h��G��ƁA���̉����c�[�����ĕ`�悷��
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�

     �E�K�v�ȊO���f�[�^�t�@�C���F
	- tree2.png
