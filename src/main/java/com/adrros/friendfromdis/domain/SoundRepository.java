package com.adrros.friendfromdis.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SoundRepository extends CrudRepository<Sound, Long> {
	
	@Query("select p.id from sound p")
	List<Long> getAllIds();
	
	@Query("select s.id from sound s " +
			"left join play_list_sound pls on s.id = pls.sound_id " +
			"where s.played_to_day = false and pls.play_list_id = :playListId")
	List<Long> getAllSoundsIdsByPlayListAndPlayedToDayFalse(@Param("playListId") Long playListId);
	
	
	@Query(value = "select s.id, sound_key, s.sound_name, s.played_to_day from sound s where sound_key = :soundKey")
	Sound findBySoundKeyWithoutData(@Param("soundKey") Long soundKey);
	
	@Query(value = "select s.id, sound_key, s.sound_name, s.played_to_day from sound s")
	List<Sound> findWithoutFileData();
	
	@Query(value = "select id, sound_key, sound_name, played_to_day from sound")
	List<Sound> findAllWithoutData();
	
	@Query(value = "select id, sound_key, sound_name, played_to_day from sound where id in (:ids)")
	List<Sound> findAllByIdInWithoutData(@Param("ids") List<Long> ids);
	
	@Query(value = "select id, sound_key, sound_name, played_to_day from sound where sound.sound_name = :soundName")
	Optional<Sound> findBySoundNameWithoutData(@Param("soundName") String soundName);
	
	@Query("select id, sound_key, sound_name, played_to_day from sound p " +
			"left join play_list_sound pls on p.id = pls.sound_id " +
			"where pls.play_list_id = :playListId and pls.sound_id = p.id")
	List<Sound> getAllSoundsByPlayListWithoutData(@Param("playListId") Long playListId);
	
	@Modifying
	@Query(value = "UPDATE sound SET played_to_day = false WHERE id IN (:ids)")
	void setPlayedToDayFalseByIdIn(@Param("ids") List<Long> ids);
	
	@Modifying
	@Query(value = "UPDATE sound SET played_to_day = true WHERE id = :id")
	void setPlayedToDayTrueById(@Param("id") Long id);
	
	// GET WITH DATA
	@Query(value = "select * from sound where played_to_day = false and id = :id")
	Optional<Sound> findByPlayedToDayIsFalseAndIdWithoutData(@Param("id") Long id);
}
