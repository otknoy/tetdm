//
// Core Program for TETDM 
// ModuleData.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

//import source.*;
import source.TextData.*;
import source.Utility.*;

import java.io.*;
import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ModuleData
{
	String absolutePath;
	
	//Module names
	static File module_dir;
	static String module_package = "module.MiningModules"; // mining module package	
	public String module_names[];
	public String miningModuleNamesInJapanese[];	
	public int moduleIDs[];
	Class<?> module[];
	public MiningModule mining[];
	
	String miningReadmeText[];
	String miningInformationText[];
	
	public class MiningPanelSet
	{
		boolean panelRecorded;
		int MiningIDs[];
		int VisualizationIDs[];
		int TextIDs[];		
		int sizeX[];

	    boolean panelBack;
	    int backMiningIDs[];
	    int backVisualizationIDs[];
		int backTextIDs[];		
		int backSizeX[];
		
		MiningPanelSet()
		{
			panelRecorded = false;
			panelBack = false;
		}
		
		public void setPanelData(int MID[], int VID[], int TID[], int sX[])
		{
			MiningIDs = new int[MID.length];
			VisualizationIDs = new int[VID.length];
			TextIDs = new int[TID.length];
			sizeX = new int[sX.length];
			
			for(int i=0;i<MID.length;i++)
				MiningIDs[i] = MID[i];
			
			for(int i=0;i<VID.length;i++)			
				VisualizationIDs[i] = VID[i];
			
			for(int i=0;i<TID.length;i++)
				TextIDs[i] = TID[i];
			
			for(int i=0;i<sX.length;i++)
				sizeX[i] = sX[i];
			
			panelRecorded = true;
		}		
		
		public void setBackPanelData(int MID[], int VID[], int TID[], int sX[])
		{
			backMiningIDs = new int[MID.length];
			backVisualizationIDs = new int[VID.length];
			backTextIDs = new int[TID.length];
			backSizeX = new int[sX.length];			
			
			for(int i=0;i<MID.length;i++)
				backMiningIDs[i] = MID[i];
			
			for(int i=0;i<VID.length;i++)			
				backVisualizationIDs[i] = VID[i];
			
			for(int i=0;i<TID.length;i++)
				backTextIDs[i] = TID[i];			
			
			for(int i=0;i<sX.length;i++)
				backSizeX[i] = sX[i];			
			
			panelBack = true;
		}		
	}
	
	MiningPanelSet miningPanelSet[];
	
	
	
	static File visu_module_dir;
	static String visu_module_package = "module.VisualizationModules"; // visualization module package	
	public String visu_module_names[];
	public String visualizationModuleNamesInJapanese[];
	public int visuModuleIDs[];	
	Class<?> visu_module[];	
	VisualizationModule visual[];
	
	String visualizationReadmeText[];
	String visualizationInformationText[];		
	
	boolean miningModuleSelection[];
	boolean visualizationModuleSelection[];
	
	ModuleSubset msub;
	
	ModuleData(String absoluteP)
	{
		absolutePath = absoluteP;
		module_dir = new File(absolutePath + "module" + File.separator + "MiningModules");	//module directory
		visu_module_dir = new File(absolutePath + "module" + File.separator + "VisualizationModules");	//module directory
		
		readModuleNames();
	}

	String[] check_dir(File dir)
	{
		File dir_list[] = dir.listFiles();
		String filename_list[];
		String filename;
		
		int count=0;
		for(int i=0;i<dir_list.length;i++)
			if(dir_list[i].getName().matches("^[A-Z][A-Za-z0-9]+$"))
				count++;
		
		filename_list = new String[count];
		
		count=0;		
		for(int i=0;i<dir_list.length;i++)
		{
			filename = dir_list[i].getName();
			if(filename.matches("^[A-Z][A-Za-z0-9]+$"))
			{
				filename_list[count] = filename; //filename.substring(0,filename.length());
				count++;
			}
		}
		
		return filename_list;
	}		
	
	private void readModuleNames()
	{
		module_names = check_dir(module_dir);
		if(module_names.length == 0)
		{
			System.out.println("No modules in directory "+module_dir.getName());
			System.exit(0);			
		}
		
		
		miningPanelSet = new MiningPanelSet[module_names.length];//PANEL SET
		for(int i=0;i<module_names.length;i++)
			miningPanelSet[i] = new MiningPanelSet();
		
		
		visu_module_names = check_dir(visu_module_dir);
		if(visu_module_names.length == 0)
		{
			System.out.println("No modules in directory "+visu_module_dir.getName());
			System.exit(0);			
		}
		
		miningModuleNamesInJapanese = new String[module_names.length];
		miningReadmeText = new String[module_names.length];
		miningInformationText = new String[module_names.length];

		for(int i=0;i<module_names.length;i++)
		{
			String filename = absolutePath + "module" + File.separator + "MiningModules" + File.separator + module_names[i] + File.separator + module_names[i]+ ".txt";
			File jpname = new File(filename);
			if(jpname.exists())
            {
				miningModuleNamesInJapanese[i] = FILEIO.TextFileAllReadCode(jpname);
                miningModuleNamesInJapanese[i] = miningModuleNamesInJapanese[i].replace(System.getProperty("line.separator"),"");
            }
			else
				miningModuleNamesInJapanese[i] = module_names[i];
			
			filename = absolutePath + "module" + File.separator + "MiningModules" + File.separator + module_names[i] + File.separator + "README.txt";
			jpname = new File(filename);
			if(jpname.exists())
				miningReadmeText[i] = FILEIO.TextFileAllReadCode(jpname);
			else
				miningReadmeText[i] = "NO INFORMATION"+System.getProperty("line.separator");
			
			miningInformationText[i] = miningReadmeText[i].substring(0,miningReadmeText[i].indexOf(System.getProperty("line.separator")));			
		}		
		
		visualizationModuleNamesInJapanese = new String[visu_module_names.length];
		visualizationReadmeText = new String[visu_module_names.length];
		visualizationInformationText = new String[visu_module_names.length];
		
		for(int i=0;i<visu_module_names.length;i++)
		{
			String filename = absolutePath + "module" + File.separator + "VisualizationModules" + File.separator + visu_module_names[i] + File.separator + visu_module_names[i]+ ".txt";
			File jpname = new File(filename);
			if(jpname.exists())
            {
				visualizationModuleNamesInJapanese[i] = FILEIO.TextFileAllReadCode(jpname);
                visualizationModuleNamesInJapanese[i] = visualizationModuleNamesInJapanese[i].replace(System.getProperty("line.separator"),"");
            }
			else
				visualizationModuleNamesInJapanese[i] = visu_module_names[i];
			
			filename = absolutePath + "module" + File.separator + "VisualizationModules" + File.separator + visu_module_names[i] + File.separator + "README.txt";
			jpname = new File(filename);
			if(jpname.exists())
				visualizationReadmeText[i] = FILEIO.TextFileAllReadCode(jpname);
			else
				visualizationReadmeText[i] = "NO INFORMATION"+System.getProperty("line.separator");
			
			visualizationInformationText[i] = visualizationReadmeText[i].substring(0,visualizationReadmeText[i].indexOf(System.getProperty("line.separator")));
		}		
		
		module = new Class<?>[module_names.length];
		try {
			for(int i=0;i<module_names.length;i++)
				module[i] = Class.forName(module_package+"."+module_names[i]+"."+module_names[i]);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		mining = new MiningModule[module_names.length];
		try {
			for(int i=0;i<module_names.length;i++)
				mining[i] = (MiningModule)module[i].newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		visu_module = new Class<?>[visu_module_names.length];
		try {
			for(int i=0;i<visu_module_names.length;i++)
				visu_module[i] = Class.forName(visu_module_package+"."+visu_module_names[i]+"."+visu_module_names[i]);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		visual = new VisualizationModule[visu_module_names.length];
		try {
			for(int i=0;i<visu_module_names.length;i++)
				visual[i] = (VisualizationModule)visu_module[i].newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		moduleIDs = new int[module_names.length];
		for(int i=0;i<module_names.length;i++)
			moduleIDs[i] = mining[i].getModuleID();
		
		visuModuleIDs = new int[visu_module_names.length];
		for(int i=0;i<visu_module_names.length;i++)
			visuModuleIDs[i] = visual[i].getModuleID();
		
		System.out.println("<MINING MODULES>");		
		for(int i=0;i<module_names.length;i++)
			System.out.println("ID:"+moduleIDs[i]+":"+module_names[i]);
		
		System.out.println("<VISUALIZATION MODULES>");		
		for(int i=0;i<visu_module_names.length;i++)
			System.out.println("ID:"+visuModuleIDs[i]+":"+visu_module_names[i]);
		
		miningModuleSelection = new boolean[module_names.length];
		for(int i=0;i<module_names.length;i++)
			miningModuleSelection[i] = true;
		
		visualizationModuleSelection = new boolean[visu_module_names.length];
		
		initializeDefaultPairingID();		/**/
	}
	//index
	public int findModule(int miningModuleID)
	{
		for(int i=0;i<module_names.length;i++)
			if(moduleIDs[i] == miningModuleID)
				return i;
		
		return -1;
	}
    //index
	public int findVisualizationModule(int visualizationModuleID)
	{
		for(int i=0;i<visu_module_names.length;i++)
			if(visuModuleIDs[i] == visualizationModuleID)
				return i;
		
		return -1;
	}
	
	public String getModuleName(int miningModuleID)
	{
		for(int i=0;i<module_names.length;i++)
			if(moduleIDs[i] == miningModuleID)
				return module_names[i];
		
		return "";
	}
    
	public String getVisualizationModuleName(int visualizationModuleID)
	{
		for(int i=0;i<visu_module_names.length;i++)
			if(visuModuleIDs[i] == visualizationModuleID)
				return visu_module_names[i];
		
		return "";
	}	
	
	//miningModuleSelection -> visualizationModuleSelection
	void initializeVisualiationModuleSelection()
	{
		for(int i=0;i<visu_module_names.length;i++)
			visualizationModuleSelection[i] = false;		
		
		for(int i=0;i<module_names.length;i++)
			if(miningModuleSelection[i])
			{
				int count = 0;
				for(int j=0;j<visu_module_names.length;j++)				
					for(int k=0;k<mining[i].pairingVisualizationID.length;k++)
						if(visuModuleIDs[j] == mining[i].pairingVisualizationID[k])
						{
							visualizationModuleSelection[j] = true;
							count++;
						}
				if(count == 0)
					miningModuleSelection[i] = false;
			}
	}
	
	void initializeDefaultPairingID()
	{
		for(int i=0;i<module_names.length;i++)
			mining[i].pairingVisuID = new int[mining[i].pairingVisualizationID.length];
		
		for(int i=0;i<module_names.length;i++)
		{
			for(int j=0;j<mining[i].pairingVisualizationID.length;j++)
				mining[i].pairingVisuID[j] = mining[i].pairingVisualizationID[j];
			setModuleIndex(i, mining[i].pairingVisualizationID);
		}
		///////////
		for(int i=0;i<visu_module_names.length;i++)		
			createPairingMiningID(i);
	}
	
	void setModuleIndex(int n, int IDs[])
	{
		mining[n].pairingVisualizationIndex = new int[IDs.length];

		for(int j=0;j<IDs.length;j++)		
			mining[n].pairingVisualizationIndex[j] = findVisualizationModule(IDs[j]);
	}
	
	void createPairingMiningID(int n)
	{
		int count=0;
		
		for(int i=0;i<module_names.length;i++)
			for(int j=0;j<mining[i].pairingVisualizationIndex.length;j++)
				if(mining[i].pairingVisualizationIndex[j] == n)
				{
					count++;
//					System.out.println("n="+n+","+module_names[i]+":"+visu_module_names[n]);
				}
		
		visual[n].pairingMiningIndex = new int[count];

		count = 0;
		for(int i=0;i<module_names.length;i++)
			for(int j=0;j<mining[i].pairingVisualizationIndex.length;j++)
				if(mining[i].pairingVisualizationIndex[j] == n)
					visual[n].pairingMiningIndex[count++] = i;
	}

///////////////////////////////	
	
	void initializeModuleSubset()
	{
		msub = new ModuleSubset();
	}
	
	class ModuleSubset
	{
		String selectedMiningNames[];
		String selectedMiningModuleNamesInJapanese[];
		int selectedMiningIDs[];
		Class<?> selectedMining[];
		String selectedVisualizationNames[];
		String selectedVisualizationModuleNamesInJapanese[];
		int selectedVisualizationIDs[];		
		Class<?> selectedVisualization[];
		
		int selectedPairDefault[];
		boolean selectedPairingVisualizationModules[][];
		
		ModuleSubset()
		{
			int miningNumber=0, visualizationNumber=0;
			
			for(int i=0;i<miningModuleSelection.length;i++)
				if(miningModuleSelection[i])
					miningNumber++;
			
			for(int i=0;i<visualizationModuleSelection.length;i++)
				if(visualizationModuleSelection[i])
					visualizationNumber++;
			
			selectedMiningNames = new String[miningNumber];
			selectedMiningModuleNamesInJapanese = new String[miningNumber];
			selectedMiningIDs = new int[miningNumber];
			selectedMining = new Class<?>[miningNumber];
			selectedVisualizationNames = new String[visualizationNumber];
			selectedVisualizationModuleNamesInJapanese = new String[visualizationNumber];
			selectedVisualizationIDs = new int[visualizationNumber];
			selectedVisualization = new Class<?>[visualizationNumber];
			
			selectedPairDefault = new int[miningNumber];
			selectedPairingVisualizationModules = new boolean[miningNumber][visualizationNumber];
			
			for(int i=0,m=0;i<miningModuleSelection.length;i++)
			{
				if(miningModuleSelection[i])
				{
					selectedMiningNames[m] = module_names[i];
					selectedMiningIDs[m] = moduleIDs[i];
					selectedMiningModuleNamesInJapanese[m] = miningModuleNamesInJapanese[i];
					selectedMining[m] = module[i];					
					m++;
				}	
			}
			
			for(int i=0,n=0;i<visualizationModuleSelection.length;i++)
			{
				if(visualizationModuleSelection[i])
				{
					selectedVisualizationNames[n] = visu_module_names[i];
					selectedVisualizationIDs[n] = visuModuleIDs[i];
					selectedVisualizationModuleNamesInJapanese[n] = visualizationModuleNamesInJapanese[i];
					selectedVisualization[n++] = visu_module[i];
				}
			}
			
			setPairID();
		}
		
		void setPairID()
		{
			for(int i=0,m=0;i<miningModuleSelection.length;i++)
				if(miningModuleSelection[i])
				{
					for(int j=0,n=0;j<visualizationModuleSelection.length;j++)
						if(visualizationModuleSelection[j])						
							selectedPairingVisualizationModules[m][n++] = false;
					m++;
				}	
			
			for(int i=0,m=0;i<miningModuleSelection.length;i++)
				if(miningModuleSelection[i])
				{
					boolean check = false;
					int defaultPair = -1;
					
					for(int j=0,n=0;j<visualizationModuleSelection.length;j++)
						if(visualizationModuleSelection[j])
						{
							for(int k=0;k<mining[i].pairingVisualizationID.length;k++)
								if(visual[j].getModuleID() == mining[i].pairingVisualizationID[k])
								{
									selectedPairingVisualizationModules[m][n] = true;
									if(!check)
									{
										selectedPairDefault[m] = n;
										check = true;
									}
								}
							n++;
						}
					m++;
				}
		}
	}
}
