//
// Visuzalization module for TETDM
// OKmap.java Version 0.30
// Copyright(C) 2013 System Interface Laboratory ALL RIGHTS RESERVED.
//
// This program is provided under the licenses.
// You Should read the README file.
//

package module.VisualizationModules.OKmap;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class OKmap extends VisualizationModule implements MouseListener, MouseMotionListener, MouseWheelListener {
  final int NODE_SIZE =18;

  //For objects operation
  int mousex, mousey;			//Coordinates for a mouse
  int old_mousex, old_mousey;	//Last Coordinates for a mosue
  int dx, dy;					//Distance for mouse moving

  int touchNumber, oldTouchNumber;				//Node Number of being touched
  int touchMap,touchCluster,touchNode;	//Touch Node Information


  boolean moving;			//Flag for node moving
  boolean dragging;		//Flag for field moving
  boolean node_name;		//nodename not display

  boolean firstTime;
  boolean dataPrepared;

  ////For spring model
  double bane_k;		//Spring constant value	K 0.2
  double minus;		//Repulsion rate against the gravity 0.8

  MapData map;
  ArrayList<MapData> array;// = new ArrayList<MapData>();

  int mapNumber;		// number of maps
  int seaLevel;		// drawing level of map

  MapData maps[];


  float dash[] = new float[50];

  void set_dash() {
    for (int i=0; i<25; i++) {
      dash[i*2] = 10;
      dash[i*2+1] = 20;
    }
  }

  public OKmap() {
    setModuleID(9);			// Set your module ID after you have got it
    dataNumbers = new int[] {0,2,0,0,   // b,i,d,S
                             0,0,0,1,    // bA,iA,dA,SA			StringArray 1+
                             2,1,0
                            };     // bA2,iA2,dA2
  }

  public void initializePanel() {
    addMouseListener(this);			//MouseListener
    addMouseMotionListener(this);	//MouseMotionListener
    addMouseWheelListener(this);
  }

  public void initializeData() {
    dataPrepared = false;

    touchNumber = -1;
    moving = false;
    dragging = false;
    firstTime = true;
    node_name = false;
    array = new ArrayList<MapData>();
    ////For spring model
    bane_k = 0.2;		//Spring constant value	K 0.2
    minus = 0.8;		//Repulsion rate against the gravity 0.8

    seaLevel = 0;

    set_dash();
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        break;

      case 100://Create Link Data in MapData
        for (int c=0; c<map.clusterNumber; c++)
          for (int i=0; i<map.cl[c].num; i++)
            for (int j=0; j<map.cl[c].num; j++) {
              map.cl[c].relation[i][j].link = link[map.cl[c].node[i].id][map.cl[c].node[j].id];
              map.cl[c].relation[i][j].stronglink = stronglink[map.cl[c].node[i].id][map.cl[c].node[j].id];
            }
        //Initialize Node Size
        for (int c=0; c<map.clusterNumber; c++)
          for (int i=0; i<map.cl[c].num; i++) {
            map.cl[c].node[i].radius = NODE_SIZE * map.cl[c].node[i].include_node;
          }

        //Spring Model
        setting_nodes();

        //Add map to MapData Array
        array.add(map);
        break;


      case 400:
        initializeMultiMapdata();
        set_node_position();
//					System.out.println(mapNumber);
        dataPrepared = true;
        break;

      case 4501:
        touchNumber = text.focus.mainFocusSegment;
        searchFocusNode();
        repaint();
        break;
    }
  }

