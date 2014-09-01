
package source.Utility;

//
// Utility Program for TETDM
// Qsort.java Version 0.44
// Copyright(C) 2007-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

public class Qsort {
  public static void initializeIndex(int index[], int n) {
    int i;

    for (i=0; i<n; i++) {
      index[i] = i;
    }
  }


  public static void quicksort(double a[],int index[], int n) {
    int i,bn=0,cn=0;
    double b[] = new double[n];
    double c[] = new double[n];
    int bindex[] = new int[n];
    int cindex[] = new int[n];

    double temp,border;
    int t;

    if (n == 0 || n == 1) {
      return;
    }

    if (n == 2) {
      if (a[1] > a[0]) {
        temp = a[0];
        a[0] = a[1];
        a[1] = temp;
        t = index[0];
        index[0] = index[1];
        index[1] = t;
      }
      return;
    }

    for (i=0,border=0; i<n; i++) {
      border += a[i];
    }
    border /= n;

    for (i=0,bn=0,cn=0; i<n; i++)
      if (a[i] > border) {
        bindex[bn] = index[i];
        b[bn++] = a[i];
      } else {
        cindex[cn] = index[i];
        c[cn++] = a[i];
      }

    if (bn != 0 && cn != 0) {
      quicksort(b,bindex,bn);
      quicksort(c,cindex,cn);

      for (i=0; i<bn; i++) {
        a[i] = b[i];
        index[i] = bindex[i];
      }
      for (i=bn; i<bn+cn; i++) {
        a[i] = c[i-bn];
        index[i] = cindex[i-bn];
      }
    }
  }

  public static void quicksort(int a[],int index[], int n) {
    int i,bn=0,cn=0;
    int b[] = new int[n];
    int c[] = new int[n];
    int bindex[] = new int[n];
    int cindex[] = new int[n];

    int temp;
    double border;
    int t;

    if (n == 0 || n == 1) {
      return;
    }

    if (n == 2) {
      if (a[1] > a[0]) {
        temp = a[0];
        a[0] = a[1];
        a[1] = temp;
        t = index[0];
        index[0] = index[1];
        index[1] = t;
      }
      return;
    }

    for (i=0,border=0; i<n; i++) {
      border += a[i];
    }
    border /= n;

    for (i=0,bn=0,cn=0; i<n; i++)
      if (a[i] > border) {
        bindex[bn] = index[i];
        b[bn++] = a[i];
      } else {
        cindex[cn] = index[i];
        c[cn++] = a[i];
      }

    if (bn != 0 && cn != 0) {
      quicksort(b,bindex,bn);
      quicksort(c,cindex,cn);

      for (i=0; i<bn; i++) {
        a[i] = b[i];
        index[i] = bindex[i];
      }
      for (i=bn; i<bn+cn; i++) {
        a[i] = c[i-bn];
        index[i] = cindex[i-bn];
      }
    }
  }


  //return Nth ranked data
  public static double quickselect(double a[], int n, int rank) {
    int i,bn=0,cn=0;
    double b[] = new double[n];
    double c[] = new double[n];
    double border, r;

    if (n == 0) {
      return 100000000;
    }

    if (n == 1) {
      return a[0];
    }

    for (i=1; i<n; i++)
      if (a[i] - a[0] < -0.000001 || a[i] - a[0] > 0.000001) {
        break;
      }
    if (i == n) {
      return a[0];
    }

    for (i=0,border=0; i<n; i++) {
      border += a[i];
    }
    border /= (double)n;

    for (i=0,bn=0,cn=0; i<n; i++)
      if (a[i] > border) {
        b[bn++] = a[i];
      } else {
        c[cn++] = a[i];
      }

    if (rank <= bn) {
      r = quickselect(b,bn,rank);
    } else {
      r = quickselect(c,cn,rank-bn);
    }

    return r;
  }

  //return Nth ranked data
  public static double quickselect(int a[], int n, int rank) {
    int i,bn=0,cn=0;
    int b[] = new int[n];
    int c[] = new int[n];
    double border, r;

    if (n == 0) {
      return 100000000;
    }

    if (n == 1) {
      return a[0];
    }

    for (i=1; i<n; i++)
      if (a[i] != a[0]) {
        break;
      }
    if (i == n) {
      return a[0];
    }

    for (i=0,border=0; i<n; i++) {
      border += a[i];
    }
    border /= (double)n;

    for (i=0,bn=0,cn=0; i<n; i++)
      if (a[i] > border) {
        b[bn++] = a[i];
      } else {
        c[cn++] = a[i];
      }

    if (rank <= bn) {
      r = quickselect(b,bn,rank);
    } else {
      r = quickselect(c,cn,rank-bn);
    }

    return r;
  }

  //return maximum data
  public static int findMax(int a[], int n) {
    int tempMax;

    tempMax = a[0];
    for (int i=1; i<n; i++)
      if (a[i] > tempMax) {
        tempMax = a[i];
      }

    return tempMax;
  }

  //return maximum data ID
  public static int findMaxID(int a[], int idlist[], int n) {
    int tempMax;
    int count;

    count = 0;
    tempMax = a[0];
    idlist[count++] = 0;

    for (int i=1; i<n; i++)
      if (a[i] > tempMax) {
        count=0;
        tempMax = a[i];
        idlist[count++] = i;
      } else if	(a[i] == tempMax) {
        idlist[count++] = i;
      }

    return count;
  }

  public static int[] createSortedValue(int array[], int sortedIndex[]) {
    int length = array.length;
    int newArray[] = new int[length];

    for (int i=0; i<length; i++) {
      newArray[i] = array[sortedIndex[i]];
    }

    return newArray;
  }

  public static double[] createSortedValue(double array[], int sortedIndex[]) {
    int length = array.length;
    double newArray[] = new double[length];

    for (int i=0; i<length; i++) {
      newArray[i] = array[sortedIndex[i]];
    }

    return newArray;
  }

  public static String[] createSortedValue(String array[], int sortedIndex[]) {
    int length = array.length;
    String newArray[] = new String[length];

    for (int i=0; i<length; i++) {
      newArray[i] = array[sortedIndex[i]];
    }

    return newArray;
  }
}
