//
// Mining module for TETDM 
// MakeLight.java Version 0.30
// Copyright(C) 2008 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//


package module.MiningModules.MakeLight;

import source.*;
import source.TextData.*;
import source.Utility.*;


//Words in a sentence which includes a topic word become topic related.

import java.io.*;
import java.text.*;
import java.util.*;


public class MakeLight extends MiningModule
{
	class KeywordData3
	{
		double lightValue[];
		
		KeywordData3(){}
	}
	
	class SentenceData3
	{
		double light_score;
		
		SentenceData3(){}
	}	
	
	KeywordData3 key3[];
	SentenceData3 sen3[];

	double related_word[];
	double copied_related[];
	
	String inJapanese[];
	String boxLabel[];	

	public MakeLight()
	{
		setModuleID(12);
		pairingVisualizationID = new int[]{2,4,1102};
		focusClickExecute = true;		
	}
	
//	public void initializePanel(){}	

	public void initializeData()
	{
		key3 = new KeywordData3[text.keywordNumber];

		for(int i=0;i<text.keywordNumber;i++)
		{
			key3[i] = new KeywordData3();
			key3[i].lightValue = new double[text.sentenceNumber];
		}	
		
		related_word = new double[text.keywordNumber];
		copied_related = new double[text.keywordNumber];
		
		for(int i=0;i<text.keywordNumber;i++)
			calc_light_by_one(i);
//		System.out.println("Calc LIGHT !!!");
		
		
		inJapanese = fileReadArray();
		String names[] = {"VIEWPOINT","TOPIC","FREQUENCY","COVERAGE","SENFREQ"};
		 
		if(isMenuInJapanese())
			boxLabel = inJapanese;
		else
			boxLabel = names;				
	}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 4502:
				firstData();
				secondData();				
				break;				
			case 0:
				firstData();
				secondData();
				break;
		}
	}
	
	int lightwords[];
	int light_num;	
	
	public void firstData()
	{
		lightwords = getNewDataIntegerArray(11, 0);	//sbase
		light_num = lightwords.length;
		
		for(int i=0;i<text.keywordNumber;i++)
			text.focus.focusKeywords[i] = false;
			
		for(int i=0;i<light_num;i++)
			text.focus.focusKeywords[lightwords[i]] = true;
		
//		light_num = 5;
//		for(int i=0;i<light_num;i++)
//			lightwords[i] = i;
		
//		for(int i=0;i<light_num;i++)
//			System.out.println(text.keyword[lightwords[i]].word);

		if(text.sentenceNumber > 0)
			sen3 = new SentenceData3[text.sentenceNumber];
		else
			sen3 = new SentenceData3[1];
			
		for(int i=0;i<text.sentenceNumber;i++)
			sen3[i] = new SentenceData3(); 
		make_light_one_by_one(lightwords, light_num);
	
		int attr[] = new int[text.sentenceNumber];
		
		for(int i=0;i<text.sentenceNumber;i++)
			if(sen3[i].light_score > 4.0)
					attr[i] = 1;
				else
					attr[i] = (int)(9 - sen3[i].light_score*2);

		setDataIntegerArray(0, attr);// for TextDisplayColor
		
		double light_value[] = new double[text.sentenceNumber];
		for(int i=0;i<text.sentenceNumber;i++)
			light_value[i] = sen3[i].light_score;

		setDataDoubleArray(0, light_value);	//for scoredist
	}	
		
	public void secondData()
	{		
		//FOR LIGHT SELECT PANEL
		setDataInteger(77,5);
		
		double value[] = new double[text.keywordNumber];
		
		//sbase
		for(int i=0;i<text.keywordNumber;i++)
			value[i] = 0;
		for(int i=0;i<light_num;i++)
			value[lightwords[i]] = 1.0;
		setDataDoubleArray(10,value);
		setDataString(10,boxLabel[0]);
		
		//topic
		int topicWords[];
		topicWords = getDataIntegerArray(11, 1);
		for(int i=0;i<text.keywordNumber;i++)
			value[i] = 0;
		for(int i=0;i<topicWords.length;i++)
			value[topicWords[i]] = 1.0;
		setDataDoubleArray(11,value);
		setDataString(11,boxLabel[1]);
		
		//frequency
		for(int i=0;i<text.keywordNumber;i++)
			value[i] = text.keyword[i].frequency;
		setDataDoubleArray(12,value);
		setDataString(12,boxLabel[2]);
		
		
		//coverage
		for(int i=0;i<text.keywordNumber;i++)
			value[i] = 0;		
		for(int i=0;i<text.keywordNumber;i++)
			for(int j=0;j<text.sentenceNumber;j++)
				if(key3[i].lightValue[j] > 0)
					value[i] += 1.0;;
		setDataDoubleArray(13,value);
		setDataString(13,boxLabel[3]);
		
		//sentence frequency
		for(int i=0;i<text.keywordNumber;i++)
			value[i] = text.keyword[i].sentenceFrequency;
		setDataDoubleArray(14,value);
		setDataString(14,boxLabel[4]);
	}

	//Light for a set of words is given as the sum of light for each word
	public void make_light_one_by_one(int lightwords[], int light_num)
	{
		for(int i=0;i<text.sentenceNumber;i++)
			sen3[i].light_score = 0.0;			
				
//		System.out.println("light_num= "+light_num+" text.sentenceNumber= "+text.sentenceNumber);
//		System.out.println("sen3.length= "+sen3.length+" key3.length= "+key3.length);
//		System.out.println("lightwords.length= "+lightwords.length+" text.textID= "+text.textID);
		
//		for(int i=0;i<text.keywordNumber;i++)
//			System.out.println("LENGTH= "+key3[i].lightValue.length);
		
		for(int x=0;x<light_num;x++)
			for(int i=0;i<text.sentenceNumber;i++)		
				sen3[i].light_score +=
					key3[lightwords[x]].lightValue[i];
	}

	public void calc_light_by_one(int lightword)
	{
		// INITIALIZE TOPIC-RELATED	SCORE
		for(int i=0;i<text.keywordNumber;i++)
			related_word[i] = 0.0;

		// TOPIC
		related_word[lightword] = 1.0;

		for(int i=0;i<text.sentenceNumber;i++)
		{
			int flag = 0;
			key3[lightword].lightValue[i] = 0;

//			for(int k=0;k<text.keywordNumber;k++)
//				copied_related[k] = related_word[k];

			for(int k=0;k<text.sentence[i].keywordNumber;k++)
			{
				int id = text.sentence[i].keywordIDList[k];
				
				key3[lightword].lightValue[i] += related_word[id];

				//				key3[lightword].light_value[i] += copied_related[id];
//					copied_related[id] = 0;			//When give the score once only

				if(related_word[id] == 1.0)
					flag = 1;
			}

			if(flag == 1)///////////////////
				for(int k=0;k<text.sentence[i].keywordNumber;k++)
				{
					int id = text.sentence[i].keywordIDList[k];
					if(related_word[id] == 0.0 && text.keyword[id].frequency <= text.keyword[lightword].frequency)
						related_word[id] = 0.5;
				}
		}
	}
}
