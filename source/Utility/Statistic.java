
package source.Utility;

//
// Utility Program for TETDM
// Statistic.java Version 0.44
// Copyright(C) 2007-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

public class Statistic {
  public static int max(int a[]) {
    int tempMax = a[0];

    for (int i=1; i<a.length; i++)
      if (a[i] > tempMax) {
        tempMax = a[i];
      }

    return tempMax;
  }

  public static double max(double a[]) {
    double tempMax = a[0];

    for (int i=1; i<a.length; i++)
      if (a[i] > tempMax) {
        tempMax = a[i];
      }

    return tempMax;
  }

  public static int min(int a[]) {
    int tempMin = a[0];

    for (int i=1; i<a.length; i++)
      if (a[i] < tempMin) {
        tempMin = a[i];
      }

    return tempMin;
  }

  public static double min(double a[]) {
    double tempMin = a[0];

    for (int i=1; i<a.length; i++)
      if (a[i] < tempMin) {
        tempMin = a[i];
      }

    return tempMin;
  }

  public static double average(int a[]) {
    double tempTotal = 0.0;

    for (int i=0; i<a.length; i++) {
      tempTotal += a[i];
    }

    return tempTotal/a.length;
  }

  public static double average(double a[]) {
    double tempTotal = 0.0;

    for (int i=0; i<a.length; i++) {
      tempTotal += a[i];
    }

    return tempTotal/a.length;
  }

  public static double median(int a[]) {
    if (a.length % 2 == 1) {
      return Qsort.quickselect(a, a.length, (a.length+1)/2);
    } else {
      return (Qsort.quickselect(a, a.length, a.length/2) + Qsort.quickselect(a, a.length, a.length/2 + 1))/2;
    }
  }

  public static double median(double a[]) {
    if (a.length % 2 == 1) {
      return Qsort.quickselect(a, a.length, (a.length+1)/2);
    } else {
      return (Qsort.quickselect(a, a.length, a.length/2) + Qsort.quickselect(a, a.length, a.length/2 + 1))/2;
    }
  }

}
