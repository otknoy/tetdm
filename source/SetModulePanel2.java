//
// Core Program for TETDM
// SetModulePanel2.java Version 0.49
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
import java.util.logging.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;


public class SetModulePanel2 extends JPanel {

  JPanel layer,upperlayer,elselayer,checkboxlayer,toolnamelayer,combinelayer;
  JLabel label,plabel,lslabel,toolnamelabel,toolinfolabel;
  JLabel [] combine_label;
  JButton reset,reduce,add;
  JCheckBox[] ckbox;
  String buttonNamesInJapanese[];
  JTextArea area,d_area;
  JToggleButton all_check;

  boolean forUser;

  //For objects operation
  int mousex, mousey;			//Coordinates for a mouse
  int old_mousex, old_mousey;	//Last Coordinates for a mosue
  int dx, dy;					//Distance for mouse moving

  int touchNumber,touchNumber2;				//Node Number of being touched
  int lastTouchNumber, lastTouchNumber2;

  int clickNumber,clickNumber2;
  int lastClickNumber,lastClickNumber2;

  boolean moving;			//Flag for node moving
  boolean displayConnection[];

  ModuleData moduleData;
  Control control;
  SetToolPanel setToolPanel;
  NewMapData2 map,map2;

  int sizeX,sizeY;
  int panelSizeY;

  int imageSize=16,maxImageSize=16;

  int miningIndex[], visualizationIndex[];
  int lastMiningIndex[], lastVisualizationIndex[];

  int panelX[], panelY[];

  int panelNumber = 1;
  int maxPanelNumber = 10;

  int [][] visual_mining;
  String tl_type[] = new String[] {"","(Si)","(Pr)","(sP)",""};
  Color toolTypeColor[] = {Color.white, Color.red, new Color(0xE0,0x66,0xFF), new Color(0x00,0x66,0xff), Color.black};

  int sortedVIndex[];
  int sortedMIndex[];


  String useJapanese[];
  String readmeFile[], readmeFile2[];

  public int oldSizeX, oldSizeY;
  public double changeX, changeY;
  int panelLetterSize = 14;
  int letter_size=12,BLSize=11;
  int maxLetterSize=12;

  int [][] visual_pairnum;
  int visual_haspair=0;
  int visual_pairsum=0;
  int vchair_num=0;
  int [] cchair;
  int [] vchair;




  Image [] toolTypePicture = new Image [4];
  Image [] connectionPicture = new Image [4];
  Image arrowImage;
  ImageIcon [] toolTypePicture2 = new ImageIcon [4];
  ImageIcon [] connectionPicture2 = new ImageIcon [4];

  String newLine;

  SetModulePanel2(Control control) {
    this.control = control;
    setLayout(new BorderLayout());

    forUser = true;

    setToolPanel = new SetToolPanel(control);

    initializeData();

    useJapanese = FILEIO.TextFileAllReadCodeArray(new File(control.absolutePath + "source" + File.separator + "useJapanese.txt"));

    initializePanel();

    read_readmefile();
    set_noderelate();//
    compare_pairnum();//

    createSortedIndex();

    initializeModuleSet();

    repaint();
  }

  public void setMenu() {
    if (control.sp1.inJapanese) {
      area.setText(useJapanese[9]);
      d_area.setText(useJapanese[9]);
      all_check.setText(useJapanese[11]);
      for (int i=0; i<ckbox.length; i++) {
        ckbox[i].setText(useJapanese[i+11]);
      }
      toolnamelabel.setText(useJapanese[9]);
      toolinfolabel.setText(useJapanese[0]);

      plabel.setText(useJapanese[1]);
      add.setText(useJapanese[2]);
      reduce.setText(useJapanese[3]);
      reset.setText(useJapanese[4]);

      for (int i=0; i<control.moduleData.moduleIDs.length; i++) {
        map.node[i].name = control.moduleData.miningModuleNamesInJapanese[i];
        map.node[i].name_length = map.node[i].name.length()-checkNames(0,i)+checkNames(0,i)/2+1;
      }

      for (int i=0; i<control.moduleData.visuModuleIDs.length; i++) {
        map2.node[i].name = control.moduleData.visualizationModuleNamesInJapanese[i];
        map2.node[i].name_length = map2.node[i].name.length()-checkNames(1,i)+checkNames(1,i)/2+1;
      }
    } else {
      String ckbox_name [] = {"all check","text update","option","focus","get data"};

      area.setText("Please choose a tool");
      d_area.setText("Please choose a tool");
      all_check.setText("all check");
      for (int i=0; i<ckbox.length; i++) {
        ckbox[i].setText(ckbox_name[i]);
      }
      toolnamelabel.setText("Please choose a tool");
      toolinfolabel.setText("Please drag execution/visualization tools to each panel");

      plabel.setText("Panel");
      add.setText("Add +");
      reduce.setText("Reduce -");
      reset.setText("Reset");

      for (int i=0; i<control.moduleData.moduleIDs.length; i++) {
        map.node[i].name = control.moduleData.module_names[i];
        map.node[i].name_length = map.node[i].name.length()-checkNames(0,i)+checkNames(0,i)/2+1;
      }

      for (int i=0; i<control.moduleData.visuModuleIDs.length; i++) {
        map2.node[i].name = control.moduleData.visu_module_names[i];
        map2.node[i].name_length = map2.node[i].name.length()-checkNames(1,i)+checkNames(1,i)/2+1;
      }

    }

    repaint();
  }

  public void initializeData() {
    map = new NewMapData2(control.moduleData.moduleIDs.length);
    map2 = new NewMapData2(control.moduleData.visuModuleIDs.length);
    map.set_relation_data(0);
    map2.set_relation_data(1);

    panelX = new int[maxPanelNumber+1];
    panelY = new int[2];

    miningIndex = new int[maxPanelNumber];
    visualizationIndex = new int[maxPanelNumber];
    lastMiningIndex = new int[maxPanelNumber];
    lastVisualizationIndex = new int[maxPanelNumber];

    sortedMIndex = new int[map.node_num];
    sortedVIndex = new int[map2.node_num];


    touchNumber = touchNumber2 = lastTouchNumber = lastTouchNumber2 = -1;
    clickNumber = clickNumber2 = lastClickNumber = lastClickNumber2 = -1;
    newLine = System.getProperty("line.separator");

    moving = false;

//		control.writeActionLog("created");
  }

  public void initializePanel() {
    //Listener
    addMouseListener(new MyMouse());			//MouseListener
    addMouseMotionListener(new MyMouse());	//MouseMotionListener

    //RIGHT SIDE
    if (control.sp1.inJapanese) {
      area = new JTextArea(useJapanese[9],4,15);
    } else {
      area = new JTextArea("Please choose a tool",4,15);
    }
    area.setLineWrap(true);
    area.setFont(new Font("Dialog", Font.BOLD, BLSize));

    if (control.sp1.inJapanese) {
      d_area = new JTextArea(useJapanese[9],4,15);
    } else {
      d_area = new JTextArea("Please choose a tool",4,15);
    }

    d_area.setLineWrap(true);
    d_area.setFont(new Font("Dialog", Font.BOLD, BLSize));

    //UPPER
    upperlayer = new JPanel();
    upperlayer.setLayout(new GridLayout(1,1));//TEMPORARY
    add("North", upperlayer);

    //UPPER1 checkbox
    checkboxlayer = new JPanel();
    checkboxlayer.setLayout(new GridLayout(1,5));
    checkboxlayer.setBackground(Color.green);
//		upperlayer.add(checkboxlayer);      //TEMPORARY NOT USE

    ckbox = new JCheckBox[5];
    String ckbox_name [] = {"all check","text update","option","focus","get data"};

    if (control.sp1.inJapanese) {
      all_check = new JToggleButton(useJapanese[11],false);
    } else {
      all_check = new JToggleButton("all check",false);
    }

    all_check.addActionListener(new MyBtn());
    all_check.setFont(new Font("Dialog", Font.BOLD, BLSize));
    checkboxlayer.add(all_check);

    displayConnection = new boolean[7];

    for (int i=0; i<ckbox.length; i++) {

      if (control.sp1.inJapanese) {
        ckbox[i] = new JCheckBox(useJapanese[i+11]);
      } else {
        ckbox[i] = new JCheckBox(ckbox_name[i]);
      }
      ckbox[i].setBorderPainted(true);
      ckbox[i].setBackground(Color.green);
      ckbox[i].setOpaque(false);
      ckbox[i].addActionListener(new MyBtn());
      if (i != 0) {
        checkboxlayer.add(ckbox[i]);
      }
      displayConnection[i] = false;
    }
//	    ckbox[1].setSelected(true);
    ckbox[3].setSelected(true);
    displayConnection[3] = true;


    //UPPER2 toolname
    toolnamelayer = new JPanel();
    toolnamelayer.setLayout(new GridLayout(1,2));
    toolnamelayer.setBackground(Color.white);
//		upperlayer.add(toolnamelayer);//TEMPORARY NOT USE

    if (control.sp1.inJapanese) {
      toolnamelabel = new JLabel(useJapanese[9]);
    } else {
      toolnamelabel = new JLabel("Please choose a tool");
    }
    toolnamelabel.setBackground(Color.white);
//		toolnamelabel.setOpaque(true);
    toolnamelabel.setBorder(new LineBorder(Color.BLACK));
    toolnamelabel.setFont(new Font("Dialog", Font.BOLD, BLSize));
    toolnamelayer.add(toolnamelabel);

    combinelayer = new JPanel();
    combinelayer.setLayout(new GridLayout(0,4));
    combinelayer.setBorder(new LineBorder(Color.BLACK));
    combinelayer.setBackground(Color.white);
    toolnamelayer.add(combinelayer);

    //UPPER3 information
    if (control.sp1.inJapanese) {
      toolinfolabel = new JLabel(useJapanese[0]);
    } else {
      toolinfolabel = new JLabel("Please drag execution/visualization tools to each panel");
    }
    toolinfolabel.setBackground(Color.white);
    toolinfolabel.setOpaque(true);
    toolinfolabel.setBorder(new LineBorder(Color.BLACK));
    toolinfolabel.setFont(new Font("Dialog", Font.BOLD, BLSize));
//		upperlayer.add(toolinfolabel);	//TEMPORARY NOT USE

    //UPPER4 panel buttons
    layer = new JPanel();
    layer.setLayout(new GridLayout(0,4));
    layer.setBackground(new Color(0xff,0xcc,0xff));
    upperlayer.add(layer);

    if (control.sp1.inJapanese) {
      plabel = new JLabel(useJapanese[1]);
    } else {
      plabel = new JLabel("Panel");
    }
    plabel.setFont(new Font("Dialog", Font.BOLD, BLSize));
    layer.add(plabel);

    if (control.sp1.inJapanese) {
      add = new JButton(useJapanese[2]);
    } else {
      add = new JButton("Add +");
    }
    add.addActionListener(new MyBtn());
    add.setFont(new Font("Dialog", Font.BOLD, BLSize));
    layer.add(add);

    if (control.sp1.inJapanese) {
      reduce = new JButton(useJapanese[3]);
    } else {
      reduce = new JButton("Reduce -");
    }
    reduce.addActionListener(new MyBtn());
    reduce.setFont(new Font("Dialog", Font.BOLD, BLSize));
    layer.add(reduce);

    if (control.sp1.inJapanese) {
      reset = new JButton(useJapanese[4]);
    } else {
      reset = new JButton("Reset");
    }
    reset.addActionListener(new MyBtn());
    reset.setFont(new Font("Dialog", Font.BOLD, BLSize));
    layer.add(reset);


    //Images
    String pic_name[]= {"Si.png","Pr.png","sP.png","no.png","text_r.png","option_r.png","focus.png","data_r.png"};
    String pic_ad = control.absolutePath + "source" + File.separator + "type" + File.separator;

    arrowImage = Toolkit.getDefaultToolkit().getImage(pic_ad + "com.png");

    for (int i=0; i< toolTypePicture.length; i++) {
      toolTypePicture[i] = Toolkit.getDefaultToolkit().getImage(pic_ad + pic_name[i]);
      toolTypePicture2[i] = new ImageIcon(pic_ad + pic_name[i]);
    }

    for (int i=0; i< connectionPicture.length; i++) {
      connectionPicture[i] = Toolkit.getDefaultToolkit().getImage(pic_ad + pic_name[i+4]);
      connectionPicture2[i] = new ImageIcon(pic_ad + pic_name[i+4]);
    }

    combine_label = new JLabel[connectionPicture.length];

    for (int i=0; i<connectionPicture.length; i++) {
      combine_label[i] = new JLabel();

      combine_label[i].setIcon(new ImageIcon(connectionPicture2[i].getImage().getScaledInstance(25, 23, Image.SCALE_SMOOTH)));
      combine_label[i].setBackground(Color.white);
      combine_label[i].setOpaque(true);
      combine_label[i].setFont(new Font("Dialog", Font.BOLD, BLSize));
      combinelayer.add(combine_label[i]);
    }
  }



