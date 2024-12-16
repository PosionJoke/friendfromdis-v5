package com.adrros.friendfromdis.command.music.play.buttonlisteners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PlayDropDown {
	
	public void createAndRunDropDown(TextChannel channel, List<String> titles) {
		List<String> titlesFixed = removeDuplicates(titles);
		
		StringSelectMenu.Builder builder = StringSelectMenu.create("menu:id");
		titlesFixed.forEach((String title) -> builder.addOption(title, title));
		StringSelectMenu menu = builder.build();
		channel.sendMessageComponents(ActionRow.of(menu)).queue();
	}
	
	public static List<String> removeDuplicates(List<String> titles) {
		Set<String> uniqueTitlesSet = new LinkedHashSet<>(titles);
		return new ArrayList<>(uniqueTitlesSet);
	}
}
