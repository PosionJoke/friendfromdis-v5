package com.adrros.friendfromdis.util;

import com.adrros.friendfromdis.Commands;
import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.adrros.friendfromdis.util.IsCommandCorrect.isCommandCorrect;
import static com.adrros.friendfromdis.util.IsCorrectPrefix.isPrefixCorrect;

@UtilityClass
public class ValidateEnterToCommand {
	
	public static boolean isCommandInvalid(final MessageReceivedEvent eventRaw, Commands command) {
		if (!isPrefixCorrect(eventRaw.getMessage().getContentRaw()))
			return true;
		if (eventRaw.getAuthor().isBot())
			return true;
		if (!isCommandCorrect(eventRaw.getMessage().getContentRaw(), command))
			return true;
		
		return false;
	}
	
}
