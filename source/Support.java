//
// Core Program for TETDM 
// Support.java Version 0.49
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

//import source.*;
import source.TextData.*;
import source.Utility.*;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.GroupLayout.Alignment;


public class Support extends JPanel implements ActionListener
{	
	JToggleButton countbutton;
	JComboBox cb = new JComboBox();
	JScrollBar scroll_bar;
	JPanel upperpanel,basepane;
	CardLayout layout;
	JTextArea touch_task;
	
    int width;
	int frameSize = 30;
	int fontSize = frameSize - 15;
	int door_x,door_y;

	public int oldSizeX, oldSizeY;
	int sizeX,sizeY,testpane_sizeY=0;
	public double changeX, changeY;
	int scroll_pos = 0;
	int pressPointx=0,pressPointy=0;
	int movePointx=0,movePointy=0;

	Control control;
	Color base_color = new Color(0x00,0xff,0xff);
	Color click_color = new Color(0x41,0x69,0xE1);
	Color clear_color = Color.pink;
	Color thema_color = new Color(0xf0,0xff,0xff);

	PopUpForSupport popForSupport2[];
	
	FlagBox flagbox;
	Image [] door = new Image[3];
	
	String pic_ad;
	Make_Task_Data data;
	Task_Panel testpane;
//	String classstage [] = {"beginner","intermediate","senior"};
	String classstage [] = {"Beginner"};
	int classstage_num = 0;
	Task_Panel task_list_panel [] = new Task_Panel[classstage.length];
	Make_Task_Data makedata [] = new Make_Task_Data [classstage.length];
//	Count_Panel countpanel;
	
//	String label_name [] = {"Let's try!"," Rank	: "," exp	: ","	"};
	String label_name [] = {"Let's try!"," SKILL LEVEL	: "," EXP	: "};
	JTextArea texttitle[] = new JTextArea[label_name.length];
	
    String japaneseWords[];
    
	Support(Control control,FlagBox flagbox,int sizeX,int sizeY)
	{
		this.control = control;
		this.flagbox = flagbox;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
        width = getWidth();
        pic_ad = control.absolutePath + "source" + File.separator;
		setLayout(new BorderLayout());
		
		for(int i=0;i<3;i++){
			door[i] = Toolkit.getDefaultToolkit().getImage(pic_ad + "operate" + File.separator + "door"+(i+1)+".png");
		}
		
        japaneseWords = FILEIO.TextFileAllReadCodeArray(new File(pic_ad+"JapaneseSupport.txt"));
        
		this.addMouseListener(new MyMouseL());
		this.addMouseMotionListener(new MyMouseL());
		
		for(int i=0;i<classstage.length;i++){
			makedata[i] = new Make_Task_Data(control, this, i);
		}
//		data = new Make_Task_Data(control);
		data = makedata[classstage_num];
		
//		scroll_bar = new JScrollBar(JScrollBar.VERTICAL, 0, 10, 0, (data.thema_num + data.task_num)/3*2);
//		scroll_bar.setBounds(sizex/6,sizey/3*2,sizex/2,10);
//		scroll_bar.addAdjustmentListener(new MyScroll());
	//	add("East",scroll_bar);
		
		upperpanel = new JPanel();
///		upperpanel.setLayout(new GridLayout(4,1));
		upperpanel.setLayout(new BoxLayout(upperpanel,BoxLayout.Y_AXIS));  ////////////////////////////
		add("North",upperpanel);
		
///////////////////////////
		
		cb.addItem(classstage[0]);
//		cb.addItem(classstage[1]);
//		cb.addItem(classstage[2]);
		cb.addActionListener(this);
////////////////cb.addItem("new");
//////cb.setEnabled(false);
		
		countbutton = new JToggleButton("count",false);			
		countbutton.addActionListener(this);
		countbutton.setFont(new Font("Dialog", Font.BOLD, fontSize));
//		countbutton
		
		JPanel upper_top_panel = new JPanel();
		upper_top_panel.setLayout(new GridLayout(1,3));
		upper_top_panel.setBackground(Color.white);
		upper_top_panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.blue));
		
		for(int i=0;i<label_name.length;i++){
			texttitle[i] = new JTextArea(label_name[i]);
			texttitle[i].setFont(new Font("Dialog", Font.BOLD, fontSize+5));
			texttitle[i].setLineWrap(true);
			texttitle[i].setEnabled(false);
			texttitle[i].setDisabledTextColor(Color.black);
			if(i == 0){
				upper_top_panel.add(texttitle[i]);
				upper_top_panel.add(cb);
//				upper_top_panel.add(countbutton);
				upperpanel.add(upper_top_panel);
			}else {
				texttitle[i].setBackground(new Color(0xad, 0xff, 0x2f));
				upperpanel.add(texttitle[i]);
			}
		}	
		///////////////////////////

