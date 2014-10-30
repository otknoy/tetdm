//
// Visuzalization module for TETDM
// FlowPanel.java Version 0.36
// Copyright(C) 2009 - 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.FlowPanel;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.lang.Runtime;


public class FlowPanel extends VisualizationModule implements ActionListener {
  class LabelData2 {
    int keys_num;
    int segmentNumber;
    String names[];
    int level[];
    int label[][];
    int apseg[][];
  }

  PaintFlow flow;
  JPanel layer;

  JButton back,stop,start,origin,prep,next,dest;

  LabelData2 label;

  boolean first;

  public FlowPanel() {
    setModuleID(12);
    dataNumbers = new int[] {0,0,0,0,   // b,i,d,S	String 1+
                             0,2,0,0,    // bA,iA,dA,SA					doubleArray 1+
                             0,0,0
                            };     // bA2,iA2,dA2
  }

  public void initializePanel() {
    //Create draw pane
    createPaintFlow();
    first = true;

    //Create operation pane
    layer = new JPanel();
    layer.setLayout(new GridLayout(0,7));
    layer.setBackground(Color.CYAN);
    add("South",layer);

    back = new JButton("<");
    back.setBackground(Color.YELLOW);
    back.addActionListener(this);
    layer.add(back);

    stop = new JButton("[]");
    stop.setBackground(Color.YELLOW);
    stop.addActionListener(this);
    layer.add(stop);

    start = new JButton(">");
    start.setBackground(Color.YELLOW);
    start.addActionListener(this);
    layer.add(start);

    origin = new JButton("|<");
    origin.setBackground(Color.YELLOW);
    origin.addActionListener(this);
    layer.add(origin);

    prep = new JButton("-");
    prep.setBackground(Color.YELLOW);
    prep.addActionListener(this);
    layer.add(prep);

    next = new JButton("+");
    next.setBackground(Color.YELLOW);
    next.addActionListener(this);
    layer.add(next);

    dest = new JButton(">|");
    dest.setBackground(Color.YELLOW);
    dest.addActionListener(this);
    layer.add(dest);
  }

  public void createPaintFlow() {
    boolean english = false;

    initialize_data2();
    flow = new PaintFlow(label, english);	// needs data
    add(flow);
  }

  public void initializeData() {
    if (!first) {
      remove(flow);
      createPaintFlow();
      revalidate();
    }
    first = false;
  }

  public void initialize_data2() {
    label = new LabelData2();
    label.keys_num = text.keywordNumber;

    label.segmentNumber = text.segmentNumber;

    if (text.segmentNumber == 0) {
      label.segmentNumber = 1;
    }

    label.names = new String[text.keywordNumber];
    for (int i=0; i<text.keywordNumber; i++) {
      label.names[i] = text.keyword[i].word;
    }
    label.apseg = new int[text.keywordNumber][label.segmentNumber];
    for (int i=0; i<text.keywordNumber; i++)
      for (int j=0; j<text.segmentNumber; j++) {
        label.apseg[i][j] = text.keyword[i].appearingSegmentTable[j];
      }

    label.level = new int[text.keywordNumber];
//		for(int i=0;i<text.keywordNumber;i++)
//			label.level[i] = 0;

    label.label = new int[text.keywordNumber][label.segmentNumber];
//		for(int i=0;i<text.keywordNumber;i++)
//			for(int j=0;j<text.segmentNumber;j++)
//				label.label[i][j] = 1;
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        flow.repaint();
        break;
      case 1:
        if (flow.current_seg + 1 < flow.segmentNumber) {
          flow.stop_animation();
          flow.start_animation(true);
        }
        break;

      case 2:
        if (flow.current_seg + 1 < flow.segmentNumber) {
          flow.stop_animation();
          flow.start_animation(true);
        }
        break;
      case 3:
        if (flow.current_seg > 0) {
          flow.stop_animation();
          flow.start_animation(false);
        }
        break;
      case 4:
        flow.stop_animation();
        break;
      case 5:
        flow.move_scene(0);
        break;
      case 6:
        flow.move_scene(flow.segmentNumber-1);
        break;
      case 7:
        if (flow.current_seg > 0) {
          flow.move_scene(flow.current_seg - 1);
        }
        break;
      case 8:
        if (flow.current_seg + 1 < flow.segmentNumber) {
          flow.move_scene(flow.current_seg + 1);
        }
        break;
    }
  }

  public boolean setData(int dataID, int data[]) {
    switch (dataID) {
      case 1355:
        label.level = data;
        return true;
    }

    if (dataID >= 1355000 && dataID < 1355000+text.segmentNumber) {
      for (int i=0; i<text.keywordNumber; i++) {
        label.label[i][dataID-1355000] = data[i];
      }
      return true;
    } else {
      return false;
    }
  }

  public void setFont() {
    flow.init_data();
    flow.repaint();
  }

