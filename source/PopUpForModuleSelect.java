

//
// Core Program for TETDM 
// PopUpWindow.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class PopUpForModuleSelect extends JFrame
{
	Container pane = getContentPane();
	SetToolPanel2 setToolPanel2;
	
	Control control;
	Select select;
		
	PopUpForModuleSelect(int X, int Y, int width, int height, Control control, Select select, String inJapanese[])
	{
		super("Module Select");
		pane.setLayout(new BorderLayout());
		
		setBounds(X, Y, width, height);
		
		setAlwaysOnTop(true);
		pane.setBackground(Color.white);
//		setUndecorated(true);//TEST
		
		setToolPanel2 = new SetToolPanel2(control, this, select, inJapanese);
		this.addWindowListener(new MyWindowListener());
        pane.add(setToolPanel2);
		setVisible(true);
		
		this.control = control;
		this.select = select;
	}
	
	public void setMenu()
	{
		setToolPanel2.setMenu();
	}
	
	class MyWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) 
		{
			//System.out.println("windowClosing");
			
			setToolPanel2.closeWindows();
			control.writeActionLog("CLOSE-MODULE-SELECT-WINDOW-SINGLE panelID= "+select.getPanelID());
		}
	}
}
