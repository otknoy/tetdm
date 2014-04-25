

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
import javax.swing.JPanel;

public class PopUpForProcessing extends JFrame
{
	ProcessingPanel processingPanel;
	Container pane = getContentPane();	
		
	PopUpForProcessing(int X, int Y, int width, int height, String processName, Color barColor)
	{
		super("Now Processing");
		pane.setLayout(new BorderLayout());
		
		setBounds(X, Y, width, height);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setAlwaysOnTop(true);
		pane.setBackground(Color.white);
//		setUndecorated(true);
		
		processingPanel = new ProcessingPanel(X, Y, width, height, processName, barColor);
//		this.addWindowListener(new MyWindowListener());
		pane.add(processingPanel);		
		setVisible(true);
		
		setPercent();
	}
	/*
	class MyWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) 
		{
			//System.out.println("windowClosing");
			
			settingPanel.closeWindow();
		}
	}*/
	
	public void setPercent()
	{
		processingPanel.setPercent();
	}	
	
	public void setPercent(int percent)
	{
		processingPanel.setPercent(percent);
	}	
}

class ProcessingPanel extends JPanel
{
	int X,Y,width,height;
	
	private int percent;
	int xmargin,ymargin;
	String processName;
	Color barColor;
	
	ProcessingPanel(int X, int Y, int width, int height, String processName, Color barColor)
	{
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.processName = processName;
		this.barColor = barColor;
		
		percent = 10;
		xmargin = 20;
		ymargin = 100;
	}
	
	public void setPercent()
	{
		paintImmediately(0,0,width,height);	
		System.out.println("START PROCESS ------------" + processName);
	}	
	
	public void setPercent(int percent)
	{
		this.percent = percent;
		paintImmediately(0,0,width,height);	
		System.out.println("CONTINUING PROCESS ------------" + processName);
//		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		drawBackground(g2);		
		drawBar(g2);
		drawMessages(g2);
	}
	
	public void drawBackground(Graphics2D g2)
	{		
		g2.setColor(new Color(0,128,255));
		g2.fillRect(0,0, width, height);
	}
					   
	public void drawBar(Graphics2D g2)
	{
		g2.setColor(Color.black);
		g2.fillRect(xmargin/2,ymargin/2, width-xmargin, height-ymargin);
		
		g2.setColor(barColor);
		g2.fillRect(xmargin/2,ymargin/2, (int)((width-xmargin)*percent/100), height-ymargin);
	}
	
	public void drawMessages(Graphics2D g2)
	{
		g2.setColor(Color.white);
		g2.drawString(processName, xmargin/2, 20);
		g2.drawString(percent + " %", width/2, height-ymargin/2+20);
	}
	
	public void update(Graphics g)		//Avoid from blinking
	{
		paintComponent(g);
	}		
	
}