package com.adrros.friendfromdis.domain;

import java.util.List;

public interface AddSoundService {
	void addSound(AddSoundParam params);
	
	List<String> getSimilarSongNames(String pureSongName);
	Sound getByName(String pureSongName);
}
