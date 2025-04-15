create table users (
    user_Pkey varchar(32),
    username varchar(32) not null unique,
    password varchar(64) not null unique,       
    first_name varchar(32),
    last_name varchar(32),
    create_numdt number,
    create_dectime number,
    is_admin varchar2(1) check(is_admin = 'Y' or is_admin = 'N'),
    CONSTRAINT pk_user_Pkey primary key(user_PKey)
);
