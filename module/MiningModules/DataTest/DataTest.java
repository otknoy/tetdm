
//
// Mining module for TETDM 
// dataTest.java Version 0.30 
// This is one of the sample modules.
// You should read the README file.
//

package module.MiningModules.DataTest;		

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DataTest extends MiningModule implements ActionListener
{
    JButton button[];
    int buttonNumber;
	JToggleButton tButton;
    String buttonNamesInJapanese[];    
    
	public DataTest()
	{
		setModuleID(7777);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{1,4};
		setToolType(3);
	}
	
	public void initializePanel()
    {
		buttonNamesInJapanese = fileReadArray();
		
		operationPanel.setBackground(Color.RED);
        
        buttonNumber = 11;
        
	    button = new JButton[buttonNumber];
        
        operationPanel.setLayout(new GridLayout(3,4));
        
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
		tButton.setSelected(true);
    }

	public void initializeData()
    {
		String buttonNamesInEnglish[] = {"boolean","int","double","String",
                                        "boolean[]","int[]","double[]","String[]",
                                        "boolean[][]","int[][]","double[][]"};
        
		if(isMenuInJapanese())
		{
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(buttonNamesInJapanese[i]);
			tButton.setText(buttonNamesInJapanese[11]);
		}
		else
		{
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(buttonNamesInEnglish[i]);
			tButton.setText("Reset Data Number");			
		}
    }
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:			
				break;
		}
	}	

	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == button[0])
        {
            boolean data = true;
            
			if(tButton.isSelected())
				resetData();

            setDataBoolean(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[1])
        {
            int data = text.sentenceNumber;
            
			if(tButton.isSelected())			
				resetData();
			
            setDataInteger(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[2])
        {
            double data = 12.3;
            
			if(tButton.isSelected())
				resetData();

            setDataDouble(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[3])
        {
            String data = text.originalText;
            
			if(tButton.isSelected())			
				resetData();
				
			setDataString(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[4])
        {
            boolean data[] = new boolean[text.sentenceNumber];
            for(int i=0;i<text.sentenceNumber;i++)
                if(text.sentence[i].keywordNumber >= 7)
                    data[i] = true;
                else
                    data[i] = false;
            
			if(tButton.isSelected())			
				resetData();

            setDataBooleanArray(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[5])
        {
            int data[] = new int[text.sentenceNumber];
            for(int i=0;i<text.sentenceNumber;i++)
                data[i] = text.sentence[i].wordNumber;
            
			if(tButton.isSelected())			
				resetData();

            setDataIntegerArray(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[6])
        {
            double data[] = new double[text.sentenceNumber];
            for(int i=0;i<text.sentenceNumber;i++)
                data[i] = text.sentence[i].wordNumber + text.sentence[i].keywordNumber * 0.1;
            
			if(tButton.isSelected())			
				resetData();

            setDataDoubleArray(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[7])
        {
            String data[] = new String[text.sentenceNumber];
            for(int i=0;i<text.sentenceNumber;i++)
                data[i] = text.sentence[i].sentenceText;
            
			if(tButton.isSelected())			
				resetData();

            setDataStringArray(data);
            displayOperations(0);            
        }
        
		if(e.getSource() == button[8])
        {
            boolean data[][] = new boolean[text.sentenceNumber][];
            for(int i=0;i<text.sentenceNumber;i++)
            {
                data[i] = new boolean[text.sentence[i].wordNumber];
                for(int j=0;j<text.sentence[i].wordNumber;j++)
                    if(text.sentence[i].wordIDList[j] >= 0)
                        data[i][j] = true;
                    else
                        data[i][j] = false;
            }
            
			if(tButton.isSelected())			
				resetData();

            setDataBooleanArray2(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[9])
        {
            int data[][] = new int[text.sentenceNumber][];
            for(int i=0;i<text.sentenceNumber;i++)
            {
                data[i] = new int[text.sentence[i].keywordNumber];
                for(int j=0;j<text.sentence[i].keywordNumber;j++)
                    data[i][j] = text.keyword[text.sentence[i].keywordIDList[j]].frequency;
            }
            
			if(tButton.isSelected())			
				resetData();

            setDataIntegerArray2(data);
            displayOperations(0);
        }
        
		if(e.getSource() == button[10])
        {
            double data[][] = new double[text.sentenceNumber][];
            for(int i=0;i<text.sentenceNumber;i++)
            {
                data[i] = new double[text.sentence[i].wordNumber];
                for(int j=0;j<text.sentence[i].wordNumber;j++)
                    data[i][j] = (double)text.sentence[i].wordIDList[j];
            }
            
			if(tButton.isSelected())			
				resetData();

            setDataDoubleArray2(data);
            displayOperations(0);
        }

	}
    

}
