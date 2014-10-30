package module.VisualizationModules.DrawTopBottom;

import source.*;
import source.TextData.*;
import source.Utility.*;


public class Score {
  static double branchValue = 0.9;

  public static double createMaxScore(int segmentNumber) {
    int rootInteger;
    double maxScore = 1.0;

    int lineNumber[];

    int baseNumber;
    int addNumber;

    double straightConditional = 0.4;

    rootInteger = (int)Math.sqrt(segmentNumber-1);

    if (rootInteger * rootInteger == segmentNumber-1) {
      rootInteger--;
    }

    if (rootInteger == -1) {
      return 1.0;
    }

    lineNumber = new int[rootInteger+1];

    baseNumber = (int)((segmentNumber-1)/(rootInteger+1));
    addNumber = (segmentNumber-1)%(rootInteger+1);


    for (int i=0; i<addNumber; i++) {
      lineNumber[i] = baseNumber + 1;
    }

    for (int i=addNumber; i<lineNumber.length; i++) {
      lineNumber[i] = baseNumber;
    }

    for (int i=0; i<lineNumber.length; i++) {
      maxScore *= (1.0/lineNumber.length + straightConditional * (lineNumber[i]-1) + branchValue);
    }

    return maxScore;
  }

  public static double scoretop(int nodeIndex, int segmentNumber, int link[][], double cond[][]) {
    int n,count;
    double score;
    double line[];
    int aaa, line_count;
    double lineScore;
    boolean check[];

    double condValue;

    System.out.println("NODEINDEX "+nodeIndex);

    count = 0;
    for (int i=nodeIndex+1; i<segmentNumber; i++)
      if (link[nodeIndex][i]==1) {
        count++;
      }

    if (count == 0) {
      return 0.0;
    }

    if (nodeIndex != 0 && count == 1)
//        if(count == 1)
    {
      return 0.0;
    }

    line = new double[count];
    for (int i=0; i<count; i++) {
      line[i]=0;
    }

    check = new boolean[segmentNumber];
    for (int i=0; i<segmentNumber; i++) {
      check[i]=false;
    }

    score = 1.0;
    for (int i=0; i<count;) {
      line_count = 0;
      lineScore = 0.0;
      n = nodeIndex;

      for (int j=n+1; j<segmentNumber; j++)
        if (link[n][j] == 1 && check[j] == false) {
          line_count++;
          condValue = cond[n][j];
          if (condValue > 0.5) {
            condValue = 0.5;
          }
          lineScore += condValue;

          n = j;
          j = n;
        }

      check[n] = true;

//            for(int k=nodeIndex;k<segmentNumber;k++)
//               System.out.print(check[k]+" ");
      //         System.out.println("");

      if (lineScore >= line[i]) {
        line[i] = lineScore;
      }

      System.out.println("LINE FOR "+i+" : "+line[i]+" line_count= "+line_count);

      if (line_count == 1) {
        i++;
      }
    }

    for (int i=0; i<count; i++) {
      score *= (line[i]+branchValue);
    }

    System.out.println("score for "+nodeIndex+" = "+score);

//        System.out.println("maxScore for "+segmentNumber+" = "+createMaxScore(segmentNumber));

    return score;
  }
}