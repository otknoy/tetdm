package module.MiningModules.TermFrequency;

import source.*;
import source.TextData.*;

import java.util.Arrays;


public class TermFrequency extends MiningModule {

  private Term[] terms;
  private String[] surfaces;
  private int[] frequencies;
  

  public TermFrequency() {
    setModuleID(90001);
    pairingVisualizationID = new int[]{90001, 90002};
    setToolType(2); // semi primitive
  }


  @Override
  public void initializePanel() {
  }

  @Override
  public void initializeData() {
    resetData();
    KeywordData[] keywords = text.keyword;
    int length = text.keywordNumber;

    Term[] terms = new Term[length];
    for (int i = 0; i < terms.length; i++) {
      terms[i] = new Term(keywords[i].word, keywords[i].frequency);
    }
    Arrays.sort(terms);
    this.terms = terms;

    int n = length;
    String[] surfaces = new String[n];
    int[] frequencies = new int[n];
    for (int i = 0; i < n; i++) {
      surfaces[i] = terms[i].getSurface();
      frequencies[i] = terms[i].getFrequency();
    }
    this.surfaces = surfaces;
    this.frequencies = frequencies;
  }

  @Override
  public void miningOperations(int optionNumber) {
    switch(optionNumber) {
    case 0:
      setDataStringArray(surfaces);
      setDataIntegerArray(frequencies);
      break;
    default:
      break;
    }
  }
}
