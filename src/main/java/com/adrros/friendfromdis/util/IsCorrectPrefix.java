package com.adrros.friendfromdis.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class IsCorrectPrefix {
	
	private static List<String> prefixes = new ArrayList<>();
	
	public static boolean isPrefixCorrect(String rawMessage) {
		Objects.requireNonNull(rawMessage);
		if (prefixes.isEmpty())
			throw new IllegalStateException("Prefixes are not set yet!");
		
		return prefixes.stream().anyMatch(rawMessage::startsWith);
	}
	
	public static void setPrefixes(List<String> prefixes) {
		IsCorrectPrefix.prefixes = prefixes;
	}
	
}
