//
// Core Program for TETDM
// Difference.java Version 0.53
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import source.TextData.*;


public class Difference {
  static boolean differenceBoolean(boolean data1, boolean data2, int pattern) { //////
    boolean data = false;

    switch (pattern) {
      case 0:  //equal
        if (data1 == data2) {
          data = true;
        } else {
          data = false;
        }
        break;

      case 1:  //or
        if (data1 == true || data2 == true) {
          data = true;
        } else {
          data = false;
        }
        break;

      case 2:  //and
        if (data1 == true && data2 == true) {
          data = true;
        } else {
          data = false;
        }
        break;

      case 3:  //notequal
        if (!(data1 == data2)) {
          data = true;
        } else {
          data = false;
        }
        break;

    }
    return data;
  }

  static int differenceInteger(int data1, int data2, int pattern) {
    int data = 0;

//       System.out.println("differenceInteger:"+pattern);
    //     System.out.println("data1:"+data1);
    //   System.out.println("data2:"+data2);



    switch (pattern) {
      case 0:
        data = data1 - data2;
        break;

      case 1:
        data = data2 - data1;
        break;

      case 2:
        if (data1 > data2) {
          data = data1;
        } else {
          data = data2;
        }
        break;

      case 3:
        if (data1 < data2) {
          data = data1;
        } else {
          data = data2;
        }
        break;
    }
//        System.out.println("calc:"+data);

    return data;
  }

  static double differenceDouble(double data1, double data2, int pattern) {
    double data = 0.0;
//       System.out.println("differenceDouble:"+pattern);


    switch (pattern) {
      case 0:
        data = data1 - data2;
        break;

      case 1:
        data = data2 - data1;
        break;

      case 2:
        if (data1 > data2) {
          data = data1;
        } else {
          data = data2;
        }
        break;

      case 3:
        if (data1 < data2) {
          data = data1;
        } else {
          data = data2;
        }
        break;
    }
//        System.out.println("calc:"+data);

    return data;
  }

  static String differenceString(String data1, String data2, int pattern) { ///////////////new function gor String
    String data = "";


    switch (pattern) {
      case 0:
        if (data1.equals(data2)) {
          data = data1;
        } else {
          data = "";
        }
        break;

      case 1:
        if (!data1.equals(data2)) {
          data = data1+","+data2;
        } else {
          data = "";
        }
        break;

      case 2:
        if (data1.length() > data2.length()) {
          data = data1;
        } else {
          data = data2;
        }
        break;

      case 3:
        if (data1.length() < data2.length()) {
          data = data1;
        } else {
          data = data2;
        }
        break;
    }
    return data;
  }


  /* -----Array----- */

  static boolean[] differenceBooleanArray(boolean data1[], boolean data2[], int pattern) {
    boolean data[] = new boolean[0];
    int totalNumber = data1.length, midNumber = data2.length;
    /*
    switch(pattern)
    {
        case 0:*/
    if (data1.length > data2.length) {
      totalNumber = data2.length;
      midNumber = data1.length;
    }

    data = new boolean[totalNumber];
    for (int i=0; i<midNumber; i++) {
      data[i] = differenceBoolean(data1[i],data2[i],pattern);
    }
    for (int i=midNumber; i<totalNumber; i++) {
      data[i] = false;
    }

    /*break;
    }        */
    return data;
  }

