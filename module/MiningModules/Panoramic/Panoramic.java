//
// Mining module for TETDM
// Panoramic.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//

package module.MiningModules.Panoramic;

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.text.*;


public class Panoramic extends MiningModule implements ActionListener {
  PvsR pvs1;
  Inform inf;
  int line;

  JPanel subpanel1, subpanel2;
  JLabel tf1;

  JButton indica, inform, conclude;

  JButton	reset, minus, plus, minus10, plus10;
  JToggleButton comp, back, view, feat;

  String summarized_text;

  double conclusionValue[];
  DecimalFormat df_a = new DecimalFormat("000.00");
  DecimalFormat df_b = new DecimalFormat("0.00");

//	NewMapData map;

  public Panoramic() {
    setModuleID(11);
    pairingVisualizationID = new int[] {1,8};
    focusClickExecute = true;
  }

  public void initializePanel() {}
  {
    line = 10;

//		operationPanel.setLayout(new BorderLayout());

    subpanel1 = new JPanel(new GridLayout());
    operationPanel.add(subpanel1);
    subpanel1.setBackground(Color.CYAN);

    tf1 = new JLabel();
    subpanel1.add(tf1);

    reset = new JButton("Reset Number");
    reset.setBackground(Color.YELLOW);
    reset.addActionListener(this);
    subpanel1.add(reset);

    minus = new JButton("<");
    minus.setBackground(Color.YELLOW);
    minus.addActionListener(this);
    subpanel1.add(minus);

    plus = new JButton(">");
    plus.setBackground(Color.YELLOW);
    plus.addActionListener(this);
    subpanel1.add(plus);

    minus10 = new JButton("<<");
    minus10.setBackground(Color.YELLOW);
    minus10.addActionListener(this);
    subpanel1.add(minus10);

    plus10 = new JButton(">>");
    plus10.setBackground(Color.YELLOW);
    plus10.addActionListener(this);
    subpanel1.add(plus10);



    subpanel2 = new JPanel(new GridLayout());
    operationPanel.add(subpanel2);
    subpanel2.setBackground(Color.RED);



    indica = new JButton("Indicative");
    indica.setBackground(Color.CYAN);
    indica.addActionListener(this);
    subpanel2.add(indica);

    inform = new JButton("Informative");
    inform.setBackground(Color.CYAN);
    inform.addActionListener(this);
    subpanel2.add(inform);

    conclude = new JButton("Conclude");
    conclude.setBackground(Color.RED);
    conclude.addActionListener(this);
    subpanel2.add(conclude);





    comp = new JToggleButton("Add Viewpoint");
    comp.setBackground(Color.CYAN);
    comp.addActionListener(this);
    subpanel2.add(comp);
    comp.setSelected(true);

    back = new JToggleButton("Background");
    back.setBackground(Color.CYAN);
    back.addActionListener(this);
    subpanel2.add(back);
    back.setSelected(true);

    view = new JToggleButton("Viewpoint");
    view.setBackground(Color.CYAN);
    view.addActionListener(this);
    subpanel2.add(view);
    view.setSelected(true);

    feat = new JToggleButton("Feature");
    feat.setBackground(Color.CYAN);
    feat.addActionListener(this);
    subpanel2.add(feat);
    feat.setSelected(true);
  }

