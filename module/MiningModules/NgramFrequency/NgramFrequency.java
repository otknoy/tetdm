package module.MiningModules.NgramFrequency;

import source.*;
import source.TextData.*;

import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;



public class NgramFrequency extends MiningModule {

  private int n;
  

  public NgramFrequency() {
    setModuleID(90002);
    pairingVisualizationID = new int[]{90001, 90002};
    setToolType(2); // semi primitive

    n = 2;
  }


  @Override
  public void initializePanel() {
  }

  @Override
  public void initializeData() {
    resetData();
    

    String[] ngramTokens = ngram(n, getTerms());
    
    Map freqMap = frequency(ngramTokens);
    String[] terms = (String[])freqMap.keySet().toArray(new String[0]);
    Integer[] freqs = (Integer[])freqMap.values().toArray(new Integer[0]);

    // convert Ingeter[] to int[]
    int[] primitiveFreqs = new int[freqs.length];
    for (int i = 0; i < primitiveFreqs.length; i++) {
      primitiveFreqs[i] = freqs[i];
    }

    setDataStringArray(terms);
    setDataIntegerArray(primitiveFreqs);
  }

  @Override
  public void miningOperations(int optionNumber) {
  }


  private List<String> getTerms() {
    // get sentences
    SentenceData[] sentences = new SentenceData[text.sentenceNumber];
    for (int i = 0; i < sentences.length; i++) {
      sentences[i] = text.sentence[i];
    }

    // get terms
    List<String> terms = new ArrayList<String>();
    for (SentenceData s : sentences) {
      int[] wordIDList = s.wordIDList;
      int wordIDListLength = s.wordNumber;
      for (int i = 0; i < wordIDListLength; i++) {
	// If word is not keyword, word id is -1.
	// And then it is ignored.
	if (wordIDList[i] < 0) continue;

	KeywordData keyword = text.keyword[wordIDList[i]];
	// Add only noun wor
	// If keyword is noun, part of speech id is 1.
	if (keyword.partOfSpeech == 1) {
	  terms.add(keyword.word);
	}
      }
    }

    return terms;
  }

  private String[] ngram(int n, List<String> terms) {
    String[] ngramTokens = new String[terms.size()-(n-1)];
    for (int i = 0; i < ngramTokens.length; i++) {

      // join n tokens
      String[] nTokens = new String[n];
      for (int j = 0; j < nTokens.length; j++) {
	nTokens[j] = terms.get(i+j);
      }
      
      ngramTokens[i] = StringUtils.join(nTokens, '-');
    }

    return ngramTokens;
  }


  private Map<String, Integer> frequency(String[] tokens) {
    Map<String, Integer> freqMap = new HashMap<String, Integer>();

    for (String t : tokens) {
      if (!freqMap.containsKey(t)) {
	freqMap.put(t, 0);
      }
      freqMap.put(t, freqMap.get(t)+1);
    }

    return freqMap;
  }
}
