//
// Visuzalization module for TETDM 
// XDrawDisplay.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the licenses.
// You Should read the README file.
//

package module.VisualizationModules.XDrawDisplay;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class XDrawDisplay extends VisualizationModule implements MouseListener, MouseMotionListener
{
    DisplayNetwork network;

    int fontSize = 20;
    
    boolean sizeChanged;
    int touchNumber, oldTouchNumber;
    
	//For objects operation
	int mouseX, mouseY;			//Coordinates for a mouse
    
	boolean fieldMove;		//Flag for field moving
	int oldMouseX, oldMouseY;	//Last Coordinates for a mosue
    
    
	//Related to drawing of background
	Toolkit tk;
	public Image image1;


	boolean makeMap = false;
	
	int nodeKeyIDList[];
	int topicNumber;	
	
    int sbaseList[];
    int topicList[];
    int backList[];
    int featList[];
	
	public XDrawDisplay()
	{
		setModuleID(8);
        dataNumbers = new int[]{0,0,0,0,    // b,i,d,S
            0,4,0,0,    // bA,iA,dA,SA
            0,0,0};     // bA2,iA2,dA2
	}

	public void initializePanel()
	{
		tk = getToolkit();		
		image1 = tk.getImage(myModulePath+"fuji.jpg");
		addMouseListener(this);			//MouseListener
		addMouseMotionListener(this);	//MouseMotionListener	
	}	
	
	public void initializeData()
	{
        String noData[] = new String[]{"NO DATA"};
        
        network = new DisplayNetwork(noData, fontSize, Color.black);
        network.centeringFromScreenSize(sizeX,sizeY);
        
		touchNumber = -1;
		oldTouchNumber = -1;
		
		fieldMove = false;
		
		topicNumber = 0;
	}

	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
            case 4501:
                repaint();
                break;
                
			case 0:makeMap = makeMapData();
			    if(makeMap);
					repaint();
			    break;
		}		
	}

	public boolean setData(int dataID, int data[])
	{
        switch(dataID)
        {
            case 0:
                sbaseList = data;
                return true;
                
            case 1:
                topicList = data;
                return true;
                
            case 2:
                backList = data;
                return true;
                
            case 3:
                featList = data;
                return true;                
        }
        
		return false;
	}
    
    boolean makeMapData()
    {
		int number, c;
        String dataName[];

        if(checkDataNumbers() == false)
			return false;
		
		number = sbaseList.length + topicList.length + backList.length + featList.length;
    	c = 0;
        dataName = new String[number];
		for(int i=0;i<sbaseList.length;i++,c++)
            dataName[c] = text.keyword[sbaseList[i]].word;
		for(int i=0;i<topicList.length;i++,c++)
			dataName[c] = text.keyword[topicList[i]].word;
		for(int i=0;i<backList.length;i++,c++)
			dataName[c] = text.keyword[backList[i]].word;
		for(int i=0;i<featList.length;i++,c++)
			dataName[c] = text.keyword[featList[i]].word;
        
        network = new DisplayNetwork(dataName, fontSize, Color.black);
	
        c=0;
		for(int i=0;i<sbaseList.length;i++,c++)
		{
			network.object[c].id = sbaseList[i];
			network.object[c].x = sizeX/2-10;
			network.object[c].y = (30*sizeY/100) / sbaseList.length * i + 25;
			network.object[c].color = new Color(0xff,0x66,0x00);
	    }

		for(int i=0;i<topicList.length;i++,c++)
	    {
			network.object[c].id = topicList[i];
			network.object[c].x = sizeX/2-10;
			network.object[c].y = (30*sizeY/100) + 50 + i*25;
			network.object[c].color = new Color(0xa5,0x2a,0x2a);
	    }

		for(int i=0;i<backList.length;i++,c++)
	    {
			network.object[c].id = backList[i];
			network.object[c].x = sizeX/2-30 + (80+((sizeX-200)/(backList.length*2))*i)*(int)Math.pow(-1.0,(double)i);
			network.object[c].y = (30*sizeY/100) + i*15 + 50;
			network.object[c].color = new Color(0x00,0x00,0xcc);
	    }

		for(int i=0;i<featList.length;i++,c++)
	    {
			network.object[c].id = featList[i];
			network.object[c].x = sizeX/2-30 + (80+((sizeX-200)/(featList.length*2))*i)*(int)Math.pow(-1.0,(double)i);
			network.object[c].y = (30*sizeY/100) + i*15 + 200;
			network.object[c].color = new Color(0x00,0x64,0x00);
	    }
		return true;
    }

	//////////DRAWING

	void calculateXY()
	{		
        network.reSizeByScreenSize(changeX, changeY);
	}	
	
	void drawBackground(Graphics2D g2)
	{
		g2.drawImage(image1,0,0,sizeX,sizeY,this);
		g2.setColor(Color.red);
		g2.drawLine(0,30*sizeY/100,sizeX,30*sizeY/100);
	}

	//Display nodes    
	public void drawData(Graphics2D g2)
	{
        g2.setColor(Color.green);
        network.fillOval(g2);
        
        g2.setColor(Color.black);
        network.drawOval(g2);
        
        network.drawWord(g2);
	}
    
	public void drawTouchedData(Graphics2D g2)
	{
        if(text.focus.mainFocusKeyword >= 0)
            for(int i=0;i<network.objectNumber;i++)
                if(network.object[i].id == text.focus.mainFocusKeyword)
                {
                    touchNumber = i;
                    break;
                }
        
        g2.setColor(Color.green);
        network.fillOval(g2, touchNumber);
        
        g2.setColor(Color.red);
        network.drawOval(g2, touchNumber);
        
        network.drawWord(g2, touchNumber, Color.red);
	}
	
	public void paintComponent(Graphics g)
	{
		if(!makeMap)
			return;		
		
		Graphics2D g2 = (Graphics2D)g;

		sizeChanged = getPanelSize();
		
		if(sizeChanged)
			calculateXY();
		
		drawBackground(g2);
		drawData(g2);
        
        if(touchNumber >= 0)
            drawTouchedData(g2);
	}
	
	public void update(Graphics g)		//Avoid from blinking
	{
		paintComponent(g);
	}
	
	//MouseListener
	public void mousePressed(MouseEvent me)	//Press
	{
		if(!makeMap)
			return;		
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		if(touchNumber < 0)
		{
			oldMouseX = mouseX;
			oldMouseY = mouseY;
			fieldMove = true;
			return;
		}
	}
	
	public void mouseReleased(MouseEvent me)	//Release
	{
		if(!makeMap)
			return;		
		
		fieldMove = false;
	}
	
	public void mouseEntered(MouseEvent me){}	//Enter an area
	public void mouseExited(MouseEvent me){}	//Exit an area
	


	public void mouseClicked(MouseEvent me)	//Click
	{
		if(!makeMap)
			return;		
		
		int clickNumber;
		
		mouseX = me.getX();
		mouseY = me.getY();
		clickNumber = network.getOnMouseObjectAddLeft(mouseX, mouseY);
		
		if(clickNumber < 0)
		{
			topicNumber = 0;
			for(int i=0;i<network.objectNumber;i++)
				if(network.object[i].y < 30*sizeY/100)
					topicNumber++;

			nodeKeyIDList = new int[topicNumber];
			
			int c=0;
			for(int i=0;i<network.objectNumber;i++)
				if(network.object[i].y < 30*sizeY/100)
					nodeKeyIDList[c++] = network.object[i].id;
			
			for(int i=0;i<network.objectNumber;i++)///////////////////////////////LINKAGE
				if(network.object[i].y < 30*sizeY/100)
					text.focus.focusKeywords[network.object[i].id] = true;
				else
					text.focus.focusKeywords[network.object[i].id] = false;

			executeAllByClick();
		}
	}	
	
	//MouseMotionListener
	public void mouseDragged(MouseEvent me)	//fieldMove
	{
		if(!makeMap)
			return;		
		
        mouseX = me.getX();
        mouseY = me.getY();
        
        if(touchNumber >= 0)
		{
            network.moveObjectByMouse(mouseX, mouseY, touchNumber);
			repaint();
		}
        
		if(fieldMove == true)
        {
            network.moveObjects(mouseX - oldMouseX, mouseY - oldMouseY);
            oldMouseX = mouseX;
            oldMouseY = mouseY;
            repaint();
        }
	}
	
	public void mouseMoved(MouseEvent me)
	{
		if(!makeMap)
			return;
		
		mouseX = me.getX();
		mouseY = me.getY();
        
		touchNumber = network.getOnMouseObjectAddLeft(mouseX, mouseY);
        
        if(touchNumber != oldTouchNumber)
        {
            if(touchNumber >= 0)
            {
                text.focus.mainFocusKeyword = network.object[touchNumber].id;
                repaintOthersByTouch();
            }
            
            repaint();
            oldTouchNumber = touchNumber;
        }
	}
}
