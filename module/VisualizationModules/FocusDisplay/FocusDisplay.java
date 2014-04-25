//
// Visuzalization module for TETDM 
// FocusDisplay.java Version 0.36
// Copyright(C) 2011 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the licenses.
// You Should read the README file.
//


package module.VisualizationModules.FocusDisplay;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class FocusDisplay extends VisualizationModule
{
	Sizepanel sizepanel;	
	
	//Text pane
	JTextPane textPanel;
	JScrollPane scrollPanel;

	//for DISPLAY
	String displayText;	//display text
	
	int type;	// keywords, segments, sentences
	int kind;	// main, sub, set

    String key,seg,sen,main,sub;
    String kindWords[][];

    String outputInJapanese[];

	public FocusDisplay()
	{
		setModuleID(1016);
		setToolType(1);		
	}

	public void initializePanel()
	{
		textPanel = new JTextPane();
		textPanel.setContentType("text/plain");		
		scrollPanel = new JScrollPane(textPanel);
		add(scrollPanel);
		
		sizepanel = new Sizepanel();
		sizepanel.setPreferredSize(new Dimension(0,60));		
		add("South",sizepanel);
		
		type = 1;
		kind = 0;
		kindWords = new String[3][3];
		outputInJapanese = fileReadArray();
	}
	
	public void initializeData()
	{
		if(isMenuInJapanese())
		{
			key =  outputInJapanese[0];
			seg =  outputInJapanese[1];
			sen =  outputInJapanese[2];
			main = outputInJapanese[3];
			sub = outputInJapanese[4];
			String list[] =  {main,sub,key,main,sub,seg,main,sub,sen};
			for(int i=0;i<3;i++)
			    for(int j=0;j<3;j++)
				kindWords[i][j] = list[i*3+j];
			displayText = outputInJapanese[5];
		}
		else
		{
			key = "KEY";
			seg = "SEG";
			sen = "SEN";
			String list[] = {"MAIN","SUB","KEYWORDS","MAIN","SUB","SEGMENTS","MAIN","SUB","SENTENCES"};
			for(int i=0;i<3;i++)
			    for(int j=0;j<3;j++)
				kindWords[i][j] = list[i*3+j];
			displayText = "Nothing";
		}
	}
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 4501:
			case 4502:	
			case 0:
				initialDisplay();
				break;
				
			case 11:
				type = 0;
				kind = 0;
				break;

			case 12:
				type = 0;
				kind = 1;
				break;
				
			case 13:
				type = 0;
				kind = 2;
				break;
				
			case 21:
				type = 1;
				kind = 0;
				break;
				
			case 22:
				type = 1;
				kind = 1;
				break;
				
			case 23:
				type = 1;
				kind = 2;
				break;
				
			case 31:
				type = 2;
				kind = 0;
				break;
				
			case 32:
				type = 2;
				kind = 1;
				break;
				
			case 33:
				type = 2;
				kind = 2;
				break;
		}		
	}	
	
	public void initialDisplay()
	{
	    if(isMenuInJapanese())
		{
		    key = outputInJapanese[0];
		    seg = outputInJapanese[1];
		    sen = outputInJapanese[2];
		}

		textPanel.setFont(new Font("Dialog", Font.BOLD, text.fontSize));
		
		displayText = "";
		switch(type)
		{
			case 0: switch(kind)
			{
				case 0:	if(text.focus.mainFocusKeyword >= 0)
					displayText = "<"+key+(text.focus.mainFocusKeyword+1)+">\n"+text.keyword[text.focus.mainFocusKeyword].word;
					break;
				case 1: if(text.focus.subFocusKeyword >= 0)
					displayText = "<"+key+(text.focus.subFocusKeyword+1)+">\n"+text.keyword[text.focus.subFocusKeyword].word;
					break;
				case 2: for(int i=0;i<text.keywordNumber;i++)
					if(text.focus.focusKeywords[i])
						displayText += "<"+key+(i+1)+">\n"+text.keyword[i].word+"\n";
					break;
			}
				break;				
				
			case 1: switch(kind)
			{
				case 0:	if(text.focus.mainFocusSegment >= 0)
					displayText = "<"+seg+(text.segment[text.focus.mainFocusSegment].segmentID+1)+">\n"+text.segment[text.focus.mainFocusSegment].segmentText;
					break;
				case 1: if(text.focus.subFocusSegment >= 0)
					displayText = "<"+seg+(text.segment[text.focus.subFocusSegment].segmentID+1)+">\n"+text.segment[text.focus.subFocusSegment].segmentText;
					break;
				case 2: for(int i=0;i<text.segmentNumber;i++)
					if(text.focus.focusSegments[i])
						displayText += "<"+seg+(text.segment[i].segmentID+1)+">\n"+text.segment[i].segmentText+"\n";
					break;
			}
				break;
				
			case 2:	switch(kind)
			{
				case 0:	if(text.focus.mainFocusSentence >= 0)
					displayText = "<"+sen+(text.sentence[text.focus.mainFocusSentence].sentenceID+1)+">\n"+text.sentence[text.focus.mainFocusSentence].sentenceText;
					break;
				case 1: if(text.focus.subFocusSentence >= 0)
					displayText = "<"+sen+(text.sentence[text.focus.subFocusSentence].sentenceID+1)+">\n"+text.sentence[text.focus.subFocusSentence].sentenceText;
					break;
				case 2: for(int i=0;i<text.sentenceNumber;i++)
					if(text.focus.focusSentences[i])
						displayText += "<"+sen+(text.sentence[i].sentenceID+1)+">\n"+text.sentence[i].sentenceText+"\n";
					break;
			}
				break;
		}
		
		textPanel.setText(displayText);
		textPanel.setCaretPosition(0);
		
		sizepanel.repaint();
	}
	
	class Sizepanel extends JPanel implements MouseListener
	{
		int sizex,sizey;
		int mousex,mousey;
		
	    //		String kindWords[][] = {{"MAIN","SUB","KEYWORDS"},{"MAIN","SUB","SEGMENTS"},{"MAIN","SUB","SENTENCES"}};
		
		int x[] = new int[3];
		int y[] = new int[3];
		
		//constructer
		Sizepanel()
		{
			addMouseListener(this);
		}
		
		//background
		public void draw_background(Graphics2D g2)
		{
			sizex = getWidth();
			sizey = getHeight();
			
			g2.setColor(new Color(0xfa, 0x80, 0x72));
			g2.fillRect(0,0,sizex,sizey/2);
			g2.setColor(new Color(0xff, 0xd7, 0x72));			
			g2.fillRect(sizex/6,0,sizex/6,sizey/2);
			g2.fillRect(3*sizex/6,0,sizex/6,sizey/2);
			g2.fillRect(5*sizex/6,0,sizex/6,sizey/2);

			g2.setColor(new Color(0xff, 0xff, 0x00));
			g2.fillRect(0,sizey/2,sizex/3,sizey);
			g2.setColor(new Color(0x00, 0xbf, 0xff));
			g2.fillRect(sizex/3,sizey/2,sizex/3,sizey);
			g2.setColor(new Color(0x00, 0xff, 0x00));
			g2.fillRect(2*sizex/3,sizey/2,sizex/3,sizey);
			
		
			
			g2.setFont(new Font("Dialog", Font.BOLD, 20));		
			g2.setColor(Color.black);
			
			y[0] = sizey/4+10;
			y[1] = sizey/4+10;
			y[2] = 3*sizey/4+10;			
			
			for(int i=0;i<3;i++)
			{
				x[0] = (1+4*i)*sizex/12 - kindWords[i][0].length()*5-5;
				x[1] = (3+4*i)*sizex/12 - kindWords[i][1].length()*5-5;
				x[2] = (1+2*i)*sizex/6 - kindWords[i][2].length()*5-15;
				
				for(int j=0;j<3;j++)
				{
					if(i == type && j == kind)
						g2.setColor(Color.red);	
					else
						g2.setColor(Color.black);
					
					g2.drawString(kindWords[i][j],x[j],y[j]);
				}
			}
		}
		
		//////////paint
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;
			
			draw_background(g2);		//background
		}
		
		public void update(Graphics g)		//avoid from blinking
		{
			paintComponent(g);
		}
		
		//MouseListener
		public void mousePressed(MouseEvent me){}	//Press
		public void mouseReleased(MouseEvent me){}	//Release
		public void mouseEntered(MouseEvent me){}	//Enter an area
		public void mouseExited(MouseEvent me){}	//Exit an area
		
		public void mouseClicked(MouseEvent me)	//Click
		{
			mousex = me.getX();
			mousey = me.getY();
			
			type = 3 * mousex / sizex;

			if(mousey > sizey/2)
				kind = 2;
			else 
				kind = (int)(6 * mousex / sizex) % 2;

			displayOperations(0);
		}
	}	
}
