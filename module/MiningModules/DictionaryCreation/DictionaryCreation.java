//
// Mining module for TETDM 
// DictionaryCreationWithDictionary Version 0.10 2012.2.9
// Copyright(C) 2012.2.9 Yoko Nishihara
// 
// This module extracts words written in a dictionary.
// You should read the README file.
//

package module.MiningModules.DictionaryCreation;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

public class DictionaryCreation extends MiningModule implements ActionListener
{
	String dictionary[];
	
    JButton bt1,bt2;
    String buttonNamesInJapanese[];	

	public DictionaryCreation()
	{
		setModuleID(9998);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{5};
	}

	public void initializePanel()
	{
		buttonNamesInJapanese = fileReadArray();		
		
		bt1 = new JButton("SAVE AND CREATE DICTIONARY");
		bt2 = new JButton("RESET DICTIONARY");
		
		operationPanel.setBackground(Color.RED);
		
		bt1.setBackground(Color.RED);
		bt1.addActionListener(this);
		operationPanel.add(bt1);
		bt2.setBackground(Color.RED);
		bt2.addActionListener(this);
		operationPanel.add(bt2);	
		
		if(isMenuInJapanese())
		{
			bt1.setText(buttonNamesInJapanese[0]);
			bt2.setText(buttonNamesInJapanese[1]);
		}
	}

    public void initializeData()
    {
		dictionary = fileReadArray(myModulePath+"AddWords.txt");
    }
	
    public void miningOperations(int optionNumber)
	{
	    switch(optionNumber)
		{
			case 0:
                resetData();//For FileDisplay
				setDataString(10000,myModulePath+"AddWords.txt");
				break;
				
			case 1:
				initializeData();
				break;
				
			case 2:
				EXECUTE.fileConnect(myModulePath+"AddWords.csv", text.absolutePath+"mecab-ipadic-2.7.0-20070801/TETOriginalNoun.csv", 
									text.absolutePath+"mecab-ipadic-2.7.0-20070801/Noun.csv","EUC-JP");
				createDictionary();
				break;
				
			case 3:
				EXECUTE.filecopy(text.absolutePath+"mecab-ipadic-2.7.0-20070801/TETOriginalNoun.csv", 
									text.absolutePath+"mecab-ipadic-2.7.0-20070801/Noun.csv","EUC-JP");
				createDictionary();
				break;					
		}
	}

	public void createCsv()
	{
		int lineNumber = 0;
		String writeText[] = new String[dictionary.length-1];
		String filename = myModulePath+"AddWords.csv";
		
		for(int i=1;i<dictionary.length;i++)
		{
			String word[] = dictionary[i].split(",", 0);
			if(word.length < 2)
				break;
			
//			writeText[lineNumber++] = word[0]+",1285,1285,5543,"+buttonNamesInJapanese[2]+","+buttonNamesInJapanese[3]+",*,*,*,*,"+word[0]+","+word[1]+","+word[1];
			writeText[lineNumber++] = word[0]+",0,0,0,"+buttonNamesInJapanese[2]+","+buttonNamesInJapanese[3]+",*,*,*,*,"+word[0]+","+word[1]+","+word[1];			
		}
		
		if(lineNumber > 0)
			fileWriteArray(filename, writeText, lineNumber);
	}
	
	//Action listener
	public void actionPerformed(ActionEvent e)
	{	
		if(e.getSource() == bt1)
		{
			displayOperations(991);			
			miningOperations(1);
			createCsv();
			miningOperations(2);
		}
		
		if(e.getSource() == bt2)
			miningOperations(3);		
	}
}
