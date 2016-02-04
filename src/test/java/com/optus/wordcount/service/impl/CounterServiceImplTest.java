package com.optus.wordcount.service.impl;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.optus.wordcount.model.WordBean;

public class CounterServiceImplTest {

	@InjectMocks
	CounterServiceImpl counterService;
	
	
	@BeforeClass
	public void setUp(){
	      MockitoAnnotations.initMocks(this);
	 }
	
	@Test
	public void countEveryWordTest(){
		List<WordBean> wordList = counterService.getTopWord(20,"D:/optus.txt");
		Assert.assertNotNull(wordList);
		
	}
}
