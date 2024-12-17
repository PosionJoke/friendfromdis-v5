package com.adrros.friendfromdis.command.music.play;


import com.adrros.friendfromdis.Commands;
import com.adrros.friendfromdis.domain.AddSoundService;
import com.adrros.friendfromdis.lavaplayer.PlayerManager;
import com.adrros.friendfromdis.lavaplayer.player_state.PlayerState;
import com.adrros.friendfromdis.util.BotState;
import com.adrros.friendfromdis.util.MessageFormatter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.commons.validator.routines.UrlValidator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.adrros.friendfromdis.command.music.MusicSource.SOUND_CLAUD;
import static com.adrros.friendfromdis.util.ValidateEnterToCommand.isCommandInvalid;

@RequiredArgsConstructor
public class PlayCommand extends ListenerAdapter {
	
	private final AddSoundService addSoundService;

	@Override
	public void onMessageReceived(@NotNull final MessageReceivedEvent eventRaw) {
		if (isCommandInvalid(eventRaw, Commands.PLAY))
			return;
		
		MessageChannelUnion channel = eventRaw.getChannel();
		joinToChannelIfNeeded(eventRaw, channel);
		
		List<String> additionalSongNames = new ArrayList<>();
		final String pureSongName = getPureSongName(eventRaw.getMessage().getContentRaw());
		List<String> songNamesFromDB = addSoundService.getSimilarSongNames(pureSongName);
		songNamesFromDB.stream()
				.map(s -> SavedSong.SAVED_SONG.getName() + s)
				.forEach(additionalSongNames::add);
		this.runPlayCommand(eventRaw.getMessage().getContentRaw(), eventRaw.getChannel(), additionalSongNames, addSoundService);
	}
	
	private static void joinToChannelIfNeeded(MessageReceivedEvent eventRaw, MessageChannelUnion channel) {
		Member self = ((Member) Objects.requireNonNull(eventRaw.getMember())).getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Member member = eventRaw.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();
		AudioManager audioManager = eventRaw.getGuild().getAudioManager();
		AudioChannelUnion memberChannel = ((GuildVoiceState) Objects.requireNonNull(memberVoiceState)).getChannel();
		PlayerState.setTextChannel(channel.asTextChannel());
		BotState.setSelf(selfVoiceState);
		BotState.setAudioManager(audioManager);
		BotState.setTextChannel(channel.asTextChannel());
		BotState.setVoiceChannel(((AudioChannelUnion) Objects.requireNonNull(memberChannel)).asVoiceChannel());
		audioManager.openAudioConnection(memberChannel);
		
	}
	
	private void runPlayCommand(String contentRaw, MessageChannelUnion channel, List<String> additionalSongNames, AddSoundService addSoundService) {
		PlayerState.setTextChannel(channel.asTextChannel());
		PlayerManager.loadAndPlay(channel.asTextChannel(), getLinkOrSongName(contentRaw), additionalSongNames, addSoundService, false);
	}
	
	private static String getLinkOrSongName(final String rawMessage) {
		List<String> prepareMessage = MessageFormatter.prepareMessage(rawMessage);
		StringBuilder allWordsToFind = new StringBuilder();
		
		for (String word : prepareMessage) {
			allWordsToFind.append(" ").append(word);
		}
		
		String link = allWordsToFind.toString().replace("=", "").replace("play", "");
		if (!isUrl(link)) {
			// * comment line below, now it should find mp3
			link = SOUND_CLAUD.source + link;
		}
		return link.trim();
	}
	
	private static boolean isUrl(String link) {
		return (new UrlValidator()).isValid(link);
	}
	
	private static String getPureSongName(final String rawMessage) {
		List<String> prepareMessage = MessageFormatter.prepareMessage(rawMessage);
		StringBuilder allWordsToFind = new StringBuilder();
		
		for (String word : prepareMessage) {
			allWordsToFind.append(" ").append(word);
		}
		
		String link = allWordsToFind.toString().replace("=", "").replace(Commands.PLAY.prefix, "");
//		if (!isUrl(link)) {
//			// * comment line below, now it should find mp3
//			link = SOUND_CLAUD.source + link;
//		}
//		link.replaceAll(" ", "");
		return link.trim();
	}
}
