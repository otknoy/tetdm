////////////////////////////////
// Visualization Module for TETDM
// Simple Keyword Map Version 0.40 2012.1.31
// Copyright(C) 2012.1.31 Tomoki Kajinami
// *IMPORTANT*
// This program is provided under a KM licence in addition to a TETDM license.
// You should read README file.
////////////////////////////////

package module.VisualizationModules.SimpleKeywordMap;

import source.*;
import source.TextData.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.awt.geom.*;

public final class SimpleKeywordMap extends VisualizationModule implements Runnable {
  private int sizex,sizey;
  private String kw1,kw2;
  private Thread act;
  private Data data;
  private boolean readdataflg;
  private int c;
  private int movecount;
  private double kan;
  private RelationPanel rpanel;
  private JPanel rwindow;
  private JPanel panel;
  private String output_text;
  private FontMetrics fms;
  private boolean readfirstflg;
  private double threshold,threshold2;
  private int redLink,blueLink;
  private boolean mouseEntered1,mouseEntered2,arrangementStopFlg;

  public SimpleKeywordMap() {
    setModuleID(7);
    dataNumbers = new int[] {0,0,0,1,   // b,i,d,S
                             0,0,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
  }

  public void initializePanel() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    arrangementStopFlg=true; // customer-changeable
    threshold=0.5; //relationship border //Double// customer-changeable
    threshold2=0.8; //relationship border (awareness function) //Double// customer-changeable
    redLink=4; //strong relationship (awareness function) //IMPORTANT // int // customer-changeable
    blueLink=4; //week relationship (awareness function) //IMPORTANT// int // customer-changeable
    ////////////////////////////////////////////////////////////////////////////////////////////////

    setLayout(new BorderLayout());
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    c=128;
    rpanel=new RelationPanel();
    rwindow=new JPanel();
    panel=new KeywordPanel();
    JLabel jl=new JLabel("Relevance balance controller");
    rwindow.setLayout(new BoxLayout(rwindow,BoxLayout.Y_AXIS));
    rwindow.add(jl);
    rwindow.add(rpanel);
    this.add(rwindow,"South");
    this.add(panel,"Center");
    rpanel.setPreferredSize(new Dimension(0,80));
    if (!readdataflg) {
      data=new Data();
      readdataflg=true;
    }
    startArrangement();
    readfirstflg=true;
  }

  private void startArrangement() {
    if (act==null) {
      act=new Thread(this);
      act.start();
    }
  }

  private void checkMouseEntered() {
    if (arrangementStopFlg && (mouseEntered1==true || mouseEntered2==true)) {
      startArrangement();
    } else {
      stopArrangement();
    }
  }

  private void stopArrangement() {
    if (act!=null) {
      act=null;
    }
  }

