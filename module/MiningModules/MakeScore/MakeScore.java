
//
// Visuzalization module for TETDM 
// MakeScore.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

// NOTE: Sentences which include less than two nouns will be scored by 0.

package module.MiningModules.MakeScore;

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.text.*;

//import java.awt.event.*;

public class MakeScore extends MiningModule
{
	double score[];
	DecimalFormat df_a;
	
	String opinionWords[];
	
	int copied_related_word[];
	int copied_original_word[];
	
	double M,alpha;
	
	
	public class SegmentData2
	{
		SentenceData2 sen2[];
		double opinionScore;
	
		public class SentenceData2
		{
			int opinion;
			int meishi_count;
			double opinionScore;
		
			SentenceData2(){}
		}
		
		public void initializeData(int seg)
		{
			sen2 = new SentenceData2[text.segment[seg].sentenceNumber];
			for(int i=0;i<text.segment[seg].sentenceNumber;i++)
				sen2[i] = new SentenceData2();
		}
	}
	
	SegmentData2 seg2[];
	
	public class KeywordData2
	{
		int relatedWord;
		int originalWord;
		
		KeywordData2(){}
	}
	
	KeywordData2 key2[];
	
	
	public void initializeData()
	{
		df_a = new DecimalFormat("###.0");
		
		seg2 = new SegmentData2[text.segmentNumber];
		for(int i=0;i<text.segmentNumber;i++)
		{
			seg2[i] = new SegmentData2();
			seg2[i].initializeData(i);
		}
		
		key2 = new KeywordData2[text.keywordNumber];
		for(int i=0;i<text.keywordNumber;i++)
		{
			key2[i] = new KeywordData2();
		}	
		
		opinionWords = fileReadArray();
		
		copied_related_word = new int[text.keywordNumber];
		copied_original_word = new int[text.keywordNumber];
		for(int i=0;i<text.keywordNumber;i++)
			key2[i].relatedWord = key2[i].originalWord = 0;
		
		M=0;
		for(int i=0;i<text.segmentNumber;i++)
			M += text.segment[i].sentenceNumber;
		M /= text.segmentNumber;	//Average number of sentence
		M = 100.0 / M;			//Maximum score of a sentence
		alpha = M / 5;			//Score of a word
		
		System.out.println("M="+M+"alpha="+alpha);		
	}
	
	
	public MakeScore()
	{
		setModuleID(22);
		pairingVisualizationID = new int[]{6};
		setToolType(3);
	}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
				meishi_count();
				
