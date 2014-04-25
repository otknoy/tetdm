//
// Core Program for TETDM 
// EXECUTE.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import java.io.*;

public class EXECUTE
{
	public static int exeProcess(String command[])
	{
		//Display commands
		for(int i=0;i<command.length;i++)
			System.out.print(command[i]+" ");
		System.out.println();

		int returnValue = -1;
		Process ps = null;
		
		try				//Execute Process
		{
			Runtime rt = Runtime.getRuntime(); // Without using constructor
			ps = rt.exec( command );				
			InputStreamReader isr = new InputStreamReader( ps.getInputStream() );
			BufferedReader br = new BufferedReader( isr );
//			StringBuffer out = new StringBuffer();			
			String line;
			
			while ((line = br.readLine()) != null) //data withdraw from the buffer
				;
					
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try
		{
			returnValue = ps.exitValue();
		}
		catch(Exception e){
			returnValue = 0;	//process is being executed
		}
		
		return returnValue;		//If return value != 0, command not found
	}
	
	public static String getExeResult(String command[])
	{
		//Display commands
		for(int i=0;i<command.length;i++)
			System.out.print(command[i]+" ");
		System.out.println();
		
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);			
		
		try				//Execute Process
		{
			Runtime rt = Runtime.getRuntime(); // Without using constructor
			Process ps = rt.exec( command );
			InputStreamReader isr = new InputStreamReader( ps.getInputStream() );
			BufferedReader br = new BufferedReader( isr );
//			StringBuffer out = new StringBuffer();			
			String line;
			
			while ((line = br.readLine()) != null) //data withdraw from the buffer
				bw.write(line);
			
			bw.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return sw.toString();
	}	
/*
	public static void filecopy(String filename1, String filename2)
	{
		String command0[] = {"cp", filename1, filename2};

		exeProcess(command0);
	}
*/
	/**
	 * File copy with binary mode
	 * @param filename1 copy source
	 * @param filename2 copy target
	 */
	public static void filecopy(String filename1, String filename2)
	{
		
		BufferedInputStream src=null;
		BufferedOutputStream trg=null;
		
		try {
			src = new BufferedInputStream(new FileInputStream(filename1));
		} catch (FileNotFoundException e) {
			System.out.println("input file "+filename1+" not found.");
			e.printStackTrace();
		}
		
		try {
			trg = new BufferedOutputStream(new FileOutputStream(filename2));
		} catch (IOException e) {
			System.out.println("Error while opening file "+filename2+".");
			e.printStackTrace();
		}
		
		try {
			int val=0;
			while((val = src.read()) >= 0){
				trg.write(val);
			}
		} catch (IOException e) {
			System.out.println("Error while copying "+filename1+" to "+filename2+".");
			e.printStackTrace();
		}
		
		try {
			src.close();
		} catch (IOException e) {
			System.out.println("input file "+filename1+" has not closed.");
			e.printStackTrace();
		}
		try {
			trg.close();
		} catch (IOException e) {
			System.out.println("input file "+filename2+" has not closed.");
			e.printStackTrace();
		}
	}
	
   	public static void filecopy(String filename1, String filename2, String encoding)
	{
		
		BufferedReader src=null;
		BufferedWriter trg=null;
		
		try {
			src = new BufferedReader(new InputStreamReader(new FileInputStream(filename1), "JISAutoDetect"));
		} catch (IOException e) {
			System.out.println("input file "+filename1+" not found.");
			e.printStackTrace();
		}
		
		try {
			trg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename2), encoding));
//			trg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename2), "SHIFT-JIS"));//Igo version
		} catch (IOException e) {
			System.out.println("Error while opening file "+filename2+".");
			e.printStackTrace();
		}
		
		try {
			int val=0;
			while((val = src.read()) >= 0){
				trg.write(val);
			}
		} catch (IOException e) {
			System.out.println("Error while copying "+filename1+" to "+filename2+".");
			e.printStackTrace();
		}
		
		try {
			src.close();
		} catch (IOException e) {
			System.out.println("input file "+filename1+" has not closed.");
			e.printStackTrace();
		}
		try {
			trg.close();
		} catch (IOException e) {
			System.out.println("input file "+filename2+" has not closed.");
			e.printStackTrace();
		}
	}
	
	
   	public static void fileConnect(String filename1, String filename2, String filename3, String encoding)
	{
		
		BufferedReader src1=null;
		BufferedReader src2=null;		
		BufferedWriter trg=null;
		
		try {
			src1 = new BufferedReader(new InputStreamReader(new FileInputStream(filename1), "JISAutoDetect"));
		} catch (IOException e) {
			System.out.println("input file "+filename1+" not found.");
			e.printStackTrace();
		}
		
		try {
			src2 = new BufferedReader(new InputStreamReader(new FileInputStream(filename2), "JISAutoDetect"));			
		} catch (IOException e) {
			System.out.println("input file "+filename2+" not found.");			
			e.printStackTrace();
		}		
		
		try {
			trg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename3), encoding));
			//			trg = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename2), "SHIFT-JIS"));//Igo version
		} catch (IOException e) {
			System.out.println("Error while opening file "+filename3+".");
			e.printStackTrace();
		}
		
		try {
			int val=0;
			while((val = src1.read()) >= 0){
				trg.write(val);
			}
		} catch (IOException e) {
			System.out.println("Error while copying "+filename1+" to "+filename3+".");
			e.printStackTrace();
		}
		
		try {
			int val=0;
			while((val = src2.read()) >= 0){
				trg.write(val);
			}
		} catch (IOException e) {
			System.out.println("Error while copying "+filename2+" to "+filename3+".");
			e.printStackTrace();
		}		
		
		try {
			src1.close();
		} catch (IOException e) {
			System.out.println("input file "+filename1+" has not closed.");
			e.printStackTrace();
		}
		try {
			src2.close();
		} catch (IOException e) {
			System.out.println("input file "+filename2+" has not closed.");
			e.printStackTrace();
		}		
		try {
			trg.close();
		} catch (IOException e) {
			System.out.println("input file "+filename3+" has not closed.");
			e.printStackTrace();
		}
	}	
	
    
	/*
	public static void exe_perl(String absolutePath)
	{
		String command0[] = {"perl","toeuc2.pl",absolutePath+"text/urashima.txt"};
		exeProcess(command0);
	}
	*/
