create table notes(
    notes_Pkey varchar(32),
    title varchar(64) not null,
    body varchar(256),
    create_numdate number,
    create_numtime number,
    lastmod_numdate number,
    lastmod_dectime number,
    
    constraint pk_notes_pkey primary key(notes_pkey)
);