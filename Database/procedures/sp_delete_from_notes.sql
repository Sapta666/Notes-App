-- for deleting data from table notes
create or replace procedure sp_delete_from_notes(  
    p_NOTES_PKEY               in notes.notes_pkey%type    
)
is
begin    
    -- first deleting the row from user_notes table
    delete from user_notes where notes_pkey = p_NOTES_PKEY;

    -- second deleting the note from the main notes table
    delete from notes where notes_pkey = p_NOTES_PKEY;
end;
/