  static int[] differenceIntegerArray(int data1[], int data2[], int pattern) {
    int data[] = new int[0];

    int datasec1[] = {5,7,8,14,18,22,27,27,25,19,14,7};
    int datasec2[] = {4,5,8,14,19,21,26,29};

    data1= datasec1;
    data2= datasec2;

//        System.out.println("differenceIntegerArray!!:"+pattern);

    if (data1.length > data2.length) {
      data = new int[data1.length];
      for (int i=0; i<data2.length; i++) {
        data[i] = differenceInteger(data1[i],data2[i],pattern);
      }
      for (int i=data2.length; i<data1.length; i++) {
        data[i] = data1[i];
      }
    } else {
      data = new int[data2.length];
      for (int i=0; i<data1.length; i++) {
        data[i] = differenceInteger(data1[i],data2[i],pattern);
      }
      for (int i=data1.length; i<data2.length; i++) {
        data[i] = 0;
      }
    }


    if (pattern==4) {

      if (data1.length > data2.length) {
        data = new int[data1.length];
        for (int i=0; i<data2.length; i++) {
          data[i] = differenceInteger(data1[i],data2[i],0);
        }
        for (int i=data2.length; i<data1.length; i++) {
          data[i] = data1[i];
        }
      } else {
        data = new int[data2.length];
        for (int i=0; i<data1.length; i++) {
          data[i] = differenceInteger(data1[i],data2[i],0);
        }
        for (int i=data1.length; i<data2.length; i++) {
          data[i] = 0;
        }
      }


      for (int i=0; i<data.length; i++) {
        if (data[i]<0) {
          data[i]= -1 * data[i];
        }
      }

    }

    if (pattern==5) {

      if (data1.length > data2.length) {
        data = new int[data1.length];
        for (int i=0; i<data2.length; i++) {
          data[i] = differenceInteger(data1[i],data2[i],1);
        }
        for (int i=data2.length; i<data1.length; i++) {
          data[i] = data1[i];
        }
      } else {
        data = new int[data2.length];
        for (int i=0; i<data1.length; i++) {
          data[i] = differenceInteger(data1[i],data2[i],1);
        }
        for (int i=data1.length; i<data2.length; i++) {
          data[i] = 0;
        }
      }


      for (int i=0; i<data.length; i++) {
        if (data[i]<0) {
          data[i]= -1 * data[i];
        }
      }

    }

    /*
    switch(pattern)
    {
        case 0:
            System.out.println("differenceIntegerArray!!:"+pattern);

            if(data1.length > data2.length)
            {
                data = new int[data1.length];
                for(int i=0;i<data2.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data2.length;i<data1.length;i++)
                    data[i] = data1[i];
            }
            else
            {
                data = new int[data2.length];
                for(int i=0;i<data1.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data1.length;i<data2.length;i++)
                    data[i] = -data2[i];
            }

            break;

        case 1:
            System.out.println("differenceIntegerArray:"+pattern);
            if(data1.length > data2.length)
            {
                data = new int[data1.length];
                for(int i=0;i<data2.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data2.length;i<data1.length;i++)
                    data[i] = data1[i];
            }
            else
            {
                data = new int[data2.length];
                for(int i=0;i<data1.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data1.length;i<data2.length;i++)
                    data[i] = -data2[i];
            }

            break;

        case 2:
            System.out.println("differenceIntegerArray:"+pattern);
            if(data1.length > data2.length)
            {
                data = new int[data1.length];
                for(int i=0;i<data2.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data2.length;i<data1.length;i++)
                    data[i] = data1[i];
            }
            else
            {
                data = new int[data2.length];
                for(int i=0;i<data1.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data1.length;i<data2.length;i++)
                    data[i] = -data2[i];
            }

            break;

        case 3:
            System.out.println("differenceIntegerArray:"+pattern);
            if(data1.length > data2.length)
            {
                data = new int[data1.length];
                for(int i=0;i<data2.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data2.length;i<data1.length;i++)
                    data[i] = data1[i];
            }
            else
            {
                data = new int[data2.length];
                for(int i=0;i<data1.length;i++)
                    data[i] = differenceInteger(data1[i],data2[i],pattern);
                for(int i=data1.length;i<data2.length;i++)
                    data[i] = -data2[i];
            }

            break;


    }*/
    return data;
  }

