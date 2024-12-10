package com.adrros.friendfromdis;


import com.adrros.friendfromdis.util.IsCorrectPrefix;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class BotConfigVariables {
	private final List<String> prodPrefixes = List.of("|");
	private final List<String> devPrefixes = List.of("[");
	private boolean isProd;
	private String devKey;
	private String botKey;
	
	@PostConstruct
	public void init() {
		IsCorrectPrefix.setPrefixes(isProd ? prodPrefixes : devPrefixes);
	}
	
	@Value("${discrod.bot.isProd}")
	public void loadIsProdFromConfig(boolean isProd) {
		this.isProd = isProd;
	}
	
	@Value("${discrod.bot.prod.key}")
	public void loadBotKeyFromConfig(String key) {
		botKey = key;
	}
	
	@Value("${discrod.bot.dev.key}")
	public void loadDevKeyFromConfig(String key) {
		devKey = key;
	}
	
	public String getBotKey() {
		return isProd ? botKey : devKey;
	}
}

