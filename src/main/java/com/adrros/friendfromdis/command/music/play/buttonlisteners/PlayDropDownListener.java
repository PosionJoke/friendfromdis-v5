package com.adrros.friendfromdis.command.music.play.buttonlisteners;

import com.adrros.friendfromdis.command.music.play.SavedSong;
import com.adrros.friendfromdis.command.music.play.SongsToPlayStore;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Optional;
import java.util.function.BiConsumer;

public class PlayDropDownListener extends ListenerAdapter {
	
	@Override
	public void onStringSelectInteraction(StringSelectInteractionEvent event) {
		if (event.getComponentId().equals("menu:id")) {
			if (event.getInteraction().getValues().isEmpty())
				throw new IllegalStateException("No song selected");
			
			String channelId = event.getChannel().getId();
			String selectedSongName = event.getInteraction().getValues().getFirst();
			boolean isFromDb = selectedSongName.startsWith(SavedSong.SAVED_SONG.getName());
			BiConsumer<String, Boolean> songToPlayConsumerNullable = SongsToPlayStore.get(channelId);
			
			Optional.ofNullable(songToPlayConsumerNullable)
					.ifPresentOrElse(
							(BiConsumer<String, Boolean> consumer) -> playSong(consumer, selectedSongName, isFromDb),
							() -> event.reply("Can not find any song to play").queue()
					);
		}
		
	}
	
	private static void playSong(BiConsumer<String, Boolean> consumer, String selectedSongName, boolean isFromDb) {
		consumer.accept(selectedSongName, isFromDb);
	}
}
