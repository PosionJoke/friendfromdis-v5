
package com.adrros.friendfromdis.command.music.play;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;

public class SongsToPlayStore {
    private static final Map<String, BlockingQueue<BiConsumer<String, Boolean>>> songsToPlayMap = new HashMap<>();

    private SongsToPlayStore() {}
    
    public static void addSong(String channelIdAsKey, BiConsumer<String, Boolean> songToPlay) {
        if (containsKey(channelIdAsKey)) {
            addSongToPlay(songsToPlayMap.get(channelIdAsKey), songToPlay);
        } else {
            BlockingQueue<BiConsumer<String, Boolean>> queue = new LinkedBlockingQueue<>();
            addSongToPlay(queue, songToPlay);
            songsToPlayMap.put(channelIdAsKey, queue);
        }

    }
    
    private static void addSongToPlay(BlockingQueue<BiConsumer<String, Boolean>> songsToPlayMap, BiConsumer<String, Boolean> songToPlay) {
        songsToPlayMap.add(songToPlay);
    }
    
    public static BiConsumer<String, Boolean> get(String key) {
        if (!containsKey(key)) {
            throw new IllegalStateException("songsToPlayMap dosent contain any songs for this key: [%s]".formatted(key));
        } else {
            return songsToPlayMap.get(key).poll();
        }
    }
    
    private static boolean containsKey(String key) {
        return songsToPlayMap.containsKey(key);
    }
}
