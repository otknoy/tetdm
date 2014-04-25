//
// Mining module for TETDM 
// Sample2.java Version 0.30
// This is one of the sample modules.
// You should read the README file.
//

package module.MiningModules.Sample2;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class Sample2 extends MiningModule implements ActionListener
{
	JButton button1;
	String buttonNameInJapanese;
	
	public Sample2()
	{
		setModuleID(10001);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{1};
		setToolType(2);
	}

	public void initializePanel()
	{
		button1 = new JButton();
		button1.addActionListener(this);
		operationPanel.add(button1);

		buttonNameInJapanese = fileRead();		
	}
	
	public void initializeData()
	{
		if(isMenuInJapanese())
			button1.setText(buttonNameInJapanese);
		else
			button1.setText("DISPLAY");		
	}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
                resetData();                
				setDataString("");
				break;
				
			case 1:
                resetData();
				setDataString(MyMethod());
				displayOperations(0);
				break;
		}
	}	
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == button1)
			miningOperations(1);
	}
	
	String MyMethod()
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<text.sentenceNumber;i++)
				for(int j=0;j<text.sentence[i].wordNumber;j++)
					bw.write(text.sentence[i].word[j]+" ");
			
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in Sample2");
		}			
		
		return sw.toString();	
	}
}
