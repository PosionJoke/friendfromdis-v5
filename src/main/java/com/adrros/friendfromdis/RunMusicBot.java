package com.adrros.friendfromdis;

import com.adrros.friendfromdis.command.music.add.AddMusicCommand;
import com.adrros.friendfromdis.command.music.loop.Loop;
import com.adrros.friendfromdis.command.music.play.PlayCommand;
import com.adrros.friendfromdis.command.music.play.PlayDropDownListener;
import com.adrros.friendfromdis.domain.AddSoundService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunMusicBot {
	private JDABuilder builder;
	
	private final BotConfigVariables botConfigVariables;
	private final AddSoundService addSoundService;
	
	@PostConstruct
	public void init() {
		this.createJda();
	}
	
	public void createJda() {
		builder = JDABuilder.createDefault(this.botConfigVariables.getBotKey());
		builder.setBulkDeleteSplittingEnabled(false);
		builder.setCompression(Compression.NONE);
		builder.setActivity(Activity.playing("Baron i zamykamy midem"));
		basicConfig();
		this.registerListeners();
		builder.build();
	}
	
	private void registerListeners() {
		List<ListenerAdapter> listeners = List.of(
				new PlayCommand(addSoundService),
				new PlayDropDownListener(),
				new Loop(),
				new AddMusicCommand(addSoundService)
		);
		listeners.forEach((ListenerAdapter listener) -> builder.addEventListeners(listener));
	}
	
	private void basicConfig() {
		builder.disableCache(CacheFlag.MEMBER_OVERRIDES);
		builder.disableCache(CacheFlag.ACTIVITY);
		builder.enableCache(CacheFlag.VOICE_STATE);
		builder.enableCache(CacheFlag.EMOJI);
		builder.enableCache(CacheFlag.STICKER);
		builder.enableCache(CacheFlag.CLIENT_STATUS);
		builder.enableCache(CacheFlag.MEMBER_OVERRIDES);
		builder.enableCache(CacheFlag.ROLE_TAGS);
		builder.enableCache(CacheFlag.FORUM_TAGS);
		builder.enableCache(CacheFlag.ONLINE_STATUS);
		builder.enableCache(CacheFlag.SCHEDULED_EVENTS);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		builder.enableIntents(GatewayIntent.GUILD_MODERATION);
		builder.enableIntents(GatewayIntent.GUILD_EMOJIS_AND_STICKERS);
		builder.enableIntents(GatewayIntent.GUILD_WEBHOOKS);
		builder.enableIntents(GatewayIntent.GUILD_INVITES);
		builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
		builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGES);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING);
		builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
		builder.enableIntents(GatewayIntent.SCHEDULED_EVENTS);
	}
}
