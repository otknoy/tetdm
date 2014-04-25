//
// Visuzalization module for TETDM 
// DrawNodes.java Version 0.36
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
// This program is provided under the license.
// You should read the README file.
//


package module.VisualizationModules.DrawNodes;		// Replace ALL [VisualizationStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import java.lang.Runtime;


public class DrawNodes extends VisualizationModule implements ActionListener, MouseListener, MouseMotionListener
{
    DisplayNetwork network;
    
    boolean sizeChanged;
    int touchNumber, oldTouchNumber;
    
    int fontSize;
    
	//For objects operation
	int mouseX, mouseY;			//Coordinates for a mouse
	int oldMouseX, oldMouseY;	//Last Coordinates for a mosue
	boolean fieldMove;		//Flag for field moving
    
	JPanel layer;
	JButton spring1,keyseg,keysen,segkey,senkey;
    JButton plus, minus;
	
    boolean active[];
    boolean keywordSegment;
    
    String buttonNamesInJapanese[];
    String relation;
	

	
	public DrawNodes()
	{
		setModuleID(1017);			// Set your module ID after you have got it
		setToolType(1);
	}
	
	public void initializePanel()
	{
		buttonNamesInJapanese = fileReadArray();
		
		//Create operation pane
		layer = new JPanel();
        layer.setLayout(new GridLayout(0,4));
        layer.setBackground(Color.CYAN);
		add("South",layer);
		
		keyseg = new JButton("Keyseg");
        keyseg.setBackground(Color.YELLOW);
        keyseg.addActionListener(this);
		layer.add(keyseg);
		
		segkey = new JButton("Segkey");
        segkey.setBackground(Color.YELLOW);
        segkey.addActionListener(this);
		layer.add(segkey);
        
		minus = new JButton("-");
        minus.setBackground(Color.YELLOW);
        minus.addActionListener(this);
		layer.add(minus);
        
		plus = new JButton("+");
        plus.setBackground(Color.YELLOW);
        plus.addActionListener(this);
		layer.add(plus);
        
        relation = "Relation";
		
		addMouseListener(this);			//MouseListener
		addMouseMotionListener(this);	//MouseMotionListener
		
		if(isMenuInJapanese())
		{
			keyseg.setText(buttonNamesInJapanese[1]);
			segkey.setText(buttonNamesInJapanese[3]);
			
            relation = buttonNamesInJapanese[5];
		}
	}
	
	public void initializeData()
	{
		touchNumber = -1;
        fontSize = 16;
		fieldMove = false;
	}
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 4501:
                repaint();
                break;
                
			case 0:
                createKeywordRelationBySegment();
                network.startSpringModel(sizeX, sizeY);
				repaint();
                keywordSegment = true;
				break;
		}		
	}	

	//////////DRAWING
	void drawBackground(Graphics2D g2)
	{
        g2.setColor(new Color(0,128,255));
        g2.fillRect(0,0, sizeX, sizeY);
	}
	
	//Display links
	public void drawLinks(Graphics2D g2)
	{
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
        
        network.drawLinkAddLeft(g2);
	}
	
	public void drawData(Graphics2D g2)
	{
        g2.setColor(new Color(0xff,0x66,0x00));
        network.fillOval(g2);
        
        g2.setColor(Color.black);
        network.drawOval(g2);
        
        network.drawWord(g2);
	}
    
	public void drawTouchedData(Graphics2D g2)
	{
        if(keywordSegment)
        {
            if(text.focus.mainFocusKeyword >= 0)
                touchNumber = text.focus.mainFocusKeyword;
        }
        else
            if(text.focus.mainFocusSegment >= 0)
                touchNumber = text.focus.mainFocusSegment;
        
        if(touchNumber < 0)
            return;
        
        g2.setColor(Color.green);
        network.fillOval(g2, touchNumber);
        
        g2.setColor(Color.red);
        network.drawOval(g2, touchNumber);
        
        network.drawWord(g2, touchNumber, Color.green);
	}

	
	//Display parameters
	public void drawParameters(Graphics2D g2)
	{
		g2.setColor(Color.yellow);
		g2.setFont(new Font("Dialog", Font.BOLD, 20));
		
		g2.drawString(relation+" > "+String.format("%.2f", network.linkThreshold), 5, sizeY-50);
		
		if(network.springModel.springModelMoving)
			g2.drawString("Moving Spring",100,sizeY-50);
		
		
		g2.drawString("Nodes="+network.activeNumber+"/"+network.objectNumber,sizeX-200,sizeY-75);
		g2.drawString("Links="+(network.linkNumber-network.objectNumber)/2,sizeX-150,sizeY-50);
	}	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
        
