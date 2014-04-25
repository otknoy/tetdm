//
// Core Program for TETDM 
// Select.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//



package source;

import source.TextData.*;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.regex.*;


public class Select extends JPanel implements ActionListener
{
	TextData text;
	private int panelID;
	int defaultPanelSizeX, defaultPanelSizeY;
	
	boolean setModule = false;
	
	ModuleData moduleData;

	//Modules
	Class<?> module[];	
	MiningModule mining[];

	String module_names[];
	int miningIDs[];
	String miningModuleNamesInJapanese[];
	int module_num = 0;
	int selected_module = 0;
	boolean make_panel[];
	
	Class<?> visu_module[];
	String visu_module_names[];
	int visualizationIDs[];
	String visualizationModuleNamesInJapanese[];
	int visu_module_num = 0;

	int pair_visu[];
	boolean pairingVisualizationModules[][];
	
    SoftBevelBorder bb = new SoftBevelBorder(SoftBevelBorder.RAISED, Color.orange, Color.orange);
	TitledBorder border;	
	
    DummyPanel dummyp;
	boolean dummyShow;

	CardLayout layout;
	JPanel modulep = new JPanel(layout = new CardLayout());	// module panel
	JPanel buttonp = new JPanel(new GridLayout());	//upper button panel

	int visualizationToIndex[];
	int indexToVisualization[];	
	int selectedMining;
	int selectedVisualization;
	
    JPanel buttonPanel;
	JButton moduleSelect;
    JButton reExecution;
	
	String menuInJapanese[];
	
	PopUpForModuleSelect popModuleSelect;
	
	
	Select(TextData tex, int id, ModuleData moduleD, String inJapanese[], int panelSizeX, int panelSizeY)
	{
		setLayout(new BorderLayout());

//		setBackground(Color.white);
//        setBackground(new Color(0xff,0xda,0xb9));
//        setBackground(new Color(0xe0,0xf2,0xf7));
        setBackground(new Color(0xfb,0xef,0xf8));

		text = tex;
		panelID = id;
		moduleData = moduleD;
		menuInJapanese = inJapanese;
		defaultPanelSizeX = panelSizeX;
		defaultPanelSizeY = panelSizeY;
		
		
		module = moduleData.msub.selectedMining;
		module_names = moduleData.msub.selectedMiningNames;
		miningIDs = moduleData.msub.selectedMiningIDs;
		miningModuleNamesInJapanese = moduleData.msub.selectedMiningModuleNamesInJapanese;
		visu_module = moduleData.msub.selectedVisualization;
		visu_module_names = moduleData.msub.selectedVisualizationNames;
		visualizationIDs = moduleData.msub.selectedVisualizationIDs;
		visualizationModuleNamesInJapanese = moduleData.msub.selectedVisualizationModuleNamesInJapanese;
		
		pair_visu = new int[moduleData.msub.selectedPairDefault.length];
		for(int i=0;i<pair_visu.length;i++)
			pair_visu[i] = moduleData.msub.selectedPairDefault[i];
		
		pairingVisualizationModules	= moduleData.msub.selectedPairingVisualizationModules;
		
		module_num = module.length;
		visu_module_num = visu_module.length;		
		
		
		mining = new MiningModule[module_num];
		make_panel = new boolean[module_num];
		for(int i=0;i<module_num;i++)
			make_panel[i] = true;
	
		initModules();

		for(int i=0;i<module_num;i++)
			mining[i].setPanelID(panelID);		

		dummyp = new DummyPanel();
		dummyp.setBackground(Color.white);
		modulep.add(dummyp,"MODULEX");
		layout.show(modulep, "MODULEX");
		dummyShow = true;

		border = new TitledBorder(bb);
		setTitle();

		if(text.control.sp1.inJapanese)
        {
			moduleSelect = new JButton(menuInJapanese[4]);
            reExecution = new JButton(menuInJapanese[10]);
        }
		else
        {
			moduleSelect = new JButton("Tool Selection");
            reExecution = new JButton("Re-Execution");            
        }
		moduleSelect.addActionListener(this);
		reExecution.addActionListener(this);
        
        buttonPanel = new JPanel(new BorderLayout());

        buttonPanel.setBackground(Color.orange);
		add(buttonPanel, BorderLayout.NORTH);

		buttonPanel.add(moduleSelect);
		buttonPanel.add(reExecution, BorderLayout.EAST);
		reExecution.setEnabled(false);
	}


