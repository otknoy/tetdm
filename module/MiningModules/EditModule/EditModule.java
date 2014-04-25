//
// Mining module for TETDM 
// EditModule.java Version 0.36
// Copyright(C) 2011 2012 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//


package module.MiningModules.EditModule;

import source.*;
import source.TextData.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class EditModule extends MiningModule implements ActionListener
{
    JButton button[];
    int buttonNumber;
	JToggleButton tButton;
	
    String editedText;
	String numberedText;

    String segment;
    String buttonNamesInJapanese[];
	
	public EditModule()
	{
		setModuleID(1);
		pairingVisualizationID = new int[]{1};
		setToolType(2);
	}

	public void initializePanel()
	{
		buttonNamesInJapanese = fileReadArray();
		
		operationPanel.setBackground(Color.RED);

        buttonNumber = 5;
        
	    button = new JButton[buttonNumber];

        operationPanel.setLayout(new GridLayout(2,3));
        
	    for(int i=0;i<buttonNumber;i++)
		{
		    button[i] = new JButton();
		    button[i].setBackground(Color.RED);
		    button[i].addActionListener(this);
		    operationPanel.add(button[i]);
		}
		
		tButton = new JToggleButton();
		tButton.setBackground(Color.RED);
		tButton.addActionListener(this);
		operationPanel.add(tButton);		
		
	}	
	
	public void initializeData()
    {		
		String buttonNamesInEnglish[] = {"cancel","save+exec","tag cut","add period","add segment","line number"};

		if(isMenuInJapanese())
		{
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(buttonNamesInJapanese[i]);
            
			tButton.setText(buttonNamesInJapanese[5]);
			segment = buttonNamesInJapanese[6];
		}
		else
		{
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(buttonNamesInEnglish[i]);
            
			tButton.setText(buttonNamesInEnglish[5]);
			segment = "SEGMENT";
		}
    }
	
	void makeNumberedText()
	{
        String kaigyo = System.getProperty("line.separator");        
        
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<text.segmentNumber;i++)
			{
				bw.write("<"+segment+" : "+(i+1)+">");
				bw.newLine();
				for(int j=0;j<text.segment[i].sentenceNumber;j++)
				{
					bw.write((text.segment[i].sentence[j].positionOfSentence+1)+": ");
					for(int k=0;k<text.segment[i].sentence[j].wordNumber;k++)
						if(!text.segment[i].sentence[j].word[k].equals(kaigyo))
							bw.write(text.segment[i].sentence[j].word[k]);
					bw.newLine();
				}
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in EditModule");
		}		

		numberedText = sw.toString();
	}
    
	String tagCut()
	{
        String kaigyo = System.getProperty("line.separator");
        int kaigyoCount;
        
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<text.segmentNumber;i++)
				for(int j=0;j<text.segment[i].sentenceNumber;j++)
					for(int k=0;k<text.segment[i].sentence[j].wordNumber;k++)
						bw.write(text.segment[i].sentence[j].word[k]);
            
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in EditModule");
		}
        
		return sw.toString();
	}
    

	String addPeriods()
	{
        String kaigyo = System.getProperty("line.separator");
        int kaigyoCount;        
        
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<text.segmentNumber;i++)
            {
				for(int j=0;j<text.segment[i].sentenceNumber;j++)
                {
                    kaigyoCount = 0;
                    
					for(int k=0;k<text.segment[i].sentence[j].wordNumber;k++)
                    {
						if(text.segment[i].sentence[j].word[k].equals(kaigyo))
                        {
                            kaigyoCount++;
                            
                            if(k != text.segment[i].sentence[j].wordNumber-1)
                            if(k+1 != kaigyoCount && kaigyoCount == 1)
                                bw.write(text.control.sp1.kind_touns[0]);
                        }
                        else
                            kaigyoCount = 0;
						bw.write(text.segment[i].sentence[j].word[k]);
//						bw.write("["+i+","+j+","+k+","+text.segment[i].sentence[j].word[k]+"]");
                    }
                }
                if(i != text.segmentNumber -1)
                    bw.write(text.control.sp1.segment_tag);
            }


			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in EditModule");
		}
        
		return sw.toString();
	}
    
	String wordData()
	{
        String kaigyo = System.getProperty("line.separator");
        int kaigyoCount;
        
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<text.segmentNumber;i++)
				for(int j=0;j<text.segment[i].sentenceNumber;j++)
					for(int k=0;k<text.segment[i].sentence[j].wordNumber;k++)
						bw.write("["+i+","+j+","+k+","+text.segment[i].sentence[j].word[k]+"]");
            
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in EditModule");
		}
        
		return sw.toString();
	}    
    
    
	String divideSegments()
	{
        String kaigyo = System.getProperty("line.separator");
        int kaigyoCount;
        
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<text.segmentNumber;i++)
            {
                kaigyoCount=0;
				for(int j=0;j<text.segment[i].sentenceNumber;j++)
                {
					for(int k=0;k<text.segment[i].sentence[j].wordNumber;k++)
                    {
						if(text.segment[i].sentence[j].word[k].equals(kaigyo))
                        {
                            kaigyoCount++;
                        
                            if(k+1 != kaigyoCount && kaigyoCount == 2)
                                bw.write(text.control.sp1.segment_tag);
                        }
                        else
                            kaigyoCount = 0;
                        
                        bw.write(text.segment[i].sentence[j].word[k]);
                    }
                }
                if(i != text.segmentNumber -1)
                    bw.write(text.control.sp1.segment_tag);
            }

			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in EditModule");
		}
        
		return sw.toString();
	}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
                resetData();
				setDataString(text.originalText);
				break;
				
			case 1:
				text.fileLoad();
				break;
               
            case 3:
                editedText = tagCut();
                resetData();                
				setDataString(editedText);
				displayOperations(0);
				break;
                
            case 4:
                editedText = addPeriods();
                resetData();               
				setDataString(editedText);
				displayOperations(0);
				break;
                
            case 5:
                editedText = divideSegments();
                resetData();                
				setDataString(editedText);
				displayOperations(0);
				break;
                
			case 6:
                //                editedText = wordData();
                //				setDataString(editedText);
				makeNumberedText();
                resetData();               
				setDataString(numberedText);
				displayOperations(0);
				break;
                
   
		}
	}
	
	//Action listener
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == button[0])
        {
            tButton.setSelected(false);
			miningOperations(1);
        }
        
		if(e.getSource() == button[1])
        {
            tButton.setSelected(false);            
			displayOperations(1);
        }
        
		if(e.getSource() == button[2])
        {
            tButton.setSelected(false);
			miningOperations(3);
        }
        
		if(e.getSource() == button[3])
        {
            tButton.setSelected(false);
			displayOperations(1);
			miningOperations(4);
			displayOperations(1);
        }
        
		if(e.getSource() == button[4])
        {
            tButton.setSelected(false);            
			displayOperations(1);
			miningOperations(5);
			displayOperations(1);
        }

		if(e.getSource() == tButton)
		{
			if(tButton.isSelected())
				miningOperations(6);
			else
				displayOperations(100);
		}
	}
}
