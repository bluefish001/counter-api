package com.optus.wordcount.model;

import com.optus.wordcount.model.json.View;
import com.fasterxml.jackson.annotation.JsonView;

public class WordBean implements Comparable<WordBean>{
	@JsonView(View.enquireResponse.class)
	private String word;
	@JsonView(View.enquireResponse.class)
	private int number;
	
	
	public WordBean(){};
	
	public WordBean(String word, int number){
		this.word = word;
		this.number = number;
	}
		
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
	@Override
	  public int hashCode() {
		return word.toUpperCase().hashCode();
	    }
	 
	@Override
	// if two word bean has the same word, will think they are the same
	public boolean equals(Object obj) {
		        if (this == obj)
		            return true;
		        if (obj == null)
		            return false;
		        if (getClass() != obj.getClass())
		            return false;
		        
		        if(obj instanceof WordBean){
		            final WordBean other = (WordBean) obj;
		            
		            return word.equalsIgnoreCase(other.word);
		        } else{
		            return false;
		        }
		    }
	 
	@Override
	public int compareTo(WordBean wordBean) {
		if(this.number == wordBean.number)
			return 0;
		else
			return this.number > wordBean.number ? -1:1;
	}
}
