//
// Core Program for TETDM 
// connection.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import source.TextData.*;

import java.io.*;
import java.util.*;

public class Connection
{
	public class ExecutedData
	{
		int textID;
		int moduleID;
		int dataID;
        
        boolean dataExist[] = new boolean[11];

		boolean dataBoolean;//0
		int dataInteger;//1
		double dataDouble;//2
		String dataString;//3
		
		boolean dataBooleanArray[];//4
		int dataIntegerArray[];//5
		double dataDoubleArray[];//6
		String dataStringArray[];  //7
        
		boolean dataBooleanArray2[][];//8
		int dataIntegerArray2[][];//9
		double dataDoubleArray2[][];//10


		boolean executing = false;
		
		ExecutedData(){}
		
		ExecutedData(int tID, int mID, int dID)
		{
			textID = tID;
			moduleID = mID;
			dataID = dID;
            
            for(int i=0;i<11;i++)
                dataExist[i] = false;
		}
	}
	
	ArrayList<ExecutedData> dataList;
	
	boolean executeFlags[];
	int basePanelID;

	TextData text;	//For acquiring control ONLY
    
    String dataType[] = {"boolean","int","double","String",
    "boolean[]","int[]","double[]","String[]",
    "boolean[][]","int[][]","double[][]"};
	
	public Connection(TextData tex)
	{
		text = tex;
		dataList = new ArrayList<ExecutedData>();
		initializeFlags();		
		System.out.println("DATABASE for "+text.sourceFilename+" was constructed "+dataList.size());
	}
	
	int checkData(int textID, int moduleID, int dataID)
	{
		ExecutedData ex;
		for(int i=0;i<dataList.size();i++)
		{
			ex = dataList.get(i);
			if(ex.textID == textID && ex.moduleID == moduleID && ex.dataID == dataID)
				return i;
		}
		
		return -1;
	}
    
	int checkData(int textID, int moduleID, int dataID, int type)
	{
		ExecutedData ex;
		for(int i=0;i<dataList.size();i++)
		{
			ex = dataList.get(i);
			if(ex.textID == textID && ex.moduleID == moduleID && ex.dataID == dataID && ex.dataExist[type])
				return i;
		}
		
		return -1;
	}
	
	//////DELETE DATA
	
