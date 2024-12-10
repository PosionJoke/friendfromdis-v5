package com.adrros.friendfromdis;

import com.adrros.friendfromdis.command.music.add.AddMusicCommand;
import com.adrros.friendfromdis.command.music.play.PlayCommand;
import com.adrros.friendfromdis.command.music.play.buttions.PlayDropDownListener;
import com.adrros.friendfromdis.domain.AddSoundService;
import jakarta.annotation.PostConstruct;
import java.util.List;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunMusicBot {
	public static JDABuilder builder;
	public static JDA jda;
	
	private final BotConfigVariables botConfigVariables;
	private final AddSoundService addSoundService;
	
	@PostConstruct
	public void init() {
		this.setUp();
	}
	
	public void setUp() {
		builder = JDABuilder.createDefault(this.botConfigVariables.getBotKey());
		builder.setBulkDeleteSplittingEnabled(false);
		builder.setCompression(Compression.NONE);
		builder.setActivity(Activity.playing("TODO"));
		basicConfig();
		this.registerListeners();
		jda = builder.build();
	}
	
	private void registerListeners() {
		List<ListenerAdapter> listeners = List.of(
				new PlayCommand(addSoundService),
				new PlayDropDownListener(),
				new AddMusicCommand(addSoundService)
		);
		listeners.forEach((listener) -> builder.addEventListeners(new Object[]{listener}));
	}
	
	private static void basicConfig() {
		builder.disableCache(CacheFlag.MEMBER_OVERRIDES, new CacheFlag[0]);
		builder.disableCache(CacheFlag.ACTIVITY, new CacheFlag[0]);
		builder.enableCache(CacheFlag.VOICE_STATE, new CacheFlag[0]);
		builder.enableCache(CacheFlag.EMOJI, new CacheFlag[0]);
		builder.enableCache(CacheFlag.STICKER, new CacheFlag[0]);
		builder.enableCache(CacheFlag.CLIENT_STATUS, new CacheFlag[0]);
		builder.enableCache(CacheFlag.MEMBER_OVERRIDES, new CacheFlag[0]);
		builder.enableCache(CacheFlag.ROLE_TAGS, new CacheFlag[0]);
		builder.enableCache(CacheFlag.FORUM_TAGS, new CacheFlag[0]);
		builder.enableCache(CacheFlag.ONLINE_STATUS, new CacheFlag[0]);
		builder.enableCache(CacheFlag.SCHEDULED_EVENTS, new CacheFlag[0]);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_MODERATION, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_EMOJIS_AND_STICKERS, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_WEBHOOKS, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_INVITES, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_PRESENCES, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGES, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGES, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, new GatewayIntent[0]);
		builder.enableIntents(GatewayIntent.SCHEDULED_EVENTS, new GatewayIntent[0]);
	}
}
