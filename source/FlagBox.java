//
// Core Program for TETDM
// FlagBox.java Version 0.49
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
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;


public class FlagBox {
  Control control;
  Flags flags;

  FlagBox(Control control,int task_num) {
    this.control = control;

//		input_String = FILEIO.TextFileAllReadCodeArray(new File(control.absolutePath + "source" + File.separator + "operate.txt"));
    flags = new Flags(task_num);
//		System.out.println();
  }

  public class Flags {
    String goal;
    int no,num;
    int id;
//		boolean clear;

    boolean clear[];
    int list_num = -1;

    Flags(int num) {
      init_data(num);
    }
    void init_data(int num) {
      list_num = num;
      clear = new boolean [list_num];
      for (int i=0; i<list_num; i++) {
        clear[i] = false;
      }
    }
  }
}

class Make_Task_Data {
  int list_num;
  int task_num=0,thema_num=0;
  String input_task[],input_String[],input_thema[];
  String file_add = "";

  Control control;

  Color base_color = new Color(0x00,0xff,0xff);
  Color click_color = new Color(0x41,0x69,0xE1);
  Color clear_color = Color.pink;
  Color thema_color = new Color(0xf0,0xff,0xff);

  class Items {
    int x,y,width,hight;
//		Color color,frame_color;

    int posx,posy;
    String goal;
    int no,num;
    int id;
    int level;
    int exp;
    boolean clear;

    int click_num;
    Color color;

    int open_condition[];   /////////////////////////[task_num]task_number,-1
    int open;//can click_task_number
    boolean canopen;

    int max_page,max_fig,max_que;
    int fig_num;
    int page;
    boolean read;

    int page_fig [];
    String fig_name [];

    String p_str[][];
    int line_max;
    String que_ans[][];
    Image image;

  }

  Items theme[],task[];

  void set_item_data(int judge) {
    Color color[] = new Color[4];
    color[0] = new Color(0xff,0xfa,0xcd);
    color[1] = new Color(0xff,0xf0,0xf5);
    color[2] = new Color(0xf0,0xff,0xf0);
    color[3] = new Color(0xf0,0xf8,0xff);

    String str="";
//		int count[] = new int [input_thema.length];
//		for(int i=0;i<input_thema.length;i++) count[i] = 0;

    if (judge == 0) {
      for (int i=0; i<input_task.length; i++) {
        str = input_task[i];
        String [] str2 = str.split(",",0);

        task[i].color = base_color;
        task[i].no = Integer.parseInt(str2[0]);
        task[i].goal = str2[1];
        task[i].exp = Integer.parseInt(str2[2]);
        task[i].level = Integer.parseInt(str2[3]);
        for (int j=0; j<input_thema.length; j++) {
          if (theme[j].level==task[i].level) {
            theme[j].num += 1;
            task[i].id = theme[j].num;
            break;
          }
        }
        if (Integer.parseInt(str2[4]) == 1) {
          task[i].clear = true;
          task[i].color = clear_color;
        } else {
          task[i].clear = false;
        }

        task[i].open = Integer.parseInt(str2[5]);

        task[i].click_num = 0;
        task[i].posy = 0;

        task[i].read = false;

        task[i].canopen = false;
//				count[task[i].level] += 1;
//				task[i].id = count[task[i].level];

//				System.out.println(task[i].no +" "+task[i].goal +" id:"+task[i].id +" level:"+task[i].level +" "+
//						task[i].exp +" "+task[i].clear +" "+task[i].click_num);
      }
    } else {
      for (int i=0; i<input_thema.length; i++) {

        str = input_thema[i];
        String [] str2 = str.split(",",0);

        theme[i].goal = str2[2];
        theme[i].num = 0;
        theme[i].level = Integer.parseInt(str2[1]);
        theme[i].exp = 0;

        theme[i].color = color[theme[i].level % 4];

        theme[i].click_num = 0;
        theme[i].posy = 0;

//				System.out.println(theme[i].num +" "+theme[i].goal +" "+theme[i].id +" "+theme[i].level +" "+
//						theme[i].exp +" "+theme[i].clear +" "+theme[i].click_num);
      }
    }
  }

  Support support;
  int classstage_num = 0;

