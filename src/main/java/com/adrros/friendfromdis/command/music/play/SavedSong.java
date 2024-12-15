package com.adrros.friendfromdis.command.music.play;

import lombok.Getter;

@Getter
public enum SavedSong {
	SAVED_SONG("🦚Saved: ");
	
	private final String name;
	
	SavedSong(String value) {
		this.name = value;
	}
}
