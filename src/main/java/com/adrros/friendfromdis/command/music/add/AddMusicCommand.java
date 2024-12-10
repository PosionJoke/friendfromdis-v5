package com.adrros.friendfromdis.command.music.add;

import com.adrros.friendfromdis.Commands;
import com.adrros.friendfromdis.domain.AddSoundParam;
import com.adrros.friendfromdis.domain.AddSoundService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Stack;

import static com.adrros.friendfromdis.util.ValidateEnterToCommand.isCommandInvalid;

public class AddMusicCommand extends ListenerAdapter {
	
	private final AddSoundService addSoundService;
	
	public AddMusicCommand(AddSoundService addSoundService) {
		this.addSoundService = addSoundService;
	}
	
	@Override
	public void onMessageReceived(@NotNull final MessageReceivedEvent eventRaw) {
		if (isCommandInvalid(eventRaw, Commands.ADD_MUSIC))
			return;
		if (!userCanAddSong(eventRaw))
			return;
		
		eventRaw.getMessage().getAttachments().forEach(attachment -> {
					if (!Objects.equals(attachment.getContentType(), "audio/mpeg"))
						return;
					
					attachment.getProxy().download().thenAccept(inputStream -> {
						try {
//					File file = new File("src/main/resources/" + attachment.getFileName());
//					Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
							addSoundService.addSound(new AddSoundParam(inputStream, attachment.getFileName(), eventRaw.getAuthor().getName()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}).exceptionally(throwable -> {
						throwable.printStackTrace();
						return null;
					});
				}
		);
	}

	private boolean userCanAddSong(MessageReceivedEvent eventRaw) {
		return Objects.requireNonNull(eventRaw.getMember())
				.getRoles()
				.stream()
				.anyMatch(role -> role.getName().equals("add_music"));
	}
	
}
