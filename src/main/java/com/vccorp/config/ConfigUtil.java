package com.vccorp.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigUtil {
    public void loadConfig() throws Exception {
        Properties pro = new Properties();
        try{
            String  path =Thread.currentThread().getContextClassLoader().getResource("/").toURI()
                    .resolve("../conf.properties").getPath();
            pro.load(new FileInputStream(path));
        } catch (Exception e) {
            pro.load(new FileInputStream("conf.properties"));
        }

        SystemConf.MYSQL_URL = pro.getProperty("MYSQL_URL");
        SystemConf.MYSQL_USERNAME = pro.getProperty("MYSQL_USERNAME");
        SystemConf.MYSQL_PASSWORD = pro.getProperty("MYSQL_PASSWORD");
        SystemConf.MONGO_HOST = pro.getProperty("MONGO_HOST");
        SystemConf.MONGO_DB = pro.getProperty("MONGO_DB");
        try{
            SystemConf.MONGO_PORT = Integer.parseInt(pro.getProperty("MONGO_PORT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
