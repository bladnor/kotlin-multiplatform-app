create table event
(
	id bigint not null
		primary key,
	description varchar(255) null,
	name varchar(255) null
)
;


create table hibernate_sequence
(
	next_val bigint null
)
;


INSERT INTO hibernate_sequence(next_val) VALUES (1);
