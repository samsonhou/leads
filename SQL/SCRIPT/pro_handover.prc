--工作交接
create or replace procedure pro_handover(g_sid       in number,
                                         g_companyid in varchar2,
                                         g_count     out number,
                                         g_msg       out varchar2) is
  p_client_count   number; --线索数量
  p_manager_count  number; --客户经理数量
  p_handover_count number; --交接给每个客户经理线索数量
begin
  --查询客户经理下所有有效线索
  select count(1)
    into p_client_count
    from lm_client l
   where l.sid = g_sid
     and l.sex = '1';
  if p_client_count = 0 then
    g_msg   := '该客户经理没有线索';
    g_count := 0;
    goto continue;
  end if;

  --查询机构下所有客户经理人数
  select count(1)
    into p_manager_count
    from t_user a, t_user_role b
   where a.user_id = b.user_id
     and a.organ_id = g_companyid
     and a.status = '1'
     and a.user_id != g_sid;
  if p_manager_count = 0 then
    g_msg   := '同机构没有其他客户经理';
    g_count := 0;
    goto continue;
  end if;
  p_handover_count := p_client_count / p_manager_count + 1;
  for c in (select a.user_id
              from t_user a, t_user_role b
             where a.user_id = b.user_id
               and a.organ_id = g_companyid
               and a.status = '1'
               and a.user_id != g_sid) loop
    update lm_client l
       set l.sid = c.user_id
     where l.sid = g_sid
       and l.sex = '1'
       and rownum < p_handover_count;
  end loop;
  g_count := p_client_count;
  g_msg   := '共' || p_client_count || '条线索被交接';
  <<continue>>
  null;
end pro_handover;
/
