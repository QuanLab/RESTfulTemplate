package com.vccorp.config;

import java.util.logging.Logger;

public class SystemConf {

	private static final Logger log = Logger.getLogger(SystemConf.class.toString());

	public static String MYSQL_URL;
	public static String MYSQL_USERNAME;
	public static String MYSQL_PASSWORD;

	public static String MONGO_HOST;
	public static String MONGO_DB;
	public static int MONGO_PORT;

	static {
		try {
			new ConfigUtil().loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.toString());
		}
	}
}
