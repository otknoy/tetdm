
//
// Core Program for TETDM
// SetToolPanel2.java Version 0.44
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

//import source.*;
import source.TextData.*;
import source.Utility.*;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.regex.*;

//Reconstruct panel from outside of select panel
//add new visualization pair for a mining

public class SetToolPanel2 extends JPanel implements ActionListener {
  Control control;
  Select select;
  PopUpForModuleSelect parentFrame;

  JPanel labelPanel;
  JLabel label1, label2;
  JLabel label3, label4;
  TopPanel topPanel;
  JPanel buttonPanel;
  JButton bt0, bt10, bt40;

  MiningModule mining[];
  VisualizationModule visual[];
  String miningNames[];
  String visualizationNames[];
  ModuleData moduleData;
  SettingData settingData;

  String inJapanese[];

  SetToolPanel2(Control control, PopUpForModuleSelect parentFrame, Select select, String inJapanese[]) {
    this.control = control;
    this.select = select;
    this.parentFrame = parentFrame;

    mining = control.moduleData.mining;
    visual = control.moduleData.visual;
    miningNames = control.moduleData.module_names;
    visualizationNames = control.moduleData.visu_module_names;
    moduleData = control.moduleData;
    settingData = control.sp1;


    setLayout(new BorderLayout());

    labelPanel = new JPanel(new GridLayout(2,2));
    labelPanel.setBackground(new Color(0x8b,0x47,0x26));
    add(labelPanel, BorderLayout.NORTH);

    this.inJapanese = inJapanese;
    if (settingData.inJapanese) {
      bt0 = new JButton(inJapanese[5]);
      bt10 = new JButton(inJapanese[6]);
      bt40 = new JButton(inJapanese[7]);
      label1 = new JLabel(inJapanese[8]);
      label2 = new JLabel(inJapanese[9]);
    } else {
      bt0 = new JButton("Reload Settings");
      bt10 = new JButton("Save Settings");
      bt40 = new JButton("Cancel");
      label1 = new JLabel("Mining Tools");
      label2 = new JLabel("Visualization Tools");
    }


    label1.setFont(new Font("Dialog", Font.BOLD, 20));
    label1.setForeground(Color.yellow);
    labelPanel.add(label1);


    label2.setFont(new Font("Dialog", Font.BOLD, 20));
    label2.setForeground(Color.yellow);
    labelPanel.add(label2);

    label3 = new JLabel("  -> ");
    label3.setFont(new Font("Dialog", Font.BOLD, 20));
    label3.setForeground(new Color(0x7f,0xff,0xf0));
    labelPanel.add(label3);

    label4 = new JLabel("  -> ");
    label4.setFont(new Font("Dialog", Font.BOLD, 20));
    label4.setForeground(new Color(0x7f,0xff,0xf0));
    labelPanel.add(label4);


    topPanel = new TopPanel(control, parentFrame, select, this);
    topPanel.setBackground(new Color(0x8b,0x47,0x26));
    add(topPanel);

    buttonPanel = new JPanel(new GridLayout());
    buttonPanel.setBackground(Color.cyan);
    add(buttonPanel, BorderLayout.SOUTH);



    bt0.addActionListener(this);
    bt0.setBackground(Color.cyan);
    buttonPanel.add(bt0);

    bt10.addActionListener(this);
    bt10.setBackground(Color.cyan);
    buttonPanel.add(bt10);

    bt40.addActionListener(this);
    bt40.setBackground(Color.cyan);
    buttonPanel.add(bt40);
  }

  void setMenu() {
    if (settingData.inJapanese) {
      bt0.setText(inJapanese[5]);
      bt10.setText(inJapanese[6]);
      bt40.setText(inJapanese[7]);
      label1.setText(inJapanese[8]);
      label2.setText(inJapanese[9]);
    } else {
      bt0.setText("Reload Settings");
      bt10.setText("Save Settings");
      bt40.setText("Cancel");
      label1.setText("Mining Tools");
      label2.setText("Visualization Tools");
    }
    repaint();
  }

