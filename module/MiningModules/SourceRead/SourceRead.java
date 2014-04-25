//
// Mining module for TETDM 
// SourceRead Version 0.37
// Copyright(C) 2012 Wataru Sunayama

// You should read the README file.
//

package module.MiningModules.SourceRead;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

public class SourceRead extends MiningModule implements ActionListener
{
	String modulePath;	
	String moduleName;

	String source[];
	String basicFunctions[];
	String dataSupply;

	
	String dataSet;
	String dataRequest[];
    
	String focusWords[];
	String focusVariables[];	
	String focusFunctions[];
	String focusFunctions2[];
    
	String optionRequests[];
	String optionRequests2[];

	String warnings;
	String readme;
	
	boolean mining;
	
    JToggleButton button[];
	int buttonNumber;
	ButtonGroup buttonGroup;
	
	JComboBox miningSelect;
	JComboBox visualizationSelect;
	int visualizationToIndex[];
	int indexToVisualization[];	
	int selectedMining;
	int selectedVisualization;
	
	String moduleNames[];
	String moduleNamesInJapanese[];
	String visuModuleNames[];
	String visuModuleNamesInJapanese[];
	
	int moduleNumber;
	int visuModuleNumber;
	
	boolean once;
	
    String wordsInJapanese[];
	

	//////////////////////
	void initializeMiningComboBox()
	{
		String names[] = new String[moduleNumber];
		
		if(text.control.sp1.inJapanese)
			for(int i=0;i<moduleNumber;i++)
				names[i] = moduleNamesInJapanese[i];
		else
			for(int i=0;i<moduleNumber;i++)			
				names[i] = moduleNames[i];
		
		miningSelect = new JComboBox(names);
		selectedMining = text.control.sp1.moduleData.findModule(getModuleID());
		miningSelect.setSelectedIndex(selectedMining);
		miningSelect.addActionListener(this);
		miningSelect.setMaximumRowCount(50);
		operationPanel.add(miningSelect);		
	}
	
	void initializeVisualizationComboBox()
	{
		String names[] = new String[visuModuleNumber];
		
		if(text.control.sp1.inJapanese)	
			for(int i=0;i<visuModuleNumber;i++)
				names[i] = visuModuleNamesInJapanese[i];
		else
			for(int i=0;i<visuModuleNumber;i++)			
				names[i] = visuModuleNames[i];
		
		visualizationSelect = new JComboBox(names);
		selectedVisualization = -1;		
		visualizationSelect.setSelectedIndex(selectedVisualization);
		visualizationSelect.addActionListener(this);
		visualizationSelect.setMaximumRowCount(50);		
		operationPanel.add(visualizationSelect);
	}		
	
	public SourceRead()
	{
		setModuleID(99999);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{1};
		setToolType(2);
	}

	public void initializePanel()
	{
		operationPanel.setLayout(new GridLayout(2,5));		
		
		String buttonNames[] = {"Source","Constructor","Global","Basic","Check","Readme"};

		buttonNumber = 6;
		button = new JToggleButton[buttonNumber];		
		wordsInJapanese = fileReadArray();				

		operationPanel.setBackground(Color.RED);

		buttonGroup = new ButtonGroup();
		for(int i=0;i<buttonNumber;i++)
		{
			button[i] = new JToggleButton(buttonNames[i]);
			button[i].setBackground(Color.RED);
			button[i].addActionListener(this);
			operationPanel.add(button[i]);
			
			buttonGroup.add(button[i]);
		}
		button[5].setSelected(true);
		
		if(isMenuInJapanese())
			for(int i=0;i<buttonNumber;i++)
				button[i].setText(wordsInJapanese[17+i]);
		
		moduleNames = text.control.sp1.moduleData.module_names;
		moduleNamesInJapanese = text.control.sp1.moduleData.miningModuleNamesInJapanese;
		moduleNumber = moduleNames.length;
		
		visuModuleNames = text.control.sp1.moduleData.visu_module_names;
		visuModuleNamesInJapanese = text.control.sp1.moduleData.visualizationModuleNamesInJapanese;
		visuModuleNumber = visuModuleNames.length;
		
		initializeMiningComboBox();
		initializeVisualizationComboBox();
	}

