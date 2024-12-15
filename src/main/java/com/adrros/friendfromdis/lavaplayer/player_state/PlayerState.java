//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.lavaplayer.player_state;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class PlayerState {
    private static boolean loopOn = false;
    private static boolean loopPlayListOn = false;
    private static AudioTrack loopTrack = null;
    private static TextChannel textChannel = null;
    private static int shortMemeMin = 1;
    private static int shortMemeMax = 100;
    private static int expectMemeValue = 50;
    private static int expectOffMusic = 5;

    private PlayerState() {
    }

    public static boolean channelExists() {
        return textChannel != null;
    }

    public static void setLoopOn(boolean b) {
        loopOn = b;
    }

    public static boolean isLoopOn() {
        return loopOn;
    }
    
    public static void changeIsLoopOn() {
        loopOn = !loopOn;
    }

    public static boolean isLoopPlayListOn() {
        return loopPlayListOn;
    }

    public static void setLoopPlayListOn(boolean loopPlayListOn) {
        PlayerState.loopPlayListOn = loopPlayListOn;
    }

    public static AudioTrack getLoopTrack() {
        return loopTrack;
    }

    public static void setLoopTrack(AudioTrack loopTrack) {
        PlayerState.loopTrack = loopTrack;
    }

    public static TextChannel getTextChannel() {
        return textChannel;
    }

    public static void setTextChannel(TextChannel textChannel) {
        PlayerState.textChannel = textChannel;
    }

    public static int getShortMemeMin() {
        return shortMemeMin;
    }

    public static void setShortMemeMin(int shortMemeMin) {
        PlayerState.shortMemeMin = shortMemeMin;
    }

    public static int getShortMemeMax() {
        return shortMemeMax;
    }

    public static void setShortMemeMax(int shortMemeMax) {
        PlayerState.shortMemeMax = shortMemeMax;
    }

    public static int getExpectMemeValue() {
        return expectMemeValue;
    }

    public static void setExpectMemeValue(int expectMemeValue) {
        PlayerState.expectMemeValue = expectMemeValue;
    }

    public static int getExpectOffMusic() {
        return expectOffMusic;
    }

    public static void setExpectOffMusic(int expectOffMusic) {
        PlayerState.expectOffMusic = expectOffMusic;
    }
}
