//
// Visuzalization module for TETDM
// LinkCheck.java Version 0.01
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the licenses.
// You should read the README file.
//


package module.VisualizationModules.DrawTopBottom;

public class LinkCheck {
  public static int total;
  //public static int check[];


  public static int linkcheck(int init, int link[][], int n,int check[]) {
    //check = new int[n];
    for (int i=0; i<n; i++) {
      check[i] = 0;
    }

    check[init] = 1;

    return check_line(init, link, n,check);
  }

  public static int check_line(int k, int link[][], int n, int check[]) {
    int sub_total = 1;

    for (int i=0; i<n; i++)
      if (link[k][i] == 1 && check[i] == 0) {
        check[i] = 1;
        sub_total += check_line(i,link,n,check);
      }

    return sub_total;
  }
}
