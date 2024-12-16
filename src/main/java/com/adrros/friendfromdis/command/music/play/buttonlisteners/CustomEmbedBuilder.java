package com.adrros.friendfromdis.command.music.play.buttonlisteners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public class CustomEmbedBuilder {
	
	private static EmbedBuilder embedBuilder = new EmbedBuilder();
	private static CustomEmbedBuilder customEmbedBuilder;
	
	public static CustomEmbedBuilder builder() {
		customEmbedBuilder = new CustomEmbedBuilder();
		embedBuilder.setColor(new Color(206, 235, 255));
		return customEmbedBuilder;
	}
	
	public CustomEmbedBuilder title(String title) {
		embedBuilder.setTitle(title);
		return customEmbedBuilder;
	}
	
	public CustomEmbedBuilder description(String description) {
		embedBuilder.setDescription(description);
		return customEmbedBuilder;
	}
	
	public CustomEmbedBuilder color(Color color) {
		embedBuilder.setColor(color);
		return customEmbedBuilder;
	}
	
	public CustomEmbedBuilder footer(String text, String avatarURL) {
		embedBuilder.setFooter(text, avatarURL);
		return customEmbedBuilder;
	}
	
	public void clear() {
		embedBuilder.clear();
	}
	
	public MessageEmbed build() {
		return embedBuilder.build();
	}
	
}
