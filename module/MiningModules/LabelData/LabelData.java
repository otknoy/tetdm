//
// Mining module for TETDM
// LabalData.java Version 0.30
// Copyright(C) 2007 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//


package module.MiningModules.LabelData;

import source.*;
import source.TextData.*;
import source.Utility.*;




// making labels until n_th segment
// this module depends on calcrelate() in calcrelate4.h;

//Downsizing of html file (To be desired in the future)

//"TOPIC RELATED WORDS" are the words in a sentence that includes a topic and within 10 words distance of the topic word

//RELATED/NON-RELATED is defined by the final status of each segment (label = 0 or 7).
//Therefore, a word CANNOT have different labels such as [6 or 5] and [4] in a segment to be INC.
//A word CAN be [6] and [5], or [3] and [2] in a segment.
//That is, a word that appears more than twice in a segment, and if that word is as RELATED in anyone, all appearance in the segment treated as RELATED.
//NON-NON->NON, YES-YES->YES, YES-NO->YES NO->YES->YES
//This means definition can be delayed in a segment

//LABEL seg[].label -1:noappear, 0:appear, 7:appear in related , 1:TOPIC, 2:MAIN, 3:SEED, 4:NEW, 5:BYWAY, 6:FLOOD, 9:SUB
//ARC -1:noappear 1:TOPIC-EVOLUTION, 2:MAIN-EVOLUTION, 3:DIVEGENCE, 4:CONVERGENCE, 5:SUB-EVOLUTION, 6:EMERGENCE

//arc -1:noappear, 1:TOPIC -> TOPIC:EVOLUTION 2:SEEDorMAIN -> MAIN : EVOLUTION
//	3:SEED or MAIN -> SUB or FLOOD:DIVERGENCE 4:NEW or BYWAY or SUB -> SEED or MAIN:CONVERGENCE
//  5:NEW or BYWAY or SUB or FLOOD -> BYWAY or SUB or FLOOD SUB-EVOLUTION 6: noappear -> NEW or SEED

//LABEL seg[].label -1:noappear, 0:appear, 7:appear in related , 1:TOPIC, 2:SEED, 3:MAIN, 4:NEW, 5:BYWAY, 6:SUB
// 1:emergence(-1 -> 2), 2:expansion(2 -> 3), 3:incorporation(4,5 -> 2), 4:divergence(3 -> 6), 5:convergence(6 -> 3),
// 6:out-emergence(-1 -> 4), 7:out-expansion(4 -> 5)
// evolution 1->1:11,8, 3->3:13,9, 5->5:15,10, 6->6:16,11

import java.io.*;
import java.text.*;
import java.util.*;


public class LabelData extends MiningModule {
  class KeywordData5 {
    int distance[];
    int label;
    int level;

    int div_count;
    int conv_count;

    KeywordData5() {}
  }

  class SegmentData5 {
    int label[];
    int level[];
    int distance[];

    SentenceData5 sen5[];

    SegmentData5(int sentenceNumber) {
      sen5 = new SentenceData5[sentenceNumber];
      for (int i=0; i<sentenceNumber; i++) {
        sen5[i] = new SentenceData5();
      }
    }

    class SentenceData5 {
      int label[];
      int arc[];

      SentenceData5() {}
    }
  }

  int level[];
  int label[][];


  int sbasenum;
  int sbase_idlist[];

  int max_distance = 10000;	//
  int disborder = 10;			//

  KeywordData5 key5[];
  SegmentData5 seg5[];



  public LabelData() {
    setModuleID(13);
    pairingVisualizationID = new int[] {12};
    focusClickExecute = true;
  }

//	public void initializePanel(){}

//	public void initializeData(){}

  public void primalData() {
    key5 = new KeywordData5[text.keywordNumber];
    for (int i=0; i<text.keywordNumber; i++) {
      key5[i] = new KeywordData5();
    }

    seg5 = new SegmentData5[text.segmentNumber];
    for (int i=0; i<text.segmentNumber; i++) {
      seg5[i] = new SegmentData5(text.segment[i].sentenceNumber);
    }

    sbase_idlist = getDataIntegerArray(11, 0);
    sbasenum = sbase_idlist.length;

    initialize_distance();
    calc_distance_sen();
    calc_distance_seg();

    new_makelabel();

    count_labels();
  }

