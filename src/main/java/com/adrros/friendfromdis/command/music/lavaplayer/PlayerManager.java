package com.adrros.friendfromdis.command.music.lavaplayer;

import com.adrros.friendfromdis.command.music.play.buttions.PlayDropDown;
import com.adrros.friendfromdis.command.music.play.buttions.SongsToPlayStore;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerManager {
    private static final Logger log = LogManager.getLogger(PlayerManager.class);
    private static final Map<Long, GuildMusicManager> musicManagers = new HashMap();
    private static final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    private static boolean isFirstRun = true;
    private static boolean timeToRestart = false;

    public PlayerManager() {
    }

    public static List<GuildMusicManager> getMusicManager() {
        return new ArrayList(musicManagers.values());
    }

    public static GuildMusicManager getMusicManager(Guild guild) {
        log.info("getMusicManager, from guild.name: " + guild.getName());
        return (GuildMusicManager)musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public static void deleteGuildMusicManager(Guild guild) {
        log.info("musicManagers.remove(guild.getIdLong());");
        musicManagers.remove(guild.getIdLong());
        log.info("Id of removing guild: " + guild.getIdLong());
        Map var10001 = musicManagers;
        log.info("Does map still contain the guild: " + var10001.containsKey(guild.getIdLong()));
    }

    public static boolean doesExistById(Guild guild) {
        return musicManagers.containsKey(guild.getIdLong());
    }

    public static void loadAndPlay(TextChannel channel, String trackUrlOrName, boolean silent, GuildMusicManager musicManager) {
        doLoadAndPlay(channel, trackUrlOrName, silent, musicManager);
    }

    public static void loadAndPlay(TextChannel channel, String trackUrlOrName, boolean silent) {
        doLoadAndPlay(channel, trackUrlOrName, silent, getMusicManager(channel.getGuild()));
    }

    private static void doLoadAndPlay(final TextChannel channel, String trackUrlOrName, final boolean silent, final GuildMusicManager musicManager) {
        if (isFirstRun || timeToRestart) {
            log.info("isFirst run: " + isFirstRun + " or timeToRestart: " + timeToRestart);
            log.info(audioPlayerManager);
            AudioSourceManagers.registerRemoteSources(audioPlayerManager);
            AudioSourceManagers.registerLocalSource(audioPlayerManager);
            isFirstRun = false;
            timeToRestart = false;
        }

        log.info("In loadAndPlay, channel: [%s], trackUrlOrName: [%s], silent: [%s]".formatted(channel, trackUrlOrName, silent));
        log.info("1 founded music manager from THE MAP: " + String.valueOf(musicManagers));
        musicManagers.entrySet().stream().forEach((entry) -> log.info("entry in the map: " + String.valueOf(entry.getKey())));
        audioPlayerManager.loadItemOrdered(musicManager, trackUrlOrName, new AudioLoadResultHandler() {
            public void trackLoaded(AudioTrack track) {
                PlayerManager.log.info("start trackLoaded, track: [%s]".formatted(track));
                PlayerManager.log.info("2 founded music manager from THE MAP: " + String.valueOf(PlayerManager.musicManagers));
                musicManager.scheduler.addToQueue(track);
                if (!silent) {
                    channel.sendMessage("Adding to queue: `");
                }

            }

            public void playlistLoaded(AudioPlaylist playlist) {
                Consumer<String> consumer = (empty) -> {
                    PlayerManager.log.info("Adding to queue");
                    List<AudioTrack> tracks = playlist.getTracks();
                    AudioTrack first = (AudioTrack)tracks.stream().filter((audioTrack) -> audioTrack.getInfo().title.equals(empty)).findFirst().orElseThrow();
                    channel.sendMessage("Adding to queue: `" + first.getInfo().title);
                    musicManager.scheduler.addToQueue(first);
                };
                SongsToPlayStore.addSong(channel.getId(), consumer);
                (new PlayDropDown()).createDropDown(channel, playlist.getTracks().stream().map((track) -> track.getInfo().title).toList());
            }

            public void noMatches() {
                System.out.println("noMatches ERROR (find me)");
            }

            public void loadFailed(FriendlyException exception) {
                System.out.println("loadFailed ERROR (find me)");
            }
        });
    }

    public static void setTimeToRestart(boolean timeToRestart) {
        PlayerManager.timeToRestart = timeToRestart;
    }

    public static record PlaySongParams(GuildMusicManager musicManager, AudioPlaylist playlist, TextChannel channel) {
        public PlaySongParams(GuildMusicManager musicManager, AudioPlaylist playlist, TextChannel channel) {
            this.musicManager = musicManager;
            this.playlist = playlist;
            this.channel = channel;
        }

        public GuildMusicManager musicManager() {
            return this.musicManager;
        }

        public AudioPlaylist playlist() {
            return this.playlist;
        }

        public TextChannel channel() {
            return this.channel;
        }
    }
}
