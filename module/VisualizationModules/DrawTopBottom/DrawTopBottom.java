//
// Visuzalization module for TETDM 
// DrawTopBottom.java Version 0.30
// Copyright(C) 2011 System Interface Laboratory (Hiroshima City University) ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//


package module.VisualizationModules.DrawTopBottom;


import source.*;
import source.TextData.*;
import source.Utility.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;



public class DrawTopBottom extends VisualizationModule implements MouseListener, MouseMotionListener
{
	String inJapanese[];

	ImageIcon icon;
	Image image;
	
	DecimalFormat df_a, df_b;	
	
	boolean moving;			//Flag for node moving
	boolean dragging;		//Flag for field moving	
	
	int touch_num;				//Node Number of being touched
	
    
	double t_p_value,t_m_value,t_value,b_p_value,b_m_value,b_value,tbar,bbar;
	int wakecount;
	int segmentCheck[];
	int newNum;
	int newCheck[];
	int sort_segment[];	// now the same as i
	
	double copy_cond[][];
	int link[][];	
	int copy_link[][];
    int check_link[][];
    
    double over_cond[][];
	
	String scoreWord, segmentWord;

	double max_score;
	
	double kariscore[][];
	
	NewMapData2 map;
	

	
	//mouse
	int mousex, mousey;			//Coordinates for a mouse
	int old_mousex, old_mousey;	//Last Coordinates for a mosue
	int dx, dy;					//Distance for mouse moving	
	int oldTouchNumber;	
	
/*	


	int max_link[][];
    int click_link;
	int check;
	int click_count=0;
	int numA=0,numB=0;
	int aaaa=0;
    String change_seg;
	//For objects operation

	


	int flag=0;
    char getData;
	

	JPanel pane;
	JFrame panel1,wordFrame[][];


	String top,bottom,commonlink;
	
	int sort_num[];

	double kari;
	String sameWord[];


	*/
	
	public DrawTopBottom()
	{
		setModuleID(22);
        dataNumbers = new int[]{0,0,0,0,    // b,i,d,S
            0,0,0,0,    // bA,iA,dA,SA
            0,0,0};     // bA2,iA2,dA2
		setToolType(1);
	}

	public void initializePanel()
	{
		addMouseListener(this);			//MouseListener
		addMouseMotionListener(this);	//MouseMotionListener	
		
		inJapanese = fileReadArray();
		icon = new ImageIcon(myModulePath+"tree2.png");
		image = icon.getImage();
		
        if(isMenuInJapanese())
        {
			scoreWord = inJapanese[0];
			segmentWord = inJapanese[1];
        }
        else
        {
			scoreWord = "Score";
			segmentWord = "Segment";			
        }		
	}
	
	public void initializeData()
	{
		df_a = new DecimalFormat("0.00");
		df_b = new DecimalFormat("000");
		
		touch_num = -1;
		
		moving = false;
		dragging = false;
	}
	
