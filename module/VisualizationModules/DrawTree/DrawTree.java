//
// Visuzalization module for TETDM
// DrawTree.java Version 0.30
// Copyright(C) 2011 System Interface Laboratory (Hiroshima City University) ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//


package module.VisualizationModules.DrawTree;

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



public class DrawTree extends VisualizationModule implements MouseListener, MouseMotionListener {
  int link[][];

  class Node {
    int up_x, up_y;
    int down_x, down_y;
    int check=-1;

    int oya;
    int num=0;

    int line=0;
    int side;
  }

  //For objects operation
  int mousex, mousey;			//Coordinates for a mouse
  int old_mousex, old_mousey;	//Last Coordinates for a mosue
  int dx, dy;					//Distance for mouse moving

  int touch_num;				//Node Number of being touched
  int oldTouchNumber;

  boolean moving;			//Flag for node moving
  boolean dragging;		//Flag for field moving

  DecimalFormat df_a;

  NewMapData2 map;

  public DrawTree() {
    setModuleID(1015);
    setToolType(1);
  }

  public void initializePanel() {
    addMouseListener(this);			//MouseListener
    addMouseMotionListener(this);	//MouseMotionListener
  }

  public void initializeData() {
    df_a = new DecimalFormat("0.00");

    touch_num = -1;

    moving = false;
    dragging = false;
  }

  public void firstData() {
    map = new NewMapData2(text.segmentRelation.number);
    map.set_relation_data(text.segmentRelation);
    text.segmentRelation.createStrongestLink(text.segmentRelation.cos);

    setData2();
    init_coordinate();
  }

  void setData2() {
    double copy_cos[][]=new double[text.segmentNumber][text.segmentNumber];
    double sort_cos[]=new double[text.segmentNumber];

    int max_seg=0,total=0;
    int k=0;
    int new_seg_num[]=new int[text.segmentNumber];
    link = new int[text.segmentNumber][text.segmentNumber];

    //link
    for (int i=0; i<text.segmentNumber; i++) {
      for (int j=0; j<text.segmentNumber; j++)
        if (i==j) {
          link[i][j]=1;  //
        } else {
          link[i][j]=0;
        }
    }

    //
    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segmentNumber; j++) {
        copy_cos[i][j]=text.segmentRelation.cos[i][j];  //
      }

    //
    for (int i=0; i<text.segmentNumber; i++) {
      for (int j=0; j<text.segmentNumber; j++) {
        if (i==j) {
          sort_cos[j]=0.0;
        } else {
          sort_cos[j]=copy_cos[i][j];
        }
      }
      Qsort.initializeIndex(new_seg_num,text.segmentNumber);
      Qsort.quicksort(sort_cos,new_seg_num,text.segmentNumber);
      //for (k=0; k<text.segmentNumber; k++)
      //	System.out.println(""+sort_cos[k]);
      //	System.out.println(i+" "+new_seg_num[k]);///
      max_seg=new_seg_num[0];
      link[i][max_seg]=link[max_seg][i]=1;//
      //System.out.println(""+max_seg);//
    }

    for (int i=0; i<text.segmentNumber; i++) {
      total = LinkCheck.linkcheck(i,link,text.segmentNumber);
    }
    //System.out.println(""+total);


    //
    if (LinkCheck.linkcheck(0,link,text.segmentNumber)!=text.segmentNumber) {
      double allsort_cos[]=new double[text.segmentNumber*text.segmentNumber];//
      int all_seg_num[]=new int[text.segmentNumber*text.segmentNumber];//

      for (int i=0; i<text.segmentNumber; i++) {
        for (int j=0; j<text.segmentNumber; j++) { //
          if (i==j) {
            allsort_cos[i*text.segmentNumber+i]=0.0;
          } else {
            allsort_cos[i*text.segmentNumber+j]=text.segmentRelation.cos[i][j];
          }
        }
      }
      Qsort.initializeIndex(all_seg_num,text.segmentNumber*text.segmentNumber);
      Qsort.quicksort(allsort_cos,all_seg_num,text.segmentNumber*text.segmentNumber);
      //for (k=0; k<10; k++)
      //	System.out.println(""+allsort_cos[k]);
      //	System.out.println(all_seg_num[2*k]/text.segmentNumber+" "+all_seg_num[2*k]%text.segmentNumber);///

      int a,b;
      for (int i=0; i<text.segmentNumber*text.segmentNumber; i++) {
        a=all_seg_num[i]/text.segmentNumber;
        b=all_seg_num[i]%text.segmentNumber;

        if (link[a][b]==0) {
          link[a][b]=link[b][a]=1;//
          if (LinkCheck.linkcheck(0,link,text.segmentNumber)>total) {
            total = (LinkCheck.linkcheck(0,link,text.segmentNumber));
          } else {
            link[a][b]=link[b][a]=0;
          }

          if (total==text.segmentNumber) {
            break;
          } else {
            continue;
          }
        }
      }
    }

