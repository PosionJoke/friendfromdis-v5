package com.adrros.friendfromdis.util;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public final class BotState {
    private static AudioManager audioManager;
    private static TextChannel textChannel;
    private static VoiceChannel voiceChannel;
    private static GuildVoiceState self;

    private BotState() {
    }

    public static boolean isVoiceChannelEmpty() {
        return voiceChannel == null || voiceChannel.getMembers().isEmpty();
    }

    public static AudioManager getAudioManager() {
        return audioManager;
    }

    public static void setAudioManager(final AudioManager audioManager) {
        BotState.audioManager = audioManager;
    }

    public static TextChannel getTextChannel() {
        return textChannel;
    }

    public static void setTextChannel(final TextChannel textChannel) {
        BotState.textChannel = textChannel;
    }

    public static VoiceChannel getVoiceChannel() {
        return voiceChannel;
    }

    public static void setVoiceChannel(final VoiceChannel voiceChannel) {
        BotState.voiceChannel = voiceChannel;
    }

    public static GuildVoiceState getSelf() {
        return self;
    }

    public static void setSelf(final GuildVoiceState self) {
        BotState.self = self;
    }
}
