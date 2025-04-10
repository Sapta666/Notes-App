create or replace function fn_gen_user_pkey
    return varchar2
is 
    v_seq_num number;
    v_seq_str varchar2(20);
begin
    select seq_user_pkey.nextval into v_seq_num from dual;
    v_seq_str := 'user' || v_seq_num;

    return v_seq_str;
end fn_gen_user_pkey;

drop function fn_gen_user_pkey;