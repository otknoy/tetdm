package module.MiningModules.NgramFrequency;

import source.*;
import source.TextData.*;

import org.apache.commons.lang3.StringUtils;


public class NgramFrequency extends MiningModule {

  private int n;
  

  public NgramFrequency() {
    setModuleID(2);
    pairingVisualizationID = new int[]{1};
    setToolType(2); // semi primitive

    n = 2;
  }


  @Override
  public void initializePanel() {
  }

  @Override
  public void initializeData() {
    resetData();
    
    KeywordData[] keywords = text.keyword;
    int length = text.keywordNumber;

    String[] ngramTokens = new String[length-(n-1)];
    for (int i = 0; i < ngramTokens.length; i++) {

      String[] nTokens = new String[n];
      for (int j = 0; j < nTokens.length; j++) {
	nTokens[j] = keywords[i+j].word;
      }
      
      ngramTokens[i] = StringUtils.join(nTokens, '-');
    }


    System.out.println("################################");
    for (String s : ngramTokens) {
      System.out.println(s);
    }

    
    setDataStringArray(ngramTokens);
    int[] hoge = new int[ngramTokens.length];
    for (int i = 0; i < hoge.length; i++) hoge[i] = 100;
    setDataIntegerArray(hoge);
  }

  @Override
  public void miningOperations(int optionNumber) {
  }
}
