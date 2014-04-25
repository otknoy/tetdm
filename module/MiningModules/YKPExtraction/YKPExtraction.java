//
// Mining module for TETDM 
// YKPExtraction.java Version 0.30
// Copyright(C) 2011 YASUFUMI TAKAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//

package module.MiningModules.YKPExtraction;		// Replace ALL [MiningStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.HashMap;

/* Create Data for Tag Cloud by extracting Key phrases with Yahoo API
 *      set_data(0,keyphrase) ... Array for Key phrases (String[])
 *      set_data(101,val) ... Frequencies for Key phrases (int[])
 *      set_data(201,sentence) ... List of Key phrases with Tab (String[]) */
@SuppressWarnings("serial")
public class YKPExtraction extends MiningModule
{
	static String[] phSentence;
	static int Sid;
	static HashMap<String,Integer>keyPhrases;
	
	String messagesInJapanese[];	

	public YKPExtraction()
	{
		setModuleID(15);
		pairingVisualizationID = new int[1];
		pairingVisualizationID[0] = 14;
	}

	public void initializePanel()
	{
		messagesInJapanese = fileReadArray();
	}
	

	public static HashMap<String, Integer> getKeyPhrases() {
		return keyPhrases;
	}

	public void initializeData(){
		Sid = 0;
		keyPhrases = new HashMap<String,Integer>();
		phSentence = new String[text.sentenceNumber];
		String appid = "";
		File idFile = new File(myModulePath + "yahoo_appid");
		if(!idFile.exists()){
			setErrorMsg(messagesInJapanese[0]);
			return;
		}
//		System.out.println(myModulePath + "yahoo_appid");
		try {
			BufferedReader br1 = new BufferedReader(
			new InputStreamReader(
			new FileInputStream(idFile)));
		 	appid = br1.readLine();
		} catch(Exception e){
			e.printStackTrace();
		}
		for(Sid=0;Sid<text.sentenceNumber;Sid++){
			try {
				phSentence[Sid]="";
				String urlText = "http://jlp.yahooapis.jp/KeyphraseService/V1/extract?appid="
						+appid+"&sentence="+URLEncoder.encode(text.sentence[Sid].sentenceText,"UTF-8");
				URL url = new URL(urlText);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				BufferedReader br2 = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
				ParseKeyPhrase(new InputSource(br2));
				br2.close();
			} catch(Exception e){
				e.printStackTrace();
				setErrorMsg(messagesInJapanese[1]);
				return;
			}
		}
	}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:
				firstData();				
				break;
		}
	}	
	
	public void firstData(){
		String[] words = (String[])keyPhrases.keySet().toArray(new String[0]);
		int[] vals = new int[keyPhrases.size()];
		int i=0;
		for(Integer v : keyPhrases.values()){
			vals[i] = v.intValue();
			i++;
		}
		setDataStringArray(0,words);
		setDataIntegerArray(1,vals);
		setDataStringArray(2,phSentence);
	}

//	public void actionPerformed(ActionEvent e){}
	
	void ParseKeyPhrase(InputSource is){
		try{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new YahooHandler();
			saxParser.parse(is, handler);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void addSentence(String string) {
		if(phSentence[Sid].length()==0)
			phSentence[Sid]+=string;
		else
			phSentence[Sid]+="\t"+string;
	}

	public void setErrorMsg(String str){
		phSentence[0]=str;
		for(int i=1;i<text.sentenceNumber;i++){
			phSentence[i]="";
		}
		HashMap<String,Integer>map = YKPExtraction.getKeyPhrases();
		map.put(str, 1);
	}
}

class YahooHandler extends DefaultHandler
{
	String currentTag;
	
	public void startElement(String uri, String localName, 
		String qName, Attributes attributes) throws SAXException {
		if(qName!=""){
			currentTag=qName;		
		}
	}
	public void endElement(String uri, String localName,
         String qName) throws SAXException {
		currentTag="";
	}
	public void characters(char[] ch, int start, int length){
		if(currentTag.equals("Keyphrase")){
			String word = new String(ch,start,length);
			HashMap<String,Integer>map = YKPExtraction.getKeyPhrases();
			if(map.containsKey(word)){
				int val = map.get(word);
				map.put(word, val+1);
			} else {
				map.put(word, 1);
			}
			YKPExtraction.addSentence(new String(ch,start,length));
		}
	}
}

