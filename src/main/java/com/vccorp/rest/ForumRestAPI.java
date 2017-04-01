package com.vccorp.rest;

import com.vccorp.service.ForumService;
import com.vccorp.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/forum/")
public class ForumRestAPI {

	private ForumService forumService = new ForumService();

	@GET
	@Path("/article/")
	public Response getArticle(@QueryParam("start") String startDate, @QueryParam("end") String endDate) {

		JSONObject result = new JSONObject();
		JSONArray dataResponse;

		//check if date null return default range for 7 day
		if(startDate==null|| endDate==null){
			String currentDate = DateUtils.getCurrentDateString();
			result.put("status", "success");
			dataResponse = forumService.getArticleCount(DateUtils.getStartOfWeek(), currentDate);
			result.put("data", dataResponse);
			return Response.status(200).entity(result.toString()).build();
		}

		// check if date is invalid format
		if(!DateUtils.validateDateString(startDate) || !DateUtils.validateDateString(endDate)){
			result.put("status", "fail");
			return Response.status(200).entity(result.toString()).build();
		}

		dataResponse = forumService.getArticleCount(startDate, endDate);
		result.put("status", "success");
		result.put("data", dataResponse);
		return Response.status(200).entity(result.toString()).build();
	}


	@GET
	@Path("/comment/")
	public Response getComment(@QueryParam("start") String startDate, @QueryParam("end") String endDate) {

		JSONObject result = new JSONObject();
		JSONArray dataResponse;

		//check if date null return default range for 7 day
		if(startDate==null|| endDate==null){
			String currentDate = DateUtils.getCurrentDateString();
			result.put("status", "success");
			dataResponse = forumService.getCommentCount(DateUtils.getStartOfWeek(), currentDate);
			result.put("data", dataResponse);
			return Response.status(200).entity(result.toString()).build();
		}

		// check if date is invalid format
		if(!DateUtils.validateDateString(startDate) || !DateUtils.validateDateString(endDate)){
			result.put("status", "fail");
			return Response.status(200).entity(result.toString()).build();
		}

		dataResponse = forumService.getCommentCount(startDate, endDate);
		result.put("status", "success");
		result.put("data", dataResponse);
		return Response.status(200).entity(result.toString()).build();
	}

}