package com.adrros.friendfromdis.lavaplayer;

import com.adrros.friendfromdis.lavaplayer.player_state.PlayerState;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrackScheduler extends AudioEventAdapter {
    private static final Logger log = LogManager.getLogger(PlayerManager.class);
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue();
    }

    public void addToQueue(AudioTrack track) {
        log.info("In queue");
        if (!this.player.startTrack(track, true)) {
            log.info("this.queue.offer(track)");
            this.queue.offer(track);
            log.info("queue.stream().toList(); : " + String.valueOf(this.queue.stream().toList()));
        }

        if (PlayerState.isLoopOn()) {
            PlayerState.setLoopTrack(track.makeClone());
        }

    }

    public void nextTrack() {
        if (this.queue.isEmpty()) {
            log.info("TrackScheduler.nextTrack() | Can't play next track, because the queue is empty");
        } else {
            this.player.startTrack((AudioTrack)this.queue.poll(), false);
        }
    }

    public void stopTrack() {
        log.info("SkipCommand -> stopTrack()");
        AudioTrack playingTrack = this.player.getPlayingTrack();
        log.info("SkipCommand -> stopTrack() | playingTrack = " + String.valueOf(playingTrack));
        if (!Objects.isNull(playingTrack)) {
            log.warn("SkipCommand -> stopTrack(), playingTrack is null");
            log.warn("SkipCommand -> current track: " + playingTrack.getIdentifier());
            log.warn("SkipCommand -> current track duration: " + playingTrack.getDuration());
            log.warn("SkipCommand -> current track position before: " + playingTrack.getPosition());
            if (playingTrack.getDuration() >= Long.MAX_VALUE) {
                this.player.playTrack((AudioTrack)null);
            } else {
                playingTrack.setPosition(playingTrack.getDuration());
            }

            playingTrack.setPosition(playingTrack.getDuration());
            log.warn("SkipCommand -> current track position after: " + playingTrack.getPosition());
        }

        PlayerState.setLoopOn(false);
        log.info("SkipCommand -> stopTrack(), PlayerState.setLoopOn(false)");
        PlayerState.setLoopTrack((AudioTrack)null);
        log.info("SkipCommand -> stopTrack(), PlayerState.setLoopTrack(null)");
    }

    public void stopPlayList() {
        AudioTrack playingTrack = this.player.getPlayingTrack();
        if (!Objects.isNull(playingTrack)) {
            log.warn("playingTrack is null");
            playingTrack.setPosition(playingTrack.getDuration());
        }

        PlayerState.setLoopOn(false);
        PlayerState.setLoopPlayListOn(false);
        PlayerState.setLoopTrack((AudioTrack)null);
    }

    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        log.info("TrackScheduler -> onTrackEnd(), track named : %s has ended".formatted(track.getInfo().title));
        log.info("TrackScheduler -> onTrackEnd(), there is no play list to play");
        log.info("TrackScheduler -> onTrackEnd(), loop is on : %s".formatted(PlayerState.isLoopOn()));
        log.info("TrackScheduler -> onTrackEnd(), try to play next song, endReason.mayStartNext : " + endReason.mayStartNext);
        log.info("TrackScheduler -> onTrackEnd(), try to play next song, endReason : " + String.valueOf(endReason));
        if (PlayerState.isLoopOn()) {
            log.info("TrackScheduler -> onTrackEnd(), loop is on, lets play next song");
            PlayerState.setLoopTrack(track.makeClone());
            this.addToQueue(PlayerState.getLoopTrack());
        } else {
            this.nextTrack();
        }
    }

    public List<AudioTrack> getQueue() {
        return this.queue.stream().toList();
    }

    public AudioTrack getPlayingTrack() {
        return this.player.getPlayingTrack();
    }
}