  public void read_readmefile() { //TextFileAllReadCode
    readmeFile = new String[map.node_num];

    for (int i=0; i<map.node_num; i++) {
      readmeFile[i] = FILEIO.TextFileAllReadCode(new File(control.absolutePath + "module" + File.separator + "MiningModules" + File.separator + map.node[i].eng_name + File.separator + "README.txt"));
    }

    readmeFile2 = new String[map2.node_num];

    for (int i=0; i<map2.node_num; i++) {
      readmeFile2[i] = FILEIO.TextFileAllReadCode(new File(control.absolutePath + "module" + File.separator + "VisualizationModules" + File.separator + map2.node[i].eng_name + File.separator + "README.txt"));
    }
  }


  public void set_noderelate() {
    int i=0,j=0,m=0,ll=0;
    String line;
    int count=0;
    visual_mining = new int[map2.node_num][map.node_num];
    for (i=0; i<map2.node_num; i++) {
      for (j=0; j<map.node_num; j++) {
        visual_mining[i][j]=-1;
      }
    }

    for (i=0; i<control.moduleData.moduleIDs.length; i++) {
      map.node[i].pair_num = control.moduleData.mining[i].pairingVisualizationID.length;
      for (j=0; j<control.moduleData.visuModuleIDs.length; j++) {
        for (int l=0; l<map.node[i].pair_num; l++) {
          if (control.moduleData.mining[i].pairingVisualizationID[l]==map2.node[j].ID) {
            map.relation[i][j].suggest=true;
            map2.node[j].existpair+=1;
          }
        }
      }
    }

    for (j=0; j<map2.node_num; j++) {
      ll=0;

      for (i=0; i<map.node_num; i++) {
        if (map.relation[i][j].suggest==true) {
          visual_mining[j][ll]=i;
          ll+=1;
        }
      }
    }
  }

  public void initializePanelXY() {
    sizeX = getWidth();
    sizeY = getHeight();

    for (int i=0; i<panelNumber+1 && i<maxPanelNumber+1; i++) {
      panelX[i] = (sizeX / panelNumber) * i;
    }

    panelY[0] = upperlayer.getSize().height;
    panelY[1] = sizeY/3;

    panelSizeY = sizeY/3 - upperlayer.getSize().height;
  }

  public void initializeModuleSet() {
    control.getPanelInformation();

    for (int i=0; i<maxPanelNumber; i++) {
      miningIndex[i] = lastMiningIndex[i] = -1;
      visualizationIndex[i] = lastVisualizationIndex[i] = -1;
    }

    for (int i=0; i<control.panelNumber; i++) {
      for (int j=0; j<map.node_num; j++) {
        if (control.miningIDs[i] == map.node[j].ID) {
          lastMiningIndex[i] = miningIndex[i] = j;
        }
      }
      for (int j=0; j<map2.node_num; j++) {
        if (control.visualizationIDs[i] == map2.node[j].ID) {
          lastVisualizationIndex[i] = visualizationIndex[i] = j;
        }
      }
      System.out.println((i+1)+":	"+map.node[miningIndex[i]].name+"	+	"+map2.node[visualizationIndex[i]].name);
    }

    panelNumber = control.panelNumber;

    initializePanelXY();
  }

  public void compare_pairnum() {
    visual_pairnum = new int[map2.node_num][2];
    cchair = new int[map.node_num];
    vchair = new int[map2.node_num];
    for (int i=0; i<map.node_num; i++) {
      cchair[i]=-1;
    }
    for (int i=0; i<map2.node_num; i++) {
      vchair[i]=-1;
    }
    int c=0,p=0,kk=0,aa=0;
    int cmp=-1,cmp2=-1;
    boolean flag = false;
    for (int i=0; i<map2.node_num; i++) {
      visual_pairnum[i][0]=map2.node[i].id;////////////////
      visual_pairnum[i][1]=map2.node[i].existpair;////////////////
      if (visual_pairnum[i][1]!=0) {
        visual_haspair+=1;
      }
      visual_pairsum+=visual_pairnum[i][1];
    }
    for (int j=0; j<map2.node_num; j++) {
      for (int i=1; i<map2.node_num; i++) {
        if (visual_pairnum[i-1][1]<visual_pairnum[i][1]) {
          cmp=visual_pairnum[i-1][1];
          visual_pairnum[i-1][1]=visual_pairnum[i][1];
          visual_pairnum[i][1]=cmp;
          cmp2=visual_pairnum[i-1][0];
          visual_pairnum[i-1][0]=visual_pairnum[i][0];
          visual_pairnum[i][0]=cmp2;
        }
      }
    }

    for (int i=0; i<map2.node_num; i++) {
      for (int j=0; j<map.node_num; j++) {
        if (visual_mining[visual_pairnum[i][0]][j]!=-1) {
          flag = false;
          if (i==0) {
            cchair[kk]=visual_mining[visual_pairnum[i][0]][j];
            kk+=1;
          } else {
            for (int n=0; n<kk; n++) {
              if (cchair[n]!=visual_mining[visual_pairnum[i][0]][j]) {
                flag = true;
              } else {
                flag = false;
                break;
              }
            }
            if (flag == true) {
              cchair[kk]=visual_mining[visual_pairnum[i][0]][j];
              kk+=1;
            } else {
              visual_pairnum[i][1]-=1;
            }
          }
        }
      }
    }
    int mm=0;
    visual_pairsum=0;
    for (int i=0; i<map2.node_num; i++) {
      visual_pairsum +=visual_pairnum[i][1];
      if (visual_pairnum[i][1]==0) {
        vchair[mm]=visual_pairnum[i][0];
        mm+=1;
      }
    }
    vchair_num=mm;
  }

  public int countMatches(String target, String str) {
    int count = 0;
    int pos = 0;
    while ((pos = target.indexOf(str, pos)) > -1) {
      count++;
      pos += str.length();
    }
    return count;
  }

  public boolean getPanelSize() {
    boolean change = false;

    oldSizeX = sizeX;
    oldSizeY = sizeY;

    sizeX = getWidth();
    sizeY = getHeight();

    if (sizeX != oldSizeX || sizeY != oldSizeY) {
      change = true;
    }

    changeX = changeY = 1.0;
    if (oldSizeX != 0) {
      changeX = sizeX / (double)oldSizeX;
    }
    if (oldSizeY != 0) {
      changeY = sizeY / (double)oldSizeY;
    }

    return change;
  }

  public void resizeData() {
    initializePanelXY();

    SetNodePosition();
    SetNode2Position();

    if (changeY > 1) {
      changeY *= 1.2;
    }
    letter_size = (int)((double)letter_size * changeY);
    imageSize = (int)((double)imageSize * changeY);

    if (letter_size <= 10) {
      letter_size = 10;
    }
    if (letter_size > maxLetterSize) {
      letter_size = maxLetterSize;
    }
    if (imageSize <= 10) {
      imageSize = 10;
    }
    if (imageSize > maxImageSize) {
      imageSize = maxImageSize;
    }

    changeY = 1;
  }


  //////////DRAWING
  void draw_background(Graphics2D g2) {
    boolean change = getPanelSize();

    if (change) {
      resizeData();
    }

    g2.setColor(new Color(0xE0,0xFF,0xFF));
//		g2.setColor(new Color(0xEE,0xFF,0xFF));
    g2.fillRect(0,0, sizeX, sizeY);
  }

  public void drawArrow(Graphics2D g2, int x0, int y0, int x1, int y1, int r, Color c, double wing) {
    double t;
    int x, y;
//		double l = 20;	// length of arrow wings, default:30

    //Angle of end of an arrow
    double dt = Math.PI / 6.0;  // pi/6 = 30 degree

    t = Math.atan2((double)(y1 - y0), (double)(x1 - x0));	// arrow vector

    g2.setColor(c);

    //FROM
    x0 = x0 + (int)(r * Math.cos(t));
    y0 = y0 + (int)(r * Math.sin(t));

    //TO
    x1 = x1 - (int)(r * Math.cos(t));
    y1 = y1 - (int)(r * Math.sin(t));

    //LINE
    g2.drawLine(x0, y0, x1, y1);

    //WING
    x = x1 - (int)(wing * Math.cos(t - dt));
    y = y1 - (int)(wing * Math.sin(t - dt));
    g2.drawLine(x1, y1, x, y);

    x = x1 - (int)(wing * Math.cos(t + dt));
    y = y1 - (int)(wing * Math.sin(t + dt));
    g2.drawLine(x1, y1, x, y);
  }





  public void drawSetPanel(Graphics2D g2) {
    int visualizationX, visualizationY = panelY[0] + panelSizeY/3;
    int miningX, miningY = panelY[0] + panelSizeY/3*2;

    for (int i=0; i<panelNumber; i++) {
      //FRAME
      g2.setColor(Color.black);
      g2.fillRect(panelX[i], panelY[0], panelX[i+1]-panelX[i], panelSizeY);
      g2.setColor(Color.white);
      g2.fillRect(panelX[i]+1, panelY[0]+2, panelX[i+1]-panelX[i]-2, panelSizeY-4);
      g2.setColor(Color.black);
      g2.drawRect(panelX[i]+3, panelY[0]+4, panelX[i+1]-panelX[i]-7, panelSizeY-9);

      //ARROW IMAGE//MOVED

      g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));

      //MODULE FRAME
      if (miningIndex[i] == -1) {
        g2.setColor(map.node[0].color);			//draw_toolset_area
        g2.fillRect(panelX[i]+9, miningY-3, panelX[i+1]-panelX[i]-19, panelLetterSize/2*3+3);
        g2.setColor(Color.black);				//draw_shadow
        g2.fillRect(panelX[i]+9, miningY-3, panelX[i+1]-panelX[i]-22, panelLetterSize/2*3);
      } else {
//				g2.setColor(Color.black);				//draw_shadow
        g2.setColor(toolTypeColor[map.node[miningIndex[i]].toolType]);
        g2.fillRect(panelX[i]+9,miningY-3,panelX[i+1]-panelX[i]-19,panelLetterSize/2*3+3);
        g2.setColor(map.node[0].color);			//draw_toolset_area
        g2.fillRect(panelX[i]+11,miningY-1,panelX[i+1]-panelX[i]-22,panelLetterSize/2*3);
      }
      if (visualizationIndex[i] == -1) {
        g2.setColor(map2.node[0].color);		//draw_toolset_area
        g2.fillRect(panelX[i]+9,visualizationY-3,panelX[i+1]-panelX[i]-19,panelLetterSize/2*3+3);
        g2.setColor(Color.black);				//draw_shadow
        g2.fillRect(panelX[i]+9,visualizationY-3,panelX[i+1]-panelX[i]-22,panelLetterSize/2*3);
      } else {
//				g2.setColor(Color.black);				//draw_shadow
        g2.setColor(toolTypeColor[map2.node[visualizationIndex[i]].toolType]);
        g2.fillRect(panelX[i]+9,visualizationY-3,panelX[i+1]-panelX[i]-19,panelLetterSize/2*3+3);
        g2.setColor(map2.node[0].color);		//draw_toolset_area
        g2.fillRect(panelX[i]+11,visualizationY-1,panelX[i+1]-panelX[i]-22,panelLetterSize/2*3);
      }




      //MODULE NAME  //MOVED


      //DATA TYPE
      if (visualizationIndex[i] != -1) {
        int dletter_size = 10;
        int length = 0, verticalLength = 0, count = 0;

        g2.setFont(new Font("Dialog", Font.BOLD, 10));
        g2.setColor(Color.black);

        for (int j=0; j<11; j++)
          if (control.moduleData.visual[visualizationIndex[i]].dataNumbers[j] > 0) {
            if (forUser && control.sp1.inJapanese) {
              g2.drawString(useJapanese[26+j],(panelX[i]+70) + length * (dletter_size+1), panelY[0] + panelSizeY/2 + dletter_size * verticalLength);
              length += useJapanese[26+j].length();
            } else {
              g2.drawString(datatype[j],(panelX[i]+70) + length * (dletter_size+1)/2, panelY[0] + panelSizeY/2 + dletter_size * verticalLength);
              length += datatype[j].length();
            }
            count++;

            if (count == 2) {
              length = 0;
              verticalLength++;
              count = 0;
            }
          }
      }
    }



    //OPTION
    //		int miningY = panelY[0] + panelSizeY/3 * 2;
