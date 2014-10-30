//
// Mining module for TETDM
// OKmap.java Version 0.30
// Copyright(C) 2013 System Interface Laboratory ALL RIGHTS RESERVED.
//
// This program is provided under the licenses.
// You Should read the README file.
//

package module.MiningModules.RClustering;		// Replace ALL [MiningStyle] with your [module name]

import java.io.*;

import source.*;
import source.TextData.*;
import source.Utility.*;
import java.util.*;


public class RClustering extends MiningModule {
  TextData tempText;//Data for clustering

  int nodeNumber;
  int mergedNodeNumber;// number of merged nodes

  MakeCluster cluster;
  ClusterData clusterData[];

  int appearingSegmentFrequency[][][];

  final int MAXLEVEL = 11;
  int level;

  public RClustering() {
    setModuleID(6);
    pairingVisualizationID = new int[] {9,1016};
  }

//	public void initializePanel(){}
//	public void initialData(){}
//	public void actionPerformed(ActionEvent e){}

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        makeClustering();
        break;
    }
  }

  public void makeClustering() {
    //TEMPTEXT SETTINGS
    tempText = new TextData();

    tempText.segmentNumber = text.segmentNumber;
    tempText.segment = new SegmentData[text.segmentNumber];

    for (int i=0; i<text.segmentNumber; i++) {
      tempText.segment[i] = new SegmentData();
      tempText.segment[i].segmentText = text.segment[i].segmentText;
    }

    tempText.segmentRelation = new RelationalData(text.segmentNumber);
    String nameList[] = new String[text.segmentNumber];
    for (int i=0; i<text.segmentNumber; i++) {
      for (int j=0; j<text.segmentNumber; j++) {
        tempText.segmentRelation.cos[i][j] = text.segmentRelation.cos[i][j];
      }
      nameList[i] = "SEG"+(i+1);
    }
    tempText.segmentRelation.setName(nameList);

    tempText.keywordNumber = text.keywordNumber;
    tempText.keyword = new KeywordData[text.keywordNumber];

    appearingSegmentFrequency = new int[MAXLEVEL+1][text.keywordNumber][text.segmentNumber];/////

    for (int i=0; i<text.keywordNumber; i++) {
      tempText.keyword[i] = new KeywordData();

      tempText.keyword[i].appearingSegment = new int[text.segmentNumber];
      tempText.keyword[i].appearingSegmentTable = new int[text.segmentNumber];
      for (int j=0; j<text.segmentNumber; j++) {
        tempText.keyword[i].appearingSegment[j] = text.keyword[i].appearingSegment[j];
        tempText.keyword[i].appearingSegmentTable[j] = text.keyword[i].appearingSegmentTable[j];

        appearingSegmentFrequency[0][i][j] = text.keyword[i].appearingSegmentTable[j];///// USE 0 as SEGMENT , 1:clusterData[0], 2:clusterData[1],...
      }
    }

    //CLUSTERING SETTINGS
    nodeNumber = text.segmentNumber;	//number of Nodes for each level
    clusterData = new ClusterData[MAXLEVEL];

    createProcessingPop();

    //CLUSTERING
    level=0;
    for (int i=0; i<MAXLEVEL; i++,level++) {

      setProcessingPercent((int)(i*100/MAXLEVEL));

//            System.out.println("CLUSTERING LEVEL = "+i);

      //CLUSTERING SETTINGS
      tempText.segmentRelation.optimizeLink(tempText.segmentRelation.cos, 0.5);
      if (tempText.segmentRelation.totalLinkNumber == 0) {
        break;
      }

      //CLUSTERING
      cluster = new MakeCluster(tempText.segmentRelation);

      if (cluster.cluster_num == 0) {
        break;
      }

      int j;
      for (j=0; j<cluster.cluster_num; j++)
        if (cluster.cl[j].num > 1) {
          break;
        }
      if (j == cluster.cluster_num) {	// If no clusters were merged,
        break;
      }

      createClusterData(i);
      mergeClusters();// data integration
      reCalculateValues();// recalculate relationship values
    }

    disposeProcessingPop();

    //SET DATA
    setDataInteger(0,text.segmentNumber);
    for (int i=0; i<level; i++) {
      sendData(i);
    }
    displayOperations(400);

    //CREATE ISLAND NAMES
    createIslandName();
    //SET
    String names[];
    for (int i=0; i<level; i++) {
      names = new String[clusterData[i].clusterNumber];
      for (int j=0; j<clusterData[i].clusterNumber; j++) {
        names[j] = clusterData[i].cl[j].name;
      }

      setDataStringArray(100+i, names);
    }

    //CREATE SEGMENT NAMES
    setDataStringArray(10,createSegmentName());
  }

  void sendData(int number) {
    int elements[][] = new int[clusterData[number].clusterNumber][];

    setDataInteger(1,clusterData[number].clusterNumber);//Number of Cluster

    for (int i=0; i<clusterData[number].clusterNumber; i++) {
      elements[i] = clusterData[number].cl[i].elements;
    }
    setDataIntegerArray2(2,elements);//Cluster elements
    setDataBooleanArray2(3,clusterData[number].link);//Links
    setDataBooleanArray2(4,clusterData[number].strongLink);//Strong Links

    displayOperations(100);
  }

  void createClusterData(int d) {
    clusterData[d] = new ClusterData(cluster.cluster_num, text.segmentNumber);
    for (int i=0; i<clusterData[d].clusterNumber; i++) {
      clusterData[d].cl[i].setElements(cluster.cl[i].elements);
    }

    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segmentNumber; j++)
        if (cluster.rd.link[i][j]) {
          clusterData[d].link[i][j] = true;
          if (tempText.segmentRelation.cos[i][j] >= 0.97) {
            clusterData[d].strongLink[i][j] = true;
          } else {
            clusterData[d].strongLink[i][j] = false;
          }
        } else {
          clusterData[d].link[i][j] = false;
          clusterData[d].strongLink[i][j] = false;
        }
  }

  void mergeClusters() {
    int headSegment, mergedSegment;

    mergedNodeNumber = 0;

    for (int i=0; i<text.keywordNumber; i++)
      for (int j=0; j<text.segmentNumber; j++) {
        appearingSegmentFrequency[level+1][i][j] = appearingSegmentFrequency[level][i][j];
      }

    for (int i=0; i<cluster.cluster_num; i++) {
      mergedNodeNumber += (cluster.cl[i].num - 1);

      headSegment = cluster.cl[i].elements[0];

      for (int j=1; j<cluster.cl[i].num; j++) {
        mergedSegment = cluster.cl[i].elements[j];

        for (int k=0; k<tempText.keywordNumber; k++) {
          if (tempText.keyword[k].appearingSegmentTable[mergedSegment] == 1) {
            tempText.keyword[k].appearingSegmentTable[headSegment] = 1;
            appearingSegmentFrequency[level+1][k][headSegment] += appearingSegmentFrequency[level][k][mergedSegment];
          }
          tempText.keyword[k].appearingSegmentTable[mergedSegment] = 0;
          appearingSegmentFrequency[level+1][k][mergedSegment] = 0;
        }
      }
    }

    for (int k=0; k<tempText.keywordNumber; k++) {
      tempText.keyword[k].segmentFrequency = 0;

      for (int i=0; i<tempText.segmentNumber; i++)
        if (tempText.keyword[k].appearingSegmentTable[i] == 1) {
          tempText.keyword[k].appearingSegment[tempText.keyword[k].segmentFrequency++] = i;
        }

      if (tempText.keyword[k].segmentFrequency != tempText.segmentNumber) {
        tempText.keyword[k].appearingSegment[tempText.keyword[k].segmentFrequency] = -1;
      }
    }
  }

  void reCalculateValues() {
    nodeNumber -= mergedNodeNumber;

    tempText.createKindNumbersForSegment();
    tempText.createSegmentRelation();
  }