	public boolean deleteData(int textID, int moduleID, int dataID, int type)
	{
		ExecutedData ex;        
		int num = checkData(textID,moduleID,dataID,type);
		
		if(num == -1)
		{
			System.out.println("NOT DELETE Data by text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]+" New DATABASE size= "+dataList.size());
			return false;
		}
		
//		dataList.remove(num);
        ex = dataList.get(num);
        ex.dataExist[type] = false;
		
		System.out.println("DELETE Data by text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]+" New DATABASE size= "+dataList.size());

		return true;
	}	
	
	//////SET DATA
	
	int setDataCommon(int textID, int moduleID, int dataID, int type)
	{
		ExecutedData ex;        
		int num = checkData(textID,moduleID,dataID);
		
		if(num == -1)
		{
            ex = new ExecutedData(textID, moduleID, dataID);
            ex.dataExist[type] = true;
			dataList.add(ex);
			num = dataList.size()-1;
		}
        else
        {
            ex = dataList.get(num);
            ex.dataExist[type] = true;
        }
		
		System.out.println("DATA Created by text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]+" New DATABASE size= "+dataList.size());
		
		return num;
	}
	
	void setData(int textID, int moduleID, int dataID, boolean data)
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 0)).dataBoolean = data;
	}	
	void setData(int textID, int moduleID, int dataID, int data)
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 1)).dataInteger = data;
	}
	void setData(int textID, int moduleID, int dataID, double data)
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 2)).dataDouble = data;
	}
	void setData(int textID, int moduleID, int dataID, String data)
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 3)).dataString = data;
	}
	void setData(int textID, int moduleID, int dataID, boolean data[])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 4)).dataBooleanArray = data;
	}	
	void setData(int textID, int moduleID, int dataID, int data[])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 5)).dataIntegerArray = data;
	}
	void setData(int textID, int moduleID, int dataID, double data[])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 6)).dataDoubleArray = data;
	}
	void setData(int textID, int moduleID, int dataID, String data[])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 7)).dataStringArray = data;
	}
	void setData(int textID, int moduleID, int dataID, boolean data[][])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 8)).dataBooleanArray2 = data;
	}	
	void setData(int textID, int moduleID, int dataID, int data[][])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 9)).dataIntegerArray2 = data;
	}
	void setData(int textID, int moduleID, int dataID, double data[][])
	{
		dataList.get(setDataCommon(textID, moduleID, dataID, 10)).dataDoubleArray2 = data;
		
		System.out.println("SET DATA "+data.length);
	}	
	

	//////GET DATA
	
    int getModuleIndex(int moduleID)
    {
        return text.control.moduleData.findModule(moduleID);
    }
    
	int executeModule(int moduleID)
	{
		return text.control.stealthPanel.change_panel_moduleID(moduleID);
	}
	
	void executeModule(int optionNumber, int moduleIndex)
	{
		text.control.stealthPanel.executeModule(optionNumber,moduleIndex);
	}	
	
	void setTextDataToStealth(int textID)
	{
		/*
		switch(textID)
		{
			case 0:text.control.stealthPanel.text = text;
				break;
			case 1:text.control.stealthPanel.text = text.segmentPartialTextData;
				break;
			case 2:text.control.stealthPanel.text = text.sentencePartialTextData;
				break;
		}
		 */
		
		if(textID == 0)
		{
			text.control.stealthPanel.text = text;
			return;
		}
		
		if(textID%2 == 1)//1,3,5 -> 0,1,2
		{
			text.control.stealthPanel.text = text.segmentPartialTextData[(int)(textID/2)];
			return;
		}
		
		if(textID%2 == 0)//2,4,6 -> 0,1,2
		{
			text.control.stealthPanel.text = text.sentencePartialTextData[(int)(textID/2) - 1];
			return;
		}		
	}
	

    /*
	int getDataCommon(int textID, int moduleID, int dataID, int type)
	{
        int num, moduleIndex;        
		System.out.println("DATA Called from text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]);
		
		num = checkData(textID,moduleID,dataID,type);   //SEARCH DATABASE
		moduleIndex = -1;
		
        //SET text data for the stealth panel
		setTextDataToStealth(textID);
		
		if(num == -1)
		{
			moduleIndex = executeModule(moduleID);      //SET MODULE, EXECUTE miningOperations(0) automatically;
			num = checkData(textID,moduleID,dataID,type);
		}
		
		if(num == -1 && moduleIndex != -1)
		{
			System.out.println("EXECUTE text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]);
			executeModule(dataID,moduleIndex);			//////////////////  //EXECUTE miningOperations(optionNumber);
			num = checkData(textID,moduleID,dataID,type);
		}		
		
        //RESET text data for the stealth panel
		setTextDataToStealth(0);
		
		return num;
	}
	*/
	public boolean getDataBoolean(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 0, dataID);
		if(num == -1)
			return false;	//CAUTION /////// false also means not found
		return dataList.get(num).dataBoolean;
	}	
	public int getDataInteger(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 1, dataID);
		if(num == -1)
			return -1;
		return dataList.get(num).dataInteger;
	}
	public double getDataDouble(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 2, dataID);
		if(num == -1)
			return -1.0;
		return dataList.get(num).dataDouble;
	}
	public String getDataString(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 3, dataID);
		if(num == -1)
			return "NOT EXECUTED";
		return dataList.get(num).dataString;
	}
	public boolean[] getDataBooleanArray(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 4, dataID);
		if(num == -1)
			return null;
		return dataList.get(num).dataBooleanArray;
	}	
	public int[] getDataIntegerArray(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 5, dataID);
		if(num == -1)
			return null;
		return dataList.get(num).dataIntegerArray;
	}
	public double[] getDataDoubleArray(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 6, dataID);
		if(num == -1)
			return null;
		return dataList.get(num).dataDoubleArray;
	}
	public String[] getDataStringArray(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 7, dataID);
		if(num == -1)
			return null;
		return dataList.get(num).dataStringArray;
	}
	public boolean[][] getDataBooleanArray2(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 8, dataID);
		if(num == -1)
			return null;
		return dataList.get(num).dataBooleanArray2;
	}	
	public int[][] getDataIntegerArray2(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 9, dataID);
		if(num == -1)
			return null;
		return dataList.get(num).dataIntegerArray2;
	}
	public double[][] getDataDoubleArray2(int textID, int moduleID, int dataID)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 10, dataID);		
		if(num == -1)
			return null;
		return dataList.get(num).dataDoubleArray2;
	}	

    //WITH OPTION
	int getDataCommonWithOption(int textID, int moduleID, int dataID, int type, int optionNumber)
	{
        int num, moduleIndex;
		System.out.println("DATA Called from text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]+" Option= "+optionNumber);
		
		num = checkData(textID,moduleID,dataID,type);   //SEARCH DATABASE
		moduleIndex = -1;
		
        //SET text data for the stealth panel
		setTextDataToStealth(textID);
		
		if(num == -1)
		{
			moduleIndex = executeModule(moduleID);      //SET MODULE, EXECUTE miningOperations(0) automatically;
			if(moduleIndex != -1)
			{
				System.out.println("EXECUTE text ID= "+textID+","+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") dataID= "+dataID+" Type= "+dataType[type]+" Option= "+optionNumber);
				executeModule(optionNumber,moduleIndex);			//////////////////  //EXECUTE miningOperations(optionNumber);
				num = checkData(textID,moduleID,dataID,type);
			}
		}
		
        //RESET text data for the stealth panel
		setTextDataToStealth(0);
		
		return num;
	}
	
	public boolean getDataBoolean(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 0, optionNumber);
		if(num == -1)
			return false;	//CAUTION /////// false also means not found
		return dataList.get(num).dataBoolean;
	}
	public int getDataInteger(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 1, optionNumber);
		if(num == -1)
			return -1;
		return dataList.get(num).dataInteger;
	}
	public double getDataDouble(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 2, optionNumber);
		if(num == -1)
			return -1.0;
		return dataList.get(num).dataDouble;
	}
	public String getDataString(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 3, optionNumber);
		if(num == -1)
			return "NOT EXECUTED";
		return dataList.get(num).dataString;
	}
	public boolean[] getDataBooleanArray(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 4, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataBooleanArray;
	}
	public int[] getDataIntegerArray(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 5, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataIntegerArray;
	}
	public double[] getDataDoubleArray(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 6, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataDoubleArray;
	}
	public String[] getDataStringArray(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 7, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataStringArray;
	}
	public boolean[][] getDataBooleanArray2(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 8, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataBooleanArray2;
	}
	public int[][] getDataIntegerArray2(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 9, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataIntegerArray2;
	}
	public double[][] getDataDoubleArray2(int textID, int moduleID, int dataID, int optionNumber)
	{
		int num = getDataCommonWithOption(textID, moduleID, dataID, 10, optionNumber);
		if(num == -1)
			return null;
		return dataList.get(num).dataDoubleArray2;
	}
    
    

	//EXECUTION	// miningModule -> miningModule
	//EXECUTION	// visualizationModule -> miningModule	
	public void executeModuleWithOptionFirst(int myPanelID, int moduleID, int optionNumber)
	{
		System.out.println("EXECUTE MODULE: PanelID="+myPanelID+" -> "+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
		{
			initializeFlags();			
			basePanelID = myPanelID;
			executeFlags[myPanelID] = true;
		}
		
		int mp = findModule(moduleID);
		
		if(mp != -1)
		{
			executeModuleWithOption(optionNumber,mp);	// Execute Module
			executeFlags[mp] = true;
		}
	}	
	
	
	public void executeModuleWithOption(int myPanelID, int moduleID, int optionNumber)
	{
		System.out.println("EXECUTE MODULE: PanelID="+myPanelID+" -> "+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
		{
			initializeFlags();			
			basePanelID = myPanelID;
			executeFlags[myPanelID] = true;
		}
		
		int mp = findModule(moduleID);
		
		if(mp != -1)
		{
			executeModuleWithOption(optionNumber,mp);	// Execute Module
			executeFlags[mp] = true;
		}
		
		if(myPanelID == basePanelID)
			initializeFlags();
	}
	
	public void executeModuleWithOptionFirstFromXXX(int moduleID, int optionNumber)
	{
		System.out.println("EXECUTE MODULE: XXX -> "+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
			initializeFlags();			
		
		int mp = findModule(moduleID);
		
		if(mp != -1)
		{
			executeModuleWithOption(optionNumber,mp);	// Execute Module
			executeFlags[mp] = true;
		}
	}	
	
	
	public void executeModuleWithOptionFromXXX(int moduleID, int optionNumber)
	{
		System.out.println("EXECUTE MODULE: XXX -> "+text.control.getModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
			initializeFlags();			

		int mp = findModule(moduleID);
		
		if(mp != -1)
		{
			executeModuleWithOption(optionNumber,mp);	// Execute Module
			executeFlags[mp] = true;
		}

		initializeFlags();
	}	
	
	void executeModuleWithOption(int optionNumber, int mp)
	{
		text.control.panel[mp].executeModule(optionNumber);
	}	
	
	
	//Basic Methods
	
	void initializeFlags()
	{
		executeFlags = new boolean[text.control.panelNumber];		
		for(int i=0;i<executeFlags.length;i++)
			executeFlags[i] = false;
		basePanelID = -1;
	}
	
	boolean isFlagsClear(boolean flags[])
	{
		for(int i=0;i<flags.length;i++)
			if(flags[i])
				return false;

		return true;
	}
	
    //GET PANEL NUMBER for module
	int findModule(int moduleID)
	{
		return text.control.findModule(moduleID, executeFlags);
	}
	//GET PANEL NUMBER for module
	int findVisualizationModule(int moduleID)
	{
		return text.control.findVisualizationModule(moduleID, executeFlags);
	}	
	


	//DISPLAY // miningModule -> visualizationModule
	//DISPLAY // visualizationModule -> visualizationModule
	public void displayModuleWithOptionFirst(int myPanelID, int moduleID, int optionNumber)
	{
		System.out.println("DISPLAY MODULE: PanelID="+myPanelID+" -> "+text.control.getVisualizationModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
		{
			initializeFlags();		
			basePanelID = myPanelID;
			executeFlags[myPanelID] = true;
		}
		
		int mp = findVisualizationModule(moduleID);
		
		if(mp != -1)
		{
			displayModuleWithOption(optionNumber,mp);	// Display Module
			executeFlags[mp] = true;
		}
	}	
	
	public void displayModuleWithOption(int myPanelID, int moduleID, int optionNumber)
	{
		System.out.println("DISPLAY MODULE: PanelID="+myPanelID+" -> "+text.control.getVisualizationModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
		{
			initializeFlags();		
			basePanelID = myPanelID;
			executeFlags[myPanelID] = true;
		}
		
		int mp = findVisualizationModule(moduleID);
		
		if(mp != -1)
		{
			displayModuleWithOption(optionNumber,mp);	// Display Module
			executeFlags[mp] = true;
		}
		
		if(myPanelID == basePanelID)
			initializeFlags();
	}

	public void displayModuleWithOptionFirstFromXXX(int moduleID, int optionNumber)
	{
		System.out.println("DISPLAY MODULE: XXX -> "+text.control.getVisualizationModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
			initializeFlags();		
		
		int mp = findVisualizationModule(moduleID);
		
		if(mp != -1)
		{
			displayModuleWithOption(optionNumber,mp);	// Display Module
			executeFlags[mp] = true;
		}
	}	
	
	public void displayModuleWithOptionFromXXX(int moduleID, int optionNumber)
	{
		System.out.println("DISPLAY MODULE: XXX -> "+text.control.getVisualizationModuleName(moduleID)+"(ID:" +moduleID+") option= "+optionNumber);
		
		if(isFlagsClear(executeFlags))
			initializeFlags();		
		
		int mp = findVisualizationModule(moduleID);
		
		if(mp != -1)
		{
			displayModuleWithOption(optionNumber,mp);	// Display Module
			executeFlags[mp] = true;
		}

		initializeFlags();
	}	
	
	void displayModuleWithOption(int optionNumber, int mp)
	{
		text.control.panel[mp].displayModule(optionNumber);
	}	
	
}
