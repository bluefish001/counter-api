package com.optus.wordcount.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.optus.wordcount.model.json.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.optus.wordcount.model.SearchCriterion;
import com.optus.wordcount.model.WordBean;
import com.optus.wordcount.service.CounterService;


@RestController
public class WordCountController {
	private Logger log = LoggerFactory.getLogger(WordCountController.class);
	
	@Configuration
	@PropertySources(value = {@PropertySource("classpath:application.properties")})
    static class DefaultProperties
    {}

    @Resource
    private Environment env;
	
	@Autowired
	CounterService counterService;
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	@JsonView(View.enquireResponse.class)
	public @ResponseBody Map<String,Map> searchText(@RequestBody SearchCriterion searchCriterion){
		
		String realPath  =  env.getRequiredProperty("file.location");
		log.debug("realPath is "+realPath);
		if(null!=searchCriterion && searchCriterion.getSearchText()!=null 
				&&searchCriterion.getSearchText().size()>0){
			
			Map<String,Map> wordCountMap = new HashMap<String,Map>();
			
			List<WordBean> wordList = counterService.getWordNumberMethod2(searchCriterion.getSearchText(),realPath);
			if(null!=wordList&&wordList.size()>0){
				Map<String,Integer> wordBeanMap = new HashMap<String,Integer>();
				
				for(WordBean wordBean : wordList){
					wordBeanMap.put(wordBean.getWord(), wordBean.getNumber());
				}
								
				wordCountMap.put("counts", wordBeanMap);
			}
			
			return wordCountMap;
		}
		
		return null;
	}
	
	
	@RequestMapping(value="/top/{topNumber}",method=RequestMethod.GET)
	public void getTopWord(@PathVariable int topNumber,HttpServletResponse response){
		String reponseString = "";
		if(topNumber<0){
			reponseString = "number must be bigger than 1";
		}
		String realPath  = env.getRequiredProperty("file.location");
		List<WordBean> wordList = counterService.getTopWord(topNumber, realPath);
		for(WordBean wordBean : wordList){
			reponseString = reponseString+wordBean.getWord()+" | "+wordBean.getNumber()+"\n";
		}
		
		response.setContentType("text/plain; charset=utf-8");
		try {
			response.getWriter().print(reponseString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
