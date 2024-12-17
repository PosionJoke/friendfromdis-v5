package com.adrros.friendfromdis;

public enum Commands {
	PLAY("play"),
	LOOP("loop"),
	SKIP("skip"),
	QUEUE("queue"),
	ADD_MUSIC("add");

	public final String prefix;
	
	Commands(String prefix) {
		this.prefix = prefix;
	}
}
