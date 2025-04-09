create table user_notes(
    user_pkey varchar(32),
    notes_pkey varchar(32),
    
    CONSTRAINT fk_user_pkey foreign key(user_pkey) REFERENCES users(user_pkey),
    CONSTRAINT fk_notes_pkey foreign key(notes_pkey) REFERENCES notes(notes_pkey),
    constraint unique_combination unique(user_pkey,notes_pkey)
);

select table_name from user_tables;