  static double[] differenceDoubleArray(double data1[], double data2[], int pattern) {
//       double datasec1[] ={5.0,7.0,8.0,14.0,18.0,22.0,27.0,27.0,25.0,19.0,14.0,7.0};
    //     double datasec2[] ={4.0,5.0,8.0,14.0,19.0,21.0,26.0,29.0,26.0,19.0,12.0,7.0};

//       data1 = datasec1;
    //     data2 = datasec2;
    double data[] = new double[0];

    switch (pattern) {
      case 0:
        if (data1.length > data2.length) {
          data = new double[data1.length];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = data1[i];
          }
        } else {
          data = new double[data2.length];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = -data2[i];
          }
        }
        break;

      case 1:
        if (data1.length > data2.length) {
          data = new double[data1.length];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = data1[i];
          }
        } else {
          data = new double[data2.length];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = -data2[i];
          }
        }
        break;

      case 2:
        if (data1.length > data2.length) {
          data = new double[data1.length];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = data1[i];
          }
        } else {
          data = new double[data2.length];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = -data2[i];
          }
        }
        break;

      case 3:
        if (data1.length > data2.length) {
          data = new double[data1.length];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = data1[i];
          }
        } else {
          data = new double[data2.length];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = -data2[i];
          }
        }
        break;

      case 4:
        if (data1.length > data2.length) {
          data = new double[data1.length];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],1);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = data1[i];
          }
        } else {
          data = new double[data2.length];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],1);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = -data2[i];
          }
        }

        for (int i= 0; i<data.length; i++) {
          if (data[i]<0.0) {
            data[i] = -1.0*data[i];
          }
        }

        break;

      case 5:
        if (data1.length > data2.length) {
          data = new double[data1.length];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],2);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = data1[i];
          }
        } else {
          data = new double[data2.length];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDouble(data1[i],data2[i],2);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = -data2[i];
          }
        }
        for (int i= 0; i<data.length; i++) {
          if (data[i]<0.0) {
            data[i] = -1.0*data[i];
          }
        }

        break;





    }
    return data;
  }

  static String[] differenceStringArray(String data1[], String data2[], int pattern) { /////////////////new funtcion for array strings
    String data[] = new String[0];
    String stdatasec1[] = {"red","green","orange","pink"};
    String stdatasec2[] = {"apple","orange","lemon","banana","peach"};


    //  data1= stdatasec1;
    // data2= stdatasec2;

    int flagsame=0;

    switch (pattern) {
      case 0:
        if (data1.length > data2.length) {
          data = new String[data1.length];

          for (int i=0; i<data1.length; i++) {
            for (int j=0; j<data2.length; j++) {
              if (data1[i].equals(data2[j])) {
                flagsame=1;
              }
            }
            if (flagsame==0) {
              data[i] = data1[i];
            } else {
              data[i] = "";
            }
            flagsame=0;
          }

          /*
          for(int i=0;i<data2.length;i++)
              data[i] = differenceString(data1[i],data2[i],pattern);
          for(int i=data2.length;i<data1.length;i++)
              data[i] = data1[i];*/
        } else {
          data = new String[data1.length];


          for (int i=0; i<data1.length; i++) {
            for (int j=0; j<data2.length; j++) {
              if (data1[i].equals(data2[j])) {
                flagsame=1;
              }

            }
            if (flagsame==0) {
              data[i] = data1[i];
            } else {
              data[i] = "";
            }
            flagsame=0;
          }

        }
        break;

      case 1:

        if (data1.length > data2.length) {
          data = new String[data1.length];

          for (int i=0; i<data1.length; i++) {
            for (int j=0; j<data2.length; j++) {
              if (data1[i].equals(data2[j])) {
                flagsame=1;
              }
            }
            if (flagsame==1) {
              data[i] = data1[i];
            } else {
              data[i] = "";
            }
            flagsame=0;
          }

          /*
           for(int i=0;i<data2.length;i++)
           data[i] = differenceString(data1[i],data2[i],pattern);
           for(int i=data2.length;i<data1.length;i++)
           data[i] = data1[i];*/
        } else {
          data = new String[data2.length];


          for (int i=0; i<data2.length; i++) {
            for (int j=0; j<data1.length; j++) {
              if (data2[i].equals(data1[j])) {
                flagsame=1;
              }
            }
            if (flagsame==1) {
              data[i] = data2[i];
            } else {
              data[i] = "";
            }
            flagsame=0;
          }

        }
        break;

      case 2:
        flagsame=0;

        if (data1.length > data2.length) {
          data = new String[data2.length];

          for (int i=0; i<data2.length; i++) {
            for (int j=0; j<data1.length; j++) {
              if (data2[i].equals(data1[j])) {
                flagsame=1;
              }
            }
            if (flagsame==0) {
              data[i] = data2[i];
            } else {
              data[i] = "";
            }
            flagsame=0;
          }

          /*
           for(int i=0;i<data2.length;i++)
           data[i] = differenceString(data1[i],data2[i],pattern);
           for(int i=data2.length;i<data1.length;i++)
           data[i] = data1[i];*/
        } else {
          data = new String[data2.length];


          for (int i=0; i<data2.length; i++) {
            for (int j=0; j<data1.length; j++) {
              if (data2[i].equals(data1[j])) {
                flagsame=1;
              }

            }
            if (flagsame==0) {
              data[i] = data2[i];
            } else {
              data[i] = "";
            }
            flagsame=0;
          }

        }
        break;

    }

    int countLength=0;

    for (int i=0; i<data.length; i++) {
      if (!data[i].equals("")) {
        countLength++;
      }
    }

    String datasec[] = new String[countLength];
    countLength = 0;

    for (int i=0; i<data.length; i++) {
      if (!data[i].equals("")) {
        datasec[countLength] = data[i];
        countLength++;
      }
    }
    data=datasec;



    return data;
  }

  /* -----Array2----- */

  static boolean[][] differenceBooleanArray2(boolean data1[][], boolean data2[][], int pattern) { //////////////////
    boolean data[][] = new boolean[0][0];

    switch (pattern) {
      case 0:
        if (data1.length < data2.length) {
          data = new boolean[data1.length][];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceBooleanArray(data1[i], data2[i], pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = new boolean[data1[i].length];
            for (int j=0; j<data1[i].length; j++) {
              data[i][j] = false;
            }
          }
        } else {
          data = new boolean[data2.length][];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceBooleanArray(data1[i], data2[i], pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = new boolean[data2[i].length];
            for (int j=0; j<data2[i].length; j++) {
              data[i][j] = false;
            }
          }
        }
        break;


    }
    return data;
  }

  static int[][] differenceIntegerArray2(int data1[][], int data2[][], int pattern) {
    int data[][] = new int[0][0];

    switch (pattern) {
      case 0:
//               System.out.println("differenceIntegerArray2:"+pattern);
        if (data1.length < data2.length) {
          data = new int[data1.length][];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceIntegerArray(data1[i], data2[i], pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = new int[data1[i].length];
            for (int j=0; j<data1[i].length; j++) {
              data[i][j] = data1[i][j];
            }
          }
        } else {
//                   System.out.println("differenceIntegerArray2:"+pattern);
          data = new int[data2.length][];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceIntegerArray(data1[i], data2[i], pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = new int[data2[i].length];
            for (int j=0; j<data2[i].length; j++) {
              data[i][j] = data2[i][j];
            }
          }
        }
        break;
    }
    return data;
  }

  static double[][] differenceDoubleArray2(double data1[][], double data2[][], int pattern) {
    double data[][] = new double[0][0];

    switch (pattern) {
      case 0:
//                System.out.println("differenceDoubleArray2:"+pattern);
        if (data1.length < data2.length) {
          data = new double[data1.length][];
          for (int i=0; i<data2.length; i++) {
            data[i] = differenceDoubleArray(data1[i], data2[i], pattern);
          }
          for (int i=data2.length; i<data1.length; i++) {
            data[i] = new double[data1[i].length];
            for (int j=0; j<data1[i].length; j++) {
              data[i][j] = data1[i][j];
            }
          }
        } else {
//                   System.out.println("differenceDoubleArray2:"+pattern);
          data = new double[data2.length][];
          for (int i=0; i<data1.length; i++) {
            data[i] = differenceDoubleArray(data1[i], data2[i], pattern);
          }
          for (int i=data1.length; i<data2.length; i++) {
            data[i] = new double[data2[i].length];
            for (int j=0; j<data2[i].length; j++) {
              data[i][j] = -data2[i][j];
            }
          }
        }
        break;


    }
    return data;
  }



  /* -----Called Functions----- */

  public static boolean createDifferenceBoolean(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    boolean data1 = text.connection.getDataBoolean(textID1, moduleID, dataID);
    boolean data2 = text.connection.getDataBoolean(textID2, moduleID, dataID);

    return differenceBoolean(data1, data2, pattern);
  }

  public static int createDifferenceInteger(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    int data1 = text.connection.getDataInteger(textID1, moduleID, dataID);
    int data2 = text.connection.getDataInteger(textID2, moduleID, dataID);

    return differenceInteger(data1, data2, pattern);
  }

  public static double createDifferenceDouble(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    double data1 = text.connection.getDataDouble(textID1, moduleID, dataID);
    double data2 = text.connection.getDataDouble(textID2, moduleID, dataID);

    return differenceDouble(data1, data2, pattern);
  }

  public static String createDifferenceString(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    String data1 = text.connection.getDataString(textID1, moduleID, dataID);
    String data2 = text.connection.getDataString(textID2, moduleID, dataID);

    return differenceString(data1, data2, pattern);
  }

  public static boolean[] createDifferenceBooleanArray(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    boolean data1[] = text.connection.getDataBooleanArray(textID1, moduleID, dataID);
    boolean data2[] = text.connection.getDataBooleanArray(textID2, moduleID, dataID);

    return differenceBooleanArray(data1, data2, pattern);
  }

  public static int[] createDifferenceIntegerArray(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    int data1[] = text.connection.getDataIntegerArray(textID1, moduleID, dataID);
    int data2[] = text.connection.getDataIntegerArray(textID2, moduleID, dataID);

    return differenceIntegerArray(data1, data2, pattern);
  }

  public static double[] createDifferenceDoubleArray(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    double data1[] = text.connection.getDataDoubleArray(textID1, moduleID, dataID);
    double data2[] = text.connection.getDataDoubleArray(textID2, moduleID, dataID);

    return differenceDoubleArray(data1, data2, pattern);
  }

  public static String[] createDifferenceStringArray(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    String data1[] = text.connection.getDataStringArray(textID1, moduleID, dataID);
    String data2[] = text.connection.getDataStringArray(textID2, moduleID, dataID);

    return differenceStringArray(data1, data2, pattern);
  }

  public static boolean[][] createDifferenceBooleanArray2(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    boolean data1[][] = text.connection.getDataBooleanArray2(textID1, moduleID, dataID);
    boolean data2[][] = text.connection.getDataBooleanArray2(textID2, moduleID, dataID);

    return differenceBooleanArray2(data1, data2, pattern);
  }

  public static int[][] createDifferenceIntegerArray2(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    int data1[][] = text.connection.getDataIntegerArray2(textID1, moduleID, dataID);
    int data2[][] = text.connection.getDataIntegerArray2(textID2, moduleID, dataID);

    return differenceIntegerArray2(data1, data2, pattern);
  }

  public static double[][] createDifferenceDoubleArray2(TextData text, int moduleID, int dataID, int textID1, int textID2, int pattern) {
    double data1[][] = text.connection.getDataDoubleArray2(textID1, moduleID, dataID);
    double data2[][] = text.connection.getDataDoubleArray2(textID2, moduleID, dataID);

    return differenceDoubleArray2(data1, data2, pattern);
  }

}
