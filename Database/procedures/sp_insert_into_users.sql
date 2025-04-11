-- for inserting data into table users
create or replace procedure sp_insert_into_users(  
 p_USER_PKEY             out users.user_pkey%type,
 p_USERNAME              in users.username%type,
 p_PASSWORD              in users.password%type, 
 p_FIRST_NAME            in users.first_name%type,
 p_LAST_NAME             in users.last_name%type,
 p_CREATE_NUMDT          in users.create_numdt%type,
 p_CREATE_DECTIME        in users.create_dectime%type,
 p_IS_ADMIN              in users.is_admin%type
)
is
    gen_user_pkey users.user_pkey%type;
begin    
    gen_user_pkey := fn_gen_user_pkey();
    p_USER_PKEY := gen_user_pkey;
       insert into users
       values ( gen_user_pkey,     
                p_USERNAME,
                p_PASSWORD,
                p_FIRST_NAME,
                p_LAST_NAME,
                p_CREATE_NUMDT,
                p_CREATE_DECTIME,
                p_IS_ADMIN
            );
end;
/