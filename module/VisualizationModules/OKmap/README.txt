�ċA�I�N���X�^�����O�i�f�[�^�̗ގ����Ɋ�Â����ށj�̌��ʂ�n�}�`���ŕ\�����܂�
OKmap(OKmap(ID=9))

�i�������O�܂��͂P�̎��ɂ́C�N���X�^�����O�̈Ӗ����Ȃ����ߋN�����܂���B


[�g�������L�����Ă�������]
�}�E�X�̃h���b�O�ŁC�n�}�̕��s�ړ�
�}�E�X�̃X�N���[���ŁC�Y�[���C���C�Y�[���A�E�g
�n�_�ɐG��邱�ƂŁC���̒n�_�ɑΉ�����e�L�X�g�i�Z�O�����g�j�̓��e���C���ڂ���e�L�X�g�i�t�H�[�J�X�Z�O�����g�ɃZ�b�g�j�Ƃ��ĘA�����ĕ\��
�C�̏�Ń}�E�X�̉E�N���b�N�^���N���b�N�ŁC�C�ʂ̃��x����ύX


[��҂ƃ��C�Z���X���]

�E��ҁF�V�X�e���C���^�t�F�[�X�������i�L���s����w�j
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for VISUALIZATION MODULE]  :  OKmap

     �E�������e�̐����i���\�b�hdisplayOperations����case���̐����j�F
case 0: break;
���ɂȂ�
case 100://Create Link Data in MapData
�n�}�f�[�^�̍쐬
case 400:
�n�}�f�[�^�̍č\�z
case 4501:
���ڃm�[�h�𖾎��I�ɕ\��
     �E�������W���[������󂯎�����̓f�[�^�F
public void setData(int n, int data)
case 0: �m�[�h��
case 1: �N���X�^���i�G���A���j

public void setData(int n, int data[][])
case 2: �G���A���̗v�f��

public void setData(int n, boolean data[][])
case 3: �����N
case 4: �������N

public void setData(int n, String name[])
case 10:// Node Names	�m�[�h��
default://100-Area Names �G���A�̃��x����

     �E�N���X���F
public class OKmap extends VisualizationModule implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener

     �E�t�H�[�J�X�^�ϐ��̗��p�F
touch_num = text.focus.mainFocusSegment;
//text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//
���ڃm�[�h���C�G���Ă���m�[�h�Ƃ��ăZ�b�g

     �E�t�H�[�J�X���ɂ������A���i�A���v���j�F
repaintOthersByTouch();
���ڃm�[�h�̏������L
     �E�t�H�[�J�X���ɂ�鏈���A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ������A���i�A���v���j�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A���i�A���v���j�F�Ȃ