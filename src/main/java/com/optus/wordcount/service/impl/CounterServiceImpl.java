package com.optus.wordcount.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.optus.wordcount.model.WordBean;
import com.optus.wordcount.service.CounterService;

@Service("CounterService")
public class CounterServiceImpl implements CounterService {
  
  private Logger log = LoggerFactory.getLogger(CounterServiceImpl.class);
	
  public List<WordBean> getWordNumberMethod1(List<String> wordString,String path){
	  List<WordBean> wordList =  new ArrayList<WordBean>();
	  
	  
	  List<String> wordStringLowCase = new ArrayList<String>();
	  for(String word : wordString){
		//change the search list to low case 
		  wordStringLowCase.add(word.toLowerCase());
		  
		//set up wordBean
		  WordBean wordBean = new WordBean(word,0);
		  wordList.add(wordBean);
	  }
	  
	  FileInputStream fis = null;
      DataInputStream dis = null;
      BufferedReader br = null;
      
	  try {
		  fis = new FileInputStream(path);
          dis = new DataInputStream(fis);
          br = new BufferedReader(new InputStreamReader(dis));
          String line = null;
          //search line by line
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, " ,.;:?\"");
			while(st.hasMoreTokens()){
				String tmp = st.nextToken().toLowerCase();
                
                if(wordStringLowCase!=null
                		&&wordStringLowCase.size()>0
                		&&wordStringLowCase.contains(tmp)){
                	WordBean tempWordBean = new WordBean(tmp,0);
                	if(wordList.contains(tempWordBean)){
                		int number =  wordList.get(wordList.indexOf(tempWordBean)).getNumber();
                		wordList.get(wordList.indexOf(tempWordBean)).setNumber(number+1);
                	}
                }
			}
		  }
	  } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }catch (IOException e) {
          e.printStackTrace();
      } finally{
          try{if(br != null) br.close();}catch(Exception ex){}
      }
	  
	  return wordList;
  }
  
  
  public List<WordBean> getWordNumberMethod2(List<String> wordString,String path){
	  List<WordBean> wordList =  new ArrayList<WordBean>();
	  //Calculate the every words in that file
	  List<WordBean> wordBeanList  = countEveryWord(path);
	  //Choose the word that user search for
	  for(String word : wordString){
		  WordBean wordBean = new WordBean(word,0);
		  if(wordBeanList.contains(wordBean)){
			  wordBean = wordBeanList.get(wordBeanList.indexOf(wordBean));
			  wordList.add(wordBean);
		  }else{
			  wordList.add(wordBean);
		  }
	  }
	  
	 return wordList;
  }
  
  public List<WordBean> getTopWord(int topNumber,String path){
	  //Calculate the every words in that file
	  List<WordBean> wordBeanList  = countEveryWord(path);
	  if(null!=wordBeanList&&wordBeanList.size()>0){
		  //Sort the list
		  Collections.sort(wordBeanList);
		  if(wordBeanList.size()<=topNumber){
			  return wordBeanList;
		  }else {
			 			  
			  for(WordBean wordBean : wordBeanList.subList(0, topNumber) ){
				  log.debug(" word is "+wordBean.getWord()+" "+"  number is "+ wordBean.getNumber());
			  }
			  
			  return wordBeanList.subList(0, topNumber);
		  }
	  }
	  
	  return null;	  
  }
  
  //Load whole file and calculate each words
  private static List<WordBean> countEveryWord(String path){
	  FileInputStream fis = null;
      DataInputStream dis = null;
      BufferedReader br = null;
      List<WordBean> wordList = new ArrayList<WordBean>();
      try {
    	     	  
    	  fis = new FileInputStream(path);
          dis = new DataInputStream(fis);
          br = new BufferedReader(new InputStreamReader(dis));
          String line = null;
          while((line = br.readLine()) != null){
              StringTokenizer st = new StringTokenizer(line, " ,.;:?\"");
              while(st.hasMoreTokens()){
                  String tmp = st.nextToken();
                  WordBean tempWordBean = new WordBean(tmp,1);
                  if(!wordList.contains(tempWordBean)){
                      wordList.add(tempWordBean);
                  }else{
                	int number =   wordList.get(wordList.indexOf(tempWordBean)).getNumber();
                	wordList.get(wordList.indexOf(tempWordBean)).setNumber(number+1);
                  }
              }
          }
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } finally{
          try{if(br != null) br.close();}catch(Exception ex){}
      }
      
      return wordList;
  }
}