  void resetAllPairingVisualization() {
    for (int k=0; k<miningNames.length; k++) {
      mining[k].pairingVisualizationID = new int[mining[k].pairingVisuID.length];
      for (int i=0; i<mining[k].pairingVisuID.length; i++) {
        mining[k].pairingVisualizationID[i] = mining[k].pairingVisuID[i];
      }

      moduleData.setModuleIndex(k, mining[k].pairingVisuID);

      for (int i=0; i<visualizationNames.length; i++) {
        moduleData.createPairingMiningID(i);
      }
    }
  }
  /*
  void setAllPairingVisualization()
  {
  	for(int k=0;k<miningNames.length;k++)
  	{
  		mining[k].pairingVisualizationID = new int[mining[k].pairingVisuID.length];
  		for(int i=0;i<mining[k].pairingVisuID.length;i++)
  			mining[k].pairingVisualizationID[i] = mining[k].pairingVisuID[i];

  		moduleData.setModuleIndex(k, mining[k].pairingVisuID);

  		for(int i=0;i<visualizationNames.length;i++)
  			moduleData.createPairingMiningID(i);
  	}
  }
  */
  //Action listener
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bt0) { //Load Original Settings
      resetAllPairingVisualization();
      settingData.readOriginalSettingFile();
      moduleData.initializeVisualiationModuleSelection();
      repaint();
    }

    if (e.getSource() == bt10) { //Save Settings
      settingData.saveSettingFile();
    }

    // Quit Settings
    if (e.getSource() == bt40) { //Cancel
      moduleData.initializeVisualiationModuleSelection();
      if (topPanel.informationWindow != null) {
        topPanel.informationWindow.dispose();
      }
      if (topPanel.information != null) {
        topPanel.information.dispose();
      }

      control.writeActionLog("CLOSE-MODULE-SELECT-WINDOW-SINGLE panelID= "+select.getPanelID());
      parentFrame.dispose();
    }
  }

  public void closeWindows() {
    if (topPanel.informationWindow != null) {
      topPanel.informationWindow.dispose();
    }
    if (topPanel.information != null) {
      topPanel.information.dispose();
    }
  }
}



class TopPanel extends JPanel implements MouseMotionListener, MouseListener {
  Control control;
  PopUpForModuleSelect parentFrame;
  Select select;

  SetToolPanel2 parentPanel;

  MiningModule mining[];
  VisualizationModule visual[];
  String miningNames[];
  String visualizationNames[];
  int miningToolType[];
  int visualizationToolType[];
  ModuleData moduleData;
  SettingData settingData;

  String typeWord[] = new String[] {"","(Si)","(Pr)","(sP)",""};


  PopUpForModuleInformation informationWindow;
  PopInformation information;
  boolean displayInformation = false;

  int lastTouchMining = -1, lastTouchVisualization = -1;
  int setMiningIndex = -1, setVisualizationIndex = -1;

  int fontSize = 20;

  int sizeX, sizeY;
  int mouseX, mouseY;

  int miningItemNumber, visualizationItemNumber;
  int miningLengthMax, visualizationLengthMax;

  int miningX, miningY;
  int miningHeight, miningWidth;
  int xNumberMining, yNumberMining;
  int miningWidthLimit, visualizationWidthLimit;

  int visualizationX, visualizationY;
  int visualizationWidth, visualizationHeight;
  int xNumberVisualization, yNumberVisualization;

  int touchNumberMining = -1, touchNumberVisualization = -1;

  boolean panelsReConstruction = false;

  String panelInformation;

  TopPanel(Control control, PopUpForModuleSelect parentFrame, Select select, SetToolPanel2 parentPanel) {
    this.control = control;
    this.parentFrame = parentFrame;
    this.select = select;
    this.parentPanel = parentPanel;

    mining = control.moduleData.mining;
    visual = control.moduleData.visual;
    miningNames = control.moduleData.module_names;
    visualizationNames = control.moduleData.visu_module_names;
    moduleData = control.moduleData;
    settingData = control.sp1;

    miningToolType = new int[miningNames.length];
    for (int i=0; i<miningNames.length; i++) {
      miningToolType[i] = control.moduleData.mining[i].getToolType();
    }

    visualizationToolType = new int[visualizationNames.length];
    for (int i=0; i<visualizationNames.length; i++) {
      visualizationToolType[i] = control.moduleData.visual[i].getToolType();
    }

    addMouseMotionListener(this);
    addMouseListener(this);

    miningItemNumber = miningNames.length;
    visualizationItemNumber = visualizationNames.length;

    miningLengthMax = miningNames[0].length();		//These lengths are based on ENGLISH Name
    for (int i=1; i<miningItemNumber; i++)
      if (miningNames[i].length() > miningLengthMax) {
        miningLengthMax = miningNames[i].length();
      }
    miningLengthMax += 4;

    visualizationLengthMax = visualizationNames[0].length();
    for (int i=1; i<visualizationItemNumber; i++)
      if (visualizationNames[i].length() > visualizationLengthMax) {
        visualizationLengthMax = visualizationNames[i].length();
      }
    visualizationLengthMax += 4;

    panelInformation = FILEIO.TextFileAllReadCode(new File(control.absolutePath + File.separator + "source" + File.separator + "Information.txt"));

    if (select.dummyShow == false) {
      setMiningIndex = moduleData.findModule(select.miningIDs[select.selected_module]);
//			this.parentPanel.label3.setText(miningNames[setMiningIndex]);moduleData.miningModuleNamesInJapanese

      if (settingData.inJapanese) {
        this.parentPanel.label3.setText("  -> "+moduleData.miningModuleNamesInJapanese[setMiningIndex]);
      } else {
        this.parentPanel.label3.setText("  -> "+moduleData.module_names[setMiningIndex]);
      }
    }
  }

