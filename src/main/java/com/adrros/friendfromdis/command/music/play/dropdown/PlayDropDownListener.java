//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.command.music.play.dropdown;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.adrros.friendfromdis.command.music.play.SavedSong;
import com.adrros.friendfromdis.command.music.play.SongsToPlayStore;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayDropDownListener extends ListenerAdapter {
	public PlayDropDownListener() {
	}
	
	public void onStringSelectInteraction(StringSelectInteractionEvent event) {
		if (event.getComponentId().equals("menu:id")) {
			List<String> selected = event.getValues();
//            event.reply("Your selection was processed").queue();
			String id = event.getChannel().getId();
			BiConsumer<String, Boolean> playSongParamsConsumerNullable = SongsToPlayStore.get(id);
			Optional.ofNullable(playSongParamsConsumerNullable).orElseThrow();
			
			String s = event.getInteraction().getValues().get(0);
			boolean isFromDb = s.startsWith(SavedSong.SAVED_SONG.getName());
			Optional.ofNullable(playSongParamsConsumerNullable)
					.ifPresentOrElse(
							(BiConsumer<String, Boolean> consumer) -> consumer.accept(s, isFromDb),
							() -> event.reply("Can not find any song to play").queue()
					);
			
		}
		
	}
}
