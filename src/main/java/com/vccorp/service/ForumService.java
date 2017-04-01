package com.vccorp.service;


import com.vccorp.dao.ForumRepository;
import com.vccorp.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForumService {

	private ForumRepository forumRepository;

	public ForumService(){
		this.forumRepository = new ForumRepository();
	}

	/**
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONArray getArticleCount(String startDate, String endDate) {

		String currentDay = DateUtils.getCurrentDateString();

		JSONArray result = new JSONArray();
		List<String> listDate = DateUtils.getDaysBetweenDates(startDate, endDate);

		// get result from table cache for count
		Map<String, Integer> resultCache = forumRepository.getCountArticleByRangeDate(startDate, endDate);
		Set<String> dateTraversed = resultCache.keySet();

		for(String date: listDate) {
			JSONObject element = new JSONObject();

			if(dateTraversed.contains(date)){
				element.put("date", date);
				element.put("count", resultCache.get(date));
				result.put(element);
			} else {
				long count = forumRepository.getCountArticleByDate(date);
				element.put("date", date);
				element.put("count", count);
				result.put(element);

				if(!date.equals(currentDay)){
					forumRepository.insertArticleCountByDate(date, count);
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONArray getCommentCount(String startDate, String endDate) {

		String currentDay = DateUtils.getCurrentDateString();

		JSONArray result = new JSONArray();
		List<String> listDate = DateUtils.getDaysBetweenDates(startDate, endDate);

		// get result from table cache for count
		Map<String, Integer> resultCache = forumRepository.getCountCommentByRangeDate(startDate, endDate);
		Set<String> dateTraversed = resultCache.keySet();

		System.out.println("dateTraversed: " +  dateTraversed);

		for(String date: listDate) {
			JSONObject element = new JSONObject();

			if(dateTraversed.contains(date)){
				element.put("date", date);
				element.put("count", resultCache.get(date));
				result.put(element);

			} else {
				long count = forumRepository.getCountCommentByDate(date);
				element.put("date", date);
				element.put("count", count);
				result.put(element);

				if(!date.equals(currentDay)){
					forumRepository.insertCommentCountByDate(date, count);
				}
			}
		}
		return result;
	}


	/**
	 * only used for the first time run to create temp table
	 * @param startDate
	 * @param endDate
	 */
	private void insertCommentFirsTime(String startDate, String endDate) {
		List<String> listDate = DateUtils.getDaysBetweenDates(startDate, endDate);
		for (String date : listDate) {
			long count = forumRepository.getCountCommentByDate(date);
			System.out.println(date + "\t" + count);
			forumRepository.insertCommentCountByDate(date, count);
		}
		System.out.println("Complete!");
	}

	/**
	 * only used for the first time run to create temp table
	 * @param startDate
	 * @param endDate
	 */
	private void insertArticleFirsTime(String startDate, String endDate) {
		List<String> listDate = DateUtils.getDaysBetweenDates(startDate, endDate);
		for (String date : listDate) {
			long count = forumRepository.getCountCommentByDate(date);
			System.out.println(date + "\t" + count);
			forumRepository.insertArticleCountByDate(date, count);
		}
		System.out.println("Complete!");
	}


	public static void main(String[] args) {
//		ForumService forumService = new ForumService();
///		forumService.insertCommentFirsTime("2017-01-03", "2017-03-28");
//		forumService.insertArticleFirsTime("2017-01-03", "2017-03-28");
	}
}