�e�Z�O�����g�̓Ǝ������m�F�ł���
�Z�O�����g�Ǝ���(DrawOriginal(ID=1013))


�Z�O�����g�i�e�L�X�gor�i���j�Ԃ̊֌W���O���t�ŕ\�����܂��D�Z�O�����g�̓m�[�h�ŕ\����܂��D
�Ǝ����������m�[�h�قǐ��]�[���i��̕��j�ɕ\������A�Ǝ������Ⴂ�قǐԂ��]�[���i���̕��j�ɕ\������܂��D
�}�E�X�Ńm�[�h�̈ړ����ł��܂��D
�����Ȃ��Ƃ���Ńh���b�O����ƁA�m�[�h�S�̂��ړ��ł��܂��D


[��҂ƃ��C�Z���X���]

�E��ҁF���R�n
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D
�E�����\�����F���R�n�E����r���F���e�̓Ǝ����̎��o���ɂ�郌�|�[�g�̓Ǝ����]���x���V�X�e���C�l�H�m�\�w��_�����CVol.23, No.6, pp.392 -- 401, (2008).


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  DrawOriginal

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 4501:		�Z�O�����g�Ԃ̊֘A����
case 0:			�Z�O�����g�Ԃ̊֘A����
case 1:			�Ǝ����̃f�[�^���t�@�C�� OriginalData �ɕۑ����܂�

     �E�������W���[������󂯎�����̓f�[�^�F�Ȃ�
     �E�N���X���F
public class DrawOriginal extends VisualizationModule implements MouseListener, MouseMotionListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
if(text.focus.mainFocusSegment < 0)
text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//
���C���Z�O�����g�C�T�u�Z�O�����g�i�}�E�X�J�[�\�����������Ă���m�[�h�����C���C���C���ƍł����Ă���m�[�h���T�u�Ƃ���j

     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F
repaintOthersByTouch();
�}�E�X�Ńm�[�h��G��ƁA���̉����c�[�����ĕ`�悷��
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F
executeAllByTouch();
�}�E�X�Ńm�[�h��G��ƁA���̏����c�[�����ď�������
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�
