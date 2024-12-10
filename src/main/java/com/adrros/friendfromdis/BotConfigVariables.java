package com.adrros.friendfromdis;


import org.springframework.context.annotation.Configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class BotConfigVariables {
	public static String botVersion;
	public static List<String> prodPrefixes = List.of("'", "!", "`", ";");
	public static List<String> devPrefixes = List.of("[");
	public static boolean isProd;
	private static String prodKey;
	private static String devKey;
	
	public BotConfigVariables() {
	}
	
	@Value("${discrod.bot.isProd}")
	public void setNameStatic(boolean isProd) {
		BotConfigVariables.isProd = isProd;
	}
	
	@Value("${discrod.bot.prod.key}")
	public void setBotProdKey(String key) {
		prodKey = key;
	}
	
	@Value("${discrod.bot.dev.key}")
	public void setBotDevKey(String key) {
		devKey = key;
	}
	
	@Value("${discrod.bot.version}")
	public void setBotVersion(String version) {
		botVersion = version;
	}
	
	public static List<String> getPrefixes() {
		return isProd ? List.of("|") : List.of("=");
	}
	
	public String getBotKey() {
		return isProd ? prodKey : devKey;
	}
}

