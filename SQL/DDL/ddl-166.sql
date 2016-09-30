-- Create table
create table T_BASE_SOURCE
(
  ID              NUMBER(10) not null,
  PID             VARCHAR2(20),
  CODE            VARCHAR2(10),
  NAME            VARCHAR2(50),
  CODE_LEVEL      VARCHAR2(5),
  STATUS          VARCHAR2(10),
  CREATED_TIME    DATE default SYSDATE,
  CREATED_USER_ID NUMBER(10),
  UPDATED_TIME    DATE,
  UPDATED_USER_ID NUMBER(10)
);
-- Add comments to the table 
comment on table T_BASE_SOURCE
  is '线索来源';
-- Add comments to the columns 
comment on column T_BASE_SOURCE.ID
  is '数据ID';
comment on column T_BASE_SOURCE.PID
  is '数据PID';
comment on column T_BASE_SOURCE.CODE
  is '编码';
comment on column T_BASE_SOURCE.NAME
  is '名称';
comment on column T_BASE_SOURCE.STATUS
  is '状态';
comment on column T_BASE_SOURCE.CREATED_TIME
  is '创建时间';
comment on column T_BASE_SOURCE.CREATED_USER_ID
  is '创建人ID';
comment on column T_BASE_SOURCE.UPDATED_TIME
  is '更新时间';
comment on column T_BASE_SOURCE.UPDATED_USER_ID
  is '更新人ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_BASE_SOURCE
  add constraint PK_T_BASE_SOURCE primary key (ID);

--create sequence
create sequence s_base_source_id start with 1200;