  //CONNECTION EXECUTION TEST
  public void miningOperations(int optionNumber) {
    int count2=0;
    int subject_idlist[];
    int subject_check[] = new int[text.keywordNumber];

    switch (optionNumber) {
      case 4502:
      case 0:
        primalData();
        firstData();
        break;

      case 1://case 10100:

        //			int count2=0;
        //			int subject_idlist[];
        //			int subject_check[] = new int[text.keywordNumber];

        count2 = 0;
        for (int j=0; j<text.keywordNumber; j++)
          if (key5[j].label != 4 && key5[j].label != 5 && key5[j].label != -1 && key5[j].label != 0) {
            subject_check[j] = 1;
            count2++;
          } else {
            subject_check[j] = 0;
          }

        subject_idlist = new int[count2];
        count2=0;
        for (int j=0; j<text.keywordNumber; j++)
          if (subject_check[j] == 1) {
            subject_idlist[count2++] = j;
          }

        setDataIntegerArray(1,subject_check);	/////
        break;

      case 2://case 10200:

        //			int count2=0;
        //			int subject_idlist[];
        //			int subject_check[] = new int[text.keywordNumber];

        count2 = 0;
        for (int j=0; j<text.keywordNumber; j++)
          if (key5[j].label != 4 && key5[j].label != 5 && key5[j].label != -1 && key5[j].label != 0) {
//						System.out.println(key5[j].label);

            subject_check[j] = 1;
            count2++;
          } else {
            subject_check[j] = 0;
          }

        subject_idlist = new int[count2];
        count2=0;
        for (int j=0; j<text.keywordNumber; j++)
          if (subject_check[j] == 1) {
            subject_idlist[count2++] = j;
          }

        setDataIntegerArray(2,subject_idlist);	/////
        break;

      case 11:
        primalData();
        setDataDoubleArray(11,makeConclusionValue());
        break;
    }
  }


  public void firstData() {
    if (text.keywordNumber == 0) {
      return;
    }

    level = new int[text.keywordNumber];
    label = new int[text.segmentNumber][text.keywordNumber];

    for (int i=0; i<text.keywordNumber; i++) {
      level[i] = key5[i].level;
    }

    setDataIntegerArray(1355,level);

    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.keywordNumber; j++) {
        label[i][j] = seg5[i].label[j];
      }

