package com.adrros.friendfromdis.command.music.add;

import com.adrros.friendfromdis.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.adrros.friendfromdis.util.ValidateEnterToCommand.isCommandInvalid;

public class AddMusicCommand extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(@NotNull final MessageReceivedEvent eventRaw) {
		if (isCommandInvalid(eventRaw, Commands.ADD_MUSIC))
			return;
		
		eventRaw.getMessage().getAttachments().forEach(attachment ->
			attachment.getProxy().download().thenAccept(inputStream -> {
				try {
					File file = new File("src/main/resources/" + attachment.getFileName());
					Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
					System.out.println("File saved successfully: " + file.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).exceptionally(throwable -> {
				throwable.printStackTrace();
				return null;
			})
		);
	}
	
}
