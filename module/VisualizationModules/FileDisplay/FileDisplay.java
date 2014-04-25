//
// Visuzalization module for TETDM 
// TextDisplay.java Version 0.31
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.FileDisplay;

import source.*;
import source.TextData.*;

import java.awt.*;
import javax.swing.*;

import java.io.File;


public class FileDisplay extends VisualizationModule
{
	//Text pane
	JTextPane text1;
	JScrollPane scroll1;
	
	//filename panel
	JPanel panel;
	JLabel label;
	String fileString;
	boolean fileExist;

	//for DISPLAY
	String filename;	//FILENAME
	String displayText;	//display text

    String nothing;
    String outputInJapanese[] = new String[2];

	int fontsize;

	public FileDisplay()
	{
		setModuleID(5);
        dataNumbers = new int[]{0,0,0,1,    // b,i,d,S
            0,0,0,0,    // bA,iA,dA,SA
            0,0,0};     // bA2,iA2,dA2
	}

	public void initializePanel()
	{
		text1 = new JTextPane();
		text1.setContentType("text/plain");		
		scroll1 = new JScrollPane(text1);
		add(scroll1);

		panel = new JPanel();
		label = new JLabel();
		
		panel.add(label);
		add("South",panel);
		
		outputInJapanese = fileReadArray();
		filename = "";
	}
	
	public void initializeData()
	{
		fontsize = 14;
		fileExist = false;

		if(isMenuInJapanese())
		{
		    displayText = outputInJapanese[0];
			fileString = outputInJapanese[1];
		}
		else
		{
		    displayText = "Nothing";
			fileString = "FILE NAME :";
		}
	}
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
				text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
				text1.setText(displayText);
				text1.setCaretPosition(0);				
				break;
				
			case 991:
				if(fileExist)
					FILEIO.TextFileWrite2(new File(filename), text1.getText());
				break;
				
			case 992:
				if(!filename.equals(""))
					FILEIO.TextFileWrite2(new File(filename), text1.getText());
				break;				
				
			case 9:
				fontsize--;
				displayOperations(0);
				break;
				
			case 10:
				fontsize++;
				displayOperations(0);
				break;
		}		
	}	
	
	public boolean setData(int dataID, String filename)
	{
        switch(dataID)
        {
            case 10000:
				this.filename = filename;
				
				displayText = fileRead(filename);
				if(displayText.equals(""))
				{
					fileExist = false;
					if(isMenuInJapanese())
						displayText = outputInJapanese[2];
					else
						displayText = "File NOT found";
				}
				else
					fileExist = true;
				
				displayOperations(0);
				panel.remove(label);
				label = new JLabel(fileString+filename);
				panel.add(label);
				panel.revalidate();
                return true;
        }
        
		return false;
	}
    /*
	public void setData(int dataID, String filename)
	{
		switch(dataID)
		{
			case 47:
				this.filename = filename;
				
				displayText = fileRead(filename);
				if(displayText.equals(""))
				{
					fileExist = false;
					if(isMenuInJapanese())
						displayText = outputInJapanese[2];
					else
						displayText = "File NOT found";
				}
				else
					fileExist = true;
				
				displayOperations(0);
				panel.remove(label);
				label = new JLabel(fileString+filename);
				panel.add(label);
				panel.revalidate();
				break;
		}
	}
	*/
	public void setFont()
	{
		fontsize = text.fontSize;
	}
}
