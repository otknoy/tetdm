
//
// Core Program for TETDM 
// PopUpForSupport.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import source.TextData.*;
import source.Utility.*;

import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;

public class PopUpForSupport extends JFrame
{
	Container pane = getContentPane();
	Control control;
	Make_Task_Data data;
	int flameSize = 30;
	int fontSize = flameSize - 15;
	JTextArea texttitle;
	JPanel theme_panel,button_panel,basepane;
	
	///////////popup_picture/////////////
	Pic_Panel pic_panel;
	String file_add = "";

	///////////popup_task_detail/////////////
	JButton button[];
	JTextArea area;
	int page = 0;
	int X,Y,width,height,task_num;
	PopUpForSupport pop_detail, pop_answer;
	PopUpForSupport p;
	JPanel process_panel;
	
	///////////popup_task_answer/////////////
	JLabel label[] ;
	JTextField textanswer[];
	JTextArea textquestion[];
	JPanel question_panel;
		
	void init_pane(int X, int Y, int width, int height, Control control){
		pane.setLayout(new BorderLayout());
		
		setBounds(X, Y, width, height);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setAlwaysOnTop(true);
		pane.setBackground(Color.white);
		
		
        this.control = control;
	}
	
	void set_text_title(int task_num){
//		texttitle = new JTextArea ("< theme >");
		texttitle = new JTextArea ();
		
//		for(int i=0;i<data.task_num;i++){
//			if(data.task[i].no == task_num){
//				texttitle.append("\n");
				texttitle.append(data.task[task_num].goal);
				texttitle.setFont(new Font("Dialog", Font.BOLD, fontSize+5));
				texttitle.setLineWrap(true);
				texttitle.setEnabled(false);
				texttitle.setBackground(new Color(0xad, 0xff, 0x2f));
				texttitle.setDisabledTextColor(Color.black);
//				break;
	//		}
//		}
		theme_panel.add(texttitle);
	}
	
	Support support;
	
	///////////popup_task_list/////////////
	PopUpForSupport(int X, int Y, int width, int height, Control control,FlagBox flagbox)
	{		
		super("Support");
		init_pane( X, Y, width/2, height, control);
		support = new Support(control,flagbox,width/2,height);
		pane.add(support);/*FOR TEST*/
	//	addWindowListener(this);
        this.addWindowListener(new MyWindowL());
		setVisible(true);
	}
	
	class MyWindowL extends WindowAdapter {
		public void windowClosing(WindowEvent e)
		{
			support.preserve_text();
		}
		public void windowClosed(WindowEvent e)
		{
			support.preserve_text();
		}
	}
		
	///////////popup_picture/////////////	
	PopUpForSupport(int X, int Y, int width, int height, Control control,Make_Task_Data data,String pic_name)
	{	
		super("Support3");
		init_pane( X, Y, width, height/2, control);
		this.data = data;
		this.file_add = data.file_add + pic_name;
		
		pic_panel = new Pic_Panel(file_add);
		pane.add(pic_panel);
		setVisible(true);
	}
	
	Support supporttaskdetail;
	///////////popup_task_detail/////////////
	PopUpForSupport(int X, int Y, int width, int height, Control control, int task_num,Make_Task_Data data, Support support)
	{	
		super("Support2");
		this.task_num = task_num;
		this.data = data;		
		this.width = width;
		this.Y = Y;
		this.X = X;
		this.height = height;
		supporttaskdetail = support;
		
		init_pane( 0, Y, width, height/2, control);/////////////
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
       	data.read_task_details(task_num);
		view_task_detail_panel();
		setVisible(true);
		pop_detail = this;

		if(data.task[task_num].max_que > 0){
			p = 
//					new PopUpForSupport((int)((width-X)/2), Y, width, height,control, task_num,data,1, supporttaskdetail);
            new PopUpForSupport(0, Y, width, height,control, task_num,data,1, supporttaskdetail);
		}
	}
	
	void view_task_detail_panel()
    {
		basepane = new JPanel();
		basepane.setLayout(new BorderLayout());
		pane.add(basepane);
		
		theme_panel = new JPanel();

		theme_panel.setLayout(new BoxLayout(theme_panel,BoxLayout.Y_AXIS));  ////////////////////////////
		basepane.add("North",theme_panel);
		
		set_text_title(task_num);
		
		process_panel = new JPanel();
		process_panel.setLayout(new BorderLayout());
		basepane.add("Center",process_panel);
		
		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(2,2));
		basepane.add("South",button_panel);
		
