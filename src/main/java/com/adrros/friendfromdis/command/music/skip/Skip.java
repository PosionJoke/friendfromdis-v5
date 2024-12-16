package com.adrros.friendfromdis.command.music.skip;

import com.adrros.friendfromdis.Commands;
import com.adrros.friendfromdis.lavaplayer.TrackScheduler;
import com.adrros.friendfromdis.lavaplayer.player_state.PlayerState;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static com.adrros.friendfromdis.lavaplayer.PlayerManager.getMusicManager;
import static com.adrros.friendfromdis.util.ValidateEnterToCommand.isCommandInvalid;

@Log4j2
public class Skip extends ListenerAdapter {
	@Override
	public void onMessageReceived(@NotNull final MessageReceivedEvent eventRaw) {
		if (isCommandInvalid(eventRaw, Commands.SKIP))
			return;
		
		TrackScheduler trackScheduler = getMusicManager(eventRaw.getGuild()).scheduler;
		trackScheduler.stopTrack();
		
		log.info("Skip: {}", trackScheduler);
	}
}
