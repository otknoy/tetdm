//
// Mining module for TETDM 
// WordReplace Version 0.10 2012.2.9
// Copyright(C) 2012.2.9 Yoko Nishihara
// 
// This module replaces words with words written in a dictionary.
// You should read the README file.
//

package module.MiningModules.WordReplace;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class WordReplace extends MiningModule implements ActionListener
{
    String dictionary[];
    String splitKeyword[];
    String originalKeyword[];
    String replaceKeyword[];
	
	JButton save;
    String buttonNamesInJapanese[];	
	
	public WordReplace()
	{
		setModuleID(4);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{1,5};
	}

	public void initializePanel()
	{
		buttonNamesInJapanese = fileReadArray();		
		
		save = new JButton("Save and Replace");
		save.addActionListener(this);
		operationPanel.add(save);
		
		if(isMenuInJapanese())
		{
			save.setText(buttonNamesInJapanese[0]);			
		}		
	}
	
    public void initializeData()
    {
		dictionary = fileReadArray(myModulePath+"ReplaceWords.txt");
		splitKeyword = new String[2];
		originalKeyword = new String[dictionary.length];
		replaceKeyword = new String[dictionary.length];
    }
	
    public void miningOperations(int optionNumber)
    {
		switch(optionNumber)
			{
				case 0:
                    resetData();
					setDataString(MyMethod());//For TextDisplay
					setDataString(10000,myModulePath+"ReplaceWords.txt");//For FileDisplay
					break;
					
				case 1:
					initializeData();
					break;
			}
    }
	
	public void actionPerformed(ActionEvent e)
	{
//		insideOfActionPerformed(e);
		
		if(e.getSource() == save)
		{
			displayOtherModule(5,991);
			miningOperations(1);
			miningOperations(0);
			displayOperations(0);
		}
	}	
	
	String MyMethod()
	{	
		for(int i=0; i<dictionary.length; i++)
		{
			splitKeyword = dictionary[i].split(" ");
			originalKeyword[i] = splitKeyword[0];
			replaceKeyword[i] = splitKeyword[1];
		}
		
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);			
		
		try{
			for(int i=0;i<text.sentenceNumber;i++)
			    for(int j=0,k; j != text.sentence[i].wordNumber; j++)
				{
					for(k=0; k<originalKeyword.length; k++)
					    if(text.sentence[i].word[j].equals(originalKeyword[k]))
					    {
							bw.write(replaceKeyword[k]);
							break;
					    }
					if(k == originalKeyword.length)
					    bw.write(text.sentence[i].word[j]);
				}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in WordReplace");
		}			
		return sw.toString();	
	}
}
