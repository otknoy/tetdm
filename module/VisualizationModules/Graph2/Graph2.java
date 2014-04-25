//
// Visuzalization module for TETDM 
// Graph2.java Version 0.36
// Copyright(C) 2011 - 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.Graph2;		// Replace ALL [VisualizationStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;
import javax.swing.event.*;


public class Graph2 extends VisualizationModule implements ActionListener
{
	double value[];
    double displayValue[];
    int dataNumber;
	
    int leftMargin;
    int rightMargin;
    int topMargin;
    int bottomMargin;
    
    double valueMax, valueMin;
    double axisMax, axisMin;
    int width,height;
	
    int focusNumber;
	
    
	JPanel layer;
	JButton minus,plus,minus2,plus2;
    JToggleButton sum,percent;
    
    int base1 = 10, base2 = 10;
    
    boolean cumulation = false;
    
	public Graph2()
	{
		setModuleID(23);			// Set your module ID after you have got it
        dataNumbers = new int[]{0,0,0,0,    // b,i,d,S
                                0,0,1,0,    // bA,iA,dA,SA
                                0,0,0};     // bA2,iA2,dA2
		setToolType(2);		
	}
	
	public void initializePanel()
	{
		//Create operation pane
		layer = new JPanel();
        layer.setLayout(new GridLayout(1,6));
        layer.setBackground(Color.CYAN);
		add("South",layer);
		
		minus = new JButton("P-");
        minus.setBackground(Color.YELLOW);
        minus.addActionListener(this);
		layer.add(minus);
		
		plus = new JButton("P+");
        plus.setBackground(Color.YELLOW);
        plus.addActionListener(this);
		layer.add(plus);
		
		minus2 = new JButton("M-");
        minus2.setBackground(Color.YELLOW);
        minus2.addActionListener(this);
		layer.add(minus2);
		
		plus2 = new JButton("M+");
        plus2.setBackground(Color.YELLOW);
        plus2.addActionListener(this);
		layer.add(plus2);

		sum = new JToggleButton("SUM");
        sum.setBackground(Color.YELLOW);
        sum.addActionListener(this);
		layer.add(sum);
        
		percent = new JToggleButton("%");
        percent.setBackground(Color.YELLOW);
        percent.addActionListener(this);
		layer.add(percent);
	}
	
	public void initializeData()
	{
        dataNumber = 10;
        
		value = new double[dataNumber];
        focusNumber = 0;
		
        leftMargin = 50;
        rightMargin = 20;
        topMargin = 20;
        bottomMargin = 50;
        
        valueMax = valueMin = 0;
        axisMax = axisMin = 0;
	}
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
            case 4503:
                if(focusNumber >= dataNumber)
                    focusNumber = 0;
                value[focusNumber] = text.focus.getMainFocusDouble();
                focusNumber++;
                repaint();
                break;				
				
			case 0:			
				repaint();
				break;
				
            case 1:focusNumber = 0;
                break;				
		}		
	}
	
	public boolean setData(int dataID, double score[])
	{
        switch(dataID)
        {
            case 0:
				if(score.length == 0)
					return false;
								
				value = score;
				dataNumber = value.length;
                
                createDisplayValue();
                calculateValueMaxMin();
                
                calculatePercent();
                
				repaint();
                return true;
        }
        
		return false;
	}	
	
