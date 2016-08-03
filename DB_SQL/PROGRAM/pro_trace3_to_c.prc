CREATE OR REPLACE procedure pro_trace3_to_c is
  p_start_time date := sysdate;
  p_error      varchar2(1000);

begin
  --回复3次且状态不为C，O 且 回复超过15天
  for c in (select lct.t_id client_id, lct.sid
              from lm_clientrace lct, lm_client lc
             where lct.t_id = lc.id
               and lct.sid = lc.sid
               and lc.rank not in ('C', 'O')
             group by lct.t_id, lct.sid
            having count(1) > 2 and max(trunc(lct.redate)) = trunc(sysdate - 15)) loop
  
    --修改线索表中的状态及原因
    update lm_client lc
       set lc.rank       = 'C',
           lc.reason     = '4',
           lc.reasoncont = '已超时15天未处理，自动放弃'
     where lc.id = c.client_id
       and lc.sid = c.sid;
    --插入线索跟踪表中一条记录
    insert into lm_clientrace lct
      select s_lm_clienttrace_id.nextval,
             '已超时15天未处理，自动放弃',
             c.client_id,
             sysdate,
             (select tu.real_name from t_user tu where tu.user_id = c.sid),
             'C',
             '4',
             c.sid
        from dual;
  
  end loop;
  --插入批处理记录表
  insert into t_batch_log
    select s_batch_log.nextval,
           'pro_trace3_to_c',
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
             'pro_trace3_to_c',
             '0',
             p_start_time,
             sysdate,
             '批处理失败,' || dbms_utility.format_error_backtrace || p_error,
             sysdate
        from dual;
    commit;
end pro_trace3_to_c;

/
