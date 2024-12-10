package com.adrros.friendfromdis.command.music.play;

public enum SavedSong {
	SAVED_SONG("🦚Saved: ");
	
	private final String name;
	
	SavedSong(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
}
