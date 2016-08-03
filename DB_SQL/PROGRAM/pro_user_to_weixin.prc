CREATE OR REPLACE procedure pro_user_to_weixin is
  p_weixin_user_id t_weixin_user.id%type;
  p_start_time     date := sysdate;
  p_error          varchar2(1000);
  v_sql            varchar2(100);
begin
  for c in (select t.user_id, t.jz_code, t.user_code, t.real_name
              from t_user t
             where t.status = '1'
               and t.jz_code is not null
               and not exists
             (select 1 from t_weixin_user a where a.jz_code = t.jz_code)) loop
    select s_weixin_user_id.nextval into p_weixin_user_id from dual;
    v_sql := 'alter sequence s_weixin_user_id increment by 1';
    execute immediate v_sql;
    insert into t_weixin_user t
      select p_weixin_user_id, c.jz_code, c.real_name, '1', sysdate
        from dual;
    insert into t_weixin_user_agent
      select p_weixin_user_id,
             t.app_id,
             t.app_name,
             c.user_code,
             p_weixin_user_id
        from t_weixin_apps t
       where t.app_id = 6;
  end loop;
  insert into t_batch_log
    select s_batch_log.nextval,
           'pro_user_to_weixin',
           '1',
           p_start_time,
           sysdate,
           '批处理成功',
           sysdate
      from dual;
  commit;
exception
  when others then
    rollback;
    p_error := sqlerrm;
    --插入批处理记录表
    insert into t_batch_log
      select s_batch_log.nextval,
             'pro_user_to_weixin',
             '0',
             p_start_time,
             sysdate,
             '批处理失败' || p_weixin_user_id ||
             dbms_utility.format_error_backtrace || p_error,
             sysdate
        from dual;
    commit;
  
end pro_user_to_weixin;

/
