package com.adrros.friendfromdis.command.music.play.buttonlisteners;

import com.adrros.friendfromdis.lavaplayer.TrackScheduler;
import com.adrros.friendfromdis.lavaplayer.player_state.PlayerState;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static com.adrros.friendfromdis.Commands.LOOP;
import static com.adrros.friendfromdis.Commands.SKIP;
import static com.adrros.friendfromdis.lavaplayer.PlayerManager.getMusicManager;

public class PlayBoxListener extends ListenerAdapter {
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		final TrackScheduler scheduler = getMusicManager(event.getGuild()).scheduler;
		if (event.getComponentId().equals(LOOP.name())) {
			PlayerState.changeIsLoopOn();
			event.reply(
					"Is loop on: %s by: ".formatted(PlayerState.isLoopOn()) + event.getUser().getName()).queue();
		} else if (event.getComponentId().equals(SKIP.name())) {
//			event.reply("Not implemented yet SKIP").queue();
			scheduler.stopTrack();
			event.reply("Skip by: " + event.getUser().getName()).queue();
		}
	}
}
