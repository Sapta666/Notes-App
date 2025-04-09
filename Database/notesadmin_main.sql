--  DB CREATION SQL

-- create pluggable database notes_app 
-- admin user notesadmin identified by notesadmin_notes_app
-- FILE_NAME_CONVERT=('/pdbseed/'
--                    ,'/notes_app_pdb/');
                    
-- ============================================================================                   
                    
-- For setting the database state to open , must be executed by sys

-- alter pluggable database notes_app open;

-- ============================================================================

-- For granting notesadmin user necessary priviledges , must be executed by sys

-- alter session set container = notes_app;

-- grant dba,connect,resource to notesadmin;

-- ============================================================================

select * from user_tables;

select * from all_tables;

select * from dual;

create table abc(
    id number);