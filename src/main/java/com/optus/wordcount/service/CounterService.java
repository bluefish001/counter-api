package com.optus.wordcount.service;

import java.util.List;

import com.optus.wordcount.model.WordBean;

public interface CounterService {
	
	public List<WordBean> getWordNumberMethod1(List<String> wordString,String path);
	
	public List<WordBean> getWordNumberMethod2(List<String> wordString,String path);
	
	public List<WordBean> getTopWord(int topNumber,String path);

}
