create table notes(
    notes_pkey varchar(32),
    user_pkey varchar(32),
    title varchar(64) not null,
    body varchar(256),
    create_numdate number,
    create_numtime number,
    lastmod_numdate number,
    lastmod_dectime number,
    
    constraint pk_notes_pkey primary key(notes_pkey),
    CONSTRAINT fk_user_pkey foreign key(user_pkey) REFERENCES users(user_pkey)
);


select * from notes;

commit;

desc notes;
