//
// Core Program for TETDM 
// SetData.java Version 0.44
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.TextData.*;

import java.io.*;


public class SetData
{
    SetData(){}

	//SET DATA
    
	public final void setDataBoolean(int dataID, boolean data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceBoolean(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, data))
        {visualization.booleanNumber++;     return;}
        
        System.out.println("DATA boolean was NOT set. dataID= "+dataID);
    }
    
	public final void setDataInteger(int dataID, int data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, data);
        
        if(diffPattern != -1)
			data = Difference.createDifferenceInteger(text, moduleID, dataID, textID1, textID2, diffPattern);		
		
        if(visualization.setData(dataID, data))
        {visualization.integerNumber++;     return;}
        
        System.out.println("DATA int was NOT set. dataID= "+dataID);
    }
    
	public final void setDataDouble(int dataID, double data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceDouble(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, data))
        {visualization.doubleNumber++;     return;}

        System.out.println("DATA double was NOT set. dataID= "+dataID);
    }
    
	public final void setDataString(int dataID, String data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceString(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, data))
        {visualization.StringNumber++;     return;}

        System.out.println("DATA String was NOT set. dataID= "+dataID);
    }
    
	public final void setDataBooleanArray(int dataID, boolean data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, booleanArrayToBooleanArray(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceBooleanArray(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, booleanArrayToBooleanArray(data)))
        {visualization.booleanArrayNumber++;     return;}

        System.out.println("DATA boolean[] was NOT set. dataID= "+dataID);
    }
    
	public final void setDataIntegerArray(int dataID, int data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, integerArrayToIntegerArray(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceIntegerArray(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, integerArrayToIntegerArray(data)))
        {visualization.integerArrayNumber++;     return;}
        
        System.out.println("DATA int[] was NOT set. dataID= "+dataID);
    }
    
	public final void setDataDoubleArray(int dataID, double data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, doubleArrayToDoubleArray(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceDoubleArray(text, moduleID, dataID, textID1, textID2, diffPattern);		
		
        if(visualization.setData(dataID, doubleArrayToDoubleArray(data)))
        {visualization.doubleArrayNumber++;     return;}

        System.out.println("DATA double[] was NOT set. dataID= "+dataID);
    }
    
	public final void setDataStringArray(int dataID, String data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, StringArrayToStringArray(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceStringArray(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, StringArrayToStringArray(data)))
        {visualization.StringArrayNumber++;     return;}
        
        System.out.println("DATA String[] was NOT set. dataID= "+dataID);
    }
    
	public final void setDataBooleanArray2(int dataID, boolean data[][], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, booleanArray2ToBooleanArray2(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceBooleanArray2(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, booleanArray2ToBooleanArray2(data)))
        {visualization.booleanArray2Number++;     return;}

        System.out.println("DATA boolean[][] was NOT set. dataID= "+dataID);
    }
    
	public final void setDataIntegerArray2(int dataID, int data[][], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, integerArray2ToIntegerArray2(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceIntegerArray2(text, moduleID, dataID, textID1, textID2, diffPattern);		
        
        if(visualization.setData(dataID, integerArray2ToIntegerArray2(data)))
        {visualization.integerArray2Number++;     return;}

        System.out.println("DATA integer[][] was NOT set. dataID= "+dataID);
    }
    
	public final void setDataDoubleArray2(int dataID, double data[][], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
      	text.connection.setData(text.textID, moduleID, dataID, doubleArray2ToDoubleArray2(data));
		
        if(diffPattern != -1)
			data = Difference.createDifferenceDoubleArray2(text, moduleID, dataID, textID1, textID2, diffPattern);		
		
        if(visualization.setData(dataID, doubleArray2ToDoubleArray2(data)))
        {visualization.doubleArray2Number++;     return;}
        
        System.out.println("DATA double[][] was NOT set. dataID= "+dataID);
    }
    
	public final int setDataBoolean(boolean data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.booleanNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceBoolean(text, moduleID, visualization.booleanNumber, textID1, textID2, diffPattern);		
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])
        if(visualization.setData(visualization.booleanNumber, data))
        {visualization.booleanNumber++;return 0;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])
		if(visualization.setData(visualization.integerNumber, booleanToInteger(data)))
		{visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])
		if(visualization.setData(visualization.doubleNumber, booleanToDouble(data)))
		{visualization.doubleNumber++;return 2;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])
		if(visualization.setData(visualization.StringNumber, booleanToString(data)))
		{visualization.StringNumber++;return 3;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])
		if(visualization.setData(visualization.booleanArrayNumber, booleanToBooleanArray(data)))
		{visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])
		if(visualization.setData(visualization.integerArrayNumber, booleanToIntegerArray(data)))
		{visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])
		if(visualization.setData(visualization.doubleArrayNumber, booleanToDoubleArray(data)))
		{visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])
		if(visualization.setData(visualization.StringArrayNumber, booleanToStringArray(data)))
		{visualization.StringArrayNumber++;return 7;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])
		if(visualization.setData(visualization.booleanArray2Number, booleanToBooleanArray2(data)))
		{visualization.booleanArray2Number++;return 8;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])
		if(visualization.setData(visualization.integerArray2Number, booleanToIntegerArray2(data)))
		{visualization.integerArray2Number++;return 9;}

        if(visualization.doubleArray2Number < visualization.dataNumbers[10])
		if(visualization.setData(visualization.doubleArray2Number, booleanToDoubleArray2(data)))
		{visualization.doubleArray2Number++;return 10;}
        
		return 11;
	}	
	
	public final int setDataInteger(int data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.integerNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceInteger(text, moduleID, visualization.integerNumber, textID1, textID2, diffPattern);		
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
        if(visualization.setData(visualization.integerNumber, data))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber,integerToDouble(data)))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])
		if(visualization.setData(visualization.booleanNumber, integerToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, integerToString(data)))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, integerToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, integerToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, integerToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, integerToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
				
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, integerToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, integerToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, integerToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
        
		return 11;        
	}
	public final int setDataDouble(double data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.doubleNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceDouble(text, moduleID, visualization.doubleNumber, textID1, textID2, diffPattern);		
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
        if(visualization.setData(visualization.doubleNumber, data))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])
		if(visualization.setData(visualization.integerNumber, doubleToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, doubleToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, doubleToString(data)))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, doubleToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}

        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, doubleToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, doubleToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, doubleToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, doubleToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}

        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, doubleToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}

        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, doubleToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
        
		return 11;        
	}
	public final int setDataString(String data, int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.StringNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceString(text, moduleID, visualization.StringNumber, textID1, textID2, diffPattern);		
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
        if(visualization.setData(visualization.StringNumber, data))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, StringToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, StringToDouble(data)))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, StringToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, StringToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, StringToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, StringToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, StringToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, StringToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, StringToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, StringToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
        
		return 11;        
	}
	public final int setDataBooleanArray(boolean data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.booleanArrayNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceBooleanArray(text, moduleID, visualization.booleanArrayNumber, textID1, textID2, diffPattern);		
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
        if(visualization.setData(visualization.booleanArrayNumber, booleanArrayToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, booleanArrayToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, booleanArrayToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, booleanArrayToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, booleanArrayToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, booleanArrayToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, booleanArrayToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, booleanArrayToString(data)))
        {visualization.StringNumber++;return 3;}			
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])
		if(visualization.setData(visualization.booleanNumber, booleanArrayToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, booleanArrayToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, booleanArrayToDouble(data)))
        {visualization.doubleNumber++;return 2;}
        
		return 11;        
	}	
	public final int setDataIntegerArray(int data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.integerArrayNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceIntegerArray(text, moduleID, visualization.integerArrayNumber, textID1, textID2, diffPattern);		

        if(visualization.integerArrayNumber < visualization.dataNumbers[5])
        if(visualization.setData(visualization.integerArrayNumber, integerArrayToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}

        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])
		if(visualization.setData(visualization.doubleArrayNumber, integerArrayToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}

        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])
		if(visualization.setData(visualization.booleanArrayNumber, integerArrayToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}

        if(visualization.StringArrayNumber < visualization.dataNumbers[7])
		if(visualization.setData(visualization.StringArrayNumber, integerArrayToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}

        if(visualization.integerArray2Number < visualization.dataNumbers[9])
		if(visualization.setData(visualization.integerArray2Number, integerArrayToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}

        if(visualization.doubleArray2Number < visualization.dataNumbers[10])
		if(visualization.setData(visualization.doubleArray2Number, integerArrayToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}

        if(visualization.booleanArray2Number < visualization.dataNumbers[8])
		if(visualization.setData(visualization.booleanArray2Number, integerArrayToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}

        if(visualization.StringNumber < visualization.dataNumbers[3])
		if(visualization.setData(visualization.StringNumber, integerArrayToString(data)))
        {visualization.StringNumber++;return 3;}

        if(visualization.integerNumber < visualization.dataNumbers[1])
		if(visualization.setData(visualization.integerNumber, integerArrayToInteger(data)))
        {visualization.integerNumber++;return 1;}

        if(visualization.doubleNumber < visualization.dataNumbers[2])
		if(visualization.setData(visualization.doubleNumber, integerArrayToDouble(data)))
        {visualization.doubleNumber++;return 2;}	

        if(visualization.booleanNumber < visualization.dataNumbers[0])
		if(visualization.setData(visualization.booleanNumber, integerArrayToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
        
		return 11;
	}
	public final int setDataDoubleArray(double data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.doubleArrayNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceDoubleArray(text, moduleID, visualization.doubleArrayNumber, textID1, textID2, diffPattern);		
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
        if(visualization.setData(visualization.doubleArrayNumber, doubleArrayToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, doubleArrayToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, doubleArrayToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, doubleArrayToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, doubleArrayToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, doubleArrayToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, doubleArrayToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, doubleArrayToString(data)))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, doubleArrayToDouble(data)))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, doubleArrayToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, doubleArrayToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
        
		return 11;
	}
	public final int setDataStringArray(String data[], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.StringArrayNumber, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceStringArray(text, moduleID, visualization.StringArrayNumber, textID1, textID2, diffPattern);		
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
        if(visualization.setData(visualization.StringArrayNumber, StringArrayToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, StringArrayToString(data)))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, StringArrayToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, StringArrayToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, StringArrayToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, StringArrayToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, StringArrayToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, StringArrayToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, StringArrayToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, StringArrayToDouble(data)))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, StringArrayToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
        
		return 11;       
	}	
	public final int setDataBooleanArray2(boolean data[][], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.booleanArray2Number, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceBooleanArray2(text, moduleID, visualization.booleanArray2Number, textID1, textID2, diffPattern);		
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
        if(visualization.setData(visualization.booleanArray2Number, booleanArray2ToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, booleanArray2ToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, booleanArray2ToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, booleanArray2ToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, booleanArray2ToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, booleanArray2ToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, booleanArray2ToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, booleanArray2ToString(data)))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, booleanArray2ToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, booleanArray2ToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, booleanArray2ToDouble(data)))
        {visualization.doubleNumber++;return 2;}
        
		return 11;        
	}	
	public final int setDataIntegerArray2(int data[][], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.integerArray2Number, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceIntegerArray2(text, moduleID, visualization.integerArray2Number, textID1, textID2, diffPattern);		
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
        if(visualization.setData(visualization.integerArray2Number, integerArray2ToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
		if(visualization.setData(visualization.doubleArray2Number, integerArray2ToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, integerArray2ToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, integerArray2ToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, integerArray2ToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, integerArray2ToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, integerArray2ToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, integerArray2ToString(data)))
        {visualization.StringNumber++;return 3;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, integerArray2ToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, integerArray2ToDouble(data)))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, integerArray2ToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
        
		return 11;
	}
	public final int setDataDoubleArray2(double data[][], int moduleID, TextData text, VisualizationModule visualization, int textID1, int textID2, int diffPattern)
	{
		text.connection.setData(text.textID, moduleID, visualization.doubleArray2Number, data);
		
        if(diffPattern != -1)
			data = Difference.createDifferenceDoubleArray2(text, moduleID, visualization.doubleArray2Number, textID1, textID2, diffPattern);		
		
        if(visualization.doubleArray2Number < visualization.dataNumbers[10])        
        if(visualization.setData(visualization.doubleArray2Number, doubleArray2ToDoubleArray2(data)))
        {visualization.doubleArray2Number++;return 10;}
		
        if(visualization.integerArray2Number < visualization.dataNumbers[9])        
		if(visualization.setData(visualization.integerArray2Number, doubleArray2ToIntegerArray2(data)))
        {visualization.integerArray2Number++;return 9;}
		
        if(visualization.booleanArray2Number < visualization.dataNumbers[8])        
		if(visualization.setData(visualization.booleanArray2Number, doubleArray2ToBooleanArray2(data)))
        {visualization.booleanArray2Number++;return 8;}
		
        if(visualization.StringArrayNumber < visualization.dataNumbers[7])        
		if(visualization.setData(visualization.StringArrayNumber, doubleArray2ToStringArray(data)))
        {visualization.StringArrayNumber++;return 7;}
		
        if(visualization.doubleArrayNumber < visualization.dataNumbers[6])        
		if(visualization.setData(visualization.doubleArrayNumber, doubleArray2ToDoubleArray(data)))
        {visualization.doubleArrayNumber++;return 6;}
		
        if(visualization.integerArrayNumber < visualization.dataNumbers[5])        
		if(visualization.setData(visualization.integerArrayNumber, doubleArray2ToIntegerArray(data)))
        {visualization.integerArrayNumber++;return 5;}
		
        if(visualization.booleanArrayNumber < visualization.dataNumbers[4])        
		if(visualization.setData(visualization.booleanArrayNumber, doubleArray2ToBooleanArray(data)))
        {visualization.booleanArrayNumber++;return 4;}
		
        if(visualization.StringNumber < visualization.dataNumbers[3])        
		if(visualization.setData(visualization.StringNumber, doubleArray2ToString(data)))
        {visualization.StringNumber++;return 3;}	
				
        if(visualization.doubleNumber < visualization.dataNumbers[2])        
		if(visualization.setData(visualization.doubleNumber, doubleArray2ToDouble(data)))
        {visualization.doubleNumber++;return 2;}
		
        if(visualization.integerNumber < visualization.dataNumbers[1])        
		if(visualization.setData(visualization.integerNumber, doubleArray2ToInteger(data)))
        {visualization.integerNumber++;return 1;}
		
        if(visualization.booleanNumber < visualization.dataNumbers[0])        
		if(visualization.setData(visualization.booleanNumber, doubleArray2ToBoolean(data)))
        {visualization.booleanNumber++;return 0;}
        
		return 11;        
	}
	
	// Data COPY
    
    boolean[] booleanArrayToBooleanArray(boolean t[])
    {
        boolean ret[] = new boolean[t.length];
        
        for(int i=0;i<t.length;i++)
            ret[i] = t[i];
        
        return ret;
    }
    
    int[] integerArrayToIntegerArray(int t[])
    {
        int ret[] = new int[t.length];
        
        for(int i=0;i<t.length;i++)
            ret[i] = t[i];
        
        return ret;
    }
    
    double[] doubleArrayToDoubleArray(double t[])
    {
        double ret[] = new double[t.length];
        
        for(int i=0;i<t.length;i++)
            ret[i] = t[i];
        
        return ret;
    }
    
    String[] StringArrayToStringArray(String t[])
    {
        String ret[] = new String[t.length];
        
        for(int i=0;i<t.length;i++)
            ret[i] = t[i];
        
        return ret;
    }
    
    boolean[][] booleanArray2ToBooleanArray2(boolean t[][])
    {
        boolean ret[][] = new boolean[t.length][];
        
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new boolean[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = t[i][j];
        }
        
        return ret;
    }

    int[][] integerArray2ToIntegerArray2(int t[][])
    {
        int ret[][] = new int[t.length][];
        
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new int[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = t[i][j];
        }
        
        return ret;
    }
    
    double[][] doubleArray2ToDoubleArray2(double t[][])
    {
        double ret[][] = new double[t.length][];
        
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new double[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = t[i][j];
        }
        
        return ret;
    }
    
	// TRANSFORMATION //
	
	int booleanToInteger(boolean t)
	{
		if(t == true)
			return 1;
		else
			return 0;
	}
	
	double booleanToDouble(boolean t)
	{
		if(t == true)
			return 1.0;
		else
			return 0.0;		
	}
	
	String booleanToString(boolean t)
	{
		return Boolean.toString(t);	
	}
	
	boolean integerToBoolean(int t)
	{
		if(t == 0)
			return false;
		else
			return true;
	}
	
	double integerToDouble(int t)
	{
		return (double)t;
	}
	
	String integerToString(int t)
	{
		return Integer.toString(t);
	}
	
	boolean doubleToBoolean(double t)
	{
		if(t == 0)
			return false;
		else
			return true;
	}
	
	int doubleToInteger(double t)
	{
		return (int)t;
	}
	
	String doubleToString(double t)
	{
		return Double.toString(t);
	}
	
	boolean StringToBoolean(String t)
	{
		if(t.equals(""))
			return false;
		else
			return true;
	}
	
	int StringToInteger(String t)
	{
		return t.length();
	}
	
	double StringToDouble(String t)
	{
		return (double)t.length();
	}
	
	// ARRAY TO ARRAY
	int[] booleanArrayToIntegerArray(boolean t[])
	{
		int ret[] = new int[t.length];
		
		for(int i=0;i<t.length;i++)
			ret[i] = booleanToInteger(t[i]);
		
		return ret;
	}
	
	double[] booleanArrayToDoubleArray(boolean t[])
	{
		double ret[] = new double[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = booleanToDouble(t[i]);
		
		return ret;
	}
	
	String[] booleanArrayToStringArray(boolean t[])
	{
		String ret[] = new String[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = booleanToString(t[i]);
		
		return ret;
	}
	
	boolean[] integerArrayToBooleanArray(int t[])
	{
		boolean ret[] = new boolean[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = integerToBoolean(t[i]);
		
		return ret;
	}
	
	double[] integerArrayToDoubleArray(int t[])
	{
		double ret[] = new double[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = integerToDouble(t[i]);
		
		return ret;
	}
	
	String[] integerArrayToStringArray(int t[])
	{
		String ret[] = new String[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = integerToString(t[i]);
		
		return ret;
	}
	
	boolean[] doubleArrayToBooleanArray(double t[])
	{
		boolean ret[] = new boolean[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = doubleToBoolean(t[i]);
		
		return ret;
	}
	
	int[] doubleArrayToIntegerArray(double t[])
	{
		int ret[] = new int[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = doubleToInteger(t[i]);
		
		return ret;
	}
	
	String[] doubleArrayToStringArray(double t[])
	{
		String ret[] = new String[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = doubleToString(t[i]);
		
		return ret;
	}		
	
	boolean[] StringArrayToBooleanArray(String t[])
	{
		boolean ret[] = new boolean[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = StringToBoolean(t[i]);
		
		return ret;
	}
	
	int[] StringArrayToIntegerArray(String t[])
	{
		int ret[] = new int[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = StringToInteger(t[i]);
		
		return ret;
	}
	
	double[] StringArrayToDoubleArray(String t[])
	{
		double ret[] = new double[t.length];
		
		for(int i=0;i<t.length;i++)		
			ret[i] = StringToDouble(t[i]);
		
		return ret;
	}
	

	// ARRAY2 TO ARRAY2
	int[][] booleanArray2ToIntegerArray2(boolean t[][])
	{
		int ret[][] = new int[t.length][];
        
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new int[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = booleanToInteger(t[i][j]);
        }
		
		return ret;
	}
	
	double[][] booleanArray2ToDoubleArray2(boolean t[][])
	{
		double ret[][] = new double[t.length][];
		
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new double[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = booleanToDouble(t[i][j]);
        }
		
		return ret;
	}
	
	boolean[][] integerArray2ToBooleanArray2(int t[][])
	{
		boolean ret[][] = new boolean[t.length][];
		
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new boolean[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = integerToBoolean(t[i][j]);
        }
		
		return ret;
	}
	
	double[][] integerArray2ToDoubleArray2(int t[][])
	{
		double ret[][] = new double[t.length][];
		
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new double[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = integerToDouble(t[i][j]);
        }
		
		return ret;
	}

	
	boolean[][] doubleArray2ToBooleanArray2(double t[][])
	{
		boolean ret[][] = new boolean[t.length][];
		
        for(int i=0;i<t.length;i++)
        {
            ret[i] = new boolean[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = doubleToBoolean(t[i][j]);
        }
		
		return ret;
	}
	
	int[][] doubleArray2ToIntegerArray2(double t[][])
	{
		int ret[][] = new int[t.length][];

        for(int i=0;i<t.length;i++)
        {
            ret[i] = new int[t[i].length];
            for(int j=0;j<t[i].length;j++)
                ret[i][j] = doubleToInteger(t[i][j]);
        }
		
		return ret;
	}
	
	
	//TO STRING
	String booleanArrayToString(boolean t[])
	{
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);	
				
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(booleanToString(t[i]));
				bw.write(",");
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in  booleanArrayToString");
		}		
				
		return sw.toString();
	}
	
	String integerArrayToString(int t[])
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(integerToString(t[i]));
				bw.write(",");
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in integerArrayToString");
		}		
		
		return sw.toString();
	}
	
	String doubleArrayToString(double t[])
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(doubleToString(t[i]));
				bw.write(",");
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in doubleArrayToString");
		}		
		
		return sw.toString();
	}	
	
	String StringArrayToString(String t[])
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(t[i]);
				bw.write(",");
				//				bw.newLine();
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in StringArrayToString");
		}		
		
		return sw.toString();
	}	
	
	String booleanArray2ToString(boolean t[][])
	{
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(booleanArrayToString(t[i]));
				bw.newLine();
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in  booleanArrayToString");
		}		
		
		return sw.toString();
	}
	
	String integerArray2ToString(int t[][])
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(integerArrayToString(t[i]));
				bw.newLine();
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in integerArrayToString");
		}		
		
		return sw.toString();
	}
	
	String doubleArray2ToString(double t[][])
	{
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);	
		
		try{
			for(int i=0;i<t.length;i++)
			{
				bw.write(doubleArrayToString(t[i]));
				bw.newLine();
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in doubleArrayToString");
		}		
		
		return sw.toString();
	}		
	
	//TO STRING[]
	String[] booleanArray2ToStringArray(boolean t[][])
	{
		String ret[] = new String[t.length];

		for(int i=0;i<t.length;i++)
			ret[i] = booleanArrayToString(t[i]);

		return ret;
	}
	
	String[] integerArray2ToStringArray(int t[][])
	{
		String ret[] = new String[t.length];
		
		for(int i=0;i<t.length;i++)
			ret[i] = integerArrayToString(t[i]);
		
		return ret;
	}
	
	String[] doubleArray2ToStringArray(double t[][])
	{
		String ret[] = new String[t.length];
		
		for(int i=0;i<t.length;i++)
			ret[i] = doubleArrayToString(t[i]);
		
		return ret;
	}	
	
	
	//[][] TO []
	boolean[] booleanArray2ToBooleanArray(boolean t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		boolean ret[] = new boolean[count];
		
        count=0;
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = t[i][j];
		
		return ret;				
	}
	
	int[] booleanArray2ToIntegerArray(boolean t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		int ret[] = new int[count];
		
        count=0;        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = booleanToInteger(t[i][j]);
		
		return ret;				
	}
	
	double[] booleanArray2ToDoubleArray(boolean t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		double ret[] = new double[count];
		
        count=0;        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = booleanToDouble(t[i][j]);
		
		return ret;				
	}	
	
	boolean[] integerArray2ToBooleanArray(int t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		boolean ret[] = new boolean[count];
		
        count=0;         
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = integerToBoolean(t[i][j]);
		
		return ret;				
	}
	
	int[] integerArray2ToIntegerArray(int t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		int ret[] = new int[count];
		
        count=0;         
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = t[i][j];
		
		return ret;				
	}
	
	double[] integerArray2ToDoubleArray(int t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		double ret[] = new double[count];
		
        count=0;         
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = integerToDouble(t[i][j]);
		
		return ret;				
	}	
	
	boolean[] doubleArray2ToBooleanArray(double t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		boolean ret[] = new boolean[count];
		
        count=0;         
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = doubleToBoolean(t[i][j]);
		
		return ret;				
	}
	
	int[] doubleArray2ToIntegerArray(double t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		int ret[] = new int[count];
		
        count=0;         
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = doubleToInteger(t[i][j]);
		
		return ret;				
	}
	
	double[] doubleArray2ToDoubleArray(double t[][])
	{
        int count=0;
        
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
                count++;
        
		double ret[] = new double[count];
		
        count=0;         
		for(int i=0;i<t.length;i++)
			for(int j=0;j<t[i].length;j++)
				ret[count++] = t[i][j];
		
		return ret;				
	}
	
	// TO ARRAY
	boolean[] booleanToBooleanArray(boolean t)
	{
		boolean ret[] = new boolean[1];
		
		ret[0] = t;
		
		return ret;
	}
	
	int[] booleanToIntegerArray(boolean t)
	{
		int ret[] = new int[1];
		
		ret[0] = booleanToInteger(t);
		
		return ret;
	}
	
	double[] booleanToDoubleArray(boolean t)
	{
		double ret[] = new double[1];
		
		ret[0] = booleanToDouble(t);
		
		return ret;
	}
	
	String[] booleanToStringArray(boolean t)
	{
		String ret[] = new String[1];
		
		ret[0] = booleanToString(t);
		
		return ret;
	}
	
	boolean[] integerToBooleanArray(int t)
	{
		boolean ret[] = new boolean[1];
		
		ret[0] = integerToBoolean(t);
		
		return ret;
	}
	
	int[] integerToIntegerArray(int t)
	{
		int ret[] = new int[1];
		
		ret[0] = t;
		
		return ret;
	}
	
	double[] integerToDoubleArray(int t)
	{
		double ret[] = new double[1];
		
		ret[0] = integerToDouble(t);
		
		return ret;
	}
	
	String[] integerToStringArray(int t)
	{
		String ret[] = new String[1];
		
		ret[0] = integerToString(t);
		
		return ret;
	}
	
	boolean[] doubleToBooleanArray(double t)
	{
		boolean ret[] = new boolean[1];
		
		ret[0] = doubleToBoolean(t);
		
		return ret;
	}
	
	int[] doubleToIntegerArray(double t)
	{
		int ret[] = new int[1];
		
		ret[0] = doubleToInteger(t);
		
		return ret;
	}
	
	double[] doubleToDoubleArray(double t)
	{
		double ret[] = new double[1];
		
		ret[0] = t;
		
		return ret;
	}
	
	String[] doubleToStringArray(double t)
	{
		String ret[] = new String[1];
		
		ret[0] = doubleToString(t);
		
		return ret;
	}		
	
	boolean[] StringToBooleanArray(String t)
	{
		boolean ret[] = new boolean[1];
		
		ret[0] = StringToBoolean(t);
		
		return ret;
	}
	
	int[] StringToIntegerArray(String t)
	{
		int ret[] = new int[1];
		
		ret[0] = StringToInteger(t);
		
		return ret;
	}
	
	double[] StringToDoubleArray(String t)
	{
		double ret[] = new double[1];
		
		ret[0] = StringToDouble(t);
		
		return ret;
	}
	
	String[] StringToStringArray(String t)
	{
		String ret[] = new String[1];
		
		ret[0] = t;
		
		return ret;
	}
	
	//ARRAY TO ARRAY2
	boolean[][] booleanArrayToBooleanArray2(boolean t[])
	{
		boolean ret[][] = new boolean[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = t[i];
		
		return ret;
	}
	
	int[][] booleanArrayToIntegerArray2(boolean t[])
	{
		int ret[][] = new int[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = booleanToInteger(t[i]);
		
		return ret;
	}
	
	double[][] booleanArrayToDoubleArray2(boolean t[])
	{
		double ret[][] = new double[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = booleanToDouble(t[i]);
		
		return ret;
	}
	
	boolean[][] integerArrayToBooleanArray2(int t[])
	{
		boolean ret[][] = new boolean[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = integerToBoolean(t[i]);
		
		return ret;
	}
	
	int[][] integerArrayToIntegerArray2(int t[])
	{
		int ret[][] = new int[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = t[i];
		
		return ret;
	}
	
	double[][] integerArrayToDoubleArray2(int t[])
	{
		double ret[][] = new double[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = integerToDouble(t[i]);
		
		return ret;
	}
	
	boolean[][] doubleArrayToBooleanArray2(double t[])
	{
		boolean ret[][] = new boolean[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = doubleToBoolean(t[i]);
		
		return ret;
	}
	
	int[][] doubleArrayToIntegerArray2(double t[])
	{
		int ret[][] = new int[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = doubleToInteger(t[i]);
		
		return ret;
	}
	
	double[][] doubleArrayToDoubleArray2(double t[])
	{
		double ret[][] = new double[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = t[i];
		
		return ret;
	}		
	
	boolean[][] StringArrayToBooleanArray2(String t[])
	{
		boolean ret[][] = new boolean[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = StringToBoolean(t[i]);
		
		return ret;
	}
	
	int[][] StringArrayToIntegerArray2(String t[])
	{
		int ret[][] = new int[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = StringToInteger(t[i]);
		
		return ret;
	}
	
	double[][] StringArrayToDoubleArray2(String t[])
	{
		double ret[][] = new double[1][t.length];
		
		for(int i=0;i<t.length;i++)
			ret[0][i] = StringToDouble(t[i]);
		
		return ret;
	}
	
	
	// TO ARRAY2
	boolean[][] booleanToBooleanArray2(boolean t)
	{
		boolean ret[][] = new boolean[1][1];
		
		ret[0][0] = t;
		
		return ret;
	}
	
	int[][] booleanToIntegerArray2(boolean t)
	{
		int ret[][] = new int[1][1];
		
		ret[0][0] = booleanToInteger(t);
		
		return ret;
	}
	
	double[][] booleanToDoubleArray2(boolean t)
	{
		double ret[][] = new double[1][1];
		
		ret[0][0] = booleanToDouble(t);
		
		return ret;
	}
	
	boolean[][] integerToBooleanArray2(int t)
	{
		boolean ret[][] = new boolean[1][1];
		
		ret[0][0] = integerToBoolean(t);
		
		return ret;
	}
	
	int[][] integerToIntegerArray2(int t)
	{
		int ret[][] = new int[1][1];
		
		ret[0][0] = t;
		
		return ret;
	}
	
	double[][] integerToDoubleArray2(int t)
	{
		double ret[][] = new double[1][1];
		
		ret[0][0] = integerToDouble(t);
		
		return ret;
	}
	
	boolean[][] doubleToBooleanArray2(double t)
	{
		boolean ret[][] = new boolean[1][1];
		
		ret[0][0] = doubleToBoolean(t);
		
		return ret;
	}
	
	int[][] doubleToIntegerArray2(double t)
	{
		int ret[][] = new int[1][1];
		
		ret[0][0] = doubleToInteger(t);
		
		return ret;
	}
	
	double[][] doubleToDoubleArray2(double t)
	{
		double ret[][] = new double[1][1];
		
		ret[0][0] = t;
		
		return ret;
	}		
	
	boolean[][] StringToBooleanArray2(String t)
	{
		boolean ret[][] = new boolean[1][1];
		
		ret[0][0] = StringToBoolean(t);
		
		return ret;
	}
	
	int[][] StringToIntegerArray2(String t)
	{
		int ret[][] = new int[1][1];
		
		ret[0][0] = StringToInteger(t);
		
		return ret;
	}
	
	double[][] StringToDoubleArray2(String t)
	{
		double ret[][] = new double[1][1];
		
		ret[0][0] = StringToDouble(t);
		
		return ret;
	}
	
	// ARRAY TO SINGLE
	boolean booleanArrayToBoolean(boolean t[])
	{
        if(t.length == 0)
            return false;
        else
            return t[0];
	}
	
	int booleanArrayToInteger(boolean t[])
	{
        if(t.length == 0)
            return 0;
        else
            return booleanToInteger(t[0]);
	}
	
	double booleanArrayToDouble(boolean t[])
	{
        if(t.length == 0)
            return 0.0;
        else
            return booleanToDouble(t[0]);
	}
	
	boolean integerArrayToBoolean(int t[])
	{
        if(t.length == 0)
            return false;
        else
            return integerToBoolean(t[0]);
	}
	
	int integerArrayToInteger(int t[])
	{
        if(t.length == 0)
            return 0;
        else
            return t[0];
	}
	
	double integerArrayToDouble(int t[])
	{
        if(t.length == 0)
            return 0.0;
        else
            return integerToDouble(t[0]);
	}	
	
	boolean doubleArrayToBoolean(double t[])
	{
        if(t.length == 0)
            return false;
        else
            return doubleToBoolean(t[0]);
	}
	
	int doubleArrayToInteger(double t[])
	{
        if(t.length == 0)
            return 0;
        else
            return doubleToInteger(t[0]);
	}
	
	double doubleArrayToDouble(double t[])
	{
        if(t.length == 0)
            return 0.0;
        else
            return t[0];
	}
	
	boolean StringArrayToBoolean(String t[])
	{
        if(t.length == 0)
            return false;
        else
            return StringToBoolean(t[0]);
	}
	
	int StringArrayToInteger(String t[])
	{
        if(t.length == 0)
            return 0;
        else
            return StringToInteger(t[0]);
	}
	
	double StringArrayToDouble(String t[])
	{
        if(t.length == 0)
            return 0.0;
        else
            return StringToDouble(t[0]);
	}
	
	// ARRAY2 TO SINGLE
	boolean booleanArray2ToBoolean(boolean t[][])
	{
        if(t.length == 0)
            return false;
        else
            if(t[0].length == 0)
                return false;
            else
                return t[0][0];
	}
	
	int booleanArray2ToInteger(boolean t[][])
	{
        if(t.length == 0)
            return 0;
        else
            if(t[0].length == 0)
                return 0;
            else
                return booleanToInteger(t[0][0]);
	}
	
	double booleanArray2ToDouble(boolean t[][])
	{
        if(t.length == 0)
            return 0.0;
        else
            if(t[0].length == 0)
                return 0.0;
            else
                return booleanToDouble(t[0][0]);
	}
	
	boolean integerArray2ToBoolean(int t[][])
	{
        if(t.length == 0)
            return false;
        else
            if(t[0].length == 0)
                return false;
            else
                return integerToBoolean(t[0][0]);
	}
	
	int integerArray2ToInteger(int t[][])
	{
        if(t.length == 0)
            return 0;
        else
            if(t[0].length == 0)
                return 0;
            else
                return t[0][0];
	}
	
	double integerArray2ToDouble(int t[][])
	{
        if(t.length == 0)
            return 0.0;
        else
            if(t[0].length == 0)
                return 0.0;
            else
                return integerToDouble(t[0][0]);
	}	
	
	boolean doubleArray2ToBoolean(double t[][])
	{
        if(t.length == 0)
            return false;
        else
            if(t[0].length == 0)
                return false;
            else
                return doubleToBoolean(t[0][0]);
	}
	
	int doubleArray2ToInteger(double t[][])
	{
        if(t.length == 0)
            return 0;
        else
            if(t[0].length == 0)
                return 0;
            else
                return doubleToInteger(t[0][0]);
	}
	
	double doubleArray2ToDouble(double t[][])
	{
        if(t.length == 0)
            return 0.0;
        else
            if(t[0].length == 0)
                return 0.0;
            else
                return t[0][0];
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