//        System.out.println("REPAINT");
        
        boolean sizeChanged = getPanelSize();
		
        if(sizeChanged)
			network.reSizeByScreenSize(changeX, changeY);            
        
		drawBackground(g2);	//Set background
        
		drawLinks(g2);			//Display links

		drawData(g2);
        drawTouchedData(g2);
        
		drawParameters(g2);	//Display parameters
	}
	
	public void update(Graphics g)		//Avoid from blinking
	{
		paintComponent(g);
	}
	
	public void createActiveForKeyword()
    {
        active = new boolean[text.keywordNumber];
        for(int i=0;i<text.keywordNumber;i++)
            if(text.keyword[i].segmentFrequency < 2)
                active[i] = false;
            else
                active[i] = true;
    }
    
	public void createActiveForSegment()
    {
        active = new boolean[text.segmentNumber];
        for(int i=0;i<text.segmentNumber;i++)
            active[i] = true;
    }
    
    public void createKeywordRelationBySegment()
    {
        String names[] = new String[text.keywordNumber];
        for(int i=0;i<names.length;i++)
            names[i] = text.keyword[i].word;
        
        network = new DisplayNetwork(names, fontSize, Color.black);
        
        createActiveForKeyword();        
        network.setActive(active);
        
        //text.keywordRelationBySentence
        
        text.keywordRelationBySegment.optimizeLink(text.keywordRelationBySegment.cos, 0.51, active);
        network.linkThreshold = text.keywordRelationBySegment.linkThreshold;
        
        for(int i=0;i<network.objectNumber;i++)
        {
            network.object[i].x = sizeX/2 + sizeX/4 * Math.cos(i);
            network.object[i].y = sizeY/2 + sizeY/4 * Math.sin(i);
        }
        
        network.setLinkValue(text.keywordRelationBySegment.cos);
        network.createLinksFromValue();        
        
        network.calculateLinkNumber();
        network.setActiveByLinkInActive();
    }
    
    public void createSegmentRelationByKeyword()
    {
        String names[] = new String[text.segmentNumber];
        for(int i=0;i<names.length;i++)
            names[i] = "SEG:"+(i+1);
        
        network = new DisplayNetwork(names, fontSize, Color.black);
        
        //text.sentenceRelation
        
        text.segmentRelation.optimizeLink(text.segmentRelation.cos, 0.51);
        network.linkThreshold = text.segmentRelation.linkThreshold;        
        
        for(int i=0;i<network.objectNumber;i++)
        {
            network.object[i].x = sizeX/2 + sizeX/4 * Math.cos(i);
            network.object[i].y = sizeY/2 + sizeY/4 * Math.sin(i);
        }
        
        network.setLinkValue(text.segmentRelation.cos);
        network.createLinksFromValue();

        network.calculateLinkNumber();
        network.setActiveByLinkInActive();
    }
	
	////Buttons
	//Action Listener
	public void actionPerformed(ActionEvent e)
	{		
		if(e.getSource() == keyseg)		
		{
            touchNumber = -1;
            createKeywordRelationBySegment();
			network.startSpringModel(sizeX, sizeY);
			repaint();
            keywordSegment = true;
		}
		
		if(e.getSource() == segkey)		
		{
            touchNumber = -1;
            createSegmentRelationByKeyword();
			network.startSpringModel(sizeX, sizeY);
			repaint();
            keywordSegment = false;
		}
		
		if(e.getSource() == minus)
		{
            if(network.linkThreshold > 0)
                network.linkThreshold -= 0.01;
            
            if(keywordSegment)
                createActiveForKeyword();
            else
                createActiveForSegment();            
            network.setActive(active);
            
            network.createLinksFromValue();
            network.setActiveByLinkInActive();
            
   			network.startSpringModel(sizeX, sizeY);
            repaint();
		}
        
		if(e.getSource() == plus)
		{
            if(network.linkThreshold < 0.989)
                network.linkThreshold += 0.01;

            if(keywordSegment)
                createActiveForKeyword();
            else
                createActiveForSegment();            
            network.setActive(active);
            
            network.createLinksFromValue();
            network.setActiveByLinkInActive();
            
			network.startSpringModel(sizeX, sizeY);
            repaint();
		}
		
	}	
	
	
	
	//MouseListener
	public void mousePressed(MouseEvent me)	//Press
	{
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
		fieldMove = false;
	}
	
	public void mouseEntered(MouseEvent me){}	//Enter an area
	public void mouseExited(MouseEvent me){}	//Exit an area
	public void mouseClicked(MouseEvent me){}	//Click
		
	
	//MouseMotionListener
	public void mouseDragged(MouseEvent me)
    {
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
		mouseX = me.getX();
		mouseY = me.getY();
        
		touchNumber = network.getOnMouseObjectAddLeft(mouseX, mouseY);
        
        if(touchNumber != oldTouchNumber)
        {
            if(keywordSegment)
                text.focus.mainFocusKeyword = touchNumber;
            else
                text.focus.mainFocusSegment = touchNumber;

            if(touchNumber >= 0)
                repaintOthersByTouch();
            
            repaint();
            oldTouchNumber = touchNumber;
        }
	}
}
