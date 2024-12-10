package com.adrros.friendfromdis.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddSoundServiceImpl implements AddSoundService {
	
	private final SoundRepository soundRepository;
	
	@Override
	public void addSound(AddSoundParam params) {
		try (InputStream inputStream = params.inputStream()) {
			Sound sound = new Sound(null, params.songName(), inputStream.readAllBytes(), params.addedBy());
			soundRepository.save(sound);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<String> getSimilarSongNames(String pureSongName) {
		final String queryParam = "%" + pureSongName + "%";
		return soundRepository.findAllWhereSongNameLike(queryParam);
	}
	
	@Override
	public Sound getByName(String pureSongName) {
		return soundRepository.getByName(pureSongName);
	}
	
}
