//
// Mining module for TETDM 
// AnnotationMining.java Version 0.30
// Copyright(C) 2011 YOKO NISHIHARA All RIGHTS RESERVED.
// This program is provided under the license.
// You should read the README file.
//



package module.MiningModules.AnnotationMining;		// Replace ALL [MiningStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

public class AnnotationMining extends MiningModule
{
	int annotationNumber[];
	
	public AnnotationMining()
	{
		setModuleID(9);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{1,2};
		setToolType(3);
	}

//	public void initializePanel(){}
	
//	public void initializeData(){}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
				annotationNumber = new int[text.sentenceNumber];
				resetData();
				setDataString(MyMethod());
				setDataIntegerArray(annotationNumber);				

				break;
		}
	}
	
	String MyMethod()
	{
		String dictionary[] = new String[7];
		dictionary = fileReadArray();

		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);		
		
		try{
			for(int i=0;i<text.sentenceNumber;i++)
			{
				int iflag = 0;
				for(int j=0; j != text.sentence[i].wordNumber; j++)
				{
					int jflag = 0;
					for(int k=0; k<2; k++){
						if(text.sentence[i].word[j].equals(dictionary[k])){
							bw.write(text.sentence[i].word[j]+"**");
							jflag = 1;
						}
					}
					if(jflag == 0){
						for(int k=2; k<5; k++){
							if(text.sentence[i].word[j].equals(dictionary[k])){
								bw.write(text.sentence[i].word[j]+"****");
								jflag = 2;
							}
						}   
					}
					if(jflag == 0){
						for(int k=5; k<7; k++){
							if(text.sentence[i].word[j].equals(dictionary[k])){
								bw.write(text.sentence[i].word[j]+"******");
								jflag = 3;
							}
						}   
					}
					if(jflag == 0){
						bw.write(text.sentence[i].word[j]+"");
					}
					if(iflag < jflag)
					{
						iflag = jflag;
					}
				}
				if(iflag == 1)
					annotationNumber[i] = 12;
				else if(iflag == 2)
					annotationNumber[i] = 13;
				else if(iflag == 3)
					annotationNumber[i] = 14;
				else 
					annotationNumber[i] = 10;
			}
			
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in AnnotationMining");
		}			
		
		return sw.toString();
		
	}	
	
//	public void actionPerformed(ActionEvent e){}

}