// CREATE NAMES
  boolean usedWord[];

  void createIslandName() {	//By Segment Frequency
    int freqlist[] = new int[text.keywordNumber];
    int idlist[] = new int[text.keywordNumber];

    usedWord = new boolean[text.keywordNumber];

    for (int i=level-1; i>=0; i--) {
//			System.out.print("LEVEL:"+i);

      for (int j=0; j<clusterData[i].clusterNumber; j++) {
        for (int k=0; k<text.keywordNumber; k++) {
          freqlist[k] = appearingSegmentFrequency[i+1][k][clusterData[i].cl[j].elements[0]];
        }

//				Qsort.findMaxID(freqlist, idlist, text.keywordNumber);

        Qsort.initializeIndex(idlist, text.keywordNumber);
        Qsort.quicksort(freqlist, idlist, text.keywordNumber);

//				System.out.println("--- CLUSTER:"+j);

        for (int list=0; list<text.keywordNumber; list++) {
//					System.out.println(text.keyword[idlist[list]].word+":"+appearingSegmentFrequency[i+1][idlist[list]][clusterData[i].cl[j].elements[0]]);

          if (usedWord[idlist[list]] == true) {
            continue;
          }

//					System.out.println(text.keyword[idlist[list]].word+" SELECTED "+appearingSegmentFrequency[i+1][idlist[list]][clusterData[i].cl[j].elements[0]]);

          usedWord[idlist[list]] = true;
          clusterData[i].cl[j].name = text.keyword[idlist[list]].word+"("+appearingSegmentFrequency[i+1][idlist[list]][clusterData[i].cl[j].elements[0]]+")";
          break;
        }
      }
    }
  }

  String[] createSegmentName() {	//By Sentence Frequency
    String names[] = new String[text.segmentNumber];
    int maxkey, maxid;

    for (int i=0; i<text.segmentNumber; i++) {
      maxkey = maxid = -1;
      for (int k=0; k<text.keywordNumber; k++)
        if (text.keyword[k].sentenceFrequencyInSegment[i] > maxkey && usedWord[k] == false) {
          maxkey = text.keyword[k].sentenceFrequencyInSegment[i];
          maxid = k;
        }

      if (maxid < 0 || maxkey == 0) {
        names[i] = "noName";
      } else {
        names[i] = text.keyword[maxid].word;
      }
    }
    return names;
  }
}

//CLUSTER DATA
class ClusterData {
  class Cluster {
    String name;
    int elements[];

    Cluster() {}

    void setElements(int ele[]) {
      elements = new int[ele.length];
      for (int i=0; i<ele.length; i++) {
        elements[i] = ele[i];
      }
    }
  }

  Cluster cl[];
  int clusterNumber;

  boolean link[][];
  boolean strongLink[][];

  ClusterData(int num, int all) {
    clusterNumber = num;
    cl = new Cluster[clusterNumber];
    for (int i=0; i<clusterNumber; i++) {
      cl[i] = new Cluster();
    }

    link = new boolean[all][all];
    strongLink = new boolean[all][all];
  }
}