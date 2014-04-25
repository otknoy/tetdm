//
// Core Program for TETDM 
// Makecha.java Version 0.44
// Copyright(C) 2009-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;



import java.io.*;
import java.util.*;

public class Makecha
{	
	String cha_filename;
	String stem_filename;	
	String write_text;
	
	String DataInJapanese[];	

	Makecha(String sourcefile, boolean windows)
	{
		cha_filename = sourcefile + ".cha";
		stem_filename = sourcefile + ".stem";		
	
		EXECUTE.exe_stemming(sourcefile, stem_filename, windows);
	
		write_text = read_text(sourcefile, stem_filename);
		FILEIO.TextFileWrite2(new File(cha_filename), write_text);
	}

	Makecha(String sourcefile)//NOT STEMMING
	{
		cha_filename = sourcefile + ".cha";
		
		write_text = readData(sourcefile);
		FILEIO.TextFileWrite2(new File(cha_filename), write_text);
	}	
	
	Makecha(String sourcefile, String absolutePath, String encoding)//CSV
	{
		cha_filename = sourcefile + ".cha";
		
		String filename = absolutePath + "source/JapaneseMakecha.txt";
		File jpname = new File(filename);
		DataInJapanese = FILEIO.TextFileAllReadCodeArray(jpname);		
		
		write_text = readCsv(sourcefile);
		FILEIO.TextFileWriteCode(new File(cha_filename), write_text, encoding);
	}
	
	public String readCsv(String sourcefile)//CSV
	{
		File source = new File(sourcefile);
		String ss;
		boolean endOfSegment = false;
		
		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(source), "JISAutoDetect"));
			StringWriter sw1 = new StringWriter();			//For words
			BufferedWriter bw1 = new BufferedWriter(sw1);
			
			while((ss = br1.readLine()) != null)
			{
				String oneline[] = ss.split(","); //Comma
				
				for(int i=0;i<oneline.length;i++)
				{
					String word = oneline[i];
					if(!word.equals(""))
					{
						if(word.equals(DataInJapanese[2]))//End of Segment
						{
							bw1.write(DataInJapanese[2]+"	"+DataInJapanese[3]+",*,*,*,*,*,"+DataInJapanese[2]+",*,*");
//Chasen							bw1.write(DataInJapanese[2]+"	"+DataInJapanese[2]+"	"+DataInJapanese[2]+"	"+DataInJapanese[3]);
							bw1.newLine();							
//							bw1.write("EOS");
//							bw1.newLine();
							endOfSegment = true;
						}
						else//Each Word
						{
							bw1.write(word+"	"+DataInJapanese[4]+",*,*,*,*,*,"+word+",*,*");
//Chasen							bw1.write(word+"	"+word+"	"+word+"	"+DataInJapanese[4]);
							bw1.newLine();							
						}
					}				
				}
				if(!endOfSegment)//End of Sentence
				{
					bw1.write(DataInJapanese[0]+"	"+DataInJapanese[1]+",*,*,*,*,*,"+DataInJapanese[0]+",*,*");
//Chasen					bw1.write(DataInJapanese[0]+"	"+DataInJapanese[0]+"	"+DataInJapanese[0]+"	"+DataInJapanese[1]);
					bw1.newLine();
//					bw1.write("EOS");
//					bw1.newLine();
				}
				endOfSegment = false;
			}
			
            bw1.write("EOS");
			bw1.newLine();
            
			bw1.flush();
			String output = sw1.toString();
			sw1.getBuffer().delete(0,sw1.getBuffer().length());
			
			return output;
		}
		catch(Exception e){
			System.out.println("File Reading ERROR in readData Makecha");
		}
		return "";
	}	
	
	
	public String readData(String sourcefile)//NOT STEMMING
	{
		File source = new File(sourcefile);
		String ss;
		
		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(source), "JISAutoDetect"));
			StringWriter sw1 = new StringWriter();			//For words
			BufferedWriter bw1 = new BufferedWriter(sw1);
			
			while((ss = br1.readLine()) != null)
			{
				String oneline[] = ss.split(" "); //TAB
				
				for(int i=0;i<oneline.length;i++)
				{
					String word = oneline[i];
					if(!word.equals(""))
					{
						if(word.equals("sunaribarafuto"))
						{
							bw1.write(word+"	"+word+"	"+word+"	"+"unknown\n");	
							bw1.write("EOS\n");												
						}
						else if((word.charAt(word.length()-1)) == '.')
						{
							word = word.replaceAll("\\.",""); 
							bw1.write(word+"	"+word+"	"+word+"	"+"data\n");
							bw1.write(".	.	.	period\n");							
						}
						else if((word.charAt(word.length()-1)) == ',')
						{
							word = word.replaceAll(",",""); 
							bw1.write(word+"	"+word+"	"+word+"	"+"data\n");
							bw1.write(",	,	,	comma\n");							
						}
						else							
							bw1.write(word+"	"+word+"	"+word+"	"+"data\n");						
					}				
				}
				bw1.write("EOS\n");						
			}
			
			bw1.flush();
			String output = sw1.toString();
			sw1.getBuffer().delete(0,sw1.getBuffer().length());
			
			return output;
		}
		catch(Exception e){
			System.out.println("File Reading ERROR in readData Makecha");
		}
		return "";
	}	
	
	public String read_text(String sourcefile, String stemfile)
	{
		File source = new File(sourcefile);
		File stem = new File(stemfile);
		String ss, ss2;

		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(source), "JISAutoDetect"));
			StringWriter sw1 = new StringWriter();			//For words
			BufferedWriter bw1 = new BufferedWriter(sw1);
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(stem), "JISAutoDetect"));
			StringWriter sw2 = new StringWriter();			//For stem words
			BufferedWriter bw2 = new BufferedWriter(sw2);			

			while((ss = br1.readLine()) != null)
			{
				ss2 = br2.readLine();
				
				String oneline[] = ss.split(" "); //TAB
				String oneline2[] = ss2.split(" "); //TAB				

				for(int i=0;i<oneline.length;i++)
				{
					String word = oneline[i];
					String stemword = oneline2[i];					
					if(!word.equals(""))
					{
						if(word.equals("sunaribarafuto"))
						{
							bw1.write(word+"	"+word+"	"+stemword+"	"+"unknown\n");	
							bw1.write("EOS\n");												
						}
						else if((word.charAt(word.length()-1)) == '.')
						{
							word = word.replaceAll("\\.",""); 
							stemword = stemword.replaceAll("\\.",""); 							
							bw1.write(word+"	"+word+"	"+stemword+"	"+"noun\n");
							bw1.write(".	.	.	period\n");							
						}
						else if((word.charAt(word.length()-1)) == ',')
						{
							word = word.replaceAll(",",""); 
							stemword = stemword.replaceAll(",",""); 													
							bw1.write(word+"	"+word+"	"+stemword+"	"+"noun\n");
							bw1.write(",	,	,	comma\n");							
						}
						else							
							bw1.write(word+"	"+word+"	"+stemword+"	"+"noun\n");						
					}				
				}
				bw1.write("EOS\n");						
			}
			
			bw1.flush();
			String output = sw1.toString();
			sw1.getBuffer().delete(0,sw1.getBuffer().length());
			
			return output;
		}
		catch(Exception e){
			System.out.println("File Reading ERROR in Makecha");
		}
		return "";
	}
}
