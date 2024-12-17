package com.adrros.friendfromdis.lavaplayer;

import com.adrros.friendfromdis.command.music.play.buttonlisteners.PlayCommandUtils;
import com.adrros.friendfromdis.command.music.play.SavedSong;
import com.adrros.friendfromdis.command.music.play.buttonlisteners.PlayDropDown;
import com.adrros.friendfromdis.command.music.play.SongsToPlayStore;
import com.adrros.friendfromdis.domain.AddSoundService;
import com.adrros.friendfromdis.domain.Sound;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.adrros.friendfromdis.Commands.PLAY;

public class PlayerManager {
//    public static final String PATH_TO_LOCAL_MP3 = "../";
//    public static final String PATH_TO_LOCAL_MP3 = "src/main/resources/";
//    public static final String PATH_TO_LOCAL_MP3 = "src\\main\\resources\\";
    public static final String PATH_TO_LOCAL_MP3 = "\\";
    
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
//        doLoadAndPlay(channel, trackUrlOrName, silent, musicManager);
    }

    public static void loadAndPlay(TextChannel channel, String trackUrlOrName, List<String> additionalSongNames, AddSoundService addSoundService, boolean silent) {
        doLoadAndPlay(channel, trackUrlOrName, silent, getMusicManager(channel.getGuild()), additionalSongNames, addSoundService);
    }

    private static void doLoadAndPlay(final TextChannel channel, String trackUrlOrName, final boolean silent, final GuildMusicManager musicManager, List<String> additionalSongNames, AddSoundService addSoundService) {
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
            // * triggered when doLoadAndPlay() has trackUrlOrName as a <tittle>.mp3
            @Override
            public void trackLoaded(AudioTrack track) {
                PlayerManager.log.info("start trackLoaded, track: [%s]".formatted(track));
                PlayerManager.log.info("2 founded music manager from THE MAP: " + String.valueOf(PlayerManager.musicManagers));
                musicManager.scheduler.addToQueue(track);
                if (!silent) {
                    channel.sendMessage("Adding to queue: `");
                }

            }
            // * triggered when doLoadAndPlay() has trackUrlOrName as a restResource:<tittle>
            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<String> songTitles = new ArrayList<>();
                songTitles.addAll(additionalSongNames);
                songTitles.addAll(playlist.getTracks().stream().map((track) -> track.getInfo().title).toList());
                
                BiConsumer<String, Boolean> consumer = (songName, playFromDataBase) -> {
                    // * if user selected song which is saved in the DB
                    if (playFromDataBase) {
                        // remove prefix
                        final String fileName = songName.replaceAll(SavedSong.SAVED_SONG.getName(), "");
                        // create local file
                        final Sound byName = addSoundService.getByName(fileName);
                        createLocalFile(byName);
                        doLoadAndPlay(channel, PATH_TO_LOCAL_MP3 + fileName, silent, getMusicManager(channel.getGuild()), additionalSongNames, addSoundService);
                        showInfoBoxOnChannel(channel, fileName);
                        return;
                    }
                    PlayerManager.log.info("Adding to queue");
                    
                    List<AudioTrack> tracks = playlist.getTracks();
                    AudioTrack first = tracks.stream().filter((audioTrack) -> audioTrack.getInfo().title.equals(songName)).findFirst().orElseThrow();
                    
                    showInfoBoxOnChannel(channel, first.getInfo().title);
                    musicManager.scheduler.addToQueue(first);
                };

                SongsToPlayStore.addSong(channel.getId(), consumer);
                new PlayDropDown().createAndRunDropDown(channel, songTitles);
            }
            @Override
            public void noMatches() {
                System.out.println("noMatches ERROR (find me)");
            }
            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("loadFailed ERROR (find me)");
            }
        });
    }
    
    private static void showInfoBoxOnChannel(TextChannel channel, String songName) {
        final MessageCreateData messageCreateData = MessageCreateData.fromContent(PLAY.name());
        final MessageCreateData build = MessageCreateBuilder.from(messageCreateData)
                .addActionRow(PlayCommandUtils.playItemButtons())
                .addEmbeds(PlayCommandUtils.getPlaySongEmbed(songName))
                .build();
        
        channel.sendMessage(build).queue();
    }
    
    private static void createLocalFile(Sound sound) {
//        try {
//            FileUtils.writeByteArrayToFile(new File(nextSound.getSoundName() + ".mp3"), nextSound.getSoundData());
//        } catch (IOException e) {
//            log.error("RadioPlayer.playNextSong fail during FileUtils.writeByteArrayToFile");
//            throw new RuntimeException(e);
//        }
        
        InputStream inputStream = new ByteArrayInputStream(sound.getSoundData());
        try {
					File file = new File(PATH_TO_LOCAL_MP3 + sound.getSoundName());
					Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("RadioPlayer.playNextSong fail during FileUtils.writeByteArrayToFile");
            throw new RuntimeException(e);
        }
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