  public void initializeData() {
    tf1.setText(Integer.toString(line));
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 4502:
      case 0:
        summarized_text = exepvs();
        //for text display
        resetData();
        setDataString(summarized_text);

        //for fujisan
        make_map_data();

        //for table display
        //set_data();

        comp.setSelected(false);	//
        back.setSelected(false);	//
        break;

      case 1:
        line = 1;
        summarized_text = exepvs();
        resetData();
        setDataString(1,summarized_text);
        break;
    }
  }


  void make_map_data() {
    //for fujisan
    int sbase,back,feat,topic2;
    back = feat = topic2 = 0;
    sbase = pvs1.sbasenum;

    int sbase_idlist[] = new int[sbase];
    for (int i=0; i<sbase; i++) {
      sbase_idlist[i] = pvs1.sbase_idlist[i];
    }

//		System.out.println("sbase= "+sbase);

    for (int i=0; i<pvs1.keyword_num; i++) { // Must consider the order of value
      if (pvs1.key2[i].score[1] > 0 && topic2 < 10) {	//TOPIC with low value
        int j;
        for (j=0; j<sbase; j++)
          if (i == sbase_idlist[j]) {
            break;
          }

        if (j == sbase) {
          topic2++;
        }
      } else if (pvs1.key2[i].score[0] > 0 && back < 15) {
        back++;
      } else if (pvs1.key2[i].score[2] > 0 && feat < 15) {
        feat++;
      }
    }

    int topic_idlist[] = new int[topic2];
    int back_idlist[] = new int[back];
    int feat_idlist[] = new int[feat];

    back = feat = topic2 = 0;
    for (int i=0; i<pvs1.keyword_num; i++) {
      if (pvs1.key2[i].score[1] > 0 && topic2 < 10) {	//TOPIC with low value
        int j;
        for (j=0; j<sbase; j++)
          if (i == sbase_idlist[j]) {
            break;
          }

        if (j == sbase) {
          topic_idlist[topic2++] = i;
        }
      } else if (pvs1.key2[i].score[0] > 0 && back < 15) {
        back_idlist[back++] = i;
      } else if (pvs1.key2[i].score[2] > 0 && feat < 15) {
        feat_idlist[feat++] = i;
      }

    }

    setDataIntegerArray(0,sbase_idlist);			// option is not intend to some specified modules
    setDataIntegerArray(1,topic_idlist);
    setDataIntegerArray(2,back_idlist);
    setDataIntegerArray(3,feat_idlist);
  }



  //Action listener
  public void actionPerformed(ActionEvent e) {
//		insideOfActionPerformed(e);

    /*
    //Execute summarization
    if(e.getSource() == execute)
    {
    	if(indica.isSelected())
    	{
    		summarized_text = exepvs();
                resetData();
    		setDataString(summarized_text);
    		make_map_data();//
    		displayOperations(0);
    	}
    	else
    	{
    		summarized_text = exeinf();
                resetData();
    		setDataString(summarized_text);
    		displayOperations(0);
    	}
     }
    */

    if (e.getSource() == indica) {
      summarized_text = exepvs();
      resetData();
      setDataString(summarized_text);
      make_map_data();//
      displayOperations(0);
    }

    if (e.getSource() == inform) {
      summarized_text = exeinf();
      resetData();
      setDataString(summarized_text);
      displayOperations(0);
    }

    if (e.getSource() == conclude) {
      conclusionValue = getDataDoubleArray(13,11);
      summarized_text = createConclusion(line);
      resetData();
      setDataString(summarized_text);
      displayOperations(0);
    }

    //Input a number of output sentences
    if (e.getSource() == minus)
      if (line > 0) {
        line--;
        tf1.setText(Integer.toString(line));
      }

    if (e.getSource() == plus) {
      line++;
      tf1.setText(Integer.toString(line));
    }

    if (e.getSource() == minus10)
      if (line > 9) {
        line -= 10;
        tf1.setText(Integer.toString(line));
      }

    if (e.getSource() == plus10) {
      line += 10;
      tf1.setText(Integer.toString(line));
    }

    if (e.getSource() == reset) {
      line = 10;
      tf1.setText(Integer.toString(line));
    }
  }

  //FOR CONCLUSION
  public String createConclusion(int n) {
//		double conclusionValue[] = new double[text.sentenceNumber];
    boolean output[] = new boolean[text.sentenceNumber];

    int num[] = new int[text.sentenceNumber];
    double sort_value[] = new double[text.sentenceNumber];

    for (int i=0; i<text.sentenceNumber; i++) {
      output[i] = false;
    }

//		makeConclusionValue(conclusionValue);


    for (int i=0; i<text.sentenceNumber; i++) {
      sort_value[i] = conclusionValue[i];
    }


    Qsort.initializeIndex(num, text.sentenceNumber-1);
    Qsort.quicksort(sort_value, num, text.sentenceNumber-1);	//Except the last sentence

    StringWriter sw1 = new StringWriter();
    BufferedWriter bw1 = new BufferedWriter(sw1);

    try {
      if (text.sentenceNumber <= n) {
        for (int i=0; i<text.sentenceNumber; i++) {
          bw1.write(text.sentence[i].sentenceText);
          bw1.newLine();
        }
      } else {
        for (int i=0; i<n-1; i++) {
          output[num[i]] = true;
        }
        output[text.sentenceNumber-1] = true;

        for (int i=0; i<text.sentenceNumber; i++)
          if (output[i]) {
            bw1.write(text.sentence[i].sentenceText+"("+df_a.format(conclusionValue[i])+")");
            bw1.newLine();
          }
      }
      bw1.write("---Conclusions ("+n+"/"+text.sentenceNumber+")---");
      bw1.flush();
    } catch (Exception e) {
      System.out.println("Error in output_conclusion");
    }

    return sw1.toString();
  }

  //FOR PVS
  public String exepvs() {
    boolean c=false;
    double b=0,v=0,f=0;
    if (comp.isSelected()) {
      c = true;
    }
    if (back.isSelected()) {
      b = 1;
    }
    if (view.isSelected()) {
      v = 1;
    }
    if (feat.isSelected()) {
      f = 1;
    }

    pvs1 = new PvsR();
    pvs1.set_text(text);
    pvs1.set_parameters(line, c,b,v,f);
    pvs1.exec();
    return pvs1.output_summary();
  }

  //FOR INF
  public String exeinf() {

    double b=0,v=0,f=0;

    if (back.isSelected()) {
      b = 1;
    }
    if (view.isSelected()) {
      v = 1;
    }
    if (feat.isSelected()) {
      f = 1;
    }

    inf = new Inform();
    inf.set_text(text);
    inf.set_parameters(line, b,v,f);

    inf.set_sbase(pvs1.sbase_idlist, pvs1.sbasenum);

    inf.exec();
    return inf.output_summary();
  }

  /*
  	public int[] get_int_array_data(int n)
  	{
  		boolean c=true;
  		double b=1.0, v=1.0, f=1.0;

  		pvs1 = new PvsR();
  		pvs1.set_text(text);
  		pvs1.set_parameters(line, c,b,v,f);
  		pvs1.exec();

  		int sbasenum;
  		int sbase_idlist[];

  		sbasenum = pvs1.sbasenum;
  		sbase_idlist = new int[sbasenum];
  		for(int i=0;i<sbasenum;i++)
  			sbase_idlist[i] = pvs1.sbase_idlist[i];

  		return sbase_idlist;
  	}
  */

  class SentenceData2 {
    double score[] = new double[3];	//Score of a sentence(background, viewpoint, feature)
    int rank[] = new int[3];		//Rank of a sentence(background, viewpoint, feature)
    int ranktotal;					//Sum of ranks of sentence
    int finalrank;					//Final rank of a sentence

    SentenceData2() {}
  }

  class KeywordData2 { // extends KeywordData
    double score[] = new double[3];	//Score of a sentence(background, viewpoint, feature)

    KeywordData2() {}
  }

  class PvsR {
    TextData text;

    int sentence_num;	//Number of sentences
    SentenceData sen[];
    SentenceData2 sen2[];

    int keyword_num;	//Number of keywords(candidates)
    KeywordData key[];
    KeywordData2 key2[];

    int freqnum = 0;	//Number of background keywords
    int freq_idlist[];

    int sbasenum = 0;	//Number of viewpoint keywords
    int sbase_idlist[];
    int sbasemin = 2;	//Minimum number of viewpoint keywords
    int sbasemax = 7;	//Maximum number of viewpoint keywords

    int select;				//Given number of output sentences
    int select_number = 10;	//Practical number of output sentences
    double select_rate=0.3;	//Default summarization rate, activate when select_number = 0
    double weight[] = {1.0, 1.0, 1.0};	// weight[back,topic,feature]; parameters for output summarization

    boolean update_sbase = true;

    //rate of keywords against the number of all keywords(candidate)
    double CUTODDSB = 0.15;		// Rate of background keywords
    double CUTODDST	= 0.05;		// Rate of viewpoint keywords
    double CUTODDSF	= 0.10;		// Rate of feature keywords

    PvsR() {}

    public void set_text(TextData tex) {
      text = tex;

      //Copy addresses for SentenceData
      sentence_num = text.sentenceNumber;
      sen = new SentenceData[sentence_num];
      sen2 = new SentenceData2[sentence_num];

      for (int i=0; i<sentence_num; i++) {

        sen[i] = text.sentence[i];
        sen2[i] = new SentenceData2();
      }

      //Copy addresses for KeywordData
      keyword_num = text.keywordNumber;
      key = new KeywordData[keyword_num];
      key2 = new KeywordData2[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        key[i] = text.keyword[i];
        key2[i] = new KeywordData2();
      }

      sbasenum = 0;
      sbase_idlist = new int[sbasemax*2];
    }

    public void set_parameters(int sel, boolean update_s, double w1, double w2, double w3) {
      select = sel;
      update_sbase = update_s;

      weight[0] = w1;
      weight[1] = w2;
      weight[2] = w3;
    }

    public void set_sbase(int idlist[], int num) {
      sbasenum = num;

      for (int i=0; i<sbasenum; i++) {
        sbase_idlist[i] = idlist[i];
      }
    }

    public void exec() {
      if (make_parameters()) {
        make_summary();
      }
    }





    //Display viewpoint keywords list
    public void show_sbase() {
      System.out.println("Number of TOPIC keywords = "+sbasenum);

      for (int i=0; i<sbasenum; i++) {
        System.out.println(key[sbase_idlist[i]].word);
      }
    }

    public boolean make_parameters() {
      if (sentence_num == 0 || keyword_num == 0) {
        return false;
      }

      //Give number of output sentences
      select_number = select;
      if (select_number > sentence_num) {
        select_number = sentence_num;
      }

      if (select_number == 0) {
        select_number = (int)((double)sentence_num * select_rate);
        if (select_number == 0) {
          select_number = 1;
        }
      }

      //Create background keywords
      double border = calc_keyparam();	    	//Decide the number of background keywords
      make_freqlist();

      //		System.out.println("Number of BASIC - TOPIC keywords : "+freqnum+" - "+sbasenum);
      //			for(int i=0;i<freqnum;i++)
      //				System.out.println(key[freq_idlist[i]].frequency+":"+key[freq_idlist[i]].word);

      //Create viewpoint keywords when viewpoint keywords do not exist

      if (sbasenum == 0) {
        for (int i=0; i<keyword_num; i++)
          if (text.focus.focusKeywords[i] && sbasenum < sbasemax) {	//////////////////FOR LINKAGE
            sbase_idlist[sbasenum++] = i;
          }
      }


      if (sbasenum == 0) {
        //Decide the number of viewpoint keywords
        sbasenum = (int)(1 + (double)border/5.0);
        if (sbasenum > sbasemax) {
          sbasenum = sbasemax;
        }
        make_sbaselist();
      }

      //		show_sbase();

      if (sbasenum == 0) {	//When viewpoint keywords were not prepared
        return false;
      }

      return true;
    }

    public double calc_keyparam() {
      int maxfreq=0;
      double border;

      for (int i=0; i<keyword_num; i++)
        if (key[i].frequency > maxfreq) {
          maxfreq = key[i].frequency;
        }

      int freq[] = new int[maxfreq+1];
      for (int i=0; i<=maxfreq; i++) {
        freq[i] = 0;
      }

      //Create frequency distribution, Evaluate words by frequency
      for (int i=0; i<keyword_num; i++) {
        freq[key[i].frequency]++;
        key2[i].score[0] = key[i].frequency;
      }

      //Temporary number of background keywords
      if (keyword_num >= 100) {
        border = 20.0 + (double)keyword_num/100.0;
      } else {
        border = ((double)keyword_num * 0.2);
      }

      //Adjustment of the number of background keywords(Include keywords which have the same frequency)
      for (int i=maxfreq,ruikei=0; i>0; i--)
        if (freq[i] != 0 || i<10) {
          ruikei += freq[i];
          if (ruikei > border && ruikei-freq[i] <= border) {
            if (ruikei-border >= border-(ruikei-freq[i])) {
              freqnum = ruikei-freq[i];
            } else {
              freqnum = ruikei;
            }
          }
        }
      if (freqnum == 0) {
        freqnum = 1;
      }

      freq_idlist = new int[freqnum];

      return border;
    }

    public void make_freqlist() {
      int num[] = new int[keyword_num];
      double sort_value[] = new double[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        sort_value[i] = key[i].frequency;
      }

      Qsort.initializeIndex(num, keyword_num);
      Qsort.quicksort(sort_value, num, keyword_num);

      for (int i=0; i<freqnum; i++) {
        freq_idlist[i] = num[i];
      }
    }

    //Count number of sentences including both words id1 and id2: using original_id
    public int common_appear(int id1, int id2) {
      int count=0;

      for (int i=0,j=0; i< key[id1].sentenceFrequency && j< key[id2].sentenceFrequency;)
        if (key[id1].appearingSentence[i] == key[id2].appearingSentence[j]) {
          count++;
          i++;
          j++;
        } else {
          if (key[id1].appearingSentence[i] < key[id2].appearingSentence[j]) {
            i++;
          } else {
            j++;
          }
        }
      return count;
    }

    //Return top n suffixes of value
    public void make_toplist(double value[], int n, int toplist[], int topn) {
      int num[] = new int[keyword_num];
      double sort_value[] = new double[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        sort_value[i] = value[i];
      }

      Qsort.initializeIndex(num, keyword_num);
      Qsort.quicksort(sort_value, num, keyword_num);

      if (topn > keyword_num) {
        topn = keyword_num;  /////////////NEED TO BE CORRECTED [topn should be revised]
      }

      for (int i=0; i<topn; i++) {
        toplist[i] = num[i];
      }
    }

    //Create viewpoint keyword list(create from background keywords)
    public void make_sbaselist() {
      int id1, id2;
      double shucho_up,shucho_down;
      int link1,link2,link12;
      double score[] = new double[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        key2[i].score[1] = 0;
      }

      for (int i=0; i<freqnum; i++) {
        id1 = freq_idlist[i];
        shucho_up = 0.0;

        for (int j=0; j<freqnum; j++) {
          link12 = link1 = link2 = 0;

          id2 = freq_idlist[j];
          link12 = common_appear(id1,id2);
          link1 = key[id1].sentenceFrequency;
          link2 = key[id2].sentenceFrequency;

          if (link2 != 0) {
            shucho_up += ((double)link12/(double)link2);
          }
        }
        key2[id1].score[1] = shucho_up/(double)freqnum;		//Top sbasenum words in this score become viewpoint
      }

      for (int i=0; i<keyword_num; i++) {
        score[i] = key2[i].score[1];
      }

      make_toplist(score, keyword_num, sbase_idlist, sbasenum);
    }

    //Return 1: when revised 0: NOT revised
    public int sbase_selection(int new_sbase_idlist[]) {
      int i,j,k;
      int searchnum, old_sbasenum;
      int revised = 0;

      searchnum = sbasenum;
      if (searchnum < sbasemin) {
        searchnum = sbasemin;
      }

      old_sbasenum = sbasenum;

      for (i=0; i<searchnum; i++) {	//Each word in New top [searchnum] word
        for (j=0; j<old_sbasenum; j++) //Compare with each word in the old list
          if (new_sbase_idlist[i] == sbase_idlist[j]) {
            break;
          }

        if (j == sbasenum) {	//If the word is not included in the old list, add to the list //If all words are added, the number is sbasenum*2
          sbase_idlist[sbasenum++] = new_sbase_idlist[i];
          revised = 1;
        }
      }

      i--;
      k = i;
      for (; i<keyword_num; i++) {	//Add a word which has the same score
        if (key2[new_sbase_idlist[i]].score[1] != key2[new_sbase_idlist[k]].score[1]) {
          break;
        }

        for (j=0; j<old_sbasenum; j++) //Compare with each word in the old list
          if (new_sbase_idlist[i] == sbase_idlist[j]) {
            break;
          }

        if (j == sbasenum && sbasenum < sbasemax) {	//If the word is not included in the old list, add to the list
          sbase_idlist[sbasenum++] = new_sbase_idlist[i];
          revised = 1;
        }
      }
      return revised;
    }

    //Main function for Panoramic View System
    public boolean make_sbase_feat() {	//Sbase will not be revised if update_sbase == false
      int id1, id2;
      double shucho_up,shucho_down;
      int link1,link2,link12;

      int new_sbase_idlist[] = new int[keyword_num];
      double score[] = new double[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        id1 = i;
        shucho_up = 0.0;
        shucho_down = 0.0;

        for (int j=0; j<sbasenum; j++) {
          link12 = link1 = link2 = 0;

          id2 = sbase_idlist[j];
          link12 = common_appear(id1,id2);
          link1 = key[id1].sentenceFrequency;
          link2 = key[id2].sentenceFrequency;

          if (link2 != 0) {
            shucho_up += ((double)link12/(double)link2);
          }

          if (link1 != 0) {
            shucho_down += ((double)link12/(double)link1);
          }
        }
        key2[id1].score[1] = (shucho_up*shucho_down)/((double)sbasenum*(double)sbasenum);
        key2[id1].score[2] = shucho_down/(double)sbasenum;
      }

      for (int i=0; i<keyword_num; i++) {
        score[i] = key2[i].score[1];
      }

      make_toplist(score, keyword_num, new_sbase_idlist, sbasemax);

      if (update_sbase == false) {
        return false;
      }

      if (sbase_selection(new_sbase_idlist) == 0) {
        return false;
      }

      if (sbasenum >= sbasemax) {
        return false;
      }

      return true;
    }

    //Set evaluation value 0 except for the words in the decided rate
    public int wordscore_cut(int type, double odds) {
      int count,bn;
      double border=0.0;
      double score[] = new double[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        score[i] = key2[i].score[type];
      }

      bn = (int)((double)keyword_num*odds);
      border = Qsort.quickselect(score, keyword_num, bn);

      //		System.out.println("= "+border+"(Ratio="+odds+")");

      count = 0;
      for (int i=0; i<keyword_num; i++) {
        if (key2[i].score[type] < border) {
          key2[i].score[type] = 0;
        } else if (key2[i].score[type] > 0) {
          count++;
        }
      }
      return count;
    }

    void wordscore_change(boolean hindocut, boolean featcut) { // Background NOT viewpoint, Feature NOT viewpoint
      int va,vb,vc;	//Cutting border for background, viewpoint, feature

      //Viewpoint keywords, value cut
      //		System.out.println("Borders, TOPIC:");
      vb = wordscore_cut(1,CUTODDST);

      //Cut dupulication of viewpoint keywords
      for (int i=0; i<keyword_num; i++)
        if (key2[i].score[1]> 0) {
          if (hindocut) {
            key2[i].score[0] = 0;
          }
          if (featcut) {
            key2[i].score[2] = 0;
          }
        }

      //Background, Feature keywords, value cut
      //	    System.out.println("BASIC:");
      va = wordscore_cut(0,CUTODDSB);

      //		System.out.println("FEATURE:");
      vc = wordscore_cut(2,CUTODDSF);

      //		System.out.println("BASIC:"+va+"/"+keyword_num+",TOPIC:"+vb+"/"+keyword_num+",FEATURE:"+vc+"/"+keyword_num);
      //		System.out.println("BASIC:"+(100*va/keyword_num)+"%,TOPIC:"+(100*vb/keyword_num)+"%,FEATURE:"+(100*vc/keyword_num)+"%");
    }

    // Create evaluation values for each sentence, Background, Viewpoint, Feature
    public void make_bunscore() {
      int t;
      int wordcount[] = new int[keyword_num];

      for (int i=0; i<keyword_num; i++) { //Plus evaluation value, first appearance ONLY
        wordcount[i] = 0;
      }

      for (int i=0; i<sentence_num; i++) {
        sen2[i].score[0] = sen2[i].score[1] = sen2[i].score[2] = 0;
        for (int j=0; j<sen[i].keywordNumber; j++) {
          t = sen[i].keywordIDList[j];
          if (wordcount[t] == 0) {
            sen2[i].score[0] += key2[t].score[0];
            sen2[i].score[1] += key2[t].score[1];
            sen2[i].score[2] += key2[t].score[2];
            wordcount[t] = 1;
          }
        }
        for (int j=0; j<sen[i].keywordNumber; j++) {	//Plus evaluation value, first appearance ONLY
          wordcount[sen[i].keywordIDList[j]] = 0;
        }
      }
    }

    //Calculate rank by evaluation value for each sentence
    public void make_bunrank() {
      int num[] = new int[sentence_num];
      double sort_value[] = new double[sentence_num];

      for (int j=0; j<3; j++) {
        for (int i=0; i<sentence_num; i++) {
          sort_value[i] = sen2[i].score[j];
        }

        Qsort.initializeIndex(num, sentence_num);
        Qsort.quicksort(sort_value, num, sentence_num);

        sen2[num[0]].rank[j] = 0;
        for (int i=1, rank=0; i<sentence_num; i++) {
          if (sen2[num[i]].score[j] != sen2[num[rank]].score[j]) {
            rank = i;
          }

          sen2[num[i]].rank[j] = rank;
        }
      }
    }

    //Calcuate the final rank of each sentence
    public void make_finalscore() {
      int num[] = new int[sentence_num];
      double sort_value[] = new double[sentence_num];

      for (int i=0; i<sentence_num; i++) {
        sen2[i].ranktotal = 0;
      }

      for (int i=0; i<sentence_num; i++)
        for (int j=0; j<3; j++) {
          sen2[i].ranktotal += (sen2[i].rank[j] * weight[j]);
        }

      //Sorting ranktotal
      for (int i=0; i<sentence_num; i++) {
        sort_value[i] = sentence_num * 3 - sen2[i].ranktotal;
      }

      Qsort.initializeIndex(num,sentence_num);
      Qsort.quicksort(sort_value,num,sentence_num);

      sen2[num[0]].finalrank = 0;
      for (int i=1,rank=0; i<sentence_num; i++) {
        if (sen2[num[i]].finalrank != sen2[num[rank]].finalrank) {
          rank = i;
        }

        sen2[num[i]].finalrank = i;
      }
    }

    public void make_summary() { //Viewpoints are not revised when flag == 0
      //Execute Panoramic View System
      for (int i=1; make_sbase_feat(); i++) {
        ;  //Loop Condition: sbase is not reached to MAX && flag != 0 -> sbaselist is revised
      }

      //		show_sbase();

      wordscore_change((weight[0]>0),(weight[1]>0));	//Create evaluation values for each word
      make_bunscore();		// Create evaluation values for each sentence
      make_bunrank();			// Create ranks for each sentence
      make_finalscore();		// Create final evaluation values for each sentence
    }

    public String output_summary() {
      StringWriter sw1 = new StringWriter();
      BufferedWriter bw1 = new BufferedWriter(sw1);
      int c = 0;
      try {
        for (int i=0; i<sentence_num; i++)
          if (sen2[i].finalrank < select_number) {
            //					bw1.write(i+"("+(sen2[i].finalrank+1)+":"+(sen2[i].ranktotal+3)+"["+
            //					(sen2[i].rank[0]+1)+","+(sen2[i].rank[1]+1)+","+(sen2[i].rank[2]+1)+"]["+
            //					sen2[i].score[0]+","+sen2[i].score[1]+","+sen2[i].score[2]+"])");
            bw1.write(sen[i].sentenceText);
            bw1.newLine();
            c++;
          }
        select_number = c;
        bw1.write("---Panoramic View System InDicative("+select_number+"/"+text.sentenceNumber+")---");
        bw1.newLine();
        bw1.flush();
      } catch (Exception e) {
        System.out.println("Error in output_summary");
      }
      return sw1.toString();
    }
  }

