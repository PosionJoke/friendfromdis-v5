create table if not exists mb_sound
(
	id            serial primary key,
	sound_name    varchar(255),
	sound_data    bytea,
	added_by    varchar(255)
);
