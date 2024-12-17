package com.adrros.friendfromdis.command.music.play.buttonlisteners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

record AudioTrackView(String name, String author, long position, long duration) {
	
	public String getTimeToEnd() {
		var toEnd = duration - position;
		
		Date date = new Date(toEnd);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}
	
	public String getDurationTime() {
		Date date = new Date(duration);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}
}
