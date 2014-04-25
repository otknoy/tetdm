//
// Core Program for TETDM 
// FILEIO.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED. 
// This program is provided under the license.
//


package source;

import java.io.*;
import java.text.*;

public class FILEIO
{
	public static int line_count(File filename)		//Count lines
	{
		String ss;
		int number = 0;

		try
		{
			BufferedReader br1 = new BufferedReader(new FileReader(filename));	//Count lines
			while((ss = br1.readLine()) != null)
				number++;
			br1.close();
		}
		catch(Exception e){
			System.out.println("File Read ERROR : Number count");
		}
		return number;
	}

	public static void TextFileRead(File filename, String cut_text[], int max_num)		//Textfile, read lines
	{
		try
		{
			BufferedReader br1 = new BufferedReader(new FileReader(filename));
			for(int i=0;i<max_num;i++)
				cut_text[i] = br1.readLine();
			br1.close();
		}
		catch(Exception e){
			System.out.println("File Reading ERROR Text data");
		}
	}

	public static void TextFileReadCode(File filename, String cut_text[], int max_num)		//Textfile, read lines
	{
		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "JISAutoDetect"));
			for(int i=0;i<max_num;i++)
				cut_text[i] = br1.readLine();
			br1.close();
		}
		catch(Exception e){
			System.out.println("File Reading ERROR Text data");
		}
	}

	public static void TextFileLineRead(File filename, String cut_text)		//Textfile, read one line only
	{
		try
		{
			BufferedReader br1 = new BufferedReader(new FileReader(filename));
			cut_text = br1.readLine();
			br1.close();
		}
		catch(Exception e){
			System.out.println("File Reading ERROR Text data");
		}
	}

	public static String TextFileAllRead(File filename)		//Textfile, read all
	{
		String ss,text="";
		try
		{
			BufferedReader br1 = new BufferedReader(new FileReader(filename));

			StringWriter sw1 = new StringWriter();
			BufferedWriter bw1 = new BufferedWriter(sw1);
			while((ss = br1.readLine()) != null)
			{
				bw1.write(ss);
				bw1.newLine();
			}
			br1.close();
			bw1.flush();
			sw1.flush();
			text = sw1.toString();
			bw1.close();
			sw1.close();
		}
		catch(Exception e){
			System.out.println("File Reading ERROR Text data");
		}
		return text;
	}

	public static String TextFileAllReadCode(File filename)		//Textfile, read all(Recommend to use)
	{
		String ss,text="";
		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "JISAutoDetect"));
			StringWriter sw1 = new StringWriter();
			BufferedWriter bw1 = new BufferedWriter(sw1);
			while((ss = br1.readLine()) != null)
			{
				bw1.write(ss);
				bw1.newLine();
			}
			br1.close();
			bw1.flush();
			sw1.flush();
			text = sw1.toString();
			bw1.close();
			sw1.close();
		}
		catch(Exception e){
			System.out.println("Text Reading ERROR "+filename.getName()+" data");
		}
		return text;
	}
	
	public static String[] TextFileAllReadCodeArray(File filename)		//Textfile, read all(Recommend to use)
	{
		String ss;		
		int number = 0;
		
		try
		{
			BufferedReader br1 = new BufferedReader(new FileReader(filename));	//Count lines
			while((ss = br1.readLine()) != null)
				number++;
			br1.close();
		}
		catch(Exception e){
			System.out.println("File Read ERROR : Number count");
		}	
		
		String dataStrings[] = new String[number];
		
		number = 0;
		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "JISAutoDetect"));

			while((ss = br1.readLine()) != null)
				dataStrings[number++] = ss;

			br1.close();
		}
		catch(Exception e){
			System.out.println("Text Reading ERROR "+filename.getName()+" data");
		}
		return dataStrings;
	}	

	public static void TextFileWrite(File filename, String cut_text[], int max_num)		//Textfile, write lines
	{
		try
		{
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(filename));
			for(int i=0;i<max_num;i++)
			{
				bw1.write(cut_text[i]);
				bw1.newLine();
			}
			bw1.close();
		}
		catch(Exception e){
			System.out.println("File Writing ERROR Text data");
		}
	}

	public static void TextFileWrite2(File filename, String cut_text)		//Textfile, write all
	{
		try
		{
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(filename));

			bw1.write(cut_text);
			bw1.close();
		}
		catch(Exception e){
			System.out.println("File Writing ERROR Text data");
		}
	}

	public static void TextFileWriteCode(File filename, String text, String encoding)		//Textfile, write all
	{
		try
		{
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), encoding));
			//Output byte stream

			bw1.write(text);
			bw1.close();
		}
		catch(Exception e){
			System.out.println("File Writing ERROR Text data");
		}
	}

	//// Followings are text processing libraries

	public static int CountTags(String text, String tag)		//Count number of lines including tags
	{
		int n=0;
		int index;
		
		String ss;
		try
		{
			BufferedReader br1 = new BufferedReader(new StringReader(text));

			while((ss = br1.readLine()) != null)
			{
				index = -1;
				while((index = ss.indexOf(tag,index+1)) >= 0)
				{
					n++;
				}
			}
		}
		catch(Exception e){
			System.out.println("Text Reading ERROR "+text+" data");
		}
		return n+1;
	}