    for (int i=0; i<text.segmentNumber; i++)
      for (int j=0; j<text.segmentNumber; j++)
        if (link[i][j] == 1) {
          map.relation[i][j].link = true;
        } else {
          map.relation[i][j].link = false;
        }
//				text.segmentRelation.link[i][j] = link[i][j];




  }

  int width = 50;

  void init_coordinate() {
    map.node[0].x = sizeX/2;

    for (int i=0; i<text.segmentNumber; i++) {
      map.node[i].y = sizeY/text.segmentNumber * (i+0.5);
    }

    int loop_check[] = new int[text.segmentNumber];
    for (int i=0; i<text.segmentNumber; i++) {
      loop_check[i] = 0;
    }
    loop_check[0] = 1;

    init_coordinate_x(0,-1,0,loop_check);

    double left_bound = map.node[0].x;
    double right_bound = map.node[0].x;
    for (int i=1; i<text.segmentNumber; i++) {
      double x = map.node[i].x;
      if (x < left_bound) {
        left_bound = x;
      }
      if (x > right_bound) {
        right_bound = x;
      }
    }

    double margin = sizeX/(text.segmentNumber/2+1);
    double rate = (sizeX-margin*2)/(right_bound - left_bound);

    for (int i=0; i<text.segmentNumber; i++) {
      map.node[i].x = (map.node[i].x - left_bound) * rate + margin;
    }
  }

  int[] count_up_link(int num, int from, int loop_check[]) {
    int count=0;
    for (int i=0; i<num; i++)
      if (link[num][i] == 1 && loop_check[i]==0) {
        count++;
      }

    int uplist[] = new int[count];
    count = 0;
    for (int i=0; i<num; i++)
      if (link[num][i] == 1 && i != from) {
        if (loop_check[i] == 1) {
          System.out.println("LOOP DETECTED:"+from+"->"+num+"->"+i+" x ");
          continue;
        }
        loop_check[i] = 1;

        uplist[count] = i;
        count++;
      }

    return uplist;
  }

  int[] count_down_link(int num, int from, int loop_check[]) {
    int count=0;
    for (int i=num+1; i<text.segmentNumber; i++)
      if (link[num][i] == 1 && loop_check[i]==0) {
        count++;
      }

    int downlist[] = new int[count];
    count = 0;
    for (int i=num+1; i<text.segmentNumber; i++)
      if (link[num][i] == 1 && i != from) {
        if (loop_check[i] == 1) {
          System.out.println("LOOP DETECTED:"+from+"->"+num+"->"+i+" x ");
          continue;
        }
        loop_check[i] = 1;

        downlist[count] = i;
        count++;
      }

    return downlist;
  }

  // initial straight: side = 0, right: side = 1, left: side = -1
  void init_coordinate_x(int current, int parent, int side, int loop_check[]) {
//		System.out.println("Parent= "+parent+"Current= "+current);

    if (parent != -1) {
      int uplist[] = count_up_link(current, parent, loop_check);

      for (int i=0; i<uplist.length; i++) {
//				System.out.println("up: "+current+" -> "+uplist[i]);

        if (side == 0) {
          side = 1;
        }

        map.node[uplist[i]].x = map.node[current].x + side * width*(i+1);
        init_coordinate_x(uplist[i],current,side, loop_check);
      }
    }

    int downlist[] = count_down_link(current, parent, loop_check);


    for (int i=0; i<downlist.length; i++) {
//			System.out.println("down: "+current+" -> "+downlist[i]);

      if (current < parent) {
        map.node[downlist[i]].x = map.node[current].x + side* (width/2 + width*i);
      } else {
        map.node[downlist[i]].x = map.node[current].x - (downlist.length-1)*width/2  + width*i;
      }

      if (side == 0 && downlist.length > 1) {
        if (i<downlist.length/2) {
          init_coordinate_x(downlist[i],current,-1, loop_check);
        } else {
          init_coordinate_x(downlist[i],current,1, loop_check);
        }
      } else {
        init_coordinate_x(downlist[i],current,side, loop_check);
      }
    }
  }

  void calcu_coordinate() {
    for (int i=0; i<text.segmentNumber; i++) {
      map.node[i].x *= changeX;
      map.node[i].y *= changeY;
    }
  }


  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 4501:
      case 0:
        firstData();
        repaint();
        break;
    }
  }


  //////////DRAWING
  void draw_background(Graphics2D g2) {
//		sizeX = getWidth();
//		sizeY = getHeight();

    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,0, sizeX, sizeY);
    ImageIcon icon = new ImageIcon(myModulePath+"tree2.png");
    Image image = icon.getImage();
    g2.drawImage(image,sizeX/2+sizeX/4,sizeY/2+sizeY/4,sizeX/4,sizeY/4,this);
  }

  //Display links
  public void draw_links(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    for (int i=0; i<map.node_num; i++)
      for (int j=0; j<map.node_num; j++)
        if (map.relation[i][j].link == true) {
          g2.drawLine((int)map.node[i].x,(int)map.node[i].y,(int)map.node[j].x,(int)map.node[j].y);
        }
  }


  //Display nodes
  public void draw_node(Graphics2D g2, int n) {
    g2.setColor(map.node[n].color);
    g2.fillOval((int)map.node[n].x - (int)map.node[n].radius,
                (int)map.node[n].y - (int)map.node[n].radius, (int)map.node[n].radius*2, (int)map.node[n].radius*2);
  }

  public void draw_nodes(Graphics2D g2) {
    for (int i=0; i<map.node_num; i++) {
      draw_node(g2, i);
    }
  }

  ////Dispaly node names
  public void draw_node_name(Graphics2D g2, int n) {
    g2.setFont(new Font("Dialog", Font.BOLD, (int)(text.fontSize+10-2*map.node_num/25.0)));
    g2.drawString(map.node[n].name, (int)map.node[n].x+(int)map.node[n].radius, (int)map.node[n].y+(int)map.node[n].radius);
  }

  public void draw_node_names(Graphics2D g2) {
    for (int i=0; i<map.node_num; i++) {
      g2.setColor(Color.black);
      draw_node_name(g2, i);
    }
  }

  public void draw_touch_node(Graphics2D g2) {
    if (touch_num < 0) {
      return;
    }

    draw_node(g2, touch_num);
  }

  //Display node names touched by a mouse
  public void draw_touch_nodename(Graphics2D g2) {
    if (text.focus.mainFocusSegment < 0) {
      return;
    }

    int i=text.focus.mainFocusSegment, j=text.segmentRelation.rank[text.focus.mainFocusSegment][1];

    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.drawLine((int)map.node[i].x,(int)map.node[i].y,(int)map.node[j].x,(int)map.node[j].y);


    g2.setColor(Color.red);
    draw_node_name(g2, i);
    draw_node_name(g2, j);
  }

  //Display parameters
  public void draw_parameters(Graphics2D g2) {
    g2.setColor(Color.yellow);
    g2.setFont(new Font("Dialog", Font.BOLD, 20));

    g2.drawString("Nodes="+map.node_num,200,sizeY-100);
    g2.drawString("Links="+map.link_num/2,200,sizeY-150);
  }

  public void paintComponent(Graphics g) {

    Graphics2D g2 = (Graphics2D)g;

    boolean change = getPanelSize();

//		if(oldSizeX == 0 || oldSizeY == 0)
//			init_coordinate();

    if (change) {
      calcu_coordinate();
    }


    draw_background(g2);	//Set background*

    draw_links(g2);			//Display links
    draw_nodes(g2);			//Display nodes

    draw_node_names(g2);	//Display node names
    draw_touch_node(g2);	//Display nodes touched by a mouse
    draw_touch_nodename(g2);	//Display node names touched by a mouse
//		draw_parameters(g2);	//Display parameters
  }

  public void update(Graphics g) {	//Avoid from blinking
    paintComponent(g);
  }

  //MouseListener
  public void mousePressed(MouseEvent me) {	//Press
    mousex = me.getX();
    mousey = me.getY();

    if (touch_num == -1) {
      old_mousex = mousex;
      old_mousey = mousey;
      dragging = true;
      return;
    }
    moving = true;
  }

  public void mouseReleased(MouseEvent me) {	//Release
    if (moving == true) {
      moving = false;
    }

    if (dragging == true) {
      dragging = false;
    }
  }

  public void mouseEntered(MouseEvent me) {}	//Enter an area
  public void mouseExited(MouseEvent me) {}	//Exit an area



  public void mouseClicked(MouseEvent me) {	//Click
    int click_num;

    mousex = me.getX();
    mousey = me.getY();
    click_num = CatchNode(mousex,mousey);

    if (click_num == -1) {
    }
    repaint();
  }

  //MouseMotionListener
  public void mouseDragged(MouseEvent me) {	//Dragging
    if (moving == true) {	//Move a node
      try {
        mousex = me.getX();
        mousey = me.getY();

        map.node[touch_num].x = mousex;
        map.node[touch_num].y = mousey;

        repaint();
      } catch (StackOverflowError e) {
        System.out.println("Stack OVERFLOW");
      }
    }

    if (dragging == true) {	//Move Field
      mousex = me.getX();
      mousey = me.getY();
      dx = old_mousex - mousex;
      dy = old_mousey - mousey;
      old_mousex = mousex;
      old_mousey = mousey;

      for (int i=0; i<map.node_num; i++) {
        map.node[i].x -= dx;
        map.node[i].y -= dy;
      }
      repaint();
    }
  }

  public void mouseMoved(MouseEvent me) {
    mousex = me.getX();
    mousey = me.getY();
    touch_num = CatchNode(mousex,mousey);

    if (touch_num >= 0) {
      text.focus.mainFocusSegment = touch_num;
      text.focus.subFocusSegment = text.segmentRelation.rank[touch_num][1];//

      if (touch_num != oldTouchNumber) {
        repaintOthersByTouch();
        oldTouchNumber = touch_num;
      }
    }
    repaint();
  }

  public int CatchNode(int mousex, int mousey) {	//Investigate number of selected node
    for (int i=0; i<map.node_num; i++)
      if ((mousex > map.node[i].x - map.node[i].radius && mousex < map.node[i].x + map.node[i].radius
           + map.node[i].name.length()*(24-2*map.node_num/25.0)) &&
          (mousey > map.node[i].y - map.node[i].radius && mousey < map.node[i].y + map.node[i].radius)) {
        return i;
      }

    return -1;
  }

  class NewMapData2 {

    class Node {
      String name;
      int id;

      double x;
      double y;
      double radius=10;

      int link_num;

      Color color;
    }

    Node node[];
    int node_num;


    //Node Relation Class
    class Relation {
      boolean link;			//Is exist a link between nodes?
      double value;

      double rdist;			// real distance for spring model
      double idist;			// ideal distance for spring model
    }

    Relation relation[][];		//Node Relation information
    int link_num;				//Number of all links

    double relate_low;

    void init_nodes(int num) {
      node_num = num;
      node = new Node[node_num];

      for (int i=0; i<node_num; i++) {
        node[i] = new Node();
      }

      relation = new Relation[node_num][node_num];

      for (int i=0; i<node_num; i++)
        for (int j=0; j<node_num; j++) {
          relation[i][j] = new Relation();
        }
    }

    NewMapData2(int num) {
      init_nodes(num);
    }

    //Variables for operation


    void set_relation_data(RelationalData rd) {
      Color color[] = new Color[4];
      color[0] = new Color(0xff,0x66,0x00);
      color[1] = new Color(0xa5,0x2a,0x2a);
      color[2] = new Color(0x00,0x00,0xcc);
      color[3] = new Color(0x00,0xdb,0x47);

      for (int i=0; i<rd.number; i++) {
        node[i].id = i;
        node[i].name = rd.name[i];
        node[i].x = 400 + 200 * Math.cos(i);
        node[i].y = 400 + 200 * Math.sin(i);
        node[i].color = color[0];
      }


      for (int i=0; i<node_num; i++)
        for (int j=0; j<node_num; j++) {
          relation[i][j].link = rd.link[i][j];
          relation[i][j].value = rd.cos[i][j];
        }

      link_num = rd.totalLinkNumber;
    }
  }
}