//		touch_task = new JTextArea("Mission List");
		touch_task = new JTextArea(japaneseWords[0]);
		touch_task.setFont(new Font("Dialog", Font.BOLD, fontSize+5));
		touch_task.setLineWrap(true);
		touch_task.setEnabled(false);
		touch_task.setBackground(Color.WHITE);
		touch_task.setDisabledTextColor(Color.black);
		touch_task.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.black));
		upperpanel.add(touch_task);
	
		popForSupport2 = new PopUpForSupport[data.task_num];
		
		basepane = new JPanel();
		layout = new CardLayout();
		basepane.setLayout(layout);
		add("Center",basepane);
		
/*		
		testpane = new Task_Panel(control,data,this,sizeX,sizeY,0);
		testpane.addMouseListener(new MyMouseL());
		testpane.addMouseMotionListener(new MyMouseL());
//		add("Center",testpane);
//		basepane.add(testpane,classstage[0]);
 */
		
		for(int i = 0; i < classstage.length; i++){
			data = makedata[i];
			check_can_open();
			task_list_panel[i] = new Task_Panel(control,data,this,sizeX,sizeY,i);
			task_list_panel[i].addMouseListener(new MyMouseL());
			task_list_panel[i].addMouseMotionListener(new MyMouseL());
			basepane.add(task_list_panel[i],classstage[i]);
		}

		data = makedata[classstage_num];
		
