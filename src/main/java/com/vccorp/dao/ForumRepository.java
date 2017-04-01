package com.vccorp.dao;


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vccorp.config.SystemConf;
import com.vccorp.utils.DateUtils;
import org.bson.types.ObjectId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ForumRepository {

    private Connection mysqlConnection =null;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public ForumRepository(){
        mongoClient = new MongoClient(SystemConf.MONGO_HOST, SystemConf.MONGO_PORT);
        mongoDatabase = mongoClient.getDatabase(SystemConf.MONGO_DB);
        mysqlConnection = new DBConnection().getConnection();
    }

    /**
     * get number of post by date
     * @param date
     * @return
     */
    public long getCountCommentByDate(String date) {
        long count=0;
        try {

            MongoCollection dbCollection = mongoDatabase.getCollection("Comments");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            Date startDate = df.parse(date  + " 00:00:00");
            Date enDate= df.parse(DateUtils.getNextDate(date) + " 00:00:00");

            BasicDBObject dateRange = new BasicDBObject();
            ObjectId objectIdStart = new ObjectId(startDate);
            ObjectId objectIdEnd = new ObjectId(enDate);

            dateRange.put("$gte", objectIdStart);
            dateRange.put("$lt", objectIdEnd);
            BasicDBObject query = new BasicDBObject("_id", dateRange);

            count = dbCollection.count(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }



    public Map<String, Integer> getCountCommentByRangeDate(String startDate, String endDate){

        Map<String, Integer> result = new HashMap<>();
        try{
            String sql="SELECT * FROM comment_count WHERE dt >=? AND dt<=?;";
            PreparedStatement pst = mysqlConnection.prepareStatement(sql);
            pst.setString(1, startDate);
            pst.setString(2, endDate);

            ResultSet rs = pst.executeQuery();

            while (rs!=null&& rs.next()){
                Date dateString = rs.getDate("date");
                int count = rs.getInt("count");
                result.put(dateString.toString(), count);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get number of comment by date
     * @param date
     * @return
     */
    public long getCountArticleByDate(String date) {
        long count=0;
        try {
            MongoCollection dbCollection = mongoDatabase.getCollection("Articles");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            Date startDate = df.parse(date  + " 00:00:00");
            Date endDate= df.parse(DateUtils.getNextDate(date) + " 00:00:00");

            BasicDBObject dateRange = new BasicDBObject();
            dateRange.put("$gte", startDate);   //
            dateRange.put("$lt", endDate);
            BasicDBObject query = new BasicDBObject("GetDate", dateRange);

            count = dbCollection.count(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, Integer> getCountArticleByRangeDate(String startDate, String endDate){
        Map<String, Integer> result = new HashMap<>();

        try{
            String sql="SELECT * FROM article_count WHERE article_count.date >=? AND article_count.date <=?;";
            PreparedStatement pst = mysqlConnection.prepareStatement(sql);
            pst.setString(1, startDate);
            pst.setString(2, endDate);

            ResultSet rs = pst.executeQuery();

            while (rs!=null&& rs.next()){
                Date dateString = rs.getDate("date");
                int count = rs.getInt("count");
                result.put(dateString.toString(), count);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Insert count by date to temp (cache) table
     * @param date
     * @param value
     */
    public void insertCommentCountByDate (String date, long value) {
        try{
            String sql="INSERT IGNORE INTO comment_count (dt, count) VALUE (?, ?)";
            PreparedStatement pst = mysqlConnection.prepareStatement(sql);
            pst.setString(1, date);
            pst.setLong(2, value);
            pst.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert count by date to temp (cache) table
     * @param date
     * @param value
     */
    public void insertArticleCountByDate (String date, long value) {
        try{
            String sql="INSERT IGNORE INTO article_count (date, count) VALUE (?, ?)";
            PreparedStatement pst = mysqlConnection.prepareStatement(sql);
            pst.setString(1, date);
            pst.setLong(2, value);
            pst.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ForumRepository forumRepository = new ForumRepository();
//        long count = forumRepository.getCountCommentByDate("2017-02-15");
        long count = forumRepository.getCountArticleByDate("2017-01-03");
        System.out.println(count);
    }
}
