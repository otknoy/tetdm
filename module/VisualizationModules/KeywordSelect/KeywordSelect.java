//
// Visuzalization module for TETDM
// KeywordSelect.java Version 0.45
// Copyright(C) 2012-2013 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.KeywordSelect;

import source.*;
import source.TextData.*;
import source.Utility.*;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

//executeOthersByClick();
//repaintOthersByClick();

public class KeywordSelect extends FocusCheckBox
{    
	public KeywordSelect()
	{
		setModuleID(1102);
		dataNumbers = new int[]{0,1,0,1,    // b,i,d,S	String 1+
            0,0,1,0,    // bA,iA,dA,SA					doubleArray 1+
            0,0,0};     // bA2,iA2,dA2
	}
    
	public void initializeData()
    {
        addSetButton = false;
        displayValues = false;
        executeAfterCheck = true;
        maxLength = 100;
		boxNumber = 2;        
        lineNumber = text.keywordNumber;
        focusSets = text.focus.focusKeywords;
        
        initBoxData();
        createInitialBox();
    }
    
    public String[] createWords()
    {
        String words[];
        
        words = new String[lineNumber];
        for(int i=0;i<lineNumber;i++)
            words[i] = text.keyword[i].word;
        
        return words;
    }
    
    public void createInitialBox()
    {
		double value[] = new double[lineNumber];
        
		for(int i=0;i<lineNumber;i++)
			value[i] = lineNumber-i;
        checkBoxPanel[0].setValue(value);
        checkBoxPanel[0].setWords(createWords(), maxLength);
        
		for(int i=0;i<lineNumber;i++)
			value[i] = text.keyword[i].frequency;
        checkBoxPanel[1].setValue(value);
        checkBoxPanel[1].setWords(createWords(), maxLength);
        
        if(isMenuInJapanese())
        {
            checkBoxPanel[0].setLabel(inJapanese[4]);    //"Appearance"
            checkBoxPanel[1].setLabel(inJapanese[5]);    //"Frequency"
        }
        else
        {
            checkBoxPanel[0].setLabel("Appearance");    //"Appearance"
            checkBoxPanel[1].setLabel("Frequency");    //"Frequency"
        }
    }
}
