package com.adrros.friendfromdis.util;

import com.adrros.friendfromdis.Commands;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IsCommandCorrect {
	
	public static boolean isCommandCorrect(String wholeRawMessage, Commands commands) {
		String withoutPrefix = deletePrefix(wholeRawMessage);
		return withoutPrefix.startsWith(commands.PREFIX);
	}
	
	private static String deletePrefix(String wholeRawMessage) {
		return wholeRawMessage.substring(1);
	}
	
}
