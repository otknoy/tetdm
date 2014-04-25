//
// Core Program for TETDM 
// tetdm1024.java Version 0.56
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.io.*;

class tetdm1024
{
	public static void main(String args[])
	{
		exe_tetdm();
	}
	
	public static void exe_tetdm()
	{
		String command0[] = {"java","-Dfile.encoding=Shift-JIS","-Xmx1024m","-jar","TETDM.jar"};		
		
//		String command0[] = {"/bin/sh","-c","java","-Xmx512m","-jar","TETDM.jar"};
//		String commandwin[] = {"cmd","/c","java","-Xmx512m","-jar","TETDM.jar"};
//		
//		if(windows)
//			EXECUTE.exeProcess(commandwin);
//		else

		exeProcess(command0);
	}
	
	public static void exeProcess(String command[])
	{
		//Display commands
		for(int i=0;i<command.length;i++)
			System.out.print(command[i]+" ");
		System.out.println();
		
		try				//Execute Process
		{
			Runtime rt = Runtime.getRuntime(); // Without using constructor
			Process ps = rt.exec( command );
			InputStreamReader isr = new InputStreamReader( ps.getInputStream() );
			BufferedReader br = new BufferedReader( isr );
			StringBuffer out = new StringBuffer();			
			String line;
			
			while ((line = br.readLine()) != null) //data withdraw from the buffer
				;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
}
