//
// Mining module for TETDM 
// TopDownOrder.java Version 0.30
// Copyright(C) 2013 System Interface Laboratory (Hiroshima City University) ALL RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//


package module.MiningModules.TopDownOrder;

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class TopDownOrder extends MiningModule
{
    int num[];
    int overCount[];
    double average;
    
	public TopDownOrder()
	{
		setModuleID(24);
		pairingVisualizationID = new int[]{18};
        setToolType(2);
	}
	
	public void initializePanel(){}	
	public void initializeData(){}
  
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0: calculateTopDownOrder();
                resetData();
                setDataIntegerArray(num);
				break;
		}
	}
    
    void calculateTopDownOrder()
    {
        num = new int[text.segmentNumber];
        overCount = new int[text.segmentNumber];
        
        average = 0.0;
        for(int i=0;i<text.segmentNumber;i++)
            for(int j=0;j<text.segmentNumber;j++)
                if(i != j)
                    average += text.segmentRelation.cond[i][j];
        average /= (text.segmentNumber * (text.segmentNumber - 1));
        
        for(int i=0;i<text.segmentNumber;i++)
        {
            overCount[i] = 0;
            for(int j=0;j<text.segmentNumber;j++)
                if(i != j)
                    if(text.segmentRelation.cond[i][j] > average)
                        overCount[i]++;
        }

        Qsort.initializeIndex(num,text.segmentNumber);
        Qsort.quicksort(overCount,num,text.segmentNumber);
    }
}
