//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.command.music.play;


import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.adrros.friendfromdis.BotConfigVariables;
import com.adrros.friendfromdis.command.music.lavaplayer.PlayerManager;
import com.adrros.friendfromdis.command.music.lavaplayer.player_state.PlayerState;
import com.adrros.friendfromdis.util.BotState;
import com.adrros.friendfromdis.util.MessageFormatter;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.commons.validator.routines.UrlValidator;

public class PlayCommand extends ListenerAdapter {
    public PlayCommand() {
    }

    public void onMessageReceived(final MessageReceivedEvent eventRaw) {
        if (!eventRaw.getAuthor().isBot()) {
            if (!eventRaw.getMessage().getContentRaw().isBlank()) {
                if (this.isPrefixCorrect(eventRaw.getMessage().getContentRaw())) {
                    MessageChannelUnion channel = eventRaw.getChannel();
                    joinToChannelIfNeeded(eventRaw, channel);
                    channel.sendMessage("I'm alive!").queue();
                    this.runPlayCommand(eventRaw.getMessage().getContentRaw(), eventRaw.getMember(), eventRaw.getChannel(), eventRaw.getAuthor().getAvatarUrl(), true);
                }
            }
        }
    }

    private static void joinToChannelIfNeeded(MessageReceivedEvent eventRaw, MessageChannelUnion channel) {
        Member self = ((Member)Objects.requireNonNull(eventRaw.getMember())).getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        if (selfVoiceState.inAudioChannel()) {
            channel.sendMessage("I'm already in a voice channel").queue();
        } else {
            Member member = eventRaw.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();
            AudioManager audioManager = eventRaw.getGuild().getAudioManager();
            AudioChannelUnion memberChannel = ((GuildVoiceState)Objects.requireNonNull(memberVoiceState)).getChannel();
            PlayerState.setTextChannel(channel.asTextChannel());
            BotState.setSelf(selfVoiceState);
            BotState.setAudioManager(audioManager);
            BotState.setTextChannel(channel.asTextChannel());
            BotState.setVoiceChannel(((AudioChannelUnion)Objects.requireNonNull(memberChannel)).asVoiceChannel());
            audioManager.openAudioConnection(memberChannel);
        }

    }

    private void runPlayCommand(String contentRaw, Member member, MessageChannelUnion channel, String avatarUrl, boolean b) {
        PlayerState.setTextChannel(channel.asTextChannel());
        PlayerManager.loadAndPlay(channel.asTextChannel(), getLinkOrSongName(contentRaw), false);
    }

    private static String getLinkOrSongName(final String rawMessage) {
        List<String> prepareMessage = MessageFormatter.prepareMessage(rawMessage);
        StringBuilder allWordsToFind = new StringBuilder();

        for(String word : prepareMessage) {
            allWordsToFind.append(" ").append(word);
        }

        String link = allWordsToFind.toString().replace("=", "").replace("play", "");
        if (!isUrl(link)) {
            link = "scsearch:" + link;
        }

        return link;
    }

    private static boolean isUrl(String link) {
        return (new UrlValidator()).isValid(link);
    }

    private boolean isPrefixCorrect(String rawMessage) {
        Stream<String> var10000 = BotConfigVariables.getPrefixes().stream();
        Objects.requireNonNull(rawMessage);
        return var10000.anyMatch(rawMessage::startsWith);
    }
}