//		countpanel = new Count_Panel(control, this);
//		countpanel.addMouseListener(new MyMouseL());
//		countpanel.addMouseMotionListener(new MyMouseL());
//		basepane.add(countpanel,"countpanel");
		
		set_position();
		oldSizeX = sizeX;
		oldSizeY = sizeY;
		check_exp();
	}
	
	//SAVE ACTION LOG
    String logFilename;
    public void writeActionLog(String data)
    {
        logFilename = control.absolutePath+"ACTIONLOG";
        try
        {
//            BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));
            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "Shift_JIS"));
			
            bw1.write(String.format("%.2f",control.getDiffTime())+" SUPPORT "+data+System.getProperty("line.separator"));
            bw1.close();
        }
        catch(Exception e){
            System.out.println("File Writing ERROR ACTIONLOG");
        }
    }
    
	public void actionPerformed(ActionEvent e)
    {
		for(int i=0;i < classstage.length; i++)
        {
			if((String)cb.getSelectedItem() == classstage[i])
            {
				classstage_num = i;
				layout.show(basepane,classstage[classstage_num]);
				data = makedata[classstage_num];
			}
		}
		
		if(e.getSource() == countbutton)		
		{
			if(countbutton.isSelected())
			{
				layout.show(basepane,"countpanel");
			} else {
				layout.show(basepane,classstage[classstage_num]);
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////	
	class MyScroll implements AdjustmentListener
	{
		public void adjustmentValueChanged(AdjustmentEvent e){
			if (e.getAdjustable() == scroll_bar){
				scroll_pos = scroll_bar.getValue();
				set_position();
				repaint();
			}
		}
	}
	int click_task = -1;
	
	class MyMouseL implements MouseListener,MouseMotionListener
	{
		public void mouseClicked(MouseEvent me){}
        
		public void mouseMoved(MouseEvent me)
        {
			movePointx = me.getX();
			movePointy = me.getY();
			
			click_task = -1;
	//		Point ptt = getMousePosition();
//			System.out.println(ptt.x +"zzzzzzzzzzzzzzzzzzzz " + ptt.y);
			click_task = check_item_area(movePointx,movePointy);			
			if(click_task != -1){
				touch_task.setText(data.task[click_task].goal);
			}
			set_position();
			repaint();
		}
        
		public void mousePressed(MouseEvent me){
			pressPointx = me.getX();
			pressPointy = me.getY();
			click_task = -1;
			click_task = check_item_area(pressPointx,pressPointy);			
			
			if(click_task != -1)
            {
				data.task[click_task].click_num += 1;
                
                writeActionLog("Question "+(click_task+1)+": ");
                
//				System.out.println(data.task[click_task].no);
				if(data.task[click_task].click_num % 2 == 1) 
					data.task[click_task].color = click_color;
				else 
					data.task[click_task].color = base_color;
				repaint();
				make_window(click_task);
				touch_task.setText(data.task[click_task].goal);
			}
			/*
			click_task = check_thema_area(pressPointx,pressPointy);		
			
			if(click_task != -1){
				data.theme[click_task].click_num += 1;
//				System.out.println(data.task[click_task].no);
				set_position();
				repaint();
			}
			*/
//			flagbox.check_flag(4);
//			System.out.println(">>>>> "+ flagbox.flags.clear[5]);
		}
		public void mouseDragged(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}			
		public void mouseEntered(MouseEvent me){}	//Enter an area	
		public void mouseExited(MouseEvent me){}	//Exit an area
	}

	boolean change = false;
	public boolean getPanelSize()
	{
		change = false;
		
//		oldSizeX = sizeX;
//		oldSizeY = sizeY;		
		sizeX = getWidth();
		sizeY = getHeight();
//////////		testpane_sizeY = testpane.getHeight();
		testpane_sizeY = basepane.getHeight();
		
		if(sizeX != oldSizeX || sizeY != oldSizeY)
			change = true;
		
		changeX = changeY = 1.0;
		if(oldSizeX != 0)
			changeX = sizeX / (double)oldSizeX;
		if(oldSizeY != 0)
			changeY = sizeY / (double)oldSizeY;
	
		return change;
	}

	public void resizeData()
    {		
		frameSize = 30;
		fontSize = frameSize - 15;
		
		if(change == true){
			frameSize *= changeY;
			fontSize *= changeY;
			set_position();
		}
//		changeY = 1;
	}
	
	public int check_item_area(int mouse_x, int mouse_y)
	{
		for(int i=0;i<data.task_num;i++)
        {
			if(mouse_y > data.task[i].posy + frameSize && mouse_y < data.task[i].posy + frameSize + door_y)
            {
				if(mouse_x > data.task[i].posx - frameSize/2 && mouse_x < data.task[i].posx + door_x - frameSize/2)
                {
		//			System.out.println(data.task[i].goal);
					if(data.task[i].canopen == true)
						return i;
				}
			}
		}
		return -1;
	}
	
	public int check_thema_area(int mouse_x,int mouse_y)
	{
		/*
		for(int i=0;i<data.thema_num;i++){
			if(frameSize*data.theme[i].posy<mouse_y && mouse_y<frameSize*(data.theme[i].posy+1)){
	//			System.out.println(data.theme[i].goal);
		//		return data.theme[i].posy;
				return i;
			}
		}
		*/
		return -1;
	}
	
	public void make_window(int num)
	{
		int x,y,width;
		x = width = getWidth();
		y = getHeight();
		if(x > 950)x = 950;
		if(y > 900)y = 900;
		Point position = getLocationOnScreen();
		
		if(data.task[num].click_num % 2 == 1){
			popForSupport2[num] 
//				= new PopUpForSupport((int)((width-x)/2), (int)position.getY()-20, x, y, control, num ,data, this);
            = new PopUpForSupport((int)width+5, (int)position.getY()-20, x, y, control, num ,data, this);
		}
		else {
			
			if(popForSupport2[num].p != null)
				popForSupport2[num].p.dispose();			
			
//			popForSupport2[num].pop_detail.dispose();
			
			popForSupport2[num].dispose();
		}
	}
	
	void set_position()
	{	
		for(int i=0;i<data.thema_num;i++)
        {
			data.theme[i].posy = testpane_sizeY / (data.thema_num) * (i);
		}
        
	    int iw=door[0].getWidth(this);
	    int ih=door[0].getHeight(this);
        
//		door_y = data.theme[1].posy - data.theme[0].posy - frameSize*2;
        
		door_y = data.theme[1].posy - data.theme[0].posy;
        door_x = iw * door_y/ih;
	    
//	    door_x = door_x/3*2;
//	    door_y = door_y/3*2;


        
//		door_x = iw ;
//		door_y = ih ;
		
		for(int i=0;i<data.task_num;i++){
			data.task[i].posy = 0;
			data.task[i].posy += data.theme[data.task[i].level].posy;
		//	data.task[i].posy += frameSize;
			int task_pos = 1;
	//		if(sizeX != 0)
			task_pos = sizeX / (data.theme[data.task[i].level].num + 1);
			data.task[i].posx = data.task[i].id*task_pos -door_x/2;
		}
//		door_x,door_y
		/*
		for(int i=0;i<data.thema_num;i++){
			data.theme[i].posy -= scroll_pos;
		}	
		for(int i=0;i<data.task_num;i++){
			data.task[i].posy -= scroll_pos;
		}
		*/
	}

	void preserve_text(){
		for(int i=0;i < classstage.length; i++){
			data = makedata[i];
			data.preserve_task_list_text(i);
		}
	}
	
    void setAnsweredFlagTrue(int level, int number)
    {
        if(makedata[level].task[number].canopen == true)
        {
            makedata[level].task[number].clear = true;
            writeActionLog("Question "+(number+1)+": clear");
            check_exp();
            repaint();
        }
    }
    
	int total_exp = 0;
	
	int getSkillLevel(int exp)
	{
		int sum = 0;
		
		for(int i=0;;i++)
			if(exp < sum)
				return i;
			else
				sum += (i+1)*100;
	}
	
	void check_exp(){	//check total exp
		total_exp = 0;
		for(int i=0;i < classstage.length; i++){
			data = makedata[i];
			for(int j=0;j<data.task_num;j++){
				if(data.task[j].clear == true){
					total_exp += data.task[j].exp;
				}
			}
		}
		data = makedata[classstage_num];
		texttitle[1].setText(label_name[1] + getSkillLevel(total_exp));
		texttitle[2].setText(label_name[2] + total_exp);
		
		check_can_open();
	}
	
	void check_can_open(){	//check can choice task
		for(int i = 0; i<data.task_num;i++){
			if(data.task[i].canopen == true)continue;
			else {
				if(data.task[i].open == 0)data.task[i].canopen = true;
				for(int j=0;j<data.task_num;j++){
					if(data.task[j].no == data.task[i].open){
						if(data.task[j].clear == true)
							data.task[i].canopen = true;
					//				System.out.println(i+"	: "+data.task[data.task[i].open].clear +"	:	"+ data.task[i].open);
					}
				}
			}
		}
	}
	
}

class Task_Panel extends JPanel
{
	Make_Task_Data data;
	Support support;
	Control control;
	Image [] door = new Image[4];
	String pic_ad;
	FlagBox flagbox;
	int class_num = -1;
	
	Task_Panel(Control control,Make_Task_Data data, Support support,int sizeX,int sizeY,int class_num)
	{	
		this.control = control;
		this.data = data;
		this.support = support;
		this.class_num = class_num;
		
		pic_ad = control.absolutePath + "source" + File.separator;

		for(int i=0;i<4;i++){
			door[i] = Toolkit.getDefaultToolkit().getImage(pic_ad + "operate" + File.separator + "door"+(i+1)+".png");
		}
	}

	void draw_background1(Graphics2D g2)
	{
		boolean change = support.getPanelSize();
		if(change)support.resizeData();
        
//		g2.setColor(new Color(0xE0,0xFF,0xFF));
//		g2.setColor(new Color(0x33,0x33,0x66));
		g2.setColor(new Color(0xad, 0xff, 0x2f));
//		if(class_num == 0)g2.setColor(Color.cyan);	
//		if(class_num == 1)g2.setColor(Color.red);
//		if(class_num == 2)g2.setColor(Color.blue);
		
        g2.fillRect(0,0, support.sizeX, support.sizeY);
	}
	
	int space = 5;
	
	void draw_letter1(Graphics2D g2)
	{	
		for(int i=0;i<data.task_num;i++){
			if(data.task[i].posx == -1)continue;
			g2.drawImage(data.task[i].image, data.task[i].posx,data.task[i].posy+support.frameSize, support.door_x, support.door_y, this);
		}
		for(int i=0;i<data.thema_num;i++){			
			if(data.theme[i].posy == -1)
                continue;
			g2.setColor(Color.white);
			g2.fillRect(0,data.theme[i].posy, support.sizeX, support.frameSize);
			g2.setColor(Color.black);
			g2.drawRect(0,data.theme[i].posy, support.sizeX, support.frameSize);			
			g2.setColor(Color.black);
			g2.setFont(new Font("Dialog", Font.BOLD, support.fontSize+2));
			g2.drawString("MISSION "+data.theme[i].level+":  "+data.theme[i].goal,10,data.theme[i].posy+support.frameSize/3*2);
		}		
	}
	
	void set_image1(Graphics2D g2,int i)
	{	
		data.task[i].image = door[0];	//can't open
		if(data.task[i].canopen == true)data.task[i].image = door[1];//can open
		if(i == support.click_task)data.task[i].image = door[3];	// click or touch now
		else if(data.task[i].click_num % 2 == 1) data.task[i].image = door[3]; //click(open) now
		if(data.task[i].clear == true)data.task[i].image = door[2]; //clear
		
	}

	void set_images1(Graphics2D g2)
	{	
		for(int i=0;i<data.task_num;i++){
			set_image1(g2,i);
		}
	}
    
	public void paintComponent(Graphics g)
	{        
		Graphics2D g2 = (Graphics2D)g;	
		draw_background1(g2);	//Set background
		set_images1(g2);
		draw_letter1(g2);
	}
	
	public void update(Graphics g)
	{
		paintComponent(g);
	}

}
/*
class Count_Panel extends JPanel
{
	Support support;
	FlagBox flagbox;
	String pic_ad = "";
	Image countback;
	String counter_name[] = {"tool_num","mining_num","visual_num","focus_num","option_num"};
	int counter_threshold [] = {10,10,10,20,40};		//test threshold
	int counter_num [] = {4,24,21,46,90};				//test count times

	int space = 10;
	int round = 10;
	int flamex = 0, flamey = 0;
	int flamewidth = 0, flameheight = 0;

	
	Count_Panel(Control control, Support support)
	{
		this.support = support;	
		pic_ad = control.absolutePath + "source" + File.separator + "operate" + File.separator + "countback.png";
		countback = Toolkit.getDefaultToolkit().getImage(pic_ad);
	}

	void draw_background1(Graphics2D g2)
	{
		boolean change = support.getPanelSize();
		if(change)support.resizeData();
        
		g2.setColor(new Color(0xff,0xff,0x99));		
        g2.fillRect(0,0, support.sizeX, support.sizeY);
	}
	
	void draw_count_flames(Graphics2D g2)
	{
		for(int i=0; i<counter_name.length; i++){
			//draw flame
			flamex = space;
			flamey = support.testpane_sizeY/counter_name.length * i + space ;
			flamewidth = support.sizeX - space*2;
			flameheight = support.testpane_sizeY/counter_name.length - space*2;
			g2.drawImage(countback, flamex, flamey, flamewidth, flameheight , this);
			
			//draw counter name
			g2.setColor(Color.black);
			g2.fillRoundRect(flamex -1 , flamey + flameheight - support.fontSize*2-1 , flamewidth , support.fontSize*2, round, round);
			g2.setColor(Color.white);
			g2.fillRoundRect(flamex, flamey + flameheight - support.fontSize*2 , flamewidth - space/2 , support.fontSize*2 - space/2, round, round);
			g2.setColor(Color.black);
			g2.setFont(new Font("Dialog", Font.BOLD,support.fontSize+2));
			g2.drawString(counter_name[i], flamex + space, flamey + flameheight - space);
		}
	}
	
	void draw_count_view(Graphics2D g2)
	{
		for(int i=0; i<counter_name.length; i++){

			float[] dash = { 20.0f, 5.0f,3.0f,5.0f };
			float[] dash2 = {10.0f, 5.0f,5.0f,5.0f };
			float[] dash3 = {15.0f, 2.0f,5.0f,2.0f };
			
			int ovalx = 0, ovaly = 0;
			int ovalwidth = 0, ovalheight = 0;
			
			flamey = support.testpane_sizeY/counter_name.length * i + space ;
			
			ovalx = flamex + flamewidth - flameheight*4/5;
			ovaly = flamey + flameheight  - flameheight*4/5;
			ovalwidth = flameheight*4/5;
			ovalheight = flameheight*4/5;
			
			int a = 20;
			g2.setColor(Color.darkGray);
			g2.setStroke(new BasicStroke(1.0f));
			g2.fillOval(ovalx, ovaly, ovalwidth, ovalheight);
			
//			int counter_threshold [] = {10,10,10,20,40};		//test threshold
//			int counter_num [] = {4,24,21,46,90};				//test count times
			
			int count_rest = counter_num[i]%counter_threshold[i];
			int count_level = (int)(counter_num[i]/counter_threshold[i]);
			double count_per = (double)count_rest/(double)counter_threshold[i];
//			System.out.println(i+" rest : "+count_rest +" level : " +count_level);
			
			double arcround = (double)((double)count_per * (double)360);
			
			g2.setColor(Color.cyan);
			g2.fillArc(ovalx, ovaly, ovalwidth, ovalheight, 0, (int)arcround);

			g2.setColor(Color.blue);
//			g2.setStroke(new BasicStroke(2.0f,BasicStroke.JOIN_ROUND,
//						BasicStroke.CAP_BUTT,1.0f,dash,0.0f));
			g2.drawArc(ovalx+3, ovaly+3, ovalwidth-2*3, ovalheight-2*3, 0, (int)arcround);
			
			g2.setColor(Color.black);
			g2.setFont(new Font("Dialog", Font.BOLD,support.fontSize+5));
			g2.drawString("Level :	"+count_level,  flamewidth/9, flamey + flameheight/3 );
			g2.setFont(new Font("Dialog", Font.BOLD,support.fontSize+2));
			g2.drawString("next level with another "+count_rest + "times !", flamewidth/7, flamey + flameheight/3*2);
			g2.setColor(Color.red);
			g2.setFont(new Font("Dialog", Font.BOLD,flameheight/3));
			g2.drawString((int)(count_per*100) +" %", ovalx - ovalwidth/4, ovaly + ovalheight/5*4);
		}
//		g2.setStroke(new BasicStroke(1.0f));
	}
    
	public void paintComponent(Graphics g)
	{        
		Graphics2D g2 = (Graphics2D)g;	
		draw_background1(g2);	//Set background
		draw_count_flames(g2);
		draw_count_view(g2);
	}
	
	public void update(Graphics g)
	{
		paintComponent(g);
	}

}
*/

class Pic_Panel extends JPanel
{
	int sizeX,sizeY;
	Image arrowImage;
	
	Pic_Panel(String pic_ad)
	{	
		arrowImage = Toolkit.getDefaultToolkit().getImage(pic_ad);
	}

    public void getPanelSize()
	{
		sizeX = getWidth();
		sizeY = getHeight();
	}
    
    void draw_background(Graphics2D g2)
	{
		getPanelSize();
		g2.setColor(Color.white);
        g2.fillRect(0,0, sizeX, sizeY);
	}
    
    void draw_image(Graphics2D g2)
    {
	    int iw=arrowImage.getWidth(this);
	    int ih=arrowImage.getHeight(this);
		if(iw > ih)
			g2.drawImage(arrowImage, 0, 0, sizeX, ih*sizeX/iw, this);
		else
			g2.drawImage(arrowImage, 0, 0, iw*sizeY/ih, sizeY, this);		    	
    }
	
	public void paintComponent(Graphics g)
	{        
		Graphics2D g2 = (Graphics2D)g;	
		draw_background(g2);	//Set background
		draw_image(g2);
	}
	
	public void update(Graphics g)
	{
		paintComponent(g);
	}
}