  void draw_background(Graphics2D g2) {
    sizeX = getWidth();
    sizeY = getHeight();
    miningX = 10;
    miningY = 10;
    miningWidth = (sizeX-20)/2-5;
    miningHeight = sizeY-20;
    visualizationX = sizeX/2;
    visualizationY = 10;
    visualizationWidth = (sizeX-20)/2;
    visualizationHeight = sizeY-20;


    miningWidthLimit = miningLengthMax * fontSize;
    visualizationWidthLimit = visualizationLengthMax * fontSize;

    miningWidthLimit *= 0.6;
    visualizationWidthLimit *= 0.6;


    xNumberMining = (int)(miningWidth/miningWidthLimit);
    if (xNumberMining == 0) {
      xNumberMining = 1;
    }
    yNumberMining = (int)(miningItemNumber/xNumberMining);
    if (miningItemNumber > xNumberMining * yNumberMining) {
      yNumberMining++;
    }

    if (yNumberMining * (xNumberMining-1) >= miningItemNumber) {
      xNumberMining--;
    }

//		System.out.println("miningItemNumber= "+miningItemNumber+"xNumberMining= "+xNumberMining+" yNumberMining= "+yNumberMining);

    xNumberVisualization = (int)(visualizationWidth/visualizationWidthLimit);
    if (xNumberVisualization == 0) {
      xNumberVisualization = 1;
    }
    yNumberVisualization = (int)(visualizationItemNumber/xNumberVisualization);
    if (visualizationItemNumber > xNumberVisualization * yNumberVisualization) {
      yNumberVisualization++;
    }

    if (yNumberVisualization * (xNumberVisualization-1) >= visualizationItemNumber) {
      xNumberVisualization--;
    }

    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,0, sizeX, sizeY);
  }

  public void drawBoxes(Graphics2D g2, int x, int y, int width, int height, int xNumber, int yNumber, String names[], int type[]) {
    int boxSizeX = width/xNumber;
    int boxSizeY = height/yNumber;

    int setFontSize = fontSize;

    BasicStroke wideStroke = new BasicStroke(4.0f);
    g2.setStroke(wideStroke);
    g2.setColor(Color.white);

    for (int i=0; i<xNumber+1; i++) {
      g2.drawLine(x+boxSizeX*i,y,x+boxSizeX*i,y+boxSizeY*yNumber);
    }

    for (int i=0; i<yNumber+1; i++) {
      g2.drawLine(x,y+boxSizeY*i,x+boxSizeX*xNumber,y+boxSizeY*i);
    }

    if (setFontSize >= boxSizeY) {
      setFontSize = boxSizeY - 2;
    }

    g2.setFont(new Font("Dialog", Font.BOLD, setFontSize));
    for (int i=0; i<xNumber; i++)
      for (int j=0; j<yNumber; j++)
        if (i*yNumber+j < names.length) {
          g2.drawString(names[i*yNumber+j]+typeWord[type[i*yNumber+j]], x + boxSizeX*i + (int)(setFontSize*0.5), y + (int)(boxSizeY*(j+0.5)) + (int)(setFontSize*0.5) - 2);
        }
  }

  public void drawOneBox(Graphics2D g2, int x, int y, int width, int height, int xNumber, int yNumber, int n, int limit, Color color) {
    int boxSizeX = width/xNumber;
    int boxSizeY = height/yNumber;

    if (n == -1 || n >= limit) {
      return;
    }

    int i = n/yNumber;
    int j = n%yNumber;

    BasicStroke wideStroke = new BasicStroke(4.0f);
    g2.setStroke(wideStroke);
    g2.setColor(color);

    g2.drawRect(x + boxSizeX*i, y + boxSizeY*j ,boxSizeX,boxSizeY);
  }

  public void drawOneBoxBackground(Graphics2D g2, int x, int y, int width, int height, int xNumber, int yNumber, int n, Color color) {
    int boxSizeX = width/xNumber;
    int boxSizeY = height/yNumber;

    int i = n/yNumber;
    int j = n%yNumber;

    g2.setColor(color);
    g2.fillRect(x + boxSizeX*i, y + boxSizeY*j ,boxSizeX, boxSizeY);
  }

  public void drawPairBoxes(Graphics2D g2, int x, int y, int width, int height, int xNumber, int yNumber, int n[], int limit) {
    for (int i=0; i<n.length; i++) {
      drawOneBox(g2, x, y, width, height, xNumber, yNumber, n[i], limit, Color.RED);
    }
  }



  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    draw_background(g2);	//Set background