//		int miningX = (int)(panelX[n] + (map.node[miningIndex[n]].name_length-1)*panelLetterSize/6*7);

//	public void drawArrow(Graphics2D g2, int x0, int y0, int x1, int y1, int r, Color c, double wing)
//		drawArrow(g2, (int)(miningX+imageSize*(1.0+CCC)), (int)clickMiningLineY,
//				  (int)(miningX+imageSize*(1.0+CCC)), (int)(miningY+imageSize), 1, clickColor,12);

    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    Color optionColor = new Color(0x22,0x8b,0x22);
    g2.setColor(optionColor);

    //M->V
    for (int i=0; i<panelNumber; i++)
      if (miningIndex[i] != -1)
        for (int j=0; j<panelNumber; j++)
          if (visualizationIndex[j] != -1)
            if (i != j)
              if (map.relation[miningIndex[i]][visualizationIndex[j]].option == true)
                drawArrow(g2, (int)((panelX[i]+panelX[i+1])/2), panelY[0] + panelSizeY/3 * 2 + panelLetterSize/4*3,
                          (int)((panelX[j]+panelX[j+1])/2), panelY[0] + panelSizeY/3 + panelLetterSize/4*3, 0, optionColor, 12);

    //				  (int)(miningX+imageSize*(1.0+CCC)), (int)(miningY+imageSize), 1, clickColor,12);
//                           g2.drawLine((int)((panelX[i]+panelX[i+1])/2), panelY[0] + panelSizeY/3 * 2 + panelLetterSize/4*3,
//                                       (int)((panelX[j]+panelX[j+1])/2), panelY[0] + panelSizeY/3 + panelLetterSize/4*3);
    //M->M
    for (int i=0; i<panelNumber; i++)
      if (miningIndex[i] != -1)
        for (int j=0; j<panelNumber; j++)
          if (miningIndex[j] != -1)
            if (i != j)
              if (map.optionInMining[miningIndex[i]][miningIndex[j]] == true)
                drawArrow(g2, (int)((panelX[i]+panelX[i+1])/2), panelY[0] + panelSizeY/3 * 2 + panelLetterSize/4*3,
                          (int)((panelX[j]+panelX[j+1])/2), panelY[0] + panelSizeY/3 * 2 + panelLetterSize/4*3, 0, optionColor, 12);

//                            g2.drawLine((int)((panelX[i]+panelX[i+1])/2), panelY[0] + panelSizeY/3 * 2 + panelLetterSize/4*3,
//                                        (int)((panelX[j]+panelX[j+1])/2), panelY[0] + panelSizeY/3 * 2 + panelLetterSize/4*3);

    //V->V
    for (int i=0; i<panelNumber; i++)
      if (visualizationIndex[i] != -1)
        for (int j=0; j<panelNumber; j++)
          if (visualizationIndex[j] != -1)
            if (i != j)
              if (map.optionInVisualization[visualizationIndex[i]][visualizationIndex[j]] == true)
                drawArrow(g2, (int)((panelX[i]+panelX[i+1])/2), panelY[0] + panelSizeY/3 + panelLetterSize/4*3,
                          (int)((panelX[j]+panelX[j+1])/2), panelY[0] + panelSizeY/3 + panelLetterSize/4*3, 0, optionColor, 12);