    for (int i=0; i<text.segmentNumber; i++) {
      setDataIntegerArray(1355000+i,label[i]);
    }

  }

  public void initialize_distance() {
    // initialize
    for (int i=0; i<text.keywordNumber; i++) {
      key5[i].distance = new int[text.keywordNumber];
      for (int x=0; x<sbasenum; x++) {
        key5[i].distance[x] = max_distance;
      }
    }
  }

  //Measure distance between words in a sentence
  public void calc_distance_sen() {
    for (int i=0; i<text.segmentNumber; i++) {
      seg5[i].label = new int[text.keywordNumber];
      for (int j=0; j<text.keywordNumber; j++) {
        seg5[i].label[j] = -1;
      }
    }

    // for each segment
    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segment[i].sentenceNumber; j++)
        for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++) {
          int id1 = text.segment[i].sentence[j].keywordIDList[k];

          if (seg5[i].label[id1] == -1) {
            seg5[i].label[id1] = 0;
          }

          for (int t=0; t<text.segment[i].sentence[j].keywordNumber; t++) {
            int id2 = text.segment[i].sentence[j].keywordIDList[t];
            int x, dis;

            for (x=0; x<sbasenum; x++)
              if (id2 == sbase_idlist[x]) {
                break;
              }

            if (x == sbasenum) {
              continue;
            }

            dis = k - t;
            if (dis < 0) {
              dis = -dis;
            }

            //RENEW keyword distance
            if (dis < key5[id1].distance[x]) {
              key5[id1].distance[x] = dis;
            }

            if (dis < disborder) {
              seg5[i].label[id1] = 7;
//							text.keyword[id1].apseg[i] = 2;
            }
          }
        }
  }

  public void calc_distance_seg() {
    for (int i=0; i<text.segmentNumber; i++) {
      seg5[i].distance = new int[sbasenum];
      for (int x=0; x<sbasenum; x++) {
        seg5[i].distance[x] = max_distance;
      }
    }

    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segment[i].sentenceNumber; j++)
        for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++)
          for (int x=0; x<sbasenum; x++)
            if (key5[text.segment[i].sentence[j].keywordIDList[k]].distance[x] < seg5[i].distance[x]) {
              seg5[i].distance[x] = key5[text.segment[i].sentence[j].keywordIDList[k]].distance[x];
            }
  }

  public void new_makelabel() {
    for (int i=0; i<text.keywordNumber; i++) {
      key5[i].label = key5[i].level = key5[i].div_count = key5[i].conv_count = 0;
    }

    for (int i=0; i<text.segmentNumber; i++) {
      for (int j=0; j<text.segment[i].sentenceNumber; j++) {
        seg5[i].sen5[j].label = new int[text.segment[i].sentence[j].keywordNumber];
        seg5[i].sen5[j].arc = new int[text.segment[i].sentence[j].keywordNumber];
        for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++) {
          seg5[i].sen5[j].arc[k] = -1;
        }
      }
      seg5[i].level = new int[text.keywordNumber];
    }

    // TOPIC for KEY
    for (int x=0; x<sbasenum; x++) {
      key5[sbase_idlist[x]].label = 1;
    }

    // LABELS for SEG and KEY
    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segment[i].sentenceNumber; j++)
        for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++) {
          int id = text.segment[i].sentence[j].keywordIDList[k];

          //TOPIC
          if (key5[id].label == 1) {
            seg5[i].sen5[j].label[k] = seg5[i].label[id] = 1;	//TOPIC
            seg5[i].sen5[j].arc[k] = 8;//11;		//topic-evolution
            seg5[i].level[id] = key5[id].level = 2;									// TOPIC becomes LEVEL2
            continue;
          }

          if (i == text.keyword[id].appearingSegment[0])
            if (key5[id].label == 0) {	//FIRST appearance
              if (seg5[i].label[id] == 7) {	//RELATED
                seg5[i].sen5[j].label[k] = seg5[i].label[id] = key5[id].label = 2;//SEED
                seg5[i].sen5[j].arc[k] = 1;		//emergence
                seg5[i].level[id] = key5[id].level = 1;							// LEVEL1
              } else {					//NON-RELATED
                seg5[i].sen5[j].label[k] = seg5[i].label[id] = key5[id].label = 4;//NEW
                seg5[i].sen5[j].arc[k] = 6;		//out-emergence
              }
              continue;
            }

          //Appearance more than TWICE
          if (seg5[i].label[id] == 0 || seg5[i].label[id] == 4 || seg5[i].label[id] == 5 || seg5[i].label[id] == 6)
            //Appeared as NON-RELATED in this segment
          {
            if (key5[id].label == 4 || key5[id].label == 5) { // Appeared NEW or BYWAY at the latest (segment)
              if (key5[id].label == 4) {
                seg5[i].sen5[j].arc[k] = 7;  //out-expansion
              } else {
                seg5[i].sen5[j].arc[k] = 10;  //15;		//byway-evolution
              }

              seg5[i].sen5[j].label[k] = seg5[i].label[id] = key5[id].label = 5;//BYWAY
            } else {
              if (key5[id].label == 2 || key5[id].label == 3) { // Appeared SEED or MAIN at the latest (segment)
                seg5[i].sen5[j].arc[k] = 4;		//Divergence
                key5[id].div_count++;
                if (key5[id].level == 1) {
                  seg5[i].level[id] = key5[id].level = 3;  // LEVEL3
                } else {
                  seg5[i].level[id] = key5[id].level++;  // LEVEL3+
                }
              } else {	//(SUB at the latest)
                seg5[i].sen5[j].arc[k] = 11;  //16;		//sub-evolution
              }

              seg5[i].sen5[j].label[k] = seg5[i].label[id] = key5[id].label = 6;//SUB
            }
          } else { //Appeared as RELATED
            if (key5[id].label == 4 || key5[id].label == 5) { // Appeared NEW or BYWAY at the latest (segment)
              seg5[i].sen5[j].label[k] = seg5[i].label[id] = key5[id].label = 2;//SEED(by INC)
              seg5[i].sen5[j].arc[k] = 3;		//incorporation
              key5[id].conv_count++;
              seg5[i].level[id] = key5[id].level = 1;							// LEVEL1
            } else {
              if (key5[id].label == 6) { // Appeared SUB at the latest (segment)
                seg5[i].sen5[j].arc[k] = 5;		//convergence
                key5[id].conv_count++;

                seg5[i].level[id] = key5[id].level++;							// LEVEL4+
              } else {	// Appeared SEED or MAIN at the latest (segment)
                if (key5[id].label == 2) {
                  seg5[i].sen5[j].arc[k] = 2;	//expansion
                  if (key5[id].level == 1) {
                    seg5[i].level[id] = key5[id].level = 2;  // LEVEL2
                  }
                } else {
                  seg5[i].sen5[j].arc[k] = 9;  //13;	//main-evolution
                }
              }
              seg5[i].sen5[j].label[k] = seg5[i].label[id] = key5[id].label = 3;//MAIN
            }
          }
        }
  }

  //count labels and levels
  public void count_labels() {
    //succeed labels from a former segment for words that not appeard
    for (int i=1; i<text.segmentNumber; i++)
      for (int j=0; j<text.keywordNumber; j++)
        if (seg5[i].label[j] == -1) {
          seg5[i].label[j] = seg5[i-1].label[j];
        }
  }

  //////////////FOR MAKING CONCLUSION

  public double[] makeConclusionValue() {
    double conclusionValue[] = new double[text.sentenceNumber];

    int leveltotal[] = new int[5];
    int topictotal = 0;
    int levelsent[] = new int[5];
    int topicsent = 0;

    for (int i=0; i<5; i++) {
      leveltotal[i] = 0;
    }

    int id;

    for (int x=0; x<sbasenum; x++) {
      System.out.println(text.keyword[sbase_idlist[x]].word);
    }

    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segment[i].sentenceNumber; j++)
        for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++) {
          id = text.segment[i].sentence[j].keywordIDList[k];

          if (key5[id].level >= 4) {
            leveltotal[4]++;
          } else {
            leveltotal[key5[id].level]++;
          }

          if (key5[id].label == 1) {
            topictotal ++;
          }
        }

    for (int i=0,v=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segment[i].sentenceNumber; j++,v++) {
        conclusionValue[v] = 0.0;

        for (int t=0; t<5; t++) {
          levelsent[t] = 0;
        }
        topicsent = 0;

        for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++) {
          id = text.segment[i].sentence[j].keywordIDList[k];

          if (key5[id].level >= 4) {
            levelsent[4]++;
          } else {
            levelsent[key5[id].level]++;
          }

          if (key5[id].label == 1) {
            topicsent ++;
          }
        }

        if (topicsent > (double)topictotal/text.sentenceNumber) {
          conclusionValue[v] += 0.5;
        }
        if (levelsent[0] > (double)leveltotal[0]/text.sentenceNumber) {
          conclusionValue[v] += 0.3;
        }
        if (levelsent[1] > (double)leveltotal[1]/text.sentenceNumber) {
          conclusionValue[v] += 0.1;
        }
        if (levelsent[4] > (double)leveltotal[4]/text.sentenceNumber) {
          conclusionValue[v] += 0.1;
        }

        conclusionValue[v] *= 100;

        conclusionValue[v] += (levelsent[1]+levelsent[2]+levelsent[4] - topicsent - levelsent[3]);

      }

    for (int i=0,v=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segment[i].sentenceNumber; j++,v++) {
        if (text.segment[i].sentence[j].keywordNumber > 14) {	//For long sentences
          conclusionValue[v] -= (text.segment[i].sentence[j].keywordNumber - 14)*(text.segment[i].sentence[j].keywordNumber - 14);
        }

        conclusionValue[v] *= (1 + (double)(v+1)/text.sentenceNumber);
      }

    return conclusionValue;
  }
}
