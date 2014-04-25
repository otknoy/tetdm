//
// Visuzalization module for TETDM 
// TextDisplay.java Version 0.36
// Copyright(C) 2011 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.TextDisplaySize;

import source.*;
import source.TextData.*;

import java.awt.*;
import javax.swing.*;

import java.io.*;


public class TextDisplay extends VisualizationModule
{
	//Text pane
	JTextPane text1;
	JScrollPane scroll1;

	//for DISPLAY
	String displayText;	//display text

    String nothing;
    String outputInJapanese;

	int fontsize;

	public TextDisplay()
	{
		setModuleID(1);
        dataNumbers = new int[]{0,0,0,1,0,0,0,0,0,0,0};
	}

	public void initializePanel()
	{
		text1 = new JTextPane();
		text1.setContentType("text/plain");		
		scroll1 = new JScrollPane(text1);
		add(scroll1);

		outputInJapanese = fileRead();
	}
	
	public void initializeData()
	{
		fontsize = 14;

		if(isMenuInJapanese())
		    displayText = outputInJapanese;
		else
		    displayText = "Nothing";		    
	}
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
				text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
/*
				displayText="";
				for(int i=0;i<text.sentenceNumber;i++)
				{
					displayText+="-----";
					displayText+=text.sentence[i].sentenceText;
				}
				
				displayText="";
				for(int i=0;i<text.sentenceNumber;i++)
				{
					displayText+="-----";
					for(int j=0;j<text.sentence[i].wordNumber;j++)
					{
						displayText+="+++";
						displayText+=text.sentence[i].word[j];
					}
					
				}
*/				

				text1.setText(displayText);
				text1.setCaretPosition(0);
				break;
				
			case 1:
				text.fileSave(text1.getText());
				break;
                
			case 2:
				text.fileSaveOnly(text1.getText());
				break;
				
			case 9:
				fontsize--;
				displayOperations(0);
				break;
				
			case 10:
				fontsize++;
				displayOperations(0);
				break;
				
			case 100:
				displayText = text.originalText;
				displayOperations(0);				
				break;				
		}		
	}	
	
	public boolean setData(int dataID, String t)
	{
        switch(dataID)
        {
            case 0:		displayText = t;
                return true;
        }

		return false;
	}
	
	public void setFont()
	{
		fontsize = text.fontSize;
	}
}
