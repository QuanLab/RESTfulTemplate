package com.vccorp.service;


import com.vccorp.dao.NewsRepository;
import com.vccorp.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewsService {

	private NewsRepository newsRepository;

	public NewsService(){
		this.newsRepository = new NewsRepository();
	}

	/**
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONArray getNewsCount(String startDate, String endDate) {

		String currentDay = DateUtils.getCurrentDateString();

		JSONArray result = new JSONArray();
		List<String> listDate = DateUtils.getDaysBetweenDates(startDate, endDate);

		// get result from table cache for count
		Map<String, Integer> resultCache = newsRepository.getCountByRangeDate(startDate, endDate);
		Set<String> dateTraversed = resultCache.keySet();

		for(String date: listDate) {
			JSONObject element = new JSONObject();

			if(dateTraversed.contains(date)){
				element.put("date", date);
				element.put("count", resultCache.get(date));
				result.put(element);

			} else {
				int count = newsRepository.getCountByDate(date);
				element.put("date", date);
				element.put("count", count);
				result.put(element);

				if(!date.equals(currentDay)){
					newsRepository.insertCountByDate(date, count);
				}
			}
		}
		return result;
	}


	// this method is only used for the first time run to create temp table
	private void insertFirsTime(String startDate, String endDate) {
		List<String> listDate = DateUtils.getDaysBetweenDates(startDate, endDate);
		for (String date : listDate) {
			int count = newsRepository.getCountByDate(date);
			System.out.println(date + "\t" + count);
			newsRepository.insertCountByDate(date, count);
		}
		System.out.println("Complete!");
	}


//	public static void main(String[] args) {
//		NewsService newsService = new NewsService();
//		newsService.insertFirsTime("2015-05-14", "2017-03-26");
//	}
}