/*
	public static String CutTags(String text, String tag)		//delete lines including tags
	{
		String ss,text2="";
		try
		{
			BufferedReader br1 = new BufferedReader(new StringReader(text));
			StringWriter sw1 = new StringWriter();
			BufferedWriter bw1 = new BufferedWriter(sw1);
			while((ss = br1.readLine()) != null)
			{
				if(ss.indexOf(tag) == -1)
					bw1.write(ss);
				bw1.newLine();
			}
			br1.close();
			bw1.flush();
			sw1.flush();
			text2 = sw1.toString();
			bw1.close();
			sw1.close();
		}
		catch(Exception e){
			System.out.println("Text Reading ERROR Text data");
		}
		return text2;
	}
*/
	public static int CutText(String text, String cut_text[], String tag, boolean leftTag)		//divide a text by tag(line including a tag)//return number of tags
	{
		BufferedReader br1 = new BufferedReader(new StringReader(text));
		StringWriter sw1 = new StringWriter();
		BufferedWriter bw1 = new BufferedWriter(sw1);		
		
		int n=0;
		int index;
		String ss="";
		String subString;
		boolean reading;
		
		try
		{
			reading = true;
			while(true)
			{
				if(reading)
					ss = br1.readLine();
				
				reading = true;
				
				if(ss == null)
					break;
				
				index = ss.indexOf(tag);
				if(index == -1)
				{
					bw1.write(ss);
					bw1.newLine();
				}
				else
				{
					subString = ss.substring(0,index);
					bw1.write(subString);
					bw1.flush();
					cut_text[n++] = sw1.toString();
					sw1.getBuffer().delete(0,sw1.getBuffer().length());
					
                    if(leftTag == true)
                        bw1.write(tag);

                    subString = ss.substring(index+tag.length(),ss.length());
					
//					System.out.println("substlength="+subString.length());
					
					if(subString.length() > 0)
					{
						ss = subString;
						reading = false;
					}
				}
				
				
			}
			bw1.flush();
			cut_text[n++] = sw1.toString();
			br1.close();
		}
		catch(Exception e){
			System.out.println("Text Reading ERROR Text data");
		}
		return n;
	}

	public static String print_double(double num)
	{
		DecimalFormat df = new DecimalFormat();
		df.applyLocalizedPattern("#####.##");

		return df.format(num);
	}

	public static String print_pattern(double num, String pat)
	{
		DecimalFormat df = new DecimalFormat();
		df.applyLocalizedPattern(pat);

		return df.format(num);
	}
	
	/* COPYED FROM ModuleData except for filename matching */	
	static String[] check_dir(File dir)
	{
		File dir_list[] = dir.listFiles();
		String filename_list[];
		String filename;
		
		int count=0;
		for(int i=0;i<dir_list.length;i++)
			if(dir_list[i].getName().matches("^[A-Za-z0-9][A-Za-z0-9_\\.]+"))			
				count++;
		
		filename_list = new String[count];
		
		count=0;		
		for(int i=0;i<dir_list.length;i++)
		{
			filename = dir_list[i].getName();
			
			if(filename.matches("^[A-Za-z0-9][A-Za-z0-9_\\.]+"))
			{
				filename_list[count] = filename; //filename.substring(0,filename.length());
				count++;
			}
		}
		
		return filename_list;
	}	
	
	public static String makeOne(File fileDirectory, String absolutePath, String defaultCode, String tag)
	{
		String fileNames[];
		fileNames = check_dir(fileDirectory);
		
		StringWriter sw = new StringWriter();			//For words
		BufferedWriter bw = new BufferedWriter(sw);
		
		if(fileNames.length == 0)
		{
			System.out.println("No files in directory "+fileDirectory.getName());
			System.exit(0);			
		}
		
		try{		
			for(int i=0;i<fileNames.length;i++)
			{
				bw.write(TextFileAllReadCode(new File(fileDirectory + File.separator + fileNames[i])));
				if(i != fileNames.length - 1)
				{
					bw.newLine();
					bw.newLine();
					bw.write(tag);
					bw.newLine();
					bw.newLine();
				}
			}
			bw.flush();
		}
		catch(Exception e){
			System.out.println("writing ERROR in FILEIO.java");
		}
		
		TextFileWriteCode(new File(absolutePath + "text" + File.separator + fileDirectory.getName()+".txt"), sw.toString(), defaultCode);
		
		return absolutePath + "text" + File.separator + fileDirectory.getName()+".txt";
	}	
}
