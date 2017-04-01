package com.vccorp.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class NewsRepository {

    private Connection mysqlConnection = null;
    private PreparedStatement pst;

    public NewsRepository(){
        mysqlConnection = new DBConnection().getConnection();
    }

    /**
     * get number of post by date
     * @param date
     * @return
     */
    public int getCountByDate(String date) {
        String startDate = date + " 00:00:00";
        String endDate = date + " 23:59:59";
        int count=0;
        try{
            String sql="SELECT COUNT(*) FROM news WHERE get_time >=? AND get_time<=?;";
            pst = mysqlConnection.prepareStatement(sql);
            pst.setString(1, startDate);
            pst.setString(2, endDate);

            ResultSet rs = pst.executeQuery();
            while (rs!=null&& rs.next()){
                count = rs.getInt(1);
            }
            return count;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * get number of post between two date
     * @return
     */
     public Map<String, Integer> getCountByRangeDate(String startDate, String endDate){
         Map<String, Integer> result = new HashMap<>();

         try{
             String sql="SELECT * FROM news_count_by_date WHERE dt >=? AND dt<=?;";
             pst = mysqlConnection.prepareStatement(sql);
             pst.setString(1, startDate);
             pst.setString(2, endDate);

             ResultSet rs = pst.executeQuery();

             while (rs!=null&& rs.next()){
                 Date dateString = rs.getDate("dt");
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
    public void insertCountByDate (String date, int value) {
        try{
            String sql="INSERT IGNORE INTO news_count_by_date(dt, count) VALUE (?, ?)";
            pst = mysqlConnection.prepareStatement(sql);
            pst.setString(1, date);
            pst.setInt(2, value);
            pst.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