		String button_name[] = {"back <","next >","figure","close"};
		
		button = new JButton [button_name.length];
	    for(int i=0;i<button_name.length;i++){
	    	button[i] = new JButton(button_name[i]);
	    	button[i].addActionListener(new MyBtn());
	    	button_panel.add(button[i]);
	    }

	    button[0].setEnabled(false);
	    if(data.task[task_num].max_page == 0)button[1].setEnabled(false);
	    
		area = new JTextArea("");
		area.setLineWrap(true);
		area.setFont(new Font("Dialog", Font.BOLD, fontSize));
		set_text();
		
		JScrollPane scroll_panel;
		scroll_panel = new JScrollPane(area);
		process_panel.add(scroll_panel);
	}

	void set_text()
    {
		area.setText("");

		for(int i=0;i<data.task[task_num].line_max;i++)
        {
			if(page != Integer.parseInt(data.task[task_num].p_str[i][0]))continue;
			if(data.task[task_num].p_str[i][1].equals("end,")){
				data.task[task_num].clear = true;
                writeActionLog("Question "+(task_num+1)+": clear");
				supporttaskdetail.check_exp();////////////////////////////////////////
				break;
			}
			area.append(data.task[task_num].p_str[i][1]);
			area.append(System.getProperty("line.separator"));
		}
        
		if(data.task[task_num].page_fig[page] == 0)
            button[2].setEnabled(false);
		else
            button[2].setEnabled(true);
        
        area.setCaretPosition(0);
	}
    
	Point position;
	
	class MyBtn implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{		
			if(e.getSource() == button[0])//back		
			{
				page -= 1;
				if(page <= 0){
					page = 0;
					button[0].setEnabled(false);
				}
				if(page != data.task[task_num].max_page)button[1].setEnabled(true);
				set_text();
			}		
			if(e.getSource() == button[1])//next		
			{
				page += 1;
				if(page >= data.task[task_num].max_page){
					page = data.task[task_num].max_page;
					button[1].setEnabled(false);
				}
				if(page != 0)button[0].setEnabled(true);
				set_text();
			}		
			if(e.getSource() == button[2])//figure		
			{
				PopUpForSupport popForSupport3[];
				popForSupport3 = new PopUpForSupport[data.task[task_num].page_fig[page]];
				position = getLocationOnScreen();
				
				int figid = 0;
				
				for(int k=0;k<page;k++){
					figid += data.task[task_num].page_fig[k];
				}
				
				for(int j=0;j<data.task[task_num].page_fig[page];j++){ // among figure in page
					figid += j;
					popForSupport3[j] 
							= new PopUpForSupport(0, (int)position.getY()-20, width, height, control,data,data.task[task_num].fig_name[figid]);
				}
			}		
			if(e.getSource() == button[3])//close	
			{
				if(p != null)
					p.dispose();
				if(data.task[task_num].click_num %2 == 1)data.task[task_num].click_num += 1;
					pop_detail.dispose();
			}		
			repaint();
		}	
	}
	
	Support supporttaskanswer;
	///////////popup_task_answer/////////////
	
	PopUpForSupport(int X, int Y, int width, int height, Control control, int task_num,Make_Task_Data data,int ans_num, Support support)
	{	
		super("Support2");
		this.task_num = task_num;
		this.data = data;		
		supporttaskanswer = support;
		
//		init_pane( X, Y, width, height/4, control);		/////////////
		init_pane( X, Y+height/2, width, calculate_size(width), control); /////////////
       	data.read_task_details(task_num);
		view_task_answer_panel();
		pop_answer = this;
		setVisible(true);
		getContentPane().setPreferredSize(new Dimension(width, textanswer[0].getSize().height*calculate_size(width)));	/////////////
		pack();	/////////////
	}
	
	int calculate_size(int width){/////////////
		int line_num = 0;

		    for(int i=0;i<data.task[task_num].max_que;i++)
		    	line_num += data.task[task_num].que_ans[i][0].length()/(width/fontSize);

		    return (line_num+6);
	} 
	
	void view_task_answer_panel(){
		basepane = new JPanel();
		basepane.setLayout(new BorderLayout());
		pane.add(basepane);
		
		theme_panel = new JPanel();
		theme_panel.setLayout(new BoxLayout(theme_panel,BoxLayout.Y_AXIS));  ////////////////////////////
		theme_panel.setBackground(new Color(0xad, 0xff, 0x2f));
		basepane.add("North",theme_panel);
		
		set_text_title(task_num);

		question_panel = new JPanel();
		question_panel.setLayout(new BoxLayout(question_panel,BoxLayout.Y_AXIS));  ////////////////////////////
		basepane.add("Center",question_panel);

		textquestion = new JTextArea [data.task[task_num].max_que];
		textanswer = new JTextField [data.task[task_num].max_que];
		
	    for(int i=0;i<data.task[task_num].max_que;i++){
	    	textquestion[i] = new JTextArea(data.task[task_num].que_ans[i][0]);
//	    	textquestion[i].setColumns(10);
	    	textquestion[i].setFont(new Font("Dialog", Font.BOLD, fontSize));
	    	textquestion[i].setLineWrap(true);
	    	textquestion[i].setEnabled(false);
//	    	textquestion[i].setBackground(Color.cyan);
	    	textquestion[i].setBackground(new Color(0xe0,0xff,0xff));
	    	textquestion[i].setDisabledTextColor(Color.black);
			question_panel.add(textquestion[i]);
	    	textanswer[i] = new JTextField();
	    	textanswer[i].setColumns(10);
	    	textanswer[i].addActionListener(new MyBtn2());
	   	    textanswer[i].setFont(new Font("Dialog", Font.BOLD, fontSize));
	   	    question_panel.add(textanswer[i]);
	    }
		
		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(1,2));
		basepane.add("South",button_panel);
		
		String button_name[] = {"check","close"};
		
		button = new JButton [button_name.length];
	    for(int i=0;i<button_name.length;i++){
	    	button[i] = new JButton(button_name[i]);
	    	button[i].addActionListener(new MyBtn2());
	    	button_panel.add(button[i]);
	    }		
	}
	
	//SAVE ACTION LOG
    String logFilename;
    public void writeActionLog(String data)
    {
        logFilename = control.absolutePath+"ACTIONLOG";
        try
        {
            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "Shift_JIS"));