/////////////////////////////////////////////////////////////////DATA RECEIVING///////////////////////////////////////////////////////////////////1

  int nodeNumber;

  boolean link[][];
  boolean stronglink[][];
  int nodeSize[];//Number of nodes included in a Cluster

  String names[];


  void initializeNodeNumber() {
    link = new boolean[nodeNumber][nodeNumber];
    stronglink = new boolean[nodeNumber][nodeNumber];
    nodeSize = new int[nodeNumber];

    for (int i=0; i<nodeNumber; i++) {
      nodeSize[i] = 1;
    }
  }

  public boolean setData(int dataID, int data) {
    switch (dataID) {
      case 0:	// Number of All Nodes
        nodeNumber = data;
        initializeNodeNumber();
        return true;

      case 1: // Number of Cluster
        map = new MapData(data);
        return true;
    }
    return false;
  }

  public boolean setData(int dataID, int data[][]) {
    switch (dataID) {
      case 2: // Cluster Elements
        for (int i=0; i<map.clusterNumber; i++) {
          map.cl[i].initializeCluster(data[i].length);
          map.cl[i].elements = data[i];
          initializeNodePosition(i);

          for (int j=0; j<data[i].length; j++) {
            map.cl[i].node[j].id = data[i][j];
            map.cl[i].node[j].include_node = nodeSize[map.cl[i].elements[j]];

            if (j != 0) {
              nodeSize[map.cl[i].elements[0]] += nodeSize[map.cl[i].elements[j]];
              nodeSize[map.cl[i].elements[j]] = 0;
            }
          }
        }
        return true;
    }
    return false;
  }

  public boolean setData(int dataID, boolean data[][]) {
    switch (dataID) {
      case 3: // Link
        link = data;
        return true;

      case 4: // Strong Link
        stronglink = data;
        return true;
    }
    return false;
  }

  public boolean setData(int dataID, String name[]) {
    switch (dataID) {
      case 10:	// Node(Segment) Names
        names = name;
        return true;

      default://100-	Area(Cluster) Names
        if (dataID-100 < 0 || dataID-100 >= maps.length) {
          break;
        }

        for (int i=0; i<name.length; i++) {
          maps[dataID-100].cl[i].ClusterName = name[i];
        }
        return true;
    }
    return false;
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////1
  ///////////////////////////////////////////////////////////////////////////////////////////////

  class MapData {
    class Node {
      int id;// Cluster Numbering
      double x;
      double y;
      double radius = 12;
      double include_node = 1;

      int link_num = 0;//Number of links
    }

    class Relation {
      boolean link;			//Is exist a link between nodes?
      boolean stronglink;
      double rdist;			// real distance for spring model
      double idist;			// ideal distance for spring model
    }

    class Cluster {
      String ClusterName;
      int num;
      int elements[];

      Node node[];
      Relation relation[][];

      boolean groundCluster;		//
      double minx,miny,maxx,maxy;	//for BoundingBox
      int outline_num;			//for outline
      double shapex[],shapey[];	//for outline

      void initializeCluster(int elementsNumber) {
        node = new Node[elementsNumber];
        relation = new Relation[elementsNumber][elementsNumber];
        elements = new int[elementsNumber];
        num = elementsNumber;
        groundCluster = false;

        for (int i=0; i<elementsNumber; i++) {
          node[i] = new Node();
          for (int j=0; j<elementsNumber; j++) {
            relation[i][j] = new Relation();
          }
        }
      }
    }

    Cluster cl[];
    int clusterNumber;

    MapData() {}

    MapData(int number) {
      clusterNumber = number;
      cl = new Cluster[clusterNumber];

      for (int i=0; i<clusterNumber; i++) {
        cl[i] = new Cluster();
      }
    }


    void changeScale(double hiritu, int clus, double centerX, double centerY) {
      double dx, dy;
      double theta, r;

      for (int i=0; i<cl[clus].num; i++) {
        cl[clus].node[i].radius *= hiritu;//change scale

        dx = cl[clus].node[i].x - centerX;
        dy = cl[clus].node[i].y - centerY;

        if (dx==0 && dy==0) { //Impossible to calculate if Zero vector
          r = 0;
          theta = 0;
        } else {
          r = Math.sqrt(dx*dx + dy*dy);
          theta = Math.acos(dx/r);	//Calculate an angle theta
          r *= hiritu;
        }

        cl[clus].node[i].x = centerX + r * Math.cos(theta);

        if (dy<0) {
          cl[clus].node[i].y = centerY - r * Math.sin(theta);
        } else {
          cl[clus].node[i].y = centerY + r * Math.sin(theta);
        }
      }

      for (int i=0; i<cl[clus].outline_num; i++) {
        dx = cl[clus].shapex[i] - centerX;
        dy = cl[clus].shapey[i] - centerY;

        if (dx==0 && dy==0) { //Impossible to calculate if Zero vector
          r = 0;
          theta = 0;
        } else {
          r = Math.sqrt(dx*dx + dy*dy);
          theta = Math.acos(dx/r);	//Calculate an angle theta
          r *= hiritu;
        }

        cl[clus].shapex[i] = centerX + r * Math.cos(theta);

        if (dy<0) {
          cl[clus].shapey[i] = centerY - r * Math.sin(theta);
        } else {
          cl[clus].shapey[i] = centerY + r * Math.sin(theta);
        }
      }
    }

    void createBoundingBox(int clus) {
      cl[clus].maxx = cl[clus].node[0].x + cl[clus].node[0].radius;
      cl[clus].maxy = cl[clus].node[0].y + cl[clus].node[0].radius;
      cl[clus].minx = cl[clus].node[0].x - cl[clus].node[0].radius;
      cl[clus].miny = cl[clus].node[0].y - cl[clus].node[0].radius;

      for (int i=1; i<cl[clus].num; i++) {
        if (cl[clus].maxx < cl[clus].node[i].x + cl[clus].node[i].radius) {
          cl[clus].maxx = cl[clus].node[i].x + cl[clus].node[i].radius;
        }

        if (cl[clus].maxy < cl[clus].node[i].y + cl[clus].node[i].radius) {
          cl[clus].maxy = cl[clus].node[i].y + cl[clus].node[i].radius;
        }

        if (cl[clus].minx > cl[clus].node[i].x - cl[clus].node[i].radius) {
          cl[clus].minx = cl[clus].node[i].x - cl[clus].node[i].radius;
        }

        if (cl[clus].miny > cl[clus].node[i].y - cl[clus].node[i].radius) {
          cl[clus].miny = cl[clus].node[i].y - cl[clus].node[i].radius;
        }
      }
    }
  }

  void initializeMultiMapdata() {
    maps = new MapData[array.size()];

    for (int i=0; i<array.size(); i++) {
      maps[i] = array.get(i);
    }
    mapNumber = maps.length;
  }

//////////////////////////////////////////////DRAWING////////////////////////////////////////////////

  void draw_background(Graphics2D g2) {
    getPanelSize();

    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,0, sizeX, sizeY);
  }

  public void paintComponent(Graphics g) {
    if (dataPrepared == false || nodeNumber <= 1) {
      return;
    }

    Graphics2D g2 = (Graphics2D)g;

    if (firstTime) {
      initializePosition();
      firstTime = false;
    }

    draw_background(g2);	//Set background

    //i<=mapNumber-1-seaLevel
    for (int i = mapNumber-1 - seaLevel; i>=0; i--) {	//ALL to TOP
      draw_clusters(g2,i);
      draw_links(g2,i);
      draw_stronglink(g2,i);
      draw_nodes(g2,i);
    }

    drawTouchNode(g2);

    for (int i = mapNumber-1 - seaLevel; i>=0; i--) {	//ALL to TOP
      draw_node_names(g2,i);
    }

    g2.setColor(Color.black);
    for (int i=0; i <= mapNumber-1 - seaLevel; i++) {
      if (i == mapNumber-1 - seaLevel) {
        g2.setColor(Color.red);
      }

      draw_clusterName(g2,i);
    }
  }

  void initializePosition() {
    getPanelSize();

    map.createBoundingBox(0);

    double centerX = (map.cl[0].maxx - map.cl[0].minx)/2;
    double centerY = (map.cl[0].maxy - map.cl[0].miny)/2;
    double x = sizeX/2;
    double y = sizeY/2;
    double minPanel;
    double hiritu;

    if (x>y) {
      minPanel = y;
    } else {
      minPanel = x;
    }

    if (centerX > centerY) {
      hiritu = minPanel/centerX;
    } else {
      hiritu = minPanel/centerY;
    }

    centerX += map.cl[0].minx;
    centerY += map.cl[0].miny;

    for (int i=0; i<mapNumber; i++)
      for (int u=0; u<maps[i].clusterNumber; u++) {
        maps[i].changeScale(hiritu,u,centerX,centerY);
      }

    for (int i=0; i<mapNumber; i++)
      for (int u=0; u<maps[i].clusterNumber; u++) {
        for (int k=0; k<maps[i].cl[u].num; k++) {
          maps[i].cl[u].node[k].x += (x - centerX);
          maps[i].cl[u].node[k].y += (y - centerY);
        }
        for (int k=0; k<maps[i].cl[u].outline_num; k++) {
          maps[i].cl[u].shapex[k] += (x - centerX);
          maps[i].cl[u].shapey[k] += (y - centerY);
        }
      }
  }

  void draw_links(Graphics2D g2, int i) {
    g2.setColor(Color.white);

    for (int u=0; u<maps[i].clusterNumber; u++)
      for (int m=0; m<maps[i].cl[u].num; m++)
        for (int n=m+1; n<maps[i].cl[u].num; n++)
          if (maps[i].cl[u].relation[m][n].link) {
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
            g2.drawLine((int)maps[i].cl[u].node[m].x,(int)maps[i].cl[u].node[m].y,(int)maps[i].cl[u].node[n].x,(int)maps[i].cl[u].node[n].y);
          }
  }

  public void draw_stronglink(Graphics2D g2, int i) {
    for (int u=0; u<maps[i].clusterNumber; u++)
      for (int m=0; m<maps[i].cl[u].num; m++)
        for (int n=m+1; n<maps[i].cl[u].num; n++)
          if (maps[i].cl[u].relation[m][n].stronglink) {
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
            g2.drawLine((int)maps[i].cl[u].node[m].x,(int)maps[i].cl[u].node[m].y,(int)maps[i].cl[u].node[n].x,(int)maps[i].cl[u].node[n].y);

            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1.0f,dash,0.0f));
            g2.drawLine((int)maps[i].cl[u].node[m].x,(int)maps[i].cl[u].node[m].y,(int)maps[i].cl[u].node[n].x,(int)maps[i].cl[u].node[n].y);

            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
          }
  }

  void draw_node(Graphics2D g2, double r, double x, double y) {
    g2.fillOval((int)x - (int)r, (int)y - (int)r, (int)r*2, (int)r*2);
  }

  void draw_nodes(Graphics2D g2, int i) {
    for (int u=0; u<maps[i].clusterNumber; u++)
      for (int m=0; m<maps[i].cl[u].num; m++) {
        if (maps[i].cl[u].node[m].include_node > 1) {
          continue;  //can draw cluster here
        }

        g2.setColor(new Color(0xc0,0x90,0x40));
        draw_node(g2, maps[i].cl[u].node[m].radius, maps[i].cl[u].node[m].x, maps[i].cl[u].node[m].y);
      }
  }

  void draw_node_names(Graphics2D g2, int i) {
    for (int u=0; u<maps[i].clusterNumber; u++)
      for (int m=0; m<maps[i].cl[u].num; m++) {
        if (maps[i].cl[u].node[m].include_node > 1) {
          continue;  //can draw cluster here
        }

        if (maps[i].cl[u].node[m].radius > 80 || maps[i].cl[u].node[m].radius < 15) {
          continue;  //NOT DISPLAY NODE NAME
        }

        g2.setColor(Color.black);
        g2.setFont(new Font("Dialog", Font.BOLD, (int)maps[i].cl[u].node[m].radius));

        if (node_name == false) {
          g2.drawString(names[maps[i].cl[u].elements[m]], (int)maps[i].cl[u].node[m].x, (int)maps[i].cl[u].node[m].y);
        } else {
          g2.drawString("SEG"+maps[i].cl[u].elements[m], (int)maps[i].cl[u].node[m].x, (int)maps[i].cl[u].node[m].y);
        }
      }
  }

  //Display node names touched by a mouse
  public void drawTouchNode(Graphics2D g2) {
    if (text.focus.mainFocusSegment < 0) {
      return;
    }

    if (touchMap > mapNumber-1 - seaLevel) {
      return;
    }

    int touchX = (int)maps[touchMap].cl[touchCluster].node[touchNode].x;
    int touchY = (int)maps[touchMap].cl[touchCluster].node[touchNode].y;
    int touchR = (int)maps[touchMap].cl[touchCluster].node[touchNode].radius;

    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.drawOval(touchX - touchR, touchY - touchR, touchR*2, touchR*2);
  }

  //Investigate number of selected node
  public int CatchNode(int mousex, int mousey) {
    for (int i=0; i<maps.length && i<=mapNumber-1-seaLevel; i++)		//ABOVE SEA LEVEL
      for (int u=0; u<maps[i].clusterNumber; u++)
        for (int k=0; k<maps[i].cl[u].num; k++) {
          if (maps[i].cl[u].node[k].include_node > 1) {
            continue;
          }

          if ((mousex > maps[i].cl[u].node[k].x - maps[i].cl[u].node[k].radius && mousex < maps[i].cl[u].node[k].x + maps[i].cl[u].node[k].radius
               //						+ map.node[i].name.length()*(24-2*map.nodeNumber/25.0)
              ) && (mousey > maps[i].cl[u].node[k].y - maps[i].cl[u].node[k].radius && mousey < maps[i].cl[u].node[k].y + maps[i].cl[u].node[k].radius)) {
            touchMap = i;
            touchCluster = u;
            touchNode = k;
            return maps[i].cl[u].elements[k];
          }

        }
    return -1;
  }

  //Investigate number of selected node
  public void searchFocusNode() {
    for (int i=0; i<maps.length; i++)
      for (int u=0; u<maps[i].clusterNumber; u++)
        for (int k=0; k<maps[i].cl[u].num; k++) {
          if (maps[i].cl[u].node[k].include_node > 1) {
            continue;
          }

          if (maps[i].cl[u].elements[k] == touchNumber) {
            touchMap = i;
            touchCluster = u;
            touchNode = k;
            return;
          }
        }
  }

  void draw_cluster(Graphics2D g2, int i, int u, int space) {
    int small,big;
    double dx,dy,kata;
    double au,beta;
    double sheta1,sheta2;
    double sin1,cos1,sin2,cos2;
    int shapeX[] = new int[4];
    int shapeY[] = new int[4];
    double a,b,c,d;

    for (int m=0; m<maps[i].cl[u].num; m++)
      for (int n=m+1; n<maps[i].cl[u].num; n++) {
        small = m;
        big = n;

        if (maps[i].cl[u].node[m].radius > maps[i].cl[u].node[n].radius) {
          small = n;
          big = m;
        }

        dx = maps[i].cl[u].node[big].x - maps[i].cl[u].node[small].x;
        dy = maps[i].cl[u].node[big].y - maps[i].cl[u].node[small].y;
        kata = dy/dx;

        au = (maps[i].cl[u].node[big].radius - maps[i].cl[u].node[small].radius)/Math.sqrt(dx*dx + dy*dy);
        beta = au/Math.sqrt(1 - au*au);

        sheta1 = Math.atan(kata) + Math.atan(beta);
        sheta2 = Math.atan(kata) - Math.atan(beta);

        sin1 = Math.sin(sheta1);
        cos1 = Math.cos(sheta1);
        sin2 = Math.sin(sheta2);
        cos2 = Math.cos(sheta2);

        if (dy > 0) {
          if (dx > 0) {
            a = d = -1;
            b = c = 1;
          } else {
            a = d = 1;
            b = c = -1;
          }
        } else {
          if (dx < 0) {
            a = d = 1;
            b = c = -1;
          } else {
            a = d = -1;
            b = c = 1;
          }
        }

        shapeX[0]=(int)(maps[i].cl[u].node[small].x + a*sin1*(maps[i].cl[u].node[small].radius+space));
        shapeX[1]=(int)(maps[i].cl[u].node[big].x + a*sin1*(maps[i].cl[u].node[big].radius+space));
        shapeX[2]=(int)(maps[i].cl[u].node[big].x + b*sin2*(maps[i].cl[u].node[big].radius+space));
        shapeX[3]=(int)(maps[i].cl[u].node[small].x + b*sin2*(maps[i].cl[u].node[small].radius+space));

        shapeY[0]=(int)(maps[i].cl[u].node[small].y + c*cos1*(maps[i].cl[u].node[small].radius+space));
        shapeY[1]=(int)(maps[i].cl[u].node[big].y + c*cos1*(maps[i].cl[u].node[big].radius+space));
        shapeY[2]=(int)(maps[i].cl[u].node[big].y + d*cos2*(maps[i].cl[u].node[big].radius+space));
        shapeY[3]=(int)(maps[i].cl[u].node[small].y + d*cos2*(maps[i].cl[u].node[small].radius+space));

        g2.fillOval((int)maps[i].cl[u].node[big].x - (int)maps[i].cl[u].node[big].radius - space,
                    (int)maps[i].cl[u].node[big].y - (int)maps[i].cl[u].node[big].radius - space,
                    (int)maps[i].cl[u].node[big].radius*2 + 2*space,
                    (int)maps[i].cl[u].node[big].radius*2 + 2*space);
        g2.fillOval((int)maps[i].cl[u].node[small].x - (int)maps[i].cl[u].node[small].radius - space,
                    (int)maps[i].cl[u].node[small].y - (int)maps[i].cl[u].node[small].radius - space,
                    (int)maps[i].cl[u].node[small].radius*2 + 2*space,
                    (int)maps[i].cl[u].node[small].radius*2 + 2*space);
        g2.fillPolygon(shapeX, shapeY, 4);
      }
  }

  void draw_clusters(Graphics2D g2, int i) {
    for (int u=0; u<maps[i].clusterNumber; u++) {
      maps[i].createBoundingBox(u);

      g2.setColor(Color.white);
      draw_cluster(g2,i,u,7);

      g2.setColor(Color.green);
      draw_cluster(g2,i,u,4);

      if (maps[i].cl[u].num > 2) {
        int x[] = new int[maps[i].cl[u].outline_num];
        int y[] = new int[maps[i].cl[u].outline_num];

        for (int m=0; m<maps[i].cl[u].outline_num; m++) {
          x[m] = (int)maps[i].cl[u].shapex[m];
          y[m] = (int)maps[i].cl[u].shapey[m];
        }
        g2.fillPolygon(x, y, maps[i].cl[u].outline_num);
      }
    }
  }

  void draw_clusterName(Graphics2D g2, int i) {
    double centerX,centerY;

    for (int u=0; u<maps[i].clusterNumber; u++) {
      centerX = (maps[i].cl[u].maxx - maps[i].cl[u].minx)/2;
      centerY = (maps[i].cl[u].maxy - maps[i].cl[u].miny)/2;

      int fSize = (int)centerY/2;

      if (centerX > centerY) {
        fSize = (int)centerX/2;
      }

      if (fSize > 80 || fSize < 20) {
        continue;  //NOT DISPLAY CLUSTER NAME
      }

      centerX += maps[i].cl[u].minx;
      centerY += maps[i].cl[u].miny;

      g2.setFont(new Font("Dialog",Font.BOLD,fSize));
      g2.drawString(maps[i].cl[u].ClusterName,(int)centerX,(int)centerY);
    }
  }