//                            g2.drawLine((int)((panelX[i]+panelX[i+1])/2), panelY[0] + panelSizeY/3 + panelLetterSize/4*3,
//                                        (int)((panelX[j]+panelX[j+1])/2), panelY[0] + panelSizeY/3 + panelLetterSize/4*3);

    if (displayConnection[3] == true) { //FOCUS
      useFocusCheck();
      drawFocusLine(g2);

      for (int i=0; i<panelNumber; i++) {
        //draw_setImageIcons(g2,i);

        if (miningIndex[i] != -1) {
          drawMiningArrow(g2,i);
        }

        if (visualizationIndex[i] != -1) {
          drawVisualizationArrow(g2,i);
        }
      }
    }

    for (int i=0; i<panelNumber; i++) {
      //ARROW IMAGE
      if (miningIndex[i] != -1 && visualizationIndex[i] != -1)
        if (map.node[miningIndex[i]].toolType != 1 && map2.node[visualizationIndex[i]].toolType != 1) {
          g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
          //                g2.drawImage(arrowImage, panelX[i]+50, visualizationY+22, imageSize, panelSizeY/3-22, this);

          drawArrow(g2, panelX[i]+50, visualizationY + panelSizeY/3, panelX[i]+50, visualizationY+22 , 3, Color.black, 20);
        }

      //MODULE NAME
      g2.setFont(new Font("Dialog", Font.BOLD, panelLetterSize));
      g2.setColor(Color.black);
      if (miningIndex[i] != -1) {
        miningX = (int)(panelX[i]+(map.node[miningIndex[i]].name_length-1)*panelLetterSize/6*7);

        if (control.sp1.inJapanese) {
          g2.drawString(control.moduleData.miningModuleNamesInJapanese[miningIndex[i]], panelX[i]+12+1, miningY+panelLetterSize+1);
        } else {
          g2.drawString(control.moduleData.module_names[miningIndex[i]], panelX[i]+12+1, miningY+panelLetterSize+1);
        }
        //                g2.drawImage(toolTypePicture[map.node[miningIndex[i]].toolType-1], miningX, miningY+1, imageSize, imageSize-1, this);

      }
      if (visualizationIndex[i] != -1) {
        visualizationX = (int)(panelX[i]+(map2.node[visualizationIndex[i]].name_length-1)*panelLetterSize/6*7);

        if (control.sp1.inJapanese) {
          g2.drawString(control.moduleData.visualizationModuleNamesInJapanese[visualizationIndex[i]], panelX[i]+12+1, visualizationY+panelLetterSize+1);
        } else {
          g2.drawString(control.moduleData.visu_module_names[visualizationIndex[i]], panelX[i]+12+1, visualizationY+panelLetterSize+1);
        }
        //                g2.drawImage(toolTypePicture[map2.node[visualizationIndex[i]].toolType-1], visualizationX, visualizationY+1, imageSize, imageSize-1, this);
      }
    }
  }

  Color touchColor = new Color(0xFF,0x45,0x00);//orange
  Color clickColor = new Color(0x08,0x0B,0xFD);//blue;

  boolean miningClickFocus, miningTouchFocus, visualizationClickFocus, visualizationTouchFocus;
  double miningClickFocus_height, miningTouchFocus_height, visualizationClickFocus_height, visualizationTouchFocus_height;


  public void useFocusCheck() {
    miningClickFocus = miningTouchFocus = visualizationClickFocus = visualizationTouchFocus = false;

    for (int i=0; i<maxPanelNumber; i++) {
      if (miningIndex[i] == -1 && visualizationIndex[i] == -1) {
        break;
      }

      if (miningIndex[i] != -1) {
        if (map.node[miningIndex[i]].focusClickExecute == true) {
          miningClickFocus = true;
        }

        if (map.node[miningIndex[i]].focusTouchExecute == true) {
          miningTouchFocus = true;
        }
      }

      if (visualizationIndex[i] != -1) {
        if (map2.node[visualizationIndex[i]].click4502 == true ||
            map2.node[visualizationIndex[i]].executeByClick == true ||
            map2.node[visualizationIndex[i]].repaintOthersByClick == true) {
          visualizationClickFocus = true;
        }

        if (map2.node[visualizationIndex[i]].touch4501 == true ||
            map2.node[visualizationIndex[i]].executeByTouch == true ||
            map2.node[visualizationIndex[i]].repaintOthersByTouch == true) {
          visualizationTouchFocus = true;
        }
      }
    }

    if (miningClickFocus && miningTouchFocus) {
      miningClickFocus_height = 1.0;
      miningTouchFocus_height = 2.0;
    } else if (miningClickFocus || miningTouchFocus) {
      miningClickFocus_height = 1.0;
      miningTouchFocus_height = 1.0;
    }

    if (visualizationClickFocus && visualizationTouchFocus) {
      visualizationClickFocus_height = 1.0;
      visualizationTouchFocus_height = 2.0;
    } else if (visualizationClickFocus || visualizationTouchFocus) {
      visualizationClickFocus_height = 1.0;
      visualizationTouchFocus_height = 1.0;
    }
  }

  public void drawFocusLine(Graphics2D g2) { //draw_focus_line
    int visualizationY = panelY[0] + panelSizeY/3;
    int miningY = panelY[0] + panelSizeY/3*2;

    double clickVisualizationLineY = visualizationY - panelSizeY/8 * visualizationClickFocus_height;
    double touchVisualizationLineY = visualizationY - panelSizeY/8 * visualizationTouchFocus_height;

    double clickMiningLineY = miningY + imageSize+2 + panelSizeY/10 * miningClickFocus_height;
    double touchMiningLineY = miningY + imageSize+2 + panelSizeY/10 * miningTouchFocus_height;

    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));

    if (visualizationClickFocus == true) {
      g2.setColor(clickColor);
      g2.drawLine(6, (int)clickVisualizationLineY, panelX[panelNumber]-6, (int)clickVisualizationLineY);

      g2.setColor(Color.black);
      g2.setFont(new Font("Dialog", Font.BOLD, 10));
      if (control.sp1.inJapanese) {
        g2.drawString(useJapanese[21],5,(int)clickVisualizationLineY);
      } else {
        g2.drawString("Click",5,(int)clickVisualizationLineY);
      }
    }
    if (visualizationTouchFocus == true) {
      g2.setColor(touchColor);
      g2.drawLine(3, (int)touchVisualizationLineY, panelX[panelNumber]-3, (int)touchVisualizationLineY);

      g2.setColor(Color.black);
      g2.setFont(new Font("Dialog", Font.BOLD, 10));
      if (control.sp1.inJapanese) {
        g2.drawString(useJapanese[20],5,(int)touchVisualizationLineY);
      } else {
        g2.drawString("Touch",5,(int)touchVisualizationLineY);
      }
    }

    if (miningClickFocus == true) {
      g2.setColor(clickColor);
      g2.drawLine(6, (int)clickMiningLineY, panelX[panelNumber]-6, (int)clickMiningLineY);
    }
    if (miningTouchFocus == true) {
      g2.setColor(touchColor);
      g2.drawLine(3, (int)touchMiningLineY, panelX[panelNumber]-3, (int)touchMiningLineY);
    }

    if (visualizationClickFocus == true && miningClickFocus == true) {
      g2.setColor(clickColor);

      g2.drawLine(6, (int)clickVisualizationLineY, 6, (int)clickMiningLineY);
      g2.drawLine(panelX[panelNumber]-6, (int)clickVisualizationLineY, panelX[panelNumber]-6, (int)clickMiningLineY);
    }

    if (visualizationTouchFocus == true && miningTouchFocus == true) {
      g2.setColor(touchColor);

      g2.drawLine(3, (int)touchVisualizationLineY, 3, (int)touchMiningLineY);
      g2.drawLine(panelX[panelNumber]-3, (int)touchVisualizationLineY, panelX[panelNumber]-3, (int)touchMiningLineY);
    }
  }



  public void drawMiningArrow(Graphics2D g2, int n) {
    int miningY = panelY[0] + panelSizeY/3 * 2;
    int miningX = (int)(panelX[n] + (map.node[miningIndex[n]].name_length-1)*panelLetterSize/6*7);

    if (miningX > panelX[n+1] - 22 - imageSize*2) {
      miningX = panelX[n+1] - 22 - imageSize*2;
    }

    double clickMiningLineY = miningY + imageSize+2 + panelSizeY/10 * miningClickFocus_height;
    double touchMiningLineY = miningY + imageSize+2 + panelSizeY/10 * miningTouchFocus_height;

    double CCC=0.0,TTT=0.0;

    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));

    if (map.node[miningIndex[n]].focusClickExecute == true || map.node[miningIndex[n]].focusTouchExecute == true) {
      //arrow origin point 1/3,1/2,2/3 at icon
      if (map.node[miningIndex[n]].focusClickExecute == true && map.node[miningIndex[n]].focusTouchExecute == true) {
        CCC=1.0/5.0;
        TTT=4.0/5.0;
      } else {
        CCC=1.0/2.0;
        TTT=1.0/2.0;
      }
      g2.drawImage(connectionPicture[2], miningX +imageSize, miningY,imageSize,imageSize,this);///////

      if (map.node[miningIndex[n]].focusClickExecute == true) // |-->
        drawArrow(g2, (int)(miningX+imageSize*(1.0+CCC)), (int)clickMiningLineY,
                  (int)(miningX+imageSize*(1.0+CCC)), (int)(miningY+imageSize), 1, clickColor,12);

      if (map.node[miningIndex[n]].focusTouchExecute == true) // |-->
        drawArrow(g2, (int)(miningX+imageSize*(1.0+TTT)), (int)touchMiningLineY,
                  (int)(miningX+imageSize*(1.0+TTT)), (int)(miningY+imageSize), 1, touchColor,12);
    }
  }

  public void drawVisualizationArrow(Graphics2D g2, int n) {
    int visualizationY = panelY[0] + panelSizeY/3;
    int visualizationX = (int)(panelX[n] + (map2.node[visualizationIndex[n]].name_length-1)*panelLetterSize/6*7);

    if (visualizationX > panelX[n+1] - 22 - imageSize*2) {
      visualizationX = panelX[n+1] - 22 - imageSize*2;
    }

    double clickVisualizationLineY = visualizationY - panelSizeY/8 * visualizationClickFocus_height;
    double touchVisualizationLineY = visualizationY - panelSizeY/8 * visualizationTouchFocus_height;

    double CCC=0.0,TTT=0.0;

    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));

    if (map2.node[visualizationIndex[n]].touch4501 == true || map2.node[visualizationIndex[n]].click4502 == true
        || map2.node[visualizationIndex[n]].executeByClick == true || map2.node[visualizationIndex[n]].executeByTouch == true
        || map2.node[visualizationIndex[n]].repaintOthersByClick == true || map2.node[visualizationIndex[n]].repaintOthersByTouch == true) {
      //arrow origin point 1/3,1/2,2/3 at icon
      if ((map2.node[visualizationIndex[n]].click4502 == true || map2.node[visualizationIndex[n]].executeByClick == true || map2.node[visualizationIndex[n]].repaintOthersByClick == true)
          &&(map2.node[visualizationIndex[n]].touch4501 == true ||map2.node[visualizationIndex[n]].executeByTouch == true || map2.node[visualizationIndex[n]].repaintOthersByTouch == true)) {
        CCC=1.0/5.0;
        TTT=4.0/5.0;
      } else {
        CCC=1.0/2.0;
        TTT=1.0/2.0;
      }

      g2.drawImage(connectionPicture[2], visualizationX + imageSize, visualizationY, imageSize,imageSize,this);	//ICON

      if (map2.node[visualizationIndex[n]].click4502 == true) // |-->
        drawArrow(g2, (int)(visualizationX + imageSize*(1.0+CCC)), (int)clickVisualizationLineY,
                  (int)(visualizationX + imageSize*(1.0+CCC)), visualizationY, 1, clickColor,12);

      if (map2.node[visualizationIndex[n]].touch4501 == true) // |-->
        drawArrow(g2, (int)(visualizationX + imageSize*(1.0+TTT)), (int)touchVisualizationLineY,
                  (int)(visualizationX + imageSize*(1.0+TTT)), visualizationY, 1, touchColor,12);

      if (map2.node[visualizationIndex[n]].repaintOthersByClick == true || map2.node[visualizationIndex[n]].executeByClick == true) // -->|
        drawArrow(g2, (int)(visualizationX + imageSize*(1.0+CCC)), visualizationY,
                  (int)(visualizationX+imageSize*(1.0+CCC)), (int)clickVisualizationLineY, 1, clickColor,12);

      if (map2.node[visualizationIndex[n]].repaintOthersByTouch == true || map2.node[visualizationIndex[n]].executeByTouch == true) // -->|
        drawArrow(g2, (int)(visualizationX + imageSize*(1.0+TTT)), visualizationY,
                  (int)(visualizationX+imageSize*(1.0+TTT)), (int)touchVisualizationLineY, 1, touchColor,12);
    }
  }






  int judge_m_pair[][]= {{1,1,1,1},{1,1,0,0},{1,1,1,0},{1,1,1,1}};
  int judge_v_pair[][]= {{1,1,1,1},{1,1,1,1},{1,0,1,1},{1,0,0,1}};

  public void draw_setImageIcons(Graphics2D g2,int n) {
    int m_posy = panelY[0]+panelSizeY/3*2;
    int v_posy = panelY[0]+panelSizeY/3;

    if (miningIndex[n]==-1&&visualizationIndex[n]==-1) {
      for (int i=0; i<toolTypePicture.length; i++) {
        g2.drawImage(toolTypePicture[i], (int)(panelX[n]+(panelX[n+1]-panelX[n])/6*(i+1)),m_posy, imageSize,imageSize,this);
        g2.drawImage(toolTypePicture[i], (int)(panelX[n]+(panelX[n+1]-panelX[n])/6*(i+1)),v_posy,imageSize,imageSize,this);
      }
    } else if (visualizationIndex[n]==-1&&miningIndex[n]!=-1) {
      for (int i=0; i<toolTypePicture.length; i++) {
        if (judge_m_pair[map.node[miningIndex[n]].toolType-1][i]==1) {
          g2.drawImage(toolTypePicture[i], (int)(panelX[n]+(panelX[n+1]-panelX[n])/6*(i+1)),v_posy,imageSize,imageSize,this);
        }
      }
    } else if (miningIndex[n]==-1&&visualizationIndex[n]!=-1) {
      for (int i=0; i<toolTypePicture.length; i++) {
        if (judge_v_pair[map2.node[visualizationIndex[n]].toolType-1][i]==1) {
          g2.drawImage(toolTypePicture[i], (int)(panelX[n]+(panelX[n+1]-panelX[n])/6*(i+1)),m_posy,imageSize,imageSize,this);
        }
      }
    }
  }



  //Display links
  public void draw_link(Graphics2D g2,int m,int n) {
    g2.drawLine((int)map.node[m].x + map.node[m].name_length*letter_size/2,(int)map.node[m].y,
                (int)map2.node[n].x + map2.node[n].name_length*letter_size/2,(int)map2.node[n].y);
  }

  //Display links
  public void draw_links(Graphics2D g2) {
//		g2.setColor(Color.darkGray);

    g2.setColor(new Color(0xb0,0xb0,0xb0));
    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));

    if (clickNumber!=-1) {
      for (int i=0; i<map2.node_num; i++)
        if (map.relation[clickNumber][i].suggest==true) {
          draw_link(g2,clickNumber,i);
        }
    } else if (clickNumber2!=-1) {
      for (int i=0; i<map.node_num; i++)
        if (map.relation[i][clickNumber2].suggest==true) {
          draw_link(g2,i,clickNumber2);
        }
    } else {
      for (int i=0; i<map.node_num; i++)
        for (int j=0; j<map2.node_num; j++)
          if (map.relation[i][j].suggest==true) {
            draw_link(g2,i,j);
          }
    }

    //option LINK
    g2.setColor(new Color(0x22,0x8b,0x22));
    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));

    if (clickNumber!=-1) {
      //M->V
      for (int j=0; j<map2.node_num; j++)
        if (map.relation[clickNumber][j].option == true) {
          draw_link(g2,clickNumber,j);
        }

      //M->M
      for (int j=0; j<map.node_num; j++)
        if (map.optionInMining[clickNumber][j] == true)
          g2.drawLine((int)map.node[clickNumber].x + map.node[clickNumber].name_length*letter_size/2,(int)map.node[clickNumber].y,
                      (int)map.node[j].x + map.node[j].name_length*letter_size/2,(int)map.node[j].y);
    } else if (clickNumber2!=-1) {
      //M->V
      for (int i=0; i<map.node_num; i++)
        if (map.relation[i][clickNumber2].option == true) {
          draw_link(g2,i,clickNumber2);
        }

      //V->V
      for (int j=0; j<map2.node_num; j++)
        if (map.optionInVisualization[clickNumber2][j] == true)
          g2.drawLine((int)map2.node[clickNumber2].x + map2.node[clickNumber2].name_length*letter_size/2,(int)map2.node[clickNumber2].y,
                      (int)map2.node[j].x + map2.node[j].name_length*letter_size/2,(int)map2.node[j].y);
    } else {

      //M->V
      for (int i=0; i<map.node_num; i++)
        for (int j=0; j<map2.node_num; j++)
          if (map.relation[i][j].option == true) {
            draw_link(g2,i,j);
          }

      //M->M
      for (int i=0; i<map.node_num; i++)
        for (int j=0; j<map.node_num; j++)
          if (map.optionInMining[i][j] == true)
            g2.drawLine((int)map.node[i].x + map.node[i].name_length*letter_size/2,(int)map.node[i].y,
                        (int)map.node[j].x + map.node[j].name_length*letter_size/2,(int)map.node[j].y);

      //V->V
      for (int i=0; i<map2.node_num; i++)
        for (int j=0; j<map2.node_num; j++)
          if (map.optionInVisualization[i][j] == true)
            g2.drawLine((int)map2.node[i].x + map2.node[i].name_length*letter_size/2,(int)map2.node[i].y,
                        (int)map2.node[j].x + map2.node[j].name_length*letter_size/2,(int)map2.node[j].y);

    }

  }

  ////Dispaly node names
  public void draw_node_name(Graphics2D g2, int n) {
    g2.setColor(map.node[n].frame_color);
    g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.drawRect((int)map.node[n].x, (int)map.node[n].y-letter_size, map.node[n].name_length*letter_size, letter_size/2*3);

//		g2.setColor(map.node[n].color);
    g2.setColor(new Color(255, 175, 175, 208));
    g2.fillRect((int)map.node[n].x, (int)map.node[n].y-letter_size, map.node[n].name_length*letter_size, letter_size/2*3);

    g2.setColor(Color.black);
    g2.setFont(new Font("Dialog", Font.BOLD, letter_size));
    g2.drawString(map.node[n].name, (int)map.node[n].x+2, (int)map.node[n].y+2);

//		g2.drawImage(toolTypePicture[map.node[n].toolType-1],
//        (int)(map.node[n].x+(map.node[n].name_length-1.5)*letter_size),(int)(map.node[n].y-letter_size*6/5)+3, imageSize,imageSize-1,this);

    int d_img_x = (int)(map.node[n].x+(map.node[n].name_length-1.5)*letter_size);
    int d_img_y = (int)(map.node[n].y-letter_size*5/4*1.7);

//		if(map.node[n].focusWords==true&&displayConnection[1]==true)
//			g2.drawImage(connectionPicture[0], (int)(d_img_x-letter_size*3),(int)(d_img_y), imageSize,imageSize,this);

//		if((map.node[n].option_v_r==true || map.node[n].option_m_r==true)&&displayConnection[2]==true)
//			g2.drawImage(connectionPicture[1], (int)(d_img_x-letter_size*2),(int)(d_img_y), imageSize,imageSize,this);

//		if(map.node[n].focus_m_t==true&&displayConnection[3]==true)
//			g2.drawImage(connectionPicture[2], (int)(d_img_x-letter_size),(int)(d_img_y),imageSize,imageSize,this);

    if (map.node[n].focus_m_t==true&&displayConnection[3]==true)
      g2.drawImage(connectionPicture[2], (int)(map.node[n].x+(map.node[n].name_length-1.5)*letter_size),
                   (int)(map.node[n].y-letter_size*6/5)+3, imageSize, imageSize, this);

//		if(map.node[n].dataget==true&&displayConnection[4]==true)
//			g2.drawImage(connectionPicture[3], (int)(d_img_x),(int)(d_img_y), imageSize,imageSize,this);
  }
  public void draw_node_name2(Graphics2D g2, int n) {
    g2.setColor(map2.node[n].frame_color);
    g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.drawRect((int)map2.node[n].x, (int)map2.node[n].y-letter_size, map2.node[n].name_length*letter_size, letter_size/2*3);

//		g2.setColor(map2.node[n].color);
    g2.setColor(new Color(0, 255, 255, 208));
    g2.fillRect((int)map2.node[n].x, (int)map2.node[n].y-letter_size, map2.node[n].name_length*letter_size, letter_size/2*3);

    g2.setColor(Color.black);
    g2.setFont(new Font("Dialog", Font.BOLD, letter_size));
    g2.drawString(map2.node[n].name, (int)map2.node[n].x+2, (int)map2.node[n].y+2);

//		g2.drawImage(toolTypePicture[map2.node[n].toolType-1],
//        (int)(map2.node[n].x+(map2.node[n].name_length-1.5)*letter_size),(int)(map2.node[n].y-letter_size*6/5)+3, imageSize,imageSize-1,this);

    int d_img_x2 = (int)(map2.node[n].x+(map2.node[n].name_length-1.5)*letter_size);
    int d_img_y2 = (int)(map2.node[n].y-letter_size*5/4*1.7);

//		if(map2.node[n].focusWords==true&&displayConnection[1]==true)
//			g2.drawImage(connectionPicture[0], (int)(d_img_x2-letter_size*3),(int)(d_img_y2), imageSize,imageSize,this);

//		if((map2.node[n].option_v_r==true || map2.node[n].option_m_r==true) && displayConnection[2]==true)
//			g2.drawImage(connectionPicture[1], (int)(d_img_x2),(int)(d_img_y2), imageSize,imageSize,this);

//		if((map2.node[n].focus_v_t==true || map2.node[n].focus_v_r==true|| map2.node[n].focus_m_r==true) &&displayConnection[3]==true)
//			g2.drawImage(connectionPicture[2], (int)(d_img_x2-letter_size),(int)(d_img_y2), imageSize,imageSize,this);

    if ((map2.node[n].focus_v_t==true || map2.node[n].focus_v_r==true|| map2.node[n].focus_m_r==true) && displayConnection[3]==true)
      g2.drawImage(connectionPicture[2], (int)(map2.node[n].x+(map2.node[n].name_length-1.5)*letter_size),
                   (int)(map2.node[n].y-letter_size*6/5)+3, imageSize,imageSize,this);
  }

  public void draw_node_names(Graphics2D g2) {
    g2.setColor(Color.black);

    if (clickNumber != -1) {
      draw_node_name(g2,clickNumber);

      for (int i=0; i<map.node_num; i++)
        if (map.optionInMining[clickNumber][i] == true) {
          draw_node_name(g2,i);
        }
    } else {
      if (clickNumber2!=-1) {
        for (int i=0; i<map.node_num; i++)
          if (map.relation[i][clickNumber2].suggest == true || map.relation[i][clickNumber2].option == true) {
            draw_node_name(g2,i);
          }
      } else
        for (int i=0; i<map.node_num; i++) {
          draw_node_name(g2,i);
        }
    }
  }
  public void draw_node_names2(Graphics2D g2) {
    g2.setColor(Color.black);

    if (clickNumber2!=-1) {
      draw_node_name2(g2,clickNumber2);

      for (int i=0; i<map2.node_num; i++)
        if (map.optionInVisualization[clickNumber2][i] == true) {
          draw_node_name(g2,i);
        }
    } else {
      if (clickNumber!=-1) {
        for (int i=0; i<map2.node_num; i++)
          if (map.relation[clickNumber][i].suggest ==true || map.relation[clickNumber][i].option == true) {
            draw_node_name2(g2,i);
          }
      } else
        for (int i=0; i<map2.node_num; i++) {
          draw_node_name2(g2,i);
        }
    }
  }

  //Display node names touched by a mouse
  public void draw_touch_nodename(Graphics2D g2) {
    if (touchNumber >= 0) {
      draw_node_name(g2, touchNumber);
      g2.setColor(Color.green);
      g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
      g2.drawRect((int)map.node[touchNumber].x-2, (int)map.node[touchNumber].y-letter_size-2,
                  map.node[touchNumber].name_length*letter_size+2, letter_size/2*3+2);
    }
  }
  public void draw_touch_nodename2(Graphics2D g2) {
    if (touchNumber2 >= 0) {
      draw_node_name2(g2, touchNumber2);
      g2.setColor(Color.green);
      g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
      g2.drawRect((int)map2.node[touchNumber2].x-2, (int)map2.node[touchNumber2].y-letter_size-2,
                  map2.node[touchNumber2].name_length*letter_size+2, letter_size/2*3+2);
    }
  }

  //Display parameters
  public void draw_parameters(Graphics2D g2) {

    // Number of Modules
    g2.setColor(map.node[0].color);
    g2.fillRect(5,sizeY-50, 11*letter_size,letter_size + 2);

    g2.setColor(new Color(0xA5,0x2A,0x2A));
    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.drawLine(5, sizeY-48 + letter_size, 11*letter_size + 6, sizeY-48 + letter_size);

    g2.setColor(map2.node[0].color);
    g2.fillRect(5,sizeY-25, 11*letter_size,letter_size + 2);

    g2.setColor(new Color(0xA5,0x2A,0x2A));
    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.drawLine(5, sizeY-23 + letter_size, 11*letter_size + 6, sizeY-23 + letter_size);

    g2.setColor(Color.black);
    g2.setFont(new Font("Dialog", Font.BOLD, letter_size));
    if (control.sp1.inJapanese) {
      g2.drawString(useJapanese[6]+map.node_num+useJapanese[8],5,sizeY-50+letter_size);
      g2.drawString(useJapanese[7]+map2.node_num+useJapanese[8],5,sizeY-25+letter_size);
    } else {
      g2.drawString("Execution All: "+map.node_num+" tools",5,sizeY-50+letter_size);
      g2.drawString("Visualization All: "+map2.node_num+" tools",5,sizeY-25+letter_size);
    }

    //OptionLine
    int rightSide = panelX[panelNumber] - useJapanese[13].length()*11;
    g2.setColor(new Color(0x22,0x8b,0x22));
    g2.drawLine(rightSide-10, panelY[0]+panelSizeY+6, rightSide - 50, panelY[0]+panelSizeY+6);
    g2.setFont(new Font("Dialog", Font.BOLD, 10));
    if (control.sp1.inJapanese) {
      g2.drawString(useJapanese[13], rightSide, panelY[0]+panelSizeY+11);
    } else {
      g2.drawString("Option", rightSide, panelY[0]+panelSizeY+11);
    }


    // Color of ToolType
    g2.setFont(new Font("Dialog", Font.BOLD, 10));
    g2.setColor(toolTypeColor[1]);
    if (control.sp1.inJapanese) {
      g2.drawString(useJapanese[22], 5, panelY[0]+panelSizeY+11);
    } else {
      g2.drawString("Simple", 5, panelY[0]+panelSizeY+11);
    }

    g2.setColor(toolTypeColor[2]);
    if (control.sp1.inJapanese) {
      g2.drawString(useJapanese[23], 5, panelY[0]+panelSizeY+22);
    } else {
      g2.drawString("Primitive", 5, panelY[0]+panelSizeY+22);
    }

    g2.setColor(toolTypeColor[3]);
    if (control.sp1.inJapanese) {
      g2.drawString(useJapanese[24], 5, panelY[0]+panelSizeY+33);
    } else {
      g2.drawString("semiPrimitive", 5, panelY[0]+panelSizeY+33);
    }

//		g2.setColor(toolTypeColor[4]);
//		g2.drawString(useJapanese[25], 5, panelY[0]+panelSizeY+44);

    int length = (5+1)*BLSize+2;
    if (control.sp1.inJapanese) {
      length = (useJapanese[1].length()+1)*BLSize+2;
    }

    //PANEL NUMBER
    for (int i=0; i<panelNumber; i++) {
      g2.setColor(Color.white);
      g2.fillRect(panelX[i+1]-length-5, panelY[0], length, BLSize+2);

      g2.setColor(Color.black);
      g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
      g2.drawRect(panelX[i+1]-length-5, panelY[0], length, BLSize+2);

      //draw_panelNumber
      g2.setFont(new Font("Dialog", Font.BOLD, BLSize));
      if (control.sp1.inJapanese) {
        g2.drawString(useJapanese[1]+(i+1),panelX[i+1]-length-4,panelY[0]+BLSize);
      } else {
        g2.drawString("Panel"+(i+1),panelX[i+1]-length-4,panelY[0]+BLSize);
      }
    }
  }

  String datatype[] = {"boolean ","int ","double ","String ",
                       "boolean[] ","int[] ","double[] ","String[] ",
                       "boolean[][] ","int[][] ","double[][] "
                      };

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

