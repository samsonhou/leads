update lm_client l set l.fromtype = 1000001002 where l.fromtype = 1;
update lm_client l set l.fromtype = 1000001003 where l.fromtype = 4;
update lm_client l set l.fromtype = 1000001001 where l.fromtype = 6;
update lm_client l set l.fromtype = 1000001012 where l.fromtype = 9;
update lm_client l set l.fromtype = 1000001004 where l.fromtype = 10;
update lm_client l set l.fromtype = 1000001024 where l.fromtype = 11;
update lm_client l set l.fromtype = 1000001005 where l.fromtype = 12;
update lm_client l set l.fromtype = 1000001007 where l.fromtype = 14;
update lm_client l set l.fromtype = 1000001011 where l.fromtype = 15;
update lm_client l set l.fromtype = 1000001006 where l.fromtype = 16;
update lm_client l set l.fromtype = 1000001009 where l.fromtype = 17;
update lm_client l set l.fromtype = 1000000011 where l.fromtype = 19;
update lm_client l set l.fromtype = 1000001015 where l.fromtype = 351;
update lm_client l set l.fromtype = 1000001019 where l.fromtype = 352;
update lm_client l set l.fromtype = 1000001010 where l.fromtype = 353;
update lm_client l set l.fromtype = 1000001018 where l.fromtype = 357;
update lm_client l set l.fromtype = 1000001025 where l.fromtype = 358;
update lm_client l set l.fromtype = 1000001023 where l.fromtype = 359;
update lm_client l set l.fromtype = 1000001020 where l.fromtype = 360;
update lm_client l set l.fromtype = 1000001021 where l.fromtype = 361;
update lm_client l set l.fromtype = 1000001017 where l.fromtype = 390;
update lm_client l set l.fromtype = 1000002000 where l.fromtype = 391;
update lm_client l set l.fromtype = 1000000000 where l.fromtype = 392;
update lm_client l set l.fromtype = 1000001013 where l.fromtype = 510;
update lm_client l set l.fromtype = 1000001014 where l.fromtype = 511;
update lm_client l set l.fromtype = 1000000008 where l.fromtype = 550;
update lm_client l set l.fromtype = 1000000010 where l.fromtype = 630;
update lm_client l set l.fromtype = 1000000016 where l.fromtype = 631;                         
update lm_client l set l.fromtype = 1000000001 where l.fromtype = 690;
update lm_client l set l.fromtype = 1000000002 where l.fromtype = 710;
update lm_client l set l.fromtype = 1000000019 where l.fromtype = 730;
update lm_client l set l.fromtype = 1000000003 where l.fromtype = 750;
update lm_client l set l.fromtype = 1000000007 where l.fromtype = 751;
update lm_client l set l.fromtype = 1000000006 where l.fromtype = 770;
update lm_client l set l.fromtype = 1000000009 where l.fromtype = 790;
update lm_client l set l.fromtype = 1000000004 where l.fromtype = 810;
update lm_client l set l.fromtype = 1000000017 where l.fromtype = 870;
update lm_client l set l.fromtype = 1000000015 where l.fromtype = 910;
update lm_client l set l.fromtype = 1000000012 where l.fromtype = 930;
update lm_client l set l.fromtype = 1000000013 where l.fromtype = 931;
update lm_client l set l.fromtype = 1000000014 where l.fromtype = 932;
update lm_client l set l.fromtype = 1000000018 where l.fromtype = 970;
update lm_client l set l.fromtype = 1000002002 where l.fromtype = 1010;                         
update lm_client l set l.fromtype = 1000001026 where l.fromtype = 1031;
update lm_client l set l.fromtype = 1000001027 where l.fromtype = 1032;
update lm_client l set l.fromtype = 1000000020 where l.fromtype = 1033;
update lm_client l set l.fromtype = 1001001007 where l.fromtype = 590;
update lm_client l set l.fromtype = 1001001008 where l.fromtype = 592;


insert into t_code_type
  (NAME, CODE_TYPE, CREATE_DATE, STATUS, SQL, TYPE)
values
  ('线索来源（类滴滴）',
   '1043',
   sysdate,
   '1',
   'select t.name NAME, t.code VALUE
  from t_base_source t
 where t.code  in
       (1000002000, 1000000008, 1000000003, 1000000001, 1000000006, 1000000019,
        1000000007, 1000000004, 1000000009, 1000001007, 1000000000,
        1000001003, 1000001001, 1000000002, 1000000012, 1000000013,
        1000000014, 1000000018)',
   '0');