//
// Core Program for TETDM
// PopUpWindow.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import source.*;

import java.io.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.event.*;

import javax.swing.border.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class PopUpForDiff extends JFrame implements ActionListener
{
    JPanel dataSelectPanel;
	JRadioButton diffDataSettings[][];
	ButtonGroup dataButtonGroup[];
    
            //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
	String dataSetName[] = {"ALL","SEG-SET-1","SEG-SET-2","SEN-SET-1","SEN-SET-2"};
    int textDataID[] = {0,1,3,2,4};
    int IDtoIndex[] = {0,1,3,2,4};
    int selectedData1, selectedData2;
    
	JTextPane textPanel;
	JScrollPane scrollPanel;
	Container pane = getContentPane();
    
	String dataType[] = {"boolean","int","double","String","boolean[]","int[]","double[]","String[]","boolean[][]","int[][]","double[][]"};
    JPanel tabPanel[];
	JScrollPane scrollPane[];
	JRadioButton diffSettings[][][];
	ButtonGroup buttonGroup[][];
	
	int dataNumbers[];
	int diffPatterns[][];
	
    JTabbedPane tab;
	
	MiningModule mining;
    
    String buttonNamesInJapanese[];
    
	boolean autoSet = false;
    
	public PopUpForDiff(int X, int Y, int width, int height, int dataNumbers[], int diffPatterns[][], MiningModule mining)
	{
		super("Differential Settings");
		setBounds(X, Y, width, height);        

		setAlwaysOnTop(true);
		pane.setBackground(Color.PINK);
        
		this.dataNumbers = dataNumbers;
		this.diffPatterns = diffPatterns;
		this.mining = mining;
        
        String filename = mining.absolutePath+"source"+File.separator+"JapaneseMining.txt";
        File jpname = new File(filename);
        
        buttonNamesInJapanese = FILEIO.TextFileAllReadCodeArray(jpname);
		
        //DATA SELECT
        dataSelectPanel = new JPanel();
        dataSelectPanel.setBackground(Color.GREEN);
        setDataSelectPanel();
        
        //TAB
        tab = new JTabbedPane();		
		tabPanel = new JPanel[11];
		scrollPane = new JScrollPane[11];
		diffSettings = new JRadioButton[11][][];
		buttonGroup = new ButtonGroup[11][];
		
		for(int i=0;i<11;i++)
		{
			tabPanel[i] = new JPanel();
			tabPanel[i].setBackground(Color.WHITE);
		}
		
		for(int i=0;i<11;i++)
			if(dataNumbers[i] > 0)
			{
				setPanel(i,dataNumbers[i]);
				tab.addTab(buttonNamesInJapanese[17+i]+"("+dataType[i]+")", scrollPane[i]);
			}

        //
        add(dataSelectPanel, BorderLayout.NORTH);
        add(tab, BorderLayout.CENTER);

		setVisible(true);
	}
    
	public void setDataSelectPanel()
	{

        JLabel firstSentence[] = new JLabel[5];
        JLabel lastSentence[] = new JLabel[5];



        int indexToID[] = {0,1,3,2,4};
        
		dataSelectPanel.setLayout(new GridLayout(dataSetName.length+1+5, 2));
        
        diffDataSettings = new JRadioButton[2][5];
        dataButtonGroup = new ButtonGroup[2];
        
        if(mining.isMenuInJapanese())
        {
            dataSetName[0] = buttonNamesInJapanese[15];
            for(int i=1;i<5;i++)
                dataSetName[i] = buttonNamesInJapanese[16]+"-"+i;
        }
        
		JLabel dataLabel[] = new JLabel[2];
        
        if(mining.partialDocumentA > 0)
            dataLabel[0] = new JLabel("  A = ["+dataSetName[mining.partialDocumentA-1]+"]");
        else
            dataLabel[0] = new JLabel("  A = []");
        
        if(mining.partialDocumentB > 0)
            dataLabel[1] = new JLabel("  B = ["+dataSetName[mining.partialDocumentB-1]+"]");
        else
            dataLabel[1] = new JLabel("  B = []");
        
		
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<dataSetName.length;j++)
            {
                diffDataSettings[i][j] = new JRadioButton(dataSetName[j]);
				diffDataSettings[i][j].addActionListener(this);
                
                firstSentence[j] = new JLabel("  <NOT SET>");
                lastSentence[j] = new JLabel("  <NOT SET>");
                
                if(mining.text.partOfTextData[indexToID[j]].sentence != null)
                {
                    if(mining.text.partOfTextData[indexToID[j]].sentenceNumber > 0)
                    {
                        firstSentence[j] = new JLabel("[  "+mining.text.partOfTextData[indexToID[j]].sentence[0].sentenceText);
                        lastSentence[j] = new JLabel("->"+mining.text.partOfTextData[indexToID[j]].sentence[mining.text.partOfTextData[indexToID[j]].sentenceNumber-1].sentenceText);
                    }
                }
			}
			dataButtonGroup[i] = new ButtonGroup();
            
            for(int j=0;j<dataSetName.length;j++)
                dataButtonGroup[i].add(diffDataSettings[i][j]);
        }
        
        for(int i=0;i<dataLabel.length;i++)
            dataSelectPanel.add(dataLabel[i]);
        
        for(int j=0;j<dataSetName.length;j++)
            for(int i=0;i<2;i++)
            {
                dataSelectPanel.add(diffDataSettings[i][j]);
                dataSelectPanel.add(firstSentence[j]);
                dataSelectPanel.add(lastSentence[j]);
            }

        
			
		autoSet = true;