    public void initializeData()
    {
		mining = true;
		once = false;
		
		moduleName = myModuleName;
		modulePath = myModulePath;
		source = fileReadArray(myModulePath+myModuleName+".java");
		
		for(int i=0;i<source.length;i++)
			source[i] = source[i].replaceAll("	","    ");
		
		basicFunctions = new String[]{"initializePanel(","initializeData(","miningOperations(","displayOperations(","additionalPanelSet("};
		dataSupply = "setData";

		focusWords = new String[]{"focusKeywords","focusSegments","focusSentences",
            "mainFocusKeyword","subFocusKeyword","mainFocusSegment","subFocusSegment","mainFocusSentence","subFocusSentence",
            "mainFocusString","subFocusString","mainFocusDouble","subFocusDouble","mainFocusInteger","subFocusInteger"};
		focusVariables = new String[]{"focusTouchExecute","focusClickExecute"};
		focusFunctions = new String[]{"repaintOthersByTouch(","repaintOthersByClick("};
		focusFunctions2 = new String[]{"executeAllByTouch","executeAllByClick","executeOthersByTouch","executeOthersByClick"};
        
        optionRequests = new String[]{"displayOtherModule(","displayOtherModuleFirst(","displayModule(","displayModuleFirst(",};
        optionRequests2 = new String[]{"executeOtherModule(","executeOtherModuleFirst(","executeModule(","executeModuleFirst("};
        
		dataRequest = new String[]{"getData","getNewData"};
        
		
		//global variables		//should not have right value if indexOf("=") > 0 -> OUT
		System.out.println("MODULE PATH"+modulePath);
		System.out.println("MODULE Name"+moduleName);		
		//
    }
	
    public void miningOperations(int optionNumber)
	{
	    switch(optionNumber)
		{
            case 0:miningOperations(5);
                break;
                
			case 6://source
                resetData();
				setDataString(sourceText());
				break;

			case 1://constructor
                resetData();
				setDataString(extractConstructor(moduleName));
				break;

			case 2://global variables
                resetData();
				setDataString(extractGlobalAreas());
				break;

			case 3://basic functions
                resetData();
				setDataString(extractFunctions(basicFunctions));
				break;

			case 4://check
                resetData();
				setDataString(checkWarnings());
				break;

			case 5://save readme.txt
                String readmeText="";
                if(mining)
                {
                    readmeText += createMiningModuleNameForReadme();
                    if(selectedMining != -1)
                        readmeText += createReadmeForMiningPair();
                    readmeText += createReadmeForCommonContents();
                    readmeText += createReadmeForMiningModule();                    
                }
                else
                {
                    readmeText += createVisualizationModuleNameForReadme();
                    readmeText += createReadmeForCommonContents();
                    readmeText += createReadmeForVisualizationModule();
                }
                resetData();
                setDataString(readmeText);
				break;		
		}
	}
	
	String checkWarnings()
	{
		warnings = "";
		
		extractGlobalAreas();
		
		if(warnings.equals(""))
			warnings = wordsInJapanese[8];
		
		return warnings;
	}
	
    String createMiningModuleNameForReadme()
    {
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
            ModuleData moduleData = text.control.sp1.moduleData;
            
            if(selectedMining == -1)
                return "NOT FOUND";
			
			bw.write(wordsInJapanese[29]+wordsInJapanese[32]);
			bw.newLine();            
            bw.write(moduleNamesInJapanese[selectedMining]);
            bw.write("("+moduleNames[selectedMining]);
            bw.write("(ID="+moduleData.moduleIDs[selectedMining]+"))");
            bw.newLine();
            
			bw.newLine();
            
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}
		
