�e�L�X�g�i�Z�O�����g�j�̏W���𕪗ނ��Ēn�}�iOKmap�j�p�̃f�[�^���쐬���܂�
�ċA�I�N���X�^�����O(RClustering(ID=6))

�E�Ή���������c�[���F
OKmap(OKmap(ID=9))
�A������(FocusDisplay(ID=1016))


�u�p�l���Z�b�g�v�{�^���ŁAOKmap(OKmap(ID=9))�A������(FocusDisplay(ID=1016))�Ƃ̑g�ݍ��킹�ɃZ�b�g���܂��B


[�g�������L�����Ă�������]
���ɑ���͕K�v����܂���D


[��҂ƃ��C�Z���X���]

�E��ҁF�V�X�e���C���^�t�F�[�X�������i�L���s����w�j
�E�{���W���[���̗��p�����ɂ��āCTETDM�T�C�g(http://tetdm.jp/)����TETDM���p�������ihttp://tetdm.jp/pukiwiki/index.php?plugin=attach&refer=TETDM������&openfile=license.txt�j�̓��e�����̂܂ܓK�p���܂��D


���W���[���J���Ҍ������
-----
[README.txt for MINING MODULE]  :  RClustering

     �E�������e�̐����i���\�b�hminingOperations����case���̐����j�F
case 0:
�ċA�I�N���X�^�����O���s���C�n�}�p�̃f�[�^���쐬
     �E�o�̓f�[�^�̐����F
setDataInteger(0,text.segmentNumber);
�n�}��̑S�m�[�h��
setDataStringArray(100+i, names);
�G���A�̃��x����
setDataStringArray(10,createSegmentName());
�m�[�h��
setDataInteger(1,clusterData[number].clusterNumber);//Number of Cluster
�G���A��
setDataIntegerArray2(2,elements);//Cluster elements
�G���A���̗v�f��
setDataBooleanArray2(3,clusterData[number].link);//Links
�����N
setDataBooleanArray2(4,clusterData[number].strongLink);//Strong Links
�������N
     �E�N���X���F
public class RClustering extends MiningModule
     �E�t�H�[�J�X�^�ϐ��̗��p�F�Ȃ�
     �E�t�H�[�J�X���ɂ�鏈���A���t���O(�A�����s)�F�Ȃ�
     �E�I�v�V�����ɂ������A��(�A���v��)�F�Ȃ�
     �E�I�v�V�����ɂ�鏈���A��(�A���v��)�F�Ȃ�
     �E�f�[�^�擾�ɂ�鏈���A��(�A���v��)�F�Ȃ