//		if(english)
//		{
//			drawBoxes(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, miningNames);
//			drawBoxes(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
//					  visualizationNames);
//		}

    if (setMiningIndex != -1) {
      drawOneBoxBackground(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, setMiningIndex, new Color(0xff,0x7f,0x00));

      for (int i=0; i<mining[setMiningIndex].pairingVisualizationIndex.length; i++)
        drawOneBoxBackground(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
                             mining[setMiningIndex].pairingVisualizationIndex[i], new Color(0xff,0xa5,0x00));
    }
    if (setVisualizationIndex != -1) {
      drawOneBoxBackground(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
                           setVisualizationIndex, new Color(0xff,0x7f,0x00));

      for (int i=0; i<visual[setVisualizationIndex].pairingMiningIndex.length; i++)
        drawOneBoxBackground(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining,
                             visual[setVisualizationIndex].pairingMiningIndex[i], new Color(0xff,0xa5,0x00));
    }

    for (int i=0; i<miningNames.length; i++)
      if (moduleData.miningModuleSelection[i] == false) {
        drawOneBoxBackground(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, i, Color.gray);
      }
    for (int i=0; i<visualizationNames.length; i++)
      if (moduleData.visualizationModuleSelection[i] == false) {
        drawOneBoxBackground(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization, i, Color.gray);
      }

    if (settingData.inJapanese) {
      drawBoxes(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, moduleData.miningModuleNamesInJapanese, miningToolType);
      drawBoxes(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
                moduleData.visualizationModuleNamesInJapanese, visualizationToolType);
    } else {
      drawBoxes(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, moduleData.module_names, miningToolType);
      drawBoxes(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
                moduleData.visu_module_names, visualizationToolType);
    }

    drawOneBox(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, touchNumberMining, miningItemNumber, Color.green);
    drawOneBox(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
               touchNumberVisualization, visualizationItemNumber, Color.green);

    drawOneBox(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining, setMiningIndex, miningItemNumber, Color.red);
    drawOneBox(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
               setVisualizationIndex, visualizationItemNumber, Color.red);



    if (setVisualizationIndex == -1) {
      if (setMiningIndex != -1)
        drawPairBoxes(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
                      mining[setMiningIndex].pairingVisualizationIndex, visualizationItemNumber);
      else if (touchNumberMining >= 0 && touchNumberMining < miningItemNumber)
        drawPairBoxes(g2, visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization,
                      mining[touchNumberMining].pairingVisualizationIndex, visualizationItemNumber);
    }

    if (setMiningIndex == -1) {
      if (setVisualizationIndex != -1)
        drawPairBoxes(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining,
                      visual[setVisualizationIndex].pairingMiningIndex, miningItemNumber);
      else if (touchNumberVisualization >= 0 && touchNumberVisualization < visualizationItemNumber)
        drawPairBoxes(g2, miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining,
                      visual[touchNumberVisualization].pairingMiningIndex, miningItemNumber);
    }

    createModuleInformationWindow();
  }

  public void update(Graphics g) {	//Avoid from blinking
    paintComponent(g);
  }

  public int findNumber(int x, int y, int width, int height, int xNumber, int yNumber) {
    int boxSizeX = width/xNumber;
    int boxSizeY = height/yNumber;

    if (mouseX <= x || mouseY <= y || mouseX >= x+boxSizeX*xNumber || mouseY >= y+boxSizeY*yNumber) {
      return -1;
    }

    int i = (int)((mouseX-x)/boxSizeX);
    int j = (int)((mouseY-y)/boxSizeY);

    return i*yNumber+j;
  }

  public boolean createModuleInformation(int x, int y, int width, int height, int xNumber, int yNumber, int number, int lastNumber, int limit, String information[], boolean right) {
    int boxSizeX,boxSizeY,i,j;
    int informationSizeX,informationSizeY;
    int informationFontSize;
    Point position = getLocationOnScreen();

    int rightMargin, leftMargin;

    if (number >=0 && number < limit) {
      boxSizeX = width/xNumber;
      boxSizeY = height/yNumber;
      i = number/yNumber;
      j = number%yNumber;

      if (right) {
        rightMargin = sizeX - (x + boxSizeX*(i+1)) + sizeX * (control.panelNumber-select.getPanelID() - 1);
        leftMargin = sizeX * select.getPanelID();
      } else {
        leftMargin = (int)(sizeX * (select.getPanelID() + 0.5) + boxSizeX*i);
        rightMargin = sizeX - (x + boxSizeX*(i+1)) + sizeX * (control.panelNumber-select.getPanelID() - 1);
      }

      //System.out.println("Left = "+leftMargin+" , Right = "+rightMargin);

      if (rightMargin > leftMargin) {
        informationSizeX = rightMargin;
        right = true;
      } else {
        informationSizeX = leftMargin;
        right = false;
      }

      informationFontSize = informationSizeX / (int)((information[number].length()+1));
      if (informationFontSize > fontSize) {
        informationFontSize = fontSize;
        informationSizeX = informationFontSize *  (int)((information[number].length()+1));
      }
      informationSizeY = (int)(informationFontSize+8);

      if (number == lastNumber) {
        //System.out.println("number="+number);
        return false;
      }

      if (right)
        informationWindow = new PopUpForModuleInformation((int)position.getX() + x + boxSizeX*(i+1), (int)position.getY() + y + boxSizeY*j,
            informationSizeX, informationSizeY, information[number], informationFontSize);
      else
        informationWindow = new PopUpForModuleInformation((int)position.getX() + x + boxSizeX*i - informationSizeX, (int)position.getY() + y + boxSizeY*j,
            informationSizeX, informationSizeY, information[number], informationFontSize);
      //System.out.println("Information Created");

      return true;
    }
    return false;
  }



  public void createModuleInformationWindow() {
    boolean created;
    PopUpForModuleInformation backup = informationWindow;



    created = createModuleInformation(miningX, miningY, miningWidth, miningHeight, xNumberMining, yNumberMining,
                                      touchNumberMining, lastTouchMining, miningItemNumber, moduleData.miningInformationText, true);
    if (created) {
      lastTouchVisualization = -1;
      if (backup != null) {
        backup.dispose();
      }
    }
    lastTouchMining = touchNumberMining;


    created = createModuleInformation(visualizationX, visualizationY, visualizationWidth, visualizationHeight, xNumberVisualization, yNumberVisualization,
                                      touchNumberVisualization, lastTouchVisualization, visualizationItemNumber, moduleData.visualizationInformationText, false);
    if (created) {
      lastTouchMining = -1;
      if (backup != null) {
        backup.dispose();
      }
    }
    lastTouchVisualization = touchNumberVisualization;

    if (touchNumberMining == -1 && touchNumberVisualization == -1)
      if (backup != null) {
        backup.dispose();
      }
  }

  public void createInformation() {
    Point position = getLocationOnScreen();
    int setFontSize;

    int width = getWidth()-20;
    int height = getHeight();
    int fontA = width/38;
    int fontB = height/29;

    if (fontA < fontB) {
      setFontSize = fontA;
      height = setFontSize * 29;
    } else {
      setFontSize = fontB;
      width = setFontSize * 38;
    }
    if (setFontSize > fontSize) {
      setFontSize = fontSize;
    }

    information = new PopInformation((int)position.getX()+10, (int)position.getY()+10, width, height, panelInformation, setFontSize);
  }

  //MouseMotionListener
  public void mouseDragged(MouseEvent me) {}
  public void mouseMoved(MouseEvent me) {
    mouseX = me.getX();
    mouseY = me.getY();

//        if(mouseY >= 0 && mouseY < miningY)
    if (mouseY > 10 && (mouseX < miningX || mouseX > visualizationX + visualizationWidth)) {
      if (displayInformation == false) {
        displayInformation = true;
        createInformation();
      }
    } else {
      if (displayInformation == true) {
        information.dispose();
      }

      displayInformation = false;
    }

    touchNumberMining = findNumber(miningX,miningY,miningWidth,miningHeight,xNumberMining,yNumberMining);
    touchNumberVisualization = findNumber(visualizationX,visualizationY,visualizationWidth,visualizationHeight,xNumberVisualization,yNumberVisualization);

//		System.out.println(touchNumberMining+":"+touchNumberVisualization);

    repaint();
  }

  //MouseListener
  public void mousePressed(MouseEvent me) {}	//Press
  public void mouseReleased(MouseEvent me) {}	//Release
  public void mouseEntered(MouseEvent me) {}	//Enter an area
  public void mouseExited(MouseEvent me) {}	//Exit an area
  public void mouseClicked(MouseEvent me) {	//Click
//		System.out.println("CLICKED:TOUCH IN ="+touchNumberMining+":"+touchNumberVisualization+" SET="+setMiningIndex+":"+setVisualizationIndex);

    //Right Click
    if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
//			System.out.println("RightClick:(touch)"+touchNumberMining+","+touchNumberVisualization+" (set) "+setMiningIndex+","+setVisualizationIndex);
      if (touchNumberMining >= 0 && touchNumberMining < miningItemNumber) {
        if (moduleData.miningModuleSelection[touchNumberMining] == true) {
          moduleData.miningModuleSelection[touchNumberMining] = false;
          setMiningIndex = -1;
          this.parentPanel.label3.setText("  -> ");
        } else {
          moduleData.miningModuleSelection[touchNumberMining] = true;
        }

        moduleData.initializeVisualiationModuleSelection();
        panelsReConstruction = true;
        repaint();
        return;
      }

      if (touchNumberVisualization >= 0 && touchNumberVisualization < visualizationItemNumber) {
        if (setMiningIndex != -1) {
          if (pairingCheck(setMiningIndex, touchNumberVisualization)) {
            deletePairingVisualization(setMiningIndex, touchNumberVisualization);
            moduleData.initializeVisualiationModuleSelection();
            setVisualizationIndex = -1;

            if (settingData.inJapanese) {
              this.parentPanel.label4.setText("  -> "+moduleData.visualizationModuleNamesInJapanese[setVisualizationIndex]);
            } else {
              this.parentPanel.label4.setText("  -> "+moduleData.visu_module_names[setVisualizationIndex]);
            }
          } else {
            moduleData.visualizationModuleSelection[touchNumberVisualization] = true;
            addPairingVisualization(setMiningIndex, touchNumberVisualization);
          }
          panelsReConstruction = true;
        }
        repaint();
        return;
      }
    }

    // Double Clicked
    if (me.getClickCount() >= 2) {
      if (touchNumberMining >= 0 && touchNumberMining < miningItemNumber) {
        setMiningIndex = touchNumberMining;
        if (settingData.inJapanese) {
          this.parentPanel.label3.setText("  -> "+moduleData.miningModuleNamesInJapanese[setMiningIndex]);
        } else {
          this.parentPanel.label3.setText("  -> "+moduleData.module_names[setMiningIndex]);
        }


        if (mining[setMiningIndex].pairingVisualizationIndex.length == 0) {
          return;
        }
        setVisualizationIndex = mining[setMiningIndex].pairingVisualizationIndex[0];

        if (settingData.inJapanese) {
          this.parentPanel.label4.setText("  -> "+moduleData.visualizationModuleNamesInJapanese[setVisualizationIndex]);
        } else {
          this.parentPanel.label4.setText("  -> "+moduleData.visu_module_names[setVisualizationIndex]);
        }
      }

      if (touchNumberVisualization >= 0 && touchNumberVisualization < visualizationItemNumber) {
        setVisualizationIndex = touchNumberVisualization;

        if (settingData.inJapanese) {
          this.parentPanel.label4.setText("  -> "+moduleData.visualizationModuleNamesInJapanese[setVisualizationIndex]);
        } else {
          this.parentPanel.label4.setText("  -> "+moduleData.visu_module_names[setVisualizationIndex]);
        }

        if (visual[setVisualizationIndex].pairingMiningIndex.length == 0) {
          return;
        }
        setMiningIndex = visual[setVisualizationIndex].pairingMiningIndex[0];

        if (settingData.inJapanese) {
          this.parentPanel.label3.setText("  -> "+moduleData.miningModuleNamesInJapanese[setMiningIndex]);
        } else {
          this.parentPanel.label3.setText("  -> "+moduleData.module_names[setMiningIndex]);
        }
      }

      setMining(setMiningIndex);



//			System.out.println("DOUBLE CLICKED:TOUCH="+touchNumberMining+":"+touchNumberVisualization+" SET="+setMiningIndex+":"+setVisualizationIndex);
      setPanel();
    }

    if (touchNumberMining >= 0 && touchNumberMining < miningItemNumber) {
      setMining(touchNumberMining);

      if (touchNumberMining == setMiningIndex) {
        setMiningIndex = -1;
        this.parentPanel.label3.setText("  -> ");
      } else {
        setMiningIndex = touchNumberMining;
        if (settingData.inJapanese) {
          this.parentPanel.label3.setText("  -> "+moduleData.miningModuleNamesInJapanese[setMiningIndex]);
        } else {
          this.parentPanel.label3.setText("  -> "+moduleData.module_names[setMiningIndex]);
        }
      }
    }

    if (touchNumberVisualization >= 0 && touchNumberVisualization < visualizationItemNumber) {
      setVisualization(touchNumberVisualization);

      if (touchNumberVisualization == setVisualizationIndex) {
        setVisualizationIndex = -1;
        this.parentPanel.label4.setText("  -> ");
      } else {
        setVisualizationIndex = touchNumberVisualization;
        if (settingData.inJapanese) {
          this.parentPanel.label4.setText("  -> "+moduleData.visualizationModuleNamesInJapanese[setVisualizationIndex]);
        } else {
          this.parentPanel.label4.setText("  -> "+moduleData.visu_module_names[setVisualizationIndex]);
        }
      }
    }

    repaint();