  public void initializeData() {
    output_text = ",,cos,random,times"+System.getProperty("line.separator")+",,0.0,0.0,0.0"+System.getProperty("line.separator");
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        break;
    }
  }

  public boolean setData(int dataID, String output_text) {
    switch (dataID) {
      case 99:	//check a data
        this.output_text=output_text;
        return true;
    }
    return false;
  }

  //About Relevance balance controller -begin-
  private final class RelationPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private int w,h;
    private int fontSize;
    private FontMetrics fm;
    private double left,right,top,bottom,gauge,contblock,inforR;
    private double allhiri;
    private String[] r;
    private boolean pushboard;
    private int mousex,mousey;

    private RelationPanel() {
      super();
      this.setBackground(Color.gray);
      this.setDoubleBuffered(true);
      this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      this.addMouseListener(this);
      fontSize=10;
      this.addMouseWheelListener(this);
    }

    //Display the relevance balance gauge and control boards
    public void paint(Graphics g) {
      super.paint(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension d=getSize();
      w=d.width;
      h=d.height;
      left=10;
      right=w-10;
      top=10;
      bottom=h-50;
      gauge=right-left;
      contblock=(w-left)/data.rsuu;
      inforR=3;
      g2.drawRect((int)left,(int)top,(int)right-10,(int)bottom-10);
      g2.drawRect((int)left+1,(int)top+1,(int)right-12,(int)bottom-52);
      g2.drawRect((int)left+2,(int)top+2,(int)right-13,(int)bottom-53);
      g2.drawRect((int)left+3,(int)top+3,(int)right-14,(int)bottom-54);
      if (data.rsuu>0) {
        allhiri=0;
        for (int i=0; i<data.rsuu; i++) {
          allhiri+=data.balance[i];
        }
        for (int i=0; i<data.rsuu; i++) {
          data.hiritu[i]=data.balance[i]/allhiri;
          data.block[i]=gauge*data.hiritu[i];
        }
      }
      setFont(g);
      setController(g);
      setLine(g);
      drawR(g);
      Point mousexy=this.getMousePosition();
      if (mousexy!=null) {
        mousex=mousexy.x;
        mousey=mousexy.y;
      }
    }
    //Create the control boards
    private void setController(Graphics g) {
      Graphics2D g2=(Graphics2D)g;
      for (int i=0; i<data.rsuu; i++) {
        g2.setColor(Color.black);
        g2.drawRect((int)left+(int)contblock*i,(int)top+30, (int)(contblock-10),30);
        g2.setPaint(new GradientPaint((float)left+(float)contblock*i-54,0.0f,data.color1[i],(float)contblock*(i+1)+50,0.0f,data.color2[i], false));
        g2.fill(new Rectangle2D.Double((int)left+1+(int)contblock*i,(int)top+31,(int)(contblock-12),29));
      }
    }
    //Create the balance gauge
    private void setLine(Graphics g) {
      Graphics2D g2=(Graphics2D)g;
      double sepa=gauge/data.rsuu;
      int blockcount=0;
      for (int i=0; i<data.rsuu; i++) {
        g2.setPaint(new GradientPaint((float)left+(float)blockcount,0.0f,data.color1[i],(float)left+(float)blockcount+(float)data.block[i],0.0f,data.color2[i], false));
        g2.fill(new Rectangle2D.Double((int)left+blockcount+1,top+1,data.block[i],19));
        g2.setColor(Color.black);
        g2.drawLine((int)left+blockcount+(int)data.block[i],(int)top,(int)left+blockcount+(int)data.block[i],(int)bottom);
        g2.drawLine((int)left+blockcount+(int)data.block[i],(int)top,(int)left+blockcount+(int)data.block[i],(int)bottom);
        blockcount+=(int)data.block[i];
      }
    }

    //Font setting
    private void setFont(Graphics g) {
      Font font=new Font("Dialog",Font.PLAIN,fontSize);
      g.setFont(font);
      fm=getFontMetrics(font);
    }

    //Display attributes'name on the control boards
    private void drawR(Graphics g) {
      Graphics2D g2=(Graphics2D)g;
      Font font=new Font("SanSerif",Font.PLAIN,10);
      g2.setFont(font);
      g2.setColor(Color.white);
      r=new String[data.rsuu];
      for (int i=0; i<data.rsuu; i++) {
        r[i]=data.rname[i+2];
        g2.drawString(r[i],(int)left+(int)inforR+(int)contblock*i,55);
      }
    }

    //Mouse event
    public void mousePressed(MouseEvent e) {
      int x=e.getX();
      int y=e.getY();
      if (e.getModifiers()==InputEvent.BUTTON1_MASK) {
        for (int i=0; i<data.rsuu; i++) {
          if ((top+30<y)&&(y<top+60)&&((left+contblock*i)<x)&&(x<(left+contblock*(i+1)-10))) {
            for (int j=0; j<data.rsuu; j++) {
              data.balancetemp[j]=1;
              data.balance[j]=0;
            }
            data.balance[i]=1;
            pushboard=true;
          } else if (!pushboard) {
            for (int j=0; j<data.rsuu; j++) {
              data.balance[j]=1;
            }
          }
        }
        pushboard=false;
        data.setLij();
        movecount=0;
      } else if (data.rsuu>=2 && e.getModifiers()==InputEvent.BUTTON3_MASK) { //If right clicking
        int positiveflg=0;
        for (int i=0; i<data.rsuu; i++) {
          if (data.balance[i]>0) {
            positiveflg++;
          }
        }
        if (positiveflg==1) {
          for (int i=0; i<data.rsuu; i++) {
            data.balance[i]=1;
          }
        }
        for (int i=0; i<data.rsuu; i++) {
          if ((top+30<y)&&(y<top+60)&&((left+contblock*i)<x)&&(x<(left+contblock*(i+1)-10))) {
            data.balance[i]=0;
            pushboard=true;
          }
        }
        pushboard=false;
        data.setLij();
        movecount=0;
      }
      repaint();
    }

    //Change the ratio of relevance by rotating mouse wheel
    public void mouseWheelMoved(MouseWheelEvent e) {
      for (int i=0; i<data.rsuu; i++) {
        if ((top+30<e.getY())&&(e.getY()<top+60)&&((left+contblock*i)<e.getX())&&(e.getX()<(left+contblock*(i+1)-10))) {
          double rot=e.getWheelRotation();
          if (rot>0) {
            data.balance[i]*=(1+rot/10);
          }
          if (rot<0) {
            data.balance[i]/=(1+Math.abs(rot/10));
          }
          data.setLij();
          movecount=0;
        }
      }
      repaint();
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
      mouseEntered2=true;
      checkMouseEntered();
    }
    public void mouseExited(MouseEvent e) {
      mouseEntered2=false;
      checkMouseEntered();
    }
    public void mouseMoved(MouseEvent e) {}
  }
  //About the relevance balance gauge and controller -end-

  //Dinamic graph visualization
  public void run() {
    while (act!=null) {
      springModel();
      update();
      if (movecount>=20) {
        around();
      }
      repaint();
      movecount++;
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  //Spring model
  private void springModel() {
    for (int i=0; i<data.s; i++) {
      Spring sp=data.spring[i];
      Keyword from=data.keyword[sp.from];
      Keyword to=data.keyword[sp.to];
      if (from.del || to.del) {
        continue;
      }
      double xij=from.x-to.x;
      double yij=from.y-to.y;
      sp.dij=Math.sqrt(xij*xij+yij*yij);
      sp.dij=sp.dij==0?0.000001:sp.dij;
      double E=(sp.dij-sp.lij)*(sp.dij-sp.lij);
      if (from.k>1) {
        from.E+=to.k*E;
      }
      if (to.k>1) {
        to.E+=from.k*E;
      }
      if ((from.set2 || to.set2) && sp.rij>=data.borderR) {
        //Keywords concentration
        double dx1=((sp.lij*xij)/sp.dij-xij)/c;
        double dy1=((sp.lij*yij)/sp.dij-yij)/c;
        double dx2=(from.concent+to.concent)*(sp.dij)*xij/(100*c);
        double dy2=(from.concent+to.concent)*(sp.dij)*yij/(100*c);
        from.dx+=(to.concent)*to.k*(dx1-dx2);
        from.dy+=(to.concent)*to.k*(dy1-dy2);
        to.dx-=(from.concent)*from.k*(dx1-dx2);
        to.dy-=(from.concent)*from.k*(dy1-dy2);
      } else {
        //Normal arrengement
        double dx=((sp.lij*xij)/sp.dij-xij)/c;
        double dy=((sp.lij*yij)/sp.dij-yij)/c;
        from.dx+=to.k*dx;
        from.dy+=to.k*dy;
        to.dx-=from.k*dx;
        to.dy-=from.k*dy;
      }
    }
  }

  private synchronized void update() {
    for (int i=0; i<data.k; i++) {
      Keyword kw=data.keyword[i];
      if (movecount<20) {
        kw.sindoucountX=0;
        kw.sindoucountY=0;
      }
      if (kw.set|| kw.set2 || (kw.concent>0) || kw.sindoucountX<-20 || kw.sindoucountY<-20 || kw.mouseset) {
        kw.dx=0;
        kw.dy=0;
      } else {
        kw.dx=Math.max(-3,Math.min(kw.dx,3));
        kw.dy=Math.max(-3,Math.min(kw.dy,3));
        if (kw.sindouX) {
          if (kw.dx<0) {
            if (Math.abs(kw.dy)>2) {
              kw.sindoucountX--;
            } else if (Math.abs(kw.dy)>1) {
              kw.sindoucountX=kw.sindoucountX-2;
            } else if (Math.abs(kw.dy)>0.5) {
              kw.sindoucountX=kw.sindoucountX-3;
            } else if (Math.abs(kw.dy)>0) {
              kw.sindoucountX=kw.sindoucountX-4;
            } else if (Math.abs(kw.dy)==0) {
              kw.sindoucountX=kw.sindoucountX-10;
            }
            kw.sindouX=false;
          } else {
            kw.sindoucountX++;
            if (kw.sindoucountX>0) {
              kw.sindoucountX=0;
            }
          }
        }
        if (kw.dx>0) {
          kw.sindouX=true;
        }
        if (kw.sindouY) {
          if (kw.dy<0) {
            if (Math.abs(kw.dx)>2) {
              kw.sindoucountY--;
            } else if (Math.abs(kw.dx)>1) {
              kw.sindoucountY=kw.sindoucountY-2;
            } else if (Math.abs(kw.dx)>0) {
              kw.sindoucountY=kw.sindoucountY-3;
            } else if (Math.abs(kw.dx)>0) {
              kw.sindoucountY=kw.sindoucountY-4;
            } else if (Math.abs(kw.dx)==0) {
              kw.sindoucountY=kw.sindoucountY-10;
            }
            kw.sindouY=false;
          } else {
            kw.sindoucountY++;
            if (kw.sindoucountY>0) {
              kw.sindoucountY=0;
            }
          }
        }
        if (kw.dy>0) {
          kw.sindouY=true;
        }
      }
      kw.move+=Math.sqrt(kw.dx*kw.dx+kw.dy*kw.dy);
      kw.x+=kw.dx;
      kw.y+=kw.dy;
      kw.dx=0;
      kw.dy=0;
      if (kw.x<10) {
        kw.x=10;
      } else if (sizex!=0 && kw.x>sizex-10) {
        kw.x=sizex-10;
      }
      if (kw.y<10) {
        kw.y=10;
      } else if (sizey!=0 && kw.y>sizey-10) {
        kw.y=sizey-10;
      }
    }
  }

  //Avoid overlapping
  private synchronized void around() {
    FontMetrics fm=getFontMetrics(new Font("Dialog",Font.PLAIN,10));
    for (int i=0; i<data.s; i++) {
      Spring sp=data.spring[i];
      Keyword from=data.keyword[sp.from];
      Keyword to=data.keyword[sp.to];
      if (from.del || to.del) {
        continue;
      }
      if (movecount==0) {
        from.aroundcount=0;
        to.aroundcount=0;
      }
      double wi=fm.stringWidth(from.lbl)/2+5;
      double wj=fm.stringWidth(to.lbl)/2+5;
      double wij=wi+wj+kan;
      double hij=fm.getHeight()+2+kan;
      double xij=Math.abs(from.x-to.x);
      double yij=Math.abs(from.y-to.y);
      if (wij>xij && hij>yij) {
        //Update of coordinates
        if (wij-xij<hij) {
          double dx=(wij-xij)/2;
          if (from.x>to.x) {
            if (!from.set) {
              from.x+=dx;
            }
            if (!to.set) {
              to.x-=dx;
            }
          } else {
            if (!from.set) {
              from.x-=dx;
            }
            if (!to.set) {
              to.x+=dx;
            }
          }
        } else {
          double dy=(hij-yij)/2;
          if (from.y>to.y) {
            if (!from.set) {
              from.y+=dy;
            }
            if (!to.set) {
              to.y-=dy;
            }
          } else {
            if (!from.set) {
              from.y-=dy;
            }
            if (!to.set) {
              to.y+=dy;
            }
          }
        }
        from.aroundcount--;
        to.aroundcount--;
      }
    }
  }


  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(Color.white);
    g2.fillRect(0,0, sizex, sizey);
  }

  //About a drawing area for keywords -begin-
  private final class KeywordPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private Keyword near;
    private int w,h;
    private int fontSize;
    private FontMetrics fm;
    private boolean keyword;
    private boolean kanrensu;
    private String savelbl="null";
    private boolean mousesetflg;
    private String countoldlbl="null";
    private int mousex,mousey;
    private int stronganycount;
    private int linkdistance1;
    private int weakanycount;

    private KeywordPanel() {
      super();
      this.setBackground(Color.white);
      this.setDoubleBuffered(true);
      this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      this.addMouseListener(this);
      keyword=true;
      fontSize=10;
      this.addMouseWheelListener(this);
    }

    //Display keywords
    public void paint(Graphics g) {
      super.paint(g);
      Dimension d=getSize();
      w=d.width;//-40;
      h=d.height;//-25;
      sizex=w;
      sizey=h;
      setFont(g);
      if (readfirstflg) {
        data.readOutputtext(output_text);
        readfirstflg=false;
      }
      Point mousexy=this.getMousePosition();
      if (mousexy!=null) {
        mousex=mousexy.x;
        mousey=mousexy.y;
      }
      for (int i=0; i<data.s; i++) {
        Keyword from=data.keyword[data.spring[i].from];
        Keyword to=data.keyword[data.spring[i].to];
        double rij=data.spring[i].rij;
        from.stopstrong=false;
        to.stopstrong=false;
        from.stopweak=false;
        to.stopweak=false;
        if (from.del || to.del || from.dummy || to.dummy) {
          continue;
        }
        String lbl;
        if (from.focus || from.focus2 || from.focus3 || from.focus4 || from.focus5 || from.focus6 || from.focus7) {
          lbl=from.lbl.substring(0,from.lbl.length()-1);
        } else {
          lbl=from.lbl;
        }
        if (to.focus || to.focus2 || to.focus3 || to.focus4 || to.focus5 || to.focus6 || to.focus7) {
          lbl=to.lbl.substring(0,to.lbl.length()-1);
        } else {
          lbl=to.lbl;
        }
        int sw=fm.stringWidth(lbl)/2+5;
        int sh=fm.getHeight()/2+2;
        if (rij<data.borderR) {
          continue;
        }
        if ((from.x-sw)<mousex && mousex<(from.x+sw) && (from.y-sh)<mousey && mousey<(from.y+sh)
            || (to.x-sw)<mousex && mousex<(to.x+sw) && (to.y-sh)<mousey && mousey<(to.y+sh)) {
          mousesetflg=true;
        }
      }
      if (mousesetflg) {
        stronganycount=0;
        weakanycount=0;
        for (int i=0; i<data.s; i++) {
          Keyword from=data.keyword[data.spring[i].from];
          Keyword to=data.keyword[data.spring[i].to];
          double rij=data.spring[i].rij;
          if (from.del || to.del || from.dummy || to.dummy) {
            continue;
          }
          String lbl;
          if (from.focus || from.focus2 || from.focus3 || from.focus4 || from.focus5 || from.focus6 || from.focus7) {
            lbl=from.lbl.substring(0,from.lbl.length()-1);
          } else {
            lbl=from.lbl;
          }
          if (to.focus || to.focus2 || to.focus3 || to.focus4 || to.focus5 || to.focus6 || to.focus7) {
            lbl=to.lbl.substring(0,to.lbl.length()-1);
          } else {
            lbl=to.lbl;
          }
          int sw=fm.stringWidth(lbl)/2+5;
          int sh=fm.getHeight()/2+2;
          if ((from.x-sw)<mousex && mousex<(from.x+sw) && (from.y-sh)<mousey && mousey<(from.y+sh)
              || (to.x-sw)<mousex && mousex<(to.x+sw) && (to.y-sh)<mousey && mousey<(to.y+sh)) {
            if (rij>=data.borderR) {
              int p[][]=new int[2][2];
              p[0][0]=(int)from.x;
              p[0][1]=(int)from.y;
              p[1][0]=(int)to.x;
              p[1][1]=(int)to.y;
              drawSpring(g,rij,p);
            }
            if ((from.x-sw)<mousex && mousex<(from.x+sw) && (from.y-sh)<mousey && mousey<(from.y+sh) && rij>=data.borderR) {
              from.mouseset=true;
              savelbl=from.lbl;
              if (!to.weaksearchflg) {
                linkdistance1++;
              }
              to.weaksearchflg=true;
            }
            if ((to.x-sw)<mousex && mousex<(to.x+sw) && (to.y-sh)<mousey && mousey<(to.y+sh) && rij>=data.borderR) {
              to.mouseset=true;
              savelbl=to.lbl;
              if (!from.weaksearchflg) {
                linkdistance1++;
              }
              from.weaksearchflg=true;
            }
          } else {
            from.mouseset=false;
            to.mouseset=false;
            if (!from.mouseset && from.lbl.equals(savelbl)) {
              to.weaksearchflg=false;
            }
            if (!to.mouseset && to.lbl.equals(savelbl)) {
              from.weaksearchflg=false;
            }
            if (((from.weaksearchflg && !to.weaksearchflg) || (to.weaksearchflg && !from.weaksearchflg))) {
              Color colorlink=null;
              Color colorlabel=null;
              int rhighcount=0;
              int overbordercount=0;
              String rlabel="";
              for (int j=0; j<data.rsuu; j++) {
                if (data.spring[i].r[j]>=threshold2 && rij<data.borderR) {
                  rhighcount++;
                  rlabel=rlabel+" "+data.rname[j+2];
                }
                if (data.spring[i].r[j]>0 && rij<data.borderR) {
                  overbordercount++;
                }
              }
              if (rhighcount>=1 && stronganycount<redLink && (!from.stopstrong && !to.stopstrong)) {
                colorlink=Color.pink;
                if (from.weaksearchflg && !to.weaksearchflg) {
                  to.strongany=true;
                  to.weakkw=true;
                  stronganycount++;
                  from.link1seed=true;
                }
                if (to.weaksearchflg && !from.weaksearchflg) {
                  from.strongany=true;
                  from.weakkw=true;
                  stronganycount++;
                  to.link1seed=true;
                }
                from.stopstrong=true;
                to.stopstrong=true;
              }
              if (overbordercount==data.rsuu && weakanycount<blueLink && (!from.stopweak && !to.stopweak)) {
                colorlink=new Color(0,0,255);
                if (from.weaksearchflg && !to.weaksearchflg) {
                  to.weakall=true;
                  to.weakkw=true;
                  weakanycount++;
                }
                if (to.weaksearchflg && !from.weaksearchflg) {
                  from.weakall=true;
                  from.weakkw=true;
                  weakanycount++;
                }
                from.stopweak=true;
                to.stopweak=true;
              }
              int p[][]=new int[2][2];
              p[0][0]=(int)from.x;
              p[0][1]=(int)from.y;
              p[1][0]=(int)to.x;
              p[1][1]=(int)to.y;
              g.setColor(colorlink);
              if (colorlink!=null) {
                g.drawLine(p[0][0],p[0][1],p[1][0],p[1][1]);
              }
              colorlabel=Color.red;
              g.setColor(colorlabel);
              if (colorlink!=null && rhighcount>=1 && overbordercount!=data.rsuu) {
                g.drawString(rlabel,(int)(p[0][0]+(p[1][0]-p[0][0])/(2)),(int)(p[0][1]+(p[1][1]-p[0][1])/(2)));
              }
            }
          }
          if ((from.set || to.set || from.set2 || to.set2)&&rij>=data.borderR) {
            int p[][]=new int[2][2];
            p[0][0]=(int)from.x;
            p[0][1]=(int)from.y;
            p[1][0]=(int)to.x;
            p[1][1]=(int)to.y;
            drawSpring(g,rij,p);
          }
        }
        mousesetflg=false;
      } else {
        for (int i=0; i<data.s; i++) {
          Keyword from=data.keyword[data.spring[i].from];
          Keyword to=data.keyword[data.spring[i].to];
          double rij=data.spring[i].rij;
          from.weaksearchflg=false;
          from.weakkw=false;
          to.weaksearchflg=false;
          to.weakkw=false;
          from.mouseset=false;
          to.mouseset=false;
          from.strongany=false;
          to.strongany=false;
          from.weakall=false;
          to.weakall=false;
          from.stopstrong=false;
          to.stopstrong=false;
          from.link1seed=false;
          to.link1seed=false;
          if (rij<data.borderR|| from.del || to.del || from.dummy || to.dummy) {
            continue;
          }
          int p[][]=new int[2][2];
          p[0][0]=(int)from.x;
          p[0][1]=(int)from.y;
          p[1][0]=(int)to.x;
          p[1][1]=(int)to.y;
          String lbl;
          if (from.focus || from.focus2 || from.focus3 || from.focus4 || from.focus5 || from.focus6 || from.focus7) {
            lbl=from.lbl.substring(0,from.lbl.length()-1);
          } else {
            lbl=from.lbl;
          }
          if (to.focus || to.focus2 || to.focus3 || to.focus4 || to.focus5 || to.focus6 || to.focus7) {
            lbl=to.lbl.substring(0,to.lbl.length()-1);
          } else {
            lbl=to.lbl;
          }
          int sw=fm.stringWidth(lbl)/2+5;
          int sh=fm.getHeight()/2+2;
          if (from.set || to.set || from.set2 || to.set2) {
            drawSpring(g,rij,p);
          }
        }
      }
      drawKeyword(g);
      drawInfo(g);
    }

    //Font setting
    private void setFont(Graphics g) {
      Font font=new Font("Dialog",Font.PLAIN,fontSize);
      g.setFont(font);
      fm=getFontMetrics(font);
    }

    //Display links(springs)
    private void drawSpring(Graphics g,double rij,int[][] p) {
      //Set link colors according to power of relationship between keywords
      g.setColor(rij>=(data.borderR+(1-data.borderR)*3/4) ? Color.black : (rij>=(data.borderR+(1-data.borderR)/2) ? Color.darkGray : (rij>=(data.borderR+(1-data.borderR)/4) ? Color.gray : Color.lightGray)));
      g.drawLine(p[0][0],p[0][1],p[1][0],p[1][1]);
    }

    //Display keywords
    private void drawKeyword(Graphics g) {
      for (int i=0; i<data.k; i++) {
        Keyword kw=data.keyword[i];
        if (kw.dummy) {
          continue;
        }
        int x=(int)kw.x;
        int y=(int)kw.y;
        String lbl;
        if (kw.focus || kw.focus2 || kw.focus3 || kw.focus4 || kw.focus5 || kw.focus6 || kw.focus7) {
          lbl=kw.lbl.substring(0,kw.lbl.length()-1);
        } else {
          lbl=kw.lbl;
        }
//				String lbl=kw.focus ? kw.lbl.substring(0,kw.lbl.length()-1) : kw.lbl; //Cut @
//				String lbl=kw.focus2 ? kw.lbl.substring(0,kw.lbl.length()-1) : kw.lbl; //Cut #
        lbl=lbl.length()>10 ? lbl.substring(0,10) : lbl;
        int sw=fm.stringWidth(lbl)/2+5; //Set keyword's width
        int sh=fm.getHeight()/2+2; //Set keyword's height
        //Set background colors of keywords
        if (!kw.del) {
          Color color;
          if (kw.set2) {
            color=Color.yellow;
          }
          //if(kw.k>=2) color=new Color(255,255-2*kw.k,0);
          //	else if(kw.set) color=new Color(255,126,28);
          //	else if(kw.focus) color=Color.blue; //@ Blue
          else if (kw.focus2) {
            color=Color.green;  //# Light green
          }
          //	else if(kw.focus3) color=new Color(255,0,255); //$ Red-purple
          else if (kw.focus4) {
            color=Color.lightGray;  //% Light gray
          } else if (kw.focus5) {
            color=new Color(128,128,255);  //& Blue-purple
          } else if (kw.focus6) {
            color=new Color(0,128,0);  //? Dark green
          }
          //	else if(kw.focus7) color=new Color(128,0,128); //! Dark purple
          else {
            color=Color.cyan;
          }
          g.setColor(color);
          g.fillRect(x-sw,y-sh,sw*2,sh*2); //Draw keyword's background
        }
        int space=lbl.indexOf(' ');
        int num=space!=-1 ? Integer.parseInt(lbl.substring(0,space)) : 0;
        g.setColor(kw.set ? Color.red : ((kw.mouseset) ? Color.blue : Color.black));
        g.drawRect(x-sw,y-sh,sw*2,sh*2);
        if (kw.set||kw.mouseset) {
          g.drawRect(x-sw+1,y-sh+1,sw*2-2,sh*2-2);
        }
        if (kw.link1seed) {
          g.setColor(Color.green);
          g.drawRect(x-sw-5, y-sh-5, sw*2+10, sh*2+10);
        }
        if (kw.weakkw && !kw.weaksearchflg) {
          if (kw.strongany) {
            g.setColor(Color.pink);  // 2 path
          }
          if (kw.weakall) {
            g.setColor(Color.green);  // 1 path
          }
          g.drawRect(x-sw-5,y-sh-5,sw*2+10,sh*2+10);
        }
        g.setColor(Color.black);
        g.drawString(lbl,x-sw+5,y-sh+2+fm.getAscent());
      }
    }

    //Display number of keywords
    private void drawInfo(Graphics g) {
      Font font=new Font("SanSerif",Font.PLAIN,12);
      g.setFont(font);
      g.setColor(Color.green);
      String strK=String.valueOf(data.k);
      g.drawString("Keywords"+strK, w-120,h-10);
    }

    //Mouse event
    public void mousePressed(MouseEvent e) {
      if (e.getModifiers()==InputEvent.BUTTON2_MASK) {}
      int x=e.getX();
      int y=e.getY();
      findNear(x,y);
      if (near!=null) {
        if (e.getModifiers()==InputEvent.BUTTON1_MASK) {
          if (!near.set || !near.del || !near.set2) {
            near.set=true;
            data.gousei++;
            near.del=false;
            movecount=0;
          }
        } else if (e.getModifiers()==InputEvent.BUTTON3_MASK) {
          movecount=0;
          stateChange();
        }
        addMouseMotionListener(this);
      }
      repaint();
    }

    //Mouse wheel event
    public void mouseWheelMoved(MouseWheelEvent e) {
      boolean setflg=false;
      for (int i=0; i<data.k; i++) {
        Keyword kw=data.keyword[i];
        if (kw.dummy) {
          continue;
        }
        int kx=(int)kw.x;
        int ky=(int)kw.y;
        String lbl;
        if (kw.focus || kw.focus2 || kw.focus3 || kw.focus4 || kw.focus5 || kw.focus6 || kw.focus7) {
          lbl=kw.lbl.substring(0,kw.lbl.length()-1);
        } else {
          lbl=kw.lbl;
        }
        int sw=fm.stringWidth(lbl)/2+5;
        int sh=fm.getHeight()/2+2;
        if ((kw.set||kw.set2)&&(kx-sw)<mousex && mousex<(kx+sw) && (ky-sh)<mousey && mousey<(ky+sh)) {
          kw.concent+=2*e.getWheelRotation();
          if (kw.concent>0) {
            kw.set2=true;
            setflg=true;
            break;
          } else {
            kw.set2=false;
            kw.concent=0;
            setflg=true;
            break;
          }
        }
      }
      if (!setflg) {
        data.m+=20*e.getWheelRotation();
        data.setLij();
      }
      movecount=0;
    }

    //Search a keyword arround the mouse cursor arrow
    private void findNear(int x,int y) {
      for (int i=0; i<data.k; i++) {
        Keyword kw=data.keyword[i];
        if (kw.dummy) {
          continue;
        }
        int kx=(int)kw.x;
        int ky=(int)kw.y;
        String lbl;
        if (kw.focus || kw.focus2 || kw.focus3 || kw.focus4 || kw.focus5 || kw.focus6 || kw.focus7) {
          lbl=kw.lbl.substring(0,kw.lbl.length()-1);
        } else {
          lbl=kw.lbl;
        }
        int sw=fm.stringWidth(lbl)/2+5;
        int sh=fm.getHeight()/2+2;
        if ((kx-sw)<x && x<(kx+sw) && (ky-sh)<y && y<(ky+sh)) {
          near=kw;
        }
      }
    }

    //Change state of keyword
    private void stateChange() {
      if (near.set && near.set2) {
        near.set2=false;
        near.concent=0;
      } else if (near.del || (near.set && !near.set2)) {
        near.set=false;
        near.set2=false;
        near.del=false;
        near.concent=0;
      } else if (near.set2) {
        near.del=false;
        near.set2=false;
        near.k=1;
        near.concent=0;
      } else if (!near.del) {
        near.del=true;
        near.concent=0;
      }

    }

    public void mouseReleased(MouseEvent e) {
      removeMouseMotionListener(this);
      movecount=0;
      repaint();
    }

    public void mouseDragged(MouseEvent e) {
      near.x=e.getX();
      near.y=e.getY();
      if (!near.del) {
        near.set=true;
      }
      movecount=0;
      repaint();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
      mouseEntered1=true;
      checkMouseEntered();
    }
    public void mouseExited(MouseEvent e) {
      mouseEntered1=false;
      checkMouseEntered();
    }
    public void mouseMoved(MouseEvent e) {}
  }


  //Keyword class
  private final class Keyword {
    private String lbl;
    private int k;
    private double x,y,dx,dy,E,move;
    private boolean focus,focus2,focus3,focus4,focus5,focus6,focus7,clear,set,set2,del,mouseset;
    //focus keyword types, clear, set focus keyword, concent set concentrate keyword, transparent keyword, etc.
    private boolean weaksearchflg;
    private boolean weakkw;
    private boolean strongany;
    private boolean weakall;
    private boolean stopstrong;
    private boolean link1seed;
    private boolean stopweak;
    private boolean sindouX,sindouY;
    private int sindoucountX,sindoucountY,aroundcount;
    private int concent;
    private boolean dummy;
    private boolean setlink1;
    private boolean linkseed;
  }

  //Spring class
  private final class Spring {
    private int from,to;
    private double rij,lij,dij;
    private double r[];
  }

  //About data setting -begin-
  private final class Data {
    private Keyword keyword[];
    private Spring spring[];
    private int k;
    private int s;
    private double m,ll;
    private int sw;
    private String[] f;
    private String[] rname;
    private double allspr;
    private double maxR;
    private int sepacount;
    private int[] sepa;
    private double[] balance,balancetemp;
    private int gousei;
    private double borderR;
    private int rsuu;
    private double[] hiritu;
    private double[] block;
    private boolean revR;
    private Color[] color1,color2;
    private String[] row;

    private Data() {
      ll=1;
      keyword=new Keyword[1024];
      spring=new Spring[1024*(1024-1)/2];
      borderR=threshold;
    }

    //Set keyword's status
    private void setParam() {
      for (int i=0; i<k; i++) {
        keyword[i].move=0;
        keyword[i].clear=true;
      }
    }

    //Read data from a mining module //IMPORTANT
    private void readOutputtext(String output_text) {
      m=Math.min(sizex,sizey)/3;
      s=0; //Clear spring
      setParam();
      String ot=output_text;
      rsuu=-2;
      row=ot.split("\n");
      f=row[0].split(",");
      sepacount=f.length;
      sepa=new int[sepacount-3];
      rname=new String[f.length];
      for (int i=0; i<f.length; i++) {
        rname[i]=f[i];
        rsuu++;
      }
      hiritu=new double[rsuu];
      block=new double[rsuu];
      balance=new double[rsuu];
      balancetemp=new double[rsuu];
      color1=new Color[rsuu];
      color2=new Color[rsuu];
      for (int i=0; i<rsuu; i++) {
        balance[i]=1;
        color1[i]=new Color((int)(Math.random()*200),(int)(Math.random()*200),(int)(Math.random()*200));
        color2[i]=new Color((int)(Math.random()*200),(int)(Math.random()*200),(int)(Math.random()*200));
      }
      for (int i=1; i<row.length; i++) {
        f=row[i].split(",");
        String from=f[0];
        String to=f[1];
        NewSetSpring(from,to);
      }
      clearKeyword();
      setLij();
    }

    //Store spring objects including several attributes
    private void NewSetSpring(String from,String to) {
      Spring sp=new Spring();
      sp.from=setKeyword(from);
      sp.to=setKeyword(to);
      if (sp.from>sp.to) {
        int n=sp.from;
        sp.from=sp.to;
        sp.to=n;
      }
      sp.r=new double[f.length-2];
      for (int i=0; i<f.length-2; i++) {
        sp.r[i]=Double.parseDouble(f[i+2]);
      }
      spring[s]=sp;
      s++;
    }

    //Store keyword object and return a it's address
    private int setKeyword(String lbl) {
      for (int i=0; i<k; i++) {
        if (keyword[i].lbl.equals(lbl)) {
          keyword[i].clear=false;
          return i;
        }
      }
      //Add keyword
      Keyword kw=new Keyword();
      kw.lbl=lbl;
      //kw.focus=lbl.endsWith("@");
      kw.focus2=lbl.endsWith("#");
      //kw.focus3=lbl.endsWith("$");
      kw.focus4=lbl.endsWith("%");
      kw.focus5=lbl.endsWith("&");
      kw.focus6=lbl.endsWith("?");
      //kw.focus7=lbl.endsWith("!");
      if (lbl.endsWith("!") || lbl.equals("dummyi") || lbl.equals("dummyj")) {
        kw.dummy=true;
      }
      kw.x=10+(sizex-20)*Math.random();
      kw.y=10+(sizey-20)*Math.random();
      kw.k=1;
      keyword[k]=kw;
      k++;
      return k-1;
    }

    //Delete unnecessary keywords
    private void clearKeyword() {
      for (int i=k-1; i>=0; i--) {
        Keyword kw=keyword[i];
        if (kw.clear || kw.del) {
          for (int j=i; j<k; i++) {
            keyword[j]=keyword[j+1];
          }
          k--;
          for (int j=0; j<s; j++) {
            Spring sp=spring[j];
            if (sp.from>i) {
              sp.from--;
            }
            if (sp.to>i) {
              sp.to--;
            }
          }
        }
      }
    }

    //Set natural spring length
    private void setLij() {
      maxR=0;
      double spcount=0;
      for (int i=0; i<s; i++) {
        for (int j=0; j<f.length-2; j++) {
          spcount+=spring[i].r[j]*balance[j];
        }
        spring[i].rij=spcount;
        spcount=0;
        allspr=Math.abs(spring[i].rij);
        if (allspr>maxR) {
          maxR=allspr;
          allspr=0;
        }
      }
      for (int i=0; i<s; i++) {
        spring[i].rij=spring[i].rij/maxR;
        if (revR && spring[i].rij!=0) {
          spring[i].lij=1.2*m*spring[i].rij+20;
        } else {
          spring[i].lij=m*(1.3-spring[i].rij)+30;
        }
      }
    }
  }
  //About data setting -end-
}