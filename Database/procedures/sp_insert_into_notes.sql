-- for inserting data into table notes
create or replace procedure sp_insert_into_notes(  
    p_NOTES_PKEY               out notes.notes_pkey%type,
    p_USER_PKEY                in users.user_pkey%type,  
    p_TITLE                    in notes.title%type,
    p_BODY                     in notes.body%type,
    p_CREATE_NUMDATE           in notes.create_numdate%type,
    p_CREATE_NUMTIME           in notes.create_numtime%type,
    p_LASTMOD_NUMDATE          in notes.lastmod_numdate%type,
    p_LASTMOD_DECTIME          in notes.lastmod_dectime%type
)
is
    gen_notes_pkey notes.notes_pkey%type;
begin    
    gen_notes_pkey := fn_gen_notes_pkey();
    p_NOTES_PKEY := gen_notes_pkey;
    
   -- first generating new notes_pkey and inserting in
   -- table notes
   insert into notes
   values ( gen_notes_pkey,                     
            p_USER_PKEY,
            p_TITLE,
            p_BODY,
            p_CREATE_NUMDATE,
            p_CREATE_NUMTIME,
            p_LASTMOD_NUMDATE,
            p_LASTMOD_DECTIME
        );   
end;
/

show error procedure sp_insert_into_notes;