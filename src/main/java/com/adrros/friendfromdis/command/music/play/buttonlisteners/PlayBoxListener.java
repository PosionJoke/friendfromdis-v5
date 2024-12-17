package com.adrros.friendfromdis.command.music.play.buttonlisteners;

import com.adrros.friendfromdis.lavaplayer.TrackScheduler;
import com.adrros.friendfromdis.lavaplayer.player_state.PlayerState;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.adrros.friendfromdis.Commands.LOOP;
import static com.adrros.friendfromdis.Commands.QUEUE;
import static com.adrros.friendfromdis.Commands.SKIP;
import static com.adrros.friendfromdis.lavaplayer.PlayerManager.getMusicManager;
import static java.util.Objects.isNull;

public class PlayBoxListener extends ListenerAdapter {
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		final TrackScheduler scheduler = getMusicManager(Objects.requireNonNull(event.getGuild())).scheduler;
		if (event.getComponentId().equals(LOOP.name())) {
			PlayerState.changeIsLoopOn();
			event.reply(
					"Is loop on: %s by: ".formatted(PlayerState.isLoopOn()) + event.getUser().getName()).queue();
		} else if (event.getComponentId().equals(SKIP.name())) {
			scheduler.stopTrack();
			event.reply("Skip by: " + event.getUser().getName()).queue();
		} else if (event.getComponentId().equals(QUEUE.name())) {
			final var queue = scheduler.getQueue();
			final var playingTrack = scheduler.getPlayingTrack();
			
			if (isNull(playingTrack)) {
				event.reply("Queue")
						.addEmbeds(CustomEmbedBuilder.builder()
								.title("the queue")
								.description("Queue is empty")
								.build())
						.queue();
				return;
			}
			
			final var playingTrackView = new AudioTrackView(
					playingTrack.getInfo().title,
					playingTrack.getInfo().author,
					playingTrack.getPosition(),
					playingTrack.getDuration()
			);
			
			final var viewQueue = queue.stream()
					.map(n -> new AudioTrackView(
							n.getInfo().title, n.getInfo().author, n.getPosition(), n.getDuration()))
					.collect(Collectors.toList());
			
			final MessageEmbed messageEmbed = CustomEmbedBuilder.builder()
					.title("the queue")
					.description(makeQueueTextList(viewQueue, playingTrackView, PlayerState.isLoopOn()))
					.build();
			
			event.reply("Queue")
					.addEmbeds(messageEmbed)
					.queue();
		}
	}
	
	private String makeQueueTextList(List<AudioTrackView> viewQueue, AudioTrackView playingTrack, boolean isLoop) {
		StringBuilder queueTextList = new StringBuilder();
		
		queueTextList.append("current : \n");
		queueTextList.append("""
                                     > **%s** by `%s`, time to end : %s
                                     > 🥩 **isLoop** : %s
                                     
                                     """.formatted(
				playingTrack.name(), playingTrack.author(), playingTrack.getTimeToEnd(), isLoop));
		
		if (!viewQueue.isEmpty()) {
			queueTextList.append("**----------------------**\n");
		}
		
		for (var v : viewQueue) {
			queueTextList.append("""
                                         > **%s** by `%s`, time : %s
                                         
                                         """.formatted(v.name(), v.author(), v.getDurationTime()));
		}
		
		return queueTextList.toString();
	}
}
