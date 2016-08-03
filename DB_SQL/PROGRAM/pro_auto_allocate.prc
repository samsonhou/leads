CREATE OR REPLACE procedure pro_auto_allocate is
  p_start_time date := sysdate;
  p_user_id    t_user.user_id%type;
  p_client_id  lm_client.id%type;
  p_sid        lm_client.id%type;
  p_error      varchar2(1000);
begin
  --选取C类，且回复等于30天的，原因不为风控的
  for c in (select l.t_id client_id, count(1)
              from lm_clientrace l
             where l.rank = 'C'
               and (l.reason != '3' or l.reason is null)
               and trunc(l.redate + 30) < trunc(sysdate)
             group by l.t_id
            having count(1) = 1) loop
  
    --战败线索及战败人
    begin
      select a.sid, a.t_id
        into p_user_id, p_client_id
        from lm_clientrace a
       where a.t_id = c.client_id
         and trunc(a.redate + 30) < trunc(sysdate)
         and a.sid is not null
         and a.t_id is not null
         and exists
       (select 1
                from t_user tu
                join t_organ_company oc on tu.organ_id = oc.organ_id
               where oc.status = '1'
                 and tu.user_id = a.sid)
         and rownum = 1;
    exception
      when no_data_found then
        p_user_id   := 0;
        p_client_id := 0;
    end;
  
    if p_user_id > 0 and p_client_id > 0 then
      --重新分配给同一分公司昨天分配最少的销售人员
      --查询前一天是否有销售没有分配线索
      begin
        select t.user_id
          into p_sid
          from t_user t
         where t.organ_id =
               (select t.organ_id from t_user t where t.user_id = p_user_id)
           and t.status = '1'
           and not exists
         (select 1
                  from lm_client l
                 where l.sid = t.user_id
                   and trunc(l.allotdate) >= trunc(sysdate - 1))
           and exists (select 1
                  from t_user_role r
                 where r.role_id = 2
                   and t.user_id = r.user_id)
           and t.user_id <> p_user_id
           and rownum = 1;
      exception
        when no_data_found then
          p_sid := 0;
      end;
      --查询前一天分配线索最少的销售人员
      begin
        if p_sid = 0 then
          select sid
            into p_sid
            from (select l.sid, count(*) count
                    from lm_client l
                   where l.sid in (select t.user_id
                                     from t_user t
                                    where t.organ_id =
                                          (select t.organ_id
                                             from t_user t
                                            where t.user_id = p_user_id)
                                      and t.status = '1')
                     and trunc(l.allotdate) >= trunc(sysdate - 1) --昨天至今天的分配量
                     and l.sid <> p_user_id
                   group by l.sid
                   order by count)
           where rownum = 1;
        end if;
      exception
        when no_data_found then
          begin
            select t.user_id
              into p_sid
              from t_user t
             where t.organ_id = (select t.organ_id
                                   from t_user t
                                  where t.user_id = p_user_id)
               and t.status = '1'
               and exists (select 1
                      from t_user_role ur
                     where ur.user_id = t.user_id
                       and ur.role_id = 2)
               and t.user_id <> p_user_id
               and rownum = 1;
          exception
            when no_data_found then
              goto continue;
          end;
      end;
    
      update lm_client lc
         set lc.sid       = p_sid,
             lc.init_rank = 'C',
             lc.rank      = null,
             lc.allotdate = sysdate
       where lc.id = p_client_id;
    
    end if;
    <<continue>>
    null;
  end loop;

  --插入批处理记录表
  insert into t_batch_log
    select s_batch_log.nextval,
           'pro_auto_allocate',
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
             'pro_auto_allocate',
             '0',
             p_start_time,
             sysdate,
             '批处理失败,' || dbms_utility.format_error_backtrace || p_error,
             sysdate
        from dual;
    commit;
end pro_auto_allocate;

/
