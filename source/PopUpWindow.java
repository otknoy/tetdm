

//
// Core Program for TETDM 
// PopUpWindow.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.event.*;

import javax.swing.border.*;

public class PopUpWindow extends JFrame
{
	JTextPane textPanel;
	JScrollPane scrollPanel;
	Container pane = getContentPane();	
		
	public PopUpWindow(String titleText, String displayText, int X, int Y, int width, int height, int fontSize, Color color)
	{
		super("README.txt");
		setBounds(X, Y, width, height);
		
		setAlwaysOnTop(true);
		pane.setBackground(Color.white);
//		setUndecorated(true);
		
		textPanel = new JTextPane();
		textPanel.setContentType("text/plain");
		scrollPanel = new JScrollPane(textPanel);
		pane.add(scrollPanel);
		
		textPanel.setText(displayText);
		textPanel.setCaretPosition(0);
		textPanel.setFont(new Font("Dialog", Font.BOLD, fontSize));
		
		scrollPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED), new TitledBorder(new LineBorder(Color.red), titleText, TitledBorder.CENTER, TitledBorder.TOP)));
				
		scrollPanel.setBackground(color);
		textPanel.setBackground(color);		
		
		setVisible(true);
	}
}