	public void displayOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 4502://Click
			case 0:
				firstData();
				repaint();
				break;
		}
		
		
	}	
	

	public void firstData()
	{
		t_p_value=t_m_value=t_value=b_p_value=b_m_value=b_value=tbar=tbar=0.0;
		wakecount=0;
		sort_segment = new int[text.segmentNumber];
		newNum=0;
		
		newCheck=new int[text.segmentNumber];
		for (int i=0; i<text.segmentNumber; i++)
			if(i==0)
				newCheck[i]=1;
			else
				newCheck[i]=0;
		
		map = new NewMapData2(text.segmentRelation.number);
		map.set_relation_data(text.segmentRelation);
		text.segmentRelation.createStrongestLink(text.segmentRelation.cond);

		check_link = new int[text.segmentNumber][text.segmentNumber];

		
        for (int i=0; i<text.segmentNumber; i++)
            for (int j=0; j<text.segmentNumber; j++)
                if(i==j)
                    check_link[i][j]=1;
                else
                    check_link[i][j]=0;
		


		kariscore= new double[text.segmentNumber][text.segmentNumber];		
		for (int i=0; i<text.segmentNumber; i++)
            for (int j=0; j<text.segmentNumber; j++)
				kariscore[i][j]=0.0;
		
		copy_cond= new double[text.segmentNumber][text.segmentNumber];
		segmentCheck=new int[text.segmentNumber];
		max_score = 0.0;
        
        over_cond = new double[text.segmentNumber][text.segmentNumber];
        for(int i=0;i<text.segmentNumber;i++)
            for(int j=0;j<text.segmentNumber;j++)
                over_cond[i][j] = 0.0;

		
		// CALCULATE TOP DOWN
		
		calculateTopDown();		
	
		for(int i=0;i<text.segmentNumber; i++)
		{
			sort_segment[i]=i;
			for(int j=0; j<text.segmentNumber; j++)
				copy_cond[i][j]=text.segmentRelation.cond[i][j];
            //    copy_cond[sort_segment[i]][j]=text.segmentRelation.cond[sort_segment[i]][j];
		}
		
		init_coordinate();
		first_link();
		firstRelation();
		
		for (int i=0; i<text.segmentNumber; i++)
			t_p_value+=Score.scoretop(i,text.segmentNumber,copy_link,copy_cond);
		
		t_value = t_p_value;
        
        System.out.println("TOP SCORE = "+t_value);
			
		bbar = tbar = 80 * t_value / Score.createMaxScore(text.segmentNumber);
        
        writeActionLog("SCORE:"+(int)tbar);
	}
 

    void calculateTopDown()
	{
		link = new int[text.segmentNumber][text.segmentNumber]; 
		copy_link = new int[text.segmentNumber][text.segmentNumber];
		int check_seg[] = new int[text.segmentNumber];
		double copy_cond[][]=new double[text.segmentNumber][text.segmentNumber];
		double sort_cond[]=new double[text.segmentNumber];
		int new_seg_num[]=new int[text.segmentNumber];
		int max_seg=0;
		int total[][] = new int[text.segmentNumber][2];
		int segmentCheck[]=new int[text.segmentNumber];
		
		for(int i=0;i<text.segmentNumber; i++){
			for(int j=0; j<text.segmentNumber; j++){
				copy_cond[i][j]=text.segmentRelation.cond[i][j];
			//	System.out.println("copy_cond["+i+"]["+j+"]:"+copy_cond[i][j]);
			}
		}
		
		for(int i=0; i<text.segmentNumber; i++)
			for(int j=0; j<text.segmentNumber; j++ )
				if (i==j) 
					link[i][j] = copy_link[i][j] = 1;
				else
					link[i][j] = copy_link[i][j] = 0;
		
		for (int i=text.segmentNumber-1; i>=0; i--)//text.segmentNumber
		{
			//for (int j=0; j<text.segmentNumber; j++)//text.segmentNumber
			for (int j=text.segmentNumber-1; j>=0; j--)//text.segmentNumber
				if (i==j)
					sort_cond[j] = 0.0;
				else
					sort_cond[j] = copy_cond[j][i];
			Qsort.initializeIndex(new_seg_num, text.segmentNumber);//text.segmentNumber
			Qsort.quicksort(sort_cond, new_seg_num, text.segmentNumber);//text.segmentNumber
			max_seg=-1;
			for(int j=0; j<text.segmentNumber; j++)//text.segmentNumber
				if(new_seg_num[j]<i)
				{
					max_seg = new_seg_num[j];
					break;
				}
			if (max_seg != -1)
			{
				copy_link[max_seg][i] = 1;
				link[max_seg][i] = link[i][max_seg] = 1;
			}
		}
		
		for(int i=0; i<text.segmentNumber; i++)
		{
			total[i][0] = LinkCheck.linkcheck(i,link,text.segmentNumber,segmentCheck);
			total[i][1] = 0;
		}
		
		while(total[0][0]!=text.segmentNumber)
		{
			for(int i=1; i<text.segmentNumber; i++)
				for (int j=0; j<i; j++)
				{
					if(total[i][0] == total[j][0])
						break;
					if( j == i-1 )
						total[i][1] = 1;
				}
			
			for (int i=0; i<text.segmentNumber; i++)
				if (total[i][1]==1)
				{
					for(int j=0; j<text.segmentNumber; j++)
						if(i==j)
							sort_cond[j] = 0.0;
						else
							sort_cond[j] = copy_cond[j][i];
					Qsort.initializeIndex(new_seg_num,text.segmentNumber);
					Qsort.quicksort(sort_cond,new_seg_num,text.segmentNumber);
					
					max_seg=-1;
					for(int j=0; j<text.segmentNumber; j++)
						if(new_seg_num[j]<i)
						{
							max_seg = new_seg_num[j];
							break;
						}
					
					copy_link[max_seg][i] = 1;
					link[max_seg][i] = link[i][max_seg] = 1;
					total[i][1] = 0;
				}
			
			for ( int i=0; i<text.segmentNumber; i++ )
				total[i][0] = LinkCheck.linkcheck(i,link,text.segmentNumber,segmentCheck);
			int segmentAmount = 1;
			for( int i=0; i<text.segmentNumber-1; i++)
				if( total[i][0]==total[i+1][0] )
					segmentAmount++;
			if( segmentAmount==text.segmentNumber && total[0][0]<text.segmentNumber)
			{
				total[0][0] = LinkCheck.linkcheck( 0, link, text.segmentNumber, segmentCheck);
				for( int i=0; i<text.segmentNumber; i++ )
					if( segmentCheck[i]==0 )
					{
						for( int j=0; j<text.segmentNumber; j++ )
							if( i==j )
								sort_cond[j] = 0.0;
							else
								sort_cond[j] = copy_cond[j][i];
						
						Qsort.initializeIndex(new_seg_num, text.segmentNumber );
						Qsort.quicksort( sort_cond, new_seg_num, text.segmentNumber );
						
						max_seg = -1;
						
						for( int j=0; j<text.segmentNumber; j++ )
							if( new_seg_num[j]<i && segmentCheck[new_seg_num[j]]==1 )
								//if( new_seg_num[j]<i && segmentCheck[j]==1 )
							{
								max_seg = new_seg_num[j];
								break;
							}
						if (max_seg!=-1)
						{
							copy_link[max_seg][i] = 1;
							link[max_seg][i] = link[i][max_seg]= 1;
							//copy_link[0][i] = 1;
							//link[0][i] = link[i][0]= 1;
							break;
						}
					}
			}
		}
		
		//for (int i=0; i<text.segmentNumber; i++) 
		//{
		//	if (i<text.segmentNumber/2) 
		//		score[i]=Score.scoretop(i,text.segmentNumber,copy_link,copy_cond);
		//}
		
		for (int i=0; i<text.segmentNumber; i++)
			for (int j=0; j<text.segmentNumber; j++)
				if(copy_link[i][j]==1)
					map.relation[i][j].link = true;
				else
					map.relation[i][j].link = false;
	}

 
	
	void calculateBottomUp()
	{
		link = new int[text.segmentNumber][text.segmentNumber];
		copy_link = new int[text.segmentNumber][text.segmentNumber];
		int check_seg[] = new int[text.segmentNumber];
		double copy_cond[][]=new double[text.segmentNumber][text.segmentNumber];
		double sort_cond[]=new double[text.segmentNumber];
		int new_seg_num[]=new int[text.segmentNumber];
		int max_seg=0;
		int total[][] = new int[text.segmentNumber][2];
		
		int segmentCheck[]=new int[text.segmentNumber];
		
		for(int i=0;i<text.segmentNumber; i++){
			for(int j=0; j<text.segmentNumber; j++){
				copy_cond[i][j]=text.segmentRelation.cond[i][j];
				// System.out.println("copy_cond["+i+"]["+j+"]:"+copy_cond[i][j]);
			}
		}
		for(int i=0; i<text.segmentNumber; i++)
			for(int j=0; j<text.segmentNumber; j++ )
				if (i==j)
					link[i][j] = copy_link[i][j] = 1;
				else
					link[i][j] = copy_link[i][j] = 0;
		
		for (int i=0; i<text.segmentNumber; i++)//text.segmentNumber
		{
			for (int j=0; j<text.segmentNumber; j++)//text.segmentNumber
				//for (int j=segmentNumber-1; j>=0; j--)//text.segmentNumber
				if (i==j)
					sort_cond[j] = 0.0;
				else
					sort_cond[j] = copy_cond[j][i];
			Qsort.initializeIndex(new_seg_num, text.segmentNumber);//text.segmentNumber
			Qsort.quicksort(sort_cond, new_seg_num, text.segmentNumber);//text.segmentNumber
			max_seg=-1;
			for(int j=0; j<text.segmentNumber; j++)//text.segmentNumber
				if(new_seg_num[j]>i)
				{
					max_seg = new_seg_num[j];
					break;
				}
			if (max_seg != -1)
			{
				copy_link[max_seg][i] = 1;
				link[max_seg][i] = link[i][max_seg] = 1;
			}
		}
		
		for(int i=0; i<text.segmentNumber; i++)
		{
			total[i][0] = LinkCheck.linkcheck(i,link,text.segmentNumber,segmentCheck);
			total[i][1] = 0;
			// System.out.println("total:"+total[i][0]);
		}
		
		while(total[text.segmentNumber-1][0]!=text.segmentNumber)
		{
			for(int i=text.segmentNumber-2; i>=0; i--)
				for (int j=text.segmentNumber-1; j>i; j--)
				{
					if(total[i][0] == total[j][0])
						break;
					if( j == i+1 )
						total[i][1] = 1;
				}
			
			for (int i=text.segmentNumber-1; i>=0; i--)
				if (total[i][1]==1)
				{
					for (int j=text.segmentNumber-1; j>=0; j--)
						//for(int j=0; j<text.segmentNumber; j++)
						if(i==j)
							sort_cond[j] = 0.0;
						else
							sort_cond[j] = copy_cond[j][i];
					
					Qsort.initializeIndex(new_seg_num,text.segmentNumber);
					Qsort.quicksort(sort_cond,new_seg_num,text.segmentNumber);
					
					max_seg=-1;
					
					for (int j=0; j<text.segmentNumber; j++)
						if(new_seg_num[j]>i)
						{
							max_seg = new_seg_num[j];
							break;
						}
					
					copy_link[max_seg][i] = 1;
					link[max_seg][i] = link[i][max_seg] = 1;
					total[i][1] = 0;
				}
			
			for ( int i=0; i<text.segmentNumber; i++ )
				total[i][0] = LinkCheck.linkcheck(i,link,text.segmentNumber,segmentCheck);
			int segmentAmount = 1;
			for( int i=0; i<text.segmentNumber-1; i++)
				if( total[i][0]==total[i+1][0] )
					segmentAmount++;
			if( segmentAmount==text.segmentNumber && total[text.segmentNumber-1][0]<text.segmentNumber)
			{
				total[text.segmentNumber-1][0] = LinkCheck.linkcheck( text.segmentNumber-1, link, text.segmentNumber, segmentCheck);
				for (int i=text.segmentNumber-1; i>=0; i--)
					if( segmentCheck[i]==0 )
					{
						for (int j=text.segmentNumber-1; j>=0; j--)
							//for( int j=0; j<text.segmentNumber; j++ )
							if( i==j )
								sort_cond[j] = 0.0;
							else
								sort_cond[j] = copy_cond[j][i];
						Qsort.initializeIndex(new_seg_num, text.segmentNumber );
						Qsort.quicksort( sort_cond, new_seg_num, text.segmentNumber );
						max_seg = -1;
						for( int j=0; j<text.segmentNumber; j++ )
							if( new_seg_num[j]>i && segmentCheck[new_seg_num[j]]==1 )
							{
								max_seg = new_seg_num[j];
								break;
							}
						
						if (max_seg!=-1)
						{
							copy_link[max_seg][i] = 1;
							link[max_seg][i] = link[i][max_seg]= 1;
							break;
						}
					}
			}
		}
		
		for (int i=0; i<text.segmentNumber; i++)
			for (int j=0; j<text.segmentNumber; j++)
				if(copy_link[i][j]==1)
					map.relation[i][j].link = true;
				else
					map.relation[i][j].link = false;
	}

	int width = 50;
	
    void init_coordinate()
    {
		map.node[0].x = sizeX/2;
        map.node2[0].x = sizeX/2;
		
		for(int i=0;i<text.segmentNumber;i++)
        {
			map.node[i].y = sizeY/text.segmentNumber * (i+0.5);
            map.node2[i].y = sizeY/text.segmentNumber * (i+0.5);
            
        }
		
		int loop_check[] = new int[text.segmentNumber];
		for(int i=0;i<text.segmentNumber;i++)
			loop_check[i] = 0;
		loop_check[0] = 1;
		
		init_coordinate_x(0,-1,0,loop_check);
		
		double left_bound = map.node[0].x;
		double right_bound = map.node[0].x;
		for(int i=1;i<text.segmentNumber;i++)
		{
			double x = map.node[i].x;
            if(x < left_bound)
				left_bound = x;
			if(x > right_bound)
				right_bound = x;
		}
        
		
		double margin = sizeX/(text.segmentNumber/2+1);
		double rate = (sizeX-margin*2)/(right_bound - left_bound);
		
		for(int i=0;i<text.segmentNumber;i++){
            if(rate>sizeX)
                break;
            else
            {
                map.node[i].x = (map.node[i].x - left_bound) * rate + margin;
                map.node2[i].x = (map.node2[i].x - left_bound) * rate + margin;
            }
        }
				
	}
	
	// initial straight: side = 0, right: side = 1, left: side = -1
	void init_coordinate_x(int current, int parent, int side, int loop_check[])
	{
		//		System.out.println("Parent= "+parent+"Current= "+current);
		
		if(parent != -1)
		{
			int uplist[] = count_up_link(current, parent, loop_check);
			
			for(int i=0;i<uplist.length;i++)
			{
				//				System.out.println("up: "+current+" -> "+uplist[i]);
				
				if(side == 0)
					side = 1;
				
				map.node[uplist[i]].x = map.node[current].x + side * width*(i+1);
                map.node2[uplist[i]].x = map.node[current].x + side * width*(i+1);
				init_coordinate_x(uplist[i],current,side, loop_check);
			}
		}
		
		int downlist[] = count_down_link(current, parent, loop_check);
		
		
		for(int i=0;i<downlist.length;i++)
		{
			//			System.out.println("down: "+current+" -> "+downlist[i]);			
			
			if(current < parent)
            {
				map.node[downlist[i]].x = map.node[current].x + side* (width/2 + width*i);
                map.node2[downlist[i]].x = map.node[current].x + side* (width/2 + width*i);
            }
			else
            {
				map.node[downlist[i]].x = map.node2[downlist[i]].x = map.node[current].x - (downlist.length-1)*width/2  + width*i;
            }
			
			if(side == 0 && downlist.length > 1)
			{
				if(i<downlist.length/2)
					init_coordinate_x(downlist[i],current,-1, loop_check);
				else
					init_coordinate_x(downlist[i],current,1, loop_check);
			}
			else
				init_coordinate_x(downlist[i],current,side, loop_check);	    
		}
		
		
    }	
	
	
    int[] count_up_link(int num, int from, int loop_check[])
    {
		int count=0;
		for(int i=0;i<num;i++)
			if(link[num][i] == 1 && loop_check[i]==0)
				count++;
		
		int uplist[] = new int[count];
		count = 0;
		for(int i=0;i<num;i++)
			if(link[num][i] == 1 && i != from)
			{
				if(loop_check[i] == 1)
				{
					System.out.println("LOOP DETECTED:"+from+"->"+num+"->"+i+" x ");
					continue;
				}
				loop_check[i] = 1;
				
				uplist[count] = i;
				count++;	
			}
		
		return uplist;
    }
	
    int[] count_down_link(int num, int from, int loop_check[])
    {
		int count=0;
		for(int i=num+1;i<text.segmentNumber;i++)
			if(link[num][i] == 1 && loop_check[i]==0)
				count++;
		
		int downlist[] = new int[count];
		count = 0;
		for(int i=num+1;i<text.segmentNumber;i++)
			if(link[num][i] == 1 && i != from)
			{
				if(loop_check[i] == 1)
				{
					System.out.println("LOOP DETECTED:"+from+"->"+num+"->"+i+" x ");
					continue;
				}
				loop_check[i] = 1;				
				
				downlist[count] = i;
				count++;	
			}
		
		return downlist;
    }
	
	void first_link()
	{
		for(int i=0;i<map.node_num;i++)
			for(int j=0;j<map.node_num;j++)
//				if(map.relation[i][j].link == true && i!=j)
                {
						map.relation[i][j].x1=map.node[i].x;
						map.relation[i][j].x2=map.node[j].x;
						map.relation[i][j].y1=map.node[i].y;
						map.relation[i][j].y2=map.node[j].y;
				}
	}	
	
    public void firstRelation()
	{
		for(int i=0;i<text.segmentNumber;i++)
			for(int j=0;j<text.segmentNumber;j++)
				if(map.relation[i][j].link==true && i!=j)
                {
                    map.firstRelation[i][j].x1 = map.relation[i][j].x1;
                    map.firstRelation[i][j].y1 = map.relation[i][j].y1;
					map.firstRelation[i][j].link = true;
					//	System.out.println("map.firstRelation["+i+"]["+j+"].link");
                    //	System.out.println("method_firstX["+i+"]["+j+"]:"+map.firstRelation[i][j].x1);
                    //	System.out.println("method_firstY["+i+"]["+j+"]:"+map.firstRelation[i][j].y1);
                }
	}
	
	//DRAWING 
	boolean change;
	
	public void paintComponent(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D)g;
		
		change = getPanelSize();
		
		//		if(oldSizeX == 0 || oldSizeY == 0)
		//			init_coordinate();		
		
		if(change)
			calcu_coordinate();
		
		draw_background(g2);	//Set background*
		
		if(moving)
		{
			draw_moving_links(g2);		
		}
		
		draw_links(g2);			//Display links
		draw_values(g2);
		draw_nodes(g2);		//Display nodes
		
		draw_node_names(g2);	//Display node names
		
		draw_touch_node(g2);	//Display nodes touched by a mouse
		draw_touch_nodename(g2);	//Display node names touched by a mouse

		if(moving)
		{	
			draw_moving_values(g2);
		}
		
		score_print(g2);
	}
	
	public void update(Graphics g)		//Avoid from blinking
	{
		paintComponent(g);
	}	
	
	

	void calcu_coordinate()
	{		
		//node
		for(int i=0;i<map.node_num;i++)
		{
			map.node2[i].x *= changeX;
			map.node2[i].y *= changeY;			
		}
		//link
		for(int i=0;i<map.node_num;i++)
			for(int j=0;j<map.node_num;j++)
				if(map.relation[i][j].link == true && i!=j)
				{
					map.relation[i][j].x1 *= changeX;
					map.relation[i][j].x2 *= changeX;
					map.relation[i][j].y1 *= changeY;
					map.relation[i][j].y2 *= changeY;
				}
	}

	void draw_background(Graphics2D g2)
	{
		g2.setColor(new Color(255,204,255));
        g2.fillRect(0,0, sizeX, sizeY);

		g2.drawImage(image,sizeX/2+sizeX/4,sizeY/2+sizeY/4,sizeX/4,sizeY/4,this);
	}	
	
	public void drawArrow(Graphics2D g2, int x0, int y0, int x1, int y1, int r)
	{
		double t;
		int x, y;
		double l = 30;	// length of arrow wings,before=30 
		
		//Angle of end of an arrow
		double dt = Math.PI / 6.0;  // pi/6 = 30 degree
		
		t = Math.atan2((double)(y1 - y0), (double)(x1 - x0));	// arrow vector
		//g2.setColor(new Color(0xfa,0x80,0x72));
		g2.drawLine(x0, y0, x1, y1);
		
		x1 = x1 - (int)(r * Math.cos(t));
		y1 = y1 - (int)(r * Math.sin(t));
		
		//g2.setColor(Color.blue);     
		x = x1 - (int)(l * Math.cos(t - dt));
		y = y1 - (int)(l * Math.sin(t - dt));
		g2.drawLine(x1, y1, x, y);
		x = x1 - (int)(l * Math.cos(t + dt));
		y = y1 - (int)(l * Math.sin(t + dt));
		g2.drawLine(x1, y1, x, y);
	}	
	
	public void draw_links(Graphics2D g2)
	{
		double cond;
		double line_weight;
		
		g2.setColor(new Color(165,42,42));//brown
		g2.setFont(new Font("Dialog", Font.BOLD, 30));		
		

		for(int i=0;i<map.node_num;i++)
        {
			line_weight=0.0;

			for(int j=i;j<map.node_num;j++)			
//			for(int j=0;j<map.node_num;j++)
            {
				if(map.relation[i][j].link == true && i!=j)
				{
					if(moving && (i == touch_num || j == touch_num))
						continue;

					//cond = copy_cond[sort_segment[i]][sort_segment[j]];
                   // cond = copy_cond[sort_segment[i]][j];
                    cond = copy_cond[i][j];
                    line_weight = cond*10/2*3;//-2)/2;
					g2.setStroke(new BasicStroke((int)line_weight, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
										
					drawArrow(g2,(int)map.relation[i][j].x1,(int)map.relation[i][j].y1,(int)map.relation[i][j].x2,(int)map.relation[i][j].y2,width/4);

				}
			}
		}
	}
	
	public void draw_values(Graphics2D g2)
	{
		double cond;
		
		g2.setColor(Color.blue);
		g2.setFont(new Font("Dialog", Font.BOLD, 30));		
		

		for(int i=0;i<map.node_num;i++)
			for(int j=0;j<map.node_num;j++)
				if(map.relation[i][j].link == true && i!=j)
                {
					if(moving && (i == touch_num || j == touch_num))
						continue;					
					
				//	cond = copy_cond[sort_segment[i]][sort_segment[j]];
                 //   cond = copy_cond[sort_segment[i]][j];
                    cond = copy_cond[i][j];
                    if(over_cond[i][j] > 0)
                    {
                        g2.setColor(Color.red);
					g2.drawString(String.format("%1.2f",cond)+" > "+String.format("%1.2f",over_cond[i][j]),
									  ((int)map.relation[i][j].x1+(int)map.relation[i][j].x2*3)/4+15,((int)map.relation[i][j].y1+(int)map.relation[i][j].y2*3)/4);
                    }
                    else
                    {
                        		g2.setColor(Color.blue);
                        g2.drawString(String.format("%1.2f",cond),
									  ((int)map.relation[i][j].x1+(int)map.relation[i][j].x2*3)/4+15,((int)map.relation[i][j].y1+(int)map.relation[i][j].y2*3)/4);
                    }
                }
	}	
	
	public void draw_moving_links(Graphics2D g2)
	{
		g2.setColor(new Color(255,255,42));//yellow				
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));		
		
//		for(int j=0;j<map.node_num;j++)
		for(int j=0;j<touch_num;j++)		
        {
			if(j == touch_num)
				continue;
			
			if(j == minSeg)
				g2.setColor(Color.red);
			else
				g2.setColor(new Color(255,255,42));				
			
			g2.drawLine((int)map.node2[touch_num].x,(int)map.node2[touch_num].y,(int)map.node2[j].x,(int)map.node2[j].y);
		}
	}
	
	public void draw_moving_values(Graphics2D g2)
	{
		double cond;
		double value;
		
		g2.setColor(new Color(255,255,42));//yellow
		g2.setFont(new Font("Dialog", Font.BOLD, 15));
		
		
		
		
//		for(int j=0;j<map.node_num;j++)
		for(int j=0;j<touch_num;j++)		
        {
			if(j == touch_num)
				continue;
			
			if(j == minSeg)
				g2.setColor(Color.red);
			else
				g2.setColor(new Color(0,100,0));
					
			cond = copy_cond[j][touch_num];						
			value = kariscore[j][touch_num];
			//System.out.println("score:"+value);
			g2.drawString(String.format("%.2f, %s: %d",cond,inJapanese[0],(int)value),
						  ((int)map.relation[touch_num][j].x1+(int)map.relation[touch_num][j].x2*3)/4,((int)map.relation[touch_num][j].y1+(int)map.relation[touch_num][j].y2*3)/4);
		}
		
		g2.setFont(new Font("Dialog", Font.BOLD, 30));		
	}	
	
    /*************/
    public void searchChildren(int node, boolean children[])
    {
        for(int i=node+1;i<text.segmentNumber;i++)
//            if(copy_link[node][i] == 1)
            if(map.relation[node][i].link == true)
            {
                children[i] = true;
                searchChildren(i,children);
            }
    }
    
    public boolean[] searchAllChildren(int node)
    {
        boolean children[] = new boolean[text.segmentNumber];
        
        for(int i=node+1;i<text.segmentNumber;i++)
            children[i] = false;
        
        searchChildren(node,children);
        
        return children;
    }
    
    public int searchParent(int node)
    {
        for(int i=0;i<node;i++)
//            if(map.relation[i][node].link == true)
            if(copy_link[i][node] == 1)
                return i;
        return -1;
    }
    
	//Display nodes
	public void draw_node(Graphics2D g2, int n)
	{

        
        
        
        
		g2.setColor(new Color(0,0,0));
        g2.fillRect((int)map.node2[n].x- (int)map.node2[n].radius*4-2,(int)map.node2[n].y - (int)map.node2[n].radius-2, 
					(int)map.node[newNum].name.length()*24+4, 30+4);
		g2.setColor(map.node[n].color);
        g2.fillRect((int)map.node2[n].x- (int)map.node2[n].radius*4,(int)map.node2[n].y - (int)map.node2[n].radius, 
					(int)map.node[newNum].name.length()*24, 30);		
	}
	
    boolean displayKeywords[];
	public void draw_nodes(Graphics2D g2)
	{
        displayKeywords = new boolean[text.keywordNumber];
        for(int i=0;i<text.keywordNumber;i++)
            displayKeywords[i] = true;
        
		for(int i=0;i<map.node_num;i++)
			draw_node(g2, i);
	}
	
	////Dispaly node names
	public void draw_node_name(Graphics2D g2, int n)
	{
		g2.setFont(new Font("Dialog", Font.BOLD, 20));
		g2.drawString(map.node[n].name, (int)map.node2[n].x- (int)map.node2[n].radius*4+10, (int)map.node2[n].y+(int)map.node2[n].radius+2);
		//System.out.println("newNum:"+newNum);
        
        boolean children[];
        int commonKeywords[];
        int parent;
        
        children = searchAllChildren(n);
        children[n] = true; // myself
        
        if(n == 0)
            parent = 0;
        else
            parent = searchParent(n);
        
        if(parent == -1)
		{
            System.out.println("*****NO PARENT : "+n);
			return;
		}
        else
        {
 //           System.out.println("PARENT FOR "+n+" : "+parent);
            children[parent] = true; //parent
        }
        
        commonKeywords = text.getCommonKeywords(children);
        
//        System.out.println("CHILDREN FOR "+n+":");
 //       for(int i=0;i<text.segmentNumber;i++)
//            if(children[i] == true)
 //               System.out.println(i);
        
        for(int i=0;i<text.keywordNumber;i++)
            if(displayKeywords[i] == false)
                commonKeywords[i] = 0;
        
//        System.out.println("Common Keywords are :");
        for(int i=0;i<text.keywordNumber;i++)
            if(commonKeywords[i] > 0)
            {
       //         System.out.println(text.keyword[i].word + " : "+commonKeywords[i]);
                displayKeywords[i] = false;
            }
        
        String displayWords = "";
        for(int i=0;i<text.keywordNumber;i++)
            if(commonKeywords[i] > 0)
                displayWords += (text.keyword[i].word+" ");
        
		g2.setFont(new Font("Dialog", Font.BOLD, 15));        
        g2.drawString(displayWords, (int)map.node2[n].x- (int)map.node2[n].radius*4, (int)map.node2[n].y-(int)map.node2[n].radius*2);
	}
	
	public void draw_node_names(Graphics2D g2)
	{
		g2.setFont(new Font("Dialog", Font.BOLD, 20));
		g2.setColor(Color.black);
		
		for(int i=0;i<map.node_num;i++)
			draw_node_name(g2, i);
	}
	
	public void draw_touch_node(Graphics2D g2)
	{
		int n;
		
		if(touch_num < 0)
			return;
		
		n = touch_num;
		
		g2.setColor(Color.green);
        g2.setFont(new Font("Dialog", Font.BOLD, 20));        
		g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));		
        g2.drawRect((int)map.node2[n].x- (int)map.node2[n].radius*4-2,(int)map.node2[n].y - (int)map.node2[n].radius-2, 
					(int)map.node[newNum].name.length()*24+4, 30+4);		
        
	}
	
	//Display node names touched by a mouse    
	public void draw_touch_nodename(Graphics2D g2)
	{
		int n;		
		
		if(touch_num < 0)
			return;
		
		n = touch_num;		
		
		g2.setColor(Color.blue);
		g2.drawString(map.node[n].name, (int)map.node2[n].x- (int)map.node2[n].radius*4+10, (int)map.node2[n].y+(int)map.node2[n].radius+2);		
	}	
	
	

	
	public void score_print(Graphics2D g2)
	{
	

        //		g2.fillRect(5,sizeY*1/16-25,(int)tbar,40);//sum

        if(tbar != bbar)
        {
            g2.setColor(Color.red);
            g2.drawString(scoreWord+": "+(int)bbar+" "+inJapanese[2]+" "+(int)tbar,0,20);

            g2.setColor(Color.pink);			
			g2.fillRect(5,22,(int)tbar*2,20);//sum				
        }
        else
        {
            g2.setColor(Color.black);
            g2.drawString(scoreWord+": "+(int)tbar,0,20);
        }
		
		g2.setColor(new Color(241,90,20));
		g2.fillRect(5,22,(int)bbar*2,20);//sum		
		
		
		
        //		g2.drawString(scoreWord+": "+(int)tbar/2,0,sizeY*1/16-30);

		g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
//		g2.drawLine(5,75,205,75);
//		g2.drawLine(5,75,5,65);
//		g2.drawLine(105,75,105,65);
//		g2.drawLine(205,75,205,65);
//		g2.drawLine(5,sizeY*1/16+30,205,sizeY*1/16+30);
//		g2.drawLine(5,sizeY*1/16+30,5,sizeY*1/16+20);
//		g2.drawLine(105,sizeY*1/16+30,105,sizeY*1/16+20);
//		g2.drawLine(205,sizeY*1/16+30,205,sizeY*1/16+20);
		
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));		
		g2.setColor(Color.red);
		g2.drawLine(165,20,165,42);
        //		g2.drawLine(165,sizeY*1/16-35,165,sizeY*1/16+25);
	}	
	
	
	

	
	void kouzou(int n)
	{

		double bar,kb_m_value,kb_p_value,kt_m_value,kt_p_value,kt_value,kb_value;		
		
		kb_m_value=kb_p_value=kt_p_value=kt_m_value=bar=0;
		
        
        copy_link[oldSeg][touch_num] = 0; // n can be the SAME as oldSeg
		copy_link[n][touch_num] = 1;
        
        for (int i=0; i<text.segmentNumber; i++)
			kt_p_value+=Score.scoretop(i,text.segmentNumber,copy_link,copy_cond);
		
        
		kt_value = kt_p_value;
        
			System.out.println("TOP:"+kt_value);
		
        bar = 80 * kt_value / Score.createMaxScore(text.segmentNumber);
		
		kariscore[n][touch_num]=bar;
		
		copy_link[n][touch_num] = 0;	 // n can be the SAME as oldSeg	
		copy_link[oldSeg][touch_num] = 1;

	}
	
	//MouseListener
	public void mousePressed(MouseEvent me)	//Press
	{
		mousex = me.getX();
		mousey = me.getY();
		
		if(touch_num == -1)
		{
			old_mousex = mousex;
			old_mousey = mousey;
			dragging = true;
			return;
		}
		moving = true;
		
//		for(int i=0;i<text.segmentNumber;i++)
		for(int i=0;i<touch_num;i++)		
			if(map.relation[i][touch_num].link == true)
			{
				oldSeg = i;
				break;
			}
     	for(int i=0;i<text.segmentNumber;i++)
			if(i != touch_num)
				kouzou(i);

		System.out.println("oldSeg = "+oldSeg);
	}
	

	public void mouseReleased(MouseEvent me)	//Release
	{
		if(moving == true)
        {
			moving = false;
			
			if(minSeg > touch_num)
			{
				copy_link[oldSeg][touch_num] = link[oldSeg][touch_num] = link[touch_num][oldSeg] = 1;
				map.newRelation[oldSeg][touch_num].link = true;
				map.relation[oldSeg][touch_num].link = true;				
				repaint();
				return;
			}
			
			if(minSeg == oldSeg)
			{
				copy_link[oldSeg][touch_num] = link[oldSeg][touch_num] = link[touch_num][oldSeg] = 1;
				map.newRelation[oldSeg][touch_num].link = true;
				map.relation[oldSeg][touch_num].link = true;				
				repaint();
				return;
			}			
			
            if( over_cond[oldSeg][touch_num] > 0 )
                over_cond[minSeg][touch_num] = over_cond[oldSeg][touch_num];
            else
                over_cond[minSeg][touch_num] = copy_cond[oldSeg][touch_num];
            
            if(over_cond[minSeg][touch_num] == copy_cond[minSeg][touch_num])
                over_cond[minSeg][touch_num] = 0;
            
			copy_link[oldSeg][touch_num] = link[oldSeg][touch_num] = link[touch_num][oldSeg] = 0;
			map.newRelation[oldSeg][touch_num].link = false;
			map.relation[oldSeg][touch_num].link = false;				
			
			copy_link[minSeg][touch_num] = link[minSeg][touch_num] = link[touch_num][minSeg] = 1;
			map.newRelation[minSeg][touch_num].link = true;
			map.relation[minSeg][touch_num].link = true;			

			text.focus.mainFocusSegment = touch_num;
			text.focus.subFocusSegment = minSeg;
			text.focus.setMainFocusInteger(minSeg);
			text.focus.setSubFocusInteger(oldSeg);
			
			tbar = kariscore[minSeg][touch_num];
			
			System.out.println("touch_num= "+touch_num+" minSeg= "+minSeg+" oldSeg= "+oldSeg);
            
            writeActionLog("NODECHANGED("+(touch_num+1)+"-"+(minSeg+1)+"<-"+(oldSeg+1)+")");

			executeOthersByClick();
        }
		
		if(dragging == true)
			dragging = false;
		
        repaint();
	}
		
	public void mouseEntered(MouseEvent me){}	//Enter an area
	public void mouseExited(MouseEvent me){}	//Exit an area
	public void mouseClicked(MouseEvent me){}	//Click
	

	int minSeg, oldSeg;
	//MouseMotionListener
	public void mouseDragged(MouseEvent me)
	{
		double a,b,dis,minDis;
		
		if(moving == true)		//Move a node
		{
			try{
				mousex = me.getX();
				mousey = me.getY();
                
                dx = old_mousex - mousex;
                dy = old_mousey - mousey;
                old_mousex = mousex;
                old_mousey = mousey;
                
                map.node2[touch_num].x = mousex;
                map.node2[touch_num].y = mousey;
                map.node[touch_num].x = mousex;
				map.node[touch_num].y = mousey;
  
				for(int i=0;i<text.segmentNumber;i++)				
//				for(int i=0;i<touch_num;i++)
				{
					map.relation[i][touch_num].x1 = map.node2[i].x;
					map.relation[i][touch_num].y1 = map.node2[i].y;
					map.relation[i][touch_num].x2 = map.node2[touch_num].x;
					map.relation[i][touch_num].y2 = map.node2[touch_num].y;
//				}
//				for(int i=touch_num+1;i<text.segmentNumber;i++)
//				{					
					map.relation[touch_num][i].x1 = map.node2[touch_num].x;
					map.relation[touch_num][i].y1 = map.node2[touch_num].y;
					map.relation[touch_num][i].x2 = map.node2[i].x;
					map.relation[touch_num][i].y2 = map.node2[i].y;						
				}				
				

				
				minSeg = -1;
				minDis = 1000000.0;
//				for(int i=0;i<text.segmentNumber;i++)
				for(int i=0;i<touch_num;i++)					
					if(i!=touch_num)
					{						
						a = Math.abs(map.node2[i].x-map.node2[touch_num].x);
						b = Math.abs(map.node2[i].y-map.node2[touch_num].y);						
						dis = Math.sqrt(a*a+b*b);
						
						if(dis < minDis)
						{
							minDis = dis;
							minSeg = i;
                            //	System.out.println("minDis:"+minDis);
							//	System.out.println("minSeg:"+minSeg);
						}
					}
				
				repaint();
			}
			
			catch(StackOverflowError e){
				System.out.println("Stack OVERFLOW");
			}
			
        }		
		
		if(dragging == true)	//Move Field
		{
			mousex = me.getX();
			mousey = me.getY();
			dx = old_mousex - mousex;
			dy = old_mousey - mousey;
			old_mousex = mousex;
			old_mousey = mousey;
			for(int i=0;i<map.node_num;i++)
			{
				map.node2[i].x -= dx;
				map.node2[i].y -= dy;
                for(int j=0;j<map.node_num;j++)
                    if(copy_link[i][j]==1)
					{						
						map.relation[i][j].x1 -= dx;
						map.relation[i][j].y1 -= dy;
						map.relation[i][j].x2 -= dx;
						map.relation[i][j].y2 -= dy;
					}
			}
			repaint();
        }	
	}
	
   	public void mouseMoved(MouseEvent me)
	{
		mousex = me.getX();
		mousey = me.getY();
		touch_num = CatchNode(mousex,mousey);
        
        
        
		if(touch_num >= 0)
		{
			if(touch_num != oldTouchNumber)
			{
				oldTouchNumber = touch_num;
				repaint();
			}
			return;
		}
		
		if(touch_num == -1)
		{
			mousex = me.getX();
			mousey = me.getY();
			dx = old_mousex - mousex;
			dy = old_mousey - mousey;
			old_mousex = mousex;
			old_mousey = mousey;
		}
		
		oldTouchNumber = touch_num;
		repaint();
		
	}
	
  
	
	
	public int CatchNode(int mousex, int mousey)	//Investigate number of selected node
	{
		for(int i=0;i<map.node_num;i++)
		{
			if((mousex > map.node2[i].x- (int)map.node2[i].radius*4-2 && mousex < (int)map.node2[i].x- (int)map.node2[i].radius*4-2 + (int)map.node[i].name.length()*24+4 ) &&
			   (mousey > (int)map.node2[i].y - (int)map.node2[i].radius-2 && mousey < (int)map.node2[i].y - (int)map.node2[i].radius-2 + 30+4))
			{
//				System.out.println("i:"+i);
                if(i==0)
                    return -1;
                    
				return i;
			}
		}
		
		return -1;
	}	
	
	
	

	
	

	class NewMapData2
	{
		
		class Node
		{
			String name,rankName;
			int id;
		
			double x;
			double y;
			double radius=10;
			
			int link_num;
			double arg_rank,speed_rank,story_rank;//
		
			Color color,rankColor;
		}
	
		Node node[],node2[];
		int node_num;
	
	
		//Node Relation Class
		class Relation
		{
			boolean link;			//Is exist a link between nodes?
			double value,x1,x2,y1,y2;
			
//			double rdist;			// real distance for spring model
//			double idist;			// ideal distance for spring model
			Color color;
		}
	
		Relation relation[][],newRelation[][],firstRelation[][];		//Node Relation information	
		int link_num;				//Number of all links

		double relate_low;

		void init_nodes(int num)
		{
			node_num = num;
			node = new Node[node_num];
            node2 = new Node[node_num];
		
			for(int i=0;i<node_num;i++)
            {
				node[i] = new Node();
                node2[i] = new Node();
            }
			
		
			relation = new Relation[node_num][node_num];
			newRelation = new Relation[node_num][node_num];
			firstRelation = new Relation[node_num][node_num];

			for(int i=0;i<node_num;i++)
				for(int j=0;j<node_num;j++)
				{
					relation[i][j] = new Relation();
					newRelation[i][j] = new Relation();
					firstRelation[i][j] = new Relation();
				}
		}
	
		NewMapData2(int num)
		{
			init_nodes(num);
		}
		
		//Variables for operation
	
		
		void set_relation_data(RelationalData rd)
		{			
			for(int i=0;i<rd.number;i++)
			{
				node[i].id = i;
				node[i].name = inJapanese[1]+(i+1);//rd.name[i];
				node2[i].x = 400 + 200 * Math.cos(i);
				node2[i].y = 400 + 200 * Math.sin(i);
                node[i].x = 400 + 200 * Math.cos(i);
				node[i].y = 400 + 200 * Math.sin(i);
				node[i].color = new Color(0,204,204);
			}			
			
			
			for(int i=0;i<node_num;i++)
				for(int j=0;j<node_num;j++)
				{
					relation[i][j].link = rd.link[i][j];
					relation[i][j].value = rd.cos[i][j];
					relation[i][j].color = new Color(0,102,0);
				}	
			
			link_num = rd.totalLinkNumber;
		}
	}
}