////Buttons
  //Action Listener
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == start) {	//Play
      displayOperations(2);
    }

    if (e.getSource() == back) {	//Reverse play
      displayOperations(3);
    }

    if (e.getSource() == stop) {	//Temporary stop
      displayOperations(4);
    }

    if (e.getSource() == origin) {	//Go to the first scene
      displayOperations(5);
    }

    if (e.getSource() == dest) {	//Go to the last scene
      displayOperations(6);
    }

    if (e.getSource() == prep) {	//Go to the previous scene
      displayOperations(7);
    }

    if (e.getSource() == next) {	//Go to the next scene
      displayOperations(8);
    }
  }


  class Worddata {
    String word;
    int x;
    int y;
    int segx[];
    int segy[];
    int fontsize[];
    int label[];
    int appear[];
    Color color[];
    int alpha[];
    int level;	// LEVEL

    int change;

    Worddata(int seg) {
      segx = new int[seg];
      segy = new int[seg];
      fontsize = new int[seg];
      label = new int[seg];
      appear = new int[seg];
      color = new Color[seg];
      alpha = new int[seg];

      for (int i=0; i<seg; i++) {	// Initial font size
        fontsize[i] = text.fontSize-4;
        color[i] = Color.white;
        alpha[i] = 255;
      }
    }
  }

  class Boxdata {
    Color color;

    int segx[];
    int segy[];
    int segxsize[];
    int segysize[];

    Boxdata(Color boxcolor, int seg) {
      color = boxcolor;

      segx = new int[seg];
      segy = new int[seg];
      segxsize = new int[seg];
      segysize = new int[seg];
    }
  }

  class Arcdata {
    Color color;

    int segx1[];
    int segy1[];
    int segx2[];
    int segy2[];
    int segwidth[];

    Arcdata(Color arccolor, int seg) {
      color = arccolor;

      segx1 = new int[seg];
      segy1 = new int[seg];
      segx2 = new int[seg];
      segy2 = new int[seg];
      segwidth = new int[seg];

      for (int i=0; i<seg; i++) { //Width of an arrow
        segwidth[i] = 1;
      }
    }
  }

  class Riverdata {
    int center[];
    int width[];
    int y;
    int ysize[];

    Riverdata(int seg, int c) {
      center = new int[seg];
      width = new int[seg];
      ysize = new int[seg];

      for (int i=0; i<seg; i++) {
        center[i] = c;
      }
    }
  }

  class Boatdata {
    int segx[];
    int segy[];
    int sizex[];
    int sizey[];
    int x;
    int y;
    int sx;
    int sy;

    Boatdata(int seg) {
      segx = new int[seg];
      segy = new int[seg];
      sizex = new int[seg];
      sizey = new int[seg];
    }
  }


  int messageX, messageY;



  //Class for drawing panel
  class PaintFlow extends JPanel implements Runnable {
    Worddata words[];
    Boxdata box[];
    Arcdata arc[];
    Riverdata river[];

    LabelData2 label;

    int keywordNumber, segmentNumber;

    //For display images
    Toolkit tk = getToolkit();
    Image image1, image2, image3;

    // 0:TOPIC, 1:SEED, 2:MAIN, 3:NEW, 4:BYWAY, 5:SUB
    Color labelcolor[] = {new Color(0xB2,0x22,0x22), Color.blue, Color.black, new Color(0x00,0x79,0x27), new Color(0x80,0x00,0x80), Color.red};
    // 0:emergence, 1:expansion, 2:incorporation, 3:divergence, 4:convergence, 5:out-emergence, 6:out-expansion
    Color arccolor[] = {new Color(0x00,0xff,0xff), Color.black, Color.green, Color.red, Color.green, new Color(0x00,0xff,0xff), new Color(0x80,0x00,0x80)};

    int current_seg = 0;	//Display number of segments
    int divided_num = 80;	//Number of division for animation
    int step_num = 0;		//Number of animation frame
    boolean animation = false;	//For animation play
    boolean forward = true;		//Direction of animation play

    Thread th;					//For thread execution

    int count;
    double odds = 1.0;

    //Constructor
    PaintFlow(LabelData2 lab, boolean english) {
      label = lab;
//			image1 = tk.getImage(text.imagePath + "ocean.jpg");		//Image for background
      //			image2 = tk.getImage(text.imagePath + "river.jpg");		//Image for background

      image1 = tk.getImage(myModulePath+"ocean.jpg");		//Image for background
      image2 = tk.getImage(myModulePath+"river.jpg");		//Image for background



      count = 0;
      if (english) {
        odds = 0.7;
      }
    }

    //Panel initialization
    public void init_data() {
      int rivercenter[] = {534,320,106,534};
      int riverwidth[] = {150,60,60,60};
      int rivery[] = {20,20,0,250};
      int riversizey[] = {0,780,0,550};

      int box_inity[] = {20, 50, 320, 50, 250, 320};
      int box_rn[] = {0,1,1,2,2,3};				//connrensponding number of the river

      int check = 0;



      read_data(1);	//READ data


      //River initialization
      river = new Riverdata[4];
      for (int i=0; i<4; i++) {
        river[i] = new Riverdata(segmentNumber, rivercenter[i]);
        river[i].width[0] = riverwidth[i];					//Set initial widths of rivers
        river[i].y = rivery[i];
      }

      //Box initialization
      box = new Boxdata[6];
      for (int i=0; i<6; i++) {
        box[i] = new Boxdata(labelcolor[i], segmentNumber);
      }

      //Arrow initialization
      arc = new Arcdata[7];
      for (int i=0; i<7; i++) {
        arc[i] = new Arcdata(arccolor[i], segmentNumber);  //x1,x2
      }

      //Calculate position of each word, each box, river width and arrow width
      for (int i=0; i<segmentNumber; i++) {
        if (i > 0)
          for (int j=0; j<4; j++) {
            river[j].width[i] = river[j].width[i-1];
          }
        check = 1;

        while (check == 1) {
          check = 0;
          for (int j=0; j<6; j++) {
            calc_xy(j+1, river[box_rn[j]].center[i], river[box_rn[j]].width[i], box_inity[j], i);
          }

          //Position Re-adjustment by the size of Boxes for SEED, MAIN, SUB
          box_minor_arrange(2, box[1].segy[i], 200 - box[1].segysize[i]/2, i);
          box_minor_arrange(3, box[2].segy[i], 550 - box[2].segysize[i]/2, i);
          box_minor_arrange(6, box[5].segy[i], 500 - box[5].segysize[i]/2, i);

          //Position adjustment by the overlapped boxes
          box_minor_arrange2(2,3,i);
          box_minor_arrange2(4,5,i);
//					box_minor_arrange2(5,6,i);

          //Change river width by boxes
          if (box[1].segy[i] < 30 || box[2].segy[i] + box[2].segysize[i] > 750) {
            river[1].width[i] += 10;
            check = 1;
          }

          if (box[3].segy[i] < 30 || box[4].segy[i] + box[4].segysize[i] > 500) {
            river[2].width[i] += 10;
            check = 1;
          }

          if (box[5].segy[i] + box[5].segysize[i] > 750) {
            river[3].width[i] += 10;
            check = 1;
          }
        }
        //Change river length by boxes
        river[1].ysize[i] = riversizey[1];
        river[2].ysize[i] = box[4].segy[i] + box[4].segysize[i] + 5;
        river[3].ysize[i] = riversizey[3];

        set_arcwidth(i);

      }

      set_arcxy();		//Set arrows coordinates

      for (int i=0; i<segmentNumber-1; i++) {	//Initialization unappeared words
        calc_xy_zero(i);
      }

      set_xy(current_seg);	//Copy word xy-coordinats as display position

      //////////////////////////////

      messageX = 50;
      messageY = 720;



      repaint();
    }

    public void read_data(int x) {
      keywordNumber = label.keys_num;
      segmentNumber = label.segmentNumber;

//			System.out.println(keywordNumber+":"+segmentNumber);

      words = new Worddata[keywordNumber];
      for (int i=0; i<keywordNumber; i++) {
        words[i] = new Worddata(segmentNumber);
      }

      for (int i=0; i<keywordNumber; i++) {
        words[i].word = label.names[i];
        words[i].level = label.level[i];

        for (int j=0; j<segmentNumber; j++) {
          words[i].label[j] = label.label[i][j];
          words[i].appear[j] = label.apseg[i][j];
          if (words[i].appear[j] == 2) { //Exchange 2 for 1
            words[i].appear[j] = 1;
          }
        }
      }
    }

    //Calculate position of words and boxes for the segment [seg] and the [label]
    public void calc_xy(int label, int river_center, int river_width, int offsety, int seg) {
      int length;
      int fontmax, writex, writey;
      int margin = 10;

      fontmax = 0;
      writex = margin;
      writey = 2;

      for (int i=0; i<keywordNumber; i++)
        if (words[i].label[seg] == label) {
          if (seg == 0) { //Set font size and transparancy
            words[i].fontsize[seg] = (text.fontSize-4) + words[i].appear[seg];
          } else {
            words[i].fontsize[seg] = words[i].fontsize[seg-1] + words[i].appear[seg];
            if (words[i].appear[seg] == 1) {
              words[i].alpha[seg] = 255;
            } else {
              words[i].alpha[seg] = words[i].alpha[seg-1] - 10;  //
            }

            if (words[i].alpha[seg] < 70) {
              words[i].alpha[seg] = 70;
            }
          }

          if (words[i].fontsize[seg] > fontmax) {
            fontmax = words[i].fontsize[seg];
          }

          length = words[i].word.length();
          if (writex + length*words[i].fontsize[seg]*odds + margin > river_width && writex != margin) {	// *0.6 if english
            writey += (fontmax);
            fontmax = words[i].fontsize[seg];
            writex = margin;
          }
          words[i].segx[seg] = river_center-river_width/2 + writex;
          words[i].segy[seg] = offsety + writey + fontmax;
          words[i].color[seg] = labelcolor[label-1];

          if (seg > 0) { //Set color: Succeeding original color
            if (words[i].color[seg-1] == labelcolor[5]) { //Experienced SUB
              words[i].color[seg] = labelcolor[5];
            }

            if ((label == 2 || label == 3) && words[i].color[seg-1] == labelcolor[3]) { //NEW -> SEED or MAIN
              words[i].color[seg] = labelcolor[3];
            }

            if ((label == 2 || label == 3) && words[i].color[seg-1] == labelcolor[4]) { //BYWAY -> SEED or MAIN
              words[i].color[seg] = labelcolor[4];
            }
          }

          writex += (length*words[i].fontsize[seg]*odds + margin/2);	//*0.6 if english
        }

      box[label-1].segysize[seg] = writey + fontmax +	2;
      box[label-1].segx[seg] = river_center-river_width/2 + margin/2;
      box[label-1].segy[seg] = offsety;
      box[label-1].segxsize[seg] = river_width - margin;
    }

    //change position of a word in a box from [oldp] to [newp]
    void box_minor_arrange(int label, int oldp, int newp, int seg) {
      int diff = newp - oldp;

      for (int i=0; i<keywordNumber; i++)
        if (words[i].label[seg] == label) {
          words[i].segy[seg] += diff;
        }

      box[label-1].segy[seg] = newp;
    }

    //Detect overlapped boxes [label_up] and [label], and adjust position
    void box_minor_arrange2(int label_up, int label, int seg) {
      int offsety;

      offsety = box[label-1].segy[seg];

      if (box[label_up-1].segy[seg] + box[label_up-1].segysize[seg] + 30 > box[label-1].segy[seg]) {
        box[label-1].segy[seg] = box[label_up-1].segy[seg] + box[label_up-1].segysize[seg] + 30;
        for (int i=0; i<keywordNumber; i++)
          if (words[i].label[seg] == label) {
            words[i].segy[seg] += (box[label_up-1].segy[seg] + box[label_up-1].segysize[seg] + 30 - offsety);
          }
      }
    }

    //Set Width of arrows
    void set_arcwidth(int seg) {
      if (seg > 0) //Set arrows
        for (int i=0; i<keywordNumber; i++)
          if (words[i].label[seg] != words[i].label[seg-1]) {
            if (words[i].label[seg] == 2 && words[i].label[seg-1] == -1) { //emergence
              arc[0].segwidth[seg-1]++;
            }
            if (words[i].label[seg] == 4 && words[i].label[seg-1] == -1) { //out-emergence
              arc[5].segwidth[seg-1]++;
            }

            if (words[i].label[seg] == 3) { //expansion
              arc[1].segwidth[seg-1]++;
            }
            if (words[i].label[seg] == 5) { //out-expansion
              arc[6].segwidth[seg-1]++;
            }

            if (words[i].label[seg-1] == 5 || (words[i].label[seg-1] == 4 && words[i].label[seg] != 5)) { //incorporation
              arc[2].segwidth[seg-1]++;
            }
            if (words[i].label[seg] == 6) { //divergence
              arc[3].segwidth[seg-1]++;
            }
            if (words[i].label[seg-1] == 6) { //convergence
              arc[4].segwidth[seg-1]++;
            }
          }
    }

    void set_arcxy() {
      for (int j=0; j<segmentNumber; j++) { //x coordinate
        arc[0].segx1[j] = river[1].center[j];
        arc[0].segx2[j] = river[1].center[j];
        arc[5].segx1[j] = river[2].center[j];
        arc[5].segx2[j] = river[2].center[j];

        arc[1].segx1[j] = river[1].center[j];
        arc[1].segx2[j] = river[1].center[j];
        arc[6].segx1[j] = river[2].center[j];
        arc[6].segx2[j] = river[2].center[j];

        arc[2].segx1[j] = river[1].center[j] - river[1].width[j]/2;
        arc[2].segx2[j] = river[2].center[j] + river[2].width[j]/2;
        arc[3].segx1[j] = river[1].center[j] + river[1].width[j]/2;
        arc[3].segx2[j] = river[3].center[j] - river[3].width[j]/2;
        arc[4].segx1[j] = river[1].center[j] + river[1].width[j]/2;
        arc[4].segx2[j] = river[3].center[j] - river[3].width[j]/2;
      }

      for (int j=0; j<segmentNumber; j++) { //y coordinate
        arc[0].segy1[j] = 0;
        arc[0].segy2[j] = box[1].segy[j];
        arc[5].segy1[j] = 0;
        arc[5].segy2[j] = box[3].segy[j];

        arc[1].segy1[j] = box[1].segy[j] + box[1].segysize[j];
        arc[1].segy2[j] = box[2].segy[j];
        arc[6].segy1[j] = box[3].segy[j] + box[3].segysize[j];
        arc[6].segy2[j] = box[4].segy[j];

        arc[2].segy1[j] = 250;
        arc[2].segy2[j] = 200;
        arc[3].segy1[j] = 300;
        arc[3].segy2[j] = 350;
        arc[4].segy1[j] = 450;
        arc[4].segy2[j] = 400;
      }
    }

    public void calc_xy_zero(int seg) { //Initialization of unappeared
      for (int i=0; i<keywordNumber; i++)
        if (words[i].label[seg] == -1) {
          words[i].segx[seg] = words[i].segx[seg+1];
          words[i].segy[seg] = 0;
          words[i].color[seg] = words[i].color[seg+1];
        }
    }

    void set_xy(int seg) {
      for (int i=0; i<keywordNumber; i++) {
        words[i].x = words[i].segx[seg];
        words[i].y = words[i].segy[seg];
      }
    }



    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// Anti aliasing

      int red,green,blue,alpha;
      double dx=1.0,dy=1.0;

      getPanelSize();

      dx = sizeX/640.0;
      dy = sizeY/800.0;

      //Draw background image//
      g2.drawImage(image2,0,0,sizeX,sizeY,this);

      if (count == 0) {
        current_seg = 0;
        init_data();
        count = 1;
      }

      //Draw rivers//
      g2.setColor(new Color(0xee,0xdd,0xaa));
      g2.setStroke(new BasicStroke(12));
      for (int i=1; i<4; i++) {
        g2.drawRect((int)((river[i].center[current_seg] - diffarray(river[i].width)/2-2)*dx), (int)((river[i].y-2)*dy),
                    (int)((diffarray(river[i].width)+4)*dx), (int)((diffarray(river[i].ysize)+4)*dy));
        g2.drawImage(image1,(int)((river[i].center[current_seg] - diffarray(river[i].width)/2)*dx), (int)(river[i].y*dy),
                     (int)(diffarray(river[i].width)*dx), (int)(diffarray(river[i].ysize)*dy),this);
      }

      //Draw arrows
      for (int i=0; i<7; i++) {
        g2.setColor(arc[i].color);
        if (i == 1 || i == 6) {
          g2.setStroke(new BasicStroke(diffarray(arc[i].segwidth)*5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        } else {
          g2.setStroke(new BasicStroke(diffarray(arc[i].segwidth)*5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        }
        g2.drawLine((int)(diffarray(arc[i].segx1)*dx), (int)(diffarray(arc[i].segy1)*dy), (int)(diffarray(arc[i].segx2)*dx), (int)(diffarray(arc[i].segy2)*dy));
      }

      //Draw background of TOPIC//
      g2.setColor(Color.white);
      g2.fillRect((int)(diffarray(box[0].segx)*dx), (int)(diffarray(box[0].segy)*dy), (int)(diffarray(box[0].segxsize)*dx), (int)(diffarray(box[0].segysize)*dy));


      double dz = dx;
      if (dy < dx) {
        dz = dy;
      }

      //Draw boxes//
      g2.setStroke(new BasicStroke(2));
      for (int i=0; i<6; i++) {
        g2.setColor(box[i].color);
        g2.drawRect((int)(diffarray(box[i].segx)*dx), (int)(diffarray(box[i].segy)*dy), (int)(diffarray(box[i].segxsize)*dx), (int)(diffarray(box[i].segysize)*dy));
      }

      //Draw frame information//
      g2.setColor(new Color(0xB2,0x22,0x22));
//			g2.setColor(new Color(0xB2,0x22,0x22,0x66));
      g2.setFont(new Font("Dialog", Font.BOLD, (int)(24*dz)));
      g2.drawString("[SEG:FRAME]="+(current_seg+1)+":"+step_num, (int)(messageX*dx), (int)(messageY*dy));

      //Draw words//
      for (int i=0; i<keywordNumber; i++) {
        red = words[i].color[current_seg].getRed();
        green = words[i].color[current_seg].getGreen();
        blue = words[i].color[current_seg].getBlue();
        if (current_seg < segmentNumber-1) {
          alpha = words[i].alpha[current_seg+1];
        } else {
          alpha = words[i].alpha[current_seg];
        }
        g2.setColor(new Color(red,green,blue,alpha));
//				if(dz > 0.8)
//					g2.setFont(new Font("Dialog", Font.BOLD, (int)(words[i].fontsize[current_seg]*dz)));
//				else
        g2.setFont(new Font("Dialog", Font.BOLD, (int)(words[i].fontsize[current_seg])));
        g2.drawString(words[i].word, (int)(words[i].x*dx), (int)(words[i].y*dy));

        if (words[i].level == 4) {
          g2.drawLine((int)(words[i].x*dx), (int)(words[i].y*dy), (int)(words[i].x*dx)+words[i].word.length()*words[i].fontsize[current_seg]/2, (int)(words[i].y*dy));
        }

        if (words[i].level > 4) {
          g2.drawLine((int)(words[i].x*dx), (int)(words[i].y*dy), (int)(words[i].x*dx)+words[i].word.length()*words[i].fontsize[current_seg], (int)(words[i].y*dy));
          /*						System.out.println(words[i].word+"("+words[i].level+")");*/
        }
      }
    }

    public int diffarray(int array[]) {
      if (current_seg < segmentNumber - 1) {
        return array[current_seg] + (array[current_seg+1] - array[current_seg]) * step_num/divided_num;
      }
      return array[current_seg];
    }

    public void update(Graphics g) {	//Avoid from blinking
      paintComponent(g);
    }

    //Related to animation
    void start_animation(boolean direction) {	//Start animation
      Thread th = new Thread(this);
      animation = true;
      forward = direction;
      if (step_num == 0) {
        if (forward) {
          step_num = 0;
        } else {
          step_num = divided_num;
          current_seg--;
        }
      }
      th.start();
    }

    void stop_animation() {	//Stop animation
      animation = false;
    }

    boolean step_forward() {	//Calculate differences
      if (forward) {
        step_num++;
        if (step_num == divided_num) {
          current_seg++;
          set_xy(current_seg);
          step_num = 0;

          if (current_seg+1 == segmentNumber) {
            return false;
          } else {
            return true;
          }
        }
      } else {
        step_num--;
        if (step_num == 0) {
          set_xy(current_seg);

          if (current_seg == 0) {
            return false;
          } else {
            current_seg--;
            step_num = divided_num - 1;
            return true;
          }
        }
      }

      if (current_seg+1 < segmentNumber)
        for (int i=0; i<keywordNumber; i++)
          if (words[i].label[current_seg] != words[i].label[current_seg+1]) {
            words[i].x = words[i].segx[current_seg] + (words[i].segx[current_seg+1] - words[i].segx[current_seg]) * step_num / divided_num;
            words[i].y = words[i].segy[current_seg] + (words[i].segy[current_seg+1] - words[i].segy[current_seg]) * step_num / divided_num;
            words[i].change = 1;
          } else {
            words[i].y = words[i].segy[current_seg] + (words[i].segy[current_seg+1] - words[i].segy[current_seg]) * step_num / divided_num;
            words[i].change = 0;
          }
      return true;
    }

    void move_scene(int seg) {
      stop_animation();
      step_num = 0;
      current_seg = seg;
      set_xy(current_seg);
      repaint();
    }

    //Execute animation
    public void run() {
      try {
        while (animation) {
          animation = step_forward();
          repaint();
          th.sleep(10);
        }
      } catch (InterruptedException e) {}
    }
  }
}
