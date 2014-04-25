//
// Mining module for TETDM 
// WordExtractionWithDictionary Version 0.10 2012.2.9
// Copyright(C) 2012.2.9 Yoko Nishihara
// 
// This module extracts words written in a dictionary.
// You should read the README file.
//

package module.MiningModules.WordExtraction;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

public class WordExtraction extends MiningModule implements ActionListener
{
	String dictionary[];
	int wordHighlightNumber[];
    int sentenceHighlightNumber[];
	
    JButton bt1,bt2,bt3,bt4;
	JButton save;
    String buttonNamesInJapanese[];	
	
	JTextField textfield;
	String query = "";	


	public WordExtraction()
	{
		setModuleID(3);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{2,5};
	}

	public void initializePanel()
	{
		buttonNamesInJapanese = fileReadArray();		
		
		bt1 = new JButton("PartOfSpeech");
		bt2 = new JButton("Frequency");
		bt3 = new JButton("Dictionary:W");
		bt4 = new JButton("Dictionary:S");
		textfield = new JTextField(10);
		
		operationPanel.setBackground(Color.RED);
		
		textfield.addActionListener(this);
		operationPanel.add(textfield);		
		
		bt1.setBackground(Color.RED);
		bt1.addActionListener(this);
		operationPanel.add(bt1);
		bt2.setBackground(Color.RED);
		bt2.addActionListener(this);
		operationPanel.add(bt2);
		bt3.setBackground(Color.RED);
		bt3.addActionListener(this);
		operationPanel.add(bt3);
		bt4.setBackground(Color.RED);
		bt4.addActionListener(this);
		operationPanel.add(bt4);
		
		save = new JButton("Save and Extraction");
		save.addActionListener(this);
		operationPanel.add(save);
		
		if(isMenuInJapanese())
		{
			bt1.setText(buttonNamesInJapanese[0]);
			bt2.setText(buttonNamesInJapanese[1]);
			bt3.setText(buttonNamesInJapanese[2]);
			bt4.setText(buttonNamesInJapanese[3]);
			save.setText(buttonNamesInJapanese[4]);			
		}
	}

    public void initializeData()
    {
		dictionary = fileReadArray(myModulePath+"ExtractionWords.txt");
		sentenceHighlightNumber = new int[text.sentenceNumber];
		wordHighlightNumber = new int[text.wordNumber];
    }
	
    public void miningOperations(int optionNumber)
	{
	    switch(optionNumber)
		{
			case 0:
                resetData();//For FileDisplay
				setDataString(10000,myModulePath+"ExtractionWords.txt");
//                resetData();//For TextDisplay
//				setDataString(createDictionaryWords());
				break;
				
			case 1:
				initializeData();
				break;				

			case 25:
				query = textfield.getText();
				setDataString(1,query);//For TextDisplayColor Color Word
				displayOperations(25);
				break;				
				
			case 11:
				ColorByPartOfSpeech();
				setDataIntegerArray(1,wordHighlightNumber);//For TextDisplayColor
				displayOperations(11);
				break;
			case 12:
				ColorByFrequency();
				setDataIntegerArray(1,wordHighlightNumber);//For TextDisplayColor
				displayOperations(11);
				break;
			case 13:
				ColorByDictionary();
				setDataIntegerArray(1,wordHighlightNumber);//For TextDisplayColor
				displayOperations(11);
				break;
			case 23:
				ColorByDictionary();
				setDataIntegerArray(0,sentenceHighlightNumber);//For TextDisplayColor
				displayOperations(0);
				break;
		}
	}
	
	String createDictionaryWords()
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0; i<dictionary.length; i++)
			{
				bw.write(dictionary[i]);
				bw.newLine();				
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in WordExtraction");
		}		
		
		return sw.toString();		
	}
	
    void ColorByPartOfSpeech()
    {
		int wordCount = 0;
		for(int i=0;i<text.sentenceNumber;i++)
		    for(int j=0; j < text.sentence[i].wordNumber; j++, wordCount++)
			{
			    wordHighlightNumber[wordCount] = 10;
			    int id = text.sentence[i].wordIDList[j];
			    if(id >= 0)
				wordHighlightNumber[wordCount] = 12+text.keyword[id].partOfSpeech;
			}
    }

	void ColorByFrequency()
	{
		int Speech2FrequencyThreashold[] = new int[8];
		int temporaryKeywordFrequency[] = new int[text.keywordNumber];

		for(int i=0; i<8; i++)
		{
		    int k=0;
		    for(int j=0; j< text.keywordNumber; j++)
			if(text.keyword[j].partOfSpeech == i)
			    temporaryKeywordFrequency[k++] = text.keyword[j].frequency;
		    
		    Arrays.sort(temporaryKeywordFrequency, 0, k);
		    Speech2FrequencyThreashold[i] = temporaryKeywordFrequency[(int)(k*0.8)];
		}

		int wordCount = 0;
		for(int i=0;i<text.sentenceNumber;i++)
		    for(int j=0; j < text.sentence[i].wordNumber; j++, wordCount++)
			{
			    wordHighlightNumber[wordCount] = 10;
			    int id = text.sentence[i].wordIDList[j];
			    if(id >= 0)
					if(text.keyword[id].frequency >= Speech2FrequencyThreashold[text.keyword[id].partOfSpeech])
						wordHighlightNumber[wordCount] = 12+text.keyword[id].partOfSpeech;
			}
	}	

	
	void ColorByDictionary()
	{	
	    int wordCount = 0;
		
	    for(int i=0;i<text.sentenceNumber;i++)
		{
   		    sentenceHighlightNumber[i] = 10;
		    for(int j=0,k; j<text.sentence[i].wordNumber; j++, wordCount++)
			{
			    for(k=0; k<dictionary.length; k++)
				if(text.sentence[i].word[j].equals(dictionary[k]))
				    {
						sentenceHighlightNumber[i] = 12;
						wordHighlightNumber[wordCount] = 12;
						break;
				    }
			    if(k == dictionary.length)
				wordHighlightNumber[wordCount] = 10;
			}
		}
	}

	//Action listener
	public void actionPerformed(ActionEvent e)
	{
//		insideOfActionPerformed(e);
		
		if(e.getSource () == textfield)
			miningOperations(25);
		
		if(e.getSource() == bt1)
			miningOperations(11);

		if(e.getSource() == bt2)
			miningOperations(12);

		if(e.getSource() == bt3)
			miningOperations(13);

		if(e.getSource() == bt4)
			miningOperations(23);
		
		if(e.getSource() == save)
		{
			displayOtherModule(5,991);
//			displayModule(5,991);			
			miningOperations(1);
			miningOperations(13);
		}		
	}
}
