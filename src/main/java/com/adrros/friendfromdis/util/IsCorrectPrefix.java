package com.adrros.friendfromdis.util;

import com.adrros.friendfromdis.BotConfigVariables;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class IsCorrectPrefix {
	
	public static boolean isPrefixCorrect(String rawMessage) {
		Stream<String> var10000 = BotConfigVariables.getPrefixes().stream();
		Objects.requireNonNull(rawMessage);
		return var10000.anyMatch(rawMessage::startsWith);
	}
	
}
