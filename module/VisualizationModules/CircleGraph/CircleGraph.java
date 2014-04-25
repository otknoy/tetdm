//
// Visuzalization module for TETDM 
// CircleGraph.java Version 0.36
// Copyright(C) 2013 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.CircleGraph;

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class CircleGraph extends VisualizationModule
{
	double value[];
    double arc[];
    double totalArc[];
    int dataNumber;
	
    final double PI = 3.1415926;
    
    int leftMargin;
    int rightMargin;
    int topMargin;
    int bottomMargin;

    int width,height;
    int centerX, centerY;
    int radius;
    
    int r,g,b;    
    int colorBase;
    int colorMax;
    int colorDiff;
    
    boolean sorting = false;
    int sortedIndex[];
	
    int focusNumber;	
    
	public CircleGraph()
	{
		setModuleID(20);			// Set your module ID after you have got it
        dataNumbers = new int[]{0,0,0,0,    // b,i,d,S
                                0,0,1,0,    // bA,iA,dA,SA
                                0,0,0};     // bA2,iA2,dA2
		setToolType(2);		
	}
	
	public void initializePanel(){}
	
	public void initializeData()
	{
        dataNumber = 10;
        
		value = new double[dataNumber];
        arc = new double[dataNumber];
        totalArc = new double[dataNumber];
        focusNumber = 0;
		
        leftMargin = 20;
        rightMargin = 20;
        topMargin = 20;
        bottomMargin = 20;
        
        colorBase = 0;
        colorMax = 240;
        colorDiff = 64;
        
        sortedIndex = new int[dataNumber];
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
            case 0:	value = score;
				dataNumber = value.length;
				repaint();
                return true;
        }
        
		return false;
	}	
	
//	public void setFont(){}	
	
    public void calculateArc()
    {
        arc = new double[value.length];
        totalArc = new double[value.length];
        
        sortedIndex = new int[value.length];
        
        double total;
        
        total = 0;        
        for(int i=0;i<value.length;i++)
            total += value[i];
        
        
        sorting = true;
        
        if(sorting == false)
        {
            for(int i=0;i<value.length;i++)
                sortedIndex[i] = i;
        }
        else
        {
            double sortValue[] = new double[value.length];
            for(int i=0;i<value.length;i++)
                sortValue[i] = value[i];
            Qsort.initializeIndex(sortedIndex, value.length);
            Qsort.quicksort(sortValue, sortedIndex, value.length);
        }
        

        totalArc[sortedIndex[0]] = arc[sortedIndex[0]] = value[sortedIndex[0]]/total * 360;
        for(int i=1;i<value.length;i++)
        {
            arc[sortedIndex[i]] = value[sortedIndex[i]]/total * 360;
            totalArc[sortedIndex[i]] = totalArc[sortedIndex[i-1]] + arc[sortedIndex[i]];
        }
        
        for(int i=0;i<value.length;i++)		
			totalArc[sortedIndex[i]] = (int)totalArc[sortedIndex[i]];
			
		arc[sortedIndex[0]] = totalArc[sortedIndex[0]];
        for(int i=1;i<value.length;i++)
			arc[sortedIndex[i]] = totalArc[sortedIndex[i]] - totalArc[sortedIndex[i-1]];

        for(int i=0;i<value.length;i++)
            totalArc[sortedIndex[i]] = 90 - totalArc[sortedIndex[i]];
    }
    
	//background
	public void drawBackground(Graphics2D g2)
	{		
        g2.setColor(Color.white);
        g2.fillRect(0,0, sizeX, sizeY);
        
        width = sizeX - leftMargin - rightMargin;
        height = sizeY - topMargin - bottomMargin;
        
        if(height > width)
            radius = (int)(width/2);
        else
            radius = (int)(height/2);
        
        centerX = (int)(sizeX/2);
        centerY = (int)(sizeY/2);
        
        g2.setColor(Color.cyan);
		g2.fillOval(centerX-radius, centerY-radius, radius*2, radius*2);
        
        calculateArc();        
	}
	
    Color nextColor()
    {        
        if(b + colorDiff < colorMax)
            b += colorDiff;
        else
        {
            b = colorBase;
            if(g + colorDiff < colorMax)
                g += colorDiff;
            else
            {
                g = colorBase;
                if(r + colorDiff < colorMax)
                    r += colorDiff;
                else
                    r = colorBase;
            }
        }
        
//        System.out.println("r= "+r+" g= "+g+" b= "+b);
        
        return new Color(r,g,b);
    }
    
	public void drawDistribution(Graphics2D g2)
	{
		if(value.length == 0)
			return;

        r = g = b = colorBase;

        for(int i=0;i<value.length;i++)	
        {        
            g2.setColor(nextColor());
			g2.fillArc(centerX-radius, centerY-radius, radius*2, radius*2, (int)(totalArc[sortedIndex[i]]), (int)(arc[sortedIndex[i]]));
		}
		
		g2.setColor(Color.white);		
        for(int i=0;i<value.length;i++)
		{
            g2.drawString(""+value[sortedIndex[i]],
                          centerX+(int)((0.7 + i%3*0.1)*radius * Math.cos(-Math.toRadians(totalArc[sortedIndex[i]]+arc[sortedIndex[i]]/2))),
                          centerY+(int)((0.7 + i%3*0.1)*radius * Math.sin(-Math.toRadians(totalArc[sortedIndex[i]]+arc[sortedIndex[i]]/2))));						  
        }

		
        //Draw StartLine
        g2.setColor(Color.black);
        g2.drawLine(centerX,centerY,centerX+(int)(radius * Math.cos(-PI/2)),centerY+(int)(radius * Math.sin(-PI/2)));
	}
	
	//////////paint
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		getPanelSize();			
		
		drawBackground(g2);		//background
		drawDistribution(g2);
	}
	
	public void update(Graphics g)		//avoid from blinking
	{
		paintComponent(g);
	}	
}