  Make_Task_Data(Control control, Support support, int classstage_num) {
    this.control = control;
    this.support = support;
    this.classstage_num = classstage_num;

    file_add = control.absolutePath + "source"
               + File.separator + "operate" + File.separator;

    init_datas(classstage_num);

    task = new Items[task_num];
    theme = new Items[thema_num];

    for (int i=0; i<task_num; i++) {
      task[i] = new Items();
    }

    for (int i=0; i<thema_num; i++) {
      theme[i] = new Items();
    }

    set_item_data(1);
    set_item_data(0);
  }

  void init_datas(int classstage_num) {
    input_String = FILEIO.TextFileAllReadCodeArray
                   (new File(file_add + "operate" + support.classstage[classstage_num] + ".txt"));

    for (int i=0; i<input_String.length; i++) {
      if (input_String[i].indexOf("Mission")!=-1) {
        thema_num += 1;
      } else {
        task_num += 1;
      }
    }

    int k = 0,l = 0;

    input_task = new String[task_num];
    input_thema = new String[thema_num];

    for (int i=0; i<input_String.length; i++) {
      if (input_String[i].indexOf("Mission")==-1) {
        input_task[k] = input_String[i];
        k++;
      } else {
        input_thema[l] = input_String[i];
        l++;
      }
    }
  }

  void read_task_details(int num) {
    if (task[num].read == true) {
      return;
    }
    task[num].read = true;
    input_String = FILEIO.TextFileAllReadCodeArray
                   (new File(file_add + "operate" + support.classstage[support.classstage_num] + task[num].no + ".txt"));
    check_text(num);
  }

  void check_text(int num) {
    String p_f = "";
    String fign = "";
    String que = "";
    String ans = "";
    int f_num = 0;
    int j=0;

    task[num].p_str = new String[input_String.length][2];

    for (int i=0; i<input_String.length; i++) {
      if (input_String[i].indexOf("page")!=-1) {
        task[num].max_page += 1;
        p_f += Integer.toString(f_num) + ",";
        f_num = 0;
        continue;
      }
      if (input_String[i].indexOf("fig,")!=-1) {
        String str = input_String[i];
        task[num].max_fig += 1;
        fign += str.substring(str.indexOf(",")+1) + ",";
        f_num += 1;
        continue;
      }
      if (input_String[i].indexOf("question,")!=-1) {
        String str = input_String[i];
        task[num].max_que += 1;
        que += str.substring(str.indexOf(",")+1) + ",";
        continue;
      }
      if (input_String[i].indexOf("answer,")!=-1) {
        String str = input_String[i];
        ans += str.substring(str.indexOf(",")+1) + ",";
        continue;
      } else {
        //			System.out.println(input_String[i]);
        task[num].p_str[j][0] = Integer.toString(task[num].max_page);
        task[num].p_str[j][1] = input_String[i];
        j++;
      }
    }
    task[num].line_max = j;

    p_f += Integer.toString(f_num) + ",";

    task[num].page_fig = new int [task[num].max_page+1];  //page_num/fig_num in page
    task[num].fig_name = new String[task[num].max_fig]; //page_num/fig_name
    task[num].que_ans = new String [task[num].max_que+1][3];  //

    String [] p_f2 = p_f.split(",",0);
    for (int i=0; i<task[num].max_page+1; i++) {
      task[num].page_fig[i] = Integer.parseInt(p_f2[i]);
    }
    String [] fign2 = fign.split(",",0);
    for (int i=0; i<task[num].max_fig; i++) {
      task[num].fig_name[i] = fign2[i];
    }

    String [] que2 = que.split(",",0);
    String [] ans2 = ans.split(",",0);
    for (int i=0; i<task[num].max_que; i++) {
      task[num].que_ans[i][0] = que2[i];
      task[num].que_ans[i][1] = ans2[i];
      task[num].que_ans[i][2] = "F";
    }
  }

  void preserve_task_list_text(int classstage_num) {
    try {
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file_add + "operate" + support.classstage[classstage_num] + ".txt")));



      for (int i=0; i<input_thema.length; i++) {
        pw.println("Mission,"+theme[i].level +","+theme[i].goal);
      }

      for (int i=0; i<input_task.length; i++) {
        pw.print(task[i].no +","+task[i].goal +","+task[i].exp +","+task[i].level);
        if (task[i].clear == true) {
          pw.print(",1,");
        } else {
          pw.print(",0,");
        }
        pw.println(task[i].open);
      }
      pw.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
