CREATE OR REPLACE procedure pro_lock_user is
  p_start_time date := sysdate; -- 跑批开始时间
  /*p_holiday       number := 0;
  p_holiday_count number := 0;*/
  p_error varchar2(1000);
begin
  /* --查询是否为节假日
  select count(*)
    into p_holiday
    from t_holiday t
   where t.holiday = trunc(sysdate);
  
  if p_holiday = 0 then
    --查询节假日计数
    select to_number(t.sysvar_value)
      into p_holiday_count
      from t_sysvar t
     where t.sysvar_code = 'lock_user_holiday_count';*/

  --跑批时，用户当前积分小于0，且解锁时间超过48小时，对用户再次加锁
  update t_user_lock t
     set t.lock_status = '1',
         t.lock_time   = sysdate,
         t.unlock_time = null,
         t.unlock_user = null,
         t.lock_times  = t.lock_times + 1
   where t.month = to_char(sysdate, 'yyyy-mm')
     and t.lock_score < 0
     and round(to_number(sysdate - t.unlock_time) * 24) >=
         48 + 24 * fun_get_holiday_count(t.unlock_time, sysdate)
     and t.lock_status = '0';

  /* --将节假日记为 0
    update t_sysvar t
       set t.sysvar_value = '0'
     where t.sysvar_code = 'lock_user_holiday_count'
       and t.sysvar_value <> '0';
  */
  --插入批处理记录表
  insert into t_batch_log
    select s_batch_log.nextval,
           'pro_lock_user',
           '1',
           p_start_time,
           sysdate,
           '批处理成功',
           sysdate
      from dual;
  /*else
    update t_sysvar t
       set t.sysvar_value = t.sysvar_value + 1
     where t.sysvar_code = 'lock_user_holiday_count'
       and not exists
     (select 1
              from t_batch_log bl
             where trunc(bl.bat_start_time) = trunc(sysdate)
               and bl.bat_name = 'pro_lock_user');
  
    --插入批处理记录表
    insert into t_batch_log
      select s_batch_log.nextval,
             'pro_lock_user',
             '1',
             p_start_time,
             sysdate,
             '批处理成功,本次批处理时为节假日',
             sysdate
        from dual;
  end if;*/

  commit;
exception
  when others then
    rollback;
    p_error := sqlerrm;
    --插入批处理记录表
    insert into t_batch_log
      select s_batch_log.nextval,
             'pro_lock_user',
             '0',
             p_start_time,
             sysdate,
             '批处理失败' || dbms_utility.format_error_backtrace || p_error,
             sysdate
        from dual;
    commit;
end pro_lock_user;

/
