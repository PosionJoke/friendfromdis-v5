package com.adrros.friendfromdis.domain;

import java.io.InputStream;

public record AddSoundParam(InputStream inputStream, String songName, String addedBy) {
}