//        diffDataSettings[0][1].setSelected(true);
        selectedData1 = -1;
//        diffDataSettings[1][2].setSelected(true);
        selectedData2 = -1;
		autoSet = false;
	}
    
	public void setPanel(int tabID, int dataNumber)
	{
		JLabel dataLabel[] = new JLabel[dataNumber+1];
		dataLabel[0] = new JLabel("ALL : ");
		for(int i=1;i<dataNumber+1;i++)
			dataLabel[i] = new JLabel("Data : " + i );
        
		String[] diffTypeName = {"A-B","B-A","Max","Min"};
		
		diffSettings[tabID] = new JRadioButton[dataNumber+1][4];
		buttonGroup[tabID] = new ButtonGroup[dataNumber+1];
		for(int i=0;i<dataNumber+1;i++)
		{
			for(int j=0;j<4;j++)
			{
				diffSettings[tabID][i][j] = new JRadioButton(diffTypeName[j]);
				diffSettings[tabID][i][j].addActionListener(this);
			}
			buttonGroup[tabID][i] = new ButtonGroup();
			
			autoSet = true;
			diffSettings[tabID][i][diffPatterns[tabID][i]].setSelected(true);
//			diffSettings[tabID][i][0].setSelected(true);			
			autoSet = false;
		}
		
		tabPanel[tabID].setLayout(new GridLayout(dataNumber+1,5));		
		
		for(int i=0;i<dataNumber+1;i++)
		{
			tabPanel[tabID].add(dataLabel[i]);
			for(int j=0;j<4;j++)
			{
				tabPanel[tabID].add(diffSettings[tabID][i][j]);
				buttonGroup[tabID][i].add(diffSettings[tabID][i][j]);
			}
		}

        scrollPane[tabID] = new JScrollPane();
        scrollPane[tabID].getViewport().setView(tabPanel[tabID]);		
		
        add(scrollPane[tabID]);		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(autoSet)
			return;

        for(int i=0;i<2;i++)
            for(int j=0;j<5;j++)
                if(e.getSource() == diffDataSettings[i][j])
                {
                    if(i == 0)
                        selectedData1 = textDataID[j];
                    else
                        selectedData2 = textDataID[j];
                    
                    if(selectedData1 != -1 && selectedData2 != -1)
                        mining.combinationPanel.panelSetForDiff(selectedData1,selectedData2);
                }
		
		for(int i=0;i<11;i++)
			if(dataNumbers[i] > 0)
			{
				for(int j=0;j<4;j++)
					if(e.getSource() == diffSettings[i][0][j])//ALL
					{
						diffPatterns[i][0] = j;
						autoSet = true;
						for(int k=1;k<dataNumbers[i]+1;k++)
						{
							diffSettings[i][k][j].setSelected(true);
							diffPatterns[i][k] = j;
						}
						autoSet = false;
						
						mining.reExecute();
					}
				
				for(int k=1;k<dataNumbers[i]+1;k++)
					for(int j=0;j<4;j++)
						if(e.getSource() == diffSettings[i][k][j])
						{
							diffPatterns[i][k] = j;
							
							mining.reExecute();							
						}
			}
	} 
}
