CREATE OR REPLACE procedure pro_dblink_erp is
  p_start_time date := sysdate;
  p_error      varchar2(1000);
begin
  --同步捷众数据
  for c in (select t1.name,
                   t1.tel_phone,
                   t2.create_time,
                   t2.pro_code,
                   t3.catena_name,
                   t3.spec_name,
                   t5.scheme_name,
                   t2.status
              from fil_cust_client t1
              join fil_project_head t2 on t1.id = t2.client_id
              left join fil_project_equipment t3 on t3.project_id = t2.id
              left join fil_project_scheme t4 on t4.project_id = t2.id
              left join t_base_scheme t5 on t5.scheme_code = t4.scheme_code
                                        and t5.key_name_zn = '租赁周期') loop
    insert into link_erp_report_temp
      select s_link_erp_report_temp.nextval,
             t6.id,
             c.name,
             c.tel_phone,
             c.create_time,
             c.pro_code,
             c.catena_name,
             c.spec_name,
             c.scheme_name,
             sysdate,
             '22'
        from lm_client t6
       where t6.tel = c.tel_phone
         and not exists (select 1
                from link_erp_report_temp t7
               where t7.l_tel_phone = c.tel_phone);
  
  end loop;
  --同步信审状态
  /* update lm_client t
  set t.credit_status = (select a.flag
                           from t_sys_site_dictionary a,
                                fil_project_head      b,
                                fil_cust_client       c
                          where a.type = '项目状态位'
                            and a.status != -2
                            and to_char(b.status) = a.code
                            and b.client_id = c.id
                            and b.id =
                                (select max(d.id)
                                   from fil_project_head d,
                                        fil_cust_client  e
                                  where d.client_id = e.id
                                    and e.tel_phone = c.tel_phone)
                            and c.tel_phone = t.tel);*/
  for c in (select a.status_new, a.id, b.status, b.tel_phone
              from fil_project_head a, fil_cust_client b
             where
            
             a.client_id = b.id
         and a.id = (select max(c.id)
                       from fil_project_head c, fil_cust_client d
                      where c.client_id = d.id
                        and d.tel_phone = b.tel_phone)) loop
    update lm_client t
       set t.credit_status = c.status_new
     where t.tel = c.tel_phone;
  end loop;

  for c in (select t1.name,
                   t1.tel_phone,
                   t2.create_time,
                   t2.pro_code,
                   t3.catena_name,
                   t3.spec_name,
                   t5.scheme_name
              from fil_cust_client t1
              join fil_project_head t2 on t1.id = t2.client_id
              left join fil_project_equipment t3 on t3.project_id = t2.id
              left join fil_project_scheme t4 on t4.project_id = t2.id
              left join t_base_scheme t5 on t5.scheme_code = t4.scheme_code
                                        and t5.key_name_zn = '租赁周期'
             where t2.lease_code is not null) loop
    insert into link_erp_report
      select s_link_erp_report.nextval,
             t6.id,
             c.name,
             c.tel_phone,
             c.create_time,
             c.pro_code,
             c.catena_name,
             c.spec_name,
             c.scheme_name,
             sysdate,
             '22'
        from lm_client t6
       where t6.tel = c.tel_phone
         and not exists (select 1
                from link_erp_report t7
               where t7.l_tel_phone = c.tel_phone);
  end loop;

  /* --同步结义数据
  for c in (select t1.name,
                   t1.tel_phone,
                   t2.create_time,
                   t2.pro_code,
                   t3.catena_name,
                   t3.spec_name,
                   t5.scheme_name
              from fil_cust_client@JY_ERP t1
              join fil_project_head@JY_ERP t2 on t1.id = t2.client_id
              left join fil_project_equipment@JY_ERP t3 on t3.project_id =
                                                           t2.id
              left join fil_project_scheme@JY_ERP t4 on t4.project_id =
                                                        t2.id
              left join t_base_scheme@JY_ERP t5 on t5.scheme_code =
                                                   t4.scheme_code
                                               and t5.key_name_zn = '租赁周期') loop
    insert into link_erp_report_temp
      select s_link_erp_report_temp.nextval,
             t6.id,
             c.name,
             c.tel_phone,
             c.create_time,
             c.pro_code,
             c.catena_name,
             c.spec_name,
             c.scheme_name,
             sysdate,
             '24'
        from lm_client t6
       where t6.tel = c.tel_phone
         and not exists (select 1
                from link_erp_report_temp t7
               where t7.l_tel_phone = c.tel_phone);
  end loop;
  
  for c in (select t1.name,
                   t1.tel_phone,
                   t2.create_time,
                   t2.pro_code,
                   t3.catena_name,
                   t3.spec_name,
                   t5.scheme_name
              from fil_cust_client@JY_ERP t1
              join fil_project_head@JY_ERP t2 on t1.id = t2.client_id
              left join fil_project_equipment@JY_ERP t3 on t3.project_id =
                                                           t2.id
              left join fil_project_scheme@JY_ERP t4 on t4.project_id =
                                                        t2.id
              left join t_base_scheme@JY_ERP t5 on t5.scheme_code =
                                                   t4.scheme_code
                                               and t5.key_name_zn = '租赁周期'
             where t2.lease_code is not null) loop
    insert into link_erp_report
      select s_link_erp_report.nextval,
             t6.id,
             c.name,
             c.tel_phone,
             c.create_time,
             c.pro_code,
             c.catena_name,
             c.spec_name,
             c.scheme_name,
             sysdate,
             '24'
        from lm_client t6
       where t6.tel = c.tel_phone
         and not exists (select 1
                from link_erp_report t7
               where t7.l_tel_phone = c.tel_phone);
  end loop;*/
  insert into t_batch_log
    select s_batch_log.nextval,
           'pro_dblink_erp',
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
    p_error := SQLERRM;
    insert into t_batch_log
      select s_batch_log.nextval,
             'pro_dblink_erp',
             '0',
             p_start_time,
             sysdate,
             '批处理失败' || dbms_utility.format_error_backtrace || p_error,
             sysdate
        from dual;
    commit;
  
end pro_dblink_erp;

/
