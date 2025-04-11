-- for deleting data from table user
create or replace procedure sp_delete_from_users(  
    p_USER_PKEY               in users.user_pkey%type    
)
is
    notes_pkeys TYPE_NOTES_PKEYS;
begin    
        
    select notes_pkey bulk collect into notes_pkeys from user_notes 
        where user_pkey = p_USER_PKEY;

    -- first deleting the row from user_notes table
--    delete from user_notes 
--        where notes_pkey 
--        in( select notes_pkey 
--            from user_notes
--            where user_pkey = p_USER_PKEY);    
            
    -- first deleting the row from user_notes table        
    delete from user_notes where notes_pkey in(select column_value from table(notes_pkeys));

    -- second deleting the note from the main notes table
    delete from notes where notes_pkey in(select column_value from table(notes_pkeys)) ;
    
    -- third , deleting the user after all the related from all respective
    -- tables have been removed
    delete from users where user_pkey = p_USER_PKEY;
end;
/
