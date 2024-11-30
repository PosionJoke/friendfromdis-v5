package com.adrros.friendfromdis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("mb_sound")
public class Sound {
	@org.springframework.data.annotation.Id
	private Long id;

	private String soundName;
	@Lob
	private byte[] soundData;
	
	private String addedBy;
	
	@Override
	public String toString() {
		return "Sound{" +
			   "id=" + id +
			   ", soundName='" + soundName + '\'' +
			   ", addedBy='" + addedBy + '\'' +
			   '}';
	}
}
