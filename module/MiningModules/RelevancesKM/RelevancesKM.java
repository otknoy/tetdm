////////////////////////////////
// Mining module for TETDM //
// RelevancesKM Version 0.10 2012.1.2 //
// Copyright(C) 2011.12.9 Tomoki Kajinami //
//
// This module is a sample module for a Simple Keyword Map,//
// makes a data set including tree attributes of relationship between extracted keywords from a text.//
// Format of data set for Keyword Map is as follows.//
/// ,,attribute's name1,attribute's name2,attribute's name3 ///
/// keyword1,keyword2,(double)relevance1,(double)relevance2,(double)relevance3 ///
////////////////////////////////


package module.MiningModules.RelevancesKM;

import source.*;
import source.TextData.*;

import java.io.*;

public final class RelevancesKM extends MiningModule{
	public RelevancesKM(){
		setModuleID(10);
		pairingVisualizationID = new int[2];
		pairingVisualizationID[0] = 1; //TextDisplay
		pairingVisualizationID[1] = 7; //SimpleKeywordMap
	}

	public void initializeData(){}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
				String text = MyMethod();
				setDataString(0,text);
				setDataString(99,text);
				break;
		}
	}	

	private String MyMethod(){
		String newLine = System.getProperty("line.separator");
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			bw.write(",,cos,random,times"+newLine); //Attributes'names
			for(int i=0;i<text.keywordRelationBySegment.number-1;i++){
				for(int j=i+1;j<text.keywordRelationBySegment.number;j++){
					double r1cos=text.keywordRelationBySegment.cos[i][j];
					double ran=Math.random();
					double r2ran=ran<0.8?0:ran;
					int abstimes=Math.abs(text.keyword[i].frequency-text.keyword[j].frequency)+1;
					double r3tim=1/abstimes;
					bw.write(text.keywordRelationBySegment.name[i]+","+text.keywordRelationBySegment.name[j]+","+r1cos+","+r2ran+","+r3tim+newLine);
				}
			}
			bw.write("dummyi,dummyj,1,1,2"); //Dummy keywords are not displayed on the SimpleKeywordMap)
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in RelevancesKM");
		}		
			
		return sw.toString();
	}
}