//	public static String getChasenPath()
//	{
//		String command0[] = {"/usr/bin/which","chasen"};
//		
//		return EXECUTE.getExeResult(command0);
//	}

	public static void exe_chasen(boolean chasenexe, String sourcefile, String chaf, boolean windows, String chasenPath)
	{
		File chafile = new File(chaf);

		if(chafile.exists() == false)
			chasenexe = true;

		if(chasenexe)
		{
			//			String command0[] = {"/bin/sh","-c","chasen "+sourcefile+" > "+chafile};
			String commandwin[] = {"cmd","/c","chasen "+sourcefile+" > "+chafile};
			
			if(windows)
				exeProcess(commandwin);
			else
			{
				String command0[] = {"/bin/sh","-c",chasenPath+"/chasen "+sourcefile+" > "+chafile};			
				int returnValue = exeProcess(command0);
				System.out.println("COMMAND RESULT = "+returnValue);
				
				if(returnValue != 0)
				{
					chasenPath = "/opt/local/bin";
					String command1[] = {"/bin/sh","-c",chasenPath+"/chasen "+sourcefile+" > "+chafile};					
					returnValue = exeProcess(command1);
					System.out.println("COMMAND RESULT = "+returnValue);
				}
                
				if(returnValue != 0)
				{
					chasenPath = "/usr/local/bin";
					String command1[] = {"/bin/sh","-c",chasenPath+"/chasen "+sourcefile+" > "+chafile};
					returnValue = exeProcess(command1);
					System.out.println("COMMAND RESULT = "+returnValue);
				}
				
				if(returnValue != 0)
				{
					System.out.println("COMMAND chasen NOT FOUND!! Please install chasen.");
				}
			}
		}
	}
    
	public static void exe_igo(boolean chasenexe, String sourcefile, String chaf, boolean windows,
                               String chasenPath, String dictionaryPath)
	{
		File chafile = new File(chaf);
        
		if(chafile.exists() == false)
			chasenexe = true;
        
		if(chasenexe)
		{
			String commandwin[] = {"cmd","/c","java -Dfile.encoding=SHIFT_JIS -cp "+chasenPath+"igo-0.4.5.jar net.reduls.igo.bin.Igo "+dictionaryPath+"/ipadic"+" < "+sourcefile+" > "+chafile};
			
			if(windows)
				exeProcess(commandwin);
			else
			{
                int returnValue=0;
                String command1[] = {"/bin/sh","-c","java -Dfile.encoding=SHIFT_JIS -cp "+chasenPath+"igo-0.4.5.jar net.reduls.igo.bin.Igo "+dictionaryPath+"/ipadic"+" < "+sourcefile+" > "+chafile};
					returnValue = exeProcess(command1);
					System.out.println("COMMAND RESULT = "+returnValue);
				
				if(returnValue != 0)
				{
					System.out.println("COMMAND chasen NOT FOUND!! Please install Igo.");
				}
			}
		}
	}
	
	public static void createDictionaryByIgo(boolean windows, String chasenPath, String dictionaryPath)
	{
		String commandwin[] = {"cmd","/c","java -Dfile.encoding=SHIFT_JIS -Xmx512m -cp "+chasenPath+"igo-0.4.5.jar net.reduls.igo.bin.BuildDic "+dictionaryPath+"/ipadic "+dictionaryPath+"/mecab-ipadic-2.7.0-20070801 EUC-JP"};
		String command1[] = {"/bin/sh","-c","java -Dfile.encoding=SHIFT_JIS -Xmx512m -cp "+chasenPath+"igo-0.4.5.jar net.reduls.igo.bin.BuildDic "+dictionaryPath+"/ipadic "+dictionaryPath+"/mecab-ipadic-2.7.0-20070801 EUC-JP"};		
			
		if(windows)
			exeProcess(commandwin);
		else
			exeProcess(command1);
	}	
	
	public static void exe_stemming(String sourcefile, String stemf, boolean windows)
	{
		File stemfile = new File(stemf);

		String command0[] = {"/bin/sh","-c","porter "+sourcefile+" > "+stemfile};
		String commandwin[] = {"cmd","/c","porter "+sourcefile+" > "+stemfile};
		
		if(windows)
			exeProcess(commandwin);
		else
			exeProcess(command0);
	}	
}