//		System.out.println("CLICKED:TOUCH OUT="+touchNumberMining+":"+touchNumberVisualization+" SET="+setMiningIndex+":"+setVisualizationIndex);

    if (setMiningIndex != -1 && setVisualizationIndex != -1) {
      setPanel();
    }
  }

  void setMining(int miningIndex) {
    if (moduleData.miningModuleSelection[miningIndex] == false) {
      moduleData.miningModuleSelection[miningIndex] = true;
      moduleData.initializeVisualiationModuleSelection();
      panelsReConstruction = true;
    }
  }

  void setVisualization(int visualizationIndex) {
    if (moduleData.visualizationModuleSelection[visualizationIndex] == false) {
      moduleData.visualizationModuleSelection[visualizationIndex] = true;
      panelsReConstruction = true;
    } else {
      moduleData.initializeVisualiationModuleSelection();
    }
  }

  void setPanel() {
    if (moduleData.visualizationModuleSelection[setVisualizationIndex] == false) {
      moduleData.visualizationModuleSelection[setVisualizationIndex] = true;
      panelsReConstruction = true;
    }

    //create backup
    control.createBackupIDs();

    if (panelsReConstruction == true) {
//          System.out.println("RECONSTRUCTION");

      addPairingVisualization(setMiningIndex, setVisualizationIndex);

      moduleData.initializeModuleSubset();

      settingData.miningID[select.getPanelID()] = mining[setMiningIndex].getModuleID();
      settingData.visualizationID[select.getPanelID()] = visual[setVisualizationIndex].getModuleID();

      try {
        control.panelsReConstruction();
      } catch (Exception e) {
        System.out.println("ERROR at setPanel in SetToolPanel2.java A");
        e.printStackTrace();

        control.traceBackupIDs();
        control.panelSet();
      }
    }

    try {
      select.changePanel(mining[setMiningIndex].getModuleID(), visual[setVisualizationIndex].getModuleID());
    } catch (Exception e) {
      System.out.println("ERROR at setPanel in SetToolPanel2.java B");
      e.printStackTrace();

      control.traceBackupIDs();
      control.panelSet();
    }

    settingData.miningID[select.getPanelID()] = mining[setMiningIndex].getModuleID();
    settingData.visualizationID[select.getPanelID()] = visual[setVisualizationIndex].getModuleID();

    control.writeActionLog("CLOSE-MODULE-SELECT-WINDOW-SINGLE panelID= "+select.getPanelID());

    if (informationWindow != null) {
      informationWindow.dispose();
    }
    parentFrame.dispose();
  }

  public int[] addOneData(int array[], int data) {
    int length = array.length;
    int newArray[] = new int[length+1];

    for (int i=0; i<length; i++) {
      newArray[i] = array[i];
    }
    newArray[length] = data;

    return newArray;
  }

  public boolean pairingCheck(int mindex, int vindex) {
    int length = mining[mindex].pairingVisualizationID.length;
    int vid = visual[vindex].getModuleID();

    for (int i=0; i<length; i++)
      if (mining[mindex].pairingVisualizationID[i] == vid) {
        return true;
      }
    return false;
  }

  public void addPairingVisualization(int mindex, int vindex) {
    //PairingVisualization
    int length = mining[mindex].pairingVisualizationID.length;
    int vid = visual[vindex].getModuleID();

    for (int i=0; i<length; i++)
      if (mining[mindex].pairingVisualizationID[i] == vid) {
        return;
      }

    mining[mindex].pairingVisualizationID = addOneData(mining[mindex].pairingVisualizationID, vid);
    mining[mindex].pairingVisualizationIndex = addOneData(mining[mindex].pairingVisualizationIndex, vindex);

    //PairingMining
    moduleData.createPairingMiningID(vindex);

//		System.out.println("Added:"+mindex+","+vindex);
  }

  public int[] deleteOneData(int array[], int deleteIndex) {
    int length = array.length;
    int newArray[] = new int[length-1];
    int count = 0;

    for (int i=0; i<length; i++)
      if (i != deleteIndex) {
        newArray[count++] = array[i];
      }

    return newArray;
  }

  public void deletePairingVisualization(int mindex, int vindex) {
    //PairingVisualization
    int length = mining[mindex].pairingVisualizationID.length;
    int vid = visual[vindex].getModuleID();
    int check = -1;

    if (length == 1) { //NOT DELETE
      return;
    }

    for (int i=0; i<length; i++)
      if (mining[mindex].pairingVisualizationID[i] == vid) {
        check = i;
        break;
      }
    if (check == -1) { //NOT FOUND
      return;
    }

    mining[mindex].pairingVisualizationID = deleteOneData(mining[mindex].pairingVisualizationID, check);
    mining[mindex].pairingVisualizationIndex = deleteOneData(mining[mindex].pairingVisualizationIndex, check);

    //PairingMining
    moduleData.createPairingMiningID(vindex);

//		System.out.println("Deleted:"+mindex+","+vindex);
  }
}