				find_related_words();
				find_original_words();				
				calc_score();
				calculateScore();
				
//				displayOperations(1);
				break;
		}
	}
	
	public void calculateScore()
	{
		score = new double[text.segmentNumber];
		
		for(int i=0;i<text.segmentNumber;i++)
			score[i] = seg2[i].opinionScore;
		
		setDataDoubleArray(score);
		setDataBoolean(false);
	}



	
	public void calc_score()
	{
		double r, related_score, original_score;
		double fact_score, opinion_score;
		int opinion_num, id, c;
		
		//These parameters are for journal
		int countr = 0;
		int counto = 0;
		int countro = 0;
		int countall = 0;
		
		for(int i=0;i<text.segmentNumber;i++)
		{
			opinion_num = 0;
			
			fact_score = opinion_score = 0.0;
			
			for(int j=0;j<text.segment[i].sentenceNumber;j++)
				seg2[i].sen2[j].opinion = 0;
			
			//Specify Opinion sentences
			for(int j=0;j<text.segment[i].sentenceNumber;j++)
				if(seg2[i].sen2[j].meishi_count > 2)	//more than 3 nouns
					for(int k=text.segment[i].sentence[j].wordNumber-1;k>=0 && k >= text.segment[i].sentence[j].wordNumber-5;k--)
						for(int w=0;w<opinionWords.length;w++)
							if(text.segment[i].sentence[j].wordEndForm[k].equals(opinionWords[w]))
							{
//																System.out.println(text.segment[i].sentence[j].sentenceText);//////
								seg2[i].sen2[j].opinion = 1;
								w=opinionWords.length;
								k=-1;
							}
			
			for(int k=0;k<text.keywordNumber;k++)
			{
				copied_related_word[k] = key2[k].relatedWord;
				copied_original_word[k] = key2[k].originalWord;
			}
			
			//Add scores of relational words and original words
			for(int j=0;j<text.segment[i].sentenceNumber;j++)
			{
				related_score = original_score = 0.0;
				
				if(seg2[i].sen2[j].meishi_count > 2)	//more than 3 nouns
					for(int k=0;k<text.segment[i].sentence[j].keywordNumber;k++)
					{
						id = text.segment[i].sentence[j].keywordIDList[k];
						related_score += copied_related_word[id];
						original_score += copied_original_word[id];
						
						copied_related_word[id] = copied_original_word[id] = 0;	//a word is evaluated only once
					}
				
//				System.out.println("related_score="+related_score+"original_score="+original_score);//for journal
				{
					
					
					if(related_score != 0)
						countr++;
					if(original_score != 0)
						counto++;
					if(related_score * original_score != 0)
						countro++;
					countall++;	
				}
				
				seg2[i].sen2[j].opinionScore = alpha * (related_score + original_score);
				if(seg2[i].sen2[j].opinionScore > M)
					seg2[i].sen2[j].opinionScore = M;
				
			//					System.out.println((i+1)+":"+j+":score="+seg2[i].sen2[j].opinionScore);
			}
			
			for(int j=0;j<text.segment[i].sentenceNumber;j++)
				opinion_num += seg2[i].sen2[j].opinion;
			r = 1 - Math.abs( 0.5 - (double)opinion_num / (double)text.segment[i].sentenceNumber);  //Eq.(5) 
			
			//Add scores of segments
			for(int j=0;j<text.segment[i].sentenceNumber;j++)
				if(seg2[i].sen2[j].opinion == 1)
					opinion_score += seg2[i].sen2[j].opinionScore;
				else
					fact_score += seg2[i].sen2[j].opinionScore;
			
			if(opinion_score > 50)
				opinion_score = 50;
			if(fact_score > 50)
				fact_score = 50;
			
//			System.out.println((i+1)+":r="+r+"/opinion="+opinion_score+":fact="+fact_score);
			
			seg2[i].opinionScore = (r * (opinion_score + fact_score));
			
			System.out.println("Score for ["+(i+1)+"] = "+df_a.format(seg2[i].opinionScore));
			
			//			System.out.println((i+1)+":"+opinion_num+"/"+text.segment[i].sentenceNumber+":score="+segment_score[i]);
		}
//		System.out.println("countr="+countr+"counto="+counto+"countro="+countro+"countall="+countall);		//for journal
	}
	
	public void meishi_count()
	{
		int id;
		for(int i=0;i<text.segmentNumber;i++)
			for(int j=0;j<text.segment[i].sentenceNumber;j++)
			{
				seg2[i].sen2[j].meishi_count = 0;
				for(int k=0;k<text.segment[i].sentence[j].keywordNumber;k++)
					if(text.keyword[text.segment[i].sentence[j].keywordIDList[k]].partOfSpeech == 1)
						seg2[i].sen2[j].meishi_count++;
			}
	}

	//more than 30% in document(segment) frequency, and in top 5% of frequency
	public void find_related_words()//Only Nouns
	{
		int c;	//Number of nouns
		double sort_data[] = new double[text.keywordNumber];
		c = 0;
		for(int i=0;i<text.keywordNumber;i++)
			if(text.keyword[i].partOfSpeech == 1)
				sort_data[c++] = (double)text.keyword[i].frequency;
		
		double border = Qsort.quickselect(sort_data, c, (int)(c*0.05+1));
		
		for(int i=0;i<text.keywordNumber;i++)
			if(text.keyword[i].segmentFrequency >= text.segmentNumber * 0.3 && text.keyword[i].frequency > border && text.keyword[i].partOfSpeech == 1)
				key2[i].relatedWord = 1;
	}
	
	//less than 5% in document(segment) frequency
	public void find_original_words()//Nouns, Verbs, Adjectives
	{
		for(int i=0;i<text.keywordNumber;i++)
			if(text.keyword[i].segmentFrequency < text.segmentNumber * 0.05 && (text.keyword[i].partOfSpeech == 1 || text.keyword[i].partOfSpeech == 2 || text.keyword[i].partOfSpeech == 3))
				key2[i].originalWord = 1;
	}	
}