	class DummyPanel extends JPanel implements ActionListener
	{
	    Toolkit tk=getToolkit();
	    Image image1 = tk.getImage(text.absolutePath+"source"+File.separator+"tet1208moto.gif");		
	    int sizex, sizey, imageSizex, imageSizey;

		JButton deleteButton;
		
		DummyPanel()
		{
			setLayout(new BorderLayout());			

			if(text.control.sp1.inJapanese)
				deleteButton = new JButton(menuInJapanese[3],new ImageIcon(text.absolutePath+"source"+File.separator+"delete.png"));				
			else
				deleteButton = new JButton("DELETE",new ImageIcon(text.absolutePath+"source"+File.separator+"delete.png"));			
			
			deleteButton.addActionListener(this);
			add(deleteButton, BorderLayout.SOUTH);
			
				
		}
		
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;
			sizex = getWidth();
			sizey = getHeight();
			imageSizex = image1.getWidth(this);
			imageSizey = image1.getHeight(this);

			if(sizex >= imageSizex)
			    g2.drawImage(image1,0,sizey-imageSizey,this);
			else
			    g2.drawImage(image1,0,sizey-imageSizey,sizex,imageSizey,this);
		}
		
		//Action listener
		public void actionPerformed(ActionEvent e)
		{
            String log = e.getSource().toString();
            String regex = "text=(.*),";
            Pattern p = Pattern.compile(regex);
            
            Matcher m = p.matcher(log);
            
            if(m.find())
                writeActionLog(m.group(1));
            else
                writeActionLog(log);
            
			if(e.getSource() == deleteButton)
				text.control.delete(panelID);
		}		
	}
	
	int getPanelID()
	{
		return panelID;
	}
	
	void setPanelID(int id)
	{
		panelID = id;
		
		for(int i=0;i<module_num;i++)
		{
			mining[i].setPanelID(panelID);
			mining[i].setVisualizationPanelID();
		}
	}
	
	void setTitle()
	{
        String type[] = {"ALL","SEG","SEN","SEG2","SEN2"};
	    String title;
		if(text.control.sp1.inJapanese)
		    title = menuInJapanese[0]+" "+(panelID+1)+" : "+menuInJapanese[1]+" ("+new File(text.sourceFilename).getName()+") "+type[text.textID];
		else
		    title = "PANEL "+(panelID+1)+" : UNSELECTED ("+new File(text.sourceFilename).getName()+") "+type[text.textID];

		setBorder(BorderFactory.createTitledBorder(border, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 14), Color.blue));
	}
	
	void setTitle2()
	{
        if(mining[selected_module].text == null)
        {
            setTitle();
            return;            
        }
        //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
        String type[] = {"---","ALL","SEG1","SEN1","SEG2","SEN2"};
        String type2[] = {"---","---","ALL","SEG1","SEG2","SEN1","SEN2"};

	    String title;	    
		if(text.control.sp1.inJapanese)	
		    title = menuInJapanese[0]+" "+(panelID+1)+" : "+miningModuleNamesInJapanese[selected_module]+" + "+visualizationModuleNamesInJapanese[pair_visu[selected_module]] +" ("+new File(text.sourceFilename).getName()+") "
			+type[text.textID+1]+" "+type[mining[selected_module].text.textID+1]+" "+type2[mining[selected_module].partialDocumentA+1]+" "+type2[mining[selected_module].partialDocumentB+1];
		else
		    title = "PANEL "+(panelID+1)+" : "+module_names[selected_module]+" + "+visu_module_names[pair_visu[selected_module]]+" ("+new File(text.sourceFilename).getName()+") "+type[text.textID+1]+" "+type[mining[selected_module].text.textID+1]
			+type2[mining[selected_module].partialDocumentA+1]+" "+type2[mining[selected_module].partialDocumentB+1];

		setBorder(BorderFactory.createTitledBorder(bb, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 14), Color.blue ));
	}

	void initModules()
	{		
		add(modulep);

		try {
			for(int i=0;i<module_num;i++)
				mining[i] = (MiningModule)module[i].newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		//				mining[i] = (MiningModule)module[i].newInstance();
		for(int i=0;i<module_num;i++)
			modulep.add(mining[i],"MODULE"+i);
	}
	//////////////////////
	
	void reset_font_size()
	{
		if(mining[selected_module].visual != null) //UNSELECTED PANEL
			mining[selected_module].setFont();
	}	

	//
	void resetAll(TextData tex)
	{
		text = tex;
		
//		resetAll_for_stealth(tex);
		
		if(setModule == false)
		{
			for(int i=0;i<module_num;i++)
				mining[i].text = text;
			return;
		}
		
		for(int i=0;i<module_num;i++)
			if(make_panel[i] == false)
				mining[i].panelReset();
		
		
        switch(mining[selected_module].partialDocumentA)
        {
            case 2:mining[selected_module].combinationPanel.panelSetFocusSegments(0);
                break;
                
            case 3:mining[selected_module].combinationPanel.panelSetFocusSegments(1);
                break;
                
            case 4:mining[selected_module].combinationPanel.panelSetFocusSentences(0);
                break;
                
            case 5:mining[selected_module].combinationPanel.panelSetFocusSentences(1);
                break;
        }
		
		mining[selected_module].selected(text);
		
		setTitle2();
	}	


	// FOCUS*
	void resetMiningByTouchFocus()
	{
		mining[selected_module].selectedByTouchFocus();		
	}		
	// FOCUS*
	void setDisplayByTouchFocus()
	{
		mining[selected_module].setDisplayByTouchFocus();
	}	
	
	// FOCUS*
	void resetMiningByClickFocus()
	{
		mining[selected_module].selectedByClickFocus();		
	}	
	// FOCUS*
	void setDisplayByClickFocus()
	{
		mining[selected_module].setDisplayByClickFocus();
	}
	
	// FOCUS*
	void resetMiningByTimingFocus()
	{
		mining[selected_module].selectedByTimingFocus();		
	}	
	// FOCUS*
	void setDisplayByTimingFocus()
	{
		mining[selected_module].setDisplayByTimingFocus();
	}	
	
	void changePanel(int mid, int vid)
	{
        int MID = selected_module;
        int VID = pair_visu[selected_module];
            
        for(int i=0;i<module_num;i++)
            if(miningIDs[i] == mid)
            {
                MID = i;
                selected_module = MID;
                VID = pair_visu[selected_module];
				
                for(int j=0;j<visu_module_num;j++)
				{
                    if(visualizationIDs[j] == vid)
                    {
                        VID = j;
                        pair_visu[selected_module] = j;
//						System.out.println("FOUND Search="+visualizationIDs[j]);//						
                    }
//		System.out.println("Search="+visualizationIDs[j]);//					
				}
            }

//		System.out.println("Required="+mid+":"+vid);//
//		System.out.println("Set="+MID+":"+VID);//
        text.control.writeSetModules("CHANGE-MODULE in PANEL "+(panelID+1));
//		text.control.writeActionLog("CHANGE-MODULE panelID= "+panelID+" ("+mid+","+vid+")");
		
        setModule = true;
            
        if(make_panel[selected_module])
        {
            mining[selected_module].init_panel(text, visu_module, pair_visu[selected_module], text.absolutePath, defaultPanelSizeX, defaultPanelSizeY);
            make_panel[selected_module] = false;
        }

        mining[selected_module].setVisu(pair_visu[selected_module]);
        mining[selected_module].selected(text);
		
        setTitle2();
        layout.show(modulep, "MODULE"+selected_module);
        reExecution.setEnabled(true);
        dummyShow = false;
    }         

	void changePanel(int mid, int vid, int tid)
	{
        int MID = selected_module;
        int VID = pair_visu[selected_module];
		
        for(int i=0;i<module_num;i++)
            if(miningIDs[i] == mid)
            {
                MID = i;
                selected_module = MID;
                VID = pair_visu[selected_module];
				
                for(int j=0;j<visu_module_num;j++)
				{
                    if(visualizationIDs[j] == vid)
                    {
                        VID = j;
                        pair_visu[selected_module] = j;
						//						System.out.println("FOUND Search="+visualizationIDs[j]);//						
                    }
					//		System.out.println("Search="+visualizationIDs[j]);//					
				}
            }
		
		//		System.out.println("Required="+mid+":"+vid);//
		//		System.out.println("Set="+MID+":"+VID);//
        text.control.writeSetModules("CHANGE-MODULE in PANEL "+(panelID+1));
//		text.control.writeActionLog("CHANGE-MODULE panelID= "+panelID+" ("+mid+","+vid+","+tid+")");
		
        setModule = true;
		
        if(make_panel[selected_module])
        {
            mining[selected_module].init_panel(text, visu_module, pair_visu[selected_module], text.absolutePath, defaultPanelSizeX, defaultPanelSizeY);
            make_panel[selected_module] = false;
        }
		
        mining[selected_module].setVisu(pair_visu[selected_module]);
		
        int IDtoIndex[] = {0,2,4,3,5};
        
        //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
            // 1:ALL, 2:SEG1, 3:SEG2, 4:SEN1, 5:SEN2
        if(tid < 0)
        {
            int ptid = tid * (-1);
            int index1 = (int)ptid/10;
            int index2 = ptid%10;
            
            System.out.println("PARTIAL "+index1+" <-> "+index2);
            
            mining[selected_module].partialDocumentA = index1;
            mining[selected_module].partialDocumentB = index2;
            
            mining[selected_module].combinationPanel.autoSet = true;
            if(index1 >= 1 && index1 <= 5)
                mining[selected_module].combinationPanel.partialDocument.setSelectedIndex(index1);
            mining[selected_module].combinationPanel.autoSet = false;
        }
        else
            if(tid >= 1 && tid <= 5)
                mining[selected_module].combinationPanel.partialDocument.setSelectedIndex(IDtoIndex[tid]);
		
        mining[selected_module].selected(text);
		
        setTitle2();
        layout.show(modulep, "MODULE"+selected_module);
        reExecution.setEnabled(true);
        dummyShow = false;
    }
    
    public void createDiffPanel()
    {
        mining[selected_module].combinationPanel.autoSet = true;
        mining[selected_module].combinationPanel.displayDiffButton.setSelected(true);
        mining[selected_module].combinationPanel.autoSet = false;
        mining[selected_module].combinationPanel.displayDiff();
    }
	
	//SAVE ACTION LOG
    String logFilename;
    public void writeActionLog(String data)
    {
        logFilename = text.absolutePath+"ACTIONLOG";
        try
        {
            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "Shift_JIS"));
//            BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));
			
            bw1.write(String.format("%.2f",text.control.getDiffTime())+" SELECT PanelID="+panelID+" "+data+System.getProperty("line.separator"));
            bw1.close();
        }
        catch(Exception e){
            System.out.println("File Writing ERROR ACTIONLOG in SELECT");
        }
    }
    
	//Action listener
	public void actionPerformed(ActionEvent e)
	{
        String log = e.getSource().toString();
        String regex = "text=(.*)]";
        Pattern p = Pattern.compile(regex);
        
        Matcher m = p.matcher(log);
        
        if(m.find())
            writeActionLog(m.group(1));
        else
            writeActionLog(log);
        
		if(e.getSource() == moduleSelect)
		{
			Point position = getLocationOnScreen();
			
			popModuleSelect = new PopUpForModuleSelect((int)position.getX(),(int)position.getY(),getWidth(),getHeight(),text.control,this,menuInJapanese);
			text.control.writeActionLog("OPEN-MODULE-SELECT-WINDOW-SINGLE panelID= "+panelID);
		}
        if(e.getSource() == reExecution)
        {
            mining[selected_module].resetFirstTime();
            mining[selected_module].selected(text);        
        }
	}
	
	
	///////////////FOR STEALTH PANEL ONLY////////////////
	int change_panel_moduleID(int mid) // return moduleIndex;
	{
		int pair=0;		//Find VisualizationStyle
		for(int i=0;i<moduleData.visu_module_names.length;i++)
			if(moduleData.visuModuleIDs[i] == 0)
				pair = i;
		
		for(int i=0;i<moduleData.module_names.length;i++)
			if(moduleData.moduleIDs[i] == mid)
			{
				moduleData.mining[i].init_panel(text, moduleData.visu_module, pair, text.absolutePath, defaultPanelSizeX, defaultPanelSizeY);
				moduleData.mining[i].initializeData();				
				moduleData.mining[i].selected(text);
				return i;
			}
		return -1;
	}
	
	void executeModule(int optionNumber, int mp)
	{
		moduleData.mining[mp].miningOperations(optionNumber);
	}
	
	void resetAll_for_stealth(TextData tex)
	{
		text = tex;		
		
		for(int i=0;i<moduleData.module_names.length;i++)
		{
			moduleData.mining[i].text = tex;
			moduleData.mining[i].firstTime = true;		
		}
	}
	
	void resetFirstTime_for_stealth()
	{
		for(int i=0; i<moduleData.module_names.length;i++)
			moduleData.mining[i].firstTime = true;
	}
	///////////////FOR STEALTH PANEL ONLY////////////////
	
	///////FOR MODULE EXECUTION
	void executeModule(int option)
	{
		mining[selected_module].miningOperations(option);
	}	
	
	///////FOR MODULE DISPLAY
	void displayModule(int option)
	{
		mining[selected_module].displayOperations(option);
	}
}