//	public void setFont(){}	
	
    public void createDisplayValue()
    {
        displayValue = new double[value.length];
        for(int i=0;i<value.length;i++)
            displayValue[i] = value[i];
    }
    
    public void calculateValueMaxMin()
    {
        valueMax = valueMin = displayValue[0];
        
		for(int i=1;i<displayValue.length;i++)
        {
			if(displayValue[i] > valueMax)
				valueMax = displayValue[i];
			if(displayValue[i] < valueMin)
				valueMin = displayValue[i];
        }
        
        axisMax = valueMax;
        axisMin = valueMin;
    }
    
	//background
	public void drawBackground(Graphics2D g2)
	{		
        g2.setColor(Color.black);
        g2.fillRect(0,0, sizeX, sizeY);
        g2.setColor(Color.white);
		g2.drawLine(leftMargin, topMargin, leftMargin, sizeY-bottomMargin);
	}
	
	public void drawDistribution(Graphics2D g2)
	{
		if(displayValue.length == 0)
			return;
		
        width = sizeX - leftMargin - rightMargin;
        height = sizeY - topMargin - bottomMargin;

        if(valueMax == valueMin)
            return;
        
        g2.setColor(Color.cyan);
        for(int i=1;i<displayValue.length;i++)
            g2.drawLine(leftMargin +  width * (i-1)/(value.length-1) ,(int)(topMargin + height * (axisMax - displayValue[i-1]) / (axisMax - axisMin)),
                        leftMargin +  width * i/(value.length-1) ,(int)(topMargin + height * (axisMax - displayValue[i]) / (axisMax - axisMin)));
        
        g2.setColor(Color.yellow);        
        for(int i=0;i<displayValue.length;i++)
            g2.fillOval(leftMargin +  width * i/(value.length-1)-5 ,(int)(topMargin + height * (axisMax - displayValue[i]) / (axisMax - axisMin))-5, 10,10);
	}
    
    public void drawGuide(Graphics2D g2)
    {
        int n=5;
        
        g2.setColor(Color.white);
        
        for(int i=0;i<5;i++)
        {
            g2.drawString(""+String.format("%.1f",axisMin+(axisMax-axisMin)*(n-1-i)/(n-1)),2,topMargin+height*i/(n-1)+6);
            g2.drawLine(leftMargin-10,topMargin+height*i/(n-1),leftMargin+10,topMargin+height*i/(n-1));
        }
        
        if(axisMin < 0)
        {
            g2.drawString(" 0 ",10,(int)(topMargin+height * axisMax/(axisMax-axisMin))+6);
            g2.drawLine(leftMargin-10,(int)(topMargin+height * axisMax/(axisMax-axisMin)),leftMargin+width,(int)(topMargin+height * axisMax/(axisMax-axisMin)));
        }
    }
	
	//////////paint
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		getPanelSize();			
		
		drawBackground(g2);		//background
		drawDistribution(g2);
        drawGuide(g2);
	}
	
	public void update(Graphics g)		//avoid from blinking
	{
		paintComponent(g);
	}
	
    public void axisFromValue()
    {
        for(int i=10;i<1000000001;i*=10)
            if(valueMax < i)
            {
                for(int j=i/10;j<i;j+=i/10)
                    if(j > valueMax)
                    {
                        axisMax = j;
                        break;
                    }
                base1 = i;
                break;
            }
        
        for(int i=10;i<1000000001;i*=10)
            if(-valueMin < i)
            {
                for(int j=i/10;j<i;j+=i/10)
                    if(j > -valueMin)
                    {
                        axisMin = -j;
                        break;
                    }
                base2 = i;
                break;
            }
    }
    
    public void calculatePercent()
    {
        //NEWLY ADDED
        if(percent.isSelected() == false)
            axisFromValue();
        else
        {
            axisMax = valueMax;
            axisMin = valueMin;
        }
    }
    
    public void calculateCumulation()
    {
        //CUMULATE
        if(sum.isSelected() == true)
            for(int i=1;i<value.length;i++)
                displayValue[i] += displayValue[i-1];
        else
            createDisplayValue();
        
        calculateValueMaxMin();
    }
    
	//Action Listener
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == minus)
        {
            if(axisMax > 0)
                axisMax -= base1/10;
            repaint();
        }
        
        if(e.getSource() == plus)
        {
            axisMax += base1/10;
            repaint();
        }
        
        if(e.getSource() == minus2)
        {
            axisMin -= base2/10;
            repaint();
        }
        
        if(e.getSource() == plus2)
        {
            if(axisMin < 0)
                axisMin += base2/10;
            repaint();
        }
        
        if(e.getSource() == sum)
        {
            calculateCumulation();
            calculatePercent();
            repaint();
        }
        
        if(e.getSource() == percent)
        {
            calculatePercent();
            repaint();
        }
	}
    
    
    
}