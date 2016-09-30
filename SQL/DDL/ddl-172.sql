create table T_ORGAN_SYN
(
  ID          NUMBER(10) not null,
  ORGAN_ID    NUMBER(10),
  STORE_ID    NUMBER(10),
  CITY_ID     NUMBER(10),
  STORE_NAME  VARCHAR2(100),
  STORE_ADD   VARCHAR2(200),
  ORG_CODE    VARCHAR2(30),
  STATUS      VARCHAR2(2),
  CREATE_DATE DATE default SYSDATE,
  UPDATE_DATE DATE default SYSDATE
);

comment on table T_ORGAN_SYN is '部门同步表';
comment on column T_ORGAN_SYN.ID is 'ID';
comment on column T_ORGAN_SYN.ORGAN_ID is '本库门店ID';
comment on column T_ORGAN_SYN.STORE_ID is '外库门店ID';
comment on column T_ORGAN_SYN.CITY_ID is '城市ID';
comment on column T_ORGAN_SYN.STORE_NAME is '门店名称';
comment on column T_ORGAN_SYN.STORE_ADD is '门店地址';
comment on column T_ORGAN_SYN.ORG_CODE is '组织机构编码';
comment on column T_ORGAN_SYN.STATUS is '同步类型： 1 新增 2 修改 3 删除';
comment on column T_ORGAN_SYN.CREATE_DATE is '同步时间';
comment on column T_ORGAN_SYN.UPDATE_DATE is '更新时间';

alter table T_ORGAN_SYN
  add constraint PK_ORGAN_SYN primary key (ID);
  
create sequence S_ORGAN_SYN start with 100;