///

////////////////////////MOUSE EVENT/////////////////////////////////33

  //MouseListener
  public void mousePressed(MouseEvent me) {	//Press
    mousex = me.getX();
    mousey = me.getY();

    old_mousex = mousex;
    old_mousey = mousey;
    dragging = true;
  }

  public void mouseReleased(MouseEvent me) {	//Release
    if (dragging == true) {
      dragging = false;
    }
  }

  public void mouseEntered(MouseEvent me) {}	//Enter an area
  public void mouseExited(MouseEvent me) {}	//Exit an area

  public void mouseClicked(MouseEvent me) {	//Click
    if (dataPrepared == false) {
      return;
    }

    int btn = me.getButton();

    if (btn == MouseEvent.BUTTON1) //left
      if (seaLevel > 0) {
        seaLevel--;
      }

    if (btn == MouseEvent.BUTTON2) { //center //name_mode change
      node_name = !node_name;
    }

    if (btn == MouseEvent.BUTTON3)//right
      if (seaLevel < mapNumber-1) {
        seaLevel++;
      }

    repaint();
  }

  //MouseMotionListener
  public void mouseDragged(MouseEvent me) {	//Dragging
    if (dataPrepared == false) {
      return;
    }

    if (dragging == true) {	//Move Field
      mousex = me.getX();
      mousey = me.getY();
      dx = old_mousex - mousex;
      dy = old_mousey - mousey;
      old_mousex = mousex;
      old_mousey = mousey;

      for (int i =0; i<map.cl[0].num; i++) {
        map.cl[0].node[i].x -= dx;
        map.cl[0].node[i].y -= dy;
      }

      for (int r=0; r<mapNumber; r++)
        for (int i=0; i<maps[r].clusterNumber; i++) {
          for (int k=0; k<maps[r].cl[i].num; k++) {
            maps[r].cl[i].node[k].x -= dx;
            maps[r].cl[i].node[k].y -= dy;
          }
          for (int k=0; k<maps[r].cl[i].outline_num; k++) {
            maps[r].cl[i].shapex[k] -= dx;
            maps[r].cl[i].shapey[k] -= dy;
          }
          maps[r].cl[i].minx -= dx;
          maps[r].cl[i].maxx -= dx;
          maps[r].cl[i].miny -= dy;
          maps[r].cl[i].maxy -= dy;
        }
      repaint();
    }
  }

  public void mouseMoved(MouseEvent me) {
    if (dataPrepared == false) {
      return;
    }

    mousex = me.getX();
    mousey = me.getY();

    touchNumber = CatchNode(mousex,mousey);

    if (touchNumber >= 0) {
      text.focus.mainFocusSegment = touchNumber;

      if (touchNumber != oldTouchNumber) {
        repaintOthersByTouch();
        oldTouchNumber = touchNumber;
      }
    }
    repaint();
  }



  //MouseWheelListener
  public void mouseWheelMoved(MouseWheelEvent mwe) {
    if (dataPrepared == false) {
      return;
    }

    double mouse_x = mwe.getX();
    double mouse_y = mwe.getY();

    if (mwe.getWheelRotation() > 0) {
      for (int i=0; i<mapNumber; i++)
        for (int u=0; u<maps[i].clusterNumber; u++) {
          maps[i].changeScale(0.9,u,mouse_x,mouse_y);
        }
    } else {
      for (int i=0; i<mapNumber; i++)
        for (int u=0; u<maps[i].clusterNumber; u++) {
          maps[i].changeScale(1.1,u,mouse_x,mouse_y);
        }
    }

    repaint();
  }