//            BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));
			
            bw1.write(String.format("%.2f",control.getDiffTime())+" SUPPORT "+data+System.getProperty("line.separator"));
            bw1.close();
        }
        catch(Exception e){
            System.out.println("File Writing ERROR ACTIONLOG");
        }
    }
    
	class MyBtn2 implements ActionListener
	{
        //SAVE ACTION LOG
        String logFilename;
        public void writeActionLog(String data)
        {
            logFilename = control.absolutePath+"ACTIONLOG";
            try
            {
                BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "Shift_JIS"));
                
                bw1.write(String.format("%.2f",control.getDiffTime())+" SUPPORT "+data+System.getProperty("line.separator"));
                bw1.flush();
                bw1.close();
            }
            catch(Exception e){
                System.out.println("File Writing ERROR ACTIONLOG");
            }
        }
        
		public void actionPerformed(ActionEvent e)
		{		
			if(e.getSource() == button[0])//check
			{
				for(int i=0;i<data.task[task_num].max_que;i++){
					String str = textanswer[i].getText();
							
                    writeActionLog("Answer for "+(task_num+1)+"-"+(i+1)+": "+str);
                    
					if(str.equals(data.task[task_num].que_ans[i][1])){
						textanswer[i].setText("clear");
					    textanswer[i].setEnabled(false);
						data.task[task_num].que_ans[i][2] = "T";
					}
					else {
						if(data.task[task_num].que_ans[i][2] != "T")
							textanswer[i].setText("miss");
					}
					//	text.setText("");
					//	text.setColumns(10);
				}
				int c = 0;
				for(int i=0;i<data.task[task_num].max_que;i++){

					if(data.task[task_num].que_ans[i][2].equals("T"))c++;
				}
				if(c == data.task[task_num].max_que){
					data.task[task_num].clear = true;
                    writeActionLog("Question "+(task_num+1)+": clear");
					supporttaskanswer.check_exp();////////////////////////////////////////
				}
			}
			if(e.getSource() == button[1])//close
			{
				//		data.task[task_num].click_num += 1;
				pop_answer.dispose();
			}
		}
	}
		
	class MyWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e)
		{
	//		control.writeActionLog("CLOSE-MODULE-SELECT-WINDOW");
		}
	}
}
