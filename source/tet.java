//
// Core Program for TETDM 
// tet.java Version 0.56
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.io.*;
import java.util.*;

class tet
{
	public static void main(String args[])
	{
		String inputFileName = "";				
		String absolutePath;

		if(args.length > 0)
			inputFileName = args[0];
		else
			inputFileName = "text" + File.separator + "empty.txt";
/*		
		if(args.length > 1)
		{
			if(Integer.parseInt(args[1]) == 1)
				english = true;
			else
				english = false;
		}
*/

		if(args.length > 2)
			absolutePath = args[2];
		else			
			absolutePath = new File(".").getAbsoluteFile().getParent() + File.separator;
		
		MainFrame frm = new MainFrame("Total Environment for Text Data Mining (TETDM-0.56)", inputFileName, absolutePath);
	}
}