///////////////////////////////////////////////////////////////////////////////33

////////////////////////////////////INTERNAL PROCESS///////////////////////////////////////4


  //Set initial position of each node
  void initializeNodePosition(int cl_num) {
    int nodex=0;
    int nodey=0;

    for (int i=0; i<map.cl[cl_num].num; i++) {
      map.cl[cl_num].node[i].x = nodex;
      map.cl[cl_num].node[i].y = nodey;

      nodex+=30;
      nodey+=35;
    }
  }

  //SPRING MODEL
  void setting_nodes() {
    for (int i=0; i<map.clusterNumber; i++)
      for (int k=0; k<200; k++) {
        calc_idist(i);
        springstep(bane_k,minus,i);
      }
  }
  //SPRING MODEL
  void calc_idist(int cl_num) {
    for (int i=0; i<map.cl[cl_num].num; i++)
      for (int j=0; j<map.cl[cl_num].num; j++) {
        if (map.cl[cl_num].relation[i][j].link == true) { //Distances to nodes which have link(s)
          map.cl[cl_num].relation[i][j].idist = 50+map.cl[cl_num].node[i].radius+map.cl[cl_num].node[j].radius;
        } else {
          map.cl[cl_num].relation[i][j].idist = 200+map.cl[cl_num].node[i].radius+map.cl[cl_num].node[j].radius;
        }

        if (map.cl[cl_num].relation[i][j].idist > 100+map.cl[cl_num].node[i].radius+map.cl[cl_num].node[j].radius) {
          map.cl[cl_num].relation[i][j].idist = 150+map.cl[cl_num].node[i].radius+map.cl[cl_num].node[j].radius;
        }
      }
  }
  //SPRING MODEL
  void springstep(double bane_k, double minus, int cl_num) {	//Calculate power effect on each node
    double tx,ty,dx,dy,tdx,tdy;
    int i,j;
    double power_limit;

    power_limit = 50;

    for (i=0; i<map.cl[cl_num].num; i++) {
      dx = dy = 0;
      for (j=0; j<map.cl[cl_num].num; j++) {
        if (i != j) {
          tx = map.cl[cl_num].node[i].x-map.cl[cl_num].node[j].x;
          ty = map.cl[cl_num].node[i].y-map.cl[cl_num].node[j].y;
          map.cl[cl_num].relation[i][j].rdist = Math.sqrt(Math.pow(tx,2)+Math.pow(ty,2));

          if (map.cl[cl_num].relation[i][j].rdist ==0) {
            continue;
          }

          tdx = tx*bane_k*(1.0 - map.cl[cl_num].relation[i][j].idist/map.cl[cl_num].relation[i][j].rdist);
          tdy = ty*bane_k*(1.0 - map.cl[cl_num].relation[i][j].idist/map.cl[cl_num].relation[i][j].rdist);

          if (tdx > power_limit) {
            tdx = power_limit;
          }
          if (tdx < -power_limit) {
            tdx = -power_limit;
          }
          if (tdy > power_limit) {
            tdy = power_limit;
          }
          if (tdy < -power_limit) {
            tdy = -power_limit;
          }

          if (map.cl[cl_num].relation[i][j].link == true) { //Gravity
            //Power to X direction and to Y direction
            dx += tdx;
            dy += tdy;
          } else {	//Repulsion
            dx += minus*tdx;
            dy += minus*tdy;
          }
        }
      }

      map.cl[cl_num].node[i].x -= dx;
      map.cl[cl_num].node[i].y -= dy;

      for (j=0; j<map.cl[cl_num].num; j++) {
        tx = map.cl[cl_num].node[i].x-map.cl[cl_num].node[j].x;
        ty = map.cl[cl_num].node[i].y-map.cl[cl_num].node[j].y;
        double doi= Math.sqrt(Math.pow(tx,2)+Math.pow(ty,2));

        if (doi < map.cl[cl_num].node[i].radius + map.cl[cl_num].node[j].radius + 50) {
          map.cl[cl_num].relation[i][j].idist += 50;
        }
      }
    }
  }

  void create_ground_data() {
    int cl = 0;
    for (int i=0; i<nodeNumber; i++) //count cluster number
      if (nodeSize[i]>1) {
        cl++;
      }

    map = new MapData(1);//World Map

    map.cl[0].initializeCluster(cl);//Cluster

    for (int i=0; i<cl; i++)
      for (int k=0; k<cl; k++) {
        map.cl[0].relation[i][k].link = map.cl[0].relation[i][k].stronglink = false;
      }

    cl = 0;
    for (int i=0; i<nodeNumber; i++) //count cluster number
      if (nodeSize[i]>1) {
        map.cl[0].elements[cl]=i;
        map.cl[0].node[cl].id = i;
        map.cl[0].node[cl].include_node = nodeSize[i];
        map.cl[0].node[cl].radius = nodeSize[i] * 12;
        cl++;
      }

    if (cl>1)
      for (int k=0; k<100; k++) {
        calc_idist(0);
        springstep(bane_k,minus,0);
      }
  }

  void set_groundCluster_position() {
    boolean flag;
    double hankei;//half size of the cluster
    double x,y, hiritu;

    for (int num=0; num<map.cl[0].num; num++) {
      flag = false;
      for (int i = mapNumber-1; i >= 0 && flag == false; i--)
        for (int u=0; u < maps[i].clusterNumber; u++)
          if (map.cl[0].elements[num] == maps[i].cl[u].elements[0]) {
            maps[i].cl[u].groundCluster=true;
            flag = true;

            maps[i].createBoundingBox(u);

            x = maps[i].cl[u].maxx - maps[i].cl[u].minx;
            y = maps[i].cl[u].maxy - maps[i].cl[u].miny;

            if (x > y) {
              hankei = x/2;
            } else {
              hankei = y/2;
            }

            hiritu = map.cl[0].node[num].radius/(hankei*Math.sqrt(2));
            move_cluster(map.cl[0].node[num].x, map.cl[0].node[num].y, hiritu, i, u);

            break;
          }
    }
  }

  void set_node_position() {
    boolean flag;
    double hankei;//half size of the cluster
    double x,y, hiritu;

    create_ground_data();//create Mapdata map

    set_groundCluster_position();//matching maps and map

    for (int i = mapNumber-1; i >= 0; i--) { //maps[mapNumber-1] is the bottom
      for (int u=0; u < maps[i].clusterNumber; u++)
        if (maps[i].cl[u].groundCluster == false) { // Except ground level: alread constructed
          flag = false;
          for (int r = i+1; r < mapNumber && flag == false; r++) // Searching upper cluster including myself
            for (int h=0; h<maps[r].clusterNumber && flag == false; h++)
              for (int num=0; num<maps[r].cl[h].num; num++)
                if (maps[r].cl[h].elements[num] == maps[i].cl[u].elements[0]) {
                  maps[i].cl[u].groundCluster = true;
                  flag = true;

                  maps[i].createBoundingBox(u);

                  x = maps[i].cl[u].maxx - maps[i].cl[u].minx;
                  y = maps[i].cl[u].maxy - maps[i].cl[u].miny;

                  if (x > y) {
                    hankei = x/2;
                  } else {
                    hankei = y/2;
                  }

                  hiritu = maps[r].cl[h].node[num].radius/(hankei*Math.sqrt(2));
                  move_cluster(maps[r].cl[h].node[num].x, maps[r].cl[h].node[num].y, hiritu, i, u);

                  break;
                }
        }
      make_outline(i);
    }
  }

  void move_cluster(double x, double y, double hiritu, int i, int u) {
    double centerX,centerY;
    double dx,dy;

    centerX = (maps[i].cl[u].maxx - maps[i].cl[u].minx)/2 + maps[i].cl[u].minx;
    centerY = (maps[i].cl[u].maxy - maps[i].cl[u].miny)/2 + maps[i].cl[u].miny;

    maps[i].changeScale(hiritu,u,centerX,centerY);

    dx = x - centerX;
    dy = y - centerY;

    for (int m=0; m<maps[i].cl[u].num; m++) {
      maps[i].cl[u].node[m].x += dx;
      maps[i].cl[u].node[m].y += dy;
    }
  }

  void make_outline(int i) {
    double miny;
    int init, now, nextc, next = 0;
    double pre_angle, angle = 2*Math.PI, min_angle;
    int shape[];

    for (int u=0; u<maps[i].clusterNumber; u++) {
      shape = new int[maps[i].cl[u].num + 1];
      maps[i].cl[u].outline_num = 0;

      //Start point is the node whose y coordinate is the minimum
      init = 0;
      miny = maps[i].cl[u].node[0].y;
      for (int m=1; m<maps[i].cl[u].num; m++)
        if (maps[i].cl[u].node[m].y < miny) {
          miny = maps[i].cl[u].node[m].y;
          init = m;
        }

      shape[maps[i].cl[u].outline_num++] = init;
      pre_angle = 0;
      now = init;

      //Seek a next node until the outlike makes a loop
      do {
        min_angle = 2*Math.PI;
        for (int m=0; m<maps[i].cl[u].num; m++) {
          nextc = m;

          if (nextc == now) {
            continue;
          }

          angle = calc_angle((int)(maps[i].cl[u].node[nextc].x - maps[i].cl[u].node[now].x),
                             (int)(maps[i].cl[u].node[nextc].y - maps[i].cl[u].node[now].y));

          if (angle >= pre_angle)
            if (angle < min_angle) {
              min_angle = angle;
              next = nextc;
            }
        }

        shape[maps[i].cl[u].outline_num++] = next;
        pre_angle = min_angle;
        now = next;
      } while (now != init && maps[i].cl[u].outline_num <= maps[i].cl[u].num);

      maps[i].cl[u].shapex = new double[maps[i].cl[u].outline_num];
      maps[i].cl[u].shapey = new double[maps[i].cl[u].outline_num];

      for (int n=0; n<maps[i].cl[u].outline_num; n++) {
        maps[i].cl[u].shapex[n] = maps[i].cl[u].node[shape[n]].x;
        maps[i].cl[u].shapey[n] = maps[i].cl[u].node[shape[n]].y;
      }
    }
  }
  //Calculate angle for a vector (x,y)
  double calc_angle(double x, double y) {
    double cos,theta;
    int rev;

    if (x==0 && y==0) { //Calculation impossible when Zero vector
      return 0;
    }

    rev = 0;
    if (y<0) {
      x *= -1;
      y *= -1;
      rev = 1;
    }

    cos = (double)x / Math.sqrt(x*x + y*y);
    theta = Math.acos(cos) + Math.PI*rev;	//Calculate an angle theta, if theta is more than pi, add pi to theta

    return theta;
  }
}