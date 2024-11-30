package com.adrros.friendfromdis;

public enum Commands {
	PLAY("play"),
	ADD_MUSIC("add");

	public final String PREFIX;
	
	Commands(String prefix) {
		PREFIX = prefix;
	}
}
