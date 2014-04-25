
//
// Mining module for TETDM 
// TypingControl.java Version 0.30 
// This is one of the sample modules.
// You should read the README file.
//

package module.MiningModules.TypingControl;		

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TypingControl extends MiningModule implements ActionListener
{
    JButton button[];
    int buttonNumber;
//	JToggleButton tButton;
    String buttonNamesInJapanese[];    
    
	public TypingControl()
	{
		setModuleID(20001);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{20001};
		setToolType(3);
        
        defaultSetPanelModuleID = new int[]{20001,0};
        defaultSetPanelVisualizationID = new int[]{20001,17};
	}
	
	public void initializePanel()
    {
		buttonNamesInJapanese = fileReadArray();
		
		operationPanel.setBackground(Color.RED);
        
        buttonNumber = 3;
        
	    button = new JButton[buttonNumber];
        
        operationPanel.setLayout(new GridLayout(0,3));
        
	    for(int i=0;i<buttonNumber;i++)
		{
		    button[i] = new JButton();
		    button[i].setBackground(Color.RED);
		    button[i].addActionListener(this);
		    operationPanel.add(button[i]);
		}
		
//		tButton = new JToggleButton();
//		tButton.setBackground(Color.RED);
//		tButton.addActionListener(this);
//		operationPanel.add(tButton);
//		tButton.setSelected(true);
    }

	public void initializeData()
    {
		String buttonNamesInEnglish[] = {"Start","Skip","Abort"};
        
		if(isMenuInJapanese())
		{
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(buttonNamesInJapanese[i]);
//			tButton.setText(buttonNamesInJapanese[2]);
		}
		else
		{
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(buttonNamesInEnglish[i]);
//			tButton.setText("...");
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
            displayOperations(1);
        }
        
		if(e.getSource() == button[1])
        {
            displayOperations(2);
        }
        
		if(e.getSource() == button[2])
        {
            displayOperations(3);
        }

//        if(e.getSource() == tButton){}


	}
    

}