		return sw.toString();
    }
    
    String createVisualizationModuleNameForReadme()
    {
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
            ModuleData moduleData = text.control.sp1.moduleData;
            
            if(selectedVisualization == -1)
                return "NOT FOUND";
			
			bw.write(wordsInJapanese[29]+wordsInJapanese[32]);			
			bw.newLine();            
            bw.write(visuModuleNamesInJapanese[selectedVisualization]);
            bw.write("("+visuModuleNames[selectedVisualization]);
            bw.write("(ID="+moduleData.visuModuleIDs[selectedVisualization]+"))");
            bw.newLine();
            
			bw.newLine();
            
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}
		
		return sw.toString();
    }
    
    String createReadmeForMiningPair()
    {
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
            bw.write(wordsInJapanese[23]);
            bw.newLine();
            
            ModuleData moduleData = text.control.sp1.moduleData;
            
            for(int i=0;i<moduleData.mining[selectedMining].pairingVisuID.length;i++)
            {
                int visualizationID = moduleData.mining[selectedMining].pairingVisuID[i];
                if(visualizationID == -1)
                    return "NOT FOUND";
                int visualizationIndex = moduleData.findVisualizationModule(visualizationID);
                bw.write(visuModuleNamesInJapanese[visualizationIndex]);
                bw.write("("+visuModuleNames[visualizationIndex]);
                bw.write("(ID="+visualizationID+"))");
                bw.newLine();
            }

			bw.newLine();
			bw.newLine();            
            
            bw.write(wordsInJapanese[30]);
            for(int i=0;i<moduleData.mining[selectedMining].pairingVisuID.length;i++)
            {
                int visualizationID = moduleData.mining[selectedMining].pairingVisuID[i];
                if(visualizationID == -1)
                    return "NOT FOUND";
                int visualizationIndex = moduleData.findVisualizationModule(visualizationID);
                bw.write(visuModuleNamesInJapanese[visualizationIndex]);
                bw.write("("+visuModuleNames[visualizationIndex]);
                bw.write("(ID="+visualizationID+"))");
            }                
            bw.write(wordsInJapanese[31]);
            
			bw.newLine();
			bw.newLine();            
            
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead at Pair");
		}
		
		return sw.toString();
    }
    
    String createReadmeForCommonContents()
    {
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			bw.newLine();
            bw.write(wordsInJapanese[24]);
            bw.newLine();            
            bw.write(wordsInJapanese[29]);            
            bw.newLine();
			bw.newLine();
			bw.newLine();             
            
            //Author and License
            bw.write(wordsInJapanese[25]);
			bw.newLine();
			bw.newLine();            
            bw.write(wordsInJapanese[26]);
            bw.write(wordsInJapanese[29]);
			bw.newLine();
            bw.write(wordsInJapanese[27]);
			bw.newLine();
			bw.newLine();
			bw.newLine();            
            
            //Developer
            bw.write(wordsInJapanese[28]);
			bw.newLine();
            bw.write("-----");
			bw.newLine();
            
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}
		
		return sw.toString();
    }
    
	String createReadmeForMiningModule()
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		String result1,result2;
		
		try{
			bw.write("[README.txt for MINING MODULE]  :  "+moduleName);
			bw.newLine();
			bw.newLine();
			
			bw.write(wordsInJapanese[0]);//miningOperations
			result1 = extractCaseLine(basicFunctions[2]);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
				bw.write(wordsInJapanese[29]);
            }
			bw.newLine();
		
			bw.write(wordsInJapanese[1]);//setData
			result1 = extractCalledFunction(dataSupply);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
		
			bw.write(wordsInJapanese[2]);//extend
			result1 = extractClassLine(moduleName);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
                bw.newLine();
				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
		
			bw.write(wordsInJapanese[3]);//focus			
			result1 = extractVariables(focusWords);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
			else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
				bw.write(wordsInJapanese[29]);                 
                bw.newLine();
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[4]);//focus
			result1 = extractLine(focusVariables[0]);
			result2 = extractLine(focusVariables[1]);
			if(result1.equals("") && result2.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                result2 = result2.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
                bw.write(result2);
                bw.newLine();
				bw.write(wordsInJapanese[29]);
            }
			bw.newLine();

			bw.write(wordsInJapanese[5]);//option visual
			result1 = extractCalledFunctions(optionRequests);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[6]);//option mining
			result1 = extractCalledFunctions(optionRequests2);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();

			bw.write(wordsInJapanese[7]);//data
			result1 = extractCalledFunction(dataRequest[0]);
			result2 = extractCalledFunction(dataRequest[1]);
			if(result1.equals("") && result2.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                result2 = result2.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
                bw.write(result2);
				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();

			bw.flush();
		}			
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}
		
		return sw.toString();
	}
	
	String createReadmeForVisualizationModule()
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		String result1,result2;
		
		try{
			
			bw.write("[README.txt for VISUALIZATION MODULE]  :  "+moduleName);
			bw.newLine();
			bw.newLine();
			
			bw.write(wordsInJapanese[9]);//displayOperations
			result1 = extractCaseLine(basicFunctions[3]);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
   				bw.write(wordsInJapanese[29]);
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[10]);//setData
			result1 = extractCaseLines(dataSupply);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
   				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[11]);//extend
			result1 = extractClassLine(moduleName);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
                bw.newLine();
   				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[12]);//focus			
			result1 = extractVariables(focusWords);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
                bw.newLine();
   				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[13]);//focus
			result1 = extractCalledFunctions(focusFunctions);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
   				bw.write(wordsInJapanese[29]);                
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[14]);//focus
			result1 = extractCalledFunctions(focusFunctions2);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
   				bw.write(wordsInJapanese[29]);
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[15]);//option visual
			result1 = extractCalledFunctions(optionRequests);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
   				bw.write(wordsInJapanese[29]);
            }
			bw.newLine();
			
			bw.write(wordsInJapanese[16]);//option mining
			result1 = extractCalledFunctions(optionRequests2);
			if(result1.equals(""))
				bw.write(wordsInJapanese[8]);
            else
            {
                result1 = result1.replaceAll("    ","");
                bw.newLine();
                bw.write(result1);
   				bw.write(wordsInJapanese[29]);
            }
			bw.newLine();

			bw.flush();
		}			
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}
		
		return sw.toString();
	}
	
	
	String extractText(String originalText[], int startLine, int endLine)
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=startLine; i<=endLine; i++)
			{

				bw.write(originalText[i]);
				bw.newLine();
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}		
		
		return sw.toString();			
	}	
	
	String sourceText()
	{
		return extractText(source, 0, source.length-1);	
	}

	int findEndOfFunction(String originalText[], int startLine)
	{
		int count = 0, index, endLine = startLine;
		boolean start = false;
		for(int i=startLine;i<originalText.length;i++)
		{
			index = -1;
			while((index = originalText[i].indexOf("{", index+1)) != -1)
				count++;
			
			if(count > 0)
				start = true;
			
			index = -1;
			while((index = originalText[i].indexOf("}", index+1)) != -1)
				count--;
			
			if(start && count == 0)
			{
				endLine = i;
				break;
			}				
		}
		return endLine;
	}
	
	String extractGlobalAreas()
	{	
		int count = 0, index = -1;		
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<source.length;i++)
			{
				if(count == 1)
				{
					bw.write(source[i]);
					bw.newLine();
					
					if(source[i].indexOf("=") != -1)
					{
						warnings += ("LINE "+(i+1)+": Using Right Value");
						warnings += System.getProperty("line.separator");
						warnings += ("You should initialize variables in initializePanel() or initializeData()");
						warnings += System.getProperty("line.separator");						
						warnings += source[i];
						warnings += System.getProperty("line.separator");
						warnings += System.getProperty("line.separator");							
					}
				}
				
				index = -1;
				while((index = source[i].indexOf("{", index+1)) != -1)
					count++;
				
				index = -1;
				while((index = source[i].indexOf("}", index+1)) != -1)
					count--;				
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}		
		
		return sw.toString();		
	}
	
	String extractConstructor(String functionName)
	{
		int startLine=0, endLine=-1;
		
		for(int i=0;i<source.length;i++)
			if(source[i].indexOf(functionName) > 0 && source[i].indexOf("(") != -1 && source[i].indexOf(")") != -1)
			{
				startLine = i;
				break;
			}
		
		if(startLine > 0)
			endLine = findEndOfFunction(source, startLine);
		
		return extractText(source, startLine, endLine);		
	}
	
	String extractClassLine(String functionName)
	{
		String out="";
		
		for(int i=0;i<source.length;i++)
			if(source[i].indexOf(functionName) != -1 && source[i].indexOf("extends") != -1)
			{
				out = source[i];
				break;
			}
		
		return out;
	}	
	
	String extractFunction(String functionName)
	{
		int startLine=0, endLine=-1;
		
		for(int i=0;i<source.length;i++)
			if(source[i].indexOf(functionName) > 0 && source[i].indexOf("void") != -1)
			{
				startLine = i;
				break;
			}
		
		if(startLine > 0)
			endLine = findEndOfFunction(source, startLine);

		return extractText(source, startLine, endLine);
	}
	
	String extractFunctions(String functionNames[])
	{
		String out = "";
		
		for(int i=0;i<functionNames.length;i++)
			out += extractFunction(functionNames[i]);
		
		return out;
	}	
	
	String extractCaseLine(String functionName)
	{
		int startLine=0, endLine=-1;
		
		for(int i=0;i<source.length;i++)
			if(source[i].indexOf(functionName) > 0 && source[i].indexOf("void") != -1)
			{
				startLine = i;
				break;
			}
		
		if(startLine > 0)
			endLine = findEndOfFunction(source, startLine);
		
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=startLine;i<=endLine;i++)				
				if(source[i].indexOf("case") != -1 || source[i].indexOf("default") != -1)
				{
					bw.write(source[i]);
					bw.newLine();
				}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}		
		
		return sw.toString();		
	}
	
	String extractCaseLine(int startLine)
	{
		int endLine=-1;

		if(startLine > 0)
			endLine = findEndOfFunction(source, startLine);
		
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=startLine;i<=endLine;i++)				
				if(source[i].indexOf("case") != -1 || source[i].indexOf("default") != -1)
				{
					bw.write(source[i]);
					bw.newLine();
				}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}		
		
		return sw.toString();		
	}	
	
	String extractCaseLines(String functionName)
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<source.length;i++)				
				if(source[i].indexOf(functionName) != -1)
				{
					bw.write(source[i]);
					bw.newLine();					
					bw.write(extractCaseLine(i));
					bw.newLine();
				}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}		
		
		return sw.toString();		
	}
	
	String extractCalledFunction(String functionName)
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		try{
			for(int i=0;i<source.length;i++)				
				if(source[i].indexOf(functionName) != -1)
				{
					bw.write(source[i]);
					bw.newLine();
				}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in SourceRead");
		}		
		
		return sw.toString();
	}
	
	String extractCalledFunctions(String functionNames[])
	{
		String out = "";
		
		for(int i=0;i<functionNames.length;i++)
			out += extractCalledFunction(functionNames[i]);
		
		return out;
	}	
	
	String extractLine(String variable)
	{
		for(int i=0;i<source.length;i++)
			if(source[i].indexOf(variable) != -1 && source[i].indexOf("true") != -1)
				return source[i];

		return "";
	}

	String extractVariable(String variable)
	{
		for(int i=0;i<source.length;i++)
			if(source[i].indexOf(variable) != -1)
				return (source[i]+System.getProperty("line.separator"));
		
		return "";
	}	
	
	String extractVariables(String variables[])
	{
		String out = "";
		
		for(int i=0;i<variables.length;i++)
			out += extractVariable(variables[i]);
		
		return out;
	}
	
	//Action listener
	public void actionPerformed(ActionEvent e)
	{
//		insideOfActionPerformed(e);
		
		for(int i=1;i<buttonNumber;i++)
			if(e.getSource () == button[i])
			{
				miningOperations(i);
				displayOperations(0);
			}
        
        if(e.getSource () == button[0])
        {
            miningOperations(6);
            displayOperations(0);
        }
		
		if(e.getSource() == miningSelect)
		{
			if(once)
			{
				miningSelect.setSelectedIndex(-1);
				once = false;
				return;
			}
				
			mining = true;
			selectedMining = miningSelect.getSelectedIndex();
			
			if(selectedMining == -1)
				return;			
			
			moduleName = moduleNames[selectedMining];
			modulePath = new String(myModulePath);
			modulePath = modulePath.replaceAll(myModuleName,moduleName);
			
			source = fileReadArray(modulePath+moduleName+".java");	
			for(int i=0;i<source.length;i++)
				source[i] = source[i].replaceAll("	","    ");			
			
			once = true;			
			visualizationSelect.setSelectedIndex(-1);
			
			button[5].setSelected(true);
			miningOperations(0);
			displayOperations(0);
		}
		
		if(e.getSource() == visualizationSelect)
		{
			if(once)
			{
				visualizationSelect.setSelectedIndex(-1);
				once = false;
				return;
			}
			
			mining = false;			
			selectedVisualization = visualizationSelect.getSelectedIndex();
			
			if(selectedVisualization == -1)
				return;
			
			moduleName = visuModuleNames[selectedVisualization];
			modulePath = new String(visuModulePath);
			modulePath = modulePath.replaceAll(visuModuleName,moduleName);			

			source = fileReadArray(modulePath+moduleName+".java");	
			for(int i=0;i<source.length;i++)
				source[i] = source[i].replaceAll("	","    ");		
			
			once = true;			
			miningSelect.setSelectedIndex(-1);
			
			button[5].setSelected(true);
			miningOperations(0);			
			displayOperations(0);
		}		
	}
}
