//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.command.music.play.buttions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class SongsToPlayStore {
    private static final Map<String, BlockingQueue<Consumer<String>>> songsToPlayMap = new HashMap();

    public SongsToPlayStore() {
    }

    public static void addSong(String key, Consumer<String> songToPlay) {
        if (songsToPlayMap.containsKey(key)) {
            ((BlockingQueue)songsToPlayMap.get(key)).add(songToPlay);
        } else {
            BlockingQueue<Consumer<String>> queue = new LinkedBlockingQueue();
            queue.add(songToPlay);
            songsToPlayMap.put(key, queue);
        }

    }

    public static Consumer<String> get(String key) {
        if (!songsToPlayMap.containsKey(key)) {
            throw new IllegalStateException("songsToPlayMap dosent contain any songs for this key: [%s]".formatted(key));
        } else {
            return (Consumer)((BlockingQueue)songsToPlayMap.get(key)).poll();
        }
    }

    public static void remove(String key) {
        songsToPlayMap.remove(key);
    }

    public static void clear() {
        songsToPlayMap.clear();
    }
}
