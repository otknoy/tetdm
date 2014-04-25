//
// Mining module for TETDM 
// Sample1.java Version 0.30
// This is one of the sample modules.
// You should read the README file.
//

package module.MiningModules.Sample1;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

public class Sample1 extends MiningModule
{
	public Sample1()
	{
		setModuleID(10000);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{1};
		setToolType(2);
	}

//	public void initializePanel(){}
	
//	public void initializeData(){}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
                resetData();
				setDataString(MyMethod());
				break;
		}
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
			System.out.println("writing ERROR in Sample1");
		}			
		
		return sw.toString();	
	}	
	
//	public void actionPerformed(ActionEvent e){}

}