///////////////////

  class SentenceData3 {
    int selected;
    double textv[];
    double segv[];
    double totalv[];

    int textrank;
    int segrank;


    double textvalue;
    double segvalue;
    double totalvalue;

    double final_textvalue;
    double final_segvalue;
    double final_totalvalue;

    SentenceData3() {}
  }

  class SegmentData3 {
    int freqmax;
    double freqave;
    double freqstd;

    SegmentData3() {}
  }

  class KeywordData3 { // extends KeywordData
    double ave_std_num;
    double ave_num;
    int extracted;

    double subject_value;
    double freq_value;

    double value;
    int type;

    KeywordData3() {}

    int calc_topicvalue(int i) {
      if (text.keyword[i].segmentFrequencyAsSubject > 1 || (ave_std_num > 0 && ave_num > 1)) {
        value = freq_value + subject_value;
        return 1;
      } else {
        value = 0;
        return 0;
      }
    }
  }

  public class Inform {
    TextData text;
    TextData segtext[];
    int select;
    int total_select;

    int sentence_num;	//Number of sentences///////
    SentenceData sen[];
    SentenceData3 sen3[];/////////


    int keyword_num;	//Number of keywords(candidates)///////
    KeywordData key[];
    KeywordData3 key3[];/////////


    int segment_num;
    SegmentData3 seg3[];



    int sbasenum;	//Number of viewpoint keywords
    int sbase_idlist[];
    int sbasemax = 7;



    //	Pvs pvs1;

    double weight[] = {0.0, 1.0, 1.0};	//Weights for PVS: background, viewpoint, feature
    boolean addsbase = false;

    int topic_max;		//Maximum number of topic keywords, MIXed main and sub
    int topic_idlist[];	//ID list of topic keywords, Main/Sub
    int maintopic_num;	//Number of main topics
    int subtopic_num;	//Number of sub topics
    int maintopic_sentenceNumber;	//Number of sentences including main topics

    int max_topickey = 10;	//Maximum number of topic keywords

    Inform() {}

    public void make_summary() {
      /***/

//			for(int i=0;i<text.keywordNumber;i++)
//				text.keyword[i].frequencyAsSubject = text.keyword[i].frequency;


      ////1. Create topic keywords

      calc_values();		//Calculate evaluation for each word by frequency and frequency as a subject in all texts
      //		System.out.println("-----------------------");

      for (int i=0; i<text.keywordNumber; i++) {
        key3[i].ave_std_num = key3[i].ave_num = key3[i].extracted = 0;
      }

      for (int i=0; i<text.segmentNumber; i++) {
        calc_ave_std(i);		//Calculate average of frequency and standard deviation in each segment
        /**/
        System.out.println((i+1)+":freqave="+seg3[i].freqave+" freqave+freqstd="+(seg3[i].freqave + seg3[i].freqstd));
        /**/
        for (int j=0; j<text.keywordNumber; j++)
          /**/{
          System.out.println(text.keyword[j].word+":"+text.keyword[j].frequencyInSegment[i]);
        }
        freq_ave_std_check(i);	//Frequency evaluation for each keyword in each segment
      }

      topic_max = 0;
      for (int i=0; i<text.keywordNumber; i++) {
        topic_max += key3[i].calc_topicvalue(i);  //Select keywords candidate
      }

      /**/		System.out.println("topic_max = "+topic_max);

      for (int i=0; i<text.keywordNumber; i++)
//				if(key3[i].value > 0)
      {
        System.out.println(text.keyword[i].word+": value = "+key3[i].value+" : FREQ = "+text.keyword[i].frequency);
      }

      topic_idlist = new int[text.keywordNumber];		//ID list for Main topics and Sub topics
      make_topiclist();			//Create topic keyword list
      count_topic_sentenceNumber();	//Count number of sentences including main topics

      //Allocate memory for evaluation values of each sentence related to each topic
      for (int i=0; i<text.sentenceNumber; i++) {
        sen3[i].textv = new double[max_topickey];
        sen3[i].segv = new double[max_topickey];
        sen3[i].totalv = new double[max_topickey];
      }

      ////2. Create sentence evaluation values according to topic keywords

      segtext = new TextData[text.segmentNumber];
      for (int i=0; i<text.segmentNumber; i++) {
        segtext[i] = new TextData();				//Create TextData for each segment

        segtext[i].originalText = text.segment[i].segmentText;		//Prepare original text

        segtext[i].segmentNumber = 1;
        segtext[i].segment = new SegmentData[1];
        segtext[i].segment[0] = new SegmentData();			//Prepare SegmentData
        segtext[i].segment[0].segmentText = segtext[i].originalText;
        segtext[i].segment[0].positionOfSegment = 0;


        segtext[i].sentenceNumber = segtext[i].segment[0].sentenceNumber = text.segment[i].sentenceNumber;
        segtext[i].keywordNumber = text.keywordNumber;

        segtext[i].sentenceToSegment = new int[segtext[i].sentenceNumber];		//Prepare [sen2seg] table
        for (int j=0; j<segtext[i].segment[0].sentenceNumber; j++) {
          segtext[i].sentenceToSegment[j] = 0;
        }

        //Copy SentenceData

        segtext[i].sentence = new SentenceData[segtext[i].sentenceNumber];

        segtext[i].segment[0].sentence = new SentenceData[segtext[i].segment[0].sentenceNumber];
        for (int j=0; j<segtext[i].segment[0].sentenceNumber; j++) {
          segtext[i].segment[0].sentence[j] = new SentenceData();
          segtext[i].segment[0].sentence[j].sentenceText = text.segment[i].sentence[j].sentenceText;
          segtext[i].segment[0].sentence[j].positionOfSegment = text.segment[i].sentence[j].positionOfSegment;
          segtext[i].segment[0].sentence[j].positionOfSentence = text.segment[i].sentence[j].positionOfSentence;
          segtext[i].segment[0].sentence[j].keywordNumber = text.segment[i].sentence[j].keywordNumber;
          segtext[i].segment[0].sentence[j].keywordIDList = text.segment[i].sentence[j].keywordIDList;

          segtext[i].sentence[j] = segtext[i].segment[0].sentence[j];
        }

        //Copy KeywordData

        segtext[i].keyword = new KeywordData[text.keywordNumber];
        for (int j=0; j<segtext[i].keywordNumber; j++) {
          segtext[i].keyword[j] = new KeywordData();
          segtext[i].keyword[j].word = text.keyword[j].word;
          segtext[i].keyword[j].frequency = 0;

          //FREQUENCY AS SUBJECT
          segtext[i].keyword[j].frequencyAsSubject = segtext[i].keyword[j].segmentFrequencyAsSubject = text.keyword[j].segmentFrequencyAsSubject;
          segtext[i].keyword[j].lastAppearingSegment = 0;
        }

        for (int j=0; j<segtext[i].sentenceNumber; j++)	//Update frequency(times) of KeywordData
          for (int k=0; k<segtext[i].segment[0].sentence[j].keywordNumber; k++) {
            segtext[i].keyword[segtext[i].segment[0].sentence[j].keywordIDList[k]].frequency++;
          }




        //Calculate list of appeared line for each keyword, frequency in a segment(apline, apnum, lastline, seg_times[], seg_stimes[])
        segtext[i].createKeywordAppearance();

      }

      for (int i=0; i<text.sentenceNumber; i++) {
        sen3[i].selected = -1;
      }

      System.out.println("MAINTOPIC_NUM = "+maintopic_num +" / "+max_topickey);

      //Extract related sentences for main topics
      total_select = 0;
      for (int t=0; t < maintopic_num && total_select < select && t < max_topickey; t++) {
        pvs_for_a_key(t);
      }

      //Extract related sentences for sub topics
      int t;
      for (t=maintopic_num; t < maintopic_num + subtopic_num && total_select < select && t < max_topickey; t++) {
        pvs_for_a_key(t);
      }


      /*
      System.out.println("MAIN/SUB TOPICS:");
      for(int i=0; i < max_topickey;i++)
      	System.out.println(FILEIO.print_double(text.keyword[topic_idlist[i]].value)+":"+text.keyword[topic_idlist[i]].word+"("+text.keyword[topic_idlist[i]].type+")"+
      					   FILEIO.print_double(text.keyword[topic_idlist[i]].subject_value)+" "+FILEIO.print_double(text.keyword[topic_idlist[i]].freq_value)+" "+
      					   key3[topic_idlist[i]].subject+" "+text.keyword[topic_idlist[i]].frequency+" "+text.keyword[topic_idlist[i]].apnum+" --> "+key3[topic_idlist[i]].extracted);

      System.out.println(t+"/"+maintopic_num+"+"+subtopic_num+"/"+max_topickey);*/
    }


    public void set_text(TextData tex) {
      text = tex;

      //Copy addresses for SentenceData
      sentence_num = text.sentenceNumber;
      sen = new SentenceData[sentence_num];
      sen3 = new SentenceData3[sentence_num];

      for (int i=0; i<sentence_num; i++) {
        sen[i] = text.sentence[i];
        sen3[i] = new SentenceData3();
      }

      //Copy addresses for SegmentData
      segment_num = text.sentenceNumber;
      seg3 = new SegmentData3[segment_num];

      for (int i=0; i<segment_num; i++) {
        seg3[i] = new SegmentData3();
      }

      //Copy addresses for KeywordData
      keyword_num = text.keywordNumber;
      key = new KeywordData[keyword_num];
      key3 = new KeywordData3[keyword_num];

      for (int i=0; i<keyword_num; i++) {
        key[i] = text.keyword[i];
        key3[i] = new KeywordData3();
      }

      sbasenum = 0;
      sbase_idlist = new int[sbasemax*2];
    }

    public void set_parameters(int sel, double w1, double w2, double w3) {
      select = sel;

      weight[0] = w1;
      weight[1] = w2;
      weight[2] = w3;
    }

    public void set_sbase(int idlist[], int num) {
      sbasenum = num;

      for (int i=0; i<sbasenum; i++) {
        sbase_idlist[i] = idlist[i];
      }
    }

    public void exec() {
//			if(make_parameters())
      make_summary();
    }


    //Calculate frequency evaluation values and frequency as a subject for each word
    public void calc_values() {
      int maxf = 0, maxsubject=0;

      for (int i=0; i<text.keywordNumber; i++) {
        if (text.keyword[i].frequency > maxf) {
          maxf = text.keyword[i].frequency;
        }
        if (text.keyword[i].frequencyAsSubject > maxsubject) {
          maxsubject = text.keyword[i].frequencyAsSubject;
        }
      }

      for (int i=0; i<text.keywordNumber; i++) {
        key3[i].freq_value = text.keyword[i].frequency / (double)maxf;
        key3[i].subject_value = text.keyword[i].frequencyAsSubject / (double)maxsubject;
        System.out.println(text.keyword[i].word+":"+key3[i].freq_value+" "+key3[i].subject_value);
      }
    }

    //Calculate average of frequency and standard deviation in a segment
    public void calc_ave_std(int seg_n) {
      double ave2 = 0.0;
      int c=0;

      seg3[seg_n].freqmax = 0;
      seg3[seg_n].freqave = 0.0;
      seg3[seg_n].freqstd = 0.0;

      for (int i=0; i<text.keywordNumber; i++) {
        if (text.keyword[i].frequencyInSegment[seg_n] > seg3[seg_n].freqmax) {
          seg3[seg_n].freqmax = text.keyword[i].frequencyInSegment[seg_n];
        }

        if (text.keyword[i].frequencyInSegment[seg_n] > 0) {
          seg3[seg_n].freqave += text.keyword[i].frequencyInSegment[seg_n];
          ave2 += text.keyword[i].frequencyInSegment[seg_n] * text.keyword[i].frequencyInSegment[seg_n];
          c++;
        }
      }

      seg3[seg_n].freqave /= (double)text.keywordNumber;
      ave2 /= (double)text.keywordNumber;
      seg3[seg_n].freqstd = ave2 - (seg3[seg_n].freqave)*(seg3[seg_n].freqave);
      seg3[seg_n].freqstd = Math.sqrt(seg3[seg_n].freqstd);
    }

    ////Frequency evaluation of keywords in each segment
    public void freq_ave_std_check(int seg_n) {
      for (int i=0; i<text.keywordNumber; i++) {
        if (text.keyword[i].frequencyInSegment[seg_n] > seg3[seg_n].freqave + seg3[seg_n].freqstd) {
          key3[i].ave_std_num++;
        }

        if (text.keyword[i].frequencyInSegment[seg_n] > seg3[seg_n].freqave) {
          key3[i].ave_num++;
        }
      }
    }

    //Create topic keywords
    public void make_topiclist() {
      int num[] = new int[text.keywordNumber];
      double sort_value[] = new double[text.keywordNumber];

      for (int i=0; i<text.keywordNumber; i++) {
        sort_value[i] = key3[i].value;
      }

      Qsort.initializeIndex(num, text.keywordNumber);
      Qsort.quicksort(sort_value, num, text.keywordNumber);

      for (int i=0; i<text.keywordNumber; i++) {
        topic_idlist[i] = num[i];
      }

      //Judgement of Main or Sub
      maintopic_num = subtopic_num = 0;
      for (int i=0; i<topic_max; i++) {
        while (key3[topic_idlist[i]].value >= key3[topic_idlist[0]].value * 0.7) { //Main if the value is more than 70% of the maximum
          key3[topic_idlist[i]].type = 1;
          i++;
          maintopic_num++;
        }
        key3[topic_idlist[i]].type = 2;
        subtopic_num++;
      }

      /**/		System.out.println("MAIN/SUB TOPICS:");
      for (int i=0; i < topic_max; i++)
        System.out.println(FILEIO.print_double(key3[topic_idlist[i]].value)+":"+text.keyword[topic_idlist[i]].word+"("+key3[topic_idlist[i]].type+")"+
                           FILEIO.print_double(key3[topic_idlist[i]].subject_value)+" "+FILEIO.print_double(key3[topic_idlist[i]].freq_value)+" "+
                           text.keyword[topic_idlist[i]].frequencyAsSubject+" "+text.keyword[topic_idlist[i]].frequency+" "+text.keyword[topic_idlist[i]].sentenceFrequency+" --> "+key3[topic_idlist[i]].extracted);

      for (int i=0; i<text.segmentNumber; i++) {
        System.out.println("seg-"+(i+1)+" FREQ: max = "+seg3[i].freqmax+", ave = "+seg3[i].freqave+", std = "+seg3[i].freqstd);
      }
    }

    //Count number of topic sentences
    public void count_topic_sentenceNumber() {
      int n;
      maintopic_sentenceNumber = 0;
      for (int i=0; i<text.segmentNumber; i++) {
        for (int j=0; j<text.segment[i].sentenceNumber; j++)
          for (int k=0; k<text.segment[i].sentence[j].keywordNumber; k++) {
            for (n=0; n<maintopic_num; n++)
              if (text.segment[i].sentence[j].keywordIDList[k] == topic_idlist[n]) {
                maintopic_sentenceNumber ++;
                break;
              }
            if (n < maintopic_num) {
              break;
            }
          }
      }
    }

    //Calculate total evaluation values
    public void make_sentence_values(int t) {
      //Normalization and calculation of evaluation values
      for (int i=0; i<text.sentenceNumber; i++) {
        sen3[i].textv[t] = sen3[i].textvalue = 1 - sen3[i].textrank / (double) text.sentenceNumber;
        sen3[i].segv[t] = sen3[i].segvalue = 1 - sen3[i].segrank / (double) text.segment[text.sentenceToSegment[i]].sentenceNumber;
        sen3[i].totalv[t] = sen3[i].totalvalue = sen3[i].textvalue + sen3[i].segvalue;
      }
    }

    //Count number of sentences appearing keywords in a segment
    public int countkey_in_segment(int k, int id) {
      int count=0;

      for (int i=0; i<text.keyword[id].sentenceFrequency; i++)
        if (text.keyword[id].appearingSentence[i] >= text.segment[k].positionOfFirstSentence && text.keyword[id].appearingSentence[i] < text.segment[k].positionOfFirstSentence + text.segment[k].sentenceNumber) {
          count++;
        }

      return count;
    }

    //Select output sentences related to a viewpoint word t in each segment
    int sentence_selection(int seg_n, int select, int t) {
      int n,k,count=0;
      double sort_value[];
      double threshold = 0;

      //Decide the number of sentences to extract
      n = countkey_in_segment(seg_n, topic_idlist[t]);	//Count number of sentences appearing keywords in a segment
      if (n == 0) {		//Keywords do not appear
        return 0;
      }

      n = (int)(select * 0.5 * (double)n / (double)maintopic_sentenceNumber + 0.5);

      if (n == 0) {		//After calculation, if extract number is zero
        return 0;
      }

      sort_value = new double[text.segment[seg_n].sentenceNumber];

      //Eliminate sentences (Evaluation values -> 0) that are already selected
      for (int i=0; i<text.segment[seg_n].sentenceNumber; i++)
        if (sen3[text.segment[seg_n].positionOfFirstSentence+i].selected != -1) {
          sort_value[i] = 0;
        } else {
          sort_value[i] = sen3[text.segment[seg_n].positionOfFirstSentence+i].totalvalue;
        }

      //There are not sentences to output, if evaluation values for all sentences are 0
      for (k=0; k<text.segment[seg_n].sentenceNumber; k++)
        if (sen3[text.segment[seg_n].positionOfFirstSentence+k].totalvalue > 0) {
          break;
        }
      if (k == text.segment[seg_n].sentenceNumber) {
        return 0;
      }

      threshold = Qsort.quickselect(sort_value, text.segment[seg_n].sentenceNumber, n);

      /**/
      System.out.println(n+" threshold for "+text.keyword[topic_idlist[t]].word+" = "+threshold);

      for (int i=0; i<text.segment[seg_n].sentenceNumber; i++)
        if (sen3[text.segment[seg_n].positionOfFirstSentence+i].totalvalue > threshold || threshold > 0 && sen3[text.segment[seg_n].positionOfFirstSentence+i].totalvalue == threshold)
          if (sen3[text.segment[seg_n].positionOfFirstSentence+i].selected == -1) {
            sen3[text.segment[seg_n].positionOfFirstSentence+i].selected = t;
            sen3[text.segment[seg_n].positionOfFirstSentence+i].final_totalvalue = sen3[text.segment[seg_n].positionOfFirstSentence+i].totalvalue;
            sen3[text.segment[seg_n].positionOfFirstSentence+i].final_textvalue = sen3[text.segment[seg_n].positionOfFirstSentence+i].textvalue;
            sen3[text.segment[seg_n].positionOfFirstSentence+i].final_segvalue = sen3[text.segment[seg_n].positionOfFirstSentence+i].segvalue;
            count++;
          }

      key3[topic_idlist[t]].extracted += count;

      return count;
    }

    public void pvs_for_a_key(int t) { //Index of viewpoint keyword
      PvsR pvs;
      int id = topic_idlist[t];

      /**/
      System.out.println("for TOPIC:"+text.keyword[id].word);

      ////Execute for all texts
      pvs = new PvsR();
      pvs.set_text(text);

      pvs.sbasenum = 1;
      pvs.sbase_idlist[0] = id;

      pvs.set_parameters(select, addsbase, weight[0], weight[1], weight[2]);

      pvs.exec();


      for (int i=0; i<text.sentenceNumber; i++) {
        sen3[i].textrank = pvs.sen2[i].finalrank;
      };

      ////Execute for each segment
      for (int k=0; k<text.segmentNumber; k++) {
        if (text.keyword[id].frequencyInSegment[k] > 0) {
          pvs = new PvsR();
          pvs.set_text(segtext[k]);

          pvs.sbasenum = 1;
          pvs.sbase_idlist[0] = id;

          pvs.set_parameters(select, addsbase, weight[0], weight[1], weight[2]);

          pvs.exec();

          for (int j=0; j<segtext[k].segment[0].sentenceNumber; j++) {
            sen3[text.segment[k].positionOfFirstSentence+j].segrank = pvs.sen2[j].finalrank;  //sen3[j].finalrank;
          }
        } else	//Processes if a partial text does not include given viewpoint
          for (int j=0; j<segtext[k].segment[0].sentenceNumber; j++) {
            sen3[text.segment[k].positionOfFirstSentence+j].segrank = text.segment[k].sentenceNumber;
          }
      }

      //Calcuate total evaluation values
      make_sentence_values(t);

      //Select output sentences
      for (int i=0; i<text.segmentNumber; i++) {
        total_select += sentence_selection(i, select, t);
      }
    }


    //Output summarized text
    public String output_summary() {
      StringWriter sw1 = new StringWriter();
      BufferedWriter bw1 = new BufferedWriter(sw1);
      try {
        for (int i=0; i<sentence_num; i++)
          if (sen3[i].selected != -1) {
            bw1.write("["+i+"]"+"["+text.keyword[topic_idlist[sen3[i].selected]].word+" "+df_b.format(sen3[i].final_totalvalue)+"]");
            //+sen3[i].final_textvalue+" + "+sen3[i].final_segvalue);

            bw1.write(text.sentence[i].sentenceText);
            bw1.newLine();
            bw1.newLine();
          }
        bw1.write("---Panoramic View System InFormative("+total_select+"/"+text.sentenceNumber+")---");
        bw1.newLine();
        bw1.flush();
      } catch (Exception e) {
        System.out.println("Error in output_summary");
      }

      return sw1.toString();
    }
  }
}