//		System.out.println("Call repaint");

    draw_background(g2);	//Set background

    drawSetPanel(g2);



    draw_links(g2);			//Display links
    draw_node_names2(g2);	//Display node names
    draw_node_names(g2);	//Display node names
    draw_parameters(g2);	//Display parameters
    draw_touch_nodename2(g2);	//Display node names touched by a mouse
    draw_touch_nodename(g2);	//Display node names touched by a mouse
  }

  public void update(Graphics g) {	//Avoid from blinking
    paintComponent(g);
  }

  //Buttons ActionListener
  class MyBtn implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == add) {
        if (panelNumber == maxPanelNumber) {
          return;
        }

        panelNumber++;

        SetNodePosition();
        SetNode2Position();

        initializePanelXY();
      }

      if (e.getSource() == reduce) {
        if (panelNumber == 1) {
          return;
        }

        panelNumber--;

        SetNodePosition();
        SetNode2Position();

        initializePanelXY();

        miningIndex[panelNumber] = -1;
        visualizationIndex[panelNumber] = -1;

        control.delete(panelNumber);
      }

      if (e.getSource() == reset) {
        for (int i=panelNumber-1; i>0 ; i--) {
          control.delete(i);
        }

        panelNumber = 1;

        SetNodePosition();
        SetNode2Position();

        initializePanelXY();

        for (int i=1; i<10; i++) {
          miningIndex[i] = -1;
          visualizationIndex[i] = -1;
        }
      }

      if (e.getSource() == all_check) {
        if (all_check.isSelected()) {
          displayConnection[0]=true;
          for (int i=1; i<ckbox.length; i++) {
            ckbox[i].setSelected(true);
            displayConnection[i]=true;
          }
        } else {
          displayConnection[0]=false;
          for (int i=1; i<ckbox.length; i++) {
            ckbox[i].setSelected(false);
            displayConnection[i]=false;
          }
        }
      }
      for (int i = 1 ; i < ckbox.length; i++) {
        if (ckbox[i].isSelected()) {
          displayConnection[i]=true;
          repaint();
        } else {
          displayConnection[i]=false;
          repaint();
        }
      }
      repaint();
    }
  }

  public void Checkmap2() {	//Investigate number of selected node
    if (touchNumber != -1) {
      for (int j=0; j<10; j++) {
        if (panelX[j]==-1) {
          break;
        }
        if ((panelX[j+1] >= mousex && panelX[j] <= mousex ) && (sizeY/3 > mousey && 0 <= mousey )) {
          miningIndex[j]=touchNumber;
          SetNodePosition();
          SetNode2Position();
          if (visualizationIndex[j]==-1) {
            for (int k=0; k<map2.node_num; k++) {
              if (control.moduleData.mining[touchNumber].pairingVisualizationID[0]==map2.node[k].ID) {
                visualizationIndex[j] = k;
                break;
              }
            }
          }
        }
      }
      repaint();
    }
    if (touchNumber2 != -1) {
      for (int j=0; j<10; j++) {
        if (panelX[j]==-1) {
          break;
        }
        if ((panelX[j+1] >= mousex && panelX[j] <= mousex ) && (sizeY/3 > mousey && 0 <= mousey )) {
          visualizationIndex[j]=touchNumber2;
          SetNodePosition();
          SetNode2Position();
        }
      }
      repaint();
    }
  }


  double difx=0,dify=0,difx2=0,dify2=0;

  //MouseListener
  class MyMouse implements MouseListener,MouseMotionListener {
    public void mousePressed(MouseEvent me) {	//Press
      mousex = me.getX();
      mousey = me.getY();

      clickNumber = -1;
      clickNumber2 = -1;

      if (touchNumber == -1 && touchNumber2 == -1) {
        old_mousex = mousex;
        old_mousey = mousey;
        return;
      }

      moving = true;
      if (touchNumber!=-1) {
        difx = mousex-map.node[touchNumber].x;
        dify = mousey-map.node[touchNumber].y;
        clickNumber = touchNumber;
        if (lastClickNumber == clickNumber) {
          clickNumber =- 1;
        }
      }
      if (touchNumber2!=-1) {
        difx2 = mousex-map2.node[touchNumber2].x;
        dify2 = mousey-map2.node[touchNumber2].y;
        clickNumber2 = touchNumber2;
        if (lastClickNumber2 == clickNumber2) {
          clickNumber2 =- 1;
        }
      }

      lastClickNumber = clickNumber;
      lastClickNumber2 = clickNumber2;
      repaint();
    }

    public void mouseReleased(MouseEvent me) {	//Release
      if (moving == true) {
        moving = false;
      }

      Checkmap2();

      if (miningIndex[0] != -1 && (touchNumber != -1 || touchNumber2 != -1)) {
        panelset();
      }

      repaint();

    }

    public void mouseEntered(MouseEvent me) {}	//Enter an area
    public void mouseExited(MouseEvent me) {}	//Exit an area

    public void mouseClicked(MouseEvent me) {	//Click
      /**/
      mousex = me.getX();
      mousey = me.getY();

      clickNumber = -1;
      clickNumber2 = -1;

      clickNumber = catchNode(mousex,mousey);
      clickNumber2 = catchNode2(mousex,mousey);

      if (lastClickNumber == clickNumber) {
        clickNumber = -1;
      }
      if (lastClickNumber2 == clickNumber2) {
        clickNumber2 = -1;
      }

      lastClickNumber = clickNumber;
      lastClickNumber2 = clickNumber2;
      repaint();/**/
    }

    //MouseMotionListener
    public void mouseDragged(MouseEvent me) {	//Dragging
      if (moving == true) {	//Move a node
        try {
          mousex = me.getX();
          mousey = me.getY();

          if (mousex<0) {
            mousex = 0;
          }
          if (mousex>sizeX-60) {
            mousex = sizeX-60;
          }
          if (mousey<upperlayer.getSize().height+5) {
            mousey = upperlayer.getSize().height+5;
          }
          if (mousey>sizeY) {
            mousey = sizeY-5;
          }
          if (touchNumber != -1) {
            map.node[touchNumber].x = mousex-difx;
            map.node[touchNumber].y = mousey-dify;
          }
          if (touchNumber2 != -1) {
            map2.node[touchNumber2].x = mousex-difx2;
            map2.node[touchNumber2].y = mousey-dify2;
          }
          repaint();
        } catch (StackOverflowError e) {
          System.out.println("Stack OVERFLOW");
        }
      }
    }

    public void mouseMoved(MouseEvent me) {
      mousex = me.getX();
      mousey = me.getY();

      touchNumber = catchNode(mousex,mousey);
      touchNumber2 = catchNode2(mousex,mousey);

      if (touchNumber != lastTouchNumber || touchNumber2 != lastTouchNumber2) {
        repaint();
        displayModuleName();
        displayModuleInformation();
        lastTouchNumber = touchNumber;
        lastTouchNumber2 = touchNumber2;
      }
    }
  }

  public void displayModuleName() {
    StringBuilder text = new StringBuilder();
    StringBuilder text2 = new StringBuilder();

    if (touchNumber != -1) {
      text.append(map.node[touchNumber].name+" "+tl_type[map.node[touchNumber].toolType]);
      text2.append(map.node[touchNumber].info);
      toolnamelabel.setText(text.toString());
      toolnamelabel.setIcon(toolTypePicture2[map.node[touchNumber].toolType-1]);
    }

    if (touchNumber2 != -1) {
      text.append(map2.node[touchNumber2].name+" "+tl_type[map2.node[touchNumber2].toolType]);
      text2.append(map2.node[touchNumber2].info);
      toolnamelabel.setText(text.toString());
      toolnamelabel.setIcon(toolTypePicture2[map2.node[touchNumber2].toolType-1]);
    }

    /*
    StringBuilder text = new StringBuilder();
    StringBuilder text2 = new StringBuilder();

    if(touchNumber!=-1){
    text.append(map.node[touchNumber].name+" "+tl_type[map.node[touchNumber].toolType]);
    text2.append(map.node[touchNumber].info);
    toolnamelabel.setText(text.toString());
    toolnamelabel.setIcon(toolTypePicture2[map.node[touchNumber].toolType-1]);
    toolinfolabel.setText(text2.toString());

    for(int i=0;i<combine_label.length;i++)combine_label[i].setVisible(false);	///////////////////////
    if(map.node[touchNumber].focusWords==true)combine_label[0].setVisible(true);
    if(map.node[touchNumber].option_v_r==true || map.node[touchNumber].option_m_r==true)combine_label[1].setVisible(true);
    if(map.node[touchNumber].focus_m_t==true)combine_label[2].setVisible(true);
    if(map.node[touchNumber].dataget==true)combine_label[3].setVisible(true);

    }
    else if(touchNumber2!=-1){
    text.append(map2.node[touchNumber2].name+" "+tl_type[map2.node[touchNumber2].toolType]);
    text2.append(map2.node[touchNumber2].info);
    toolnamelabel.setText(text.toString());
    toolnamelabel.setIcon(toolTypePicture2[map2.node[touchNumber2].toolType-1]);
    toolinfolabel.setText(text2.toString());

    for(int i=0;i<combine_label.length;i++)combine_label[i].setVisible(false);	///////////////////////
    if(map2.node[touchNumber2].focusWords==true)combine_label[0].setVisible(true);
    if(map2.node[touchNumber2].option_v_r==true || map2.node[touchNumber2].option_m_r==true)combine_label[1].setVisible(true);
    if(map2.node[touchNumber2].focus_v_t==true || map2.node[touchNumber2].focus_v_r==true || map2.node[touchNumber2].focus_m_r==true)
    combine_label[2].setVisible(true);
    }
    */
  }

  public void displayModuleInformation() {
    int lastPosition;

    if (touchNumber != -1) {
      lastPosition = readmeFile[touchNumber].indexOf("[");
      if (lastPosition == -1) {
        lastPosition = readmeFile[touchNumber].length();
      }

      area.setText(readmeFile[touchNumber].substring(0, lastPosition));
      area.append(newLine);
      area.setCaretPosition(0);

      d_area.setText(readmeFile[touchNumber].substring(0, readmeFile[touchNumber].length()));
      d_area.append(newLine);
      d_area.setCaretPosition(0);
    }

    if (touchNumber2 != -1) {
      lastPosition = readmeFile2[touchNumber2].indexOf("[");
      if (lastPosition == -1) {
        lastPosition = readmeFile2[touchNumber2].length();
      }

      area.setText(readmeFile2[touchNumber2].substring(0, lastPosition));
      area.append(newLine);
      area.setCaretPosition(0);

      d_area.setText(readmeFile2[touchNumber2].substring(0, readmeFile2[touchNumber2].length()));
      d_area.append(newLine);
      d_area.setCaretPosition(0);
    }
  }

  public int checkNames(int k,int num) {
    int count=0;
    String test="";
    for (int i=97; i<123; i++) {
      test=String.valueOf((char)i);
      if (k==0) {
        count += countMatches(map.node[num].name,test);
      }
      if (k==1) {
        count += countMatches(map2.node[num].name,test);
      }
    }
    for (int i=40; i<42; i++) {
      test=String.valueOf((char)i);
      if (k==0) {
        count += countMatches(map.node[num].name,test);
      }
      if (k==1) {
        count += countMatches(map2.node[num].name,test);
      }
    }
    return count;
  }

  public int checkInformation(int k,int num) {
    int count=0;
    String test="";
    for (int i=97; i<123; i++) {
      test=String.valueOf((char)i);
      if (k==0) {
        count += countMatches(control.moduleData.miningInformationText[num],test);
      }
      if (k==1) {
        count += countMatches(control.moduleData.visualizationInformationText[num],test);
      }
    }
    for (int i=40; i<42; i++) {
      test=String.valueOf((char)i);
      if (k==0) {
        count += countMatches(control.moduleData.miningInformationText[num],test);
      }
      if (k==1) {
        count += countMatches(control.moduleData.visualizationInformationText[num],test);
      }
    }
    return count;
  }

  public void createSortedIndex() {
    //VISUAL
    int linkNumbers[] = new int[map2.node_num];

    for (int i=0; i<map2.node_num; i++) {
      linkNumbers[i] = map2.node[i].existpair;
    }

    Qsort.initializeIndex(sortedVIndex,map2.node_num);
    Qsort.quicksort(linkNumbers,sortedVIndex,map2.node_num);

//		for(int i=0;i<map2.node_num;i++)
//			System.out.print(sortedVIndex[i]+"("+linkNumbers[i]+") ");

    //MINING
    boolean check[] = new boolean[map.node_num];
    for (int i=0; i<check.length; i++) {
      check[i] = false;
    }

    int sort=0;

    for (int i=0; i<map2.node_num; i++) {
      for (int j=0; j<map.node_num; j++) {
        if (map.relation[j][sortedVIndex[i]].suggest == true && check[j] == false) {
          sortedMIndex[sort] = j;
          check[j] = true;
          sort++;
        }
        if (sort == map.node_num) {
          break;
        }
      }
    }

    if (sort < map.node_num)
      for (int i=0; i<map.node_num; i++)
        if (check[i] == false) {
          sortedMIndex[sort++] = i;
        }

//		for(int i=0;i<map.node_num;i++)
//			System.out.print(sortedMIndex[i]+" ");

  }


  public void SetNodePosition() {



    int marginY = (sizeY/3 * 2 - 100)/(map.node_num/2);
    double shiftA = 0;
    double shiftB = 0;
    boolean flagA = false;
    boolean flagB = false;


    int diff = marginY-(letter_size+4);
    if (diff < 0 && marginY > 0) {
      letter_size += diff;
    }

    for (int i=0; i<map.node_num; i++) {

      double round = 2 * Math.PI/map.node_num * i - Math.PI/2;

      map.node[sortedMIndex[i]].x = sizeX/15*7 + sizeX/6 * Math.cos(round);
      map.node[sortedMIndex[i]].y = sizeY/3*2 + sizeY/4 * Math.sin(round);

      if (i != 0) {
        if (round < Math.PI/2) {
          map.node[sortedMIndex[i]].y = map.node[sortedMIndex[i-1]].y + marginY;
        }

        if (round >= Math.PI/2 && round < Math.PI/2 * 3 && i>1) {
          if (flagA == false) {
            if (map.node[sortedMIndex[i]].x + map.node[sortedMIndex[i]].name_length*letter_size+4 > map.node[sortedMIndex[i-2]].x) {
              shiftA = map.node[sortedMIndex[i]].x + map.node[sortedMIndex[i]].name_length*letter_size+4 - map.node[sortedMIndex[i-2]].x;
            }

            flagA = true;
          }
          map.node[sortedMIndex[i]].y = map.node[sortedMIndex[i-1]].y - marginY;
          map.node[sortedMIndex[i]].x -= shiftA;
        }

        if (round >= Math.PI/2 * 3 && round < Math.PI * 2 && i>1) {
          if (flagB == false) {
            if (map.node[sortedMIndex[i-2]].x + map.node[sortedMIndex[i-2]].name_length*letter_size+4 > map.node[sortedMIndex[i]].x) {
              shiftB = map.node[sortedMIndex[i-2]].x + map.node[sortedMIndex[i-2]].name_length*letter_size+4 - map.node[sortedMIndex[i]].x;
            }

            flagB = true;
          }
          map.node[sortedMIndex[i]].y = map.node[sortedMIndex[i-1]].y + marginY;
          map.node[sortedMIndex[i]].x += shiftB;
        }
      }
//            System.out.println(i+"/"+map.node_num);
    }
  }

  public void SetNode2Position() {


    int marginY = (sizeY/3 * 2 - 100)/(map2.node_num/2);
    double shiftA = 100;
    double shiftB = 100;

    int diff = marginY-(letter_size+4);
    if (diff < 0 && marginY > 0) {
      letter_size += diff;
    }



    for (int i=0; i<map2.node_num; i++) {

      double round = 2 * Math.PI/map2.node_num * i - Math.PI/2;

      map2.node[sortedVIndex[i]].x = sizeX/15*7 + sizeX/8 * 3 * Math.cos(round);
      map2.node[sortedVIndex[i]].y = sizeY/3*2 + sizeY/4 * Math.sin(round);

      if (i == 0) {
        map2.node[sortedVIndex[i]].x += shiftB;
        if (map2.node[sortedVIndex[i]].x + map2.node[sortedVIndex[i]].name_length*letter_size+4 > sizeX) {
          map2.node[sortedVIndex[i]].x = sizeX - (map2.node[sortedVIndex[i]].name_length*letter_size+4) - 2;
        }
      }

      if (i != 0) {
        if (round < Math.PI/2) {
          map2.node[sortedVIndex[i]].y = map2.node[sortedVIndex[i-1]].y + marginY;
          map2.node[sortedVIndex[i]].x += shiftB;
          if (map2.node[sortedVIndex[i]].x + map2.node[sortedVIndex[i]].name_length*letter_size+4 > sizeX) {
            map2.node[sortedVIndex[i]].x = sizeX - (map2.node[sortedVIndex[i]].name_length*letter_size+4) - 2;
          }

          if (map2.node[sortedVIndex[i]].x < sizeX/3 * 2) {
            map2.node[sortedVIndex[i]].x = sizeX/3 * 2;
          }
        }

        if (round >= Math.PI/2 && round < Math.PI/2 * 3) {
          map2.node[sortedVIndex[i]].y = map2.node[sortedVIndex[i-1]].y - marginY;
          map2.node[sortedVIndex[i]].x -= shiftA;
          if (map2.node[sortedVIndex[i]].x < 0) {
            map2.node[sortedVIndex[i]].x = 2;
          }

          if (map2.node[sortedVIndex[i]].x + map2.node[sortedVIndex[i]].name_length*letter_size+4 > sizeX/3) {
            map2.node[sortedVIndex[i]].x = sizeX/3 - (map2.node[sortedVIndex[i]].name_length*letter_size+4);
          }
        }

        if (round >= Math.PI/2 * 3 && round < Math.PI * 2) {
          map2.node[sortedVIndex[i]].y = map2.node[sortedVIndex[i-1]].y + marginY;
          map2.node[sortedVIndex[i]].x += shiftB;
          if (map2.node[sortedVIndex[i]].x + map2.node[sortedVIndex[i]].name_length*letter_size+4 > sizeX) {
            map2.node[sortedVIndex[i]].x = sizeX - (map2.node[sortedVIndex[i]].name_length*letter_size+4) - 2;
          }

          if (map2.node[sortedVIndex[i]].x < sizeX/3 * 2) {
            map2.node[sortedVIndex[i]].x = sizeX/3 * 2;
          }
        }
      }
//            System.out.println(i+"/"+map2.node_num);
    }
  }

  /*


   for(int i=0;i<map.node_num;i++)
   {

   double round = 2 * Math.PI/map.node_num * i - Math.PI/2;

   map.node[i].x = sizeX/15*7 + sizeX/6 * Math.cos(round);
   map.node[i].y = sizeY/3*2 + sizeY/4 * Math.sin(round);

   if(i != 0)
   {
   if(round < Math.PI/2)
   map.node[i].y = map.node[i-1].y + marginY;

   if(round >= Math.PI/2 && round < Math.PI/2 * 3 && i>1)
   {
   if(flagA == false)
   {
   if(map.node[i].x + map.node[i].name_length*letter_size+4 > map.node[i-2].x)
   shiftA = map.node[i].x + map.node[i].name_length*letter_size+4 - map.node[i-2].x;

   flagA = true;
   }
   map.node[i].y = map.node[i-1].y - marginY;
   map.node[i].x -= shiftA;
   }

   if(round >= Math.PI/2 * 3 && round < Math.PI * 2 && i>1)
   {
   if(flagB == false)
   {
   if(map.node[i-2].x + map.node[i-2].name_length*letter_size+4 > map.node[i].x)
   shiftB = map.node[i-2].x + map.node[i-2].name_length*letter_size+4 - map.node[i].x;

   flagB = true;
   }
   map.node[i].y = map.node[i-1].y + marginY;
   map.node[i].x += shiftB;
   }
   }
   //            System.out.println(i+"/"+map.node_num);
   }

   for(int i=0;i<map2.node_num;i++)
   {

   double round = 2 * Math.PI/map2.node_num * i - Math.PI/2;

   map2.node[i].x = sizeX/15*7 + sizeX/8 * 3 * Math.cos(round);
   map2.node[i].y = sizeY/3*2 + sizeY/4 * Math.sin(round);

   if(i == 0)
   {
   map2.node[i].x += shiftB;
   if(map2.node[i].x + map2.node[i].name_length*letter_size+4 > sizeX)
   map2.node[i].x = sizeX - (map2.node[i].name_length*letter_size+4) - 2;
   }

   if(i != 0)
   {
   if(round < Math.PI/2)
   {
   map2.node[i].y = map2.node[i-1].y + marginY;
   map2.node[i].x += shiftB;
   if(map2.node[i].x + map2.node[i].name_length*letter_size+4 > sizeX)
   map2.node[i].x = sizeX - (map2.node[i].name_length*letter_size+4) - 2;

   if(map2.node[i].x < sizeX/3 * 2)
   map2.node[i].x = sizeX/3 * 2;
   }

   if(round >= Math.PI/2 && round < Math.PI/2 * 3)
   {
   map2.node[i].y = map2.node[i-1].y - marginY;
   map2.node[i].x -= shiftA;
   if(map2.node[i].x < 0)
   map2.node[i].x = 2;

   if(map2.node[i].x + map2.node[i].name_length*letter_size+4 > sizeX/3)
   map2.node[i].x = sizeX/3 - (map2.node[i].name_length*letter_size+4);
   }

   if(round >= Math.PI/2 * 3 && round < Math.PI * 2)
   {
   map2.node[i].y = map2.node[i-1].y + marginY;
   map2.node[i].x += shiftB;
   if(map2.node[i].x + map2.node[i].name_length*letter_size+4 > sizeX)
   map2.node[i].x = sizeX - (map2.node[i].name_length*letter_size+4) - 2;

   if(map2.node[i].x < sizeX/3 * 2)
   map2.node[i].x = sizeX/3 * 2;
   }
   }
   //            System.out.println(i+"/"+map2.node_num);
   }
   */


  /*
  	public void SetNode2Position(int nodenum)
  	{
  		if(visual_pairsum==0)
  			return;

  		double round=360/visual_pairsum;

  		int ccc,bbb=0,ddd=0;

  		for(ccc=0;ccc<map2.node_num;ccc++)
  		{
  			if(nodenum==visual_pairnum[ccc][0])
  				break;

  			bbb += visual_pairnum[ccc][1];
  		}

  		bbb += visual_pairnum[ccc][1]/2;
  		round *= bbb;
  		round += 100;
  		round = Math.toRadians(round);


  		map2.node[nodenum].x = sizeX/15*7  +  Math.cos(round)*sizeX/2*0.8;
  		map2.node[nodenum].y = sizeY/3*2 + sizeY/4 * Math.sin(round)*1.0;

  		if(visual_pairnum[ccc][1]==0)
  		{
  			round = 360/vchair_num;

  			for(ddd=0;ddd<map2.node_num;ddd++)
  				if(nodenum==vchair[ddd])
  					break;

  			round*=ddd;
  			round = Math.toRadians(round);

  			map2.node[nodenum].x = sizeX/15*7  +  Math.cos(round)*sizeX/2*0.9;
  			map2.node[nodenum].y = sizeY/3*2 + sizeY/4 * Math.sin(round)*1.2;
  		}
  	}
  	*/

  public int catchNode(int mousex, int mousey) {	//Investigate number of selected node
    for (int i=0; i<map.node_num; i++) {
      if ((mousex > (int)map.node[i].x && mousex < (int)map.node[i].x+map.node[i].name_length*letter_size) &&
          (mousey > (int)map.node[i].y-letter_size && mousey < (int)map.node[i].y-letter_size+letter_size/2*3)) {
        if (clickNumber!=-1) {
          if (i==clickNumber) {
            return i;
          }
        } else if (clickNumber2!=-1) {
          if (map.relation[i][clickNumber2].suggest ==true || map.relation[i][clickNumber2].option == true) {
            return i;
          }
        } else {
          return i;
        }
      }
    }
    return -1;
  }

  public int catchNode2(int mousex, int mousey) {	//Investigate number of selected node
    if (touchNumber!=-1) {
      return -1;
    }
    for (int i=0; i<map2.node_num; i++)
      if ((mousex > (int)map2.node[i].x && mousex < (int)map2.node[i].x+map2.node[i].name_length*letter_size) &&
          (mousey > (int)map2.node[i].y-letter_size && mousey < (int)map2.node[i].y-letter_size+letter_size/2*3)) {
        if (clickNumber2!=-1) {
          if (i==clickNumber2) {
            return i;
          }
        } else if (clickNumber!=-1) {
          if (map.relation[clickNumber][i].suggest ==true || map.relation[clickNumber][i].option == true) {
            return i;
          }
        } else {
          return i;
        }
      }
    return -1;
  }

  public void panelset() {
    int newPanelNumber = 0;
    boolean flag = false;

    for (int i=0; i<maxPanelNumber; i++) {
      if (miningIndex[i] == -1) {
        break;
      }
      if (miningIndex[i]!= -1) {
        newPanelNumber++;
      }
    }

    for (int i=0; i<newPanelNumber; i++) {
      if (lastMiningIndex[i] != miningIndex[i]) {
        flag = true;
        break;
      }
      if (lastVisualizationIndex[i] != visualizationIndex[i]) {
        flag = true;
        break;
      }
    }

    if (flag == false) {
      return;
    }

    //create backup
    control.createBackupIDs();

    control.miningIDs = new int[newPanelNumber];
    control.visualizationIDs = new int[newPanelNumber];

    for (int j=0; j<newPanelNumber; j++) {
      control.miningIDs[j] = map.node[miningIndex[j]].ID;
      control.visualizationIDs[j] = map2.node[visualizationIndex[j]].ID;
    }

    try {
      System.out.println("-------------------BEGIN TRY A-------------------");
      control.panelSet();
      System.out.println("-------------------END TRY A-------------------");
    } catch (Exception e) {
      System.out.println("-------------------BEGIN ERROR-------------------");
//				System.out.println(j+":	"+map.node[miningIndex[j]].name+"	+	"+map2.node[visualizationIndex[j]].name);
      control.traceBackupIDs();

      for (int j=0; j<newPanelNumber; j++) {
        for (int k=0; k<map.node_num; k++) {
          if (control.miningIDs[j] == map.node[k].ID) {
            miningIndex[j]=k;
          }
        }
        for (int k=0; k<map2.node_num; k++) {
          if (control.visualizationIDs[j] == map2.node[k].ID) {
            visualizationIndex[j]=k;
          }
        }
      }

      control.panelSet();
      System.out.println("-------------------END ERROR-------------------");
    }


    for (int i=0; i<newPanelNumber; i++) {
      lastMiningIndex[i] = miningIndex[i];
      lastVisualizationIndex[i] = visualizationIndex[i];
      System.out.println((i+1)+":	"+map.node[miningIndex[i]].name+"	+	"+map2.node[visualizationIndex[i]].name);
    }

    if (newPanelNumber == 1) {
      control.balancedPanelSizeX[0] = control.buttonPanel.getWidth();
    } else
      for (int i=0; i<newPanelNumber; i++) {
        control.balancedPanelSizeX[i] = (control.buttonPanel.getWidth() - (11*(newPanelNumber-1)))/newPanelNumber;
      }
    for (int i=0; i<newPanelNumber-1; i++) {
      control.setBalancedSplitDivider(i);
    }

    repaint();
  }

  /*
  	public void panelset()
  	{
  		int newPanelNumber = 0;
  		boolean flag = false;

  		for(int i=0;i<maxPanelNumber;i++)
          {
  			if(miningIndex[i] == -1)
                  break;
  			if(miningIndex[i]!= -1)
                  newPanelNumber++;
  		}

  		for(int i=0;i<newPanelNumber;i++)
          {
  			if(lastMiningIndex[i] != miningIndex[i])
              {
  				flag = true;
  				break;
  			}
  			if(lastVisualizationIndex[i] != visualizationIndex[i])
              {
  				flag = true;
  				break;
  			}
  		}

  		if(flag == false)
              return;

          //create backup
          control.createBackupIDs();

  		control.miningIDs = new int[newPanelNumber];
  		control.visualizationIDs = new int[newPanelNumber];

  		for(int j=0;j<newPanelNumber;j++){
  			control.miningIDs[j] = map.node[miningIndex[j]].ID;
  			control.visualizationIDs[j] = map2.node[visualizationIndex[j]].ID;
  			try{
  				control.panelSet();
  			}catch(Exception e){
  				System.out.print("ERROR ");
  				System.out.println(j+":	"+map.node[miningIndex[j]].name+"	+	"+map2.node[visualizationIndex[j]].name);
  				control.traceBackupIDs();
  				for(int k=0;k<map.node_num;k++){
  					if(control.miningIDs[j] == map.node[k].ID)miningIndex[j]=k;
  				}
  				for(int k=0;k<map2.node_num;k++){
  					if(control.visualizationIDs[j] == map2.node[k].ID)visualizationIndex[j]=k;
  				}
  				control.panelSet();
  			}
  		}

  		for(int i=0;i<newPanelNumber;i++)
          {
  			lastMiningIndex[i] = miningIndex[i];
  			lastVisualizationIndex[i] = visualizationIndex[i];
  			System.out.println((i+1)+":	"+map.node[miningIndex[i]].name+"	+	"+map2.node[visualizationIndex[i]].name);
  		}

  		if(newPanelNumber == 1)
  			control.balancedPanelSizeX[0] = control.buttonPanel.getWidth();
  		else
  			for(int i=0;i<newPanelNumber;i++)
  				control.balancedPanelSizeX[i] = (control.buttonPanel.getWidth() - (11*(newPanelNumber-1)))/newPanelNumber;
  		for(int i=0;i<newPanelNumber-1;i++)control.setBalancedSplitDivider(i);

  		repaint();
  	}
  */
  class NewMapData2 {
    class Node {
      String name,eng_name;
      String info;
      int name_length;
      int id;
      int ID;
      double x;
      double y;
      Color color,frame_color;
      int pair_num;
      int existpair;
      int toolType;

      boolean focusWords;
      boolean option_m_r;
      boolean option_m_t;
      boolean option_v_r;
      boolean option_v_t;
      boolean focus_m_r;
      boolean focus_m_t;
      boolean focus_v_r;
      boolean focus_v_t;
      boolean dataget;

      boolean focus_m_r_T;
      boolean focus_m_r_C;
      boolean executeByTouch;
      boolean executeByClick;
      boolean repaintOthersByTouch;
      boolean repaintOthersByClick;
      boolean focusTouchExecute;
      boolean focusClickExecute;

      boolean touch4501;
      boolean click4502;

      int focus_num;
    }

    Node node[];
    int node_num;

    //Node Relation Class
    class Relation {
      //Is exist a link between nodes?
      boolean suggest;
      boolean option;     // use M -> V only, (from/to M -> from/to V)

      Relation() {
        suggest = false;
        option = false;
      }
    }

    Relation relation[][];		//Node Relation information

    boolean optionInMining[][];// from M -> to M
    boolean optionInVisualization[][];// from V ->  to V

    void init_nodes(int num) {
      node_num = num;
      node = new Node[node_num];

      for (int i=0; i<node_num; i++) {
        node[i] = new Node();
      }

      relation = new Relation[control.moduleData.moduleIDs.length][control.moduleData.visuModuleIDs.length];

      for (int i=0; i<control.moduleData.moduleIDs.length; i++)
        for (int j=0; j<control.moduleData.visuModuleIDs.length; j++) {
          relation[i][j] = new Relation();
        }

      optionInMining = new boolean[control.moduleData.moduleIDs.length][control.moduleData.moduleIDs.length];
      optionInVisualization = new boolean[control.moduleData.visuModuleIDs.length][control.moduleData.visuModuleIDs.length];

      for (int i=0; i<control.moduleData.moduleIDs.length; i++)
        for (int j=0; j<control.moduleData.moduleIDs.length; j++) {
          optionInMining[i][j] = false;
        }

      for (int i=0; i<control.moduleData.visuModuleIDs.length; i++)
        for (int j=0; j<control.moduleData.visuModuleIDs.length; j++) {
          optionInVisualization[i][j] = false;
        }
    }

    NewMapData2(int num) {
      init_nodes(num);
    }

    void set_relation_data(int judge) {
      //		RelationalData rd;
      Color color[] = new Color[5];
      color[0] = new Color(0xff,0x66,0x00);
      color[1] = new Color(0xa5,0x2a,0x2a);
      color[2] = new Color(0x00,0x00,0xcc);
      color[3] = new Color(0x00,0xdb,0x47);
      color[4] = new Color(0xff,0x6f,0x94);




      if (judge==0) {
//			System.out.println("=====================Mining Modules=====================");
        for (int i=0; i<control.moduleData.moduleIDs.length; i++) {
          map.node[i].id = i;
          map.node[i].ID = control.moduleData.moduleIDs[i];
          map.node[i].eng_name = control.moduleData.module_names[i];
          if (control.sp1.inJapanese) {
            map.node[i].name = control.moduleData.miningModuleNamesInJapanese[i];
          } else {
            map.node[i].name = control.moduleData.module_names[i];
          }
          map.node[i].name_length = map.node[i].name.length()-checkNames(0,i)+checkNames(0,i)/2+1;
//					map.node[i].name_length = map.node[i].name.length()+1;
          map.node[i].info = control.moduleData.miningInformationText[i];


          map.node[i].color = Color.pink;
          map.node[i].focus_num = 0;
          map.node[i].toolType = control.moduleData.mining[i].getToolType();
          map.node[i].frame_color = toolTypeColor[map.node[i].toolType];


          if (map.node[i].ID == 99999) { //For SourceRead Module
            continue;
          }

          for (int j=0; j<setToolPanel.moduleInformation.moduleChecked[i].focusWordsExist.length; j++) {
            if (setToolPanel.moduleInformation.moduleChecked[i].focusWordsExist[j]==true) {
              map.node[i].focusWords = true;
            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.moduleChecked[i].focusVariablesExist.length; j++) {
            if (setToolPanel.moduleInformation.moduleChecked[i].focusVariablesExist[j]==true) {
              map.node[i].focus_m_t=true;////////tc
              map.node[i].name_length += 1;

              if (j==0) {
                map.node[i].focusTouchExecute=true;
                map.node[i].focus_num += 1;
              }
              if (j==1) {
                map.node[i].focusClickExecute=true;
                map.node[i].focus_num += 1;
              }
            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.moduleChecked[i].optionRequestsExist.length; j++) {
            if (setToolPanel.moduleInformation.moduleChecked[i].optionRequestsExist[j]==true) { //Visualization Option
              map.node[i].option_v_r=true;

              for (int k=0; k<setToolPanel.moduleInformation.moduleChecked[i].optionRequestNumbers.length; k++) { //display
                int VIndex = control.moduleData.findVisualizationModule(Integer.parseInt(setToolPanel.moduleInformation.moduleChecked[i].optionRequestNumbers[k]));

//                                System.out.println(control.moduleData.module_names[i]+" M->V,Display:"+setToolPanel.moduleInformation.moduleChecked[i].optionRequestNumbers[k]+" "+VIndex+" ");
//                                System.out.println(map2.node[VIndex].name);
                //                              System.out.println(control.moduleData.visu_module_names[VIndex]);

                map.relation[i][VIndex].option = true;
              }


            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.moduleChecked[i].optionRequests2Exist.length; j++) {
            if (setToolPanel.moduleInformation.moduleChecked[i].optionRequests2Exist[j]==true) { //Mining Option
              map.node[i].option_m_r=true;

              for (int k=0; k<setToolPanel.moduleInformation.moduleChecked[i].optionRequestNumbers2.length; k++) { //execute
                int MIndex = control.moduleData.findModule(Integer.parseInt(setToolPanel.moduleInformation.moduleChecked[i].optionRequestNumbers2[k]));

//                                System.out.println(control.moduleData.module_names[i]+" M->M,Execute:"+setToolPanel.moduleInformation.moduleChecked[i].optionRequestNumbers2[k]+" "+MIndex+" ");
                //                              System.out.println(map.node[MIndex].name);
                //                            System.out.println(control.moduleData.module_names[MIndex]);

                map.optionInMining[i][MIndex] = true;
              }


            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.moduleChecked[i].dataRequestExist.length; j++) {
            if (setToolPanel.moduleInformation.moduleChecked[i].dataRequestExist[j]==true) {
              map.node[i].dataget=true;
            }
          }
        }
//					SetNodePosition();
      } else {
//				System.out.println("=====================Visualization Modules=====================");
        for (int i=0; i<control.moduleData.visuModuleIDs.length; i++) {
          map2.node[i].id = i;
          map2.node[i].ID = control.moduleData.visuModuleIDs[i];
          map2.node[i].eng_name = control.moduleData.visu_module_names[i];
          if (control.sp1.inJapanese) {
            map2.node[i].name = control.moduleData.visualizationModuleNamesInJapanese[i];
          } else {
            map2.node[i].name = control.moduleData.visu_module_names[i];
          }
          map2.node[i].name_length = map2.node[i].name.length()-checkNames(1,i)+checkNames(1,i)/2+1;
//					map2.node[i].name_length = map2.node[i].name.length()+1;
          map2.node[i].info = control.moduleData.visualizationInformationText[i];


          map2.node[i].color = Color.cyan;
          map2.node[i].existpair=0;
          map2.node[i].focus_num = 0;
          map2.node[i].toolType = control.moduleData.visual[i].getToolType();
          map2.node[i].frame_color = toolTypeColor[map2.node[i].toolType];
          for (int j=0; j<setToolPanel.moduleInformation.visuModuleChecked[i].focusWordsExist.length; j++) {
            if (setToolPanel.moduleInformation.visuModuleChecked[i].focusWordsExist[j]==true) {
              map2.node[i].focusWords = true;
            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.visuModuleChecked[i].focusVariables2Exist.length; j++) {
            if (setToolPanel.moduleInformation.visuModuleChecked[i].focusVariables2Exist[j]==true) {
              map2.node[i].focus_v_t = true;/////////tc


              if (j==0) {
                map2.node[i].touch4501=true;
              }
              if (j==1) {
                map2.node[i].click4502=true;
              }
            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.visuModuleChecked[i].focusFunctionsExist.length; j++) {
            if (setToolPanel.moduleInformation.visuModuleChecked[i].focusFunctionsExist[j]==true) {
              map2.node[i].focus_v_r = true;/////////tc
              if (j==0) {
                map2.node[i].repaintOthersByTouch=true;
              }
              if (j==1) {
                map2.node[i].repaintOthersByClick=true;
              }
            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.visuModuleChecked[i].focusFunctions2Exist.length; j++) {
            if (setToolPanel.moduleInformation.visuModuleChecked[i].focusFunctions2Exist[j]==true) {
              map2.node[i].focus_m_r = true;/////////tctc
              if (j==0||j==2) {
                map2.node[i].executeByTouch=true;
              }
              if (j==1||j==3) {
                map2.node[i].executeByClick=true;
              }
            }
          }

          if (map2.node[i].touch4501==true || map2.node[i].click4502==true || map2.node[i].repaintOthersByTouch==true ||
              map2.node[i].repaintOthersByClick==true || map2.node[i].executeByTouch==true || map2.node[i].executeByClick==true) {
            map2.node[i].name_length += 1;
          }

          if (map2.node[i].focusTouchExecute == true || map2.node[i].executeByTouch == true) {
            map2.node[i].focus_num += 1;
          }
          if (map2.node[i].focusClickExecute == true || map2.node[i].executeByClick == true) {
            map2.node[i].focus_num += 1;
          }
          if (map2.node[i].repaintOthersByTouch == true) {
            map2.node[i].focus_num += 1;
          }
          if (map2.node[i].repaintOthersByClick == true) {
            map2.node[i].focus_num += 1;
          }

          for (int j=0; j<setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestsExist.length; j++) {
            if (setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestsExist[j]==true) {
              map2.node[i].option_v_r = true;

              for (int k=0; k<setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestNumbers.length; k++) { //display
                //                             System.out.println(control.moduleData.visu_module_names[i]+" V->V,Display:"+setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestNumbers[k]+" ");

                int VIndex = control.moduleData.findVisualizationModule(Integer.parseInt(setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestNumbers[k]));
                map.optionInVisualization[i][VIndex] = true;
              }
            }
          }
          for (int j=0; j<setToolPanel.moduleInformation.visuModuleChecked[i].optionRequests2Exist.length; j++) {
            if (setToolPanel.moduleInformation.visuModuleChecked[i].optionRequests2Exist[j]==true) {
              map2.node[i].option_m_r = true;

              for (int k=0; k<setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestNumbers2.length; k++) { //execute
                //                           System.out.println(control.moduleData.visu_module_names[i]+" V->M,Execute:"+setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestNumbers2[k]+" ");

                int MIndex = control.moduleData.findModule(Integer.parseInt(setToolPanel.moduleInformation.visuModuleChecked[i].optionRequestNumbers2[k]));
                map.relation[MIndex][i].option = true;
              }
            }
          }
        }
//				SetNode2Position();
      }
    }
  }
}