class InformationPanel extends JPanel {
  InformationPanel(String message, int fontSize) {
    setBackground(new Color(0xff, 0xf8, 0xdc));
    JLabel label = new JLabel(message);
    label.setForeground(Color.black);
    label.setFont(new Font("Dialog", Font.BOLD, fontSize));
    label.setBackground(new Color(0xff, 0xf8, 0xdc));
    add(label);
  }
}

class PopUpForModuleInformation extends JFrame {
  InformationPanel informationPanel;
  Container pane = getContentPane();

  PopUpForModuleInformation(int X, int Y, int width, int height, String message, int fontSize) {
    super("Information");
    pane.setLayout(new BorderLayout());

    setBounds(X, Y, width, height);
    setBackground(new Color(0xff, 0xf8, 0xdc));
    setAlwaysOnTop(true);
    setUndecorated(true);

    informationPanel = new InformationPanel(message, fontSize);
    pane.add(informationPanel);
    setVisible(true);
  }
}

class InformationTextPanel extends JPanel {
  JTextPane text1;

  InformationTextPanel(String displayText, int font) {
    setBackground(new Color(0xff, 0xf8, 0xdc));
    text1 = new JTextPane();
    text1.setContentType("text/plain");
    text1.setFont(new Font("Dialog", Font.BOLD, font));
    text1.setForeground(Color.black);
    text1.setBackground(new Color(0xff, 0xf8, 0xdc));
    text1.setText(displayText);
    text1.setEditable(false);
    text1.setCaretPosition(0);
    add(text1);
  }
}

class PopInformation extends JFrame {
  InformationTextPanel informationTextPanel;
  Container pane = getContentPane();

  PopInformation(int X, int Y, int width, int height, String message, int fontSize) {
    super("Information");
    pane.setLayout(new BorderLayout());

    setBounds(X, Y, width, height);
    setBackground(new Color(0xff, 0xf8, 0xdc));
    setAlwaysOnTop(true);
    setUndecorated(true);

    informationTextPanel = new InformationTextPanel(message, fontSize);
    pane.add(informationTextPanel);
    setVisible(true);
  }
}
