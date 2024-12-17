package com.adrros.friendfromdis.command.music.play.buttonlisteners;


import com.adrros.friendfromdis.lavaplayer.player_state.PlayerState;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

import static com.adrros.friendfromdis.Commands.LOOP;
import static com.adrros.friendfromdis.Commands.QUEUE;
import static com.adrros.friendfromdis.Commands.SKIP;

public class PlayCommandUtils {
	public static List<ItemComponent> playItemButtons() {
		return List.of(
				Button.primary(LOOP.name(), "Loop ON/OFF"),
				Button.danger(SKIP.name(), "Skip"),
				Button.success(QUEUE.name(), "Queue")
		);
	}
	
	public static MessageEmbed getPlaySongEmbed(String songName) {
		return CustomEmbedBuilder.builder()
				.title("🐋🪽")
				.description("""
                                     **New song:** %s
                                     🎺 loop: **%s**
                                     """.formatted(songName, PlayerState.isLoopOn()))
				.build();
	}
}
