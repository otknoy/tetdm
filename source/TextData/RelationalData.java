//
// Core Program for TETDM
// RelationalData.java Version 0.44
// Copyright(C) 2008-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;

import source.Utility.*;



public class RelationalData {
  public String name[];		// Name of data
  public int frequency[];		// Frequency of data
  public int coFrequency[][];	// Co-Frequency of two data
  public double cond[][];		// conditional probability of two data
  public double cos[][];		// cos value of two data

  public double linkThreshold;	//link creating threshold	//added
  public boolean link[][];		//link						added
  public int totalLinkNumber;		//total number of links		added
  public int linkNumber[];		//number of links			added

  public int rank[][];		// relationship ranking		//added		to be sorted values rank[][]

  public double value[];		// value of data	added		for temporary use

  public int number;			// Number of data


  public RelationalData(int num) {
    number = num;

    name = new String[number];
    frequency = new int[number];
    coFrequency = new int[number][number];
    cond = new double[number][number];
    cos = new double[number][number];

    link = new boolean[number][number];
    linkNumber = new int[number];
    rank = new int[number][number];

    value = new double[number];
  }

  public void setName(String names[]) {
    for (int i=0; i<number; i++) {
      name[i] = names[i];
    }
  }

  public void setFrequency(int freqs[]) {
    for (int i=0; i<number; i++) {
      frequency[i] = freqs[i];
    }
  }

  public void setCoFrequency(int common_freqs[][]) {
    for (int i=0; i<number; i++)
      for (int j=0; j<=i; j++) {
        coFrequency[i][j] = coFrequency[j][i] = common_freqs[i][j];
      }
  }

  public void createCondValue() {
    for (int i=0; i<number; i++)
      for (int j=0; j<number; j++)
        if (frequency[i] == 0) {
          if (i != j) {
            cond[i][j] = 0.0;
          } else {
            cond[i][j] = 1.0;
          }
        } else {
          cond[i][j] = (double)coFrequency[i][j] / frequency[i];
        }
  }

  public void createCosValue() {
    for (int i=0; i<number; i++)
      for (int j=0; j<=i; j++)
        if (frequency[i] == 0 || frequency[j] == 0) {
          if (i != j) {
            cos[i][j] = cos[j][i] = 0.0;
          } else {
            cos[i][j] = cos[j][i] = 1.0;
          }
        } else {
          cos[i][j] = cos[j][i] = (double)coFrequency[i][j] / Math.sqrt(frequency[i] * frequency[j]);
        }
//				cos[i][j] = cos[j][i] = Math.sqrt(cond[i][j] * cond[j][i]);
  }


  //to be devided into calcu rank and make links
  public void createStrongestLink(double relate[][]) {
    if (number < 2) {
      return;
    }

//		int num[] = new int[number];
    double sort[] = new double[number];

    for (int i=0; i<number; i++) {
      for (int j=0; j<number; j++) {
        rank[i][j] = j;
        sort[j] = relate[i][j];
      }

      Qsort.quicksort(sort,rank[i],number);

      for (int j=0; j<number; j++)
        if (j == rank[i][1]) {	// must be checked i != j
          link[i][j] = true;
        } else {
          link[i][j] = false;
        }

      value[i] = relate[i][rank[i][1]];

    }
    countLinkNumber();
  }


  public void createLinks(double relate[][], double th) {
    linkThreshold = th;

    for (int i=0; i<number; i++)
      for (int j=0; j<number; j++) {
        //System.out.println(i+","+j+" "+relate[i][j]);
        if (relate[i][j] > linkThreshold) {
          link[i][j] = true;
        } else {
          link[i][j] = false;
        }
      }

    countLinkNumber();
  }

  public void createLinks(double relate[][], double th, boolean active[]) {
    linkThreshold = th;

    for (int i=0; i<number; i++)
      for (int j=0; j<number; j++)
        if (relate[i][j] > linkThreshold && active[i] && active[j]) {
          link[i][j] = true;
        } else {
          link[i][j] = false;
        }

    countLinkNumber();
  }

  public void countLinkNumber() {
    totalLinkNumber = 0;
    for (int i=0; i<number; i++) {
      linkNumber[i] = 0;
      for (int j=0; j<number; j++)
        if (link[i][j]) {
          linkNumber[i]++;
          totalLinkNumber++;
        }
    }
  }

  public void optimizeLink(double relate[][], double link_rate) {
    int opt;
    double temp_th;
    double maxLink;

    for (opt = 0; opt < 100; opt++) {
      temp_th = (double)opt/100;
      createLinks(relate, temp_th);

//			System.out.println("number = "+number+" totalLinkNumber = "+totalLinkNumber);
      totalLinkNumber = (totalLinkNumber - number) / 2;		// reduce self-link and double count FOR relate[i][j] == relate[j][i]

      maxLink = number * link_rate;

      if (maxLink <= 1.0) {
        maxLink = 2.0;
      }

      if (totalLinkNumber < maxLink) {
        break;
      }
    }

    if (opt == 100) {
      linkThreshold = 0.999;
      createLinks(relate, linkThreshold);
    }

//		System.out.println("Total Links = "+totalLinkNumber+" Threshold = "+linkThreshold);
  }

  public void optimizeLink(double relate[][], double link_rate, boolean active[]) {
    int opt;
    double temp_th;
    double maxLink;

    for (opt = 0; opt < 100; opt++) {
      temp_th = (double)opt/100;
      createLinks(relate, temp_th, active);

      totalLinkNumber = (totalLinkNumber - number) / 2;   // reduce self-link and double count

      maxLink = number * link_rate;
      if (maxLink <= 1.0) {
        maxLink = 2.0;
      }

      if (totalLinkNumber < maxLink) {
        break;
      }
    }

    if (opt == 100) {
      linkThreshold = 0.999;
      createLinks(relate, linkThreshold, active);
    }

//		System.out.println("Total Links = "+totalLinkNumber+" Threshold = "+linkThreshold);
  }
}
