
//
// Visuzalization module for TETDM
// KeywordCompare.java Version 0.36
// Copyright(C) 2012 - 2013 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.KeywordCompare;

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class KeywordCompare extends VisualizationModule// implements ActionListener
{
//    boolean dataNameA[], dataNameB[];
    int dataA[], dataB[];
    int dataNumberA, dataNumberB;
    
    boolean checkDataA, checkDataB;
    
    String outputInJapanese;
    
    int leftCount, rightCount, centerCount;

    int leftSortValue[], leftSortedIndex[];
    int rightSortValue[], rightSortedIndex[];
    int centerSortValue[], centerSortedIndex[];
    
	public KeywordCompare()
	{
		setModuleID(21);			// Set your module ID after you have got it
        dataNumbers = new int[]{0,0,0,0,    // b,i,d,S
            0,2,0,0,    // bA,iA,dA,SA
            0,0,0};     // bA2,iA2,dA2
		setToolType(2);		
	}
	
	public void initializePanel(){outputInJapanese = fileRead();}
	
	public void initializeData()
    {
        dataNumberA = dataNumberB = 0;
        checkDataA = checkDataB = false;
    }
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 4501:
				repaint();
				break;
				
			case 0:
                repaint();
				break;
		}
	}
	
	public boolean setData(int dataID, int data[])
	{
        if(data.length != text.keywordNumber)
            return false;
        
        switch(dataID)
        {
            case 0:	dataA = data;
                if(dataNumberA == 0)
                    dataNumberA = dataA.length;
                
                checkDataA = true;
                if(checkDataB == true)
                    createIndex();
                
				repaint();
                return true;
                
            case 1:	dataB = data;
                if(dataNumberB == 0)
                    dataNumberB = data.length;
                
                checkDataB = true;
                if(checkDataA == true)
                    createIndex();
                
				repaint();
                return true;
        }
        
		return false;
	}
    

	
    //	public void setFont(){}
	
	//background
	public void drawBackground(Graphics2D g2)
	{
        g2.setColor(Color.white);
        g2.fillRect(0,0, sizeX, sizeY);
        
        //left
        g2.setColor(new Color(0xff,0xf6,0x8f));//(152,251,152));
        g2.fillRoundRect( sizeX * 2/100, sizeY * 4/100, sizeX * 33/100, sizeY * 92/100, 10, 10);
        
        //right
        g2.setColor(new Color(0x54,0xff,0x94));//(255,181,197));
        g2.fillRoundRect( sizeX * 65/100, sizeY * 4/100, sizeX * 33/100, sizeY * 92/100, 10, 10);
        
        //center right
        g2.setColor(new Color(1.0f,1.0f,0.58f,0.8f));//(105,139,34));
        g2.fillRoundRect( sizeX * 33/100, sizeY * 4/100, sizeX * 30/100, sizeY * 92/100, 10, 10);
        
        //center left
        g2.setColor(new Color(0.76f,1.0f,0.76f,0.6f));//(205,105,201));//(188,143,143));
        g2.fillRoundRect( sizeX * 35/100, sizeY * 4/100, sizeX * 30/100, sizeY * 92/100, 10, 10);
	}
	

	
	//////////paint
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
        
		getPanelSize();
        
		drawBackground(g2);		//background
        
        if(checkDataA == false || checkDataB == false)
            return;
        
		drawWords(g2);

	}
	
	public void update(Graphics g)		//avoid from blinking
	{
		paintComponent(g);
	}
    
    public void drawWords(Graphics2D g2)
    {
        String displayString;
        
        g2.setFont(new Font("Dialog", Font.BOLD, 12));
        g2.setColor(Color.black);
        
        //left
        for(int i=0;i<60 && i<leftCount;i++)
        {
            if(dataA[leftSortedIndex[i]] == 1)
                break;
			
			if(text.focus.getMainFocusString().equals(text.keyword[leftSortedIndex[i]].word))
				g2.setColor(Color.red);
			else if(text.focus.getSubFocusString().equals(text.keyword[leftSortedIndex[i]].word))
				g2.setColor(Color.blue);
			else
				g2.setColor(Color.black);
            
            displayString = text.keyword[leftSortedIndex[i]].word +
                "("+dataA[leftSortedIndex[i]]+","+dataB[leftSortedIndex[i]]+ ")";
            g2.drawString(displayString, sizeX * 5/100 + sizeX * 14/100 * (i%2), sizeY * 8/100 + sizeY * 3/100 * (int)(i/2) );
        }
        
        //right
        for(int i=0;i<60 && i<rightCount;i++)
        {
            if(dataB[rightSortedIndex[i]] == 1)
                break;
			
			if(text.focus.getMainFocusString().equals(text.keyword[rightSortedIndex[i]].word))
				g2.setColor(Color.red);
			else if(text.focus.getSubFocusString().equals(text.keyword[rightSortedIndex[i]].word))
				g2.setColor(Color.blue);
			else
				g2.setColor(Color.black);			
            
            displayString = text.keyword[rightSortedIndex[i]].word +
                "("+dataA[rightSortedIndex[i]]+","+dataB[rightSortedIndex[i]]+ ")";
            g2.drawString(displayString, sizeX * 68/100 + sizeX * 14/100 * (i%2), sizeY * 8/100 + sizeY * 3/100 * (int)(i/2) );
        }
        
        //center
        for(int i=0;i<30 && i<centerCount;i++)
        {
            if(dataA[leftSortedIndex[i]] + dataB[rightSortedIndex[i]] == 2)
                break;
            
			if(text.focus.getMainFocusString().equals(text.keyword[centerSortedIndex[i]].word))
				g2.setColor(Color.red);
			else if(text.focus.getSubFocusString().equals(text.keyword[centerSortedIndex[i]].word))
				g2.setColor(Color.blue);				
			else
				g2.setColor(Color.black);			
			
            displayString = text.keyword[centerSortedIndex[i]].word +
                "("+dataA[centerSortedIndex[i]]+","+dataB[centerSortedIndex[i]]+ ")";
            g2.drawString(displayString, sizeX * 40/100, sizeY * 8/100 + sizeY * 3/100 * (i%30) );
        }
    }
    
    public void createIndex()
    {
		int sortValue[] = new int[text.keywordNumber];
		int sortedIndex[] = new int[text.keywordNumber];
		boolean checkKeywords[] = new boolean[text.keywordNumber];
		
		for(int i=0;i<text.keywordNumber;i++)
			checkKeywords[i] = false;
		
		
		//LEFT
        for(int i=0;i<text.keywordNumber;i++)
            sortValue[i] = dataA[i] - dataB[i];
		
        Qsort.initializeIndex(sortedIndex, text.keywordNumber);
        Qsort.quicksort(sortValue, sortedIndex, text.keywordNumber);
		
		for(leftCount=0;leftCount<60;leftCount++)
		{
			if(sortValue[leftCount] <= 0)
				break;			
		}
		
        leftSortValue = new int[leftCount];
        leftSortedIndex = new int[leftCount];		
		
		for(int i=0;i<leftCount;i++)
		{
			leftSortValue[i] = sortValue[i];
			leftSortedIndex[i] = sortedIndex[i];
			checkKeywords[sortedIndex[i]] = true;
		}
		
		
		//RIGHT
        for(int i=0;i<text.keywordNumber;i++)
            sortValue[i] = dataB[i] - dataA[i];
		
        Qsort.initializeIndex(sortedIndex, text.keywordNumber);
        Qsort.quicksort(sortValue, sortedIndex, text.keywordNumber);
		
		for(rightCount=0;rightCount<60;rightCount++)
		{
			if(sortValue[rightCount] <= 0)
				break;			
		}
		
        rightSortValue = new int[rightCount];
        rightSortedIndex = new int[rightCount];		
		
		for(int i=0;i<rightCount;i++)//
		{
			rightSortValue[i] = sortValue[i];
			rightSortedIndex[i] = sortedIndex[i];
			checkKeywords[sortedIndex[i]] = true;
		}		
		
		
		//CENTER
        for(int i=0;i<text.keywordNumber;i++)
            sortValue[i] = dataA[i] + dataB[i];
		
        Qsort.initializeIndex(sortedIndex, text.keywordNumber);
        Qsort.quicksort(sortValue, sortedIndex, text.keywordNumber);

		centerCount=0;
		int count;
		for(count=0; centerCount < 30 && count < text.keywordNumber; count++)
		{
			if(sortValue[count] <= 0)
				break;
			
			if(checkKeywords[sortedIndex[count]] == true)
				continue;
			
			centerCount++;
		}
		
        centerSortValue = new int[centerCount];
        centerSortedIndex = new int[centerCount];		
		
		centerCount=0;
		for(int i=0;i<count;i++)
		{
			if(checkKeywords[sortedIndex[i]] == true)
				continue;
			
			centerSortValue[centerCount] = sortValue[i];
			centerSortedIndex[centerCount] = sortedIndex[i];
			checkKeywords[sortedIndex[i]] = true;
			
			centerCount++;
		}	
		
		
    }

}
