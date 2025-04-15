--  DB CREATION SQL

-- create pluggable database notes_app 
-- admin user notesadmin identified by notesadmin_notesapppdb
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
    
-- for testing purpose
    
insert into users (user_pkey,username,password,is_admin)
    values ('pkey1','user101','pass101','Y');
    
delete from users where user_pkey = 'pkey1';

select * from users;

declare
    notes_pkey VARCHAR2(32) := '';
begin
    sp_insert_into_notes(
    notes_pkey,
    'pkey1',
    'title test',
    'body test',
    null,
    null,
    null,
    null );
end;

select * from users;
select * from user_notes;
select * from notes;

execute sp_delete_from_notes('note102');
execute sp_delete_from_users('pkey1');

commit;


-- ==========================================================================


alter table users modify is_admin varchar2(1) check(is_admin = 'Y' or is_admin = 'N');

alter table users modify  password varchar(64);       

select * from user_objects where object_type = 'INDEX';
desc user_objects;

commit;

select * from users;

insert into users values('usertemp','bastet666','bastet366','fname','lname',0,0,'N');

delete from users where user_pkey = 'user109';
update users set username = 'bastet666' where user_pkey = 'user110';
